package ae.gov.dubaipolice.dna.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import ae.gov.dubaipolice.dna.IntegrationTest;
import ae.gov.dubaipolice.dna.domain.InBodyResultsSheet;
import ae.gov.dubaipolice.dna.repository.InBodyResultsSheetRepository;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
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
 * Integration tests for the {@link InBodyResultsSheetResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class InBodyResultsSheetResourceIT {

    private static final Integer DEFAULT_USER_ID = 1;
    private static final Integer UPDATED_USER_ID = 2;

    private static final Instant DEFAULT_DATETIMES = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATETIMES = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_ORDER_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_ORDER_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_INBODY_IMAGE = "AAAAAAAAAA";
    private static final String UPDATED_INBODY_IMAGE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/in-body-results-sheets";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private InBodyResultsSheetRepository inBodyResultsSheetRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restInBodyResultsSheetMockMvc;

    private InBodyResultsSheet inBodyResultsSheet;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static InBodyResultsSheet createEntity(EntityManager em) {
        InBodyResultsSheet inBodyResultsSheet = new InBodyResultsSheet()
            .userId(DEFAULT_USER_ID)
            .datetimes(DEFAULT_DATETIMES)
            .orderDate(DEFAULT_ORDER_DATE)
            .inbodyImage(DEFAULT_INBODY_IMAGE);
        return inBodyResultsSheet;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static InBodyResultsSheet createUpdatedEntity(EntityManager em) {
        InBodyResultsSheet inBodyResultsSheet = new InBodyResultsSheet()
            .userId(UPDATED_USER_ID)
            .datetimes(UPDATED_DATETIMES)
            .orderDate(UPDATED_ORDER_DATE)
            .inbodyImage(UPDATED_INBODY_IMAGE);
        return inBodyResultsSheet;
    }

    @BeforeEach
    public void initTest() {
        inBodyResultsSheet = createEntity(em);
    }

    @Test
    @Transactional
    void createInBodyResultsSheet() throws Exception {
        int databaseSizeBeforeCreate = inBodyResultsSheetRepository.findAll().size();
        // Create the InBodyResultsSheet
        restInBodyResultsSheetMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(inBodyResultsSheet))
            )
            .andExpect(status().isCreated());

        // Validate the InBodyResultsSheet in the database
        List<InBodyResultsSheet> inBodyResultsSheetList = inBodyResultsSheetRepository.findAll();
        assertThat(inBodyResultsSheetList).hasSize(databaseSizeBeforeCreate + 1);
        InBodyResultsSheet testInBodyResultsSheet = inBodyResultsSheetList.get(inBodyResultsSheetList.size() - 1);
        assertThat(testInBodyResultsSheet.getUserId()).isEqualTo(DEFAULT_USER_ID);
        assertThat(testInBodyResultsSheet.getDatetimes()).isEqualTo(DEFAULT_DATETIMES);
        assertThat(testInBodyResultsSheet.getOrderDate()).isEqualTo(DEFAULT_ORDER_DATE);
        assertThat(testInBodyResultsSheet.getInbodyImage()).isEqualTo(DEFAULT_INBODY_IMAGE);
    }

    @Test
    @Transactional
    void createInBodyResultsSheetWithExistingId() throws Exception {
        // Create the InBodyResultsSheet with an existing ID
        inBodyResultsSheet.setId(1L);

        int databaseSizeBeforeCreate = inBodyResultsSheetRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restInBodyResultsSheetMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(inBodyResultsSheet))
            )
            .andExpect(status().isBadRequest());

        // Validate the InBodyResultsSheet in the database
        List<InBodyResultsSheet> inBodyResultsSheetList = inBodyResultsSheetRepository.findAll();
        assertThat(inBodyResultsSheetList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllInBodyResultsSheets() throws Exception {
        // Initialize the database
        inBodyResultsSheetRepository.saveAndFlush(inBodyResultsSheet);

        // Get all the inBodyResultsSheetList
        restInBodyResultsSheetMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(inBodyResultsSheet.getId().intValue())))
            .andExpect(jsonPath("$.[*].userId").value(hasItem(DEFAULT_USER_ID)))
            .andExpect(jsonPath("$.[*].datetimes").value(hasItem(DEFAULT_DATETIMES.toString())))
            .andExpect(jsonPath("$.[*].orderDate").value(hasItem(DEFAULT_ORDER_DATE.toString())))
            .andExpect(jsonPath("$.[*].inbodyImage").value(hasItem(DEFAULT_INBODY_IMAGE)));
    }

    @Test
    @Transactional
    void getInBodyResultsSheet() throws Exception {
        // Initialize the database
        inBodyResultsSheetRepository.saveAndFlush(inBodyResultsSheet);

        // Get the inBodyResultsSheet
        restInBodyResultsSheetMockMvc
            .perform(get(ENTITY_API_URL_ID, inBodyResultsSheet.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(inBodyResultsSheet.getId().intValue()))
            .andExpect(jsonPath("$.userId").value(DEFAULT_USER_ID))
            .andExpect(jsonPath("$.datetimes").value(DEFAULT_DATETIMES.toString()))
            .andExpect(jsonPath("$.orderDate").value(DEFAULT_ORDER_DATE.toString()))
            .andExpect(jsonPath("$.inbodyImage").value(DEFAULT_INBODY_IMAGE));
    }

    @Test
    @Transactional
    void getNonExistingInBodyResultsSheet() throws Exception {
        // Get the inBodyResultsSheet
        restInBodyResultsSheetMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewInBodyResultsSheet() throws Exception {
        // Initialize the database
        inBodyResultsSheetRepository.saveAndFlush(inBodyResultsSheet);

        int databaseSizeBeforeUpdate = inBodyResultsSheetRepository.findAll().size();

        // Update the inBodyResultsSheet
        InBodyResultsSheet updatedInBodyResultsSheet = inBodyResultsSheetRepository.findById(inBodyResultsSheet.getId()).get();
        // Disconnect from session so that the updates on updatedInBodyResultsSheet are not directly saved in db
        em.detach(updatedInBodyResultsSheet);
        updatedInBodyResultsSheet
            .userId(UPDATED_USER_ID)
            .datetimes(UPDATED_DATETIMES)
            .orderDate(UPDATED_ORDER_DATE)
            .inbodyImage(UPDATED_INBODY_IMAGE);

        restInBodyResultsSheetMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedInBodyResultsSheet.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedInBodyResultsSheet))
            )
            .andExpect(status().isOk());

        // Validate the InBodyResultsSheet in the database
        List<InBodyResultsSheet> inBodyResultsSheetList = inBodyResultsSheetRepository.findAll();
        assertThat(inBodyResultsSheetList).hasSize(databaseSizeBeforeUpdate);
        InBodyResultsSheet testInBodyResultsSheet = inBodyResultsSheetList.get(inBodyResultsSheetList.size() - 1);
        assertThat(testInBodyResultsSheet.getUserId()).isEqualTo(UPDATED_USER_ID);
        assertThat(testInBodyResultsSheet.getDatetimes()).isEqualTo(UPDATED_DATETIMES);
        assertThat(testInBodyResultsSheet.getOrderDate()).isEqualTo(UPDATED_ORDER_DATE);
        assertThat(testInBodyResultsSheet.getInbodyImage()).isEqualTo(UPDATED_INBODY_IMAGE);
    }

    @Test
    @Transactional
    void putNonExistingInBodyResultsSheet() throws Exception {
        int databaseSizeBeforeUpdate = inBodyResultsSheetRepository.findAll().size();
        inBodyResultsSheet.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restInBodyResultsSheetMockMvc
            .perform(
                put(ENTITY_API_URL_ID, inBodyResultsSheet.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(inBodyResultsSheet))
            )
            .andExpect(status().isBadRequest());

        // Validate the InBodyResultsSheet in the database
        List<InBodyResultsSheet> inBodyResultsSheetList = inBodyResultsSheetRepository.findAll();
        assertThat(inBodyResultsSheetList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchInBodyResultsSheet() throws Exception {
        int databaseSizeBeforeUpdate = inBodyResultsSheetRepository.findAll().size();
        inBodyResultsSheet.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restInBodyResultsSheetMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(inBodyResultsSheet))
            )
            .andExpect(status().isBadRequest());

        // Validate the InBodyResultsSheet in the database
        List<InBodyResultsSheet> inBodyResultsSheetList = inBodyResultsSheetRepository.findAll();
        assertThat(inBodyResultsSheetList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamInBodyResultsSheet() throws Exception {
        int databaseSizeBeforeUpdate = inBodyResultsSheetRepository.findAll().size();
        inBodyResultsSheet.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restInBodyResultsSheetMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(inBodyResultsSheet))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the InBodyResultsSheet in the database
        List<InBodyResultsSheet> inBodyResultsSheetList = inBodyResultsSheetRepository.findAll();
        assertThat(inBodyResultsSheetList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateInBodyResultsSheetWithPatch() throws Exception {
        // Initialize the database
        inBodyResultsSheetRepository.saveAndFlush(inBodyResultsSheet);

        int databaseSizeBeforeUpdate = inBodyResultsSheetRepository.findAll().size();

        // Update the inBodyResultsSheet using partial update
        InBodyResultsSheet partialUpdatedInBodyResultsSheet = new InBodyResultsSheet();
        partialUpdatedInBodyResultsSheet.setId(inBodyResultsSheet.getId());

        partialUpdatedInBodyResultsSheet.userId(UPDATED_USER_ID).datetimes(UPDATED_DATETIMES).orderDate(UPDATED_ORDER_DATE);

        restInBodyResultsSheetMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedInBodyResultsSheet.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedInBodyResultsSheet))
            )
            .andExpect(status().isOk());

        // Validate the InBodyResultsSheet in the database
        List<InBodyResultsSheet> inBodyResultsSheetList = inBodyResultsSheetRepository.findAll();
        assertThat(inBodyResultsSheetList).hasSize(databaseSizeBeforeUpdate);
        InBodyResultsSheet testInBodyResultsSheet = inBodyResultsSheetList.get(inBodyResultsSheetList.size() - 1);
        assertThat(testInBodyResultsSheet.getUserId()).isEqualTo(UPDATED_USER_ID);
        assertThat(testInBodyResultsSheet.getDatetimes()).isEqualTo(UPDATED_DATETIMES);
        assertThat(testInBodyResultsSheet.getOrderDate()).isEqualTo(UPDATED_ORDER_DATE);
        assertThat(testInBodyResultsSheet.getInbodyImage()).isEqualTo(DEFAULT_INBODY_IMAGE);
    }

    @Test
    @Transactional
    void fullUpdateInBodyResultsSheetWithPatch() throws Exception {
        // Initialize the database
        inBodyResultsSheetRepository.saveAndFlush(inBodyResultsSheet);

        int databaseSizeBeforeUpdate = inBodyResultsSheetRepository.findAll().size();

        // Update the inBodyResultsSheet using partial update
        InBodyResultsSheet partialUpdatedInBodyResultsSheet = new InBodyResultsSheet();
        partialUpdatedInBodyResultsSheet.setId(inBodyResultsSheet.getId());

        partialUpdatedInBodyResultsSheet
            .userId(UPDATED_USER_ID)
            .datetimes(UPDATED_DATETIMES)
            .orderDate(UPDATED_ORDER_DATE)
            .inbodyImage(UPDATED_INBODY_IMAGE);

        restInBodyResultsSheetMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedInBodyResultsSheet.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedInBodyResultsSheet))
            )
            .andExpect(status().isOk());

        // Validate the InBodyResultsSheet in the database
        List<InBodyResultsSheet> inBodyResultsSheetList = inBodyResultsSheetRepository.findAll();
        assertThat(inBodyResultsSheetList).hasSize(databaseSizeBeforeUpdate);
        InBodyResultsSheet testInBodyResultsSheet = inBodyResultsSheetList.get(inBodyResultsSheetList.size() - 1);
        assertThat(testInBodyResultsSheet.getUserId()).isEqualTo(UPDATED_USER_ID);
        assertThat(testInBodyResultsSheet.getDatetimes()).isEqualTo(UPDATED_DATETIMES);
        assertThat(testInBodyResultsSheet.getOrderDate()).isEqualTo(UPDATED_ORDER_DATE);
        assertThat(testInBodyResultsSheet.getInbodyImage()).isEqualTo(UPDATED_INBODY_IMAGE);
    }

    @Test
    @Transactional
    void patchNonExistingInBodyResultsSheet() throws Exception {
        int databaseSizeBeforeUpdate = inBodyResultsSheetRepository.findAll().size();
        inBodyResultsSheet.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restInBodyResultsSheetMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, inBodyResultsSheet.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(inBodyResultsSheet))
            )
            .andExpect(status().isBadRequest());

        // Validate the InBodyResultsSheet in the database
        List<InBodyResultsSheet> inBodyResultsSheetList = inBodyResultsSheetRepository.findAll();
        assertThat(inBodyResultsSheetList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchInBodyResultsSheet() throws Exception {
        int databaseSizeBeforeUpdate = inBodyResultsSheetRepository.findAll().size();
        inBodyResultsSheet.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restInBodyResultsSheetMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(inBodyResultsSheet))
            )
            .andExpect(status().isBadRequest());

        // Validate the InBodyResultsSheet in the database
        List<InBodyResultsSheet> inBodyResultsSheetList = inBodyResultsSheetRepository.findAll();
        assertThat(inBodyResultsSheetList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamInBodyResultsSheet() throws Exception {
        int databaseSizeBeforeUpdate = inBodyResultsSheetRepository.findAll().size();
        inBodyResultsSheet.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restInBodyResultsSheetMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(inBodyResultsSheet))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the InBodyResultsSheet in the database
        List<InBodyResultsSheet> inBodyResultsSheetList = inBodyResultsSheetRepository.findAll();
        assertThat(inBodyResultsSheetList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteInBodyResultsSheet() throws Exception {
        // Initialize the database
        inBodyResultsSheetRepository.saveAndFlush(inBodyResultsSheet);

        int databaseSizeBeforeDelete = inBodyResultsSheetRepository.findAll().size();

        // Delete the inBodyResultsSheet
        restInBodyResultsSheetMockMvc
            .perform(delete(ENTITY_API_URL_ID, inBodyResultsSheet.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<InBodyResultsSheet> inBodyResultsSheetList = inBodyResultsSheetRepository.findAll();
        assertThat(inBodyResultsSheetList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
