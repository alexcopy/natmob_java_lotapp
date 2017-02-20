package ru.m2mcom.natmob.service;

import ru.m2mcom.natmob.domain.NatBallsAndStarsHitStats;
import java.util.List;

/**
 * Service Interface for managing NatBallsAndStarsHitStats.
 */
public interface NatBallsAndStarsHitStatsService {

    /**
     * Save a natBallsAndStarsHitStats.
     *
     * @param natBallsAndStarsHitStats the entity to save
     * @return the persisted entity
     */
    NatBallsAndStarsHitStats save(NatBallsAndStarsHitStats natBallsAndStarsHitStats);

    /**
     *  Get all the natBallsAndStarsHitStats.
     *  
     *  @return the list of entities
     */
    List<NatBallsAndStarsHitStats> findAll();

    /**
     *  Get the "id" natBallsAndStarsHitStats.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    NatBallsAndStarsHitStats findOne(Long id);

    /**
     *  Delete the "id" natBallsAndStarsHitStats.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the natBallsAndStarsHitStats corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @return the list of entities
     */
    List<NatBallsAndStarsHitStats> search(String query);
}
