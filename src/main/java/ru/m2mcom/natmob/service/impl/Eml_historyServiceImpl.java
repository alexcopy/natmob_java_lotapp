package ru.m2mcom.natmob.service.impl;

import ru.m2mcom.natmob.service.Eml_historyService;
import ru.m2mcom.natmob.domain.Eml_history;
import ru.m2mcom.natmob.repository.Eml_historyRepository;
import ru.m2mcom.natmob.repository.search.Eml_historySearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Eml_history.
 */
@Service
@Transactional
public class Eml_historyServiceImpl implements Eml_historyService{

    private final Logger log = LoggerFactory.getLogger(Eml_historyServiceImpl.class);
    
    private final Eml_historyRepository eml_historyRepository;

    private final Eml_historySearchRepository eml_historySearchRepository;

    public Eml_historyServiceImpl(Eml_historyRepository eml_historyRepository, Eml_historySearchRepository eml_historySearchRepository) {
        this.eml_historyRepository = eml_historyRepository;
        this.eml_historySearchRepository = eml_historySearchRepository;
    }

    /**
     * Save a eml_history.
     *
     * @param eml_history the entity to save
     * @return the persisted entity
     */
    @Override
    public Eml_history save(Eml_history eml_history) {
        log.debug("Request to save Eml_history : {}", eml_history);
        Eml_history result = eml_historyRepository.save(eml_history);
        eml_historySearchRepository.save(result);
        return result;
    }

    /**
     *  Get all the eml_histories.
     *  
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<Eml_history> findAll() {
        log.debug("Request to get all Eml_histories");
        List<Eml_history> result = eml_historyRepository.findAll();

        return result;
    }

    /**
     *  Get one eml_history by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Eml_history findOne(Long id) {
        log.debug("Request to get Eml_history : {}", id);
        Eml_history eml_history = eml_historyRepository.findOne(id);
        return eml_history;
    }

    /**
     *  Delete the  eml_history by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Eml_history : {}", id);
        eml_historyRepository.delete(id);
        eml_historySearchRepository.delete(id);
    }

    /**
     * Search for the eml_history corresponding to the query.
     *
     *  @param query the query of the search
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<Eml_history> search(String query) {
        log.debug("Request to search Eml_histories for query {}", query);
        return StreamSupport
            .stream(eml_historySearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
