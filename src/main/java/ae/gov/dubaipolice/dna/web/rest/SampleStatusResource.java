package ae.gov.dubaipolice.dna.web.rest;

import ae.gov.dubaipolice.dna.domain.SampleStatus;
import ae.gov.dubaipolice.dna.repository.SampleStatusRepository;
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
 * REST controller for managing {@link ae.gov.dubaipolice.dna.domain.SampleStatus}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class SampleStatusResource {

    private final Logger log = LoggerFactory.getLogger(SampleStatusResource.class);

    private static final String ENTITY_NAME = "sampleStatus";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SampleStatusRepository sampleStatusRepository;

    public SampleStatusResource(SampleStatusRepository sampleStatusRepository) {
        this.sampleStatusRepository = sampleStatusRepository;
    }

    /**
     * {@code POST  /sample-statuses} : Create a new sampleStatus.
     *
     * @param sampleStatus the sampleStatus to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new sampleStatus, or with status {@code 400 (Bad Request)} if the sampleStatus has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/sample-statuses")
    public ResponseEntity<SampleStatus> createSampleStatus(@RequestBody SampleStatus sampleStatus) throws URISyntaxException {
        log.debug("REST request to save SampleStatus : {}", sampleStatus);
        if (sampleStatus.getId() != null) {
            throw new BadRequestAlertException("A new sampleStatus cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SampleStatus result = sampleStatusRepository.save(sampleStatus);
        return ResponseEntity
            .created(new URI("/api/sample-statuses/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /sample-statuses/:id} : Updates an existing sampleStatus.
     *
     * @param id the id of the sampleStatus to save.
     * @param sampleStatus the sampleStatus to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated sampleStatus,
     * or with status {@code 400 (Bad Request)} if the sampleStatus is not valid,
     * or with status {@code 500 (Internal Server Error)} if the sampleStatus couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/sample-statuses/{id}")
    public ResponseEntity<SampleStatus> updateSampleStatus(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody SampleStatus sampleStatus
    ) throws URISyntaxException {
        log.debug("REST request to update SampleStatus : {}, {}", id, sampleStatus);
        if (sampleStatus.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, sampleStatus.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!sampleStatusRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        SampleStatus result = sampleStatusRepository.save(sampleStatus);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, sampleStatus.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /sample-statuses/:id} : Partial updates given fields of an existing sampleStatus, field will ignore if it is null
     *
     * @param id the id of the sampleStatus to save.
     * @param sampleStatus the sampleStatus to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated sampleStatus,
     * or with status {@code 400 (Bad Request)} if the sampleStatus is not valid,
     * or with status {@code 404 (Not Found)} if the sampleStatus is not found,
     * or with status {@code 500 (Internal Server Error)} if the sampleStatus couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/sample-statuses/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<SampleStatus> partialUpdateSampleStatus(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody SampleStatus sampleStatus
    ) throws URISyntaxException {
        log.debug("REST request to partial update SampleStatus partially : {}, {}", id, sampleStatus);
        if (sampleStatus.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, sampleStatus.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!sampleStatusRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<SampleStatus> result = sampleStatusRepository
            .findById(sampleStatus.getId())
            .map(existingSampleStatus -> {
                if (sampleStatus.getName() != null) {
                    existingSampleStatus.setName(sampleStatus.getName());
                }
                if (sampleStatus.getDescription() != null) {
                    existingSampleStatus.setDescription(sampleStatus.getDescription());
                }

                return existingSampleStatus;
            })
            .map(sampleStatusRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, sampleStatus.getId().toString())
        );
    }

    /**
     * {@code GET  /sample-statuses} : get all the sampleStatuses.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of sampleStatuses in body.
     */
    @GetMapping("/sample-statuses")
    public List<SampleStatus> getAllSampleStatuses() {
        log.debug("REST request to get all SampleStatuses");
        return sampleStatusRepository.findAll();
    }

    /**
     * {@code GET  /sample-statuses/:id} : get the "id" sampleStatus.
     *
     * @param id the id of the sampleStatus to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the sampleStatus, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/sample-statuses/{id}")
    public ResponseEntity<SampleStatus> getSampleStatus(@PathVariable Long id) {
        log.debug("REST request to get SampleStatus : {}", id);
        Optional<SampleStatus> sampleStatus = sampleStatusRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(sampleStatus);
    }

    /**
     * {@code DELETE  /sample-statuses/:id} : delete the "id" sampleStatus.
     *
     * @param id the id of the sampleStatus to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/sample-statuses/{id}")
    public ResponseEntity<Void> deleteSampleStatus(@PathVariable Long id) {
        log.debug("REST request to delete SampleStatus : {}", id);
        sampleStatusRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
