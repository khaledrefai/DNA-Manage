package ae.gov.dubaipolice.dna.web.rest;

import ae.gov.dubaipolice.dna.domain.InbodyData;
import ae.gov.dubaipolice.dna.repository.InbodyDataRepository;
import ae.gov.dubaipolice.dna.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link ae.gov.dubaipolice.dna.domain.InbodyData}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class InbodyDataResource {

    private final Logger log = LoggerFactory.getLogger(InbodyDataResource.class);

    private static final String ENTITY_NAME = "inbodyData";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final InbodyDataRepository inbodyDataRepository;

    public InbodyDataResource(InbodyDataRepository inbodyDataRepository) {
        this.inbodyDataRepository = inbodyDataRepository;
    }

    /**
     * {@code POST  /inbody-data} : Create a new inbodyData.
     *
     * @param inbodyData the inbodyData to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new inbodyData, or with status {@code 400 (Bad Request)} if the inbodyData has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/inbody-data")
    public ResponseEntity<InbodyData> createInbodyData(@RequestBody InbodyData inbodyData) throws URISyntaxException {
        log.debug("REST request to save InbodyData : {}", inbodyData);
        if (inbodyData.getId() != null) {
            throw new BadRequestAlertException("A new inbodyData cannot already have an ID", ENTITY_NAME, "idexists");
        }
        InbodyData result = inbodyDataRepository.save(inbodyData);
        return ResponseEntity
            .created(new URI("/api/inbody-data/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /inbody-data/:id} : Updates an existing inbodyData.
     *
     * @param id the id of the inbodyData to save.
     * @param inbodyData the inbodyData to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated inbodyData,
     * or with status {@code 400 (Bad Request)} if the inbodyData is not valid,
     * or with status {@code 500 (Internal Server Error)} if the inbodyData couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/inbody-data/{id}")
    public ResponseEntity<InbodyData> updateInbodyData(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody InbodyData inbodyData
    ) throws URISyntaxException {
        log.debug("REST request to update InbodyData : {}, {}", id, inbodyData);
        if (inbodyData.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, inbodyData.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!inbodyDataRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        InbodyData result = inbodyDataRepository.save(inbodyData);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, inbodyData.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /inbody-data/:id} : Partial updates given fields of an existing inbodyData, field will ignore if it is null
     *
     * @param id the id of the inbodyData to save.
     * @param inbodyData the inbodyData to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated inbodyData,
     * or with status {@code 400 (Bad Request)} if the inbodyData is not valid,
     * or with status {@code 404 (Not Found)} if the inbodyData is not found,
     * or with status {@code 500 (Internal Server Error)} if the inbodyData couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/inbody-data/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<InbodyData> partialUpdateInbodyData(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody InbodyData inbodyData
    ) throws URISyntaxException {
        log.debug("REST request to partial update InbodyData partially : {}, {}", id, inbodyData);
        if (inbodyData.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, inbodyData.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!inbodyDataRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<InbodyData> result = inbodyDataRepository
            .findById(inbodyData.getId())
            .map(existingInbodyData -> {
                if (inbodyData.getUserID() != null) {
                    existingInbodyData.setUserID(inbodyData.getUserID());
                }
                if (inbodyData.getColumnName() != null) {
                    existingInbodyData.setColumnName(inbodyData.getColumnName());
                }
                if (inbodyData.getColumnValue() != null) {
                    existingInbodyData.setColumnValue(inbodyData.getColumnValue());
                }

                return existingInbodyData;
            })
            .map(inbodyDataRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, inbodyData.getId().toString())
        );
    }

    /**
     * {@code GET  /inbody-data} : get all the inbodyData.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of inbodyData in body.
     */
    @GetMapping("/inbody-data")
    public ResponseEntity<List<InbodyData>> getAllInbodyData(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of InbodyData");
        Page<InbodyData> page = inbodyDataRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /inbody-data/:id} : get the "id" inbodyData.
     *
     * @param id the id of the inbodyData to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the inbodyData, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/inbody-data/{id}")
    public ResponseEntity<InbodyData> getInbodyData(@PathVariable Long id) {
        log.debug("REST request to get InbodyData : {}", id);
        Optional<InbodyData> inbodyData = inbodyDataRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(inbodyData);
    }

    /**
     * {@code DELETE  /inbody-data/:id} : delete the "id" inbodyData.
     *
     * @param id the id of the inbodyData to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/inbody-data/{id}")
    public ResponseEntity<Void> deleteInbodyData(@PathVariable Long id) {
        log.debug("REST request to delete InbodyData : {}", id);
        inbodyDataRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
