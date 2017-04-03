package ru.m2mcom.lotmicro.service;

import ru.m2mcom.lotmicro.service.dto.JpInHistoryDTO;
import java.util.List;

/**
 * Service Interface for managing JpInHistory.
 */
public interface JpInHistoryService {

    /**
     * Save a jpInHistory.
     *
     * @param jpInHistoryDTO the entity to save
     * @return the persisted entity
     */
    JpInHistoryDTO save(JpInHistoryDTO jpInHistoryDTO);

    /**
     *  Get all the jpInHistories.
     *  
     *  @return the list of entities
     */
    List<JpInHistoryDTO> findAll();

    /**
     *  Get the "id" jpInHistory.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    JpInHistoryDTO findOne(Long id);

    /**
     *  Delete the "id" jpInHistory.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the jpInHistory corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @return the list of entities
     */
    List<JpInHistoryDTO> search(String query);
}
