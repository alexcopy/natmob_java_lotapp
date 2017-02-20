package ru.m2mcom.natmob.service;

import ru.m2mcom.natmob.domain.ThbBallsAndStarsHitStats;
import java.util.List;

/**
 * Service Interface for managing ThbBallsAndStarsHitStats.
 */
public interface ThbBallsAndStarsHitStatsService {

    /**
     * Save a thbBallsAndStarsHitStats.
     *
     * @param thbBallsAndStarsHitStats the entity to save
     * @return the persisted entity
     */
    ThbBallsAndStarsHitStats save(ThbBallsAndStarsHitStats thbBallsAndStarsHitStats);

    /**
     *  Get all the thbBallsAndStarsHitStats.
     *  
     *  @return the list of entities
     */
    List<ThbBallsAndStarsHitStats> findAll();

    /**
     *  Get the "id" thbBallsAndStarsHitStats.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    ThbBallsAndStarsHitStats findOne(Long id);

    /**
     *  Delete the "id" thbBallsAndStarsHitStats.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the thbBallsAndStarsHitStats corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @return the list of entities
     */
    List<ThbBallsAndStarsHitStats> search(String query);
}
