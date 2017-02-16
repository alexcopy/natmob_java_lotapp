package ru.m2mcom.natmob.service;

import ru.m2mcom.natmob.domain.Eml_history;
import java.util.List;

/**
 * Service Interface for managing Eml_history.
 */
public interface Eml_historyService {

    /**
     * Save a eml_history.
     *
     * @param eml_history the entity to save
     * @return the persisted entity
     */
    Eml_history save(Eml_history eml_history);

    /**
     *  Get all the eml_histories.
     *  
     *  @return the list of entities
     */
    List<Eml_history> findAll();

    /**
     *  Get the "id" eml_history.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    Eml_history findOne(Long id);

    /**
     *  Delete the "id" eml_history.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the eml_history corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @return the list of entities
     */
    List<Eml_history> search(String query);
}
