package ru.m2mcom.lotmicro.service.mapper;

import ru.m2mcom.lotmicro.domain.*;
import ru.m2mcom.lotmicro.service.dto.LocalPlayDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity LocalPlay and its DTO LocalPlayDTO.
 */
@Mapper(componentModel = "spring", uses = {RankMapper.class, BonusRankMapper.class, })
public interface LocalPlayMapper {

    @Mapping(source = "rank.id", target = "rankId")
    @Mapping(source = "bonusrank.id", target = "bonusrankId")
    LocalPlayDTO localPlayToLocalPlayDTO(LocalPlay localPlay);

    List<LocalPlayDTO> localPlaysToLocalPlayDTOs(List<LocalPlay> localPlays);

    @Mapping(source = "rankId", target = "rank")
    @Mapping(source = "bonusrankId", target = "bonusrank")
    LocalPlay localPlayDTOToLocalPlay(LocalPlayDTO localPlayDTO);

    List<LocalPlay> localPlayDTOsToLocalPlays(List<LocalPlayDTO> localPlayDTOs);
    /**
     * generating the fromId for all mappers if the databaseType is sql, as the class has relationship to it might need it, instead of
     * creating a new attribute to know if the entity has any relationship from some other entity
     *
     * @param id id of the entity
     * @return the entity instance
     */
     
    default LocalPlay localPlayFromId(Long id) {
        if (id == null) {
            return null;
        }
        LocalPlay localPlay = new LocalPlay();
        localPlay.setId(id);
        return localPlay;
    }
    

}
