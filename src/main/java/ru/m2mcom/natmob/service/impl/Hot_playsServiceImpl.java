package ru.m2mcom.natmob.service.impl;

import ru.m2mcom.natmob.service.Hot_playsService;
import ru.m2mcom.natmob.domain.Hot_plays;
import ru.m2mcom.natmob.repository.Hot_playsRepository;
import ru.m2mcom.natmob.repository.search.Hot_playsSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Hot_plays.
 */
@Service
@Transactional
public class Hot_playsServiceImpl implements Hot_playsService{

    private final Logger log = LoggerFactory.getLogger(Hot_playsServiceImpl.class);
    
    private final Hot_playsRepository hot_playsRepository;

    private final Hot_playsSearchRepository hot_playsSearchRepository;

    public Hot_playsServiceImpl(Hot_playsRepository hot_playsRepository, Hot_playsSearchRepository hot_playsSearchRepository) {
        this.hot_playsRepository = hot_playsRepository;
        this.hot_playsSearchRepository = hot_playsSearchRepository;
    }

    /**
     * Save a hot_plays.
     *
     * @param hot_plays the entity to save
     * @return the persisted entity
     */
    @Override
    public Hot_plays save(Hot_plays hot_plays) {
        log.debug("Request to save Hot_plays : {}", hot_plays);
        Hot_plays result = hot_playsRepository.save(hot_plays);
        hot_playsSearchRepository.save(result);
        return result;
    }

    /**
     *  Get all the hot_plays.
     *  
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<Hot_plays> findAll() {
        log.debug("Request to get all Hot_plays");
        List<Hot_plays> result = hot_playsRepository.findAll();

        return result;
    }

    /**
     *  Get one hot_plays by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Hot_plays findOne(Long id) {
        log.debug("Request to get Hot_plays : {}", id);
        Hot_plays hot_plays = hot_playsRepository.findOne(id);
        return hot_plays;
    }

    /**
     *  Delete the  hot_plays by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Hot_plays : {}", id);
        hot_playsRepository.delete(id);
        hot_playsSearchRepository.delete(id);
    }

    /**
     * Search for the hot_plays corresponding to the query.
     *
     *  @param query the query of the search
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<Hot_plays> search(String query) {
        log.debug("Request to search Hot_plays for query {}", query);
        return StreamSupport
            .stream(hot_playsSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
