package ru.m2mcom.natmob.service.impl;

import ru.m2mcom.natmob.service.Eml_playsService;
import ru.m2mcom.natmob.domain.Eml_plays;
import ru.m2mcom.natmob.repository.Eml_playsRepository;
import ru.m2mcom.natmob.repository.search.Eml_playsSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Eml_plays.
 */
@Service
@Transactional
public class Eml_playsServiceImpl implements Eml_playsService{

    private final Logger log = LoggerFactory.getLogger(Eml_playsServiceImpl.class);
    
    private final Eml_playsRepository eml_playsRepository;

    private final Eml_playsSearchRepository eml_playsSearchRepository;

    public Eml_playsServiceImpl(Eml_playsRepository eml_playsRepository, Eml_playsSearchRepository eml_playsSearchRepository) {
        this.eml_playsRepository = eml_playsRepository;
        this.eml_playsSearchRepository = eml_playsSearchRepository;
    }

    /**
     * Save a eml_plays.
     *
     * @param eml_plays the entity to save
     * @return the persisted entity
     */
    @Override
    public Eml_plays save(Eml_plays eml_plays) {
        log.debug("Request to save Eml_plays : {}", eml_plays);
        Eml_plays result = eml_playsRepository.save(eml_plays);
        eml_playsSearchRepository.save(result);
        return result;
    }

    /**
     *  Get all the eml_plays.
     *  
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<Eml_plays> findAll() {
        log.debug("Request to get all Eml_plays");
        List<Eml_plays> result = eml_playsRepository.findAll();

        return result;
    }

    /**
     *  Get one eml_plays by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Eml_plays findOne(Long id) {
        log.debug("Request to get Eml_plays : {}", id);
        Eml_plays eml_plays = eml_playsRepository.findOne(id);
        return eml_plays;
    }

    /**
     *  Delete the  eml_plays by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Eml_plays : {}", id);
        eml_playsRepository.delete(id);
        eml_playsSearchRepository.delete(id);
    }

    /**
     * Search for the eml_plays corresponding to the query.
     *
     *  @param query the query of the search
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<Eml_plays> search(String query) {
        log.debug("Request to search Eml_plays for query {}", query);
        return StreamSupport
            .stream(eml_playsSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
