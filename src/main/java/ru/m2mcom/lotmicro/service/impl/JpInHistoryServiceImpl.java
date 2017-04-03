package ru.m2mcom.lotmicro.service.impl;

import ru.m2mcom.lotmicro.service.JpInHistoryService;
import ru.m2mcom.lotmicro.domain.JpInHistory;
import ru.m2mcom.lotmicro.repository.JpInHistoryRepository;
import ru.m2mcom.lotmicro.repository.search.JpInHistorySearchRepository;
import ru.m2mcom.lotmicro.service.dto.JpInHistoryDTO;
import ru.m2mcom.lotmicro.service.mapper.JpInHistoryMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing JpInHistory.
 */
@Service
@Transactional
public class JpInHistoryServiceImpl implements JpInHistoryService{

    private final Logger log = LoggerFactory.getLogger(JpInHistoryServiceImpl.class);
    
    private final JpInHistoryRepository jpInHistoryRepository;

    private final JpInHistoryMapper jpInHistoryMapper;

    private final JpInHistorySearchRepository jpInHistorySearchRepository;

    public JpInHistoryServiceImpl(JpInHistoryRepository jpInHistoryRepository, JpInHistoryMapper jpInHistoryMapper, JpInHistorySearchRepository jpInHistorySearchRepository) {
        this.jpInHistoryRepository = jpInHistoryRepository;
        this.jpInHistoryMapper = jpInHistoryMapper;
        this.jpInHistorySearchRepository = jpInHistorySearchRepository;
    }

    /**
     * Save a jpInHistory.
     *
     * @param jpInHistoryDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public JpInHistoryDTO save(JpInHistoryDTO jpInHistoryDTO) {
        log.debug("Request to save JpInHistory : {}", jpInHistoryDTO);
        JpInHistory jpInHistory = jpInHistoryMapper.jpInHistoryDTOToJpInHistory(jpInHistoryDTO);
        jpInHistory = jpInHistoryRepository.save(jpInHistory);
        JpInHistoryDTO result = jpInHistoryMapper.jpInHistoryToJpInHistoryDTO(jpInHistory);
        jpInHistorySearchRepository.save(jpInHistory);
        return result;
    }

    /**
     *  Get all the jpInHistories.
     *  
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<JpInHistoryDTO> findAll() {
        log.debug("Request to get all JpInHistories");
        List<JpInHistoryDTO> result = jpInHistoryRepository.findAll().stream()
            .map(jpInHistoryMapper::jpInHistoryToJpInHistoryDTO)
            .collect(Collectors.toCollection(LinkedList::new));

        return result;
    }

    /**
     *  Get one jpInHistory by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public JpInHistoryDTO findOne(Long id) {
        log.debug("Request to get JpInHistory : {}", id);
        JpInHistory jpInHistory = jpInHistoryRepository.findOne(id);
        JpInHistoryDTO jpInHistoryDTO = jpInHistoryMapper.jpInHistoryToJpInHistoryDTO(jpInHistory);
        return jpInHistoryDTO;
    }

    /**
     *  Delete the  jpInHistory by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete JpInHistory : {}", id);
        jpInHistoryRepository.delete(id);
        jpInHistorySearchRepository.delete(id);
    }

    /**
     * Search for the jpInHistory corresponding to the query.
     *
     *  @param query the query of the search
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<JpInHistoryDTO> search(String query) {
        log.debug("Request to search JpInHistories for query {}", query);
        return StreamSupport
            .stream(jpInHistorySearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(jpInHistoryMapper::jpInHistoryToJpInHistoryDTO)
            .collect(Collectors.toList());
    }
}
