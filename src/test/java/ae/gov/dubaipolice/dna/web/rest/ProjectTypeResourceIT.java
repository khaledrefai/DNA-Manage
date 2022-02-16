package ae.gov.dubaipolice.dna.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import ae.gov.dubaipolice.dna.IntegrationTest;
import ae.gov.dubaipolice.dna.domain.ProjectType;
import ae.gov.dubaipolice.dna.repository.ProjectTypeRepository;
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
 * Integration tests for the {@link ProjectTypeResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ProjectTypeResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/project-types";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ProjectTypeRepository projectTypeRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restProjectTypeMockMvc;

    private ProjectType projectType;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProjectType createEntity(EntityManager em) {
        ProjectType projectType = new ProjectType().name(DEFAULT_NAME).description(DEFAULT_DESCRIPTION);
        return projectType;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProjectType createUpdatedEntity(EntityManager em) {
        ProjectType projectType = new ProjectType().name(UPDATED_NAME).description(UPDATED_DESCRIPTION);
        return projectType;
    }

    @BeforeEach
    public void initTest() {
        projectType = createEntity(em);
    }

    @Test
    @Transactional
    void createProjectType() throws Exception {
        int databaseSizeBeforeCreate = projectTypeRepository.findAll().size();
        // Create the ProjectType
        restProjectTypeMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(projectType))
            )
            .andExpect(status().isCreated());

        // Validate the ProjectType in the database
        List<ProjectType> projectTypeList = projectTypeRepository.findAll();
        assertThat(projectTypeList).hasSize(databaseSizeBeforeCreate + 1);
        ProjectType testProjectType = projectTypeList.get(projectTypeList.size() - 1);
        assertThat(testProjectType.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testProjectType.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    void createProjectTypeWithExistingId() throws Exception {
        // Create the ProjectType with an existing ID
        projectType.setId(1L);

        int databaseSizeBeforeCreate = projectTypeRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restProjectTypeMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(projectType))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProjectType in the database
        List<ProjectType> projectTypeList = projectTypeRepository.findAll();
        assertThat(projectTypeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllProjectTypes() throws Exception {
        // Initialize the database
        projectTypeRepository.saveAndFlush(projectType);

        // Get all the projectTypeList
        restProjectTypeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(projectType.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));
    }

    @Test
    @Transactional
    void getProjectType() throws Exception {
        // Initialize the database
        projectTypeRepository.saveAndFlush(projectType);

        // Get the projectType
        restProjectTypeMockMvc
            .perform(get(ENTITY_API_URL_ID, projectType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(projectType.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION));
    }

    @Test
    @Transactional
    void getNonExistingProjectType() throws Exception {
        // Get the projectType
        restProjectTypeMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewProjectType() throws Exception {
        // Initialize the database
        projectTypeRepository.saveAndFlush(projectType);

        int databaseSizeBeforeUpdate = projectTypeRepository.findAll().size();

        // Update the projectType
        ProjectType updatedProjectType = projectTypeRepository.findById(projectType.getId()).get();
        // Disconnect from session so that the updates on updatedProjectType are not directly saved in db
        em.detach(updatedProjectType);
        updatedProjectType.name(UPDATED_NAME).description(UPDATED_DESCRIPTION);

        restProjectTypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedProjectType.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedProjectType))
            )
            .andExpect(status().isOk());

        // Validate the ProjectType in the database
        List<ProjectType> projectTypeList = projectTypeRepository.findAll();
        assertThat(projectTypeList).hasSize(databaseSizeBeforeUpdate);
        ProjectType testProjectType = projectTypeList.get(projectTypeList.size() - 1);
        assertThat(testProjectType.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testProjectType.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void putNonExistingProjectType() throws Exception {
        int databaseSizeBeforeUpdate = projectTypeRepository.findAll().size();
        projectType.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProjectTypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, projectType.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(projectType))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProjectType in the database
        List<ProjectType> projectTypeList = projectTypeRepository.findAll();
        assertThat(projectTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchProjectType() throws Exception {
        int databaseSizeBeforeUpdate = projectTypeRepository.findAll().size();
        projectType.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProjectTypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(projectType))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProjectType in the database
        List<ProjectType> projectTypeList = projectTypeRepository.findAll();
        assertThat(projectTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamProjectType() throws Exception {
        int databaseSizeBeforeUpdate = projectTypeRepository.findAll().size();
        projectType.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProjectTypeMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(projectType))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ProjectType in the database
        List<ProjectType> projectTypeList = projectTypeRepository.findAll();
        assertThat(projectTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateProjectTypeWithPatch() throws Exception {
        // Initialize the database
        projectTypeRepository.saveAndFlush(projectType);

        int databaseSizeBeforeUpdate = projectTypeRepository.findAll().size();

        // Update the projectType using partial update
        ProjectType partialUpdatedProjectType = new ProjectType();
        partialUpdatedProjectType.setId(projectType.getId());

        restProjectTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProjectType.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedProjectType))
            )
            .andExpect(status().isOk());

        // Validate the ProjectType in the database
        List<ProjectType> projectTypeList = projectTypeRepository.findAll();
        assertThat(projectTypeList).hasSize(databaseSizeBeforeUpdate);
        ProjectType testProjectType = projectTypeList.get(projectTypeList.size() - 1);
        assertThat(testProjectType.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testProjectType.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    void fullUpdateProjectTypeWithPatch() throws Exception {
        // Initialize the database
        projectTypeRepository.saveAndFlush(projectType);

        int databaseSizeBeforeUpdate = projectTypeRepository.findAll().size();

        // Update the projectType using partial update
        ProjectType partialUpdatedProjectType = new ProjectType();
        partialUpdatedProjectType.setId(projectType.getId());

        partialUpdatedProjectType.name(UPDATED_NAME).description(UPDATED_DESCRIPTION);

        restProjectTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProjectType.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedProjectType))
            )
            .andExpect(status().isOk());

        // Validate the ProjectType in the database
        List<ProjectType> projectTypeList = projectTypeRepository.findAll();
        assertThat(projectTypeList).hasSize(databaseSizeBeforeUpdate);
        ProjectType testProjectType = projectTypeList.get(projectTypeList.size() - 1);
        assertThat(testProjectType.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testProjectType.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void patchNonExistingProjectType() throws Exception {
        int databaseSizeBeforeUpdate = projectTypeRepository.findAll().size();
        projectType.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProjectTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, projectType.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(projectType))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProjectType in the database
        List<ProjectType> projectTypeList = projectTypeRepository.findAll();
        assertThat(projectTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchProjectType() throws Exception {
        int databaseSizeBeforeUpdate = projectTypeRepository.findAll().size();
        projectType.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProjectTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(projectType))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProjectType in the database
        List<ProjectType> projectTypeList = projectTypeRepository.findAll();
        assertThat(projectTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamProjectType() throws Exception {
        int databaseSizeBeforeUpdate = projectTypeRepository.findAll().size();
        projectType.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProjectTypeMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(projectType))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ProjectType in the database
        List<ProjectType> projectTypeList = projectTypeRepository.findAll();
        assertThat(projectTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteProjectType() throws Exception {
        // Initialize the database
        projectTypeRepository.saveAndFlush(projectType);

        int databaseSizeBeforeDelete = projectTypeRepository.findAll().size();

        // Delete the projectType
        restProjectTypeMockMvc
            .perform(delete(ENTITY_API_URL_ID, projectType.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ProjectType> projectTypeList = projectTypeRepository.findAll();
        assertThat(projectTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
