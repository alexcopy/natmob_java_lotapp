package ru.m2mcom.natmob.service.impl;

import ru.m2mcom.natmob.service.Nat_playsService;
import ru.m2mcom.natmob.domain.Nat_plays;
import ru.m2mcom.natmob.repository.Nat_playsRepository;
import ru.m2mcom.natmob.repository.search.Nat_playsSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Nat_plays.
 */
@Service
@Transactional
public class Nat_playsServiceImpl implements Nat_playsService{

    private final Logger log = LoggerFactory.getLogger(Nat_playsServiceImpl.class);
    
    private final Nat_playsRepository nat_playsRepository;

    private final Nat_playsSearchRepository nat_playsSearchRepository;

    public Nat_playsServiceImpl(Nat_playsRepository nat_playsRepository, Nat_playsSearchRepository nat_playsSearchRepository) {
        this.nat_playsRepository = nat_playsRepository;
        this.nat_playsSearchRepository = nat_playsSearchRepository;
    }

    /**
     * Save a nat_plays.
     *
     * @param nat_plays the entity to save
     * @return the persisted entity
     */
    @Override
    public Nat_plays save(Nat_plays nat_plays) {
        log.debug("Request to save Nat_plays : {}", nat_plays);
        Nat_plays result = nat_playsRepository.save(nat_plays);
        nat_playsSearchRepository.save(result);
        return result;
    }

    /**
     *  Get all the nat_plays.
     *  
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<Nat_plays> findAll() {
        log.debug("Request to get all Nat_plays");
        List<Nat_plays> result = nat_playsRepository.findAll();

        return result;
    }

    /**
     *  Get one nat_plays by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Nat_plays findOne(Long id) {
        log.debug("Request to get Nat_plays : {}", id);
        Nat_plays nat_plays = nat_playsRepository.findOne(id);
        return nat_plays;
    }

    /**
     *  Delete the  nat_plays by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Nat_plays : {}", id);
        nat_playsRepository.delete(id);
        nat_playsSearchRepository.delete(id);
    }

    /**
     * Search for the nat_plays corresponding to the query.
     *
     *  @param query the query of the search
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<Nat_plays> search(String query) {
        log.debug("Request to search Nat_plays for query {}", query);
        return StreamSupport
            .stream(nat_playsSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
