package br.com.history.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import br.com.history.IntegrationTest;
import br.com.history.domain.SiteImage;
import br.com.history.repository.SiteImageRepository;
import br.com.history.service.dto.SiteImageDTO;
import br.com.history.service.mapper.SiteImageMapper;
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
 * Integration tests for the {@link SiteImageResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class SiteImageResourceIT {

    private static final Integer DEFAULT_NUMBER_IMAGE = 1;
    private static final Integer UPDATED_NUMBER_IMAGE = 2;

    private static final byte[] DEFAULT_IMAGE_3_D = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_IMAGE_3_D = TestUtil.createByteArray(1, "1");

    private static final byte[] DEFAULT_IMAGE_PREVIEW = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_IMAGE_PREVIEW = TestUtil.createByteArray(1, "1");

    private static final Integer DEFAULT_YEAR = LocalDate.ofEpochDay(0L).getYear();
    private static final Integer UPDATED_YEAR = LocalDate.now(ZoneId.systemDefault()).getYear();

    private static final String ENTITY_API_URL = "/api/site-images";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private SiteImageRepository siteImageRepository;

    @Autowired
    private SiteImageMapper siteImageMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSiteImageMockMvc;

    private SiteImage siteImage;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SiteImage createEntity(EntityManager em) {
        return new SiteImage()
            .numberImage(DEFAULT_NUMBER_IMAGE)
            .image3D(DEFAULT_IMAGE_3_D)
            .imagePreview(DEFAULT_IMAGE_PREVIEW)
            .year(DEFAULT_YEAR);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SiteImage createUpdatedEntity(EntityManager em) {
        return new SiteImage()
            .numberImage(UPDATED_NUMBER_IMAGE)
            .image3D(UPDATED_IMAGE_3_D)
            .imagePreview(UPDATED_IMAGE_PREVIEW)
            .year(UPDATED_YEAR);
    }

    @BeforeEach
    public void initTest() {
        siteImage = createEntity(em);
    }

    @Test
    @Transactional
    void createSiteImage() throws Exception {
        int databaseSizeBeforeCreate = siteImageRepository.findAll().size();
        // Create the SiteImage
        SiteImageDTO siteImageDTO = siteImageMapper.toDto(siteImage);
        restSiteImageMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(siteImageDTO)))
            .andExpect(status().isCreated());

        // Validate the SiteImage in the database
        List<SiteImage> siteImageList = siteImageRepository.findAll();
        assertThat(siteImageList).hasSize(databaseSizeBeforeCreate + 1);
        SiteImage testSiteImage = siteImageList.get(siteImageList.size() - 1);
        assertThat(testSiteImage.getNumberImage()).isEqualTo(DEFAULT_NUMBER_IMAGE);
        assertThat(testSiteImage.getImage3D()).isEqualTo(DEFAULT_IMAGE_3_D);
        assertThat(testSiteImage.getImagePreview()).isEqualTo(DEFAULT_IMAGE_PREVIEW);
        assertThat(testSiteImage.getYear()).isEqualTo(DEFAULT_YEAR);
    }

    @Test
    @Transactional
    void createSiteImageWithExistingId() throws Exception {
        // Create the SiteImage with an existing ID
        siteImage.setId(1L);
        SiteImageDTO siteImageDTO = siteImageMapper.toDto(siteImage);

        int databaseSizeBeforeCreate = siteImageRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restSiteImageMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(siteImageDTO)))
            .andExpect(status().isBadRequest());

        // Validate the SiteImage in the database
        List<SiteImage> siteImageList = siteImageRepository.findAll();
        assertThat(siteImageList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllSiteImages() throws Exception {
        // Initialize the database
        siteImageRepository.saveAndFlush(siteImage);

        // Get all the siteImageList
        restSiteImageMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(siteImage.getId().intValue())))
            .andExpect(jsonPath("$.[*].numberImage").value(hasItem(DEFAULT_NUMBER_IMAGE)))
            .andExpect(jsonPath("$.[*].image3D").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMAGE_3_D))))
            .andExpect(jsonPath("$.[*].imagePreview").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMAGE_PREVIEW))))
            .andExpect(jsonPath("$.[*].year").value(hasItem(DEFAULT_YEAR.toString())));
    }

    @Test
    @Transactional
    void getSiteImage() throws Exception {
        // Initialize the database
        siteImageRepository.saveAndFlush(siteImage);

        // Get the siteImage
        restSiteImageMockMvc
            .perform(get(ENTITY_API_URL_ID, siteImage.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(siteImage.getId().intValue()))
            .andExpect(jsonPath("$.numberImage").value(DEFAULT_NUMBER_IMAGE))
            .andExpect(jsonPath("$.image3D").value(Base64Utils.encodeToString(DEFAULT_IMAGE_3_D)))
            .andExpect(jsonPath("$.imagePreview").value(Base64Utils.encodeToString(DEFAULT_IMAGE_PREVIEW)))
            .andExpect(jsonPath("$.year").value(DEFAULT_YEAR.toString()));
    }

    @Test
    @Transactional
    void getNonExistingSiteImage() throws Exception {
        // Get the siteImage
        restSiteImageMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewSiteImage() throws Exception {
        // Initialize the database
        siteImageRepository.saveAndFlush(siteImage);

        int databaseSizeBeforeUpdate = siteImageRepository.findAll().size();

        // Update the siteImage
        SiteImage updatedSiteImage = siteImageRepository.findById(siteImage.getId()).get();
        // Disconnect from session so that the updates on updatedSiteImage are not directly saved in db
        em.detach(updatedSiteImage);
        updatedSiteImage
            .numberImage(UPDATED_NUMBER_IMAGE)
            .image3D(UPDATED_IMAGE_3_D)
            .imagePreview(UPDATED_IMAGE_PREVIEW)
            .year(UPDATED_YEAR);
        SiteImageDTO siteImageDTO = siteImageMapper.toDto(updatedSiteImage);

        restSiteImageMockMvc
            .perform(
                put(ENTITY_API_URL_ID, siteImageDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(siteImageDTO))
            )
            .andExpect(status().isOk());

        // Validate the SiteImage in the database
        List<SiteImage> siteImageList = siteImageRepository.findAll();
        assertThat(siteImageList).hasSize(databaseSizeBeforeUpdate);
        SiteImage testSiteImage = siteImageList.get(siteImageList.size() - 1);
        assertThat(testSiteImage.getNumberImage()).isEqualTo(UPDATED_NUMBER_IMAGE);
        assertThat(testSiteImage.getImage3D()).isEqualTo(UPDATED_IMAGE_3_D);
        assertThat(testSiteImage.getImagePreview()).isEqualTo(UPDATED_IMAGE_PREVIEW);
        assertThat(testSiteImage.getYear()).isEqualTo(UPDATED_YEAR);
    }

    @Test
    @Transactional
    void putNonExistingSiteImage() throws Exception {
        int databaseSizeBeforeUpdate = siteImageRepository.findAll().size();
        siteImage.setId(count.incrementAndGet());

        // Create the SiteImage
        SiteImageDTO siteImageDTO = siteImageMapper.toDto(siteImage);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSiteImageMockMvc
            .perform(
                put(ENTITY_API_URL_ID, siteImageDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(siteImageDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SiteImage in the database
        List<SiteImage> siteImageList = siteImageRepository.findAll();
        assertThat(siteImageList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchSiteImage() throws Exception {
        int databaseSizeBeforeUpdate = siteImageRepository.findAll().size();
        siteImage.setId(count.incrementAndGet());

        // Create the SiteImage
        SiteImageDTO siteImageDTO = siteImageMapper.toDto(siteImage);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSiteImageMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(siteImageDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SiteImage in the database
        List<SiteImage> siteImageList = siteImageRepository.findAll();
        assertThat(siteImageList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamSiteImage() throws Exception {
        int databaseSizeBeforeUpdate = siteImageRepository.findAll().size();
        siteImage.setId(count.incrementAndGet());

        // Create the SiteImage
        SiteImageDTO siteImageDTO = siteImageMapper.toDto(siteImage);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSiteImageMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(siteImageDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the SiteImage in the database
        List<SiteImage> siteImageList = siteImageRepository.findAll();
        assertThat(siteImageList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateSiteImageWithPatch() throws Exception {
        // Initialize the database
        siteImageRepository.saveAndFlush(siteImage);

        int databaseSizeBeforeUpdate = siteImageRepository.findAll().size();

        // Update the siteImage using partial update
        SiteImage partialUpdatedSiteImage = new SiteImage();
        partialUpdatedSiteImage.setId(siteImage.getId());

        partialUpdatedSiteImage
            .imagePreview(UPDATED_IMAGE_PREVIEW)
            .year(UPDATED_YEAR);

        restSiteImageMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSiteImage.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSiteImage))
            )
            .andExpect(status().isOk());

        // Validate the SiteImage in the database
        List<SiteImage> siteImageList = siteImageRepository.findAll();
        assertThat(siteImageList).hasSize(databaseSizeBeforeUpdate);
        SiteImage testSiteImage = siteImageList.get(siteImageList.size() - 1);
        assertThat(testSiteImage.getNumberImage()).isEqualTo(DEFAULT_NUMBER_IMAGE);
        assertThat(testSiteImage.getImage3D()).isEqualTo(DEFAULT_IMAGE_3_D);
        assertThat(testSiteImage.getImagePreview()).isEqualTo(UPDATED_IMAGE_PREVIEW);
        assertThat(testSiteImage.getYear()).isEqualTo(UPDATED_YEAR);
    }

    @Test
    @Transactional
    void fullUpdateSiteImageWithPatch() throws Exception {
        // Initialize the database
        siteImageRepository.saveAndFlush(siteImage);

        int databaseSizeBeforeUpdate = siteImageRepository.findAll().size();

        // Update the siteImage using partial update
        SiteImage partialUpdatedSiteImage = new SiteImage();
        partialUpdatedSiteImage.setId(siteImage.getId());

        partialUpdatedSiteImage
            .numberImage(UPDATED_NUMBER_IMAGE)
            .image3D(UPDATED_IMAGE_3_D)
            .imagePreview(UPDATED_IMAGE_PREVIEW)
            .year(UPDATED_YEAR);

        restSiteImageMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSiteImage.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSiteImage))
            )
            .andExpect(status().isOk());

        // Validate the SiteImage in the database
        List<SiteImage> siteImageList = siteImageRepository.findAll();
        assertThat(siteImageList).hasSize(databaseSizeBeforeUpdate);
        SiteImage testSiteImage = siteImageList.get(siteImageList.size() - 1);
        assertThat(testSiteImage.getNumberImage()).isEqualTo(UPDATED_NUMBER_IMAGE);
        assertThat(testSiteImage.getImage3D()).isEqualTo(UPDATED_IMAGE_3_D);
        assertThat(testSiteImage.getImagePreview()).isEqualTo(UPDATED_IMAGE_PREVIEW);
        assertThat(testSiteImage.getYear()).isEqualTo(UPDATED_YEAR);
    }

    @Test
    @Transactional
    void patchNonExistingSiteImage() throws Exception {
        int databaseSizeBeforeUpdate = siteImageRepository.findAll().size();
        siteImage.setId(count.incrementAndGet());

        // Create the SiteImage
        SiteImageDTO siteImageDTO = siteImageMapper.toDto(siteImage);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSiteImageMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, siteImageDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(siteImageDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SiteImage in the database
        List<SiteImage> siteImageList = siteImageRepository.findAll();
        assertThat(siteImageList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchSiteImage() throws Exception {
        int databaseSizeBeforeUpdate = siteImageRepository.findAll().size();
        siteImage.setId(count.incrementAndGet());

        // Create the SiteImage
        SiteImageDTO siteImageDTO = siteImageMapper.toDto(siteImage);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSiteImageMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(siteImageDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SiteImage in the database
        List<SiteImage> siteImageList = siteImageRepository.findAll();
        assertThat(siteImageList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamSiteImage() throws Exception {
        int databaseSizeBeforeUpdate = siteImageRepository.findAll().size();
        siteImage.setId(count.incrementAndGet());

        // Create the SiteImage
        SiteImageDTO siteImageDTO = siteImageMapper.toDto(siteImage);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSiteImageMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(siteImageDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the SiteImage in the database
        List<SiteImage> siteImageList = siteImageRepository.findAll();
        assertThat(siteImageList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteSiteImage() throws Exception {
        // Initialize the database
        siteImageRepository.saveAndFlush(siteImage);

        int databaseSizeBeforeDelete = siteImageRepository.findAll().size();

        // Delete the siteImage
        restSiteImageMockMvc
            .perform(delete(ENTITY_API_URL_ID, siteImage.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<SiteImage> siteImageList = siteImageRepository.findAll();
        assertThat(siteImageList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
