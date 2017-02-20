package ru.m2mcom.natmob.service.impl;

import ru.m2mcom.natmob.service.CombinedBallsAndStarsStatsService;
import ru.m2mcom.natmob.domain.CombinedBallsAndStarsStats;
import ru.m2mcom.natmob.repository.CombinedBallsAndStarsStatsRepository;
import ru.m2mcom.natmob.repository.search.CombinedBallsAndStarsStatsSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing CombinedBallsAndStarsStats.
 */
@Service
@Transactional
public class CombinedBallsAndStarsStatsServiceImpl implements CombinedBallsAndStarsStatsService{

    private final Logger log = LoggerFactory.getLogger(CombinedBallsAndStarsStatsServiceImpl.class);
    
    private final CombinedBallsAndStarsStatsRepository combinedBallsAndStarsStatsRepository;

    private final CombinedBallsAndStarsStatsSearchRepository combinedBallsAndStarsStatsSearchRepository;

    public CombinedBallsAndStarsStatsServiceImpl(CombinedBallsAndStarsStatsRepository combinedBallsAndStarsStatsRepository, CombinedBallsAndStarsStatsSearchRepository combinedBallsAndStarsStatsSearchRepository) {
        this.combinedBallsAndStarsStatsRepository = combinedBallsAndStarsStatsRepository;
        this.combinedBallsAndStarsStatsSearchRepository = combinedBallsAndStarsStatsSearchRepository;
    }

    /**
     * Save a combinedBallsAndStarsStats.
     *
     * @param combinedBallsAndStarsStats the entity to save
     * @return the persisted entity
     */
    @Override
    public CombinedBallsAndStarsStats save(CombinedBallsAndStarsStats combinedBallsAndStarsStats) {
        log.debug("Request to save CombinedBallsAndStarsStats : {}", combinedBallsAndStarsStats);
        CombinedBallsAndStarsStats result = combinedBallsAndStarsStatsRepository.save(combinedBallsAndStarsStats);
        combinedBallsAndStarsStatsSearchRepository.save(result);
        return result;
    }

    /**
     *  Get all the combinedBallsAndStarsStats.
     *  
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<CombinedBallsAndStarsStats> findAll() {
        log.debug("Request to get all CombinedBallsAndStarsStats");
        List<CombinedBallsAndStarsStats> result = combinedBallsAndStarsStatsRepository.findAll();

        return result;
    }

    /**
     *  Get one combinedBallsAndStarsStats by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public CombinedBallsAndStarsStats findOne(Long id) {
        log.debug("Request to get CombinedBallsAndStarsStats : {}", id);
        CombinedBallsAndStarsStats combinedBallsAndStarsStats = combinedBallsAndStarsStatsRepository.findOne(id);
        return combinedBallsAndStarsStats;
    }

    /**
     *  Delete the  combinedBallsAndStarsStats by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete CombinedBallsAndStarsStats : {}", id);
        combinedBallsAndStarsStatsRepository.delete(id);
        combinedBallsAndStarsStatsSearchRepository.delete(id);
    }

    /**
     * Search for the combinedBallsAndStarsStats corresponding to the query.
     *
     *  @param query the query of the search
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<CombinedBallsAndStarsStats> search(String query) {
        log.debug("Request to search CombinedBallsAndStarsStats for query {}", query);
        return StreamSupport
            .stream(combinedBallsAndStarsStatsSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
