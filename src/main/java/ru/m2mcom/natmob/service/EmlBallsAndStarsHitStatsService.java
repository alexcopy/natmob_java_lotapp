package ru.m2mcom.natmob.service;

import ru.m2mcom.natmob.domain.EmlBallsAndStarsHitStats;
import java.util.List;

/**
 * Service Interface for managing EmlBallsAndStarsHitStats.
 */
public interface EmlBallsAndStarsHitStatsService {

    /**
     * Save a emlBallsAndStarsHitStats.
     *
     * @param emlBallsAndStarsHitStats the entity to save
     * @return the persisted entity
     */
    EmlBallsAndStarsHitStats save(EmlBallsAndStarsHitStats emlBallsAndStarsHitStats);

    /**
     *  Get all the emlBallsAndStarsHitStats.
     *  
     *  @return the list of entities
     */
    List<EmlBallsAndStarsHitStats> findAll();

    /**
     *  Get the "id" emlBallsAndStarsHitStats.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    EmlBallsAndStarsHitStats findOne(Long id);

    /**
     *  Delete the "id" emlBallsAndStarsHitStats.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the emlBallsAndStarsHitStats corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @return the list of entities
     */
    List<EmlBallsAndStarsHitStats> search(String query);
}
