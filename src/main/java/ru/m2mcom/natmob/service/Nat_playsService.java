package ru.m2mcom.natmob.service;

import ru.m2mcom.natmob.domain.Nat_plays;
import java.util.List;

/**
 * Service Interface for managing Nat_plays.
 */
public interface Nat_playsService {

    /**
     * Save a nat_plays.
     *
     * @param nat_plays the entity to save
     * @return the persisted entity
     */
    Nat_plays save(Nat_plays nat_plays);

    /**
     *  Get all the nat_plays.
     *  
     *  @return the list of entities
     */
    List<Nat_plays> findAll();

    /**
     *  Get the "id" nat_plays.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    Nat_plays findOne(Long id);

    /**
     *  Delete the "id" nat_plays.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the nat_plays corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @return the list of entities
     */
    List<Nat_plays> search(String query);
}
