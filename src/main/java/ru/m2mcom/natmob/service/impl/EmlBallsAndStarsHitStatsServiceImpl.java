package ru.m2mcom.natmob.service.impl;

import ru.m2mcom.natmob.service.EmlBallsAndStarsHitStatsService;
import ru.m2mcom.natmob.domain.EmlBallsAndStarsHitStats;
import ru.m2mcom.natmob.repository.EmlBallsAndStarsHitStatsRepository;
import ru.m2mcom.natmob.repository.search.EmlBallsAndStarsHitStatsSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing EmlBallsAndStarsHitStats.
 */
@Service
@Transactional
public class EmlBallsAndStarsHitStatsServiceImpl implements EmlBallsAndStarsHitStatsService{

    private final Logger log = LoggerFactory.getLogger(EmlBallsAndStarsHitStatsServiceImpl.class);
    
    private final EmlBallsAndStarsHitStatsRepository emlBallsAndStarsHitStatsRepository;

    private final EmlBallsAndStarsHitStatsSearchRepository emlBallsAndStarsHitStatsSearchRepository;

    public EmlBallsAndStarsHitStatsServiceImpl(EmlBallsAndStarsHitStatsRepository emlBallsAndStarsHitStatsRepository, EmlBallsAndStarsHitStatsSearchRepository emlBallsAndStarsHitStatsSearchRepository) {
        this.emlBallsAndStarsHitStatsRepository = emlBallsAndStarsHitStatsRepository;
        this.emlBallsAndStarsHitStatsSearchRepository = emlBallsAndStarsHitStatsSearchRepository;
    }

    /**
     * Save a emlBallsAndStarsHitStats.
     *
     * @param emlBallsAndStarsHitStats the entity to save
     * @return the persisted entity
     */
    @Override
    public EmlBallsAndStarsHitStats save(EmlBallsAndStarsHitStats emlBallsAndStarsHitStats) {
        log.debug("Request to save EmlBallsAndStarsHitStats : {}", emlBallsAndStarsHitStats);
        EmlBallsAndStarsHitStats result = emlBallsAndStarsHitStatsRepository.save(emlBallsAndStarsHitStats);
        emlBallsAndStarsHitStatsSearchRepository.save(result);
        return result;
    }

    /**
     *  Get all the emlBallsAndStarsHitStats.
     *  
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<EmlBallsAndStarsHitStats> findAll() {
        log.debug("Request to get all EmlBallsAndStarsHitStats");
        List<EmlBallsAndStarsHitStats> result = emlBallsAndStarsHitStatsRepository.findAll();

        return result;
    }

    /**
     *  Get one emlBallsAndStarsHitStats by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public EmlBallsAndStarsHitStats findOne(Long id) {
        log.debug("Request to get EmlBallsAndStarsHitStats : {}", id);
        EmlBallsAndStarsHitStats emlBallsAndStarsHitStats = emlBallsAndStarsHitStatsRepository.findOne(id);
        return emlBallsAndStarsHitStats;
    }

    /**
     *  Delete the  emlBallsAndStarsHitStats by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete EmlBallsAndStarsHitStats : {}", id);
        emlBallsAndStarsHitStatsRepository.delete(id);
        emlBallsAndStarsHitStatsSearchRepository.delete(id);
    }

    /**
     * Search for the emlBallsAndStarsHitStats corresponding to the query.
     *
     *  @param query the query of the search
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<EmlBallsAndStarsHitStats> search(String query) {
        log.debug("Request to search EmlBallsAndStarsHitStats for query {}", query);
        return StreamSupport
            .stream(emlBallsAndStarsHitStatsSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
