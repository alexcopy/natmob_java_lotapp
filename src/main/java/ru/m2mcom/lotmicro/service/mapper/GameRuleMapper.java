package ru.m2mcom.lotmicro.service.mapper;

import ru.m2mcom.lotmicro.domain.*;
import ru.m2mcom.lotmicro.service.dto.GameRuleDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity GameRule and its DTO GameRuleDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface GameRuleMapper {

    GameRuleDTO gameRuleToGameRuleDTO(GameRule gameRule);

    List<GameRuleDTO> gameRulesToGameRuleDTOs(List<GameRule> gameRules);

    GameRule gameRuleDTOToGameRule(GameRuleDTO gameRuleDTO);

    List<GameRule> gameRuleDTOsToGameRules(List<GameRuleDTO> gameRuleDTOs);
    /**
     * generating the fromId for all mappers if the databaseType is sql, as the class has relationship to it might need it, instead of
     * creating a new attribute to know if the entity has any relationship from some other entity
     *
     * @param id id of the entity
     * @return the entity instance
     */
     
    default GameRule gameRuleFromId(Long id) {
        if (id == null) {
            return null;
        }
        GameRule gameRule = new GameRule();
        gameRule.setId(id);
        return gameRule;
    }
    

}
