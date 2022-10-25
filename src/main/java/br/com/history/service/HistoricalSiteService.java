package br.com.history.service;

import br.com.history.client.LocationClient;
import br.com.history.domain.Address;
import br.com.history.domain.HistoricalSite;
import br.com.history.domain.enumeration.StatusEnum;
import br.com.history.repository.HistoricalSiteRepository;
import br.com.history.repository.SiteImageRepository;
import br.com.history.service.dto.HistoricalSiteDTO;
import br.com.history.service.dto.HistoricalSiteIdDTO;
import br.com.history.service.dto.HistoricalSiteMapDTO;
import br.com.history.service.dto.HistoricalSiteUpdateStatusDTO;
import br.com.history.service.dto.location.LocationRequest;
import br.com.history.service.mapper.HistoricalSiteMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link HistoricalSite}.
 */
@Service
@Transactional
public class HistoricalSiteService {

    private final Logger log = LoggerFactory.getLogger(HistoricalSiteService.class);

    private final HistoricalSiteRepository historicalSiteRepository;

    private final SiteImageRepository siteImageRepository;

    private final HistoricalSiteMapper historicalSiteMapper;

    private final LocationClient locationClient;

    public HistoricalSiteService(HistoricalSiteRepository historicalSiteRepository, SiteImageRepository siteImageRepository, HistoricalSiteMapper historicalSiteMapper, LocationClient locationClient) {
        this.historicalSiteRepository = historicalSiteRepository;
        this.siteImageRepository = siteImageRepository;
        this.historicalSiteMapper = historicalSiteMapper;
        this.locationClient = locationClient;
    }

    /**
     * Save a historicalSite.
     *
     * @param historicalSiteDTO the entity to save.
     * @return the persisted entity.
     */
    public HistoricalSiteDTO save(HistoricalSiteDTO historicalSiteDTO) {
        log.debug("Request to save HistoricalSite : {}", historicalSiteDTO);
        HistoricalSite historicalSite = historicalSiteMapper.toEntity(historicalSiteDTO);
        historicalSite.setStatus(StatusEnum.EM_ANALISE);
        historicalSite.setAddress(endereco(historicalSite.getAddress()));
        historicalSite.getSiteImages()
            .stream()
            .map(siteImage -> {
                if (siteImage.getYear() == null)
                    siteImage.setYear(LocalDate.now().getYear());
                return siteImage;
            })
            .collect(Collectors.toSet());
        historicalSite = historicalSiteRepository.save(historicalSite);
        return historicalSiteMapper.toDto(historicalSite);
    }

    public Address endereco(Address address) {
        String endereco = address.getStreetAddress() + "+" + address.getProvince() + "+" + address.getCity() + "+" + address.getUf();
        String url = endereco.trim().replaceAll(" ", "+");
        LocationRequest location = locationClient.findLocation(url);
        address.setLatitude(location.getResults().get(0).getGeometry().getLocation().getLat().toString());
        address.setLongitude(location.getResults().get(0).getGeometry().getLocation().getLng().toString());
        return address;
    }

    /**
     * Partially update a historicalSite.
     *
     * @param historicalSiteDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<HistoricalSiteDTO> partialUpdate(HistoricalSiteDTO historicalSiteDTO) {
        log.debug("Request to partially update HistoricalSite : {}", historicalSiteDTO);

        return historicalSiteRepository
            .findById(historicalSiteDTO.getId())
            .map(
                existingHistoricalSite -> {
                    historicalSiteMapper.partialUpdate(existingHistoricalSite, historicalSiteDTO);
                    return existingHistoricalSite;
                }
            )
            .map(historicalSiteRepository::save)
            .map(historicalSiteMapper::toDto);
    }

    /**
     * Get all the historicalSites.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<HistoricalSiteDTO> findAll(Pageable pageable) {
        log.debug("Request to get all HistoricalSites");
        return historicalSiteRepository.findAll(pageable).map(historicalSiteMapper::toDto);
    }

    @Transactional(readOnly = true)
    public List<HistoricalSiteMapDTO> findAllMap() {
        log.debug("Request to get all HistoricalSites");
        List<HistoricalSiteMapDTO> allSitesForMap = historicalSiteRepository.findAllSitesForMap();
        allSitesForMap.stream().map(historicalSiteMapDTO -> {
            historicalSiteMapDTO.setSiteImageMapDTOS(siteImageRepository.findAllByIdMap(historicalSiteMapDTO.getId()));
            return historicalSiteMapDTO;
        }).collect(Collectors.toList());
        return allSitesForMap;
    }

    @Transactional(readOnly = true)
    public List<HistoricalSiteMapDTO> findAllByFilter(String filter) {
        log.debug("Request to get all HistoricalSites");
        List<HistoricalSiteMapDTO> allSitesForMap = historicalSiteRepository.findAllToMapByFilter(filter);
        allSitesForMap.stream().map(historicalSiteMapDTO -> {
            historicalSiteMapDTO.setSiteImageMapDTOS(siteImageRepository.findAllByIdMap(historicalSiteMapDTO.getId()));
            return historicalSiteMapDTO;
        }).collect(Collectors.toList());
        return allSitesForMap;
    }

    public HistoricalSiteDTO attHistoricalSite(Long id, HistoricalSiteUpdateStatusDTO historicalSiteUpdateStatusDTO) {
        Optional<HistoricalSite> historicalSiteOptional = historicalSiteRepository.findById(id);
        HistoricalSite historicalSite = historicalSiteOptional.get();

        historicalSite.setStatus(historicalSiteUpdateStatusDTO.getStatus());
        historicalSite.setLink(historicalSiteUpdateStatusDTO.getLink());
        historicalSite.setComment(historicalSiteUpdateStatusDTO.getComment());

        return historicalSiteMapper.toDto(historicalSiteRepository.save(historicalSite));
    }

    /**
     * Get one historicalSite by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public HistoricalSiteIdDTO findOne(Long id) {
        log.debug("Request to get HistoricalSite : {}", id);
        List<Integer> years = new ArrayList<>();
        Optional<HistoricalSite> historicalSite = historicalSiteRepository.findById(id);
        historicalSite.get().getSiteImages().forEach(siteImage -> {
            years.add(siteImage.getYear());
        });
        HistoricalSiteIdDTO historicalSiteIdDTO = historicalSiteMapper.historicalSitetoHistoricalSiteIdDTO(historicalSite.get());
        historicalSiteIdDTO.setYears(years);
        return historicalSiteIdDTO;
    }

    @Transactional(readOnly = true)
    public List<HistoricalSiteDTO> findAllHistoricalSiteByIdUSer(Long id) {
        log.debug("Request to get All HistoricalSite by idUser: {}", id);
        return historicalSiteRepository.findAllByUserId(id)
            .stream()
            .map(historicalSite -> {
                HistoricalSiteDTO historicalSiteDTO = historicalSiteMapper.toDto(historicalSite);
                historicalSiteDTO.setIdUser(id);
                return historicalSiteDTO;
            })
            .collect(Collectors.toList());
    }

    /**
     * Delete the historicalSite by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete HistoricalSite : {}", id);
        historicalSiteRepository.deleteById(id);
    }
}
