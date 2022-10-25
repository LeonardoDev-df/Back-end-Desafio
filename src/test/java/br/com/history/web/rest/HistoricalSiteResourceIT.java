package br.com.history.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import br.com.history.IntegrationTest;
import br.com.history.domain.HistoricalSite;
import br.com.history.domain.enumeration.StatusEnum;
import br.com.history.repository.HistoricalSiteRepository;
import br.com.history.service.dto.HistoricalSiteDTO;
import br.com.history.service.mapper.HistoricalSiteMapper;
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
 * Integration tests for the {@link HistoricalSiteResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class HistoricalSiteResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Integer DEFAULT_LIKE = 1;
    private static final Integer UPDATED_LIKE = 2;

    private static final String DEFAULT_COMMENT = "AAAAAAAAAA";
    private static final String UPDATED_COMMENT = "BBBBBBBBBB";

    private static final StatusEnum DEFAULT_STATUS = StatusEnum.EM_ANALISE;
    private static final StatusEnum UPDATED_STATUS = StatusEnum.ACEITO;

    private static final String ENTITY_API_URL = "/api/historical-sites";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private HistoricalSiteRepository historicalSiteRepository;

    @Autowired
    private HistoricalSiteMapper historicalSiteMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restHistoricalSiteMockMvc;

    private HistoricalSite historicalSite;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static HistoricalSite createEntity(EntityManager em) {
        return new HistoricalSite()
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION)
            .like(DEFAULT_LIKE)
            .comment(DEFAULT_COMMENT)
            .status(DEFAULT_STATUS);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static HistoricalSite createUpdatedEntity(EntityManager em) {
        return new HistoricalSite()
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .like(UPDATED_LIKE)
            .comment(UPDATED_COMMENT)
            .status(UPDATED_STATUS);
    }

    @BeforeEach
    public void initTest() {
        historicalSite = createEntity(em);
    }

    @Test
    @Transactional
    void createHistoricalSite() throws Exception {
        int databaseSizeBeforeCreate = historicalSiteRepository.findAll().size();
        // Create the HistoricalSite
        HistoricalSiteDTO historicalSiteDTO = historicalSiteMapper.toDto(historicalSite);
        restHistoricalSiteMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(historicalSiteDTO))
            )
            .andExpect(status().isCreated());

        // Validate the HistoricalSite in the database
        List<HistoricalSite> historicalSiteList = historicalSiteRepository.findAll();
        assertThat(historicalSiteList).hasSize(databaseSizeBeforeCreate + 1);
        HistoricalSite testHistoricalSite = historicalSiteList.get(historicalSiteList.size() - 1);
        assertThat(testHistoricalSite.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testHistoricalSite.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testHistoricalSite.getLike()).isEqualTo(DEFAULT_LIKE);
        assertThat(testHistoricalSite.getComment()).isEqualTo(DEFAULT_COMMENT);
        assertThat(testHistoricalSite.getStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    void createHistoricalSiteWithExistingId() throws Exception {
        // Create the HistoricalSite with an existing ID
        historicalSite.setId(1L);
        HistoricalSiteDTO historicalSiteDTO = historicalSiteMapper.toDto(historicalSite);

        int databaseSizeBeforeCreate = historicalSiteRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restHistoricalSiteMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(historicalSiteDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the HistoricalSite in the database
        List<HistoricalSite> historicalSiteList = historicalSiteRepository.findAll();
        assertThat(historicalSiteList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllHistoricalSites() throws Exception {
        // Initialize the database
        historicalSiteRepository.saveAndFlush(historicalSite);

        // Get all the historicalSiteList
        restHistoricalSiteMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(historicalSite.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].like").value(hasItem(DEFAULT_LIKE)))
            .andExpect(jsonPath("$.[*].comment").value(hasItem(DEFAULT_COMMENT)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())));
    }

    @Test
    @Transactional
    void getHistoricalSite() throws Exception {
        // Initialize the database
        historicalSiteRepository.saveAndFlush(historicalSite);

        // Get the historicalSite
        restHistoricalSiteMockMvc
            .perform(get(ENTITY_API_URL_ID, historicalSite.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(historicalSite.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.like").value(DEFAULT_LIKE))
            .andExpect(jsonPath("$.comment").value(DEFAULT_COMMENT))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()));
    }

    @Test
    @Transactional
    void getNonExistingHistoricalSite() throws Exception {
        // Get the historicalSite
        restHistoricalSiteMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewHistoricalSite() throws Exception {
        // Initialize the database
        historicalSiteRepository.saveAndFlush(historicalSite);

        int databaseSizeBeforeUpdate = historicalSiteRepository.findAll().size();

        // Update the historicalSite
        HistoricalSite updatedHistoricalSite = historicalSiteRepository.findById(historicalSite.getId()).get();
        // Disconnect from session so that the updates on updatedHistoricalSite are not directly saved in db
        em.detach(updatedHistoricalSite);
        updatedHistoricalSite
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .like(UPDATED_LIKE)
            .comment(UPDATED_COMMENT)
            .status(UPDATED_STATUS);
        HistoricalSiteDTO historicalSiteDTO = historicalSiteMapper.toDto(updatedHistoricalSite);

        restHistoricalSiteMockMvc
            .perform(
                put(ENTITY_API_URL_ID, historicalSiteDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(historicalSiteDTO))
            )
            .andExpect(status().isOk());

        // Validate the HistoricalSite in the database
        List<HistoricalSite> historicalSiteList = historicalSiteRepository.findAll();
        assertThat(historicalSiteList).hasSize(databaseSizeBeforeUpdate);
        HistoricalSite testHistoricalSite = historicalSiteList.get(historicalSiteList.size() - 1);
        assertThat(testHistoricalSite.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testHistoricalSite.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testHistoricalSite.getLike()).isEqualTo(UPDATED_LIKE);
        assertThat(testHistoricalSite.getComment()).isEqualTo(UPDATED_COMMENT);
        assertThat(testHistoricalSite.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    void putNonExistingHistoricalSite() throws Exception {
        int databaseSizeBeforeUpdate = historicalSiteRepository.findAll().size();
        historicalSite.setId(count.incrementAndGet());

        // Create the HistoricalSite
        HistoricalSiteDTO historicalSiteDTO = historicalSiteMapper.toDto(historicalSite);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restHistoricalSiteMockMvc
            .perform(
                put(ENTITY_API_URL_ID, historicalSiteDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(historicalSiteDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the HistoricalSite in the database
        List<HistoricalSite> historicalSiteList = historicalSiteRepository.findAll();
        assertThat(historicalSiteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchHistoricalSite() throws Exception {
        int databaseSizeBeforeUpdate = historicalSiteRepository.findAll().size();
        historicalSite.setId(count.incrementAndGet());

        // Create the HistoricalSite
        HistoricalSiteDTO historicalSiteDTO = historicalSiteMapper.toDto(historicalSite);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restHistoricalSiteMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(historicalSiteDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the HistoricalSite in the database
        List<HistoricalSite> historicalSiteList = historicalSiteRepository.findAll();
        assertThat(historicalSiteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamHistoricalSite() throws Exception {
        int databaseSizeBeforeUpdate = historicalSiteRepository.findAll().size();
        historicalSite.setId(count.incrementAndGet());

        // Create the HistoricalSite
        HistoricalSiteDTO historicalSiteDTO = historicalSiteMapper.toDto(historicalSite);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restHistoricalSiteMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(historicalSiteDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the HistoricalSite in the database
        List<HistoricalSite> historicalSiteList = historicalSiteRepository.findAll();
        assertThat(historicalSiteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateHistoricalSiteWithPatch() throws Exception {
        // Initialize the database
        historicalSiteRepository.saveAndFlush(historicalSite);

        int databaseSizeBeforeUpdate = historicalSiteRepository.findAll().size();

        // Update the historicalSite using partial update
        HistoricalSite partialUpdatedHistoricalSite = new HistoricalSite();
        partialUpdatedHistoricalSite.setId(historicalSite.getId());

        partialUpdatedHistoricalSite.description(UPDATED_DESCRIPTION).comment(UPDATED_COMMENT).status(UPDATED_STATUS);

        restHistoricalSiteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedHistoricalSite.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedHistoricalSite))
            )
            .andExpect(status().isOk());

        // Validate the HistoricalSite in the database
        List<HistoricalSite> historicalSiteList = historicalSiteRepository.findAll();
        assertThat(historicalSiteList).hasSize(databaseSizeBeforeUpdate);
        HistoricalSite testHistoricalSite = historicalSiteList.get(historicalSiteList.size() - 1);
        assertThat(testHistoricalSite.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testHistoricalSite.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testHistoricalSite.getLike()).isEqualTo(DEFAULT_LIKE);
        assertThat(testHistoricalSite.getComment()).isEqualTo(UPDATED_COMMENT);
        assertThat(testHistoricalSite.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    void fullUpdateHistoricalSiteWithPatch() throws Exception {
        // Initialize the database
        historicalSiteRepository.saveAndFlush(historicalSite);

        int databaseSizeBeforeUpdate = historicalSiteRepository.findAll().size();

        // Update the historicalSite using partial update
        HistoricalSite partialUpdatedHistoricalSite = new HistoricalSite();
        partialUpdatedHistoricalSite.setId(historicalSite.getId());

        partialUpdatedHistoricalSite
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .like(UPDATED_LIKE)
            .comment(UPDATED_COMMENT)
            .status(UPDATED_STATUS);

        restHistoricalSiteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedHistoricalSite.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedHistoricalSite))
            )
            .andExpect(status().isOk());

        // Validate the HistoricalSite in the database
        List<HistoricalSite> historicalSiteList = historicalSiteRepository.findAll();
        assertThat(historicalSiteList).hasSize(databaseSizeBeforeUpdate);
        HistoricalSite testHistoricalSite = historicalSiteList.get(historicalSiteList.size() - 1);
        assertThat(testHistoricalSite.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testHistoricalSite.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testHistoricalSite.getLike()).isEqualTo(UPDATED_LIKE);
        assertThat(testHistoricalSite.getComment()).isEqualTo(UPDATED_COMMENT);
        assertThat(testHistoricalSite.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    void patchNonExistingHistoricalSite() throws Exception {
        int databaseSizeBeforeUpdate = historicalSiteRepository.findAll().size();
        historicalSite.setId(count.incrementAndGet());

        // Create the HistoricalSite
        HistoricalSiteDTO historicalSiteDTO = historicalSiteMapper.toDto(historicalSite);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restHistoricalSiteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, historicalSiteDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(historicalSiteDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the HistoricalSite in the database
        List<HistoricalSite> historicalSiteList = historicalSiteRepository.findAll();
        assertThat(historicalSiteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchHistoricalSite() throws Exception {
        int databaseSizeBeforeUpdate = historicalSiteRepository.findAll().size();
        historicalSite.setId(count.incrementAndGet());

        // Create the HistoricalSite
        HistoricalSiteDTO historicalSiteDTO = historicalSiteMapper.toDto(historicalSite);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restHistoricalSiteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(historicalSiteDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the HistoricalSite in the database
        List<HistoricalSite> historicalSiteList = historicalSiteRepository.findAll();
        assertThat(historicalSiteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamHistoricalSite() throws Exception {
        int databaseSizeBeforeUpdate = historicalSiteRepository.findAll().size();
        historicalSite.setId(count.incrementAndGet());

        // Create the HistoricalSite
        HistoricalSiteDTO historicalSiteDTO = historicalSiteMapper.toDto(historicalSite);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restHistoricalSiteMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(historicalSiteDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the HistoricalSite in the database
        List<HistoricalSite> historicalSiteList = historicalSiteRepository.findAll();
        assertThat(historicalSiteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteHistoricalSite() throws Exception {
        // Initialize the database
        historicalSiteRepository.saveAndFlush(historicalSite);

        int databaseSizeBeforeDelete = historicalSiteRepository.findAll().size();

        // Delete the historicalSite
        restHistoricalSiteMockMvc
            .perform(delete(ENTITY_API_URL_ID, historicalSite.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<HistoricalSite> historicalSiteList = historicalSiteRepository.findAll();
        assertThat(historicalSiteList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
