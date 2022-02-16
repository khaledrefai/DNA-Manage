package ae.gov.dubaipolice.dna.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import ae.gov.dubaipolice.dna.IntegrationTest;
import ae.gov.dubaipolice.dna.domain.SampleStatus;
import ae.gov.dubaipolice.dna.repository.SampleStatusRepository;
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
 * Integration tests for the {@link SampleStatusResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class SampleStatusResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/sample-statuses";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private SampleStatusRepository sampleStatusRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSampleStatusMockMvc;

    private SampleStatus sampleStatus;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SampleStatus createEntity(EntityManager em) {
        SampleStatus sampleStatus = new SampleStatus().name(DEFAULT_NAME).description(DEFAULT_DESCRIPTION);
        return sampleStatus;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SampleStatus createUpdatedEntity(EntityManager em) {
        SampleStatus sampleStatus = new SampleStatus().name(UPDATED_NAME).description(UPDATED_DESCRIPTION);
        return sampleStatus;
    }

    @BeforeEach
    public void initTest() {
        sampleStatus = createEntity(em);
    }

    @Test
    @Transactional
    void createSampleStatus() throws Exception {
        int databaseSizeBeforeCreate = sampleStatusRepository.findAll().size();
        // Create the SampleStatus
        restSampleStatusMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(sampleStatus))
            )
            .andExpect(status().isCreated());

        // Validate the SampleStatus in the database
        List<SampleStatus> sampleStatusList = sampleStatusRepository.findAll();
        assertThat(sampleStatusList).hasSize(databaseSizeBeforeCreate + 1);
        SampleStatus testSampleStatus = sampleStatusList.get(sampleStatusList.size() - 1);
        assertThat(testSampleStatus.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testSampleStatus.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    void createSampleStatusWithExistingId() throws Exception {
        // Create the SampleStatus with an existing ID
        sampleStatus.setId(1L);

        int databaseSizeBeforeCreate = sampleStatusRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restSampleStatusMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(sampleStatus))
            )
            .andExpect(status().isBadRequest());

        // Validate the SampleStatus in the database
        List<SampleStatus> sampleStatusList = sampleStatusRepository.findAll();
        assertThat(sampleStatusList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllSampleStatuses() throws Exception {
        // Initialize the database
        sampleStatusRepository.saveAndFlush(sampleStatus);

        // Get all the sampleStatusList
        restSampleStatusMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(sampleStatus.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));
    }

    @Test
    @Transactional
    void getSampleStatus() throws Exception {
        // Initialize the database
        sampleStatusRepository.saveAndFlush(sampleStatus);

        // Get the sampleStatus
        restSampleStatusMockMvc
            .perform(get(ENTITY_API_URL_ID, sampleStatus.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(sampleStatus.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION));
    }

    @Test
    @Transactional
    void getNonExistingSampleStatus() throws Exception {
        // Get the sampleStatus
        restSampleStatusMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewSampleStatus() throws Exception {
        // Initialize the database
        sampleStatusRepository.saveAndFlush(sampleStatus);

        int databaseSizeBeforeUpdate = sampleStatusRepository.findAll().size();

        // Update the sampleStatus
        SampleStatus updatedSampleStatus = sampleStatusRepository.findById(sampleStatus.getId()).get();
        // Disconnect from session so that the updates on updatedSampleStatus are not directly saved in db
        em.detach(updatedSampleStatus);
        updatedSampleStatus.name(UPDATED_NAME).description(UPDATED_DESCRIPTION);

        restSampleStatusMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedSampleStatus.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedSampleStatus))
            )
            .andExpect(status().isOk());

        // Validate the SampleStatus in the database
        List<SampleStatus> sampleStatusList = sampleStatusRepository.findAll();
        assertThat(sampleStatusList).hasSize(databaseSizeBeforeUpdate);
        SampleStatus testSampleStatus = sampleStatusList.get(sampleStatusList.size() - 1);
        assertThat(testSampleStatus.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testSampleStatus.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void putNonExistingSampleStatus() throws Exception {
        int databaseSizeBeforeUpdate = sampleStatusRepository.findAll().size();
        sampleStatus.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSampleStatusMockMvc
            .perform(
                put(ENTITY_API_URL_ID, sampleStatus.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(sampleStatus))
            )
            .andExpect(status().isBadRequest());

        // Validate the SampleStatus in the database
        List<SampleStatus> sampleStatusList = sampleStatusRepository.findAll();
        assertThat(sampleStatusList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchSampleStatus() throws Exception {
        int databaseSizeBeforeUpdate = sampleStatusRepository.findAll().size();
        sampleStatus.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSampleStatusMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(sampleStatus))
            )
            .andExpect(status().isBadRequest());

        // Validate the SampleStatus in the database
        List<SampleStatus> sampleStatusList = sampleStatusRepository.findAll();
        assertThat(sampleStatusList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamSampleStatus() throws Exception {
        int databaseSizeBeforeUpdate = sampleStatusRepository.findAll().size();
        sampleStatus.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSampleStatusMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(sampleStatus))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the SampleStatus in the database
        List<SampleStatus> sampleStatusList = sampleStatusRepository.findAll();
        assertThat(sampleStatusList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateSampleStatusWithPatch() throws Exception {
        // Initialize the database
        sampleStatusRepository.saveAndFlush(sampleStatus);

        int databaseSizeBeforeUpdate = sampleStatusRepository.findAll().size();

        // Update the sampleStatus using partial update
        SampleStatus partialUpdatedSampleStatus = new SampleStatus();
        partialUpdatedSampleStatus.setId(sampleStatus.getId());

        partialUpdatedSampleStatus.description(UPDATED_DESCRIPTION);

        restSampleStatusMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSampleStatus.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSampleStatus))
            )
            .andExpect(status().isOk());

        // Validate the SampleStatus in the database
        List<SampleStatus> sampleStatusList = sampleStatusRepository.findAll();
        assertThat(sampleStatusList).hasSize(databaseSizeBeforeUpdate);
        SampleStatus testSampleStatus = sampleStatusList.get(sampleStatusList.size() - 1);
        assertThat(testSampleStatus.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testSampleStatus.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void fullUpdateSampleStatusWithPatch() throws Exception {
        // Initialize the database
        sampleStatusRepository.saveAndFlush(sampleStatus);

        int databaseSizeBeforeUpdate = sampleStatusRepository.findAll().size();

        // Update the sampleStatus using partial update
        SampleStatus partialUpdatedSampleStatus = new SampleStatus();
        partialUpdatedSampleStatus.setId(sampleStatus.getId());

        partialUpdatedSampleStatus.name(UPDATED_NAME).description(UPDATED_DESCRIPTION);

        restSampleStatusMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSampleStatus.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSampleStatus))
            )
            .andExpect(status().isOk());

        // Validate the SampleStatus in the database
        List<SampleStatus> sampleStatusList = sampleStatusRepository.findAll();
        assertThat(sampleStatusList).hasSize(databaseSizeBeforeUpdate);
        SampleStatus testSampleStatus = sampleStatusList.get(sampleStatusList.size() - 1);
        assertThat(testSampleStatus.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testSampleStatus.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void patchNonExistingSampleStatus() throws Exception {
        int databaseSizeBeforeUpdate = sampleStatusRepository.findAll().size();
        sampleStatus.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSampleStatusMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, sampleStatus.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(sampleStatus))
            )
            .andExpect(status().isBadRequest());

        // Validate the SampleStatus in the database
        List<SampleStatus> sampleStatusList = sampleStatusRepository.findAll();
        assertThat(sampleStatusList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchSampleStatus() throws Exception {
        int databaseSizeBeforeUpdate = sampleStatusRepository.findAll().size();
        sampleStatus.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSampleStatusMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(sampleStatus))
            )
            .andExpect(status().isBadRequest());

        // Validate the SampleStatus in the database
        List<SampleStatus> sampleStatusList = sampleStatusRepository.findAll();
        assertThat(sampleStatusList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamSampleStatus() throws Exception {
        int databaseSizeBeforeUpdate = sampleStatusRepository.findAll().size();
        sampleStatus.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSampleStatusMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(sampleStatus))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the SampleStatus in the database
        List<SampleStatus> sampleStatusList = sampleStatusRepository.findAll();
        assertThat(sampleStatusList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteSampleStatus() throws Exception {
        // Initialize the database
        sampleStatusRepository.saveAndFlush(sampleStatus);

        int databaseSizeBeforeDelete = sampleStatusRepository.findAll().size();

        // Delete the sampleStatus
        restSampleStatusMockMvc
            .perform(delete(ENTITY_API_URL_ID, sampleStatus.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<SampleStatus> sampleStatusList = sampleStatusRepository.findAll();
        assertThat(sampleStatusList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
