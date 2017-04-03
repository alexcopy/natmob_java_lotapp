package ru.m2mcom.lotmicro.service;

import ru.m2mcom.lotmicro.service.dto.GameRuleDTO;
import java.util.List;

/**
 * Service Interface for managing GameRule.
 */
public interface GameRuleService {

    /**
     * Save a gameRule.
     *
     * @param gameRuleDTO the entity to save
     * @return the persisted entity
     */
    GameRuleDTO save(GameRuleDTO gameRuleDTO);

    /**
     *  Get all the gameRules.
     *  
     *  @return the list of entities
     */
    List<GameRuleDTO> findAll();

    /**
     *  Get the "id" gameRule.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    GameRuleDTO findOne(Long id);

    /**
     *  Delete the "id" gameRule.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the gameRule corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @return the list of entities
     */
    List<GameRuleDTO> search(String query);
}
