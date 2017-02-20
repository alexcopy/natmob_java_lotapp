package ru.m2mcom.natmob.service.impl;

import ru.m2mcom.natmob.service.HitBallsAndStarsPredictionService;
import ru.m2mcom.natmob.domain.HitBallsAndStarsPrediction;
import ru.m2mcom.natmob.repository.HitBallsAndStarsPredictionRepository;
import ru.m2mcom.natmob.repository.search.HitBallsAndStarsPredictionSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing HitBallsAndStarsPrediction.
 */
@Service
@Transactional
public class HitBallsAndStarsPredictionServiceImpl implements HitBallsAndStarsPredictionService{

    private final Logger log = LoggerFactory.getLogger(HitBallsAndStarsPredictionServiceImpl.class);
    
    private final HitBallsAndStarsPredictionRepository hitBallsAndStarsPredictionRepository;

    private final HitBallsAndStarsPredictionSearchRepository hitBallsAndStarsPredictionSearchRepository;

    public HitBallsAndStarsPredictionServiceImpl(HitBallsAndStarsPredictionRepository hitBallsAndStarsPredictionRepository, HitBallsAndStarsPredictionSearchRepository hitBallsAndStarsPredictionSearchRepository) {
        this.hitBallsAndStarsPredictionRepository = hitBallsAndStarsPredictionRepository;
        this.hitBallsAndStarsPredictionSearchRepository = hitBallsAndStarsPredictionSearchRepository;
    }

    /**
     * Save a hitBallsAndStarsPrediction.
     *
     * @param hitBallsAndStarsPrediction the entity to save
     * @return the persisted entity
     */
    @Override
    public HitBallsAndStarsPrediction save(HitBallsAndStarsPrediction hitBallsAndStarsPrediction) {
        log.debug("Request to save HitBallsAndStarsPrediction : {}", hitBallsAndStarsPrediction);
        HitBallsAndStarsPrediction result = hitBallsAndStarsPredictionRepository.save(hitBallsAndStarsPrediction);
        hitBallsAndStarsPredictionSearchRepository.save(result);
        return result;
    }

    /**
     *  Get all the hitBallsAndStarsPredictions.
     *  
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<HitBallsAndStarsPrediction> findAll() {
        log.debug("Request to get all HitBallsAndStarsPredictions");
        List<HitBallsAndStarsPrediction> result = hitBallsAndStarsPredictionRepository.findAll();

        return result;
    }

    /**
     *  Get one hitBallsAndStarsPrediction by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public HitBallsAndStarsPrediction findOne(Long id) {
        log.debug("Request to get HitBallsAndStarsPrediction : {}", id);
        HitBallsAndStarsPrediction hitBallsAndStarsPrediction = hitBallsAndStarsPredictionRepository.findOne(id);
        return hitBallsAndStarsPrediction;
    }

    /**
     *  Delete the  hitBallsAndStarsPrediction by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete HitBallsAndStarsPrediction : {}", id);
        hitBallsAndStarsPredictionRepository.delete(id);
        hitBallsAndStarsPredictionSearchRepository.delete(id);
    }

    /**
     * Search for the hitBallsAndStarsPrediction corresponding to the query.
     *
     *  @param query the query of the search
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<HitBallsAndStarsPrediction> search(String query) {
        log.debug("Request to search HitBallsAndStarsPredictions for query {}", query);
        return StreamSupport
            .stream(hitBallsAndStarsPredictionSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
