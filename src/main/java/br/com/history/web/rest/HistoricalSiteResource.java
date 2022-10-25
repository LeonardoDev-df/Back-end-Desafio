package br.com.history.web.rest;

import br.com.history.repository.HistoricalSiteRepository;
import br.com.history.service.HistoricalSiteService;
import br.com.history.service.dto.HistoricalSiteDTO;
import br.com.history.service.dto.HistoricalSiteIdDTO;
import br.com.history.service.dto.HistoricalSiteMapDTO;
import br.com.history.service.dto.HistoricalSiteUpdateStatusDTO;
import br.com.history.web.rest.errors.BadRequestAlertException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * REST controller for managing {@link br.com.history.domain.HistoricalSite}.
 */
@RestController
@RequestMapping("/api")
public class HistoricalSiteResource {

    private final Logger log = LoggerFactory.getLogger(HistoricalSiteResource.class);

    private static final String ENTITY_NAME = "historicalSite";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final HistoricalSiteService historicalSiteService;
    private final HistoricalSiteRepository historicalSiteRepository;

    public HistoricalSiteResource(HistoricalSiteService historicalSiteService, HistoricalSiteRepository historicalSiteRepository) {
        this.historicalSiteService = historicalSiteService;
        this.historicalSiteRepository = historicalSiteRepository;
    }

    /**
     * {@code POST  /historical-sites} : Create a new historicalSite.
     *
     * @param historicalSiteDTO the historicalSiteDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new historicalSiteDTO,
     * or with status {@code 400 (Bad Request)} if the historicalSite has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/historical-sites")
    public ResponseEntity<HistoricalSiteDTO> createHistoricalSite(@RequestBody HistoricalSiteDTO historicalSiteDTO)
        throws URISyntaxException {
        log.debug("REST request to save HistoricalSite : {}", historicalSiteDTO);
        if (historicalSiteDTO.getId() != null) {
            throw new BadRequestAlertException("A new historicalSite cannot already have an ID", ENTITY_NAME, "idexists");
        }
        HistoricalSiteDTO result = historicalSiteService.save(historicalSiteDTO);
        return ResponseEntity
            .created(new URI("/api/historical-sites/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /historical-sites/:id} : Updates an existing historicalSite.
     *
     * @param id the id of the historicalSiteDTO to save.
     * @param historicalSiteDTO the historicalSiteDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated historicalSiteDTO,
     * or with status {@code 400 (Bad Request)} if the historicalSiteDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the historicalSiteDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/historical-sites/{id}")
    public ResponseEntity<HistoricalSiteDTO> updateHistoricalSite(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody HistoricalSiteDTO historicalSiteDTO
    ) throws URISyntaxException {
        log.debug("REST request to update HistoricalSite : {}, {}", id, historicalSiteDTO);
        if (historicalSiteDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, historicalSiteDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!historicalSiteRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        HistoricalSiteDTO result = historicalSiteService.save(historicalSiteDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, historicalSiteDTO.getId().toString()))
            .body(result);
    }

    @PutMapping("/historical-sites/status/{id}")
    public ResponseEntity<HistoricalSiteDTO> updateHistoricalSiteStatus(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody HistoricalSiteUpdateStatusDTO historicalSiteDTO
    ) throws URISyntaxException {
        log.debug("REST request to update HistoricalSite : {}, {}", id, historicalSiteDTO);

        if (!historicalSiteRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        HistoricalSiteDTO result = historicalSiteService.attHistoricalSite(id, historicalSiteDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /historical-sites/:id} : Partial updates given fields of an existing historicalSite, field will ignore if it is null
     *
     * @param id the id of the historicalSiteDTO to save.
     * @param historicalSiteDTO the historicalSiteDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated historicalSiteDTO,
     * or with status {@code 400 (Bad Request)} if the historicalSiteDTO is not valid,
     * or with status {@code 404 (Not Found)} if the historicalSiteDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the historicalSiteDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/historical-sites/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<HistoricalSiteDTO> partialUpdateHistoricalSite(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody HistoricalSiteDTO historicalSiteDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update HistoricalSite partially : {}, {}", id, historicalSiteDTO);
        if (historicalSiteDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, historicalSiteDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!historicalSiteRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<HistoricalSiteDTO> result = historicalSiteService.partialUpdate(historicalSiteDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, historicalSiteDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /historical-sites} : get all the historicalSites.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of historicalSites in body.
     */
    @GetMapping("/historical-sites")
    public ResponseEntity<List<HistoricalSiteDTO>> getAllHistoricalSites(Pageable pageable) {
        log.debug("REST request to get a page of HistoricalSites");
        Page<HistoricalSiteDTO> page = historicalSiteService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("/historical-sites/map")
    public ResponseEntity<List<HistoricalSiteMapDTO>> getAllHistoricalSitesMap() {
        log.debug("REST request to get a page of HistoricalSites");
        List<HistoricalSiteMapDTO> historicalSitesMap = historicalSiteService.findAllMap();
        return ResponseEntity.ok().body(historicalSitesMap);
    }

    @GetMapping("/historical-sites/map/filter")
    public ResponseEntity<List<HistoricalSiteMapDTO>> getAllHistoricalSitesByFilter(String filter) {
        log.debug("REST request to get a list of HistoricalSites by filter");
        List<HistoricalSiteMapDTO> historicalSitesMap = historicalSiteService.findAllByFilter(filter);
        return ResponseEntity.ok().body(historicalSitesMap);
    }

    /**
     * {@code GET  /historical-sites/:id} : get the "id" historicalSite.
     *
     * @param id the id of the historicalSiteDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the historicalSiteDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/historical-sites/{id}")
    public ResponseEntity<HistoricalSiteIdDTO> getHistoricalSite(@PathVariable Long id) {
        log.debug("REST request to get HistoricalSite : {}", id);
        HistoricalSiteIdDTO historicalSiteDTO = historicalSiteService.findOne(id);
        return ResponseEntity.ok(historicalSiteDTO);
    }

    @GetMapping("/historical-sites/user/{idUser}")
    public ResponseEntity<List<HistoricalSiteDTO>> getAllHistoricalSiteByIdUser(@PathVariable Long idUser) {
        log.debug("REST request to get All HistoricalSite by idUser : {}", idUser);
        List<HistoricalSiteDTO> historicalSitesDTO = historicalSiteService.findAllHistoricalSiteByIdUSer(idUser);
        return ResponseEntity.ok(historicalSitesDTO);
    }

    /**
     * {@code DELETE  /historical-sites/:id} : delete the "id" historicalSite.
     *
     * @param id the id of the historicalSiteDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/historical-sites/{id}")
    public ResponseEntity<Void> deleteHistoricalSite(@PathVariable Long id) {
        log.debug("REST request to delete HistoricalSite : {}", id);
        historicalSiteService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
