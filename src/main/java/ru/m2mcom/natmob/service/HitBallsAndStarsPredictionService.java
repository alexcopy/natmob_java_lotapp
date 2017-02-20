package ru.m2mcom.natmob.service;

import ru.m2mcom.natmob.domain.HitBallsAndStarsPrediction;
import java.util.List;

/**
 * Service Interface for managing HitBallsAndStarsPrediction.
 */
public interface HitBallsAndStarsPredictionService {

    /**
     * Save a hitBallsAndStarsPrediction.
     *
     * @param hitBallsAndStarsPrediction the entity to save
     * @return the persisted entity
     */
    HitBallsAndStarsPrediction save(HitBallsAndStarsPrediction hitBallsAndStarsPrediction);

    /**
     *  Get all the hitBallsAndStarsPredictions.
     *  
     *  @return the list of entities
     */
    List<HitBallsAndStarsPrediction> findAll();

    /**
     *  Get the "id" hitBallsAndStarsPrediction.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    HitBallsAndStarsPrediction findOne(Long id);

    /**
     *  Delete the "id" hitBallsAndStarsPrediction.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the hitBallsAndStarsPrediction corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @return the list of entities
     */
    List<HitBallsAndStarsPrediction> search(String query);
}
