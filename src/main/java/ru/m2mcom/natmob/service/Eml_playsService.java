package ru.m2mcom.natmob.service;

import ru.m2mcom.natmob.domain.Eml_plays;
import java.util.List;

/**
 * Service Interface for managing Eml_plays.
 */
public interface Eml_playsService {

    /**
     * Save a eml_plays.
     *
     * @param eml_plays the entity to save
     * @return the persisted entity
     */
    Eml_plays save(Eml_plays eml_plays);

    /**
     *  Get all the eml_plays.
     *  
     *  @return the list of entities
     */
    List<Eml_plays> findAll();

    /**
     *  Get the "id" eml_plays.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    Eml_plays findOne(Long id);

    /**
     *  Delete the "id" eml_plays.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the eml_plays corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @return the list of entities
     */
    List<Eml_plays> search(String query);
}
