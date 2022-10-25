package br.com.history.service;

import br.com.history.domain.SiteImage;
import br.com.history.repository.SiteImageRepository;
import br.com.history.service.dto.SiteImage3DViewDTO;
import br.com.history.service.dto.SiteImageDTO;
import br.com.history.service.mapper.SiteImageMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link SiteImage}.
 */
@Service
@Transactional
public class SiteImageService {

    private final Logger log = LoggerFactory.getLogger(SiteImageService.class);

    private final SiteImageRepository siteImageRepository;

    private final SiteImageMapper siteImageMapper;

    public SiteImageService(SiteImageRepository siteImageRepository, SiteImageMapper siteImageMapper) {
        this.siteImageRepository = siteImageRepository;
        this.siteImageMapper = siteImageMapper;
    }

    /**
     * Save a siteImage.
     *
     * @param siteImageDTO the entity to save.
     * @return the persisted entity.
     */
    public SiteImageDTO save(SiteImageDTO siteImageDTO) {
        log.debug("Request to save SiteImage : {}", siteImageDTO);
        SiteImage siteImage = siteImageMapper.toEntity(siteImageDTO);
        siteImage.setYear(LocalDate.now().getYear());
        siteImage = siteImageRepository.save(siteImage);
        return siteImageMapper.toDto(siteImage);
    }

    /**
     * Partially update a siteImage.
     *
     * @param siteImageDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<SiteImageDTO> partialUpdate(SiteImageDTO siteImageDTO) {
        log.debug("Request to partially update SiteImage : {}", siteImageDTO);

        return siteImageRepository
            .findById(siteImageDTO.getId())
            .map(
                existingSiteImage -> {
                    siteImageMapper.partialUpdate(existingSiteImage, siteImageDTO);
                    return existingSiteImage;
                }
            )
            .map(siteImageRepository::save)
            .map(siteImageMapper::toDto);
    }

    /**
     * Get all the siteImages.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<SiteImageDTO> findAll(Pageable pageable) {
        log.debug("Request to get all SiteImages");
        return siteImageRepository.findAll(pageable).map(siteImageMapper::toDto);
    }

    /**
     * Get one siteImage by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<SiteImageDTO> findOne(Long id) {
        log.debug("Request to get SiteImage : {}", id);
        return siteImageRepository.findById(id).map(siteImageMapper::toDto);
    }

    /**
     * Delete the siteImage by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete SiteImage : {}", id);
        siteImageRepository.deleteById(id);
    }

    public List<SiteImage3DViewDTO> findAllYear(Long idHistoricalSite, Integer year) {
        return siteImageRepository.findAllByIdHistoricalSiteAndYear(idHistoricalSite, year);
    }

    public SiteImage3DViewDTO findByIdHistoricalSite(Long idHistoricalSite) {
        return siteImageRepository.findByIdHistoricalSite(idHistoricalSite);
    }
}
