package uz.tashkec.education.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

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
import uz.tashkec.education.domain.Target;
import uz.tashkec.education.repository.TargetRepository;
import uz.tashkec.education.service.dto.TargetDTO;
import uz.tashkec.education.service.mapper.TargetMapper;

/**
 * Integration tests for the {@link TargetResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class TargetResourceIT {

    private static final String DEFAULT_TITLE_UZ = "AAAAAAAAAA";
    private static final String UPDATED_TITLE_UZ = "BBBBBBBBBB";

    private static final String DEFAULT_TITLE_RU = "AAAAAAAAAA";
    private static final String UPDATED_TITLE_RU = "BBBBBBBBBB";

    private static final String DEFAULT_CONTENT_UZ = "AAAAAAAAAA";
    private static final String UPDATED_CONTENT_UZ = "BBBBBBBBBB";

    private static final String DEFAULT_CONTENT_RU = "AAAAAAAAAA";
    private static final String UPDATED_CONTENT_RU = "BBBBBBBBBB";

    private static final Boolean DEFAULT_STATUS = false;
    private static final Boolean UPDATED_STATUS = true;

    private static final String ENTITY_API_URL = "/api/targets";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private TargetRepository targetRepository;

    @Autowired
    private TargetMapper targetMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTargetMockMvc;

    private Target target;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Target createEntity(EntityManager em) {
        Target target = new Target()
            .titleUz(DEFAULT_TITLE_UZ)
            .titleRu(DEFAULT_TITLE_RU)
            .contentUz(DEFAULT_CONTENT_UZ)
            .contentRu(DEFAULT_CONTENT_RU)
            .status(DEFAULT_STATUS);
        return target;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Target createUpdatedEntity(EntityManager em) {
        Target target = new Target()
            .titleUz(UPDATED_TITLE_UZ)
            .titleRu(UPDATED_TITLE_RU)
            .contentUz(UPDATED_CONTENT_UZ)
            .contentRu(UPDATED_CONTENT_RU)
            .status(UPDATED_STATUS);
        return target;
    }

    @BeforeEach
    public void initTest() {
        target = createEntity(em);
    }

    @Test
    @Transactional
    void createTarget() throws Exception {
        int databaseSizeBeforeCreate = targetRepository.findAll().size();
        // Create the Target
        TargetDTO targetDTO = targetMapper.toDto(target);
        restTargetMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(targetDTO)))
            .andExpect(status().isCreated());

        // Validate the Target in the database
        List<Target> targetList = targetRepository.findAll();
        assertThat(targetList).hasSize(databaseSizeBeforeCreate + 1);
        Target testTarget = targetList.get(targetList.size() - 1);
        assertThat(testTarget.getTitleUz()).isEqualTo(DEFAULT_TITLE_UZ);
        assertThat(testTarget.getTitleRu()).isEqualTo(DEFAULT_TITLE_RU);
        assertThat(testTarget.getContentUz()).isEqualTo(DEFAULT_CONTENT_UZ);
        assertThat(testTarget.getContentRu()).isEqualTo(DEFAULT_CONTENT_RU);
        assertThat(testTarget.getStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    void createTargetWithExistingId() throws Exception {
        // Create the Target with an existing ID
        target.setId(1L);
        TargetDTO targetDTO = targetMapper.toDto(target);

        int databaseSizeBeforeCreate = targetRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTargetMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(targetDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Target in the database
        List<Target> targetList = targetRepository.findAll();
        assertThat(targetList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkContentUzIsRequired() throws Exception {
        int databaseSizeBeforeTest = targetRepository.findAll().size();
        // set the field null
        target.setContentUz(null);

        // Create the Target, which fails.
        TargetDTO targetDTO = targetMapper.toDto(target);

        restTargetMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(targetDTO)))
            .andExpect(status().isBadRequest());

        List<Target> targetList = targetRepository.findAll();
        assertThat(targetList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkContentRuIsRequired() throws Exception {
        int databaseSizeBeforeTest = targetRepository.findAll().size();
        // set the field null
        target.setContentRu(null);

        // Create the Target, which fails.
        TargetDTO targetDTO = targetMapper.toDto(target);

        restTargetMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(targetDTO)))
            .andExpect(status().isBadRequest());

        List<Target> targetList = targetRepository.findAll();
        assertThat(targetList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = targetRepository.findAll().size();
        // set the field null
        target.setStatus(null);

        // Create the Target, which fails.
        TargetDTO targetDTO = targetMapper.toDto(target);

        restTargetMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(targetDTO)))
            .andExpect(status().isBadRequest());

        List<Target> targetList = targetRepository.findAll();
        assertThat(targetList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllTargets() throws Exception {
        // Initialize the database
        targetRepository.saveAndFlush(target);

        // Get all the targetList
        restTargetMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(target.getId().intValue())))
            .andExpect(jsonPath("$.[*].titleUz").value(hasItem(DEFAULT_TITLE_UZ)))
            .andExpect(jsonPath("$.[*].titleRu").value(hasItem(DEFAULT_TITLE_RU)))
            .andExpect(jsonPath("$.[*].contentUz").value(hasItem(DEFAULT_CONTENT_UZ)))
            .andExpect(jsonPath("$.[*].contentRu").value(hasItem(DEFAULT_CONTENT_RU)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.booleanValue())));
    }

    @Test
    @Transactional
    void getTarget() throws Exception {
        // Initialize the database
        targetRepository.saveAndFlush(target);

        // Get the target
        restTargetMockMvc
            .perform(get(ENTITY_API_URL_ID, target.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(target.getId().intValue()))
            .andExpect(jsonPath("$.titleUz").value(DEFAULT_TITLE_UZ))
            .andExpect(jsonPath("$.titleRu").value(DEFAULT_TITLE_RU))
            .andExpect(jsonPath("$.contentUz").value(DEFAULT_CONTENT_UZ))
            .andExpect(jsonPath("$.contentRu").value(DEFAULT_CONTENT_RU))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.booleanValue()));
    }

    @Test
    @Transactional
    void getNonExistingTarget() throws Exception {
        // Get the target
        restTargetMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingTarget() throws Exception {
        // Initialize the database
        targetRepository.saveAndFlush(target);

        int databaseSizeBeforeUpdate = targetRepository.findAll().size();

        // Update the target
        Target updatedTarget = targetRepository.findById(target.getId()).get();
        // Disconnect from session so that the updates on updatedTarget are not directly saved in db
        em.detach(updatedTarget);
        updatedTarget
            .titleUz(UPDATED_TITLE_UZ)
            .titleRu(UPDATED_TITLE_RU)
            .contentUz(UPDATED_CONTENT_UZ)
            .contentRu(UPDATED_CONTENT_RU)
            .status(UPDATED_STATUS);
        TargetDTO targetDTO = targetMapper.toDto(updatedTarget);

        restTargetMockMvc
            .perform(
                put(ENTITY_API_URL_ID, targetDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(targetDTO))
            )
            .andExpect(status().isOk());

        // Validate the Target in the database
        List<Target> targetList = targetRepository.findAll();
        assertThat(targetList).hasSize(databaseSizeBeforeUpdate);
        Target testTarget = targetList.get(targetList.size() - 1);
        assertThat(testTarget.getTitleUz()).isEqualTo(UPDATED_TITLE_UZ);
        assertThat(testTarget.getTitleRu()).isEqualTo(UPDATED_TITLE_RU);
        assertThat(testTarget.getContentUz()).isEqualTo(UPDATED_CONTENT_UZ);
        assertThat(testTarget.getContentRu()).isEqualTo(UPDATED_CONTENT_RU);
        assertThat(testTarget.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    void putNonExistingTarget() throws Exception {
        int databaseSizeBeforeUpdate = targetRepository.findAll().size();
        target.setId(count.incrementAndGet());

        // Create the Target
        TargetDTO targetDTO = targetMapper.toDto(target);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTargetMockMvc
            .perform(
                put(ENTITY_API_URL_ID, targetDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(targetDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Target in the database
        List<Target> targetList = targetRepository.findAll();
        assertThat(targetList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchTarget() throws Exception {
        int databaseSizeBeforeUpdate = targetRepository.findAll().size();
        target.setId(count.incrementAndGet());

        // Create the Target
        TargetDTO targetDTO = targetMapper.toDto(target);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTargetMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(targetDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Target in the database
        List<Target> targetList = targetRepository.findAll();
        assertThat(targetList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamTarget() throws Exception {
        int databaseSizeBeforeUpdate = targetRepository.findAll().size();
        target.setId(count.incrementAndGet());

        // Create the Target
        TargetDTO targetDTO = targetMapper.toDto(target);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTargetMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(targetDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Target in the database
        List<Target> targetList = targetRepository.findAll();
        assertThat(targetList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateTargetWithPatch() throws Exception {
        // Initialize the database
        targetRepository.saveAndFlush(target);

        int databaseSizeBeforeUpdate = targetRepository.findAll().size();

        // Update the target using partial update
        Target partialUpdatedTarget = new Target();
        partialUpdatedTarget.setId(target.getId());

        partialUpdatedTarget
            .titleUz(UPDATED_TITLE_UZ)
            .titleRu(UPDATED_TITLE_RU)
            .contentUz(UPDATED_CONTENT_UZ)
            .contentRu(UPDATED_CONTENT_RU)
            .status(UPDATED_STATUS);

        restTargetMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTarget.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTarget))
            )
            .andExpect(status().isOk());

        // Validate the Target in the database
        List<Target> targetList = targetRepository.findAll();
        assertThat(targetList).hasSize(databaseSizeBeforeUpdate);
        Target testTarget = targetList.get(targetList.size() - 1);
        assertThat(testTarget.getTitleUz()).isEqualTo(UPDATED_TITLE_UZ);
        assertThat(testTarget.getTitleRu()).isEqualTo(UPDATED_TITLE_RU);
        assertThat(testTarget.getContentUz()).isEqualTo(UPDATED_CONTENT_UZ);
        assertThat(testTarget.getContentRu()).isEqualTo(UPDATED_CONTENT_RU);
        assertThat(testTarget.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    void fullUpdateTargetWithPatch() throws Exception {
        // Initialize the database
        targetRepository.saveAndFlush(target);

        int databaseSizeBeforeUpdate = targetRepository.findAll().size();

        // Update the target using partial update
        Target partialUpdatedTarget = new Target();
        partialUpdatedTarget.setId(target.getId());

        partialUpdatedTarget
            .titleUz(UPDATED_TITLE_UZ)
            .titleRu(UPDATED_TITLE_RU)
            .contentUz(UPDATED_CONTENT_UZ)
            .contentRu(UPDATED_CONTENT_RU)
            .status(UPDATED_STATUS);

        restTargetMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTarget.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTarget))
            )
            .andExpect(status().isOk());

        // Validate the Target in the database
        List<Target> targetList = targetRepository.findAll();
        assertThat(targetList).hasSize(databaseSizeBeforeUpdate);
        Target testTarget = targetList.get(targetList.size() - 1);
        assertThat(testTarget.getTitleUz()).isEqualTo(UPDATED_TITLE_UZ);
        assertThat(testTarget.getTitleRu()).isEqualTo(UPDATED_TITLE_RU);
        assertThat(testTarget.getContentUz()).isEqualTo(UPDATED_CONTENT_UZ);
        assertThat(testTarget.getContentRu()).isEqualTo(UPDATED_CONTENT_RU);
        assertThat(testTarget.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    void patchNonExistingTarget() throws Exception {
        int databaseSizeBeforeUpdate = targetRepository.findAll().size();
        target.setId(count.incrementAndGet());

        // Create the Target
        TargetDTO targetDTO = targetMapper.toDto(target);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTargetMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, targetDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(targetDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Target in the database
        List<Target> targetList = targetRepository.findAll();
        assertThat(targetList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchTarget() throws Exception {
        int databaseSizeBeforeUpdate = targetRepository.findAll().size();
        target.setId(count.incrementAndGet());

        // Create the Target
        TargetDTO targetDTO = targetMapper.toDto(target);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTargetMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(targetDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Target in the database
        List<Target> targetList = targetRepository.findAll();
        assertThat(targetList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamTarget() throws Exception {
        int databaseSizeBeforeUpdate = targetRepository.findAll().size();
        target.setId(count.incrementAndGet());

        // Create the Target
        TargetDTO targetDTO = targetMapper.toDto(target);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTargetMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(targetDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Target in the database
        List<Target> targetList = targetRepository.findAll();
        assertThat(targetList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteTarget() throws Exception {
        // Initialize the database
        targetRepository.saveAndFlush(target);

        int databaseSizeBeforeDelete = targetRepository.findAll().size();

        // Delete the target
        restTargetMockMvc
            .perform(delete(ENTITY_API_URL_ID, target.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Target> targetList = targetRepository.findAll();
        assertThat(targetList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
