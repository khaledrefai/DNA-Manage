package ae.gov.dubaipolice.dna.web.rest;

import ae.gov.dubaipolice.dna.domain.MemberData;
import ae.gov.dubaipolice.dna.repository.MemberDataRepository;
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
 * REST controller for managing {@link ae.gov.dubaipolice.dna.domain.MemberData}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class MemberDataResource {

    private final Logger log = LoggerFactory.getLogger(MemberDataResource.class);

    private static final String ENTITY_NAME = "memberData";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MemberDataRepository memberDataRepository;

    public MemberDataResource(MemberDataRepository memberDataRepository) {
        this.memberDataRepository = memberDataRepository;
    }

    /**
     * {@code POST  /member-data} : Create a new memberData.
     *
     * @param memberData the memberData to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new memberData, or with status {@code 400 (Bad Request)} if the memberData has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/member-data")
    public ResponseEntity<MemberData> createMemberData(@RequestBody MemberData memberData) throws URISyntaxException {
        log.debug("REST request to save MemberData : {}", memberData);
        if (memberData.getId() != null) {
            throw new BadRequestAlertException("A new memberData cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MemberData result = memberDataRepository.save(memberData);
        return ResponseEntity
            .created(new URI("/api/member-data/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /member-data/:id} : Updates an existing memberData.
     *
     * @param id the id of the memberData to save.
     * @param memberData the memberData to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated memberData,
     * or with status {@code 400 (Bad Request)} if the memberData is not valid,
     * or with status {@code 500 (Internal Server Error)} if the memberData couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/member-data/{id}")
    public ResponseEntity<MemberData> updateMemberData(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody MemberData memberData
    ) throws URISyntaxException {
        log.debug("REST request to update MemberData : {}, {}", id, memberData);
        if (memberData.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, memberData.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!memberDataRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        MemberData result = memberDataRepository.save(memberData);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, memberData.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /member-data/:id} : Partial updates given fields of an existing memberData, field will ignore if it is null
     *
     * @param id the id of the memberData to save.
     * @param memberData the memberData to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated memberData,
     * or with status {@code 400 (Bad Request)} if the memberData is not valid,
     * or with status {@code 404 (Not Found)} if the memberData is not found,
     * or with status {@code 500 (Internal Server Error)} if the memberData couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/member-data/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<MemberData> partialUpdateMemberData(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody MemberData memberData
    ) throws URISyntaxException {
        log.debug("REST request to partial update MemberData partially : {}, {}", id, memberData);
        if (memberData.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, memberData.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!memberDataRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<MemberData> result = memberDataRepository
            .findById(memberData.getId())
            .map(existingMemberData -> {
                if (memberData.getTestDate() != null) {
                    existingMemberData.setTestDate(memberData.getTestDate());
                }
                if (memberData.getUserId() != null) {
                    existingMemberData.setUserId(memberData.getUserId());
                }
                if (memberData.getUserName() != null) {
                    existingMemberData.setUserName(memberData.getUserName());
                }
                if (memberData.getUserGender() != null) {
                    existingMemberData.setUserGender(memberData.getUserGender());
                }
                if (memberData.getUserBirthday() != null) {
                    existingMemberData.setUserBirthday(memberData.getUserBirthday());
                }
                if (memberData.getUserAge() != null) {
                    existingMemberData.setUserAge(memberData.getUserAge());
                }
                if (memberData.getUserHeight() != null) {
                    existingMemberData.setUserHeight(memberData.getUserHeight());
                }
                if (memberData.getOrderDate() != null) {
                    existingMemberData.setOrderDate(memberData.getOrderDate());
                }

                return existingMemberData;
            })
            .map(memberDataRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, memberData.getId().toString())
        );
    }

    /**
     * {@code GET  /member-data} : get all the memberData.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of memberData in body.
     */
    @GetMapping("/member-data")
    public List<MemberData> getAllMemberData() {
        log.debug("REST request to get all MemberData");
        return memberDataRepository.findAll();
    }

    /**
     * {@code GET  /member-data/:id} : get the "id" memberData.
     *
     * @param id the id of the memberData to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the memberData, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/member-data/{id}")
    public ResponseEntity<MemberData> getMemberData(@PathVariable Long id) {
        log.debug("REST request to get MemberData : {}", id);
        Optional<MemberData> memberData = memberDataRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(memberData);
    }

    /**
     * {@code DELETE  /member-data/:id} : delete the "id" memberData.
     *
     * @param id the id of the memberData to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/member-data/{id}")
    public ResponseEntity<Void> deleteMemberData(@PathVariable Long id) {
        log.debug("REST request to delete MemberData : {}", id);
        memberDataRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
