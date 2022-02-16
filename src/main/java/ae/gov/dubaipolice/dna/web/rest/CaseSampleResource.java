package ae.gov.dubaipolice.dna.web.rest;

import ae.gov.dubaipolice.dna.domain.CaseSample;
import ae.gov.dubaipolice.dna.repository.CaseSampleRepository;
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
 * REST controller for managing {@link ae.gov.dubaipolice.dna.domain.CaseSample}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class CaseSampleResource {

    private final Logger log = LoggerFactory.getLogger(CaseSampleResource.class);

    private static final String ENTITY_NAME = "caseSample";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CaseSampleRepository caseSampleRepository;

    public CaseSampleResource(CaseSampleRepository caseSampleRepository) {
        this.caseSampleRepository = caseSampleRepository;
    }

    /**
     * {@code POST  /case-samples} : Create a new caseSample.
     *
     * @param caseSample the caseSample to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new caseSample, or with status {@code 400 (Bad Request)} if the caseSample has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/case-samples")
    public ResponseEntity<CaseSample> createCaseSample(@RequestBody CaseSample caseSample) throws URISyntaxException {
        log.debug("REST request to save CaseSample : {}", caseSample);
        if (caseSample.getId() != null) {
            throw new BadRequestAlertException("A new caseSample cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CaseSample result = caseSampleRepository.save(caseSample);
        return ResponseEntity
            .created(new URI("/api/case-samples/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /case-samples/:id} : Updates an existing caseSample.
     *
     * @param id the id of the caseSample to save.
     * @param caseSample the caseSample to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated caseSample,
     * or with status {@code 400 (Bad Request)} if the caseSample is not valid,
     * or with status {@code 500 (Internal Server Error)} if the caseSample couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/case-samples/{id}")
    public ResponseEntity<CaseSample> updateCaseSample(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody CaseSample caseSample
    ) throws URISyntaxException {
        log.debug("REST request to update CaseSample : {}, {}", id, caseSample);
        if (caseSample.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, caseSample.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!caseSampleRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        CaseSample result = caseSampleRepository.save(caseSample);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, caseSample.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /case-samples/:id} : Partial updates given fields of an existing caseSample, field will ignore if it is null
     *
     * @param id the id of the caseSample to save.
     * @param caseSample the caseSample to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated caseSample,
     * or with status {@code 400 (Bad Request)} if the caseSample is not valid,
     * or with status {@code 404 (Not Found)} if the caseSample is not found,
     * or with status {@code 500 (Internal Server Error)} if the caseSample couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/case-samples/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<CaseSample> partialUpdateCaseSample(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody CaseSample caseSample
    ) throws URISyntaxException {
        log.debug("REST request to partial update CaseSample partially : {}, {}", id, caseSample);
        if (caseSample.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, caseSample.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!caseSampleRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CaseSample> result = caseSampleRepository
            .findById(caseSample.getId())
            .map(existingCaseSample -> {
                if (caseSample.getSampleId() != null) {
                    existingCaseSample.setSampleId(caseSample.getSampleId());
                }
                if (caseSample.getFullNameAr() != null) {
                    existingCaseSample.setFullNameAr(caseSample.getFullNameAr());
                }
                if (caseSample.getFullNameEn() != null) {
                    existingCaseSample.setFullNameEn(caseSample.getFullNameEn());
                }
                if (caseSample.getNatAr() != null) {
                    existingCaseSample.setNatAr(caseSample.getNatAr());
                }
                if (caseSample.getNatEn() != null) {
                    existingCaseSample.setNatEn(caseSample.getNatEn());
                }
                if (caseSample.getUid() != null) {
                    existingCaseSample.setUid(caseSample.getUid());
                }
                if (caseSample.getEmiratesId() != null) {
                    existingCaseSample.setEmiratesId(caseSample.getEmiratesId());
                }
                if (caseSample.getExhibit() != null) {
                    existingCaseSample.setExhibit(caseSample.getExhibit());
                }
                if (caseSample.getGender() != null) {
                    existingCaseSample.setGender(caseSample.getGender());
                }
                if (caseSample.getDateOfBirth() != null) {
                    existingCaseSample.setDateOfBirth(caseSample.getDateOfBirth());
                }
                if (caseSample.getDueDate() != null) {
                    existingCaseSample.setDueDate(caseSample.getDueDate());
                }
                if (caseSample.getRecievedDate() != null) {
                    existingCaseSample.setRecievedDate(caseSample.getRecievedDate());
                }
                if (caseSample.getSampleNotes() != null) {
                    existingCaseSample.setSampleNotes(caseSample.getSampleNotes());
                }
                if (caseSample.getAddBy() != null) {
                    existingCaseSample.setAddBy(caseSample.getAddBy());
                }
                if (caseSample.getAddDate() != null) {
                    existingCaseSample.setAddDate(caseSample.getAddDate());
                }
                if (caseSample.getUpdateBy() != null) {
                    existingCaseSample.setUpdateBy(caseSample.getUpdateBy());
                }
                if (caseSample.getUpdateDate() != null) {
                    existingCaseSample.setUpdateDate(caseSample.getUpdateDate());
                }
                if (caseSample.getAttachment() != null) {
                    existingCaseSample.setAttachment(caseSample.getAttachment());
                }
                if (caseSample.getAttachmentContentType() != null) {
                    existingCaseSample.setAttachmentContentType(caseSample.getAttachmentContentType());
                }
                if (caseSample.getCaseNumber() != null) {
                    existingCaseSample.setCaseNumber(caseSample.getCaseNumber());
                }
                if (caseSample.getBarcodeNumber() != null) {
                    existingCaseSample.setBarcodeNumber(caseSample.getBarcodeNumber());
                }
                if (caseSample.getCaseType() != null) {
                    existingCaseSample.setCaseType(caseSample.getCaseType());
                }
                if (caseSample.getPoliceStation() != null) {
                    existingCaseSample.setPoliceStation(caseSample.getPoliceStation());
                }
                if (caseSample.getReportNumber() != null) {
                    existingCaseSample.setReportNumber(caseSample.getReportNumber());
                }
                if (caseSample.getTestEndDate() != null) {
                    existingCaseSample.setTestEndDate(caseSample.getTestEndDate());
                }
                if (caseSample.getCaseNote() != null) {
                    existingCaseSample.setCaseNote(caseSample.getCaseNote());
                }

                return existingCaseSample;
            })
            .map(caseSampleRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, caseSample.getId().toString())
        );
    }

    /**
     * {@code GET  /case-samples} : get all the caseSamples.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of caseSamples in body.
     */
    @GetMapping("/case-samples")
    public ResponseEntity<List<CaseSample>> getAllCaseSamples(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of CaseSamples");
        Page<CaseSample> page = caseSampleRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /case-samples/:id} : get the "id" caseSample.
     *
     * @param id the id of the caseSample to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the caseSample, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/case-samples/{id}")
    public ResponseEntity<CaseSample> getCaseSample(@PathVariable Long id) {
        log.debug("REST request to get CaseSample : {}", id);
        Optional<CaseSample> caseSample = caseSampleRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(caseSample);
    }

    /**
     * {@code DELETE  /case-samples/:id} : delete the "id" caseSample.
     *
     * @param id the id of the caseSample to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/case-samples/{id}")
    public ResponseEntity<Void> deleteCaseSample(@PathVariable Long id) {
        log.debug("REST request to delete CaseSample : {}", id);
        caseSampleRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
