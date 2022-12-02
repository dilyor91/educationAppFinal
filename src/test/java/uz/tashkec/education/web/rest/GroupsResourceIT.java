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
import uz.tashkec.education.domain.Groups;
import uz.tashkec.education.repository.GroupsRepository;
import uz.tashkec.education.service.dto.GroupsDTO;
import uz.tashkec.education.service.mapper.GroupsMapper;

/**
 * Integration tests for the {@link GroupsResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class GroupsResourceIT {

    private static final String DEFAULT_GROUP_NO = "AAAAAAAAAA";
    private static final String UPDATED_GROUP_NO = "BBBBBBBBBB";

    private static final Instant DEFAULT_START_HOUR = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_START_HOUR = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_END_HOUR = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_END_HOUR = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Integer DEFAULT_CAPACITY = 1;
    private static final Integer UPDATED_CAPACITY = 2;

    private static final Integer DEFAULT_RESERVED_PLACE = 1;
    private static final Integer UPDATED_RESERVED_PLACE = 2;

    private static final Boolean DEFAULT_STATUS = false;
    private static final Boolean UPDATED_STATUS = true;

    private static final Boolean DEFAULT_FULL = false;
    private static final Boolean UPDATED_FULL = true;

    private static final String ENTITY_API_URL = "/api/groups";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private GroupsRepository groupsRepository;

    @Autowired
    private GroupsMapper groupsMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restGroupsMockMvc;

    private Groups groups;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Groups createEntity(EntityManager em) {
        Groups groups = new Groups()
            .groupNo(DEFAULT_GROUP_NO)
            .startHour(DEFAULT_START_HOUR)
            .endHour(DEFAULT_END_HOUR)
            .capacity(DEFAULT_CAPACITY)
            .reservedPlace(DEFAULT_RESERVED_PLACE)
            .status(DEFAULT_STATUS)
            .full(DEFAULT_FULL);
        return groups;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Groups createUpdatedEntity(EntityManager em) {
        Groups groups = new Groups()
            .groupNo(UPDATED_GROUP_NO)
            .startHour(UPDATED_START_HOUR)
            .endHour(UPDATED_END_HOUR)
            .capacity(UPDATED_CAPACITY)
            .reservedPlace(UPDATED_RESERVED_PLACE)
            .status(UPDATED_STATUS)
            .full(UPDATED_FULL);
        return groups;
    }

    @BeforeEach
    public void initTest() {
        groups = createEntity(em);
    }

    @Test
    @Transactional
    void createGroups() throws Exception {
        int databaseSizeBeforeCreate = groupsRepository.findAll().size();
        // Create the Groups
        GroupsDTO groupsDTO = groupsMapper.toDto(groups);
        restGroupsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(groupsDTO)))
            .andExpect(status().isCreated());

        // Validate the Groups in the database
        List<Groups> groupsList = groupsRepository.findAll();
        assertThat(groupsList).hasSize(databaseSizeBeforeCreate + 1);
        Groups testGroups = groupsList.get(groupsList.size() - 1);
        assertThat(testGroups.getGroupNo()).isEqualTo(DEFAULT_GROUP_NO);
        assertThat(testGroups.getStartHour()).isEqualTo(DEFAULT_START_HOUR);
        assertThat(testGroups.getEndHour()).isEqualTo(DEFAULT_END_HOUR);
        assertThat(testGroups.getCapacity()).isEqualTo(DEFAULT_CAPACITY);
        assertThat(testGroups.getReservedPlace()).isEqualTo(DEFAULT_RESERVED_PLACE);
        assertThat(testGroups.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testGroups.getFull()).isEqualTo(DEFAULT_FULL);
    }

    @Test
    @Transactional
    void createGroupsWithExistingId() throws Exception {
        // Create the Groups with an existing ID
        groups.setId(1L);
        GroupsDTO groupsDTO = groupsMapper.toDto(groups);

        int databaseSizeBeforeCreate = groupsRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restGroupsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(groupsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Groups in the database
        List<Groups> groupsList = groupsRepository.findAll();
        assertThat(groupsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkGroupNoIsRequired() throws Exception {
        int databaseSizeBeforeTest = groupsRepository.findAll().size();
        // set the field null
        groups.setGroupNo(null);

        // Create the Groups, which fails.
        GroupsDTO groupsDTO = groupsMapper.toDto(groups);

        restGroupsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(groupsDTO)))
            .andExpect(status().isBadRequest());

        List<Groups> groupsList = groupsRepository.findAll();
        assertThat(groupsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkStartHourIsRequired() throws Exception {
        int databaseSizeBeforeTest = groupsRepository.findAll().size();
        // set the field null
        groups.setStartHour(null);

        // Create the Groups, which fails.
        GroupsDTO groupsDTO = groupsMapper.toDto(groups);

        restGroupsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(groupsDTO)))
            .andExpect(status().isBadRequest());

        List<Groups> groupsList = groupsRepository.findAll();
        assertThat(groupsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkEndHourIsRequired() throws Exception {
        int databaseSizeBeforeTest = groupsRepository.findAll().size();
        // set the field null
        groups.setEndHour(null);

        // Create the Groups, which fails.
        GroupsDTO groupsDTO = groupsMapper.toDto(groups);

        restGroupsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(groupsDTO)))
            .andExpect(status().isBadRequest());

        List<Groups> groupsList = groupsRepository.findAll();
        assertThat(groupsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCapacityIsRequired() throws Exception {
        int databaseSizeBeforeTest = groupsRepository.findAll().size();
        // set the field null
        groups.setCapacity(null);

        // Create the Groups, which fails.
        GroupsDTO groupsDTO = groupsMapper.toDto(groups);

        restGroupsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(groupsDTO)))
            .andExpect(status().isBadRequest());

        List<Groups> groupsList = groupsRepository.findAll();
        assertThat(groupsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkReservedPlaceIsRequired() throws Exception {
        int databaseSizeBeforeTest = groupsRepository.findAll().size();
        // set the field null
        groups.setReservedPlace(null);

        // Create the Groups, which fails.
        GroupsDTO groupsDTO = groupsMapper.toDto(groups);

        restGroupsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(groupsDTO)))
            .andExpect(status().isBadRequest());

        List<Groups> groupsList = groupsRepository.findAll();
        assertThat(groupsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = groupsRepository.findAll().size();
        // set the field null
        groups.setStatus(null);

        // Create the Groups, which fails.
        GroupsDTO groupsDTO = groupsMapper.toDto(groups);

        restGroupsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(groupsDTO)))
            .andExpect(status().isBadRequest());

        List<Groups> groupsList = groupsRepository.findAll();
        assertThat(groupsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllGroups() throws Exception {
        // Initialize the database
        groupsRepository.saveAndFlush(groups);

        // Get all the groupsList
        restGroupsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(groups.getId().intValue())))
            .andExpect(jsonPath("$.[*].groupNo").value(hasItem(DEFAULT_GROUP_NO)))
            .andExpect(jsonPath("$.[*].startHour").value(hasItem(DEFAULT_START_HOUR.toString())))
            .andExpect(jsonPath("$.[*].endHour").value(hasItem(DEFAULT_END_HOUR.toString())))
            .andExpect(jsonPath("$.[*].capacity").value(hasItem(DEFAULT_CAPACITY)))
            .andExpect(jsonPath("$.[*].reservedPlace").value(hasItem(DEFAULT_RESERVED_PLACE)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.booleanValue())))
            .andExpect(jsonPath("$.[*].full").value(hasItem(DEFAULT_FULL.booleanValue())));
    }

    @Test
    @Transactional
    void getGroups() throws Exception {
        // Initialize the database
        groupsRepository.saveAndFlush(groups);

        // Get the groups
        restGroupsMockMvc
            .perform(get(ENTITY_API_URL_ID, groups.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(groups.getId().intValue()))
            .andExpect(jsonPath("$.groupNo").value(DEFAULT_GROUP_NO))
            .andExpect(jsonPath("$.startHour").value(DEFAULT_START_HOUR.toString()))
            .andExpect(jsonPath("$.endHour").value(DEFAULT_END_HOUR.toString()))
            .andExpect(jsonPath("$.capacity").value(DEFAULT_CAPACITY))
            .andExpect(jsonPath("$.reservedPlace").value(DEFAULT_RESERVED_PLACE))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.booleanValue()))
            .andExpect(jsonPath("$.full").value(DEFAULT_FULL.booleanValue()));
    }

    @Test
    @Transactional
    void getNonExistingGroups() throws Exception {
        // Get the groups
        restGroupsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingGroups() throws Exception {
        // Initialize the database
        groupsRepository.saveAndFlush(groups);

        int databaseSizeBeforeUpdate = groupsRepository.findAll().size();

        // Update the groups
        Groups updatedGroups = groupsRepository.findById(groups.getId()).get();
        // Disconnect from session so that the updates on updatedGroups are not directly saved in db
        em.detach(updatedGroups);
        updatedGroups
            .groupNo(UPDATED_GROUP_NO)
            .startHour(UPDATED_START_HOUR)
            .endHour(UPDATED_END_HOUR)
            .capacity(UPDATED_CAPACITY)
            .reservedPlace(UPDATED_RESERVED_PLACE)
            .status(UPDATED_STATUS)
            .full(UPDATED_FULL);
        GroupsDTO groupsDTO = groupsMapper.toDto(updatedGroups);

        restGroupsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, groupsDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(groupsDTO))
            )
            .andExpect(status().isOk());

        // Validate the Groups in the database
        List<Groups> groupsList = groupsRepository.findAll();
        assertThat(groupsList).hasSize(databaseSizeBeforeUpdate);
        Groups testGroups = groupsList.get(groupsList.size() - 1);
        assertThat(testGroups.getGroupNo()).isEqualTo(UPDATED_GROUP_NO);
        assertThat(testGroups.getStartHour()).isEqualTo(UPDATED_START_HOUR);
        assertThat(testGroups.getEndHour()).isEqualTo(UPDATED_END_HOUR);
        assertThat(testGroups.getCapacity()).isEqualTo(UPDATED_CAPACITY);
        assertThat(testGroups.getReservedPlace()).isEqualTo(UPDATED_RESERVED_PLACE);
        assertThat(testGroups.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testGroups.getFull()).isEqualTo(UPDATED_FULL);
    }

    @Test
    @Transactional
    void putNonExistingGroups() throws Exception {
        int databaseSizeBeforeUpdate = groupsRepository.findAll().size();
        groups.setId(count.incrementAndGet());

        // Create the Groups
        GroupsDTO groupsDTO = groupsMapper.toDto(groups);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGroupsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, groupsDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(groupsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Groups in the database
        List<Groups> groupsList = groupsRepository.findAll();
        assertThat(groupsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchGroups() throws Exception {
        int databaseSizeBeforeUpdate = groupsRepository.findAll().size();
        groups.setId(count.incrementAndGet());

        // Create the Groups
        GroupsDTO groupsDTO = groupsMapper.toDto(groups);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGroupsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(groupsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Groups in the database
        List<Groups> groupsList = groupsRepository.findAll();
        assertThat(groupsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamGroups() throws Exception {
        int databaseSizeBeforeUpdate = groupsRepository.findAll().size();
        groups.setId(count.incrementAndGet());

        // Create the Groups
        GroupsDTO groupsDTO = groupsMapper.toDto(groups);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGroupsMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(groupsDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Groups in the database
        List<Groups> groupsList = groupsRepository.findAll();
        assertThat(groupsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateGroupsWithPatch() throws Exception {
        // Initialize the database
        groupsRepository.saveAndFlush(groups);

        int databaseSizeBeforeUpdate = groupsRepository.findAll().size();

        // Update the groups using partial update
        Groups partialUpdatedGroups = new Groups();
        partialUpdatedGroups.setId(groups.getId());

        partialUpdatedGroups.startHour(UPDATED_START_HOUR).endHour(UPDATED_END_HOUR).status(UPDATED_STATUS);

        restGroupsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedGroups.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedGroups))
            )
            .andExpect(status().isOk());

        // Validate the Groups in the database
        List<Groups> groupsList = groupsRepository.findAll();
        assertThat(groupsList).hasSize(databaseSizeBeforeUpdate);
        Groups testGroups = groupsList.get(groupsList.size() - 1);
        assertThat(testGroups.getGroupNo()).isEqualTo(DEFAULT_GROUP_NO);
        assertThat(testGroups.getStartHour()).isEqualTo(UPDATED_START_HOUR);
        assertThat(testGroups.getEndHour()).isEqualTo(UPDATED_END_HOUR);
        assertThat(testGroups.getCapacity()).isEqualTo(DEFAULT_CAPACITY);
        assertThat(testGroups.getReservedPlace()).isEqualTo(DEFAULT_RESERVED_PLACE);
        assertThat(testGroups.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testGroups.getFull()).isEqualTo(DEFAULT_FULL);
    }

    @Test
    @Transactional
    void fullUpdateGroupsWithPatch() throws Exception {
        // Initialize the database
        groupsRepository.saveAndFlush(groups);

        int databaseSizeBeforeUpdate = groupsRepository.findAll().size();

        // Update the groups using partial update
        Groups partialUpdatedGroups = new Groups();
        partialUpdatedGroups.setId(groups.getId());

        partialUpdatedGroups
            .groupNo(UPDATED_GROUP_NO)
            .startHour(UPDATED_START_HOUR)
            .endHour(UPDATED_END_HOUR)
            .capacity(UPDATED_CAPACITY)
            .reservedPlace(UPDATED_RESERVED_PLACE)
            .status(UPDATED_STATUS)
            .full(UPDATED_FULL);

        restGroupsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedGroups.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedGroups))
            )
            .andExpect(status().isOk());

        // Validate the Groups in the database
        List<Groups> groupsList = groupsRepository.findAll();
        assertThat(groupsList).hasSize(databaseSizeBeforeUpdate);
        Groups testGroups = groupsList.get(groupsList.size() - 1);
        assertThat(testGroups.getGroupNo()).isEqualTo(UPDATED_GROUP_NO);
        assertThat(testGroups.getStartHour()).isEqualTo(UPDATED_START_HOUR);
        assertThat(testGroups.getEndHour()).isEqualTo(UPDATED_END_HOUR);
        assertThat(testGroups.getCapacity()).isEqualTo(UPDATED_CAPACITY);
        assertThat(testGroups.getReservedPlace()).isEqualTo(UPDATED_RESERVED_PLACE);
        assertThat(testGroups.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testGroups.getFull()).isEqualTo(UPDATED_FULL);
    }

    @Test
    @Transactional
    void patchNonExistingGroups() throws Exception {
        int databaseSizeBeforeUpdate = groupsRepository.findAll().size();
        groups.setId(count.incrementAndGet());

        // Create the Groups
        GroupsDTO groupsDTO = groupsMapper.toDto(groups);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGroupsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, groupsDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(groupsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Groups in the database
        List<Groups> groupsList = groupsRepository.findAll();
        assertThat(groupsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchGroups() throws Exception {
        int databaseSizeBeforeUpdate = groupsRepository.findAll().size();
        groups.setId(count.incrementAndGet());

        // Create the Groups
        GroupsDTO groupsDTO = groupsMapper.toDto(groups);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGroupsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(groupsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Groups in the database
        List<Groups> groupsList = groupsRepository.findAll();
        assertThat(groupsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamGroups() throws Exception {
        int databaseSizeBeforeUpdate = groupsRepository.findAll().size();
        groups.setId(count.incrementAndGet());

        // Create the Groups
        GroupsDTO groupsDTO = groupsMapper.toDto(groups);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGroupsMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(groupsDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Groups in the database
        List<Groups> groupsList = groupsRepository.findAll();
        assertThat(groupsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteGroups() throws Exception {
        // Initialize the database
        groupsRepository.saveAndFlush(groups);

        int databaseSizeBeforeDelete = groupsRepository.findAll().size();

        // Delete the groups
        restGroupsMockMvc
            .perform(delete(ENTITY_API_URL_ID, groups.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Groups> groupsList = groupsRepository.findAll();
        assertThat(groupsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
