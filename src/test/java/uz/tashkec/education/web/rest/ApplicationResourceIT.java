package uz.tashkec.education.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

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
import uz.tashkec.education.IntegrationTest;
import uz.tashkec.education.domain.Application;
import uz.tashkec.education.domain.enumeration.AppStatusEnum;
import uz.tashkec.education.domain.enumeration.GenderEnum;
import uz.tashkec.education.repository.ApplicationRepository;
import uz.tashkec.education.service.dto.ApplicationDTO;
import uz.tashkec.education.service.mapper.ApplicationMapper;

/**
 * Integration tests for the {@link ApplicationResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ApplicationResourceIT {

    private static final String DEFAULT_FIRST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FIRST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_LAST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_LAST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_MIDDLE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_MIDDLE_NAME = "BBBBBBBBBB";

    private static final Instant DEFAULT_BIRTHDAY = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_BIRTHDAY = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final GenderEnum DEFAULT_GENDER = GenderEnum.MALE;
    private static final GenderEnum UPDATED_GENDER = GenderEnum.FEMALE;

    private static final String DEFAULT_PASSPORT_NO = "AAAAAAAAAA";
    private static final String UPDATED_PASSPORT_NO = "BBBBBBBBBB";

    private static final String DEFAULT_NATIONALITY = "AAAAAAAAAA";
    private static final String UPDATED_NATIONALITY = "BBBBBBBBBB";

    private static final String DEFAULT_OCCUPATION = "AAAAAAAAAA";
    private static final String UPDATED_OCCUPATION = "BBBBBBBBBB";

    private static final String DEFAULT_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS = "BBBBBBBBBB";

    private static final String DEFAULT_MOB_PHONE = "AAAAAAAAAA";
    private static final String UPDATED_MOB_PHONE = "BBBBBBBBBB";

    private static final String DEFAULT_HOME_PHONE = "AAAAAAAAAA";
    private static final String UPDATED_HOME_PHONE = "BBBBBBBBBB";

    private static final String DEFAULT_NOTIFICATION_METHOD = "AAAAAAAAAA";
    private static final String UPDATED_NOTIFICATION_METHOD = "BBBBBBBBBB";

    private static final String DEFAULT_CERTIFICATE_NO = "AAAAAAAAAA";
    private static final String UPDATED_CERTIFICATE_NO = "BBBBBBBBBB";

    private static final Instant DEFAULT_CERTIFICATE_GIVEN_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CERTIFICATE_GIVEN_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final AppStatusEnum DEFAULT_STATUS = AppStatusEnum.DRAFT;
    private static final AppStatusEnum UPDATED_STATUS = AppStatusEnum.NEW;

    private static final String ENTITY_API_URL = "/api/applications";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ApplicationRepository applicationRepository;

    @Autowired
    private ApplicationMapper applicationMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restApplicationMockMvc;

    private Application application;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Application createEntity(EntityManager em) {
        Application application = new Application()
            .firstName(DEFAULT_FIRST_NAME)
            .lastName(DEFAULT_LAST_NAME)
            .middleName(DEFAULT_MIDDLE_NAME)
            .birthday(DEFAULT_BIRTHDAY)
            .gender(DEFAULT_GENDER)
            .passportNo(DEFAULT_PASSPORT_NO)
            .nationality(DEFAULT_NATIONALITY)
            .occupation(DEFAULT_OCCUPATION)
            .address(DEFAULT_ADDRESS)
            .mobPhone(DEFAULT_MOB_PHONE)
            .homePhone(DEFAULT_HOME_PHONE)
            .notificationMethod(DEFAULT_NOTIFICATION_METHOD)
            .certificateNo(DEFAULT_CERTIFICATE_NO)
            .certificateGivenDate(DEFAULT_CERTIFICATE_GIVEN_DATE)
            .status(DEFAULT_STATUS);
        return application;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Application createUpdatedEntity(EntityManager em) {
        Application application = new Application()
            .firstName(UPDATED_FIRST_NAME)
            .lastName(UPDATED_LAST_NAME)
            .middleName(UPDATED_MIDDLE_NAME)
            .birthday(UPDATED_BIRTHDAY)
            .gender(UPDATED_GENDER)
            .passportNo(UPDATED_PASSPORT_NO)
            .nationality(UPDATED_NATIONALITY)
            .occupation(UPDATED_OCCUPATION)
            .address(UPDATED_ADDRESS)
            .mobPhone(UPDATED_MOB_PHONE)
            .homePhone(UPDATED_HOME_PHONE)
            .notificationMethod(UPDATED_NOTIFICATION_METHOD)
            .certificateNo(UPDATED_CERTIFICATE_NO)
            .certificateGivenDate(UPDATED_CERTIFICATE_GIVEN_DATE)
            .status(UPDATED_STATUS);
        return application;
    }

    @BeforeEach
    public void initTest() {
        application = createEntity(em);
    }

    @Test
    @Transactional
    void createApplication() throws Exception {
        int databaseSizeBeforeCreate = applicationRepository.findAll().size();
        // Create the Application
        ApplicationDTO applicationDTO = applicationMapper.toDto(application);
        restApplicationMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(applicationDTO))
            )
            .andExpect(status().isCreated());

        // Validate the Application in the database
        List<Application> applicationList = applicationRepository.findAll();
        assertThat(applicationList).hasSize(databaseSizeBeforeCreate + 1);
        Application testApplication = applicationList.get(applicationList.size() - 1);
        assertThat(testApplication.getFirstName()).isEqualTo(DEFAULT_FIRST_NAME);
        assertThat(testApplication.getLastName()).isEqualTo(DEFAULT_LAST_NAME);
        assertThat(testApplication.getMiddleName()).isEqualTo(DEFAULT_MIDDLE_NAME);
        assertThat(testApplication.getBirthday()).isEqualTo(DEFAULT_BIRTHDAY);
        assertThat(testApplication.getGender()).isEqualTo(DEFAULT_GENDER);
        assertThat(testApplication.getPassportNo()).isEqualTo(DEFAULT_PASSPORT_NO);
        assertThat(testApplication.getNationality()).isEqualTo(DEFAULT_NATIONALITY);
        assertThat(testApplication.getOccupation()).isEqualTo(DEFAULT_OCCUPATION);
        assertThat(testApplication.getAddress()).isEqualTo(DEFAULT_ADDRESS);
        assertThat(testApplication.getMobPhone()).isEqualTo(DEFAULT_MOB_PHONE);
        assertThat(testApplication.getHomePhone()).isEqualTo(DEFAULT_HOME_PHONE);
        assertThat(testApplication.getNotificationMethod()).isEqualTo(DEFAULT_NOTIFICATION_METHOD);
        assertThat(testApplication.getCertificateNo()).isEqualTo(DEFAULT_CERTIFICATE_NO);
        assertThat(testApplication.getCertificateGivenDate()).isEqualTo(DEFAULT_CERTIFICATE_GIVEN_DATE);
        assertThat(testApplication.getStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    void createApplicationWithExistingId() throws Exception {
        // Create the Application with an existing ID
        application.setId(1L);
        ApplicationDTO applicationDTO = applicationMapper.toDto(application);

        int databaseSizeBeforeCreate = applicationRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restApplicationMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(applicationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Application in the database
        List<Application> applicationList = applicationRepository.findAll();
        assertThat(applicationList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkFirstNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = applicationRepository.findAll().size();
        // set the field null
        application.setFirstName(null);

        // Create the Application, which fails.
        ApplicationDTO applicationDTO = applicationMapper.toDto(application);

        restApplicationMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(applicationDTO))
            )
            .andExpect(status().isBadRequest());

        List<Application> applicationList = applicationRepository.findAll();
        assertThat(applicationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkLastNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = applicationRepository.findAll().size();
        // set the field null
        application.setLastName(null);

        // Create the Application, which fails.
        ApplicationDTO applicationDTO = applicationMapper.toDto(application);

        restApplicationMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(applicationDTO))
            )
            .andExpect(status().isBadRequest());

        List<Application> applicationList = applicationRepository.findAll();
        assertThat(applicationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkMiddleNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = applicationRepository.findAll().size();
        // set the field null
        application.setMiddleName(null);

        // Create the Application, which fails.
        ApplicationDTO applicationDTO = applicationMapper.toDto(application);

        restApplicationMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(applicationDTO))
            )
            .andExpect(status().isBadRequest());

        List<Application> applicationList = applicationRepository.findAll();
        assertThat(applicationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkBirthdayIsRequired() throws Exception {
        int databaseSizeBeforeTest = applicationRepository.findAll().size();
        // set the field null
        application.setBirthday(null);

        // Create the Application, which fails.
        ApplicationDTO applicationDTO = applicationMapper.toDto(application);

        restApplicationMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(applicationDTO))
            )
            .andExpect(status().isBadRequest());

        List<Application> applicationList = applicationRepository.findAll();
        assertThat(applicationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkGenderIsRequired() throws Exception {
        int databaseSizeBeforeTest = applicationRepository.findAll().size();
        // set the field null
        application.setGender(null);

        // Create the Application, which fails.
        ApplicationDTO applicationDTO = applicationMapper.toDto(application);

        restApplicationMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(applicationDTO))
            )
            .andExpect(status().isBadRequest());

        List<Application> applicationList = applicationRepository.findAll();
        assertThat(applicationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkPassportNoIsRequired() throws Exception {
        int databaseSizeBeforeTest = applicationRepository.findAll().size();
        // set the field null
        application.setPassportNo(null);

        // Create the Application, which fails.
        ApplicationDTO applicationDTO = applicationMapper.toDto(application);

        restApplicationMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(applicationDTO))
            )
            .andExpect(status().isBadRequest());

        List<Application> applicationList = applicationRepository.findAll();
        assertThat(applicationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkNationalityIsRequired() throws Exception {
        int databaseSizeBeforeTest = applicationRepository.findAll().size();
        // set the field null
        application.setNationality(null);

        // Create the Application, which fails.
        ApplicationDTO applicationDTO = applicationMapper.toDto(application);

        restApplicationMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(applicationDTO))
            )
            .andExpect(status().isBadRequest());

        List<Application> applicationList = applicationRepository.findAll();
        assertThat(applicationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkOccupationIsRequired() throws Exception {
        int databaseSizeBeforeTest = applicationRepository.findAll().size();
        // set the field null
        application.setOccupation(null);

        // Create the Application, which fails.
        ApplicationDTO applicationDTO = applicationMapper.toDto(application);

        restApplicationMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(applicationDTO))
            )
            .andExpect(status().isBadRequest());

        List<Application> applicationList = applicationRepository.findAll();
        assertThat(applicationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkAddressIsRequired() throws Exception {
        int databaseSizeBeforeTest = applicationRepository.findAll().size();
        // set the field null
        application.setAddress(null);

        // Create the Application, which fails.
        ApplicationDTO applicationDTO = applicationMapper.toDto(application);

        restApplicationMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(applicationDTO))
            )
            .andExpect(status().isBadRequest());

        List<Application> applicationList = applicationRepository.findAll();
        assertThat(applicationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkMobPhoneIsRequired() throws Exception {
        int databaseSizeBeforeTest = applicationRepository.findAll().size();
        // set the field null
        application.setMobPhone(null);

        // Create the Application, which fails.
        ApplicationDTO applicationDTO = applicationMapper.toDto(application);

        restApplicationMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(applicationDTO))
            )
            .andExpect(status().isBadRequest());

        List<Application> applicationList = applicationRepository.findAll();
        assertThat(applicationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllApplications() throws Exception {
        // Initialize the database
        applicationRepository.saveAndFlush(application);

        // Get all the applicationList
        restApplicationMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(application.getId().intValue())))
            .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME)))
            .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME)))
            .andExpect(jsonPath("$.[*].middleName").value(hasItem(DEFAULT_MIDDLE_NAME)))
            .andExpect(jsonPath("$.[*].birthday").value(hasItem(DEFAULT_BIRTHDAY.toString())))
            .andExpect(jsonPath("$.[*].gender").value(hasItem(DEFAULT_GENDER.toString())))
            .andExpect(jsonPath("$.[*].passportNo").value(hasItem(DEFAULT_PASSPORT_NO)))
            .andExpect(jsonPath("$.[*].nationality").value(hasItem(DEFAULT_NATIONALITY)))
            .andExpect(jsonPath("$.[*].occupation").value(hasItem(DEFAULT_OCCUPATION)))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS)))
            .andExpect(jsonPath("$.[*].mobPhone").value(hasItem(DEFAULT_MOB_PHONE)))
            .andExpect(jsonPath("$.[*].homePhone").value(hasItem(DEFAULT_HOME_PHONE)))
            .andExpect(jsonPath("$.[*].notificationMethod").value(hasItem(DEFAULT_NOTIFICATION_METHOD)))
            .andExpect(jsonPath("$.[*].certificateNo").value(hasItem(DEFAULT_CERTIFICATE_NO)))
            .andExpect(jsonPath("$.[*].certificateGivenDate").value(hasItem(DEFAULT_CERTIFICATE_GIVEN_DATE.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())));
    }

    @Test
    @Transactional
    void getApplication() throws Exception {
        // Initialize the database
        applicationRepository.saveAndFlush(application);

        // Get the application
        restApplicationMockMvc
            .perform(get(ENTITY_API_URL_ID, application.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(application.getId().intValue()))
            .andExpect(jsonPath("$.firstName").value(DEFAULT_FIRST_NAME))
            .andExpect(jsonPath("$.lastName").value(DEFAULT_LAST_NAME))
            .andExpect(jsonPath("$.middleName").value(DEFAULT_MIDDLE_NAME))
            .andExpect(jsonPath("$.birthday").value(DEFAULT_BIRTHDAY.toString()))
            .andExpect(jsonPath("$.gender").value(DEFAULT_GENDER.toString()))
            .andExpect(jsonPath("$.passportNo").value(DEFAULT_PASSPORT_NO))
            .andExpect(jsonPath("$.nationality").value(DEFAULT_NATIONALITY))
            .andExpect(jsonPath("$.occupation").value(DEFAULT_OCCUPATION))
            .andExpect(jsonPath("$.address").value(DEFAULT_ADDRESS))
            .andExpect(jsonPath("$.mobPhone").value(DEFAULT_MOB_PHONE))
            .andExpect(jsonPath("$.homePhone").value(DEFAULT_HOME_PHONE))
            .andExpect(jsonPath("$.notificationMethod").value(DEFAULT_NOTIFICATION_METHOD))
            .andExpect(jsonPath("$.certificateNo").value(DEFAULT_CERTIFICATE_NO))
            .andExpect(jsonPath("$.certificateGivenDate").value(DEFAULT_CERTIFICATE_GIVEN_DATE.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()));
    }

    @Test
    @Transactional
    void getNonExistingApplication() throws Exception {
        // Get the application
        restApplicationMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingApplication() throws Exception {
        // Initialize the database
        applicationRepository.saveAndFlush(application);

        int databaseSizeBeforeUpdate = applicationRepository.findAll().size();

        // Update the application
        Application updatedApplication = applicationRepository.findById(application.getId()).get();
        // Disconnect from session so that the updates on updatedApplication are not directly saved in db
        em.detach(updatedApplication);
        updatedApplication
            .firstName(UPDATED_FIRST_NAME)
            .lastName(UPDATED_LAST_NAME)
            .middleName(UPDATED_MIDDLE_NAME)
            .birthday(UPDATED_BIRTHDAY)
            .gender(UPDATED_GENDER)
            .passportNo(UPDATED_PASSPORT_NO)
            .nationality(UPDATED_NATIONALITY)
            .occupation(UPDATED_OCCUPATION)
            .address(UPDATED_ADDRESS)
            .mobPhone(UPDATED_MOB_PHONE)
            .homePhone(UPDATED_HOME_PHONE)
            .notificationMethod(UPDATED_NOTIFICATION_METHOD)
            .certificateNo(UPDATED_CERTIFICATE_NO)
            .certificateGivenDate(UPDATED_CERTIFICATE_GIVEN_DATE)
            .status(UPDATED_STATUS);
        ApplicationDTO applicationDTO = applicationMapper.toDto(updatedApplication);

        restApplicationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, applicationDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(applicationDTO))
            )
            .andExpect(status().isOk());

        // Validate the Application in the database
        List<Application> applicationList = applicationRepository.findAll();
        assertThat(applicationList).hasSize(databaseSizeBeforeUpdate);
        Application testApplication = applicationList.get(applicationList.size() - 1);
        assertThat(testApplication.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testApplication.getLastName()).isEqualTo(UPDATED_LAST_NAME);
        assertThat(testApplication.getMiddleName()).isEqualTo(UPDATED_MIDDLE_NAME);
        assertThat(testApplication.getBirthday()).isEqualTo(UPDATED_BIRTHDAY);
        assertThat(testApplication.getGender()).isEqualTo(UPDATED_GENDER);
        assertThat(testApplication.getPassportNo()).isEqualTo(UPDATED_PASSPORT_NO);
        assertThat(testApplication.getNationality()).isEqualTo(UPDATED_NATIONALITY);
        assertThat(testApplication.getOccupation()).isEqualTo(UPDATED_OCCUPATION);
        assertThat(testApplication.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testApplication.getMobPhone()).isEqualTo(UPDATED_MOB_PHONE);
        assertThat(testApplication.getHomePhone()).isEqualTo(UPDATED_HOME_PHONE);
        assertThat(testApplication.getNotificationMethod()).isEqualTo(UPDATED_NOTIFICATION_METHOD);
        assertThat(testApplication.getCertificateNo()).isEqualTo(UPDATED_CERTIFICATE_NO);
        assertThat(testApplication.getCertificateGivenDate()).isEqualTo(UPDATED_CERTIFICATE_GIVEN_DATE);
        assertThat(testApplication.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    void putNonExistingApplication() throws Exception {
        int databaseSizeBeforeUpdate = applicationRepository.findAll().size();
        application.setId(count.incrementAndGet());

        // Create the Application
        ApplicationDTO applicationDTO = applicationMapper.toDto(application);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restApplicationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, applicationDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(applicationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Application in the database
        List<Application> applicationList = applicationRepository.findAll();
        assertThat(applicationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchApplication() throws Exception {
        int databaseSizeBeforeUpdate = applicationRepository.findAll().size();
        application.setId(count.incrementAndGet());

        // Create the Application
        ApplicationDTO applicationDTO = applicationMapper.toDto(application);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restApplicationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(applicationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Application in the database
        List<Application> applicationList = applicationRepository.findAll();
        assertThat(applicationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamApplication() throws Exception {
        int databaseSizeBeforeUpdate = applicationRepository.findAll().size();
        application.setId(count.incrementAndGet());

        // Create the Application
        ApplicationDTO applicationDTO = applicationMapper.toDto(application);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restApplicationMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(applicationDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Application in the database
        List<Application> applicationList = applicationRepository.findAll();
        assertThat(applicationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateApplicationWithPatch() throws Exception {
        // Initialize the database
        applicationRepository.saveAndFlush(application);

        int databaseSizeBeforeUpdate = applicationRepository.findAll().size();

        // Update the application using partial update
        Application partialUpdatedApplication = new Application();
        partialUpdatedApplication.setId(application.getId());

        partialUpdatedApplication
            .lastName(UPDATED_LAST_NAME)
            .nationality(UPDATED_NATIONALITY)
            .mobPhone(UPDATED_MOB_PHONE)
            .notificationMethod(UPDATED_NOTIFICATION_METHOD)
            .certificateNo(UPDATED_CERTIFICATE_NO);

        restApplicationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedApplication.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedApplication))
            )
            .andExpect(status().isOk());

        // Validate the Application in the database
        List<Application> applicationList = applicationRepository.findAll();
        assertThat(applicationList).hasSize(databaseSizeBeforeUpdate);
        Application testApplication = applicationList.get(applicationList.size() - 1);
        assertThat(testApplication.getFirstName()).isEqualTo(DEFAULT_FIRST_NAME);
        assertThat(testApplication.getLastName()).isEqualTo(UPDATED_LAST_NAME);
        assertThat(testApplication.getMiddleName()).isEqualTo(DEFAULT_MIDDLE_NAME);
        assertThat(testApplication.getBirthday()).isEqualTo(DEFAULT_BIRTHDAY);
        assertThat(testApplication.getGender()).isEqualTo(DEFAULT_GENDER);
        assertThat(testApplication.getPassportNo()).isEqualTo(DEFAULT_PASSPORT_NO);
        assertThat(testApplication.getNationality()).isEqualTo(UPDATED_NATIONALITY);
        assertThat(testApplication.getOccupation()).isEqualTo(DEFAULT_OCCUPATION);
        assertThat(testApplication.getAddress()).isEqualTo(DEFAULT_ADDRESS);
        assertThat(testApplication.getMobPhone()).isEqualTo(UPDATED_MOB_PHONE);
        assertThat(testApplication.getHomePhone()).isEqualTo(DEFAULT_HOME_PHONE);
        assertThat(testApplication.getNotificationMethod()).isEqualTo(UPDATED_NOTIFICATION_METHOD);
        assertThat(testApplication.getCertificateNo()).isEqualTo(UPDATED_CERTIFICATE_NO);
        assertThat(testApplication.getCertificateGivenDate()).isEqualTo(DEFAULT_CERTIFICATE_GIVEN_DATE);
        assertThat(testApplication.getStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    void fullUpdateApplicationWithPatch() throws Exception {
        // Initialize the database
        applicationRepository.saveAndFlush(application);

        int databaseSizeBeforeUpdate = applicationRepository.findAll().size();

        // Update the application using partial update
        Application partialUpdatedApplication = new Application();
        partialUpdatedApplication.setId(application.getId());

        partialUpdatedApplication
            .firstName(UPDATED_FIRST_NAME)
            .lastName(UPDATED_LAST_NAME)
            .middleName(UPDATED_MIDDLE_NAME)
            .birthday(UPDATED_BIRTHDAY)
            .gender(UPDATED_GENDER)
            .passportNo(UPDATED_PASSPORT_NO)
            .nationality(UPDATED_NATIONALITY)
            .occupation(UPDATED_OCCUPATION)
            .address(UPDATED_ADDRESS)
            .mobPhone(UPDATED_MOB_PHONE)
            .homePhone(UPDATED_HOME_PHONE)
            .notificationMethod(UPDATED_NOTIFICATION_METHOD)
            .certificateNo(UPDATED_CERTIFICATE_NO)
            .certificateGivenDate(UPDATED_CERTIFICATE_GIVEN_DATE)
            .status(UPDATED_STATUS);

        restApplicationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedApplication.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedApplication))
            )
            .andExpect(status().isOk());

        // Validate the Application in the database
        List<Application> applicationList = applicationRepository.findAll();
        assertThat(applicationList).hasSize(databaseSizeBeforeUpdate);
        Application testApplication = applicationList.get(applicationList.size() - 1);
        assertThat(testApplication.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testApplication.getLastName()).isEqualTo(UPDATED_LAST_NAME);
        assertThat(testApplication.getMiddleName()).isEqualTo(UPDATED_MIDDLE_NAME);
        assertThat(testApplication.getBirthday()).isEqualTo(UPDATED_BIRTHDAY);
        assertThat(testApplication.getGender()).isEqualTo(UPDATED_GENDER);
        assertThat(testApplication.getPassportNo()).isEqualTo(UPDATED_PASSPORT_NO);
        assertThat(testApplication.getNationality()).isEqualTo(UPDATED_NATIONALITY);
        assertThat(testApplication.getOccupation()).isEqualTo(UPDATED_OCCUPATION);
        assertThat(testApplication.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testApplication.getMobPhone()).isEqualTo(UPDATED_MOB_PHONE);
        assertThat(testApplication.getHomePhone()).isEqualTo(UPDATED_HOME_PHONE);
        assertThat(testApplication.getNotificationMethod()).isEqualTo(UPDATED_NOTIFICATION_METHOD);
        assertThat(testApplication.getCertificateNo()).isEqualTo(UPDATED_CERTIFICATE_NO);
        assertThat(testApplication.getCertificateGivenDate()).isEqualTo(UPDATED_CERTIFICATE_GIVEN_DATE);
        assertThat(testApplication.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    void patchNonExistingApplication() throws Exception {
        int databaseSizeBeforeUpdate = applicationRepository.findAll().size();
        application.setId(count.incrementAndGet());

        // Create the Application
        ApplicationDTO applicationDTO = applicationMapper.toDto(application);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restApplicationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, applicationDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(applicationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Application in the database
        List<Application> applicationList = applicationRepository.findAll();
        assertThat(applicationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchApplication() throws Exception {
        int databaseSizeBeforeUpdate = applicationRepository.findAll().size();
        application.setId(count.incrementAndGet());

        // Create the Application
        ApplicationDTO applicationDTO = applicationMapper.toDto(application);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restApplicationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(applicationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Application in the database
        List<Application> applicationList = applicationRepository.findAll();
        assertThat(applicationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamApplication() throws Exception {
        int databaseSizeBeforeUpdate = applicationRepository.findAll().size();
        application.setId(count.incrementAndGet());

        // Create the Application
        ApplicationDTO applicationDTO = applicationMapper.toDto(application);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restApplicationMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(applicationDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Application in the database
        List<Application> applicationList = applicationRepository.findAll();
        assertThat(applicationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteApplication() throws Exception {
        // Initialize the database
        applicationRepository.saveAndFlush(application);

        int databaseSizeBeforeDelete = applicationRepository.findAll().size();

        // Delete the application
        restApplicationMockMvc
            .perform(delete(ENTITY_API_URL_ID, application.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Application> applicationList = applicationRepository.findAll();
        assertThat(applicationList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
