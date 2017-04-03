package ru.m2mcom.lotmicro.service;

import ru.m2mcom.lotmicro.service.dto.GameDTO;
import java.util.List;

/**
 * Service Interface for managing Game.
 */
public interface GameService {

    /**
     * Save a game.
     *
     * @param gameDTO the entity to save
     * @return the persisted entity
     */
    GameDTO save(GameDTO gameDTO);

    /**
     *  Get all the games.
     *  
     *  @return the list of entities
     */
    List<GameDTO> findAll();

    /**
     *  Get the "id" game.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    GameDTO findOne(Long id);

    /**
     *  Delete the "id" game.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the game corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @return the list of entities
     */
    List<GameDTO> search(String query);
}
