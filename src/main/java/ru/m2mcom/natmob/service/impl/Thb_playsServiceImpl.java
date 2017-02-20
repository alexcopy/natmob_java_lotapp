package ru.m2mcom.natmob.service.impl;

import ru.m2mcom.natmob.service.Thb_playsService;
import ru.m2mcom.natmob.domain.Thb_plays;
import ru.m2mcom.natmob.repository.Thb_playsRepository;
import ru.m2mcom.natmob.repository.search.Thb_playsSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Thb_plays.
 */
@Service
@Transactional
public class Thb_playsServiceImpl implements Thb_playsService{

    private final Logger log = LoggerFactory.getLogger(Thb_playsServiceImpl.class);
    
    private final Thb_playsRepository thb_playsRepository;

    private final Thb_playsSearchRepository thb_playsSearchRepository;

    public Thb_playsServiceImpl(Thb_playsRepository thb_playsRepository, Thb_playsSearchRepository thb_playsSearchRepository) {
        this.thb_playsRepository = thb_playsRepository;
        this.thb_playsSearchRepository = thb_playsSearchRepository;
    }

    /**
     * Save a thb_plays.
     *
     * @param thb_plays the entity to save
     * @return the persisted entity
     */
    @Override
    public Thb_plays save(Thb_plays thb_plays) {
        log.debug("Request to save Thb_plays : {}", thb_plays);
        Thb_plays result = thb_playsRepository.save(thb_plays);
        thb_playsSearchRepository.save(result);
        return result;
    }

    /**
     *  Get all the thb_plays.
     *  
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<Thb_plays> findAll() {
        log.debug("Request to get all Thb_plays");
        List<Thb_plays> result = thb_playsRepository.findAll();

        return result;
    }

    /**
     *  Get one thb_plays by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Thb_plays findOne(Long id) {
        log.debug("Request to get Thb_plays : {}", id);
        Thb_plays thb_plays = thb_playsRepository.findOne(id);
        return thb_plays;
    }

    /**
     *  Delete the  thb_plays by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Thb_plays : {}", id);
        thb_playsRepository.delete(id);
        thb_playsSearchRepository.delete(id);
    }

    /**
     * Search for the thb_plays corresponding to the query.
     *
     *  @param query the query of the search
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<Thb_plays> search(String query) {
        log.debug("Request to search Thb_plays for query {}", query);
        return StreamSupport
            .stream(thb_playsSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
