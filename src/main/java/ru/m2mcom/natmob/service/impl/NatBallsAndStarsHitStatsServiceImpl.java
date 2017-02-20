package ru.m2mcom.natmob.service.impl;

import ru.m2mcom.natmob.service.NatBallsAndStarsHitStatsService;
import ru.m2mcom.natmob.domain.NatBallsAndStarsHitStats;
import ru.m2mcom.natmob.repository.NatBallsAndStarsHitStatsRepository;
import ru.m2mcom.natmob.repository.search.NatBallsAndStarsHitStatsSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing NatBallsAndStarsHitStats.
 */
@Service
@Transactional
public class NatBallsAndStarsHitStatsServiceImpl implements NatBallsAndStarsHitStatsService{

    private final Logger log = LoggerFactory.getLogger(NatBallsAndStarsHitStatsServiceImpl.class);
    
    private final NatBallsAndStarsHitStatsRepository natBallsAndStarsHitStatsRepository;

    private final NatBallsAndStarsHitStatsSearchRepository natBallsAndStarsHitStatsSearchRepository;

    public NatBallsAndStarsHitStatsServiceImpl(NatBallsAndStarsHitStatsRepository natBallsAndStarsHitStatsRepository, NatBallsAndStarsHitStatsSearchRepository natBallsAndStarsHitStatsSearchRepository) {
        this.natBallsAndStarsHitStatsRepository = natBallsAndStarsHitStatsRepository;
        this.natBallsAndStarsHitStatsSearchRepository = natBallsAndStarsHitStatsSearchRepository;
    }

    /**
     * Save a natBallsAndStarsHitStats.
     *
     * @param natBallsAndStarsHitStats the entity to save
     * @return the persisted entity
     */
    @Override
    public NatBallsAndStarsHitStats save(NatBallsAndStarsHitStats natBallsAndStarsHitStats) {
        log.debug("Request to save NatBallsAndStarsHitStats : {}", natBallsAndStarsHitStats);
        NatBallsAndStarsHitStats result = natBallsAndStarsHitStatsRepository.save(natBallsAndStarsHitStats);
        natBallsAndStarsHitStatsSearchRepository.save(result);
        return result;
    }

    /**
     *  Get all the natBallsAndStarsHitStats.
     *  
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<NatBallsAndStarsHitStats> findAll() {
        log.debug("Request to get all NatBallsAndStarsHitStats");
        List<NatBallsAndStarsHitStats> result = natBallsAndStarsHitStatsRepository.findAll();

        return result;
    }

    /**
     *  Get one natBallsAndStarsHitStats by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public NatBallsAndStarsHitStats findOne(Long id) {
        log.debug("Request to get NatBallsAndStarsHitStats : {}", id);
        NatBallsAndStarsHitStats natBallsAndStarsHitStats = natBallsAndStarsHitStatsRepository.findOne(id);
        return natBallsAndStarsHitStats;
    }

    /**
     *  Delete the  natBallsAndStarsHitStats by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete NatBallsAndStarsHitStats : {}", id);
        natBallsAndStarsHitStatsRepository.delete(id);
        natBallsAndStarsHitStatsSearchRepository.delete(id);
    }

    /**
     * Search for the natBallsAndStarsHitStats corresponding to the query.
     *
     *  @param query the query of the search
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<NatBallsAndStarsHitStats> search(String query) {
        log.debug("Request to search NatBallsAndStarsHitStats for query {}", query);
        return StreamSupport
            .stream(natBallsAndStarsHitStatsSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
