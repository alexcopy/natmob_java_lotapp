package ru.m2mcom.natmob.service.impl;

import ru.m2mcom.natmob.service.Thb_historyService;
import ru.m2mcom.natmob.domain.Thb_history;
import ru.m2mcom.natmob.repository.Thb_historyRepository;
import ru.m2mcom.natmob.repository.search.Thb_historySearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Thb_history.
 */
@Service
@Transactional
public class Thb_historyServiceImpl implements Thb_historyService{

    private final Logger log = LoggerFactory.getLogger(Thb_historyServiceImpl.class);
    
    private final Thb_historyRepository thb_historyRepository;

    private final Thb_historySearchRepository thb_historySearchRepository;

    public Thb_historyServiceImpl(Thb_historyRepository thb_historyRepository, Thb_historySearchRepository thb_historySearchRepository) {
        this.thb_historyRepository = thb_historyRepository;
        this.thb_historySearchRepository = thb_historySearchRepository;
    }

    /**
     * Save a thb_history.
     *
     * @param thb_history the entity to save
     * @return the persisted entity
     */
    @Override
    public Thb_history save(Thb_history thb_history) {
        log.debug("Request to save Thb_history : {}", thb_history);
        Thb_history result = thb_historyRepository.save(thb_history);
        thb_historySearchRepository.save(result);
        return result;
    }

    /**
     *  Get all the thb_histories.
     *  
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<Thb_history> findAll() {
        log.debug("Request to get all Thb_histories");
        List<Thb_history> result = thb_historyRepository.findAll();

        return result;
    }

    /**
     *  Get one thb_history by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Thb_history findOne(Long id) {
        log.debug("Request to get Thb_history : {}", id);
        Thb_history thb_history = thb_historyRepository.findOne(id);
        return thb_history;
    }

    /**
     *  Delete the  thb_history by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Thb_history : {}", id);
        thb_historyRepository.delete(id);
        thb_historySearchRepository.delete(id);
    }

    /**
     * Search for the thb_history corresponding to the query.
     *
     *  @param query the query of the search
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<Thb_history> search(String query) {
        log.debug("Request to search Thb_histories for query {}", query);
        return StreamSupport
            .stream(thb_historySearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
