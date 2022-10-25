package br.com.history.service.mapper;

import br.com.history.domain.*;
import br.com.history.service.dto.SiteImageDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link SiteImage} and its DTO {@link SiteImageDTO}.
 */
@Mapper(componentModel = "spring", uses = { HistoricalSiteMapper.class })
public interface SiteImageMapper extends EntityMapper<SiteImageDTO, SiteImage> {
//    @Mapping(target = "historicalSite", source = "historicalSite", qualifiedByName = "id")
    SiteImageDTO toDto(SiteImage s);
}
