package ru.m2mcom.natmob.service;

import ru.m2mcom.natmob.domain.Thb_history;
import java.util.List;

/**
 * Service Interface for managing Thb_history.
 */
public interface Thb_historyService {

    /**
     * Save a thb_history.
     *
     * @param thb_history the entity to save
     * @return the persisted entity
     */
    Thb_history save(Thb_history thb_history);

    /**
     *  Get all the thb_histories.
     *  
     *  @return the list of entities
     */
    List<Thb_history> findAll();

    /**
     *  Get the "id" thb_history.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    Thb_history findOne(Long id);

    /**
     *  Delete the "id" thb_history.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the thb_history corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @return the list of entities
     */
    List<Thb_history> search(String query);
}
