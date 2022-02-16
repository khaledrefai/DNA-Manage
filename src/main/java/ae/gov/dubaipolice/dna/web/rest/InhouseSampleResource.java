package ae.gov.dubaipolice.dna.web.rest;

import ae.gov.dubaipolice.dna.domain.InhouseSample;
import ae.gov.dubaipolice.dna.repository.InhouseSampleRepository;
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
 * REST controller for managing {@link ae.gov.dubaipolice.dna.domain.InhouseSample}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class InhouseSampleResource {

    private final Logger log = LoggerFactory.getLogger(InhouseSampleResource.class);

    private static final String ENTITY_NAME = "inhouseSample";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final InhouseSampleRepository inhouseSampleRepository;

    public InhouseSampleResource(InhouseSampleRepository inhouseSampleRepository) {
        this.inhouseSampleRepository = inhouseSampleRepository;
    }

    /**
     * {@code POST  /inhouse-samples} : Create a new inhouseSample.
     *
     * @param inhouseSample the inhouseSample to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new inhouseSample, or with status {@code 400 (Bad Request)} if the inhouseSample has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/inhouse-samples")
    public ResponseEntity<InhouseSample> createInhouseSample(@RequestBody InhouseSample inhouseSample) throws URISyntaxException {
        log.debug("REST request to save InhouseSample : {}", inhouseSample);
        if (inhouseSample.getId() != null) {
            throw new BadRequestAlertException("A new inhouseSample cannot already have an ID", ENTITY_NAME, "idexists");
        }
        InhouseSample result = inhouseSampleRepository.save(inhouseSample);
        return ResponseEntity
            .created(new URI("/api/inhouse-samples/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /inhouse-samples/:id} : Updates an existing inhouseSample.
     *
     * @param id the id of the inhouseSample to save.
     * @param inhouseSample the inhouseSample to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated inhouseSample,
     * or with status {@code 400 (Bad Request)} if the inhouseSample is not valid,
     * or with status {@code 500 (Internal Server Error)} if the inhouseSample couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/inhouse-samples/{id}")
    public ResponseEntity<InhouseSample> updateInhouseSample(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody InhouseSample inhouseSample
    ) throws URISyntaxException {
        log.debug("REST request to update InhouseSample : {}, {}", id, inhouseSample);
        if (inhouseSample.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, inhouseSample.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!inhouseSampleRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        InhouseSample result = inhouseSampleRepository.save(inhouseSample);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, inhouseSample.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /inhouse-samples/:id} : Partial updates given fields of an existing inhouseSample, field will ignore if it is null
     *
     * @param id the id of the inhouseSample to save.
     * @param inhouseSample the inhouseSample to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated inhouseSample,
     * or with status {@code 400 (Bad Request)} if the inhouseSample is not valid,
     * or with status {@code 404 (Not Found)} if the inhouseSample is not found,
     * or with status {@code 500 (Internal Server Error)} if the inhouseSample couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/inhouse-samples/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<InhouseSample> partialUpdateInhouseSample(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody InhouseSample inhouseSample
    ) throws URISyntaxException {
        log.debug("REST request to partial update InhouseSample partially : {}, {}", id, inhouseSample);
        if (inhouseSample.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, inhouseSample.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!inhouseSampleRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<InhouseSample> result = inhouseSampleRepository
            .findById(inhouseSample.getId())
            .map(existingInhouseSample -> {
                if (inhouseSample.getSampleId() != null) {
                    existingInhouseSample.setSampleId(inhouseSample.getSampleId());
                }
                if (inhouseSample.getEmpGrpNo() != null) {
                    existingInhouseSample.setEmpGrpNo(inhouseSample.getEmpGrpNo());
                }
                if (inhouseSample.getFullNameAr() != null) {
                    existingInhouseSample.setFullNameAr(inhouseSample.getFullNameAr());
                }
                if (inhouseSample.getFullNameEn() != null) {
                    existingInhouseSample.setFullNameEn(inhouseSample.getFullNameEn());
                }
                if (inhouseSample.getNatAr() != null) {
                    existingInhouseSample.setNatAr(inhouseSample.getNatAr());
                }
                if (inhouseSample.getNatEn() != null) {
                    existingInhouseSample.setNatEn(inhouseSample.getNatEn());
                }
                if (inhouseSample.getUid() != null) {
                    existingInhouseSample.setUid(inhouseSample.getUid());
                }
                if (inhouseSample.getEmiratesId() != null) {
                    existingInhouseSample.setEmiratesId(inhouseSample.getEmiratesId());
                }
                if (inhouseSample.getExhibit() != null) {
                    existingInhouseSample.setExhibit(inhouseSample.getExhibit());
                }
                if (inhouseSample.getGender() != null) {
                    existingInhouseSample.setGender(inhouseSample.getGender());
                }
                if (inhouseSample.getDateOfBirth() != null) {
                    existingInhouseSample.setDateOfBirth(inhouseSample.getDateOfBirth());
                }
                if (inhouseSample.getBatchDate() != null) {
                    existingInhouseSample.setBatchDate(inhouseSample.getBatchDate());
                }
                if (inhouseSample.getCollectionDate() != null) {
                    existingInhouseSample.setCollectionDate(inhouseSample.getCollectionDate());
                }
                if (inhouseSample.getSampleNotes() != null) {
                    existingInhouseSample.setSampleNotes(inhouseSample.getSampleNotes());
                }
                if (inhouseSample.getAddBy() != null) {
                    existingInhouseSample.setAddBy(inhouseSample.getAddBy());
                }
                if (inhouseSample.getAddDate() != null) {
                    existingInhouseSample.setAddDate(inhouseSample.getAddDate());
                }
                if (inhouseSample.getUpdateBy() != null) {
                    existingInhouseSample.setUpdateBy(inhouseSample.getUpdateBy());
                }
                if (inhouseSample.getUpdateDate() != null) {
                    existingInhouseSample.setUpdateDate(inhouseSample.getUpdateDate());
                }
                if (inhouseSample.getAttachment() != null) {
                    existingInhouseSample.setAttachment(inhouseSample.getAttachment());
                }
                if (inhouseSample.getAttachmentContentType() != null) {
                    existingInhouseSample.setAttachmentContentType(inhouseSample.getAttachmentContentType());
                }

                return existingInhouseSample;
            })
            .map(inhouseSampleRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, inhouseSample.getId().toString())
        );
    }

    /**
     * {@code GET  /inhouse-samples} : get all the inhouseSamples.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of inhouseSamples in body.
     */
    @GetMapping("/inhouse-samples")
    public ResponseEntity<List<InhouseSample>> getAllInhouseSamples(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of InhouseSamples");
        Page<InhouseSample> page = inhouseSampleRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /inhouse-samples/:id} : get the "id" inhouseSample.
     *
     * @param id the id of the inhouseSample to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the inhouseSample, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/inhouse-samples/{id}")
    public ResponseEntity<InhouseSample> getInhouseSample(@PathVariable Long id) {
        log.debug("REST request to get InhouseSample : {}", id);
        Optional<InhouseSample> inhouseSample = inhouseSampleRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(inhouseSample);
    }

    /**
     * {@code DELETE  /inhouse-samples/:id} : delete the "id" inhouseSample.
     *
     * @param id the id of the inhouseSample to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/inhouse-samples/{id}")
    public ResponseEntity<Void> deleteInhouseSample(@PathVariable Long id) {
        log.debug("REST request to delete InhouseSample : {}", id);
        inhouseSampleRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
