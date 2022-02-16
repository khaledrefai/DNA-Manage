package ae.gov.dubaipolice.dna.web.rest;

import ae.gov.dubaipolice.dna.domain.WorkPlace;
import ae.gov.dubaipolice.dna.repository.WorkPlaceRepository;
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
 * REST controller for managing {@link ae.gov.dubaipolice.dna.domain.WorkPlace}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class WorkPlaceResource {

    private final Logger log = LoggerFactory.getLogger(WorkPlaceResource.class);

    private static final String ENTITY_NAME = "workPlace";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final WorkPlaceRepository workPlaceRepository;

    public WorkPlaceResource(WorkPlaceRepository workPlaceRepository) {
        this.workPlaceRepository = workPlaceRepository;
    }

    /**
     * {@code POST  /work-places} : Create a new workPlace.
     *
     * @param workPlace the workPlace to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new workPlace, or with status {@code 400 (Bad Request)} if the workPlace has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/work-places")
    public ResponseEntity<WorkPlace> createWorkPlace(@RequestBody WorkPlace workPlace) throws URISyntaxException {
        log.debug("REST request to save WorkPlace : {}", workPlace);
        if (workPlace.getId() != null) {
            throw new BadRequestAlertException("A new workPlace cannot already have an ID", ENTITY_NAME, "idexists");
        }
        WorkPlace result = workPlaceRepository.save(workPlace);
        return ResponseEntity
            .created(new URI("/api/work-places/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /work-places/:id} : Updates an existing workPlace.
     *
     * @param id the id of the workPlace to save.
     * @param workPlace the workPlace to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated workPlace,
     * or with status {@code 400 (Bad Request)} if the workPlace is not valid,
     * or with status {@code 500 (Internal Server Error)} if the workPlace couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/work-places/{id}")
    public ResponseEntity<WorkPlace> updateWorkPlace(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody WorkPlace workPlace
    ) throws URISyntaxException {
        log.debug("REST request to update WorkPlace : {}, {}", id, workPlace);
        if (workPlace.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, workPlace.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!workPlaceRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        WorkPlace result = workPlaceRepository.save(workPlace);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, workPlace.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /work-places/:id} : Partial updates given fields of an existing workPlace, field will ignore if it is null
     *
     * @param id the id of the workPlace to save.
     * @param workPlace the workPlace to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated workPlace,
     * or with status {@code 400 (Bad Request)} if the workPlace is not valid,
     * or with status {@code 404 (Not Found)} if the workPlace is not found,
     * or with status {@code 500 (Internal Server Error)} if the workPlace couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/work-places/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<WorkPlace> partialUpdateWorkPlace(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody WorkPlace workPlace
    ) throws URISyntaxException {
        log.debug("REST request to partial update WorkPlace partially : {}, {}", id, workPlace);
        if (workPlace.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, workPlace.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!workPlaceRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<WorkPlace> result = workPlaceRepository
            .findById(workPlace.getId())
            .map(existingWorkPlace -> {
                if (workPlace.getName() != null) {
                    existingWorkPlace.setName(workPlace.getName());
                }
                if (workPlace.getDescription() != null) {
                    existingWorkPlace.setDescription(workPlace.getDescription());
                }

                return existingWorkPlace;
            })
            .map(workPlaceRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, workPlace.getId().toString())
        );
    }

    /**
     * {@code GET  /work-places} : get all the workPlaces.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of workPlaces in body.
     */
    @GetMapping("/work-places")
    public List<WorkPlace> getAllWorkPlaces() {
        log.debug("REST request to get all WorkPlaces");
        return workPlaceRepository.findAll();
    }

    /**
     * {@code GET  /work-places/:id} : get the "id" workPlace.
     *
     * @param id the id of the workPlace to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the workPlace, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/work-places/{id}")
    public ResponseEntity<WorkPlace> getWorkPlace(@PathVariable Long id) {
        log.debug("REST request to get WorkPlace : {}", id);
        Optional<WorkPlace> workPlace = workPlaceRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(workPlace);
    }

    /**
     * {@code DELETE  /work-places/:id} : delete the "id" workPlace.
     *
     * @param id the id of the workPlace to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/work-places/{id}")
    public ResponseEntity<Void> deleteWorkPlace(@PathVariable Long id) {
        log.debug("REST request to delete WorkPlace : {}", id);
        workPlaceRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
