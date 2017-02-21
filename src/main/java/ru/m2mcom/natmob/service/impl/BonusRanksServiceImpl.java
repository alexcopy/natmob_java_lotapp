package ru.m2mcom.natmob.service.impl;

import ru.m2mcom.natmob.service.BonusRanksService;
import ru.m2mcom.natmob.domain.BonusRanks;
import ru.m2mcom.natmob.repository.BonusRanksRepository;
import ru.m2mcom.natmob.repository.search.BonusRanksSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing BonusRanks.
 */
@Service
@Transactional
public class BonusRanksServiceImpl implements BonusRanksService{

    private final Logger log = LoggerFactory.getLogger(BonusRanksServiceImpl.class);
    
    private final BonusRanksRepository bonusRanksRepository;

    private final BonusRanksSearchRepository bonusRanksSearchRepository;

    public BonusRanksServiceImpl(BonusRanksRepository bonusRanksRepository, BonusRanksSearchRepository bonusRanksSearchRepository) {
        this.bonusRanksRepository = bonusRanksRepository;
        this.bonusRanksSearchRepository = bonusRanksSearchRepository;
    }

    /**
     * Save a bonusRanks.
     *
     * @param bonusRanks the entity to save
     * @return the persisted entity
     */
    @Override
    public BonusRanks save(BonusRanks bonusRanks) {
        log.debug("Request to save BonusRanks : {}", bonusRanks);
        BonusRanks result = bonusRanksRepository.save(bonusRanks);
        bonusRanksSearchRepository.save(result);
        return result;
    }

    /**
     *  Get all the bonusRanks.
     *  
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<BonusRanks> findAll() {
        log.debug("Request to get all BonusRanks");
        List<BonusRanks> result = bonusRanksRepository.findAll();

        return result;
    }

    /**
     *  Get one bonusRanks by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public BonusRanks findOne(Long id) {
        log.debug("Request to get BonusRanks : {}", id);
        BonusRanks bonusRanks = bonusRanksRepository.findOne(id);
        return bonusRanks;
    }

    /**
     *  Delete the  bonusRanks by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete BonusRanks : {}", id);
        bonusRanksRepository.delete(id);
        bonusRanksSearchRepository.delete(id);
    }

    /**
     * Search for the bonusRanks corresponding to the query.
     *
     *  @param query the query of the search
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<BonusRanks> search(String query) {
        log.debug("Request to search BonusRanks for query {}", query);
        return StreamSupport
            .stream(bonusRanksSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
