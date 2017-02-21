package ru.m2mcom.natmob.service.impl;

import ru.m2mcom.natmob.service.RanksService;
import ru.m2mcom.natmob.domain.Ranks;
import ru.m2mcom.natmob.repository.RanksRepository;
import ru.m2mcom.natmob.repository.search.RanksSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Ranks.
 */
@Service
@Transactional
public class RanksServiceImpl implements RanksService{

    private final Logger log = LoggerFactory.getLogger(RanksServiceImpl.class);
    
    private final RanksRepository ranksRepository;

    private final RanksSearchRepository ranksSearchRepository;

    public RanksServiceImpl(RanksRepository ranksRepository, RanksSearchRepository ranksSearchRepository) {
        this.ranksRepository = ranksRepository;
        this.ranksSearchRepository = ranksSearchRepository;
    }

    /**
     * Save a ranks.
     *
     * @param ranks the entity to save
     * @return the persisted entity
     */
    @Override
    public Ranks save(Ranks ranks) {
        log.debug("Request to save Ranks : {}", ranks);
        Ranks result = ranksRepository.save(ranks);
        ranksSearchRepository.save(result);
        return result;
    }

    /**
     *  Get all the ranks.
     *  
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<Ranks> findAll() {
        log.debug("Request to get all Ranks");
        List<Ranks> result = ranksRepository.findAll();

        return result;
    }

    /**
     *  Get one ranks by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Ranks findOne(Long id) {
        log.debug("Request to get Ranks : {}", id);
        Ranks ranks = ranksRepository.findOne(id);
        return ranks;
    }

    /**
     *  Delete the  ranks by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Ranks : {}", id);
        ranksRepository.delete(id);
        ranksSearchRepository.delete(id);
    }

    /**
     * Search for the ranks corresponding to the query.
     *
     *  @param query the query of the search
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<Ranks> search(String query) {
        log.debug("Request to search Ranks for query {}", query);
        return StreamSupport
            .stream(ranksSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
