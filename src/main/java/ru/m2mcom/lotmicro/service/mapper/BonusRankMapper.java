package ru.m2mcom.lotmicro.service.mapper;

import ru.m2mcom.lotmicro.domain.*;
import ru.m2mcom.lotmicro.service.dto.BonusRankDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity BonusRank and its DTO BonusRankDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface BonusRankMapper {

    BonusRankDTO bonusRankToBonusRankDTO(BonusRank bonusRank);

    List<BonusRankDTO> bonusRanksToBonusRankDTOs(List<BonusRank> bonusRanks);

    BonusRank bonusRankDTOToBonusRank(BonusRankDTO bonusRankDTO);

    List<BonusRank> bonusRankDTOsToBonusRanks(List<BonusRankDTO> bonusRankDTOs);
    /**
     * generating the fromId for all mappers if the databaseType is sql, as the class has relationship to it might need it, instead of
     * creating a new attribute to know if the entity has any relationship from some other entity
     *
     * @param id id of the entity
     * @return the entity instance
     */
     
    default BonusRank bonusRankFromId(Long id) {
        if (id == null) {
            return null;
        }
        BonusRank bonusRank = new BonusRank();
        bonusRank.setId(id);
        return bonusRank;
    }
    

}
