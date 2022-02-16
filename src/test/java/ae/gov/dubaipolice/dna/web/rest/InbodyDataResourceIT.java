package ae.gov.dubaipolice.dna.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import ae.gov.dubaipolice.dna.IntegrationTest;
import ae.gov.dubaipolice.dna.domain.InbodyData;
import ae.gov.dubaipolice.dna.repository.InbodyDataRepository;
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
 * Integration tests for the {@link InbodyDataResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class InbodyDataResourceIT {

    private static final Integer DEFAULT_USER_ID = 1;
    private static final Integer UPDATED_USER_ID = 2;

    private static final String DEFAULT_COLUMN_NAME = "AAAAAAAAAA";
    private static final String UPDATED_COLUMN_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_COLUMN_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_COLUMN_VALUE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/inbody-data";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private InbodyDataRepository inbodyDataRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restInbodyDataMockMvc;

    private InbodyData inbodyData;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static InbodyData createEntity(EntityManager em) {
        InbodyData inbodyData = new InbodyData().userID(DEFAULT_USER_ID).columnName(DEFAULT_COLUMN_NAME).columnValue(DEFAULT_COLUMN_VALUE);
        return inbodyData;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static InbodyData createUpdatedEntity(EntityManager em) {
        InbodyData inbodyData = new InbodyData().userID(UPDATED_USER_ID).columnName(UPDATED_COLUMN_NAME).columnValue(UPDATED_COLUMN_VALUE);
        return inbodyData;
    }

    @BeforeEach
    public void initTest() {
        inbodyData = createEntity(em);
    }

    @Test
    @Transactional
    void createInbodyData() throws Exception {
        int databaseSizeBeforeCreate = inbodyDataRepository.findAll().size();
        // Create the InbodyData
        restInbodyDataMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(inbodyData))
            )
            .andExpect(status().isCreated());

        // Validate the InbodyData in the database
        List<InbodyData> inbodyDataList = inbodyDataRepository.findAll();
        assertThat(inbodyDataList).hasSize(databaseSizeBeforeCreate + 1);
        InbodyData testInbodyData = inbodyDataList.get(inbodyDataList.size() - 1);
        assertThat(testInbodyData.getUserID()).isEqualTo(DEFAULT_USER_ID);
        assertThat(testInbodyData.getColumnName()).isEqualTo(DEFAULT_COLUMN_NAME);
        assertThat(testInbodyData.getColumnValue()).isEqualTo(DEFAULT_COLUMN_VALUE);
    }

    @Test
    @Transactional
    void createInbodyDataWithExistingId() throws Exception {
        // Create the InbodyData with an existing ID
        inbodyData.setId(1L);

        int databaseSizeBeforeCreate = inbodyDataRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restInbodyDataMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(inbodyData))
            )
            .andExpect(status().isBadRequest());

        // Validate the InbodyData in the database
        List<InbodyData> inbodyDataList = inbodyDataRepository.findAll();
        assertThat(inbodyDataList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllInbodyData() throws Exception {
        // Initialize the database
        inbodyDataRepository.saveAndFlush(inbodyData);

        // Get all the inbodyDataList
        restInbodyDataMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(inbodyData.getId().intValue())))
            .andExpect(jsonPath("$.[*].userID").value(hasItem(DEFAULT_USER_ID)))
            .andExpect(jsonPath("$.[*].columnName").value(hasItem(DEFAULT_COLUMN_NAME)))
            .andExpect(jsonPath("$.[*].columnValue").value(hasItem(DEFAULT_COLUMN_VALUE)));
    }

    @Test
    @Transactional
    void getInbodyData() throws Exception {
        // Initialize the database
        inbodyDataRepository.saveAndFlush(inbodyData);

        // Get the inbodyData
        restInbodyDataMockMvc
            .perform(get(ENTITY_API_URL_ID, inbodyData.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(inbodyData.getId().intValue()))
            .andExpect(jsonPath("$.userID").value(DEFAULT_USER_ID))
            .andExpect(jsonPath("$.columnName").value(DEFAULT_COLUMN_NAME))
            .andExpect(jsonPath("$.columnValue").value(DEFAULT_COLUMN_VALUE));
    }

    @Test
    @Transactional
    void getNonExistingInbodyData() throws Exception {
        // Get the inbodyData
        restInbodyDataMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewInbodyData() throws Exception {
        // Initialize the database
        inbodyDataRepository.saveAndFlush(inbodyData);

        int databaseSizeBeforeUpdate = inbodyDataRepository.findAll().size();

        // Update the inbodyData
        InbodyData updatedInbodyData = inbodyDataRepository.findById(inbodyData.getId()).get();
        // Disconnect from session so that the updates on updatedInbodyData are not directly saved in db
        em.detach(updatedInbodyData);
        updatedInbodyData.userID(UPDATED_USER_ID).columnName(UPDATED_COLUMN_NAME).columnValue(UPDATED_COLUMN_VALUE);

        restInbodyDataMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedInbodyData.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedInbodyData))
            )
            .andExpect(status().isOk());

        // Validate the InbodyData in the database
        List<InbodyData> inbodyDataList = inbodyDataRepository.findAll();
        assertThat(inbodyDataList).hasSize(databaseSizeBeforeUpdate);
        InbodyData testInbodyData = inbodyDataList.get(inbodyDataList.size() - 1);
        assertThat(testInbodyData.getUserID()).isEqualTo(UPDATED_USER_ID);
        assertThat(testInbodyData.getColumnName()).isEqualTo(UPDATED_COLUMN_NAME);
        assertThat(testInbodyData.getColumnValue()).isEqualTo(UPDATED_COLUMN_VALUE);
    }

    @Test
    @Transactional
    void putNonExistingInbodyData() throws Exception {
        int databaseSizeBeforeUpdate = inbodyDataRepository.findAll().size();
        inbodyData.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restInbodyDataMockMvc
            .perform(
                put(ENTITY_API_URL_ID, inbodyData.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(inbodyData))
            )
            .andExpect(status().isBadRequest());

        // Validate the InbodyData in the database
        List<InbodyData> inbodyDataList = inbodyDataRepository.findAll();
        assertThat(inbodyDataList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchInbodyData() throws Exception {
        int databaseSizeBeforeUpdate = inbodyDataRepository.findAll().size();
        inbodyData.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restInbodyDataMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(inbodyData))
            )
            .andExpect(status().isBadRequest());

        // Validate the InbodyData in the database
        List<InbodyData> inbodyDataList = inbodyDataRepository.findAll();
        assertThat(inbodyDataList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamInbodyData() throws Exception {
        int databaseSizeBeforeUpdate = inbodyDataRepository.findAll().size();
        inbodyData.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restInbodyDataMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(inbodyData))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the InbodyData in the database
        List<InbodyData> inbodyDataList = inbodyDataRepository.findAll();
        assertThat(inbodyDataList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateInbodyDataWithPatch() throws Exception {
        // Initialize the database
        inbodyDataRepository.saveAndFlush(inbodyData);

        int databaseSizeBeforeUpdate = inbodyDataRepository.findAll().size();

        // Update the inbodyData using partial update
        InbodyData partialUpdatedInbodyData = new InbodyData();
        partialUpdatedInbodyData.setId(inbodyData.getId());

        partialUpdatedInbodyData.userID(UPDATED_USER_ID);

        restInbodyDataMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedInbodyData.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedInbodyData))
            )
            .andExpect(status().isOk());

        // Validate the InbodyData in the database
        List<InbodyData> inbodyDataList = inbodyDataRepository.findAll();
        assertThat(inbodyDataList).hasSize(databaseSizeBeforeUpdate);
        InbodyData testInbodyData = inbodyDataList.get(inbodyDataList.size() - 1);
        assertThat(testInbodyData.getUserID()).isEqualTo(UPDATED_USER_ID);
        assertThat(testInbodyData.getColumnName()).isEqualTo(DEFAULT_COLUMN_NAME);
        assertThat(testInbodyData.getColumnValue()).isEqualTo(DEFAULT_COLUMN_VALUE);
    }

    @Test
    @Transactional
    void fullUpdateInbodyDataWithPatch() throws Exception {
        // Initialize the database
        inbodyDataRepository.saveAndFlush(inbodyData);

        int databaseSizeBeforeUpdate = inbodyDataRepository.findAll().size();

        // Update the inbodyData using partial update
        InbodyData partialUpdatedInbodyData = new InbodyData();
        partialUpdatedInbodyData.setId(inbodyData.getId());

        partialUpdatedInbodyData.userID(UPDATED_USER_ID).columnName(UPDATED_COLUMN_NAME).columnValue(UPDATED_COLUMN_VALUE);

        restInbodyDataMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedInbodyData.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedInbodyData))
            )
            .andExpect(status().isOk());

        // Validate the InbodyData in the database
        List<InbodyData> inbodyDataList = inbodyDataRepository.findAll();
        assertThat(inbodyDataList).hasSize(databaseSizeBeforeUpdate);
        InbodyData testInbodyData = inbodyDataList.get(inbodyDataList.size() - 1);
        assertThat(testInbodyData.getUserID()).isEqualTo(UPDATED_USER_ID);
        assertThat(testInbodyData.getColumnName()).isEqualTo(UPDATED_COLUMN_NAME);
        assertThat(testInbodyData.getColumnValue()).isEqualTo(UPDATED_COLUMN_VALUE);
    }

    @Test
    @Transactional
    void patchNonExistingInbodyData() throws Exception {
        int databaseSizeBeforeUpdate = inbodyDataRepository.findAll().size();
        inbodyData.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restInbodyDataMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, inbodyData.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(inbodyData))
            )
            .andExpect(status().isBadRequest());

        // Validate the InbodyData in the database
        List<InbodyData> inbodyDataList = inbodyDataRepository.findAll();
        assertThat(inbodyDataList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchInbodyData() throws Exception {
        int databaseSizeBeforeUpdate = inbodyDataRepository.findAll().size();
        inbodyData.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restInbodyDataMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(inbodyData))
            )
            .andExpect(status().isBadRequest());

        // Validate the InbodyData in the database
        List<InbodyData> inbodyDataList = inbodyDataRepository.findAll();
        assertThat(inbodyDataList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamInbodyData() throws Exception {
        int databaseSizeBeforeUpdate = inbodyDataRepository.findAll().size();
        inbodyData.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restInbodyDataMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(inbodyData))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the InbodyData in the database
        List<InbodyData> inbodyDataList = inbodyDataRepository.findAll();
        assertThat(inbodyDataList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteInbodyData() throws Exception {
        // Initialize the database
        inbodyDataRepository.saveAndFlush(inbodyData);

        int databaseSizeBeforeDelete = inbodyDataRepository.findAll().size();

        // Delete the inbodyData
        restInbodyDataMockMvc
            .perform(delete(ENTITY_API_URL_ID, inbodyData.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<InbodyData> inbodyDataList = inbodyDataRepository.findAll();
        assertThat(inbodyDataList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
