package ru.m2mcom.lotmicro.service.impl;

import ru.m2mcom.lotmicro.service.DblTrplStatsService;
import ru.m2mcom.lotmicro.domain.DblTrplStats;
import ru.m2mcom.lotmicro.repository.DblTrplStatsRepository;
import ru.m2mcom.lotmicro.repository.search.DblTrplStatsSearchRepository;
import ru.m2mcom.lotmicro.service.dto.DblTrplStatsDTO;
import ru.m2mcom.lotmicro.service.mapper.DblTrplStatsMapper;
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
 * Service Implementation for managing DblTrplStats.
 */
@Service
@Transactional
public class DblTrplStatsServiceImpl implements DblTrplStatsService{

    private final Logger log = LoggerFactory.getLogger(DblTrplStatsServiceImpl.class);
    
    private final DblTrplStatsRepository dblTrplStatsRepository;

    private final DblTrplStatsMapper dblTrplStatsMapper;

    private final DblTrplStatsSearchRepository dblTrplStatsSearchRepository;

    public DblTrplStatsServiceImpl(DblTrplStatsRepository dblTrplStatsRepository, DblTrplStatsMapper dblTrplStatsMapper, DblTrplStatsSearchRepository dblTrplStatsSearchRepository) {
        this.dblTrplStatsRepository = dblTrplStatsRepository;
        this.dblTrplStatsMapper = dblTrplStatsMapper;
        this.dblTrplStatsSearchRepository = dblTrplStatsSearchRepository;
    }

    /**
     * Save a dblTrplStats.
     *
     * @param dblTrplStatsDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public DblTrplStatsDTO save(DblTrplStatsDTO dblTrplStatsDTO) {
        log.debug("Request to save DblTrplStats : {}", dblTrplStatsDTO);
        DblTrplStats dblTrplStats = dblTrplStatsMapper.dblTrplStatsDTOToDblTrplStats(dblTrplStatsDTO);
        dblTrplStats = dblTrplStatsRepository.save(dblTrplStats);
        DblTrplStatsDTO result = dblTrplStatsMapper.dblTrplStatsToDblTrplStatsDTO(dblTrplStats);
        dblTrplStatsSearchRepository.save(dblTrplStats);
        return result;
    }

    /**
     *  Get all the dblTrplStats.
     *  
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<DblTrplStatsDTO> findAll() {
        log.debug("Request to get all DblTrplStats");
        List<DblTrplStatsDTO> result = dblTrplStatsRepository.findAll().stream()
            .map(dblTrplStatsMapper::dblTrplStatsToDblTrplStatsDTO)
            .collect(Collectors.toCollection(LinkedList::new));

        return result;
    }

    /**
     *  Get one dblTrplStats by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public DblTrplStatsDTO findOne(Long id) {
        log.debug("Request to get DblTrplStats : {}", id);
        DblTrplStats dblTrplStats = dblTrplStatsRepository.findOne(id);
        DblTrplStatsDTO dblTrplStatsDTO = dblTrplStatsMapper.dblTrplStatsToDblTrplStatsDTO(dblTrplStats);
        return dblTrplStatsDTO;
    }

    /**
     *  Delete the  dblTrplStats by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete DblTrplStats : {}", id);
        dblTrplStatsRepository.delete(id);
        dblTrplStatsSearchRepository.delete(id);
    }

    /**
     * Search for the dblTrplStats corresponding to the query.
     *
     *  @param query the query of the search
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<DblTrplStatsDTO> search(String query) {
        log.debug("Request to search DblTrplStats for query {}", query);
        return StreamSupport
            .stream(dblTrplStatsSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(dblTrplStatsMapper::dblTrplStatsToDblTrplStatsDTO)
            .collect(Collectors.toList());
    }
}
