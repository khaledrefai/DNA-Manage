package ae.gov.dubaipolice.dna.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import ae.gov.dubaipolice.dna.IntegrationTest;
import ae.gov.dubaipolice.dna.domain.DnaProfileType;
import ae.gov.dubaipolice.dna.repository.DnaProfileTypeRepository;
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
 * Integration tests for the {@link DnaProfileTypeResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class DnaProfileTypeResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/dna-profile-types";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private DnaProfileTypeRepository dnaProfileTypeRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDnaProfileTypeMockMvc;

    private DnaProfileType dnaProfileType;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DnaProfileType createEntity(EntityManager em) {
        DnaProfileType dnaProfileType = new DnaProfileType().name(DEFAULT_NAME).description(DEFAULT_DESCRIPTION);
        return dnaProfileType;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DnaProfileType createUpdatedEntity(EntityManager em) {
        DnaProfileType dnaProfileType = new DnaProfileType().name(UPDATED_NAME).description(UPDATED_DESCRIPTION);
        return dnaProfileType;
    }

    @BeforeEach
    public void initTest() {
        dnaProfileType = createEntity(em);
    }

    @Test
    @Transactional
    void createDnaProfileType() throws Exception {
        int databaseSizeBeforeCreate = dnaProfileTypeRepository.findAll().size();
        // Create the DnaProfileType
        restDnaProfileTypeMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(dnaProfileType))
            )
            .andExpect(status().isCreated());

        // Validate the DnaProfileType in the database
        List<DnaProfileType> dnaProfileTypeList = dnaProfileTypeRepository.findAll();
        assertThat(dnaProfileTypeList).hasSize(databaseSizeBeforeCreate + 1);
        DnaProfileType testDnaProfileType = dnaProfileTypeList.get(dnaProfileTypeList.size() - 1);
        assertThat(testDnaProfileType.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testDnaProfileType.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    void createDnaProfileTypeWithExistingId() throws Exception {
        // Create the DnaProfileType with an existing ID
        dnaProfileType.setId(1L);

        int databaseSizeBeforeCreate = dnaProfileTypeRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restDnaProfileTypeMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(dnaProfileType))
            )
            .andExpect(status().isBadRequest());

        // Validate the DnaProfileType in the database
        List<DnaProfileType> dnaProfileTypeList = dnaProfileTypeRepository.findAll();
        assertThat(dnaProfileTypeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllDnaProfileTypes() throws Exception {
        // Initialize the database
        dnaProfileTypeRepository.saveAndFlush(dnaProfileType);

        // Get all the dnaProfileTypeList
        restDnaProfileTypeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(dnaProfileType.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));
    }

    @Test
    @Transactional
    void getDnaProfileType() throws Exception {
        // Initialize the database
        dnaProfileTypeRepository.saveAndFlush(dnaProfileType);

        // Get the dnaProfileType
        restDnaProfileTypeMockMvc
            .perform(get(ENTITY_API_URL_ID, dnaProfileType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(dnaProfileType.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION));
    }

    @Test
    @Transactional
    void getNonExistingDnaProfileType() throws Exception {
        // Get the dnaProfileType
        restDnaProfileTypeMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewDnaProfileType() throws Exception {
        // Initialize the database
        dnaProfileTypeRepository.saveAndFlush(dnaProfileType);

        int databaseSizeBeforeUpdate = dnaProfileTypeRepository.findAll().size();

        // Update the dnaProfileType
        DnaProfileType updatedDnaProfileType = dnaProfileTypeRepository.findById(dnaProfileType.getId()).get();
        // Disconnect from session so that the updates on updatedDnaProfileType are not directly saved in db
        em.detach(updatedDnaProfileType);
        updatedDnaProfileType.name(UPDATED_NAME).description(UPDATED_DESCRIPTION);

        restDnaProfileTypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedDnaProfileType.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedDnaProfileType))
            )
            .andExpect(status().isOk());

        // Validate the DnaProfileType in the database
        List<DnaProfileType> dnaProfileTypeList = dnaProfileTypeRepository.findAll();
        assertThat(dnaProfileTypeList).hasSize(databaseSizeBeforeUpdate);
        DnaProfileType testDnaProfileType = dnaProfileTypeList.get(dnaProfileTypeList.size() - 1);
        assertThat(testDnaProfileType.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testDnaProfileType.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void putNonExistingDnaProfileType() throws Exception {
        int databaseSizeBeforeUpdate = dnaProfileTypeRepository.findAll().size();
        dnaProfileType.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDnaProfileTypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, dnaProfileType.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(dnaProfileType))
            )
            .andExpect(status().isBadRequest());

        // Validate the DnaProfileType in the database
        List<DnaProfileType> dnaProfileTypeList = dnaProfileTypeRepository.findAll();
        assertThat(dnaProfileTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchDnaProfileType() throws Exception {
        int databaseSizeBeforeUpdate = dnaProfileTypeRepository.findAll().size();
        dnaProfileType.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDnaProfileTypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(dnaProfileType))
            )
            .andExpect(status().isBadRequest());

        // Validate the DnaProfileType in the database
        List<DnaProfileType> dnaProfileTypeList = dnaProfileTypeRepository.findAll();
        assertThat(dnaProfileTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamDnaProfileType() throws Exception {
        int databaseSizeBeforeUpdate = dnaProfileTypeRepository.findAll().size();
        dnaProfileType.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDnaProfileTypeMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(dnaProfileType))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the DnaProfileType in the database
        List<DnaProfileType> dnaProfileTypeList = dnaProfileTypeRepository.findAll();
        assertThat(dnaProfileTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateDnaProfileTypeWithPatch() throws Exception {
        // Initialize the database
        dnaProfileTypeRepository.saveAndFlush(dnaProfileType);

        int databaseSizeBeforeUpdate = dnaProfileTypeRepository.findAll().size();

        // Update the dnaProfileType using partial update
        DnaProfileType partialUpdatedDnaProfileType = new DnaProfileType();
        partialUpdatedDnaProfileType.setId(dnaProfileType.getId());

        partialUpdatedDnaProfileType.name(UPDATED_NAME).description(UPDATED_DESCRIPTION);

        restDnaProfileTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDnaProfileType.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDnaProfileType))
            )
            .andExpect(status().isOk());

        // Validate the DnaProfileType in the database
        List<DnaProfileType> dnaProfileTypeList = dnaProfileTypeRepository.findAll();
        assertThat(dnaProfileTypeList).hasSize(databaseSizeBeforeUpdate);
        DnaProfileType testDnaProfileType = dnaProfileTypeList.get(dnaProfileTypeList.size() - 1);
        assertThat(testDnaProfileType.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testDnaProfileType.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void fullUpdateDnaProfileTypeWithPatch() throws Exception {
        // Initialize the database
        dnaProfileTypeRepository.saveAndFlush(dnaProfileType);

        int databaseSizeBeforeUpdate = dnaProfileTypeRepository.findAll().size();

        // Update the dnaProfileType using partial update
        DnaProfileType partialUpdatedDnaProfileType = new DnaProfileType();
        partialUpdatedDnaProfileType.setId(dnaProfileType.getId());

        partialUpdatedDnaProfileType.name(UPDATED_NAME).description(UPDATED_DESCRIPTION);

        restDnaProfileTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDnaProfileType.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDnaProfileType))
            )
            .andExpect(status().isOk());

        // Validate the DnaProfileType in the database
        List<DnaProfileType> dnaProfileTypeList = dnaProfileTypeRepository.findAll();
        assertThat(dnaProfileTypeList).hasSize(databaseSizeBeforeUpdate);
        DnaProfileType testDnaProfileType = dnaProfileTypeList.get(dnaProfileTypeList.size() - 1);
        assertThat(testDnaProfileType.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testDnaProfileType.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void patchNonExistingDnaProfileType() throws Exception {
        int databaseSizeBeforeUpdate = dnaProfileTypeRepository.findAll().size();
        dnaProfileType.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDnaProfileTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, dnaProfileType.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(dnaProfileType))
            )
            .andExpect(status().isBadRequest());

        // Validate the DnaProfileType in the database
        List<DnaProfileType> dnaProfileTypeList = dnaProfileTypeRepository.findAll();
        assertThat(dnaProfileTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchDnaProfileType() throws Exception {
        int databaseSizeBeforeUpdate = dnaProfileTypeRepository.findAll().size();
        dnaProfileType.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDnaProfileTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(dnaProfileType))
            )
            .andExpect(status().isBadRequest());

        // Validate the DnaProfileType in the database
        List<DnaProfileType> dnaProfileTypeList = dnaProfileTypeRepository.findAll();
        assertThat(dnaProfileTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamDnaProfileType() throws Exception {
        int databaseSizeBeforeUpdate = dnaProfileTypeRepository.findAll().size();
        dnaProfileType.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDnaProfileTypeMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(dnaProfileType))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the DnaProfileType in the database
        List<DnaProfileType> dnaProfileTypeList = dnaProfileTypeRepository.findAll();
        assertThat(dnaProfileTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteDnaProfileType() throws Exception {
        // Initialize the database
        dnaProfileTypeRepository.saveAndFlush(dnaProfileType);

        int databaseSizeBeforeDelete = dnaProfileTypeRepository.findAll().size();

        // Delete the dnaProfileType
        restDnaProfileTypeMockMvc
            .perform(delete(ENTITY_API_URL_ID, dnaProfileType.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<DnaProfileType> dnaProfileTypeList = dnaProfileTypeRepository.findAll();
        assertThat(dnaProfileTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
