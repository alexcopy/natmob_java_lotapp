package ru.m2mcom.lotmicro.service;

import ru.m2mcom.lotmicro.service.dto.HistoryDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * Service Interface for managing History.
 */
public interface HistoryService {

    /**
     * Save a history.
     *
     * @param historyDTO the entity to save
     * @return the persisted entity
     */
    HistoryDTO save(HistoryDTO historyDTO);

    /**
     *  Get all the histories.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<HistoryDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" history.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    HistoryDTO findOne(Long id);

    /**
     *  Delete the "id" history.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the history corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<HistoryDTO> search(String query, Pageable pageable);
}
