package ru.m2mcom.lotmicro.service;

import ru.m2mcom.lotmicro.service.dto.RankDTO;
import java.util.List;

/**
 * Service Interface for managing Rank.
 */
public interface RankService {

    /**
     * Save a rank.
     *
     * @param rankDTO the entity to save
     * @return the persisted entity
     */
    RankDTO save(RankDTO rankDTO);

    /**
     *  Get all the ranks.
     *  
     *  @return the list of entities
     */
    List<RankDTO> findAll();

    /**
     *  Get the "id" rank.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    RankDTO findOne(Long id);

    /**
     *  Delete the "id" rank.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the rank corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @return the list of entities
     */
    List<RankDTO> search(String query);
}
