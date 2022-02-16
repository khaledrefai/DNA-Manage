package ae.gov.dubaipolice.dna.web.rest;

import ae.gov.dubaipolice.dna.domain.DnaProfileType;
import ae.gov.dubaipolice.dna.repository.DnaProfileTypeRepository;
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
 * REST controller for managing {@link ae.gov.dubaipolice.dna.domain.DnaProfileType}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class DnaProfileTypeResource {

    private final Logger log = LoggerFactory.getLogger(DnaProfileTypeResource.class);

    private static final String ENTITY_NAME = "dnaProfileType";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DnaProfileTypeRepository dnaProfileTypeRepository;

    public DnaProfileTypeResource(DnaProfileTypeRepository dnaProfileTypeRepository) {
        this.dnaProfileTypeRepository = dnaProfileTypeRepository;
    }

    /**
     * {@code POST  /dna-profile-types} : Create a new dnaProfileType.
     *
     * @param dnaProfileType the dnaProfileType to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new dnaProfileType, or with status {@code 400 (Bad Request)} if the dnaProfileType has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/dna-profile-types")
    public ResponseEntity<DnaProfileType> createDnaProfileType(@RequestBody DnaProfileType dnaProfileType) throws URISyntaxException {
        log.debug("REST request to save DnaProfileType : {}", dnaProfileType);
        if (dnaProfileType.getId() != null) {
            throw new BadRequestAlertException("A new dnaProfileType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DnaProfileType result = dnaProfileTypeRepository.save(dnaProfileType);
        return ResponseEntity
            .created(new URI("/api/dna-profile-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /dna-profile-types/:id} : Updates an existing dnaProfileType.
     *
     * @param id the id of the dnaProfileType to save.
     * @param dnaProfileType the dnaProfileType to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated dnaProfileType,
     * or with status {@code 400 (Bad Request)} if the dnaProfileType is not valid,
     * or with status {@code 500 (Internal Server Error)} if the dnaProfileType couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/dna-profile-types/{id}")
    public ResponseEntity<DnaProfileType> updateDnaProfileType(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody DnaProfileType dnaProfileType
    ) throws URISyntaxException {
        log.debug("REST request to update DnaProfileType : {}, {}", id, dnaProfileType);
        if (dnaProfileType.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, dnaProfileType.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!dnaProfileTypeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        DnaProfileType result = dnaProfileTypeRepository.save(dnaProfileType);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, dnaProfileType.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /dna-profile-types/:id} : Partial updates given fields of an existing dnaProfileType, field will ignore if it is null
     *
     * @param id the id of the dnaProfileType to save.
     * @param dnaProfileType the dnaProfileType to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated dnaProfileType,
     * or with status {@code 400 (Bad Request)} if the dnaProfileType is not valid,
     * or with status {@code 404 (Not Found)} if the dnaProfileType is not found,
     * or with status {@code 500 (Internal Server Error)} if the dnaProfileType couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/dna-profile-types/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<DnaProfileType> partialUpdateDnaProfileType(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody DnaProfileType dnaProfileType
    ) throws URISyntaxException {
        log.debug("REST request to partial update DnaProfileType partially : {}, {}", id, dnaProfileType);
        if (dnaProfileType.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, dnaProfileType.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!dnaProfileTypeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<DnaProfileType> result = dnaProfileTypeRepository
            .findById(dnaProfileType.getId())
            .map(existingDnaProfileType -> {
                if (dnaProfileType.getName() != null) {
                    existingDnaProfileType.setName(dnaProfileType.getName());
                }
                if (dnaProfileType.getDescription() != null) {
                    existingDnaProfileType.setDescription(dnaProfileType.getDescription());
                }

                return existingDnaProfileType;
            })
            .map(dnaProfileTypeRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, dnaProfileType.getId().toString())
        );
    }

    /**
     * {@code GET  /dna-profile-types} : get all the dnaProfileTypes.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of dnaProfileTypes in body.
     */
    @GetMapping("/dna-profile-types")
    public List<DnaProfileType> getAllDnaProfileTypes() {
        log.debug("REST request to get all DnaProfileTypes");
        return dnaProfileTypeRepository.findAll();
    }

    /**
     * {@code GET  /dna-profile-types/:id} : get the "id" dnaProfileType.
     *
     * @param id the id of the dnaProfileType to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the dnaProfileType, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/dna-profile-types/{id}")
    public ResponseEntity<DnaProfileType> getDnaProfileType(@PathVariable Long id) {
        log.debug("REST request to get DnaProfileType : {}", id);
        Optional<DnaProfileType> dnaProfileType = dnaProfileTypeRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(dnaProfileType);
    }

    /**
     * {@code DELETE  /dna-profile-types/:id} : delete the "id" dnaProfileType.
     *
     * @param id the id of the dnaProfileType to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/dna-profile-types/{id}")
    public ResponseEntity<Void> deleteDnaProfileType(@PathVariable Long id) {
        log.debug("REST request to delete DnaProfileType : {}", id);
        dnaProfileTypeRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
