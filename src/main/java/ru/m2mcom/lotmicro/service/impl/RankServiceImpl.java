package ru.m2mcom.lotmicro.service.impl;

import ru.m2mcom.lotmicro.service.RankService;
import ru.m2mcom.lotmicro.domain.Rank;
import ru.m2mcom.lotmicro.repository.RankRepository;
import ru.m2mcom.lotmicro.repository.search.RankSearchRepository;
import ru.m2mcom.lotmicro.service.dto.RankDTO;
import ru.m2mcom.lotmicro.service.mapper.RankMapper;
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
 * Service Implementation for managing Rank.
 */
@Service
@Transactional
public class RankServiceImpl implements RankService{

    private final Logger log = LoggerFactory.getLogger(RankServiceImpl.class);
    
    private final RankRepository rankRepository;

    private final RankMapper rankMapper;

    private final RankSearchRepository rankSearchRepository;

    public RankServiceImpl(RankRepository rankRepository, RankMapper rankMapper, RankSearchRepository rankSearchRepository) {
        this.rankRepository = rankRepository;
        this.rankMapper = rankMapper;
        this.rankSearchRepository = rankSearchRepository;
    }

    /**
     * Save a rank.
     *
     * @param rankDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public RankDTO save(RankDTO rankDTO) {
        log.debug("Request to save Rank : {}", rankDTO);
        Rank rank = rankMapper.rankDTOToRank(rankDTO);
        rank = rankRepository.save(rank);
        RankDTO result = rankMapper.rankToRankDTO(rank);
        rankSearchRepository.save(rank);
        return result;
    }

    /**
     *  Get all the ranks.
     *  
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<RankDTO> findAll() {
        log.debug("Request to get all Ranks");
        List<RankDTO> result = rankRepository.findAll().stream()
            .map(rankMapper::rankToRankDTO)
            .collect(Collectors.toCollection(LinkedList::new));

        return result;
    }

    /**
     *  Get one rank by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public RankDTO findOne(Long id) {
        log.debug("Request to get Rank : {}", id);
        Rank rank = rankRepository.findOne(id);
        RankDTO rankDTO = rankMapper.rankToRankDTO(rank);
        return rankDTO;
    }

    /**
     *  Delete the  rank by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Rank : {}", id);
        rankRepository.delete(id);
        rankSearchRepository.delete(id);
    }

    /**
     * Search for the rank corresponding to the query.
     *
     *  @param query the query of the search
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<RankDTO> search(String query) {
        log.debug("Request to search Ranks for query {}", query);
        return StreamSupport
            .stream(rankSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(rankMapper::rankToRankDTO)
            .collect(Collectors.toList());
    }
}
