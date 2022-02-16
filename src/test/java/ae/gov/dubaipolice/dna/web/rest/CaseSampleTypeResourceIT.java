package ae.gov.dubaipolice.dna.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import ae.gov.dubaipolice.dna.IntegrationTest;
import ae.gov.dubaipolice.dna.domain.CaseSampleType;
import ae.gov.dubaipolice.dna.repository.CaseSampleTypeRepository;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link CaseSampleTypeResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CaseSampleTypeResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/case-sample-types";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CaseSampleTypeRepository caseSampleTypeRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCaseSampleTypeMockMvc;

    private CaseSampleType caseSampleType;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CaseSampleType createEntity(EntityManager em) {
        CaseSampleType caseSampleType = new CaseSampleType().name(DEFAULT_NAME).description(DEFAULT_DESCRIPTION);
        return caseSampleType;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CaseSampleType createUpdatedEntity(EntityManager em) {
        CaseSampleType caseSampleType = new CaseSampleType().name(UPDATED_NAME).description(UPDATED_DESCRIPTION);
        return caseSampleType;
    }

    @BeforeEach
    public void initTest() {
        caseSampleType = createEntity(em);
    }

    @Test
    @Transactional
    void createCaseSampleType() throws Exception {
        int databaseSizeBeforeCreate = caseSampleTypeRepository.findAll().size();
        // Create the CaseSampleType
        restCaseSampleTypeMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(caseSampleType))
            )
            .andExpect(status().isCreated());

        // Validate the CaseSampleType in the database
        List<CaseSampleType> caseSampleTypeList = caseSampleTypeRepository.findAll();
        assertThat(caseSampleTypeList).hasSize(databaseSizeBeforeCreate + 1);
        CaseSampleType testCaseSampleType = caseSampleTypeList.get(caseSampleTypeList.size() - 1);
        assertThat(testCaseSampleType.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCaseSampleType.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    void createCaseSampleTypeWithExistingId() throws Exception {
        // Create the CaseSampleType with an existing ID
        caseSampleType.setId(1L);

        int databaseSizeBeforeCreate = caseSampleTypeRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCaseSampleTypeMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(caseSampleType))
            )
            .andExpect(status().isBadRequest());

        // Validate the CaseSampleType in the database
        List<CaseSampleType> caseSampleTypeList = caseSampleTypeRepository.findAll();
        assertThat(caseSampleTypeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllCaseSampleTypes() throws Exception {
        // Initialize the database
        caseSampleTypeRepository.saveAndFlush(caseSampleType);

        // Get all the caseSampleTypeList
        restCaseSampleTypeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(caseSampleType.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));
    }

    @Test
    @Transactional
    void getCaseSampleType() throws Exception {
        // Initialize the database
        caseSampleTypeRepository.saveAndFlush(caseSampleType);

        // Get the caseSampleType
        restCaseSampleTypeMockMvc
            .perform(get(ENTITY_API_URL_ID, caseSampleType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(caseSampleType.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION));
    }

    @Test
    @Transactional
    void getNonExistingCaseSampleType() throws Exception {
        // Get the caseSampleType
        restCaseSampleTypeMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewCaseSampleType() throws Exception {
        // Initialize the database
        caseSampleTypeRepository.saveAndFlush(caseSampleType);

        int databaseSizeBeforeUpdate = caseSampleTypeRepository.findAll().size();

        // Update the caseSampleType
        CaseSampleType updatedCaseSampleType = caseSampleTypeRepository.findById(caseSampleType.getId()).get();
        // Disconnect from session so that the updates on updatedCaseSampleType are not directly saved in db
        em.detach(updatedCaseSampleType);
        updatedCaseSampleType.name(UPDATED_NAME).description(UPDATED_DESCRIPTION);

        restCaseSampleTypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedCaseSampleType.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedCaseSampleType))
            )
            .andExpect(status().isOk());

        // Validate the CaseSampleType in the database
        List<CaseSampleType> caseSampleTypeList = caseSampleTypeRepository.findAll();
        assertThat(caseSampleTypeList).hasSize(databaseSizeBeforeUpdate);
        CaseSampleType testCaseSampleType = caseSampleTypeList.get(caseSampleTypeList.size() - 1);
        assertThat(testCaseSampleType.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCaseSampleType.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void putNonExistingCaseSampleType() throws Exception {
        int databaseSizeBeforeUpdate = caseSampleTypeRepository.findAll().size();
        caseSampleType.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCaseSampleTypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, caseSampleType.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(caseSampleType))
            )
            .andExpect(status().isBadRequest());

        // Validate the CaseSampleType in the database
        List<CaseSampleType> caseSampleTypeList = caseSampleTypeRepository.findAll();
        assertThat(caseSampleTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCaseSampleType() throws Exception {
        int databaseSizeBeforeUpdate = caseSampleTypeRepository.findAll().size();
        caseSampleType.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCaseSampleTypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(caseSampleType))
            )
            .andExpect(status().isBadRequest());

        // Validate the CaseSampleType in the database
        List<CaseSampleType> caseSampleTypeList = caseSampleTypeRepository.findAll();
        assertThat(caseSampleTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCaseSampleType() throws Exception {
        int databaseSizeBeforeUpdate = caseSampleTypeRepository.findAll().size();
        caseSampleType.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCaseSampleTypeMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(caseSampleType))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CaseSampleType in the database
        List<CaseSampleType> caseSampleTypeList = caseSampleTypeRepository.findAll();
        assertThat(caseSampleTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCaseSampleTypeWithPatch() throws Exception {
        // Initialize the database
        caseSampleTypeRepository.saveAndFlush(caseSampleType);

        int databaseSizeBeforeUpdate = caseSampleTypeRepository.findAll().size();

        // Update the caseSampleType using partial update
        CaseSampleType partialUpdatedCaseSampleType = new CaseSampleType();
        partialUpdatedCaseSampleType.setId(caseSampleType.getId());

        partialUpdatedCaseSampleType.description(UPDATED_DESCRIPTION);

        restCaseSampleTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCaseSampleType.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCaseSampleType))
            )
            .andExpect(status().isOk());

        // Validate the CaseSampleType in the database
        List<CaseSampleType> caseSampleTypeList = caseSampleTypeRepository.findAll();
        assertThat(caseSampleTypeList).hasSize(databaseSizeBeforeUpdate);
        CaseSampleType testCaseSampleType = caseSampleTypeList.get(caseSampleTypeList.size() - 1);
        assertThat(testCaseSampleType.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCaseSampleType.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void fullUpdateCaseSampleTypeWithPatch() throws Exception {
        // Initialize the database
        caseSampleTypeRepository.saveAndFlush(caseSampleType);

        int databaseSizeBeforeUpdate = caseSampleTypeRepository.findAll().size();

        // Update the caseSampleType using partial update
        CaseSampleType partialUpdatedCaseSampleType = new CaseSampleType();
        partialUpdatedCaseSampleType.setId(caseSampleType.getId());

        partialUpdatedCaseSampleType.name(UPDATED_NAME).description(UPDATED_DESCRIPTION);

        restCaseSampleTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCaseSampleType.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCaseSampleType))
            )
            .andExpect(status().isOk());

        // Validate the CaseSampleType in the database
        List<CaseSampleType> caseSampleTypeList = caseSampleTypeRepository.findAll();
        assertThat(caseSampleTypeList).hasSize(databaseSizeBeforeUpdate);
        CaseSampleType testCaseSampleType = caseSampleTypeList.get(caseSampleTypeList.size() - 1);
        assertThat(testCaseSampleType.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCaseSampleType.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void patchNonExistingCaseSampleType() throws Exception {
        int databaseSizeBeforeUpdate = caseSampleTypeRepository.findAll().size();
        caseSampleType.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCaseSampleTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, caseSampleType.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(caseSampleType))
            )
            .andExpect(status().isBadRequest());

        // Validate the CaseSampleType in the database
        List<CaseSampleType> caseSampleTypeList = caseSampleTypeRepository.findAll();
        assertThat(caseSampleTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCaseSampleType() throws Exception {
        int databaseSizeBeforeUpdate = caseSampleTypeRepository.findAll().size();
        caseSampleType.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCaseSampleTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(caseSampleType))
            )
            .andExpect(status().isBadRequest());

        // Validate the CaseSampleType in the database
        List<CaseSampleType> caseSampleTypeList = caseSampleTypeRepository.findAll();
        assertThat(caseSampleTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCaseSampleType() throws Exception {
        int databaseSizeBeforeUpdate = caseSampleTypeRepository.findAll().size();
        caseSampleType.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCaseSampleTypeMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(caseSampleType))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CaseSampleType in the database
        List<CaseSampleType> caseSampleTypeList = caseSampleTypeRepository.findAll();
        assertThat(caseSampleTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCaseSampleType() throws Exception {
        // Initialize the database
        caseSampleTypeRepository.saveAndFlush(caseSampleType);

        int databaseSizeBeforeDelete = caseSampleTypeRepository.findAll().size();

        // Delete the caseSampleType
        restCaseSampleTypeMockMvc
            .perform(delete(ENTITY_API_URL_ID, caseSampleType.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CaseSampleType> caseSampleTypeList = caseSampleTypeRepository.findAll();
        assertThat(caseSampleTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
