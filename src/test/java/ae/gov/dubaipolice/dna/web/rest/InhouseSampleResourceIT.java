package ae.gov.dubaipolice.dna.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import ae.gov.dubaipolice.dna.IntegrationTest;
import ae.gov.dubaipolice.dna.domain.InhouseSample;
import ae.gov.dubaipolice.dna.repository.InhouseSampleRepository;
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
 * Integration tests for the {@link InhouseSampleResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class InhouseSampleResourceIT {

    private static final Long DEFAULT_SAMPLE_ID = 1L;
    private static final Long UPDATED_SAMPLE_ID = 2L;

    private static final Long DEFAULT_EMP_GRP_NO = 1L;
    private static final Long UPDATED_EMP_GRP_NO = 2L;

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

    private static final LocalDate DEFAULT_BATCH_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_BATCH_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_COLLECTION_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_COLLECTION_DATE = LocalDate.now(ZoneId.systemDefault());

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

    private static final String ENTITY_API_URL = "/api/inhouse-samples";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private InhouseSampleRepository inhouseSampleRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restInhouseSampleMockMvc;

    private InhouseSample inhouseSample;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static InhouseSample createEntity(EntityManager em) {
        InhouseSample inhouseSample = new InhouseSample()
            .sampleId(DEFAULT_SAMPLE_ID)
            .empGrpNo(DEFAULT_EMP_GRP_NO)
            .fullNameAr(DEFAULT_FULL_NAME_AR)
            .fullNameEn(DEFAULT_FULL_NAME_EN)
            .natAr(DEFAULT_NAT_AR)
            .natEn(DEFAULT_NAT_EN)
            .uid(DEFAULT_UID)
            .emiratesId(DEFAULT_EMIRATES_ID)
            .exhibit(DEFAULT_EXHIBIT)
            .gender(DEFAULT_GENDER)
            .dateOfBirth(DEFAULT_DATE_OF_BIRTH)
            .batchDate(DEFAULT_BATCH_DATE)
            .collectionDate(DEFAULT_COLLECTION_DATE)
            .sampleNotes(DEFAULT_SAMPLE_NOTES)
            .addBy(DEFAULT_ADD_BY)
            .addDate(DEFAULT_ADD_DATE)
            .updateBy(DEFAULT_UPDATE_BY)
            .updateDate(DEFAULT_UPDATE_DATE)
            .attachment(DEFAULT_ATTACHMENT)
            .attachmentContentType(DEFAULT_ATTACHMENT_CONTENT_TYPE);
        return inhouseSample;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static InhouseSample createUpdatedEntity(EntityManager em) {
        InhouseSample inhouseSample = new InhouseSample()
            .sampleId(UPDATED_SAMPLE_ID)
            .empGrpNo(UPDATED_EMP_GRP_NO)
            .fullNameAr(UPDATED_FULL_NAME_AR)
            .fullNameEn(UPDATED_FULL_NAME_EN)
            .natAr(UPDATED_NAT_AR)
            .natEn(UPDATED_NAT_EN)
            .uid(UPDATED_UID)
            .emiratesId(UPDATED_EMIRATES_ID)
            .exhibit(UPDATED_EXHIBIT)
            .gender(UPDATED_GENDER)
            .dateOfBirth(UPDATED_DATE_OF_BIRTH)
            .batchDate(UPDATED_BATCH_DATE)
            .collectionDate(UPDATED_COLLECTION_DATE)
            .sampleNotes(UPDATED_SAMPLE_NOTES)
            .addBy(UPDATED_ADD_BY)
            .addDate(UPDATED_ADD_DATE)
            .updateBy(UPDATED_UPDATE_BY)
            .updateDate(UPDATED_UPDATE_DATE)
            .attachment(UPDATED_ATTACHMENT)
            .attachmentContentType(UPDATED_ATTACHMENT_CONTENT_TYPE);
        return inhouseSample;
    }

    @BeforeEach
    public void initTest() {
        inhouseSample = createEntity(em);
    }

    @Test
    @Transactional
    void createInhouseSample() throws Exception {
        int databaseSizeBeforeCreate = inhouseSampleRepository.findAll().size();
        // Create the InhouseSample
        restInhouseSampleMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(inhouseSample))
            )
            .andExpect(status().isCreated());

        // Validate the InhouseSample in the database
        List<InhouseSample> inhouseSampleList = inhouseSampleRepository.findAll();
        assertThat(inhouseSampleList).hasSize(databaseSizeBeforeCreate + 1);
        InhouseSample testInhouseSample = inhouseSampleList.get(inhouseSampleList.size() - 1);
        assertThat(testInhouseSample.getSampleId()).isEqualTo(DEFAULT_SAMPLE_ID);
        assertThat(testInhouseSample.getEmpGrpNo()).isEqualTo(DEFAULT_EMP_GRP_NO);
        assertThat(testInhouseSample.getFullNameAr()).isEqualTo(DEFAULT_FULL_NAME_AR);
        assertThat(testInhouseSample.getFullNameEn()).isEqualTo(DEFAULT_FULL_NAME_EN);
        assertThat(testInhouseSample.getNatAr()).isEqualTo(DEFAULT_NAT_AR);
        assertThat(testInhouseSample.getNatEn()).isEqualTo(DEFAULT_NAT_EN);
        assertThat(testInhouseSample.getUid()).isEqualTo(DEFAULT_UID);
        assertThat(testInhouseSample.getEmiratesId()).isEqualTo(DEFAULT_EMIRATES_ID);
        assertThat(testInhouseSample.getExhibit()).isEqualTo(DEFAULT_EXHIBIT);
        assertThat(testInhouseSample.getGender()).isEqualTo(DEFAULT_GENDER);
        assertThat(testInhouseSample.getDateOfBirth()).isEqualTo(DEFAULT_DATE_OF_BIRTH);
        assertThat(testInhouseSample.getBatchDate()).isEqualTo(DEFAULT_BATCH_DATE);
        assertThat(testInhouseSample.getCollectionDate()).isEqualTo(DEFAULT_COLLECTION_DATE);
        assertThat(testInhouseSample.getSampleNotes()).isEqualTo(DEFAULT_SAMPLE_NOTES);
        assertThat(testInhouseSample.getAddBy()).isEqualTo(DEFAULT_ADD_BY);
        assertThat(testInhouseSample.getAddDate()).isEqualTo(DEFAULT_ADD_DATE);
        assertThat(testInhouseSample.getUpdateBy()).isEqualTo(DEFAULT_UPDATE_BY);
        assertThat(testInhouseSample.getUpdateDate()).isEqualTo(DEFAULT_UPDATE_DATE);
        assertThat(testInhouseSample.getAttachment()).isEqualTo(DEFAULT_ATTACHMENT);
        assertThat(testInhouseSample.getAttachmentContentType()).isEqualTo(DEFAULT_ATTACHMENT_CONTENT_TYPE);
    }

    @Test
    @Transactional
    void createInhouseSampleWithExistingId() throws Exception {
        // Create the InhouseSample with an existing ID
        inhouseSample.setId(1L);

        int databaseSizeBeforeCreate = inhouseSampleRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restInhouseSampleMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(inhouseSample))
            )
            .andExpect(status().isBadRequest());

        // Validate the InhouseSample in the database
        List<InhouseSample> inhouseSampleList = inhouseSampleRepository.findAll();
        assertThat(inhouseSampleList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllInhouseSamples() throws Exception {
        // Initialize the database
        inhouseSampleRepository.saveAndFlush(inhouseSample);

        // Get all the inhouseSampleList
        restInhouseSampleMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(inhouseSample.getId().intValue())))
            .andExpect(jsonPath("$.[*].sampleId").value(hasItem(DEFAULT_SAMPLE_ID.intValue())))
            .andExpect(jsonPath("$.[*].empGrpNo").value(hasItem(DEFAULT_EMP_GRP_NO.intValue())))
            .andExpect(jsonPath("$.[*].fullNameAr").value(hasItem(DEFAULT_FULL_NAME_AR)))
            .andExpect(jsonPath("$.[*].fullNameEn").value(hasItem(DEFAULT_FULL_NAME_EN)))
            .andExpect(jsonPath("$.[*].natAr").value(hasItem(DEFAULT_NAT_AR)))
            .andExpect(jsonPath("$.[*].natEn").value(hasItem(DEFAULT_NAT_EN)))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID)))
            .andExpect(jsonPath("$.[*].emiratesId").value(hasItem(DEFAULT_EMIRATES_ID)))
            .andExpect(jsonPath("$.[*].exhibit").value(hasItem(DEFAULT_EXHIBIT)))
            .andExpect(jsonPath("$.[*].gender").value(hasItem(DEFAULT_GENDER)))
            .andExpect(jsonPath("$.[*].dateOfBirth").value(hasItem(DEFAULT_DATE_OF_BIRTH.toString())))
            .andExpect(jsonPath("$.[*].batchDate").value(hasItem(DEFAULT_BATCH_DATE.toString())))
            .andExpect(jsonPath("$.[*].collectionDate").value(hasItem(DEFAULT_COLLECTION_DATE.toString())))
            .andExpect(jsonPath("$.[*].sampleNotes").value(hasItem(DEFAULT_SAMPLE_NOTES)))
            .andExpect(jsonPath("$.[*].addBy").value(hasItem(DEFAULT_ADD_BY.intValue())))
            .andExpect(jsonPath("$.[*].addDate").value(hasItem(DEFAULT_ADD_DATE.toString())))
            .andExpect(jsonPath("$.[*].updateBy").value(hasItem(DEFAULT_UPDATE_BY.intValue())))
            .andExpect(jsonPath("$.[*].updateDate").value(hasItem(DEFAULT_UPDATE_DATE.toString())))
            .andExpect(jsonPath("$.[*].attachmentContentType").value(hasItem(DEFAULT_ATTACHMENT_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].attachment").value(hasItem(Base64Utils.encodeToString(DEFAULT_ATTACHMENT))));
    }

    @Test
    @Transactional
    void getInhouseSample() throws Exception {
        // Initialize the database
        inhouseSampleRepository.saveAndFlush(inhouseSample);

        // Get the inhouseSample
        restInhouseSampleMockMvc
            .perform(get(ENTITY_API_URL_ID, inhouseSample.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(inhouseSample.getId().intValue()))
            .andExpect(jsonPath("$.sampleId").value(DEFAULT_SAMPLE_ID.intValue()))
            .andExpect(jsonPath("$.empGrpNo").value(DEFAULT_EMP_GRP_NO.intValue()))
            .andExpect(jsonPath("$.fullNameAr").value(DEFAULT_FULL_NAME_AR))
            .andExpect(jsonPath("$.fullNameEn").value(DEFAULT_FULL_NAME_EN))
            .andExpect(jsonPath("$.natAr").value(DEFAULT_NAT_AR))
            .andExpect(jsonPath("$.natEn").value(DEFAULT_NAT_EN))
            .andExpect(jsonPath("$.uid").value(DEFAULT_UID))
            .andExpect(jsonPath("$.emiratesId").value(DEFAULT_EMIRATES_ID))
            .andExpect(jsonPath("$.exhibit").value(DEFAULT_EXHIBIT))
            .andExpect(jsonPath("$.gender").value(DEFAULT_GENDER))
            .andExpect(jsonPath("$.dateOfBirth").value(DEFAULT_DATE_OF_BIRTH.toString()))
            .andExpect(jsonPath("$.batchDate").value(DEFAULT_BATCH_DATE.toString()))
            .andExpect(jsonPath("$.collectionDate").value(DEFAULT_COLLECTION_DATE.toString()))
            .andExpect(jsonPath("$.sampleNotes").value(DEFAULT_SAMPLE_NOTES))
            .andExpect(jsonPath("$.addBy").value(DEFAULT_ADD_BY.intValue()))
            .andExpect(jsonPath("$.addDate").value(DEFAULT_ADD_DATE.toString()))
            .andExpect(jsonPath("$.updateBy").value(DEFAULT_UPDATE_BY.intValue()))
            .andExpect(jsonPath("$.updateDate").value(DEFAULT_UPDATE_DATE.toString()))
            .andExpect(jsonPath("$.attachmentContentType").value(DEFAULT_ATTACHMENT_CONTENT_TYPE))
            .andExpect(jsonPath("$.attachment").value(Base64Utils.encodeToString(DEFAULT_ATTACHMENT)));
    }

    @Test
    @Transactional
    void getNonExistingInhouseSample() throws Exception {
        // Get the inhouseSample
        restInhouseSampleMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewInhouseSample() throws Exception {
        // Initialize the database
        inhouseSampleRepository.saveAndFlush(inhouseSample);

        int databaseSizeBeforeUpdate = inhouseSampleRepository.findAll().size();

        // Update the inhouseSample
        InhouseSample updatedInhouseSample = inhouseSampleRepository.findById(inhouseSample.getId()).get();
        // Disconnect from session so that the updates on updatedInhouseSample are not directly saved in db
        em.detach(updatedInhouseSample);
        updatedInhouseSample
            .sampleId(UPDATED_SAMPLE_ID)
            .empGrpNo(UPDATED_EMP_GRP_NO)
            .fullNameAr(UPDATED_FULL_NAME_AR)
            .fullNameEn(UPDATED_FULL_NAME_EN)
            .natAr(UPDATED_NAT_AR)
            .natEn(UPDATED_NAT_EN)
            .uid(UPDATED_UID)
            .emiratesId(UPDATED_EMIRATES_ID)
            .exhibit(UPDATED_EXHIBIT)
            .gender(UPDATED_GENDER)
            .dateOfBirth(UPDATED_DATE_OF_BIRTH)
            .batchDate(UPDATED_BATCH_DATE)
            .collectionDate(UPDATED_COLLECTION_DATE)
            .sampleNotes(UPDATED_SAMPLE_NOTES)
            .addBy(UPDATED_ADD_BY)
            .addDate(UPDATED_ADD_DATE)
            .updateBy(UPDATED_UPDATE_BY)
            .updateDate(UPDATED_UPDATE_DATE)
            .attachment(UPDATED_ATTACHMENT)
            .attachmentContentType(UPDATED_ATTACHMENT_CONTENT_TYPE);

        restInhouseSampleMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedInhouseSample.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedInhouseSample))
            )
            .andExpect(status().isOk());

        // Validate the InhouseSample in the database
        List<InhouseSample> inhouseSampleList = inhouseSampleRepository.findAll();
        assertThat(inhouseSampleList).hasSize(databaseSizeBeforeUpdate);
        InhouseSample testInhouseSample = inhouseSampleList.get(inhouseSampleList.size() - 1);
        assertThat(testInhouseSample.getSampleId()).isEqualTo(UPDATED_SAMPLE_ID);
        assertThat(testInhouseSample.getEmpGrpNo()).isEqualTo(UPDATED_EMP_GRP_NO);
        assertThat(testInhouseSample.getFullNameAr()).isEqualTo(UPDATED_FULL_NAME_AR);
        assertThat(testInhouseSample.getFullNameEn()).isEqualTo(UPDATED_FULL_NAME_EN);
        assertThat(testInhouseSample.getNatAr()).isEqualTo(UPDATED_NAT_AR);
        assertThat(testInhouseSample.getNatEn()).isEqualTo(UPDATED_NAT_EN);
        assertThat(testInhouseSample.getUid()).isEqualTo(UPDATED_UID);
        assertThat(testInhouseSample.getEmiratesId()).isEqualTo(UPDATED_EMIRATES_ID);
        assertThat(testInhouseSample.getExhibit()).isEqualTo(UPDATED_EXHIBIT);
        assertThat(testInhouseSample.getGender()).isEqualTo(UPDATED_GENDER);
        assertThat(testInhouseSample.getDateOfBirth()).isEqualTo(UPDATED_DATE_OF_BIRTH);
        assertThat(testInhouseSample.getBatchDate()).isEqualTo(UPDATED_BATCH_DATE);
        assertThat(testInhouseSample.getCollectionDate()).isEqualTo(UPDATED_COLLECTION_DATE);
        assertThat(testInhouseSample.getSampleNotes()).isEqualTo(UPDATED_SAMPLE_NOTES);
        assertThat(testInhouseSample.getAddBy()).isEqualTo(UPDATED_ADD_BY);
        assertThat(testInhouseSample.getAddDate()).isEqualTo(UPDATED_ADD_DATE);
        assertThat(testInhouseSample.getUpdateBy()).isEqualTo(UPDATED_UPDATE_BY);
        assertThat(testInhouseSample.getUpdateDate()).isEqualTo(UPDATED_UPDATE_DATE);
        assertThat(testInhouseSample.getAttachment()).isEqualTo(UPDATED_ATTACHMENT);
        assertThat(testInhouseSample.getAttachmentContentType()).isEqualTo(UPDATED_ATTACHMENT_CONTENT_TYPE);
    }

    @Test
    @Transactional
    void putNonExistingInhouseSample() throws Exception {
        int databaseSizeBeforeUpdate = inhouseSampleRepository.findAll().size();
        inhouseSample.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restInhouseSampleMockMvc
            .perform(
                put(ENTITY_API_URL_ID, inhouseSample.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(inhouseSample))
            )
            .andExpect(status().isBadRequest());

        // Validate the InhouseSample in the database
        List<InhouseSample> inhouseSampleList = inhouseSampleRepository.findAll();
        assertThat(inhouseSampleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchInhouseSample() throws Exception {
        int databaseSizeBeforeUpdate = inhouseSampleRepository.findAll().size();
        inhouseSample.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restInhouseSampleMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(inhouseSample))
            )
            .andExpect(status().isBadRequest());

        // Validate the InhouseSample in the database
        List<InhouseSample> inhouseSampleList = inhouseSampleRepository.findAll();
        assertThat(inhouseSampleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamInhouseSample() throws Exception {
        int databaseSizeBeforeUpdate = inhouseSampleRepository.findAll().size();
        inhouseSample.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restInhouseSampleMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(inhouseSample))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the InhouseSample in the database
        List<InhouseSample> inhouseSampleList = inhouseSampleRepository.findAll();
        assertThat(inhouseSampleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateInhouseSampleWithPatch() throws Exception {
        // Initialize the database
        inhouseSampleRepository.saveAndFlush(inhouseSample);

        int databaseSizeBeforeUpdate = inhouseSampleRepository.findAll().size();

        // Update the inhouseSample using partial update
        InhouseSample partialUpdatedInhouseSample = new InhouseSample();
        partialUpdatedInhouseSample.setId(inhouseSample.getId());

        partialUpdatedInhouseSample
            .empGrpNo(UPDATED_EMP_GRP_NO)
            .fullNameAr(UPDATED_FULL_NAME_AR)
            .fullNameEn(UPDATED_FULL_NAME_EN)
            .uid(UPDATED_UID)
            .emiratesId(UPDATED_EMIRATES_ID)
            .exhibit(UPDATED_EXHIBIT)
            .gender(UPDATED_GENDER)
            .dateOfBirth(UPDATED_DATE_OF_BIRTH)
            .updateBy(UPDATED_UPDATE_BY)
            .attachment(UPDATED_ATTACHMENT)
            .attachmentContentType(UPDATED_ATTACHMENT_CONTENT_TYPE);

        restInhouseSampleMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedInhouseSample.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedInhouseSample))
            )
            .andExpect(status().isOk());

        // Validate the InhouseSample in the database
        List<InhouseSample> inhouseSampleList = inhouseSampleRepository.findAll();
        assertThat(inhouseSampleList).hasSize(databaseSizeBeforeUpdate);
        InhouseSample testInhouseSample = inhouseSampleList.get(inhouseSampleList.size() - 1);
        assertThat(testInhouseSample.getSampleId()).isEqualTo(DEFAULT_SAMPLE_ID);
        assertThat(testInhouseSample.getEmpGrpNo()).isEqualTo(UPDATED_EMP_GRP_NO);
        assertThat(testInhouseSample.getFullNameAr()).isEqualTo(UPDATED_FULL_NAME_AR);
        assertThat(testInhouseSample.getFullNameEn()).isEqualTo(UPDATED_FULL_NAME_EN);
        assertThat(testInhouseSample.getNatAr()).isEqualTo(DEFAULT_NAT_AR);
        assertThat(testInhouseSample.getNatEn()).isEqualTo(DEFAULT_NAT_EN);
        assertThat(testInhouseSample.getUid()).isEqualTo(UPDATED_UID);
        assertThat(testInhouseSample.getEmiratesId()).isEqualTo(UPDATED_EMIRATES_ID);
        assertThat(testInhouseSample.getExhibit()).isEqualTo(UPDATED_EXHIBIT);
        assertThat(testInhouseSample.getGender()).isEqualTo(UPDATED_GENDER);
        assertThat(testInhouseSample.getDateOfBirth()).isEqualTo(UPDATED_DATE_OF_BIRTH);
        assertThat(testInhouseSample.getBatchDate()).isEqualTo(DEFAULT_BATCH_DATE);
        assertThat(testInhouseSample.getCollectionDate()).isEqualTo(DEFAULT_COLLECTION_DATE);
        assertThat(testInhouseSample.getSampleNotes()).isEqualTo(DEFAULT_SAMPLE_NOTES);
        assertThat(testInhouseSample.getAddBy()).isEqualTo(DEFAULT_ADD_BY);
        assertThat(testInhouseSample.getAddDate()).isEqualTo(DEFAULT_ADD_DATE);
        assertThat(testInhouseSample.getUpdateBy()).isEqualTo(UPDATED_UPDATE_BY);
        assertThat(testInhouseSample.getUpdateDate()).isEqualTo(DEFAULT_UPDATE_DATE);
        assertThat(testInhouseSample.getAttachment()).isEqualTo(UPDATED_ATTACHMENT);
        assertThat(testInhouseSample.getAttachmentContentType()).isEqualTo(UPDATED_ATTACHMENT_CONTENT_TYPE);
    }

    @Test
    @Transactional
    void fullUpdateInhouseSampleWithPatch() throws Exception {
        // Initialize the database
        inhouseSampleRepository.saveAndFlush(inhouseSample);

        int databaseSizeBeforeUpdate = inhouseSampleRepository.findAll().size();

        // Update the inhouseSample using partial update
        InhouseSample partialUpdatedInhouseSample = new InhouseSample();
        partialUpdatedInhouseSample.setId(inhouseSample.getId());

        partialUpdatedInhouseSample
            .sampleId(UPDATED_SAMPLE_ID)
            .empGrpNo(UPDATED_EMP_GRP_NO)
            .fullNameAr(UPDATED_FULL_NAME_AR)
            .fullNameEn(UPDATED_FULL_NAME_EN)
            .natAr(UPDATED_NAT_AR)
            .natEn(UPDATED_NAT_EN)
            .uid(UPDATED_UID)
            .emiratesId(UPDATED_EMIRATES_ID)
            .exhibit(UPDATED_EXHIBIT)
            .gender(UPDATED_GENDER)
            .dateOfBirth(UPDATED_DATE_OF_BIRTH)
            .batchDate(UPDATED_BATCH_DATE)
            .collectionDate(UPDATED_COLLECTION_DATE)
            .sampleNotes(UPDATED_SAMPLE_NOTES)
            .addBy(UPDATED_ADD_BY)
            .addDate(UPDATED_ADD_DATE)
            .updateBy(UPDATED_UPDATE_BY)
            .updateDate(UPDATED_UPDATE_DATE)
            .attachment(UPDATED_ATTACHMENT)
            .attachmentContentType(UPDATED_ATTACHMENT_CONTENT_TYPE);

        restInhouseSampleMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedInhouseSample.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedInhouseSample))
            )
            .andExpect(status().isOk());

        // Validate the InhouseSample in the database
        List<InhouseSample> inhouseSampleList = inhouseSampleRepository.findAll();
        assertThat(inhouseSampleList).hasSize(databaseSizeBeforeUpdate);
        InhouseSample testInhouseSample = inhouseSampleList.get(inhouseSampleList.size() - 1);
        assertThat(testInhouseSample.getSampleId()).isEqualTo(UPDATED_SAMPLE_ID);
        assertThat(testInhouseSample.getEmpGrpNo()).isEqualTo(UPDATED_EMP_GRP_NO);
        assertThat(testInhouseSample.getFullNameAr()).isEqualTo(UPDATED_FULL_NAME_AR);
        assertThat(testInhouseSample.getFullNameEn()).isEqualTo(UPDATED_FULL_NAME_EN);
        assertThat(testInhouseSample.getNatAr()).isEqualTo(UPDATED_NAT_AR);
        assertThat(testInhouseSample.getNatEn()).isEqualTo(UPDATED_NAT_EN);
        assertThat(testInhouseSample.getUid()).isEqualTo(UPDATED_UID);
        assertThat(testInhouseSample.getEmiratesId()).isEqualTo(UPDATED_EMIRATES_ID);
        assertThat(testInhouseSample.getExhibit()).isEqualTo(UPDATED_EXHIBIT);
        assertThat(testInhouseSample.getGender()).isEqualTo(UPDATED_GENDER);
        assertThat(testInhouseSample.getDateOfBirth()).isEqualTo(UPDATED_DATE_OF_BIRTH);
        assertThat(testInhouseSample.getBatchDate()).isEqualTo(UPDATED_BATCH_DATE);
        assertThat(testInhouseSample.getCollectionDate()).isEqualTo(UPDATED_COLLECTION_DATE);
        assertThat(testInhouseSample.getSampleNotes()).isEqualTo(UPDATED_SAMPLE_NOTES);
        assertThat(testInhouseSample.getAddBy()).isEqualTo(UPDATED_ADD_BY);
        assertThat(testInhouseSample.getAddDate()).isEqualTo(UPDATED_ADD_DATE);
        assertThat(testInhouseSample.getUpdateBy()).isEqualTo(UPDATED_UPDATE_BY);
        assertThat(testInhouseSample.getUpdateDate()).isEqualTo(UPDATED_UPDATE_DATE);
        assertThat(testInhouseSample.getAttachment()).isEqualTo(UPDATED_ATTACHMENT);
        assertThat(testInhouseSample.getAttachmentContentType()).isEqualTo(UPDATED_ATTACHMENT_CONTENT_TYPE);
    }

    @Test
    @Transactional
    void patchNonExistingInhouseSample() throws Exception {
        int databaseSizeBeforeUpdate = inhouseSampleRepository.findAll().size();
        inhouseSample.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restInhouseSampleMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, inhouseSample.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(inhouseSample))
            )
            .andExpect(status().isBadRequest());

        // Validate the InhouseSample in the database
        List<InhouseSample> inhouseSampleList = inhouseSampleRepository.findAll();
        assertThat(inhouseSampleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchInhouseSample() throws Exception {
        int databaseSizeBeforeUpdate = inhouseSampleRepository.findAll().size();
        inhouseSample.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restInhouseSampleMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(inhouseSample))
            )
            .andExpect(status().isBadRequest());

        // Validate the InhouseSample in the database
        List<InhouseSample> inhouseSampleList = inhouseSampleRepository.findAll();
        assertThat(inhouseSampleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamInhouseSample() throws Exception {
        int databaseSizeBeforeUpdate = inhouseSampleRepository.findAll().size();
        inhouseSample.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restInhouseSampleMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(inhouseSample))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the InhouseSample in the database
        List<InhouseSample> inhouseSampleList = inhouseSampleRepository.findAll();
        assertThat(inhouseSampleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteInhouseSample() throws Exception {
        // Initialize the database
        inhouseSampleRepository.saveAndFlush(inhouseSample);

        int databaseSizeBeforeDelete = inhouseSampleRepository.findAll().size();

        // Delete the inhouseSample
        restInhouseSampleMockMvc
            .perform(delete(ENTITY_API_URL_ID, inhouseSample.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<InhouseSample> inhouseSampleList = inhouseSampleRepository.findAll();
        assertThat(inhouseSampleList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
