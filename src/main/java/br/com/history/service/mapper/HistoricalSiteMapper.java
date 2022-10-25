package br.com.history.service.mapper;

import br.com.history.domain.HistoricalSite;
import br.com.history.service.dto.HistoricalSiteDTO;
import br.com.history.service.dto.HistoricalSiteIdDTO;
import br.com.history.service.dto.HistoricalSiteUpdateStatusDTO;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

/**
 * Mapper for the entity {@link HistoricalSite} and its DTO {@link HistoricalSiteDTO}.
 */
@Mapper(componentModel = "spring", uses = { AddressMapper.class })
public interface HistoricalSiteMapper extends EntityMapper<HistoricalSiteDTO, HistoricalSite> {

    HistoricalSiteDTO toDto(HistoricalSite s);

    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    HistoricalSiteDTO toDtoId(HistoricalSite historicalSite);

    @Mapping(target = "user.id", source = "idUser")
    HistoricalSite toEntity(HistoricalSiteDTO historicalSiteDTO);


    HistoricalSite historicalSiteUpdateStatusDTOtoHistoricalSite(HistoricalSiteUpdateStatusDTO historicalSiteUpdateStatusDTO);

    HistoricalSiteIdDTO historicalSitetoHistoricalSiteIdDTO(HistoricalSite historicalSite);
}
