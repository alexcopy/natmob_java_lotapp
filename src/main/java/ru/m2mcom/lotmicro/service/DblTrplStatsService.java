package ru.m2mcom.lotmicro.service;

import ru.m2mcom.lotmicro.service.dto.DblTrplStatsDTO;
import java.util.List;

/**
 * Service Interface for managing DblTrplStats.
 */
public interface DblTrplStatsService {

    /**
     * Save a dblTrplStats.
     *
     * @param dblTrplStatsDTO the entity to save
     * @return the persisted entity
     */
    DblTrplStatsDTO save(DblTrplStatsDTO dblTrplStatsDTO);

    /**
     *  Get all the dblTrplStats.
     *  
     *  @return the list of entities
     */
    List<DblTrplStatsDTO> findAll();

    /**
     *  Get the "id" dblTrplStats.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    DblTrplStatsDTO findOne(Long id);

    /**
     *  Delete the "id" dblTrplStats.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the dblTrplStats corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @return the list of entities
     */
    List<DblTrplStatsDTO> search(String query);
}
