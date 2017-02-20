package ru.m2mcom.natmob.service.impl;

import ru.m2mcom.natmob.service.ThbBallsAndStarsHitStatsService;
import ru.m2mcom.natmob.domain.ThbBallsAndStarsHitStats;
import ru.m2mcom.natmob.repository.ThbBallsAndStarsHitStatsRepository;
import ru.m2mcom.natmob.repository.search.ThbBallsAndStarsHitStatsSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing ThbBallsAndStarsHitStats.
 */
@Service
@Transactional
public class ThbBallsAndStarsHitStatsServiceImpl implements ThbBallsAndStarsHitStatsService{

    private final Logger log = LoggerFactory.getLogger(ThbBallsAndStarsHitStatsServiceImpl.class);
    
    private final ThbBallsAndStarsHitStatsRepository thbBallsAndStarsHitStatsRepository;

    private final ThbBallsAndStarsHitStatsSearchRepository thbBallsAndStarsHitStatsSearchRepository;

    public ThbBallsAndStarsHitStatsServiceImpl(ThbBallsAndStarsHitStatsRepository thbBallsAndStarsHitStatsRepository, ThbBallsAndStarsHitStatsSearchRepository thbBallsAndStarsHitStatsSearchRepository) {
        this.thbBallsAndStarsHitStatsRepository = thbBallsAndStarsHitStatsRepository;
        this.thbBallsAndStarsHitStatsSearchRepository = thbBallsAndStarsHitStatsSearchRepository;
    }

    /**
     * Save a thbBallsAndStarsHitStats.
     *
     * @param thbBallsAndStarsHitStats the entity to save
     * @return the persisted entity
     */
    @Override
    public ThbBallsAndStarsHitStats save(ThbBallsAndStarsHitStats thbBallsAndStarsHitStats) {
        log.debug("Request to save ThbBallsAndStarsHitStats : {}", thbBallsAndStarsHitStats);
        ThbBallsAndStarsHitStats result = thbBallsAndStarsHitStatsRepository.save(thbBallsAndStarsHitStats);
        thbBallsAndStarsHitStatsSearchRepository.save(result);
        return result;
    }

    /**
     *  Get all the thbBallsAndStarsHitStats.
     *  
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<ThbBallsAndStarsHitStats> findAll() {
        log.debug("Request to get all ThbBallsAndStarsHitStats");
        List<ThbBallsAndStarsHitStats> result = thbBallsAndStarsHitStatsRepository.findAll();

        return result;
    }

    /**
     *  Get one thbBallsAndStarsHitStats by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public ThbBallsAndStarsHitStats findOne(Long id) {
        log.debug("Request to get ThbBallsAndStarsHitStats : {}", id);
        ThbBallsAndStarsHitStats thbBallsAndStarsHitStats = thbBallsAndStarsHitStatsRepository.findOne(id);
        return thbBallsAndStarsHitStats;
    }

    /**
     *  Delete the  thbBallsAndStarsHitStats by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ThbBallsAndStarsHitStats : {}", id);
        thbBallsAndStarsHitStatsRepository.delete(id);
        thbBallsAndStarsHitStatsSearchRepository.delete(id);
    }

    /**
     * Search for the thbBallsAndStarsHitStats corresponding to the query.
     *
     *  @param query the query of the search
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<ThbBallsAndStarsHitStats> search(String query) {
        log.debug("Request to search ThbBallsAndStarsHitStats for query {}", query);
        return StreamSupport
            .stream(thbBallsAndStarsHitStatsSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
