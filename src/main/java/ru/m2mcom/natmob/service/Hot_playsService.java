package ru.m2mcom.natmob.service;

import ru.m2mcom.natmob.domain.Hot_plays;
import java.util.List;

/**
 * Service Interface for managing Hot_plays.
 */
public interface Hot_playsService {

    /**
     * Save a hot_plays.
     *
     * @param hot_plays the entity to save
     * @return the persisted entity
     */
    Hot_plays save(Hot_plays hot_plays);

    /**
     *  Get all the hot_plays.
     *  
     *  @return the list of entities
     */
    List<Hot_plays> findAll();

    /**
     *  Get the "id" hot_plays.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    Hot_plays findOne(Long id);

    /**
     *  Delete the "id" hot_plays.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the hot_plays corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @return the list of entities
     */
    List<Hot_plays> search(String query);
}
