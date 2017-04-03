package ru.m2mcom.lotmicro.service;

import ru.m2mcom.lotmicro.service.dto.BonusRankDTO;
import java.util.List;

/**
 * Service Interface for managing BonusRank.
 */
public interface BonusRankService {

    /**
     * Save a bonusRank.
     *
     * @param bonusRankDTO the entity to save
     * @return the persisted entity
     */
    BonusRankDTO save(BonusRankDTO bonusRankDTO);

    /**
     *  Get all the bonusRanks.
     *  
     *  @return the list of entities
     */
    List<BonusRankDTO> findAll();

    /**
     *  Get the "id" bonusRank.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    BonusRankDTO findOne(Long id);

    /**
     *  Delete the "id" bonusRank.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the bonusRank corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @return the list of entities
     */
    List<BonusRankDTO> search(String query);
}
