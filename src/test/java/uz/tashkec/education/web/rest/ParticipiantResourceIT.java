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
import uz.tashkec.education.domain.Participiant;
import uz.tashkec.education.repository.ParticipiantRepository;
import uz.tashkec.education.service.dto.ParticipiantDTO;
import uz.tashkec.education.service.mapper.ParticipiantMapper;

/**
 * Integration tests for the {@link ParticipiantResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ParticipiantResourceIT {

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

    private static final String ENTITY_API_URL = "/api/participiants";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ParticipiantRepository participiantRepository;

    @Autowired
    private ParticipiantMapper participiantMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restParticipiantMockMvc;

    private Participiant participiant;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Participiant createEntity(EntityManager em) {
        Participiant participiant = new Participiant()
            .titleUz(DEFAULT_TITLE_UZ)
            .titleRu(DEFAULT_TITLE_RU)
            .contentUz(DEFAULT_CONTENT_UZ)
            .contentRu(DEFAULT_CONTENT_RU)
            .status(DEFAULT_STATUS);
        return participiant;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Participiant createUpdatedEntity(EntityManager em) {
        Participiant participiant = new Participiant()
            .titleUz(UPDATED_TITLE_UZ)
            .titleRu(UPDATED_TITLE_RU)
            .contentUz(UPDATED_CONTENT_UZ)
            .contentRu(UPDATED_CONTENT_RU)
            .status(UPDATED_STATUS);
        return participiant;
    }

    @BeforeEach
    public void initTest() {
        participiant = createEntity(em);
    }

    @Test
    @Transactional
    void createParticipiant() throws Exception {
        int databaseSizeBeforeCreate = participiantRepository.findAll().size();
        // Create the Participiant
        ParticipiantDTO participiantDTO = participiantMapper.toDto(participiant);
        restParticipiantMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(participiantDTO))
            )
            .andExpect(status().isCreated());

        // Validate the Participiant in the database
        List<Participiant> participiantList = participiantRepository.findAll();
        assertThat(participiantList).hasSize(databaseSizeBeforeCreate + 1);
        Participiant testParticipiant = participiantList.get(participiantList.size() - 1);
        assertThat(testParticipiant.getTitleUz()).isEqualTo(DEFAULT_TITLE_UZ);
        assertThat(testParticipiant.getTitleRu()).isEqualTo(DEFAULT_TITLE_RU);
        assertThat(testParticipiant.getContentUz()).isEqualTo(DEFAULT_CONTENT_UZ);
        assertThat(testParticipiant.getContentRu()).isEqualTo(DEFAULT_CONTENT_RU);
        assertThat(testParticipiant.getStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    void createParticipiantWithExistingId() throws Exception {
        // Create the Participiant with an existing ID
        participiant.setId(1L);
        ParticipiantDTO participiantDTO = participiantMapper.toDto(participiant);

        int databaseSizeBeforeCreate = participiantRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restParticipiantMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(participiantDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Participiant in the database
        List<Participiant> participiantList = participiantRepository.findAll();
        assertThat(participiantList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkContentUzIsRequired() throws Exception {
        int databaseSizeBeforeTest = participiantRepository.findAll().size();
        // set the field null
        participiant.setContentUz(null);

        // Create the Participiant, which fails.
        ParticipiantDTO participiantDTO = participiantMapper.toDto(participiant);

        restParticipiantMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(participiantDTO))
            )
            .andExpect(status().isBadRequest());

        List<Participiant> participiantList = participiantRepository.findAll();
        assertThat(participiantList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkContentRuIsRequired() throws Exception {
        int databaseSizeBeforeTest = participiantRepository.findAll().size();
        // set the field null
        participiant.setContentRu(null);

        // Create the Participiant, which fails.
        ParticipiantDTO participiantDTO = participiantMapper.toDto(participiant);

        restParticipiantMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(participiantDTO))
            )
            .andExpect(status().isBadRequest());

        List<Participiant> participiantList = participiantRepository.findAll();
        assertThat(participiantList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = participiantRepository.findAll().size();
        // set the field null
        participiant.setStatus(null);

        // Create the Participiant, which fails.
        ParticipiantDTO participiantDTO = participiantMapper.toDto(participiant);

        restParticipiantMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(participiantDTO))
            )
            .andExpect(status().isBadRequest());

        List<Participiant> participiantList = participiantRepository.findAll();
        assertThat(participiantList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllParticipiants() throws Exception {
        // Initialize the database
        participiantRepository.saveAndFlush(participiant);

        // Get all the participiantList
        restParticipiantMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(participiant.getId().intValue())))
            .andExpect(jsonPath("$.[*].titleUz").value(hasItem(DEFAULT_TITLE_UZ)))
            .andExpect(jsonPath("$.[*].titleRu").value(hasItem(DEFAULT_TITLE_RU)))
            .andExpect(jsonPath("$.[*].contentUz").value(hasItem(DEFAULT_CONTENT_UZ)))
            .andExpect(jsonPath("$.[*].contentRu").value(hasItem(DEFAULT_CONTENT_RU)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.booleanValue())));
    }

    @Test
    @Transactional
    void getParticipiant() throws Exception {
        // Initialize the database
        participiantRepository.saveAndFlush(participiant);

        // Get the participiant
        restParticipiantMockMvc
            .perform(get(ENTITY_API_URL_ID, participiant.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(participiant.getId().intValue()))
            .andExpect(jsonPath("$.titleUz").value(DEFAULT_TITLE_UZ))
            .andExpect(jsonPath("$.titleRu").value(DEFAULT_TITLE_RU))
            .andExpect(jsonPath("$.contentUz").value(DEFAULT_CONTENT_UZ))
            .andExpect(jsonPath("$.contentRu").value(DEFAULT_CONTENT_RU))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.booleanValue()));
    }

    @Test
    @Transactional
    void getNonExistingParticipiant() throws Exception {
        // Get the participiant
        restParticipiantMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingParticipiant() throws Exception {
        // Initialize the database
        participiantRepository.saveAndFlush(participiant);

        int databaseSizeBeforeUpdate = participiantRepository.findAll().size();

        // Update the participiant
        Participiant updatedParticipiant = participiantRepository.findById(participiant.getId()).get();
        // Disconnect from session so that the updates on updatedParticipiant are not directly saved in db
        em.detach(updatedParticipiant);
        updatedParticipiant
            .titleUz(UPDATED_TITLE_UZ)
            .titleRu(UPDATED_TITLE_RU)
            .contentUz(UPDATED_CONTENT_UZ)
            .contentRu(UPDATED_CONTENT_RU)
            .status(UPDATED_STATUS);
        ParticipiantDTO participiantDTO = participiantMapper.toDto(updatedParticipiant);

        restParticipiantMockMvc
            .perform(
                put(ENTITY_API_URL_ID, participiantDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(participiantDTO))
            )
            .andExpect(status().isOk());

        // Validate the Participiant in the database
        List<Participiant> participiantList = participiantRepository.findAll();
        assertThat(participiantList).hasSize(databaseSizeBeforeUpdate);
        Participiant testParticipiant = participiantList.get(participiantList.size() - 1);
        assertThat(testParticipiant.getTitleUz()).isEqualTo(UPDATED_TITLE_UZ);
        assertThat(testParticipiant.getTitleRu()).isEqualTo(UPDATED_TITLE_RU);
        assertThat(testParticipiant.getContentUz()).isEqualTo(UPDATED_CONTENT_UZ);
        assertThat(testParticipiant.getContentRu()).isEqualTo(UPDATED_CONTENT_RU);
        assertThat(testParticipiant.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    void putNonExistingParticipiant() throws Exception {
        int databaseSizeBeforeUpdate = participiantRepository.findAll().size();
        participiant.setId(count.incrementAndGet());

        // Create the Participiant
        ParticipiantDTO participiantDTO = participiantMapper.toDto(participiant);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restParticipiantMockMvc
            .perform(
                put(ENTITY_API_URL_ID, participiantDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(participiantDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Participiant in the database
        List<Participiant> participiantList = participiantRepository.findAll();
        assertThat(participiantList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchParticipiant() throws Exception {
        int databaseSizeBeforeUpdate = participiantRepository.findAll().size();
        participiant.setId(count.incrementAndGet());

        // Create the Participiant
        ParticipiantDTO participiantDTO = participiantMapper.toDto(participiant);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restParticipiantMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(participiantDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Participiant in the database
        List<Participiant> participiantList = participiantRepository.findAll();
        assertThat(participiantList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamParticipiant() throws Exception {
        int databaseSizeBeforeUpdate = participiantRepository.findAll().size();
        participiant.setId(count.incrementAndGet());

        // Create the Participiant
        ParticipiantDTO participiantDTO = participiantMapper.toDto(participiant);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restParticipiantMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(participiantDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Participiant in the database
        List<Participiant> participiantList = participiantRepository.findAll();
        assertThat(participiantList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateParticipiantWithPatch() throws Exception {
        // Initialize the database
        participiantRepository.saveAndFlush(participiant);

        int databaseSizeBeforeUpdate = participiantRepository.findAll().size();

        // Update the participiant using partial update
        Participiant partialUpdatedParticipiant = new Participiant();
        partialUpdatedParticipiant.setId(participiant.getId());

        partialUpdatedParticipiant.titleRu(UPDATED_TITLE_RU).contentUz(UPDATED_CONTENT_UZ).contentRu(UPDATED_CONTENT_RU);

        restParticipiantMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedParticipiant.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedParticipiant))
            )
            .andExpect(status().isOk());

        // Validate the Participiant in the database
        List<Participiant> participiantList = participiantRepository.findAll();
        assertThat(participiantList).hasSize(databaseSizeBeforeUpdate);
        Participiant testParticipiant = participiantList.get(participiantList.size() - 1);
        assertThat(testParticipiant.getTitleUz()).isEqualTo(DEFAULT_TITLE_UZ);
        assertThat(testParticipiant.getTitleRu()).isEqualTo(UPDATED_TITLE_RU);
        assertThat(testParticipiant.getContentUz()).isEqualTo(UPDATED_CONTENT_UZ);
        assertThat(testParticipiant.getContentRu()).isEqualTo(UPDATED_CONTENT_RU);
        assertThat(testParticipiant.getStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    void fullUpdateParticipiantWithPatch() throws Exception {
        // Initialize the database
        participiantRepository.saveAndFlush(participiant);

        int databaseSizeBeforeUpdate = participiantRepository.findAll().size();

        // Update the participiant using partial update
        Participiant partialUpdatedParticipiant = new Participiant();
        partialUpdatedParticipiant.setId(participiant.getId());

        partialUpdatedParticipiant
            .titleUz(UPDATED_TITLE_UZ)
            .titleRu(UPDATED_TITLE_RU)
            .contentUz(UPDATED_CONTENT_UZ)
            .contentRu(UPDATED_CONTENT_RU)
            .status(UPDATED_STATUS);

        restParticipiantMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedParticipiant.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedParticipiant))
            )
            .andExpect(status().isOk());

        // Validate the Participiant in the database
        List<Participiant> participiantList = participiantRepository.findAll();
        assertThat(participiantList).hasSize(databaseSizeBeforeUpdate);
        Participiant testParticipiant = participiantList.get(participiantList.size() - 1);
        assertThat(testParticipiant.getTitleUz()).isEqualTo(UPDATED_TITLE_UZ);
        assertThat(testParticipiant.getTitleRu()).isEqualTo(UPDATED_TITLE_RU);
        assertThat(testParticipiant.getContentUz()).isEqualTo(UPDATED_CONTENT_UZ);
        assertThat(testParticipiant.getContentRu()).isEqualTo(UPDATED_CONTENT_RU);
        assertThat(testParticipiant.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    void patchNonExistingParticipiant() throws Exception {
        int databaseSizeBeforeUpdate = participiantRepository.findAll().size();
        participiant.setId(count.incrementAndGet());

        // Create the Participiant
        ParticipiantDTO participiantDTO = participiantMapper.toDto(participiant);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restParticipiantMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, participiantDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(participiantDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Participiant in the database
        List<Participiant> participiantList = participiantRepository.findAll();
        assertThat(participiantList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchParticipiant() throws Exception {
        int databaseSizeBeforeUpdate = participiantRepository.findAll().size();
        participiant.setId(count.incrementAndGet());

        // Create the Participiant
        ParticipiantDTO participiantDTO = participiantMapper.toDto(participiant);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restParticipiantMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(participiantDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Participiant in the database
        List<Participiant> participiantList = participiantRepository.findAll();
        assertThat(participiantList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamParticipiant() throws Exception {
        int databaseSizeBeforeUpdate = participiantRepository.findAll().size();
        participiant.setId(count.incrementAndGet());

        // Create the Participiant
        ParticipiantDTO participiantDTO = participiantMapper.toDto(participiant);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restParticipiantMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(participiantDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Participiant in the database
        List<Participiant> participiantList = participiantRepository.findAll();
        assertThat(participiantList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteParticipiant() throws Exception {
        // Initialize the database
        participiantRepository.saveAndFlush(participiant);

        int databaseSizeBeforeDelete = participiantRepository.findAll().size();

        // Delete the participiant
        restParticipiantMockMvc
            .perform(delete(ENTITY_API_URL_ID, participiant.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Participiant> participiantList = participiantRepository.findAll();
        assertThat(participiantList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
