package ae.gov.dubaipolice.dna.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import ae.gov.dubaipolice.dna.IntegrationTest;
import ae.gov.dubaipolice.dna.domain.SampleType;
import ae.gov.dubaipolice.dna.repository.SampleTypeRepository;
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
 * Integration tests for the {@link SampleTypeResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class SampleTypeResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/sample-types";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private SampleTypeRepository sampleTypeRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSampleTypeMockMvc;

    private SampleType sampleType;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SampleType createEntity(EntityManager em) {
        SampleType sampleType = new SampleType().name(DEFAULT_NAME).description(DEFAULT_DESCRIPTION);
        return sampleType;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SampleType createUpdatedEntity(EntityManager em) {
        SampleType sampleType = new SampleType().name(UPDATED_NAME).description(UPDATED_DESCRIPTION);
        return sampleType;
    }

    @BeforeEach
    public void initTest() {
        sampleType = createEntity(em);
    }

    @Test
    @Transactional
    void createSampleType() throws Exception {
        int databaseSizeBeforeCreate = sampleTypeRepository.findAll().size();
        // Create the SampleType
        restSampleTypeMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(sampleType))
            )
            .andExpect(status().isCreated());

        // Validate the SampleType in the database
        List<SampleType> sampleTypeList = sampleTypeRepository.findAll();
        assertThat(sampleTypeList).hasSize(databaseSizeBeforeCreate + 1);
        SampleType testSampleType = sampleTypeList.get(sampleTypeList.size() - 1);
        assertThat(testSampleType.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testSampleType.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    void createSampleTypeWithExistingId() throws Exception {
        // Create the SampleType with an existing ID
        sampleType.setId(1L);

        int databaseSizeBeforeCreate = sampleTypeRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restSampleTypeMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(sampleType))
            )
            .andExpect(status().isBadRequest());

        // Validate the SampleType in the database
        List<SampleType> sampleTypeList = sampleTypeRepository.findAll();
        assertThat(sampleTypeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllSampleTypes() throws Exception {
        // Initialize the database
        sampleTypeRepository.saveAndFlush(sampleType);

        // Get all the sampleTypeList
        restSampleTypeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(sampleType.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));
    }

    @Test
    @Transactional
    void getSampleType() throws Exception {
        // Initialize the database
        sampleTypeRepository.saveAndFlush(sampleType);

        // Get the sampleType
        restSampleTypeMockMvc
            .perform(get(ENTITY_API_URL_ID, sampleType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(sampleType.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION));
    }

    @Test
    @Transactional
    void getNonExistingSampleType() throws Exception {
        // Get the sampleType
        restSampleTypeMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewSampleType() throws Exception {
        // Initialize the database
        sampleTypeRepository.saveAndFlush(sampleType);

        int databaseSizeBeforeUpdate = sampleTypeRepository.findAll().size();

        // Update the sampleType
        SampleType updatedSampleType = sampleTypeRepository.findById(sampleType.getId()).get();
        // Disconnect from session so that the updates on updatedSampleType are not directly saved in db
        em.detach(updatedSampleType);
        updatedSampleType.name(UPDATED_NAME).description(UPDATED_DESCRIPTION);

        restSampleTypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedSampleType.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedSampleType))
            )
            .andExpect(status().isOk());

        // Validate the SampleType in the database
        List<SampleType> sampleTypeList = sampleTypeRepository.findAll();
        assertThat(sampleTypeList).hasSize(databaseSizeBeforeUpdate);
        SampleType testSampleType = sampleTypeList.get(sampleTypeList.size() - 1);
        assertThat(testSampleType.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testSampleType.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void putNonExistingSampleType() throws Exception {
        int databaseSizeBeforeUpdate = sampleTypeRepository.findAll().size();
        sampleType.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSampleTypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, sampleType.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(sampleType))
            )
            .andExpect(status().isBadRequest());

        // Validate the SampleType in the database
        List<SampleType> sampleTypeList = sampleTypeRepository.findAll();
        assertThat(sampleTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchSampleType() throws Exception {
        int databaseSizeBeforeUpdate = sampleTypeRepository.findAll().size();
        sampleType.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSampleTypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(sampleType))
            )
            .andExpect(status().isBadRequest());

        // Validate the SampleType in the database
        List<SampleType> sampleTypeList = sampleTypeRepository.findAll();
        assertThat(sampleTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamSampleType() throws Exception {
        int databaseSizeBeforeUpdate = sampleTypeRepository.findAll().size();
        sampleType.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSampleTypeMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(sampleType))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the SampleType in the database
        List<SampleType> sampleTypeList = sampleTypeRepository.findAll();
        assertThat(sampleTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateSampleTypeWithPatch() throws Exception {
        // Initialize the database
        sampleTypeRepository.saveAndFlush(sampleType);

        int databaseSizeBeforeUpdate = sampleTypeRepository.findAll().size();

        // Update the sampleType using partial update
        SampleType partialUpdatedSampleType = new SampleType();
        partialUpdatedSampleType.setId(sampleType.getId());

        partialUpdatedSampleType.description(UPDATED_DESCRIPTION);

        restSampleTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSampleType.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSampleType))
            )
            .andExpect(status().isOk());

        // Validate the SampleType in the database
        List<SampleType> sampleTypeList = sampleTypeRepository.findAll();
        assertThat(sampleTypeList).hasSize(databaseSizeBeforeUpdate);
        SampleType testSampleType = sampleTypeList.get(sampleTypeList.size() - 1);
        assertThat(testSampleType.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testSampleType.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void fullUpdateSampleTypeWithPatch() throws Exception {
        // Initialize the database
        sampleTypeRepository.saveAndFlush(sampleType);

        int databaseSizeBeforeUpdate = sampleTypeRepository.findAll().size();

        // Update the sampleType using partial update
        SampleType partialUpdatedSampleType = new SampleType();
        partialUpdatedSampleType.setId(sampleType.getId());

        partialUpdatedSampleType.name(UPDATED_NAME).description(UPDATED_DESCRIPTION);

        restSampleTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSampleType.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSampleType))
            )
            .andExpect(status().isOk());

        // Validate the SampleType in the database
        List<SampleType> sampleTypeList = sampleTypeRepository.findAll();
        assertThat(sampleTypeList).hasSize(databaseSizeBeforeUpdate);
        SampleType testSampleType = sampleTypeList.get(sampleTypeList.size() - 1);
        assertThat(testSampleType.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testSampleType.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void patchNonExistingSampleType() throws Exception {
        int databaseSizeBeforeUpdate = sampleTypeRepository.findAll().size();
        sampleType.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSampleTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, sampleType.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(sampleType))
            )
            .andExpect(status().isBadRequest());

        // Validate the SampleType in the database
        List<SampleType> sampleTypeList = sampleTypeRepository.findAll();
        assertThat(sampleTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchSampleType() throws Exception {
        int databaseSizeBeforeUpdate = sampleTypeRepository.findAll().size();
        sampleType.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSampleTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(sampleType))
            )
            .andExpect(status().isBadRequest());

        // Validate the SampleType in the database
        List<SampleType> sampleTypeList = sampleTypeRepository.findAll();
        assertThat(sampleTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamSampleType() throws Exception {
        int databaseSizeBeforeUpdate = sampleTypeRepository.findAll().size();
        sampleType.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSampleTypeMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(sampleType))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the SampleType in the database
        List<SampleType> sampleTypeList = sampleTypeRepository.findAll();
        assertThat(sampleTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteSampleType() throws Exception {
        // Initialize the database
        sampleTypeRepository.saveAndFlush(sampleType);

        int databaseSizeBeforeDelete = sampleTypeRepository.findAll().size();

        // Delete the sampleType
        restSampleTypeMockMvc
            .perform(delete(ENTITY_API_URL_ID, sampleType.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<SampleType> sampleTypeList = sampleTypeRepository.findAll();
        assertThat(sampleTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
