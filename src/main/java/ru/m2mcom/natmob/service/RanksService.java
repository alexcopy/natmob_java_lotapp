package ru.m2mcom.natmob.service;

import ru.m2mcom.natmob.domain.Ranks;
import java.util.List;

/**
 * Service Interface for managing Ranks.
 */
public interface RanksService {

    /**
     * Save a ranks.
     *
     * @param ranks the entity to save
     * @return the persisted entity
     */
    Ranks save(Ranks ranks);

    /**
     *  Get all the ranks.
     *  
     *  @return the list of entities
     */
    List<Ranks> findAll();

    /**
     *  Get the "id" ranks.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    Ranks findOne(Long id);

    /**
     *  Delete the "id" ranks.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the ranks corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @return the list of entities
     */
    List<Ranks> search(String query);
}
