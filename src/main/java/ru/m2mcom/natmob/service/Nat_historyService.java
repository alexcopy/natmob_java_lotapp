package ru.m2mcom.natmob.service;

import ru.m2mcom.natmob.domain.Nat_history;
import java.util.List;

/**
 * Service Interface for managing Nat_history.
 */
public interface Nat_historyService {

    /**
     * Save a nat_history.
     *
     * @param nat_history the entity to save
     * @return the persisted entity
     */
    Nat_history save(Nat_history nat_history);

    /**
     *  Get all the nat_histories.
     *  
     *  @return the list of entities
     */
    List<Nat_history> findAll();

    /**
     *  Get the "id" nat_history.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    Nat_history findOne(Long id);

    /**
     *  Delete the "id" nat_history.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the nat_history corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @return the list of entities
     */
    List<Nat_history> search(String query);
}
