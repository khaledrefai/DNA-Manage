package ae.gov.dubaipolice.dna.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import ae.gov.dubaipolice.dna.IntegrationTest;
import ae.gov.dubaipolice.dna.domain.WorkPlace;
import ae.gov.dubaipolice.dna.repository.WorkPlaceRepository;
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
 * Integration tests for the {@link WorkPlaceResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class WorkPlaceResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/work-places";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private WorkPlaceRepository workPlaceRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restWorkPlaceMockMvc;

    private WorkPlace workPlace;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static WorkPlace createEntity(EntityManager em) {
        WorkPlace workPlace = new WorkPlace().name(DEFAULT_NAME).description(DEFAULT_DESCRIPTION);
        return workPlace;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static WorkPlace createUpdatedEntity(EntityManager em) {
        WorkPlace workPlace = new WorkPlace().name(UPDATED_NAME).description(UPDATED_DESCRIPTION);
        return workPlace;
    }

    @BeforeEach
    public void initTest() {
        workPlace = createEntity(em);
    }

    @Test
    @Transactional
    void createWorkPlace() throws Exception {
        int databaseSizeBeforeCreate = workPlaceRepository.findAll().size();
        // Create the WorkPlace
        restWorkPlaceMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(workPlace))
            )
            .andExpect(status().isCreated());

        // Validate the WorkPlace in the database
        List<WorkPlace> workPlaceList = workPlaceRepository.findAll();
        assertThat(workPlaceList).hasSize(databaseSizeBeforeCreate + 1);
        WorkPlace testWorkPlace = workPlaceList.get(workPlaceList.size() - 1);
        assertThat(testWorkPlace.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testWorkPlace.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    void createWorkPlaceWithExistingId() throws Exception {
        // Create the WorkPlace with an existing ID
        workPlace.setId(1L);

        int databaseSizeBeforeCreate = workPlaceRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restWorkPlaceMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(workPlace))
            )
            .andExpect(status().isBadRequest());

        // Validate the WorkPlace in the database
        List<WorkPlace> workPlaceList = workPlaceRepository.findAll();
        assertThat(workPlaceList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllWorkPlaces() throws Exception {
        // Initialize the database
        workPlaceRepository.saveAndFlush(workPlace);

        // Get all the workPlaceList
        restWorkPlaceMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(workPlace.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));
    }

    @Test
    @Transactional
    void getWorkPlace() throws Exception {
        // Initialize the database
        workPlaceRepository.saveAndFlush(workPlace);

        // Get the workPlace
        restWorkPlaceMockMvc
            .perform(get(ENTITY_API_URL_ID, workPlace.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(workPlace.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION));
    }

    @Test
    @Transactional
    void getNonExistingWorkPlace() throws Exception {
        // Get the workPlace
        restWorkPlaceMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewWorkPlace() throws Exception {
        // Initialize the database
        workPlaceRepository.saveAndFlush(workPlace);

        int databaseSizeBeforeUpdate = workPlaceRepository.findAll().size();

        // Update the workPlace
        WorkPlace updatedWorkPlace = workPlaceRepository.findById(workPlace.getId()).get();
        // Disconnect from session so that the updates on updatedWorkPlace are not directly saved in db
        em.detach(updatedWorkPlace);
        updatedWorkPlace.name(UPDATED_NAME).description(UPDATED_DESCRIPTION);

        restWorkPlaceMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedWorkPlace.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedWorkPlace))
            )
            .andExpect(status().isOk());

        // Validate the WorkPlace in the database
        List<WorkPlace> workPlaceList = workPlaceRepository.findAll();
        assertThat(workPlaceList).hasSize(databaseSizeBeforeUpdate);
        WorkPlace testWorkPlace = workPlaceList.get(workPlaceList.size() - 1);
        assertThat(testWorkPlace.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testWorkPlace.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void putNonExistingWorkPlace() throws Exception {
        int databaseSizeBeforeUpdate = workPlaceRepository.findAll().size();
        workPlace.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restWorkPlaceMockMvc
            .perform(
                put(ENTITY_API_URL_ID, workPlace.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(workPlace))
            )
            .andExpect(status().isBadRequest());

        // Validate the WorkPlace in the database
        List<WorkPlace> workPlaceList = workPlaceRepository.findAll();
        assertThat(workPlaceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchWorkPlace() throws Exception {
        int databaseSizeBeforeUpdate = workPlaceRepository.findAll().size();
        workPlace.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restWorkPlaceMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(workPlace))
            )
            .andExpect(status().isBadRequest());

        // Validate the WorkPlace in the database
        List<WorkPlace> workPlaceList = workPlaceRepository.findAll();
        assertThat(workPlaceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamWorkPlace() throws Exception {
        int databaseSizeBeforeUpdate = workPlaceRepository.findAll().size();
        workPlace.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restWorkPlaceMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(workPlace))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the WorkPlace in the database
        List<WorkPlace> workPlaceList = workPlaceRepository.findAll();
        assertThat(workPlaceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateWorkPlaceWithPatch() throws Exception {
        // Initialize the database
        workPlaceRepository.saveAndFlush(workPlace);

        int databaseSizeBeforeUpdate = workPlaceRepository.findAll().size();

        // Update the workPlace using partial update
        WorkPlace partialUpdatedWorkPlace = new WorkPlace();
        partialUpdatedWorkPlace.setId(workPlace.getId());

        partialUpdatedWorkPlace.description(UPDATED_DESCRIPTION);

        restWorkPlaceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedWorkPlace.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedWorkPlace))
            )
            .andExpect(status().isOk());

        // Validate the WorkPlace in the database
        List<WorkPlace> workPlaceList = workPlaceRepository.findAll();
        assertThat(workPlaceList).hasSize(databaseSizeBeforeUpdate);
        WorkPlace testWorkPlace = workPlaceList.get(workPlaceList.size() - 1);
        assertThat(testWorkPlace.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testWorkPlace.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void fullUpdateWorkPlaceWithPatch() throws Exception {
        // Initialize the database
        workPlaceRepository.saveAndFlush(workPlace);

        int databaseSizeBeforeUpdate = workPlaceRepository.findAll().size();

        // Update the workPlace using partial update
        WorkPlace partialUpdatedWorkPlace = new WorkPlace();
        partialUpdatedWorkPlace.setId(workPlace.getId());

        partialUpdatedWorkPlace.name(UPDATED_NAME).description(UPDATED_DESCRIPTION);

        restWorkPlaceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedWorkPlace.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedWorkPlace))
            )
            .andExpect(status().isOk());

        // Validate the WorkPlace in the database
        List<WorkPlace> workPlaceList = workPlaceRepository.findAll();
        assertThat(workPlaceList).hasSize(databaseSizeBeforeUpdate);
        WorkPlace testWorkPlace = workPlaceList.get(workPlaceList.size() - 1);
        assertThat(testWorkPlace.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testWorkPlace.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void patchNonExistingWorkPlace() throws Exception {
        int databaseSizeBeforeUpdate = workPlaceRepository.findAll().size();
        workPlace.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restWorkPlaceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, workPlace.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(workPlace))
            )
            .andExpect(status().isBadRequest());

        // Validate the WorkPlace in the database
        List<WorkPlace> workPlaceList = workPlaceRepository.findAll();
        assertThat(workPlaceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchWorkPlace() throws Exception {
        int databaseSizeBeforeUpdate = workPlaceRepository.findAll().size();
        workPlace.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restWorkPlaceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(workPlace))
            )
            .andExpect(status().isBadRequest());

        // Validate the WorkPlace in the database
        List<WorkPlace> workPlaceList = workPlaceRepository.findAll();
        assertThat(workPlaceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamWorkPlace() throws Exception {
        int databaseSizeBeforeUpdate = workPlaceRepository.findAll().size();
        workPlace.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restWorkPlaceMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(workPlace))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the WorkPlace in the database
        List<WorkPlace> workPlaceList = workPlaceRepository.findAll();
        assertThat(workPlaceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteWorkPlace() throws Exception {
        // Initialize the database
        workPlaceRepository.saveAndFlush(workPlace);

        int databaseSizeBeforeDelete = workPlaceRepository.findAll().size();

        // Delete the workPlace
        restWorkPlaceMockMvc
            .perform(delete(ENTITY_API_URL_ID, workPlace.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<WorkPlace> workPlaceList = workPlaceRepository.findAll();
        assertThat(workPlaceList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
