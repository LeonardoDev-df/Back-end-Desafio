package br.com.history.repository;

import br.com.history.domain.SiteImage;
import br.com.history.service.dto.SiteImage3DViewDTO;
import br.com.history.service.dto.SiteImageMapDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

/**
 * Spring Data SQL repository for the SiteImage entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SiteImageRepository extends JpaRepository<SiteImage, Long> {

    @Query("select new br.com.history.service.dto.SiteImage3DViewDTO(s.numberImage, s.image3D, s.imagePreview, s.buttonPosition, s.buttonColor) from SiteImage s " +
        "where s.historicalSite.id = :idHistoricalSite and (:year IS NULL OR s.year = :year)")
    List<SiteImage3DViewDTO> findAllByIdHistoricalSiteAndYear(@Param("idHistoricalSite") Long idHistoricalSite, @Param("year") Integer year);

    @Query("select new br.com.history.service.dto.SiteImageMapDTO(s.imagePreview) from SiteImage s " +
        "where s.historicalSite.id = :idHistoricalSite")
    Set<SiteImageMapDTO> findAllByIdMap(@Param("idHistoricalSite") Long idHistoricalSite);

    @Query("select new br.com.history.service.dto.SiteImage3DViewDTO(s.numberImage, s.image3D, s.imagePreview, s.buttonPosition, s.buttonColor) from SiteImage s " +
        "where s.historicalSite.id = :idHistoricalSite and s.year=(select max(si.year) from SiteImage si)")
    SiteImage3DViewDTO findByIdHistoricalSite(@Param("idHistoricalSite") Long idHistoricalSite);
}
