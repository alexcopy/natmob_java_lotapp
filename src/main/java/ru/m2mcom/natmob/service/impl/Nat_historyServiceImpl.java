package ru.m2mcom.natmob.service.impl;

import ru.m2mcom.natmob.service.Nat_historyService;
import ru.m2mcom.natmob.domain.Nat_history;
import ru.m2mcom.natmob.repository.Nat_historyRepository;
import ru.m2mcom.natmob.repository.search.Nat_historySearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Nat_history.
 */
@Service
@Transactional
public class Nat_historyServiceImpl implements Nat_historyService{

    private final Logger log = LoggerFactory.getLogger(Nat_historyServiceImpl.class);
    
    private final Nat_historyRepository nat_historyRepository;

    private final Nat_historySearchRepository nat_historySearchRepository;

    public Nat_historyServiceImpl(Nat_historyRepository nat_historyRepository, Nat_historySearchRepository nat_historySearchRepository) {
        this.nat_historyRepository = nat_historyRepository;
        this.nat_historySearchRepository = nat_historySearchRepository;
    }

    /**
     * Save a nat_history.
     *
     * @param nat_history the entity to save
     * @return the persisted entity
     */
    @Override
    public Nat_history save(Nat_history nat_history) {
        log.debug("Request to save Nat_history : {}", nat_history);
        Nat_history result = nat_historyRepository.save(nat_history);
        nat_historySearchRepository.save(result);
        return result;
    }

    /**
     *  Get all the nat_histories.
     *  
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<Nat_history> findAll() {
        log.debug("Request to get all Nat_histories");
        List<Nat_history> result = nat_historyRepository.findAll();

        return result;
    }

    /**
     *  Get one nat_history by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Nat_history findOne(Long id) {
        log.debug("Request to get Nat_history : {}", id);
        Nat_history nat_history = nat_historyRepository.findOne(id);
        return nat_history;
    }

    /**
     *  Delete the  nat_history by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Nat_history : {}", id);
        nat_historyRepository.delete(id);
        nat_historySearchRepository.delete(id);
    }

    /**
     * Search for the nat_history corresponding to the query.
     *
     *  @param query the query of the search
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<Nat_history> search(String query) {
        log.debug("Request to search Nat_histories for query {}", query);
        return StreamSupport
            .stream(nat_historySearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
