package ru.m2mcom.natmob.service;

import ru.m2mcom.natmob.domain.CombinedBallsAndStarsStats;
import java.util.List;

/**
 * Service Interface for managing CombinedBallsAndStarsStats.
 */
public interface CombinedBallsAndStarsStatsService {

    /**
     * Save a combinedBallsAndStarsStats.
     *
     * @param combinedBallsAndStarsStats the entity to save
     * @return the persisted entity
     */
    CombinedBallsAndStarsStats save(CombinedBallsAndStarsStats combinedBallsAndStarsStats);

    /**
     *  Get all the combinedBallsAndStarsStats.
     *  
     *  @return the list of entities
     */
    List<CombinedBallsAndStarsStats> findAll();

    /**
     *  Get the "id" combinedBallsAndStarsStats.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    CombinedBallsAndStarsStats findOne(Long id);

    /**
     *  Delete the "id" combinedBallsAndStarsStats.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the combinedBallsAndStarsStats corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @return the list of entities
     */
    List<CombinedBallsAndStarsStats> search(String query);
}
