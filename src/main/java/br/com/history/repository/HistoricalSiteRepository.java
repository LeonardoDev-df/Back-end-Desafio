package br.com.history.repository;

import br.com.history.domain.HistoricalSite;
import br.com.history.service.dto.HistoricalSiteMapDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data SQL repository for the HistoricalSite entity.
 */
@SuppressWarnings("unused")
@Repository
public interface HistoricalSiteRepository extends JpaRepository<HistoricalSite, Long> {

    @Query("SELECT NEW br.com.history.service.dto.HistoricalSiteMapDTO(" +
        "h.id, " +
        "h.name, " +
        "h.description, " +
        "h.address.streetAddress, " +
        "h.address.city, " +
        "h.address.uf, " +
        "h.address.zipCode, " +
        "h.address.longitude, " +
        "h.address.latitude) " +
        "FROM HistoricalSite h " +
        "WHERE h.status = 'ACEITO'"
    )
    List<HistoricalSiteMapDTO> findAllSitesForMap();

    @Query("SELECT NEW br.com.history.service.dto.HistoricalSiteMapDTO(" +
        "h.id, " +
        "h.name, " +
        "h.description, " +
        "h.address.streetAddress, " +
        "h.address.city, " +
        "h.address.uf, " +
        "h.address.zipCode, " +
        "h.address.longitude, " +
        "h.address.latitude) " +
        "FROM HistoricalSite h " +
        "WHERE h.status = 'ACEITO' " +
        "AND LOWER(h.name) LIKE LOWER(CONCAT('%',?1,'%'))"
    )
    List<HistoricalSiteMapDTO> findAllToMapByFilter(String filter);

    List<HistoricalSite> findAllByUserId(Long idUser);
}
