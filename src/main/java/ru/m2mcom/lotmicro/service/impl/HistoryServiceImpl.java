package ru.m2mcom.lotmicro.service.impl;

import ru.m2mcom.lotmicro.service.HistoryService;
import ru.m2mcom.lotmicro.domain.History;
import ru.m2mcom.lotmicro.repository.HistoryRepository;
import ru.m2mcom.lotmicro.repository.search.HistorySearchRepository;
import ru.m2mcom.lotmicro.service.dto.HistoryDTO;
import ru.m2mcom.lotmicro.service.mapper.HistoryMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing History.
 */
@Service
@Transactional
public class HistoryServiceImpl implements HistoryService{

    private final Logger log = LoggerFactory.getLogger(HistoryServiceImpl.class);
    
    private final HistoryRepository historyRepository;

    private final HistoryMapper historyMapper;

    private final HistorySearchRepository historySearchRepository;

    public HistoryServiceImpl(HistoryRepository historyRepository, HistoryMapper historyMapper, HistorySearchRepository historySearchRepository) {
        this.historyRepository = historyRepository;
        this.historyMapper = historyMapper;
        this.historySearchRepository = historySearchRepository;
    }

    /**
     * Save a history.
     *
     * @param historyDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public HistoryDTO save(HistoryDTO historyDTO) {
        log.debug("Request to save History : {}", historyDTO);
        History history = historyMapper.historyDTOToHistory(historyDTO);
        history = historyRepository.save(history);
        HistoryDTO result = historyMapper.historyToHistoryDTO(history);
        historySearchRepository.save(history);
        return result;
    }

    /**
     *  Get all the histories.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<HistoryDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Histories");
        Page<History> result = historyRepository.findAll(pageable);
        return result.map(history -> historyMapper.historyToHistoryDTO(history));
    }

    /**
     *  Get one history by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public HistoryDTO findOne(Long id) {
        log.debug("Request to get History : {}", id);
        History history = historyRepository.findOne(id);
        HistoryDTO historyDTO = historyMapper.historyToHistoryDTO(history);
        return historyDTO;
    }

    /**
     *  Delete the  history by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete History : {}", id);
        historyRepository.delete(id);
        historySearchRepository.delete(id);
    }

    /**
     * Search for the history corresponding to the query.
     *
     *  @param query the query of the search
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<HistoryDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Histories for query {}", query);
        Page<History> result = historySearchRepository.search(queryStringQuery(query), pageable);
        return result.map(history -> historyMapper.historyToHistoryDTO(history));
    }
}
