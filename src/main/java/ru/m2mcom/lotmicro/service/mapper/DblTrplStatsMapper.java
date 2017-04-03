package ru.m2mcom.lotmicro.service.mapper;

import ru.m2mcom.lotmicro.domain.*;
import ru.m2mcom.lotmicro.service.dto.DblTrplStatsDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity DblTrplStats and its DTO DblTrplStatsDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface DblTrplStatsMapper {

    DblTrplStatsDTO dblTrplStatsToDblTrplStatsDTO(DblTrplStats dblTrplStats);

    List<DblTrplStatsDTO> dblTrplStatsToDblTrplStatsDTOs(List<DblTrplStats> dblTrplStats);

    DblTrplStats dblTrplStatsDTOToDblTrplStats(DblTrplStatsDTO dblTrplStatsDTO);

    List<DblTrplStats> dblTrplStatsDTOsToDblTrplStats(List<DblTrplStatsDTO> dblTrplStatsDTOs);
    /**
     * generating the fromId for all mappers if the databaseType is sql, as the class has relationship to it might need it, instead of
     * creating a new attribute to know if the entity has any relationship from some other entity
     *
     * @param id id of the entity
     * @return the entity instance
     */
     
    default DblTrplStats dblTrplStatsFromId(Long id) {
        if (id == null) {
            return null;
        }
        DblTrplStats dblTrplStats = new DblTrplStats();
        dblTrplStats.setId(id);
        return dblTrplStats;
    }
    

}
