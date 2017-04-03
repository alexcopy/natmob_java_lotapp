package ru.m2mcom.lotmicro.service.mapper;

import ru.m2mcom.lotmicro.domain.*;
import ru.m2mcom.lotmicro.service.dto.RankDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity Rank and its DTO RankDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface RankMapper {

    RankDTO rankToRankDTO(Rank rank);

    List<RankDTO> ranksToRankDTOs(List<Rank> ranks);

    Rank rankDTOToRank(RankDTO rankDTO);

    List<Rank> rankDTOsToRanks(List<RankDTO> rankDTOs);
    /**
     * generating the fromId for all mappers if the databaseType is sql, as the class has relationship to it might need it, instead of
     * creating a new attribute to know if the entity has any relationship from some other entity
     *
     * @param id id of the entity
     * @return the entity instance
     */
     
    default Rank rankFromId(Long id) {
        if (id == null) {
            return null;
        }
        Rank rank = new Rank();
        rank.setId(id);
        return rank;
    }
    

}
