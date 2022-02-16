package ae.gov.dubaipolice.dna.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import ae.gov.dubaipolice.dna.IntegrationTest;
import ae.gov.dubaipolice.dna.domain.CaseSample;
import ae.gov.dubaipolice.dna.repository.CaseSampleRepository;
import java.time.LocalDate;
import java.time.ZoneId;
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
import org.springframework.util.Base64Utils;

/**
 * Integration tests for the {@link CaseSampleResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CaseSampleResourceIT {

    private static final Long DEFAULT_SAMPLE_ID = 1L;
    private static final Long UPDATED_SAMPLE_ID = 2L;

    private static final String DEFAULT_FULL_NAME_AR = "AAAAAAAAAA";
    private static final String UPDATED_FULL_NAME_AR = "BBBBBBBBBB";

    private static final String DEFAULT_FULL_NAME_EN = "AAAAAAAAAA";
    private static final String UPDATED_FULL_NAME_EN = "BBBBBBBBBB";

    private static final String DEFAULT_NAT_AR = "AAAAAAAAAA";
    private static final String UPDATED_NAT_AR = "BBBBBBBBBB";

    private static final String DEFAULT_NAT_EN = "AAAAAAAAAA";
    private static final String UPDATED_NAT_EN = "BBBBBBBBBB";

    private static final String DEFAULT_UID = "AAAAAAAAAA";
    private static final String UPDATED_UID = "BBBBBBBBBB";

    private static final String DEFAULT_EMIRATES_ID = "AAAAAAAAAA";
    private static final String UPDATED_EMIRATES_ID = "BBBBBBBBBB";

    private static final String DEFAULT_EXHIBIT = "AAAAAAAAAA";
    private static final String UPDATED_EXHIBIT = "BBBBBBBBBB";

    private static final String DEFAULT_GENDER = "AAAAAAAAAA";
    private static final String UPDATED_GENDER = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATE_OF_BIRTH = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_OF_BIRTH = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DUE_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DUE_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_RECIEVED_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_RECIEVED_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_SAMPLE_NOTES = "AAAAAAAAAA";
    private static final String UPDATED_SAMPLE_NOTES = "BBBBBBBBBB";

    private static final Long DEFAULT_ADD_BY = 1L;
    private static final Long UPDATED_ADD_BY = 2L;

    private static final LocalDate DEFAULT_ADD_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_ADD_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Long DEFAULT_UPDATE_BY = 1L;
    private static final Long UPDATED_UPDATE_BY = 2L;

    private static final LocalDate DEFAULT_UPDATE_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_UPDATE_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final byte[] DEFAULT_ATTACHMENT = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_ATTACHMENT = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_ATTACHMENT_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_ATTACHMENT_CONTENT_TYPE = "image/png";

    private static final String DEFAULT_CASE_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_CASE_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_BARCODE_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_BARCODE_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_CASE_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_CASE_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_POLICE_STATION = "AAAAAAAAAA";
    private static final String UPDATED_POLICE_STATION = "BBBBBBBBBB";

    private static final String DEFAULT_REPORT_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_REPORT_NUMBER = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_TEST_END_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_TEST_END_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_CASE_NOTE = "AAAAAAAAAA";
    private static final String UPDATED_CASE_NOTE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/case-samples";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CaseSampleRepository caseSampleRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCaseSampleMockMvc;

    private CaseSample caseSample;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CaseSample createEntity(EntityManager em) {
        CaseSample caseSample = new CaseSample()
            .sampleId(DEFAULT_SAMPLE_ID)
            .fullNameAr(DEFAULT_FULL_NAME_AR)
            .fullNameEn(DEFAULT_FULL_NAME_EN)
            .natAr(DEFAULT_NAT_AR)
            .natEn(DEFAULT_NAT_EN)
            .uid(DEFAULT_UID)
            .emiratesId(DEFAULT_EMIRATES_ID)
            .exhibit(DEFAULT_EXHIBIT)
            .gender(DEFAULT_GENDER)
            .dateOfBirth(DEFAULT_DATE_OF_BIRTH)
            .dueDate(DEFAULT_DUE_DATE)
            .recievedDate(DEFAULT_RECIEVED_DATE)
            .sampleNotes(DEFAULT_SAMPLE_NOTES)
            .addBy(DEFAULT_ADD_BY)
            .addDate(DEFAULT_ADD_DATE)
            .updateBy(DEFAULT_UPDATE_BY)
            .updateDate(DEFAULT_UPDATE_DATE)
            .attachment(DEFAULT_ATTACHMENT)
            .attachmentContentType(DEFAULT_ATTACHMENT_CONTENT_TYPE)
            .caseNumber(DEFAULT_CASE_NUMBER)
            .barcodeNumber(DEFAULT_BARCODE_NUMBER)
            .caseType(DEFAULT_CASE_TYPE)
            .policeStation(DEFAULT_POLICE_STATION)
            .reportNumber(DEFAULT_REPORT_NUMBER)
            .testEndDate(DEFAULT_TEST_END_DATE)
            .caseNote(DEFAULT_CASE_NOTE);
        return caseSample;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CaseSample createUpdatedEntity(EntityManager em) {
        CaseSample caseSample = new CaseSample()
            .sampleId(UPDATED_SAMPLE_ID)
            .fullNameAr(UPDATED_FULL_NAME_AR)
            .fullNameEn(UPDATED_FULL_NAME_EN)
            .natAr(UPDATED_NAT_AR)
            .natEn(UPDATED_NAT_EN)
            .uid(UPDATED_UID)
            .emiratesId(UPDATED_EMIRATES_ID)
            .exhibit(UPDATED_EXHIBIT)
            .gender(UPDATED_GENDER)
            .dateOfBirth(UPDATED_DATE_OF_BIRTH)
            .dueDate(UPDATED_DUE_DATE)
            .recievedDate(UPDATED_RECIEVED_DATE)
            .sampleNotes(UPDATED_SAMPLE_NOTES)
            .addBy(UPDATED_ADD_BY)
            .addDate(UPDATED_ADD_DATE)
            .updateBy(UPDATED_UPDATE_BY)
            .updateDate(UPDATED_UPDATE_DATE)
            .attachment(UPDATED_ATTACHMENT)
            .attachmentContentType(UPDATED_ATTACHMENT_CONTENT_TYPE)
            .caseNumber(UPDATED_CASE_NUMBER)
            .barcodeNumber(UPDATED_BARCODE_NUMBER)
            .caseType(UPDATED_CASE_TYPE)
            .policeStation(UPDATED_POLICE_STATION)
            .reportNumber(UPDATED_REPORT_NUMBER)
            .testEndDate(UPDATED_TEST_END_DATE)
            .caseNote(UPDATED_CASE_NOTE);
        return caseSample;
    }

    @BeforeEach
    public void initTest() {
        caseSample = createEntity(em);
    }

    @Test
    @Transactional
    void createCaseSample() throws Exception {
        int databaseSizeBeforeCreate = caseSampleRepository.findAll().size();
        // Create the CaseSample
        restCaseSampleMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(caseSample))
            )
            .andExpect(status().isCreated());

        // Validate the CaseSample in the database
        List<CaseSample> caseSampleList = caseSampleRepository.findAll();
        assertThat(caseSampleList).hasSize(databaseSizeBeforeCreate + 1);
        CaseSample testCaseSample = caseSampleList.get(caseSampleList.size() - 1);
        assertThat(testCaseSample.getSampleId()).isEqualTo(DEFAULT_SAMPLE_ID);
        assertThat(testCaseSample.getFullNameAr()).isEqualTo(DEFAULT_FULL_NAME_AR);
        assertThat(testCaseSample.getFullNameEn()).isEqualTo(DEFAULT_FULL_NAME_EN);
        assertThat(testCaseSample.getNatAr()).isEqualTo(DEFAULT_NAT_AR);
        assertThat(testCaseSample.getNatEn()).isEqualTo(DEFAULT_NAT_EN);
        assertThat(testCaseSample.getUid()).isEqualTo(DEFAULT_UID);
        assertThat(testCaseSample.getEmiratesId()).isEqualTo(DEFAULT_EMIRATES_ID);
        assertThat(testCaseSample.getExhibit()).isEqualTo(DEFAULT_EXHIBIT);
        assertThat(testCaseSample.getGender()).isEqualTo(DEFAULT_GENDER);
        assertThat(testCaseSample.getDateOfBirth()).isEqualTo(DEFAULT_DATE_OF_BIRTH);
        assertThat(testCaseSample.getDueDate()).isEqualTo(DEFAULT_DUE_DATE);
        assertThat(testCaseSample.getRecievedDate()).isEqualTo(DEFAULT_RECIEVED_DATE);
        assertThat(testCaseSample.getSampleNotes()).isEqualTo(DEFAULT_SAMPLE_NOTES);
        assertThat(testCaseSample.getAddBy()).isEqualTo(DEFAULT_ADD_BY);
        assertThat(testCaseSample.getAddDate()).isEqualTo(DEFAULT_ADD_DATE);
        assertThat(testCaseSample.getUpdateBy()).isEqualTo(DEFAULT_UPDATE_BY);
        assertThat(testCaseSample.getUpdateDate()).isEqualTo(DEFAULT_UPDATE_DATE);
        assertThat(testCaseSample.getAttachment()).isEqualTo(DEFAULT_ATTACHMENT);
        assertThat(testCaseSample.getAttachmentContentType()).isEqualTo(DEFAULT_ATTACHMENT_CONTENT_TYPE);
        assertThat(testCaseSample.getCaseNumber()).isEqualTo(DEFAULT_CASE_NUMBER);
        assertThat(testCaseSample.getBarcodeNumber()).isEqualTo(DEFAULT_BARCODE_NUMBER);
        assertThat(testCaseSample.getCaseType()).isEqualTo(DEFAULT_CASE_TYPE);
        assertThat(testCaseSample.getPoliceStation()).isEqualTo(DEFAULT_POLICE_STATION);
        assertThat(testCaseSample.getReportNumber()).isEqualTo(DEFAULT_REPORT_NUMBER);
        assertThat(testCaseSample.getTestEndDate()).isEqualTo(DEFAULT_TEST_END_DATE);
        assertThat(testCaseSample.getCaseNote()).isEqualTo(DEFAULT_CASE_NOTE);
    }

    @Test
    @Transactional
    void createCaseSampleWithExistingId() throws Exception {
        // Create the CaseSample with an existing ID
        caseSample.setId(1L);

        int databaseSizeBeforeCreate = caseSampleRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCaseSampleMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(caseSample))
            )
            .andExpect(status().isBadRequest());

        // Validate the CaseSample in the database
        List<CaseSample> caseSampleList = caseSampleRepository.findAll();
        assertThat(caseSampleList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllCaseSamples() throws Exception {
        // Initialize the database
        caseSampleRepository.saveAndFlush(caseSample);

        // Get all the caseSampleList
        restCaseSampleMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(caseSample.getId().intValue())))
            .andExpect(jsonPath("$.[*].sampleId").value(hasItem(DEFAULT_SAMPLE_ID.intValue())))
            .andExpect(jsonPath("$.[*].fullNameAr").value(hasItem(DEFAULT_FULL_NAME_AR)))
            .andExpect(jsonPath("$.[*].fullNameEn").value(hasItem(DEFAULT_FULL_NAME_EN)))
            .andExpect(jsonPath("$.[*].natAr").value(hasItem(DEFAULT_NAT_AR)))
            .andExpect(jsonPath("$.[*].natEn").value(hasItem(DEFAULT_NAT_EN)))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID)))
            .andExpect(jsonPath("$.[*].emiratesId").value(hasItem(DEFAULT_EMIRATES_ID)))
            .andExpect(jsonPath("$.[*].exhibit").value(hasItem(DEFAULT_EXHIBIT)))
            .andExpect(jsonPath("$.[*].gender").value(hasItem(DEFAULT_GENDER)))
            .andExpect(jsonPath("$.[*].dateOfBirth").value(hasItem(DEFAULT_DATE_OF_BIRTH.toString())))
            .andExpect(jsonPath("$.[*].dueDate").value(hasItem(DEFAULT_DUE_DATE.toString())))
            .andExpect(jsonPath("$.[*].recievedDate").value(hasItem(DEFAULT_RECIEVED_DATE.toString())))
            .andExpect(jsonPath("$.[*].sampleNotes").value(hasItem(DEFAULT_SAMPLE_NOTES)))
            .andExpect(jsonPath("$.[*].addBy").value(hasItem(DEFAULT_ADD_BY.intValue())))
            .andExpect(jsonPath("$.[*].addDate").value(hasItem(DEFAULT_ADD_DATE.toString())))
            .andExpect(jsonPath("$.[*].updateBy").value(hasItem(DEFAULT_UPDATE_BY.intValue())))
            .andExpect(jsonPath("$.[*].updateDate").value(hasItem(DEFAULT_UPDATE_DATE.toString())))
            .andExpect(jsonPath("$.[*].attachmentContentType").value(hasItem(DEFAULT_ATTACHMENT_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].attachment").value(hasItem(Base64Utils.encodeToString(DEFAULT_ATTACHMENT))))
            .andExpect(jsonPath("$.[*].caseNumber").value(hasItem(DEFAULT_CASE_NUMBER)))
            .andExpect(jsonPath("$.[*].barcodeNumber").value(hasItem(DEFAULT_BARCODE_NUMBER)))
            .andExpect(jsonPath("$.[*].caseType").value(hasItem(DEFAULT_CASE_TYPE)))
            .andExpect(jsonPath("$.[*].policeStation").value(hasItem(DEFAULT_POLICE_STATION)))
            .andExpect(jsonPath("$.[*].reportNumber").value(hasItem(DEFAULT_REPORT_NUMBER)))
            .andExpect(jsonPath("$.[*].testEndDate").value(hasItem(DEFAULT_TEST_END_DATE.toString())))
            .andExpect(jsonPath("$.[*].caseNote").value(hasItem(DEFAULT_CASE_NOTE)));
    }

    @Test
    @Transactional
    void getCaseSample() throws Exception {
        // Initialize the database
        caseSampleRepository.saveAndFlush(caseSample);

        // Get the caseSample
        restCaseSampleMockMvc
            .perform(get(ENTITY_API_URL_ID, caseSample.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(caseSample.getId().intValue()))
            .andExpect(jsonPath("$.sampleId").value(DEFAULT_SAMPLE_ID.intValue()))
            .andExpect(jsonPath("$.fullNameAr").value(DEFAULT_FULL_NAME_AR))
            .andExpect(jsonPath("$.fullNameEn").value(DEFAULT_FULL_NAME_EN))
            .andExpect(jsonPath("$.natAr").value(DEFAULT_NAT_AR))
            .andExpect(jsonPath("$.natEn").value(DEFAULT_NAT_EN))
            .andExpect(jsonPath("$.uid").value(DEFAULT_UID))
            .andExpect(jsonPath("$.emiratesId").value(DEFAULT_EMIRATES_ID))
            .andExpect(jsonPath("$.exhibit").value(DEFAULT_EXHIBIT))
            .andExpect(jsonPath("$.gender").value(DEFAULT_GENDER))
            .andExpect(jsonPath("$.dateOfBirth").value(DEFAULT_DATE_OF_BIRTH.toString()))
            .andExpect(jsonPath("$.dueDate").value(DEFAULT_DUE_DATE.toString()))
            .andExpect(jsonPath("$.recievedDate").value(DEFAULT_RECIEVED_DATE.toString()))
            .andExpect(jsonPath("$.sampleNotes").value(DEFAULT_SAMPLE_NOTES))
            .andExpect(jsonPath("$.addBy").value(DEFAULT_ADD_BY.intValue()))
            .andExpect(jsonPath("$.addDate").value(DEFAULT_ADD_DATE.toString()))
            .andExpect(jsonPath("$.updateBy").value(DEFAULT_UPDATE_BY.intValue()))
            .andExpect(jsonPath("$.updateDate").value(DEFAULT_UPDATE_DATE.toString()))
            .andExpect(jsonPath("$.attachmentContentType").value(DEFAULT_ATTACHMENT_CONTENT_TYPE))
            .andExpect(jsonPath("$.attachment").value(Base64Utils.encodeToString(DEFAULT_ATTACHMENT)))
            .andExpect(jsonPath("$.caseNumber").value(DEFAULT_CASE_NUMBER))
            .andExpect(jsonPath("$.barcodeNumber").value(DEFAULT_BARCODE_NUMBER))
            .andExpect(jsonPath("$.caseType").value(DEFAULT_CASE_TYPE))
            .andExpect(jsonPath("$.policeStation").value(DEFAULT_POLICE_STATION))
            .andExpect(jsonPath("$.reportNumber").value(DEFAULT_REPORT_NUMBER))
            .andExpect(jsonPath("$.testEndDate").value(DEFAULT_TEST_END_DATE.toString()))
            .andExpect(jsonPath("$.caseNote").value(DEFAULT_CASE_NOTE));
    }

    @Test
    @Transactional
    void getNonExistingCaseSample() throws Exception {
        // Get the caseSample
        restCaseSampleMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewCaseSample() throws Exception {
        // Initialize the database
        caseSampleRepository.saveAndFlush(caseSample);

        int databaseSizeBeforeUpdate = caseSampleRepository.findAll().size();

        // Update the caseSample
        CaseSample updatedCaseSample = caseSampleRepository.findById(caseSample.getId()).get();
        // Disconnect from session so that the updates on updatedCaseSample are not directly saved in db
        em.detach(updatedCaseSample);
        updatedCaseSample
            .sampleId(UPDATED_SAMPLE_ID)
            .fullNameAr(UPDATED_FULL_NAME_AR)
            .fullNameEn(UPDATED_FULL_NAME_EN)
            .natAr(UPDATED_NAT_AR)
            .natEn(UPDATED_NAT_EN)
            .uid(UPDATED_UID)
            .emiratesId(UPDATED_EMIRATES_ID)
            .exhibit(UPDATED_EXHIBIT)
            .gender(UPDATED_GENDER)
            .dateOfBirth(UPDATED_DATE_OF_BIRTH)
            .dueDate(UPDATED_DUE_DATE)
            .recievedDate(UPDATED_RECIEVED_DATE)
            .sampleNotes(UPDATED_SAMPLE_NOTES)
            .addBy(UPDATED_ADD_BY)
            .addDate(UPDATED_ADD_DATE)
            .updateBy(UPDATED_UPDATE_BY)
            .updateDate(UPDATED_UPDATE_DATE)
            .attachment(UPDATED_ATTACHMENT)
            .attachmentContentType(UPDATED_ATTACHMENT_CONTENT_TYPE)
            .caseNumber(UPDATED_CASE_NUMBER)
            .barcodeNumber(UPDATED_BARCODE_NUMBER)
            .caseType(UPDATED_CASE_TYPE)
            .policeStation(UPDATED_POLICE_STATION)
            .reportNumber(UPDATED_REPORT_NUMBER)
            .testEndDate(UPDATED_TEST_END_DATE)
            .caseNote(UPDATED_CASE_NOTE);

        restCaseSampleMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedCaseSample.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedCaseSample))
            )
            .andExpect(status().isOk());

        // Validate the CaseSample in the database
        List<CaseSample> caseSampleList = caseSampleRepository.findAll();
        assertThat(caseSampleList).hasSize(databaseSizeBeforeUpdate);
        CaseSample testCaseSample = caseSampleList.get(caseSampleList.size() - 1);
        assertThat(testCaseSample.getSampleId()).isEqualTo(UPDATED_SAMPLE_ID);
        assertThat(testCaseSample.getFullNameAr()).isEqualTo(UPDATED_FULL_NAME_AR);
        assertThat(testCaseSample.getFullNameEn()).isEqualTo(UPDATED_FULL_NAME_EN);
        assertThat(testCaseSample.getNatAr()).isEqualTo(UPDATED_NAT_AR);
        assertThat(testCaseSample.getNatEn()).isEqualTo(UPDATED_NAT_EN);
        assertThat(testCaseSample.getUid()).isEqualTo(UPDATED_UID);
        assertThat(testCaseSample.getEmiratesId()).isEqualTo(UPDATED_EMIRATES_ID);
        assertThat(testCaseSample.getExhibit()).isEqualTo(UPDATED_EXHIBIT);
        assertThat(testCaseSample.getGender()).isEqualTo(UPDATED_GENDER);
        assertThat(testCaseSample.getDateOfBirth()).isEqualTo(UPDATED_DATE_OF_BIRTH);
        assertThat(testCaseSample.getDueDate()).isEqualTo(UPDATED_DUE_DATE);
        assertThat(testCaseSample.getRecievedDate()).isEqualTo(UPDATED_RECIEVED_DATE);
        assertThat(testCaseSample.getSampleNotes()).isEqualTo(UPDATED_SAMPLE_NOTES);
        assertThat(testCaseSample.getAddBy()).isEqualTo(UPDATED_ADD_BY);
        assertThat(testCaseSample.getAddDate()).isEqualTo(UPDATED_ADD_DATE);
        assertThat(testCaseSample.getUpdateBy()).isEqualTo(UPDATED_UPDATE_BY);
        assertThat(testCaseSample.getUpdateDate()).isEqualTo(UPDATED_UPDATE_DATE);
        assertThat(testCaseSample.getAttachment()).isEqualTo(UPDATED_ATTACHMENT);
        assertThat(testCaseSample.getAttachmentContentType()).isEqualTo(UPDATED_ATTACHMENT_CONTENT_TYPE);
        assertThat(testCaseSample.getCaseNumber()).isEqualTo(UPDATED_CASE_NUMBER);
        assertThat(testCaseSample.getBarcodeNumber()).isEqualTo(UPDATED_BARCODE_NUMBER);
        assertThat(testCaseSample.getCaseType()).isEqualTo(UPDATED_CASE_TYPE);
        assertThat(testCaseSample.getPoliceStation()).isEqualTo(UPDATED_POLICE_STATION);
        assertThat(testCaseSample.getReportNumber()).isEqualTo(UPDATED_REPORT_NUMBER);
        assertThat(testCaseSample.getTestEndDate()).isEqualTo(UPDATED_TEST_END_DATE);
        assertThat(testCaseSample.getCaseNote()).isEqualTo(UPDATED_CASE_NOTE);
    }

    @Test
    @Transactional
    void putNonExistingCaseSample() throws Exception {
        int databaseSizeBeforeUpdate = caseSampleRepository.findAll().size();
        caseSample.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCaseSampleMockMvc
            .perform(
                put(ENTITY_API_URL_ID, caseSample.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(caseSample))
            )
            .andExpect(status().isBadRequest());

        // Validate the CaseSample in the database
        List<CaseSample> caseSampleList = caseSampleRepository.findAll();
        assertThat(caseSampleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCaseSample() throws Exception {
        int databaseSizeBeforeUpdate = caseSampleRepository.findAll().size();
        caseSample.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCaseSampleMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(caseSample))
            )
            .andExpect(status().isBadRequest());

        // Validate the CaseSample in the database
        List<CaseSample> caseSampleList = caseSampleRepository.findAll();
        assertThat(caseSampleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCaseSample() throws Exception {
        int databaseSizeBeforeUpdate = caseSampleRepository.findAll().size();
        caseSample.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCaseSampleMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(caseSample))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CaseSample in the database
        List<CaseSample> caseSampleList = caseSampleRepository.findAll();
        assertThat(caseSampleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCaseSampleWithPatch() throws Exception {
        // Initialize the database
        caseSampleRepository.saveAndFlush(caseSample);

        int databaseSizeBeforeUpdate = caseSampleRepository.findAll().size();

        // Update the caseSample using partial update
        CaseSample partialUpdatedCaseSample = new CaseSample();
        partialUpdatedCaseSample.setId(caseSample.getId());

        partialUpdatedCaseSample
            .fullNameEn(UPDATED_FULL_NAME_EN)
            .natEn(UPDATED_NAT_EN)
            .emiratesId(UPDATED_EMIRATES_ID)
            .gender(UPDATED_GENDER)
            .dateOfBirth(UPDATED_DATE_OF_BIRTH)
            .attachment(UPDATED_ATTACHMENT)
            .attachmentContentType(UPDATED_ATTACHMENT_CONTENT_TYPE)
            .caseType(UPDATED_CASE_TYPE);

        restCaseSampleMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCaseSample.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCaseSample))
            )
            .andExpect(status().isOk());

        // Validate the CaseSample in the database
        List<CaseSample> caseSampleList = caseSampleRepository.findAll();
        assertThat(caseSampleList).hasSize(databaseSizeBeforeUpdate);
        CaseSample testCaseSample = caseSampleList.get(caseSampleList.size() - 1);
        assertThat(testCaseSample.getSampleId()).isEqualTo(DEFAULT_SAMPLE_ID);
        assertThat(testCaseSample.getFullNameAr()).isEqualTo(DEFAULT_FULL_NAME_AR);
        assertThat(testCaseSample.getFullNameEn()).isEqualTo(UPDATED_FULL_NAME_EN);
        assertThat(testCaseSample.getNatAr()).isEqualTo(DEFAULT_NAT_AR);
        assertThat(testCaseSample.getNatEn()).isEqualTo(UPDATED_NAT_EN);
        assertThat(testCaseSample.getUid()).isEqualTo(DEFAULT_UID);
        assertThat(testCaseSample.getEmiratesId()).isEqualTo(UPDATED_EMIRATES_ID);
        assertThat(testCaseSample.getExhibit()).isEqualTo(DEFAULT_EXHIBIT);
        assertThat(testCaseSample.getGender()).isEqualTo(UPDATED_GENDER);
        assertThat(testCaseSample.getDateOfBirth()).isEqualTo(UPDATED_DATE_OF_BIRTH);
        assertThat(testCaseSample.getDueDate()).isEqualTo(DEFAULT_DUE_DATE);
        assertThat(testCaseSample.getRecievedDate()).isEqualTo(DEFAULT_RECIEVED_DATE);
        assertThat(testCaseSample.getSampleNotes()).isEqualTo(DEFAULT_SAMPLE_NOTES);
        assertThat(testCaseSample.getAddBy()).isEqualTo(DEFAULT_ADD_BY);
        assertThat(testCaseSample.getAddDate()).isEqualTo(DEFAULT_ADD_DATE);
        assertThat(testCaseSample.getUpdateBy()).isEqualTo(DEFAULT_UPDATE_BY);
        assertThat(testCaseSample.getUpdateDate()).isEqualTo(DEFAULT_UPDATE_DATE);
        assertThat(testCaseSample.getAttachment()).isEqualTo(UPDATED_ATTACHMENT);
        assertThat(testCaseSample.getAttachmentContentType()).isEqualTo(UPDATED_ATTACHMENT_CONTENT_TYPE);
        assertThat(testCaseSample.getCaseNumber()).isEqualTo(DEFAULT_CASE_NUMBER);
        assertThat(testCaseSample.getBarcodeNumber()).isEqualTo(DEFAULT_BARCODE_NUMBER);
        assertThat(testCaseSample.getCaseType()).isEqualTo(UPDATED_CASE_TYPE);
        assertThat(testCaseSample.getPoliceStation()).isEqualTo(DEFAULT_POLICE_STATION);
        assertThat(testCaseSample.getReportNumber()).isEqualTo(DEFAULT_REPORT_NUMBER);
        assertThat(testCaseSample.getTestEndDate()).isEqualTo(DEFAULT_TEST_END_DATE);
        assertThat(testCaseSample.getCaseNote()).isEqualTo(DEFAULT_CASE_NOTE);
    }

    @Test
    @Transactional
    void fullUpdateCaseSampleWithPatch() throws Exception {
        // Initialize the database
        caseSampleRepository.saveAndFlush(caseSample);

        int databaseSizeBeforeUpdate = caseSampleRepository.findAll().size();

        // Update the caseSample using partial update
        CaseSample partialUpdatedCaseSample = new CaseSample();
        partialUpdatedCaseSample.setId(caseSample.getId());

        partialUpdatedCaseSample
            .sampleId(UPDATED_SAMPLE_ID)
            .fullNameAr(UPDATED_FULL_NAME_AR)
            .fullNameEn(UPDATED_FULL_NAME_EN)
            .natAr(UPDATED_NAT_AR)
            .natEn(UPDATED_NAT_EN)
            .uid(UPDATED_UID)
            .emiratesId(UPDATED_EMIRATES_ID)
            .exhibit(UPDATED_EXHIBIT)
            .gender(UPDATED_GENDER)
            .dateOfBirth(UPDATED_DATE_OF_BIRTH)
            .dueDate(UPDATED_DUE_DATE)
            .recievedDate(UPDATED_RECIEVED_DATE)
            .sampleNotes(UPDATED_SAMPLE_NOTES)
            .addBy(UPDATED_ADD_BY)
            .addDate(UPDATED_ADD_DATE)
            .updateBy(UPDATED_UPDATE_BY)
            .updateDate(UPDATED_UPDATE_DATE)
            .attachment(UPDATED_ATTACHMENT)
            .attachmentContentType(UPDATED_ATTACHMENT_CONTENT_TYPE)
            .caseNumber(UPDATED_CASE_NUMBER)
            .barcodeNumber(UPDATED_BARCODE_NUMBER)
            .caseType(UPDATED_CASE_TYPE)
            .policeStation(UPDATED_POLICE_STATION)
            .reportNumber(UPDATED_REPORT_NUMBER)
            .testEndDate(UPDATED_TEST_END_DATE)
            .caseNote(UPDATED_CASE_NOTE);

        restCaseSampleMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCaseSample.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCaseSample))
            )
            .andExpect(status().isOk());

        // Validate the CaseSample in the database
        List<CaseSample> caseSampleList = caseSampleRepository.findAll();
        assertThat(caseSampleList).hasSize(databaseSizeBeforeUpdate);
        CaseSample testCaseSample = caseSampleList.get(caseSampleList.size() - 1);
        assertThat(testCaseSample.getSampleId()).isEqualTo(UPDATED_SAMPLE_ID);
        assertThat(testCaseSample.getFullNameAr()).isEqualTo(UPDATED_FULL_NAME_AR);
        assertThat(testCaseSample.getFullNameEn()).isEqualTo(UPDATED_FULL_NAME_EN);
        assertThat(testCaseSample.getNatAr()).isEqualTo(UPDATED_NAT_AR);
        assertThat(testCaseSample.getNatEn()).isEqualTo(UPDATED_NAT_EN);
        assertThat(testCaseSample.getUid()).isEqualTo(UPDATED_UID);
        assertThat(testCaseSample.getEmiratesId()).isEqualTo(UPDATED_EMIRATES_ID);
        assertThat(testCaseSample.getExhibit()).isEqualTo(UPDATED_EXHIBIT);
        assertThat(testCaseSample.getGender()).isEqualTo(UPDATED_GENDER);
        assertThat(testCaseSample.getDateOfBirth()).isEqualTo(UPDATED_DATE_OF_BIRTH);
        assertThat(testCaseSample.getDueDate()).isEqualTo(UPDATED_DUE_DATE);
        assertThat(testCaseSample.getRecievedDate()).isEqualTo(UPDATED_RECIEVED_DATE);
        assertThat(testCaseSample.getSampleNotes()).isEqualTo(UPDATED_SAMPLE_NOTES);
        assertThat(testCaseSample.getAddBy()).isEqualTo(UPDATED_ADD_BY);
        assertThat(testCaseSample.getAddDate()).isEqualTo(UPDATED_ADD_DATE);
        assertThat(testCaseSample.getUpdateBy()).isEqualTo(UPDATED_UPDATE_BY);
        assertThat(testCaseSample.getUpdateDate()).isEqualTo(UPDATED_UPDATE_DATE);
        assertThat(testCaseSample.getAttachment()).isEqualTo(UPDATED_ATTACHMENT);
        assertThat(testCaseSample.getAttachmentContentType()).isEqualTo(UPDATED_ATTACHMENT_CONTENT_TYPE);
        assertThat(testCaseSample.getCaseNumber()).isEqualTo(UPDATED_CASE_NUMBER);
        assertThat(testCaseSample.getBarcodeNumber()).isEqualTo(UPDATED_BARCODE_NUMBER);
        assertThat(testCaseSample.getCaseType()).isEqualTo(UPDATED_CASE_TYPE);
        assertThat(testCaseSample.getPoliceStation()).isEqualTo(UPDATED_POLICE_STATION);
        assertThat(testCaseSample.getReportNumber()).isEqualTo(UPDATED_REPORT_NUMBER);
        assertThat(testCaseSample.getTestEndDate()).isEqualTo(UPDATED_TEST_END_DATE);
        assertThat(testCaseSample.getCaseNote()).isEqualTo(UPDATED_CASE_NOTE);
    }

    @Test
    @Transactional
    void patchNonExistingCaseSample() throws Exception {
        int databaseSizeBeforeUpdate = caseSampleRepository.findAll().size();
        caseSample.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCaseSampleMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, caseSample.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(caseSample))
            )
            .andExpect(status().isBadRequest());

        // Validate the CaseSample in the database
        List<CaseSample> caseSampleList = caseSampleRepository.findAll();
        assertThat(caseSampleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCaseSample() throws Exception {
        int databaseSizeBeforeUpdate = caseSampleRepository.findAll().size();
        caseSample.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCaseSampleMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(caseSample))
            )
            .andExpect(status().isBadRequest());

        // Validate the CaseSample in the database
        List<CaseSample> caseSampleList = caseSampleRepository.findAll();
        assertThat(caseSampleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCaseSample() throws Exception {
        int databaseSizeBeforeUpdate = caseSampleRepository.findAll().size();
        caseSample.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCaseSampleMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(caseSample))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CaseSample in the database
        List<CaseSample> caseSampleList = caseSampleRepository.findAll();
        assertThat(caseSampleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCaseSample() throws Exception {
        // Initialize the database
        caseSampleRepository.saveAndFlush(caseSample);

        int databaseSizeBeforeDelete = caseSampleRepository.findAll().size();

        // Delete the caseSample
        restCaseSampleMockMvc
            .perform(delete(ENTITY_API_URL_ID, caseSample.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CaseSample> caseSampleList = caseSampleRepository.findAll();
        assertThat(caseSampleList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
