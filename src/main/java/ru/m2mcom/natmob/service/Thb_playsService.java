package ru.m2mcom.natmob.service;

import ru.m2mcom.natmob.domain.Thb_plays;
import java.util.List;

/**
 * Service Interface for managing Thb_plays.
 */
public interface Thb_playsService {

    /**
     * Save a thb_plays.
     *
     * @param thb_plays the entity to save
     * @return the persisted entity
     */
    Thb_plays save(Thb_plays thb_plays);

    /**
     *  Get all the thb_plays.
     *  
     *  @return the list of entities
     */
    List<Thb_plays> findAll();

    /**
     *  Get the "id" thb_plays.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    Thb_plays findOne(Long id);

    /**
     *  Delete the "id" thb_plays.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the thb_plays corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @return the list of entities
     */
    List<Thb_plays> search(String query);
}
