package ae.gov.dubaipolice.dna.web.rest;

import ae.gov.dubaipolice.dna.domain.CaseSampleType;
import ae.gov.dubaipolice.dna.repository.CaseSampleTypeRepository;
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
 * REST controller for managing {@link ae.gov.dubaipolice.dna.domain.CaseSampleType}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class CaseSampleTypeResource {

    private final Logger log = LoggerFactory.getLogger(CaseSampleTypeResource.class);

    private static final String ENTITY_NAME = "caseSampleType";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CaseSampleTypeRepository caseSampleTypeRepository;

    public CaseSampleTypeResource(CaseSampleTypeRepository caseSampleTypeRepository) {
        this.caseSampleTypeRepository = caseSampleTypeRepository;
    }

    /**
     * {@code POST  /case-sample-types} : Create a new caseSampleType.
     *
     * @param caseSampleType the caseSampleType to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new caseSampleType, or with status {@code 400 (Bad Request)} if the caseSampleType has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/case-sample-types")
    public ResponseEntity<CaseSampleType> createCaseSampleType(@RequestBody CaseSampleType caseSampleType) throws URISyntaxException {
        log.debug("REST request to save CaseSampleType : {}", caseSampleType);
        if (caseSampleType.getId() != null) {
            throw new BadRequestAlertException("A new caseSampleType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CaseSampleType result = caseSampleTypeRepository.save(caseSampleType);
        return ResponseEntity
            .created(new URI("/api/case-sample-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /case-sample-types/:id} : Updates an existing caseSampleType.
     *
     * @param id the id of the caseSampleType to save.
     * @param caseSampleType the caseSampleType to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated caseSampleType,
     * or with status {@code 400 (Bad Request)} if the caseSampleType is not valid,
     * or with status {@code 500 (Internal Server Error)} if the caseSampleType couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/case-sample-types/{id}")
    public ResponseEntity<CaseSampleType> updateCaseSampleType(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody CaseSampleType caseSampleType
    ) throws URISyntaxException {
        log.debug("REST request to update CaseSampleType : {}, {}", id, caseSampleType);
        if (caseSampleType.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, caseSampleType.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!caseSampleTypeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        CaseSampleType result = caseSampleTypeRepository.save(caseSampleType);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, caseSampleType.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /case-sample-types/:id} : Partial updates given fields of an existing caseSampleType, field will ignore if it is null
     *
     * @param id the id of the caseSampleType to save.
     * @param caseSampleType the caseSampleType to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated caseSampleType,
     * or with status {@code 400 (Bad Request)} if the caseSampleType is not valid,
     * or with status {@code 404 (Not Found)} if the caseSampleType is not found,
     * or with status {@code 500 (Internal Server Error)} if the caseSampleType couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/case-sample-types/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<CaseSampleType> partialUpdateCaseSampleType(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody CaseSampleType caseSampleType
    ) throws URISyntaxException {
        log.debug("REST request to partial update CaseSampleType partially : {}, {}", id, caseSampleType);
        if (caseSampleType.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, caseSampleType.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!caseSampleTypeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CaseSampleType> result = caseSampleTypeRepository
            .findById(caseSampleType.getId())
            .map(existingCaseSampleType -> {
                if (caseSampleType.getName() != null) {
                    existingCaseSampleType.setName(caseSampleType.getName());
                }
                if (caseSampleType.getDescription() != null) {
                    existingCaseSampleType.setDescription(caseSampleType.getDescription());
                }

                return existingCaseSampleType;
            })
            .map(caseSampleTypeRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, caseSampleType.getId().toString())
        );
    }

    /**
     * {@code GET  /case-sample-types} : get all the caseSampleTypes.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of caseSampleTypes in body.
     */
    @GetMapping("/case-sample-types")
    public List<CaseSampleType> getAllCaseSampleTypes() {
        log.debug("REST request to get all CaseSampleTypes");
        return caseSampleTypeRepository.findAll();
    }

    /**
     * {@code GET  /case-sample-types/:id} : get the "id" caseSampleType.
     *
     * @param id the id of the caseSampleType to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the caseSampleType, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/case-sample-types/{id}")
    public ResponseEntity<CaseSampleType> getCaseSampleType(@PathVariable Long id) {
        log.debug("REST request to get CaseSampleType : {}", id);
        Optional<CaseSampleType> caseSampleType = caseSampleTypeRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(caseSampleType);
    }

    /**
     * {@code DELETE  /case-sample-types/:id} : delete the "id" caseSampleType.
     *
     * @param id the id of the caseSampleType to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/case-sample-types/{id}")
    public ResponseEntity<Void> deleteCaseSampleType(@PathVariable Long id) {
        log.debug("REST request to delete CaseSampleType : {}", id);
        caseSampleTypeRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
