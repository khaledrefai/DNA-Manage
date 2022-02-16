package ae.gov.dubaipolice.dna.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import ae.gov.dubaipolice.dna.IntegrationTest;
import ae.gov.dubaipolice.dna.domain.MemberData;
import ae.gov.dubaipolice.dna.repository.MemberDataRepository;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
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

/**
 * Integration tests for the {@link MemberDataResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class MemberDataResourceIT {

    private static final Instant DEFAULT_TEST_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_TEST_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Integer DEFAULT_USER_ID = 1;
    private static final Integer UPDATED_USER_ID = 2;

    private static final String DEFAULT_USER_NAME = "AAAAAAAAAA";
    private static final String UPDATED_USER_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_USER_GENDER = "AAAAAAAAAA";
    private static final String UPDATED_USER_GENDER = "BBBBBBBBBB";

    private static final String DEFAULT_USER_BIRTHDAY = "AAAAAAAAAA";
    private static final String UPDATED_USER_BIRTHDAY = "BBBBBBBBBB";

    private static final Integer DEFAULT_USER_AGE = 1;
    private static final Integer UPDATED_USER_AGE = 2;

    private static final Float DEFAULT_USER_HEIGHT = 1F;
    private static final Float UPDATED_USER_HEIGHT = 2F;

    private static final Instant DEFAULT_ORDER_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_ORDER_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/member-data";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private MemberDataRepository memberDataRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMemberDataMockMvc;

    private MemberData memberData;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MemberData createEntity(EntityManager em) {
        MemberData memberData = new MemberData()
            .testDate(DEFAULT_TEST_DATE)
            .userId(DEFAULT_USER_ID)
            .userName(DEFAULT_USER_NAME)
            .userGender(DEFAULT_USER_GENDER)
            .userBirthday(DEFAULT_USER_BIRTHDAY)
            .userAge(DEFAULT_USER_AGE)
            .userHeight(DEFAULT_USER_HEIGHT)
            .orderDate(DEFAULT_ORDER_DATE);
        return memberData;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MemberData createUpdatedEntity(EntityManager em) {
        MemberData memberData = new MemberData()
            .testDate(UPDATED_TEST_DATE)
            .userId(UPDATED_USER_ID)
            .userName(UPDATED_USER_NAME)
            .userGender(UPDATED_USER_GENDER)
            .userBirthday(UPDATED_USER_BIRTHDAY)
            .userAge(UPDATED_USER_AGE)
            .userHeight(UPDATED_USER_HEIGHT)
            .orderDate(UPDATED_ORDER_DATE);
        return memberData;
    }

    @BeforeEach
    public void initTest() {
        memberData = createEntity(em);
    }

    @Test
    @Transactional
    void createMemberData() throws Exception {
        int databaseSizeBeforeCreate = memberDataRepository.findAll().size();
        // Create the MemberData
        restMemberDataMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(memberData))
            )
            .andExpect(status().isCreated());

        // Validate the MemberData in the database
        List<MemberData> memberDataList = memberDataRepository.findAll();
        assertThat(memberDataList).hasSize(databaseSizeBeforeCreate + 1);
        MemberData testMemberData = memberDataList.get(memberDataList.size() - 1);
        assertThat(testMemberData.getTestDate()).isEqualTo(DEFAULT_TEST_DATE);
        assertThat(testMemberData.getUserId()).isEqualTo(DEFAULT_USER_ID);
        assertThat(testMemberData.getUserName()).isEqualTo(DEFAULT_USER_NAME);
        assertThat(testMemberData.getUserGender()).isEqualTo(DEFAULT_USER_GENDER);
        assertThat(testMemberData.getUserBirthday()).isEqualTo(DEFAULT_USER_BIRTHDAY);
        assertThat(testMemberData.getUserAge()).isEqualTo(DEFAULT_USER_AGE);
        assertThat(testMemberData.getUserHeight()).isEqualTo(DEFAULT_USER_HEIGHT);
        assertThat(testMemberData.getOrderDate()).isEqualTo(DEFAULT_ORDER_DATE);
    }

    @Test
    @Transactional
    void createMemberDataWithExistingId() throws Exception {
        // Create the MemberData with an existing ID
        memberData.setId(1L);

        int databaseSizeBeforeCreate = memberDataRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restMemberDataMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(memberData))
            )
            .andExpect(status().isBadRequest());

        // Validate the MemberData in the database
        List<MemberData> memberDataList = memberDataRepository.findAll();
        assertThat(memberDataList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllMemberData() throws Exception {
        // Initialize the database
        memberDataRepository.saveAndFlush(memberData);

        // Get all the memberDataList
        restMemberDataMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(memberData.getId().intValue())))
            .andExpect(jsonPath("$.[*].testDate").value(hasItem(DEFAULT_TEST_DATE.toString())))
            .andExpect(jsonPath("$.[*].userId").value(hasItem(DEFAULT_USER_ID)))
            .andExpect(jsonPath("$.[*].userName").value(hasItem(DEFAULT_USER_NAME)))
            .andExpect(jsonPath("$.[*].userGender").value(hasItem(DEFAULT_USER_GENDER)))
            .andExpect(jsonPath("$.[*].userBirthday").value(hasItem(DEFAULT_USER_BIRTHDAY)))
            .andExpect(jsonPath("$.[*].userAge").value(hasItem(DEFAULT_USER_AGE)))
            .andExpect(jsonPath("$.[*].userHeight").value(hasItem(DEFAULT_USER_HEIGHT.doubleValue())))
            .andExpect(jsonPath("$.[*].orderDate").value(hasItem(DEFAULT_ORDER_DATE.toString())));
    }

    @Test
    @Transactional
    void getMemberData() throws Exception {
        // Initialize the database
        memberDataRepository.saveAndFlush(memberData);

        // Get the memberData
        restMemberDataMockMvc
            .perform(get(ENTITY_API_URL_ID, memberData.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(memberData.getId().intValue()))
            .andExpect(jsonPath("$.testDate").value(DEFAULT_TEST_DATE.toString()))
            .andExpect(jsonPath("$.userId").value(DEFAULT_USER_ID))
            .andExpect(jsonPath("$.userName").value(DEFAULT_USER_NAME))
            .andExpect(jsonPath("$.userGender").value(DEFAULT_USER_GENDER))
            .andExpect(jsonPath("$.userBirthday").value(DEFAULT_USER_BIRTHDAY))
            .andExpect(jsonPath("$.userAge").value(DEFAULT_USER_AGE))
            .andExpect(jsonPath("$.userHeight").value(DEFAULT_USER_HEIGHT.doubleValue()))
            .andExpect(jsonPath("$.orderDate").value(DEFAULT_ORDER_DATE.toString()));
    }

    @Test
    @Transactional
    void getNonExistingMemberData() throws Exception {
        // Get the memberData
        restMemberDataMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewMemberData() throws Exception {
        // Initialize the database
        memberDataRepository.saveAndFlush(memberData);

        int databaseSizeBeforeUpdate = memberDataRepository.findAll().size();

        // Update the memberData
        MemberData updatedMemberData = memberDataRepository.findById(memberData.getId()).get();
        // Disconnect from session so that the updates on updatedMemberData are not directly saved in db
        em.detach(updatedMemberData);
        updatedMemberData
            .testDate(UPDATED_TEST_DATE)
            .userId(UPDATED_USER_ID)
            .userName(UPDATED_USER_NAME)
            .userGender(UPDATED_USER_GENDER)
            .userBirthday(UPDATED_USER_BIRTHDAY)
            .userAge(UPDATED_USER_AGE)
            .userHeight(UPDATED_USER_HEIGHT)
            .orderDate(UPDATED_ORDER_DATE);

        restMemberDataMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedMemberData.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedMemberData))
            )
            .andExpect(status().isOk());

        // Validate the MemberData in the database
        List<MemberData> memberDataList = memberDataRepository.findAll();
        assertThat(memberDataList).hasSize(databaseSizeBeforeUpdate);
        MemberData testMemberData = memberDataList.get(memberDataList.size() - 1);
        assertThat(testMemberData.getTestDate()).isEqualTo(UPDATED_TEST_DATE);
        assertThat(testMemberData.getUserId()).isEqualTo(UPDATED_USER_ID);
        assertThat(testMemberData.getUserName()).isEqualTo(UPDATED_USER_NAME);
        assertThat(testMemberData.getUserGender()).isEqualTo(UPDATED_USER_GENDER);
        assertThat(testMemberData.getUserBirthday()).isEqualTo(UPDATED_USER_BIRTHDAY);
        assertThat(testMemberData.getUserAge()).isEqualTo(UPDATED_USER_AGE);
        assertThat(testMemberData.getUserHeight()).isEqualTo(UPDATED_USER_HEIGHT);
        assertThat(testMemberData.getOrderDate()).isEqualTo(UPDATED_ORDER_DATE);
    }

    @Test
    @Transactional
    void putNonExistingMemberData() throws Exception {
        int databaseSizeBeforeUpdate = memberDataRepository.findAll().size();
        memberData.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMemberDataMockMvc
            .perform(
                put(ENTITY_API_URL_ID, memberData.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(memberData))
            )
            .andExpect(status().isBadRequest());

        // Validate the MemberData in the database
        List<MemberData> memberDataList = memberDataRepository.findAll();
        assertThat(memberDataList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchMemberData() throws Exception {
        int databaseSizeBeforeUpdate = memberDataRepository.findAll().size();
        memberData.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMemberDataMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(memberData))
            )
            .andExpect(status().isBadRequest());

        // Validate the MemberData in the database
        List<MemberData> memberDataList = memberDataRepository.findAll();
        assertThat(memberDataList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamMemberData() throws Exception {
        int databaseSizeBeforeUpdate = memberDataRepository.findAll().size();
        memberData.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMemberDataMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(memberData))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the MemberData in the database
        List<MemberData> memberDataList = memberDataRepository.findAll();
        assertThat(memberDataList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateMemberDataWithPatch() throws Exception {
        // Initialize the database
        memberDataRepository.saveAndFlush(memberData);

        int databaseSizeBeforeUpdate = memberDataRepository.findAll().size();

        // Update the memberData using partial update
        MemberData partialUpdatedMemberData = new MemberData();
        partialUpdatedMemberData.setId(memberData.getId());

        partialUpdatedMemberData.userName(UPDATED_USER_NAME).userBirthday(UPDATED_USER_BIRTHDAY).userHeight(UPDATED_USER_HEIGHT);

        restMemberDataMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMemberData.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedMemberData))
            )
            .andExpect(status().isOk());

        // Validate the MemberData in the database
        List<MemberData> memberDataList = memberDataRepository.findAll();
        assertThat(memberDataList).hasSize(databaseSizeBeforeUpdate);
        MemberData testMemberData = memberDataList.get(memberDataList.size() - 1);
        assertThat(testMemberData.getTestDate()).isEqualTo(DEFAULT_TEST_DATE);
        assertThat(testMemberData.getUserId()).isEqualTo(DEFAULT_USER_ID);
        assertThat(testMemberData.getUserName()).isEqualTo(UPDATED_USER_NAME);
        assertThat(testMemberData.getUserGender()).isEqualTo(DEFAULT_USER_GENDER);
        assertThat(testMemberData.getUserBirthday()).isEqualTo(UPDATED_USER_BIRTHDAY);
        assertThat(testMemberData.getUserAge()).isEqualTo(DEFAULT_USER_AGE);
        assertThat(testMemberData.getUserHeight()).isEqualTo(UPDATED_USER_HEIGHT);
        assertThat(testMemberData.getOrderDate()).isEqualTo(DEFAULT_ORDER_DATE);
    }

    @Test
    @Transactional
    void fullUpdateMemberDataWithPatch() throws Exception {
        // Initialize the database
        memberDataRepository.saveAndFlush(memberData);

        int databaseSizeBeforeUpdate = memberDataRepository.findAll().size();

        // Update the memberData using partial update
        MemberData partialUpdatedMemberData = new MemberData();
        partialUpdatedMemberData.setId(memberData.getId());

        partialUpdatedMemberData
            .testDate(UPDATED_TEST_DATE)
            .userId(UPDATED_USER_ID)
            .userName(UPDATED_USER_NAME)
            .userGender(UPDATED_USER_GENDER)
            .userBirthday(UPDATED_USER_BIRTHDAY)
            .userAge(UPDATED_USER_AGE)
            .userHeight(UPDATED_USER_HEIGHT)
            .orderDate(UPDATED_ORDER_DATE);

        restMemberDataMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMemberData.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedMemberData))
            )
            .andExpect(status().isOk());

        // Validate the MemberData in the database
        List<MemberData> memberDataList = memberDataRepository.findAll();
        assertThat(memberDataList).hasSize(databaseSizeBeforeUpdate);
        MemberData testMemberData = memberDataList.get(memberDataList.size() - 1);
        assertThat(testMemberData.getTestDate()).isEqualTo(UPDATED_TEST_DATE);
        assertThat(testMemberData.getUserId()).isEqualTo(UPDATED_USER_ID);
        assertThat(testMemberData.getUserName()).isEqualTo(UPDATED_USER_NAME);
        assertThat(testMemberData.getUserGender()).isEqualTo(UPDATED_USER_GENDER);
        assertThat(testMemberData.getUserBirthday()).isEqualTo(UPDATED_USER_BIRTHDAY);
        assertThat(testMemberData.getUserAge()).isEqualTo(UPDATED_USER_AGE);
        assertThat(testMemberData.getUserHeight()).isEqualTo(UPDATED_USER_HEIGHT);
        assertThat(testMemberData.getOrderDate()).isEqualTo(UPDATED_ORDER_DATE);
    }

    @Test
    @Transactional
    void patchNonExistingMemberData() throws Exception {
        int databaseSizeBeforeUpdate = memberDataRepository.findAll().size();
        memberData.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMemberDataMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, memberData.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(memberData))
            )
            .andExpect(status().isBadRequest());

        // Validate the MemberData in the database
        List<MemberData> memberDataList = memberDataRepository.findAll();
        assertThat(memberDataList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchMemberData() throws Exception {
        int databaseSizeBeforeUpdate = memberDataRepository.findAll().size();
        memberData.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMemberDataMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(memberData))
            )
            .andExpect(status().isBadRequest());

        // Validate the MemberData in the database
        List<MemberData> memberDataList = memberDataRepository.findAll();
        assertThat(memberDataList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamMemberData() throws Exception {
        int databaseSizeBeforeUpdate = memberDataRepository.findAll().size();
        memberData.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMemberDataMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(memberData))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the MemberData in the database
        List<MemberData> memberDataList = memberDataRepository.findAll();
        assertThat(memberDataList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteMemberData() throws Exception {
        // Initialize the database
        memberDataRepository.saveAndFlush(memberData);

        int databaseSizeBeforeDelete = memberDataRepository.findAll().size();

        // Delete the memberData
        restMemberDataMockMvc
            .perform(delete(ENTITY_API_URL_ID, memberData.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<MemberData> memberDataList = memberDataRepository.findAll();
        assertThat(memberDataList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
