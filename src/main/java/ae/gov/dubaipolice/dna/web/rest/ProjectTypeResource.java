package ae.gov.dubaipolice.dna.web.rest;

import ae.gov.dubaipolice.dna.domain.ProjectType;
import ae.gov.dubaipolice.dna.repository.ProjectTypeRepository;
import ae.gov.dubaipolice.dna.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link ae.gov.dubaipolice.dna.domain.ProjectType}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class ProjectTypeResource {

    private final Logger log = LoggerFactory.getLogger(ProjectTypeResource.class);

    private static final String ENTITY_NAME = "projectType";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProjectTypeRepository projectTypeRepository;

    public ProjectTypeResource(ProjectTypeRepository projectTypeRepository) {
        this.projectTypeRepository = projectTypeRepository;
    }

    /**
     * {@code POST  /project-types} : Create a new projectType.
     *
     * @param projectType the projectType to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new projectType, or with status {@code 400 (Bad Request)} if the projectType has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/project-types")
    public ResponseEntity<ProjectType> createProjectType(@RequestBody ProjectType projectType) throws URISyntaxException {
        log.debug("REST request to save ProjectType : {}", projectType);
        if (projectType.getId() != null) {
            throw new BadRequestAlertException("A new projectType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProjectType result = projectTypeRepository.save(projectType);
        return ResponseEntity
            .created(new URI("/api/project-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /project-types/:id} : Updates an existing projectType.
     *
     * @param id the id of the projectType to save.
     * @param projectType the projectType to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated projectType,
     * or with status {@code 400 (Bad Request)} if the projectType is not valid,
     * or with status {@code 500 (Internal Server Error)} if the projectType couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/project-types/{id}")
    public ResponseEntity<ProjectType> updateProjectType(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ProjectType projectType
    ) throws URISyntaxException {
        log.debug("REST request to update ProjectType : {}, {}", id, projectType);
        if (projectType.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, projectType.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!projectTypeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ProjectType result = projectTypeRepository.save(projectType);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, projectType.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /project-types/:id} : Partial updates given fields of an existing projectType, field will ignore if it is null
     *
     * @param id the id of the projectType to save.
     * @param projectType the projectType to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated projectType,
     * or with status {@code 400 (Bad Request)} if the projectType is not valid,
     * or with status {@code 404 (Not Found)} if the projectType is not found,
     * or with status {@code 500 (Internal Server Error)} if the projectType couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/project-types/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ProjectType> partialUpdateProjectType(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ProjectType projectType
    ) throws URISyntaxException {
        log.debug("REST request to partial update ProjectType partially : {}, {}", id, projectType);
        if (projectType.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, projectType.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!projectTypeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ProjectType> result = projectTypeRepository
            .findById(projectType.getId())
            .map(existingProjectType -> {
                if (projectType.getName() != null) {
                    existingProjectType.setName(projectType.getName());
                }
                if (projectType.getDescription() != null) {
                    existingProjectType.setDescription(projectType.getDescription());
                }

                return existingProjectType;
            })
            .map(projectTypeRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, projectType.getId().toString())
        );
    }

    /**
     * {@code GET  /project-types} : get all the projectTypes.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of projectTypes in body.
     */
    @GetMapping("/project-types")
    public List<ProjectType> getAllProjectTypes() {
        log.debug("REST request to get all ProjectTypes");
        return projectTypeRepository.findAll();
    }

    /**
     * {@code GET  /project-types/:id} : get the "id" projectType.
     *
     * @param id the id of the projectType to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the projectType, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/project-types/{id}")
    public ResponseEntity<ProjectType> getProjectType(@PathVariable Long id) {
        log.debug("REST request to get ProjectType : {}", id);
        Optional<ProjectType> projectType = projectTypeRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(projectType);
    }

    /**
     * {@code DELETE  /project-types/:id} : delete the "id" projectType.
     *
     * @param id the id of the projectType to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/project-types/{id}")
    public ResponseEntity<Void> deleteProjectType(@PathVariable Long id) {
        log.debug("REST request to delete ProjectType : {}", id);
        projectTypeRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
