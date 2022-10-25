package br.com.history.web.rest;

import br.com.history.repository.SiteImageRepository;
import br.com.history.service.SiteImageService;
import br.com.history.service.dto.SiteImage3DViewDTO;
import br.com.history.service.dto.SiteImageDTO;
import br.com.history.service.mapper.SiteImageMapper;
import br.com.history.web.rest.errors.BadRequestAlertException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * REST controller for managing {@link br.com.history.domain.SiteImage}.
 */
@RestController
@RequestMapping("/api")
public class SiteImageResource {

    private final Logger log = LoggerFactory.getLogger(SiteImageResource.class);

    private static final String ENTITY_NAME = "siteImage";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SiteImageService siteImageService;
    private final SiteImageRepository siteImageRepository;
    private final SiteImageMapper siteImageMapper;

    public SiteImageResource(SiteImageService siteImageService,
                            SiteImageRepository siteImageRepository,
                            SiteImageMapper siteImageMapper)
    {
        this.siteImageService = siteImageService;
        this.siteImageRepository = siteImageRepository;
        this.siteImageMapper = siteImageMapper;
    }

    /**
     * {@code POST  /site-images} : Create a new siteImage.
     *
     * @param siteImageDTO the siteImageDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new siteImageDTO,
     * or with status {@code 400 (Bad Request)} if the siteImage has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/site-images")
    public ResponseEntity<SiteImageDTO> createSiteImage(@RequestBody SiteImageDTO siteImageDTO) throws URISyntaxException {
        log.debug("REST request to save SiteImage : {}", siteImageDTO);
        if (siteImageDTO.getId() != null) {
            throw new BadRequestAlertException("A new siteImage cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SiteImageDTO result = siteImageService.save(siteImageDTO);
        return ResponseEntity
            .created(new URI("/api/site-images/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /site-images/upload/:id} : Updates an existing siteImage.
     *
     * @param id the id of the siteImageDTO to save.
     * @param image3D the Image3D to save.
     * @param imagePreview the imagePreview to save.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated siteImageDTO,
     * or with status {@code 400 (Bad Request)} if the siteImageDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the siteImageDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping(value = "/site-images/upload/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<SiteImageDTO> uploadSiteImage(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestPart MultipartFile image3D,
        @RequestPart MultipartFile imagePreview
    ) throws URISyntaxException, IOException {
        log.debug("REST request to upload SiteImage : {}, {}", id, image3D);

        if (!siteImageRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        SiteImageDTO siteImageDTO = siteImageMapper.toDto(siteImageRepository.findById(id).get());
        siteImageDTO.setImage3D(image3D.getBytes());
        siteImageDTO.setImagePreview(imagePreview.getBytes());
        SiteImageDTO result = siteImageService.save(siteImageDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, siteImageDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /site-images/:id} : Updates an existing siteImage.
     *
     * @param id the id of the siteImageDTO to save.
     * @param siteImageDTO the siteImageDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated siteImageDTO,
     * or with status {@code 400 (Bad Request)} if the siteImageDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the siteImageDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/site-images/{id}")
    public ResponseEntity<SiteImageDTO> updateSiteImage(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody SiteImageDTO siteImageDTO
    ) throws URISyntaxException {
        log.debug("REST request to update SiteImage : {}, {}", id, siteImageDTO);
        if (siteImageDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, siteImageDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!siteImageRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        SiteImageDTO result = siteImageService.save(siteImageDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, siteImageDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /site-images/:id} : Partial updates given fields of an existing siteImage, field will ignore if it is null
     *
     * @param id the id of the siteImageDTO to save.
     * @param siteImageDTO the siteImageDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated siteImageDTO,
     * or with status {@code 400 (Bad Request)} if the siteImageDTO is not valid,
     * or with status {@code 404 (Not Found)} if the siteImageDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the siteImageDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/site-images/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<SiteImageDTO> partialUpdateSiteImage(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody SiteImageDTO siteImageDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update SiteImage partially : {}, {}", id, siteImageDTO);
        if (siteImageDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, siteImageDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!siteImageRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<SiteImageDTO> result = siteImageService.partialUpdate(siteImageDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, siteImageDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /site-images} : get all the siteImages.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of siteImages in body.
     */
    @GetMapping("/site-images")
    public ResponseEntity<List<SiteImageDTO>> getAllSiteImages(Pageable pageable) {
        log.debug("REST request to get a page of SiteImages");
        Page<SiteImageDTO> page = siteImageService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /site-images/{idHistoricalSite}/{year}} : get all the siteImages.
     *
     * @param idHistoricalSite the id HistoricalSite.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of siteImages in body.
     */
    @GetMapping("/site-images/{idHistoricalSite}/{year}")
    public ResponseEntity<List<SiteImage3DViewDTO>> getAllSiteImagesYear(@PathVariable Long idHistoricalSite, @PathVariable Integer year) {
        log.debug("REST request to get a page of SiteImages");
        List<SiteImage3DViewDTO> siteImageList = siteImageService.findAllYear(idHistoricalSite, year);
        return ResponseEntity.ok().body(siteImageList);
    }

    /**
     * {@code GET  /site-images/:idHistoricalSite} : get the "id" siteImage.
     *
     * @param idHistoricalSite the id of the siteImageDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the siteImageDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/site-images/{idHistoricalSite}")
    public ResponseEntity<SiteImage3DViewDTO> getAllSiteImageByIdHistoricalSite(@PathVariable Long idHistoricalSite) {
        log.debug("REST request to get All SiteImage : {}", idHistoricalSite);
        SiteImage3DViewDTO siteImageList = siteImageService.findByIdHistoricalSite(idHistoricalSite);
        return ResponseEntity.ok().body(siteImageList);
    }

    /**
     * {@code DELETE  /site-images/:id} : delete the "id" siteImage.
     *
     * @param id the id of the siteImageDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/site-images/{id}")
    public ResponseEntity<Void> deleteSiteImage(@PathVariable Long id) {
        log.debug("REST request to delete SiteImage : {}", id);
        siteImageService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
