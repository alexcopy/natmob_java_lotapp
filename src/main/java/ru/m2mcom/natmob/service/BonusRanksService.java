package ru.m2mcom.natmob.service;

import ru.m2mcom.natmob.domain.BonusRanks;
import java.util.List;

/**
 * Service Interface for managing BonusRanks.
 */
public interface BonusRanksService {

    /**
     * Save a bonusRanks.
     *
     * @param bonusRanks the entity to save
     * @return the persisted entity
     */
    BonusRanks save(BonusRanks bonusRanks);

    /**
     *  Get all the bonusRanks.
     *  
     *  @return the list of entities
     */
    List<BonusRanks> findAll();

    /**
     *  Get the "id" bonusRanks.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    BonusRanks findOne(Long id);

    /**
     *  Delete the "id" bonusRanks.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the bonusRanks corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @return the list of entities
     */
    List<BonusRanks> search(String query);
}
