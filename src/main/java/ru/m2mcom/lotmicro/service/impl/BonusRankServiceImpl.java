package ru.m2mcom.lotmicro.service.impl;

import ru.m2mcom.lotmicro.service.BonusRankService;
import ru.m2mcom.lotmicro.domain.BonusRank;
import ru.m2mcom.lotmicro.repository.BonusRankRepository;
import ru.m2mcom.lotmicro.repository.search.BonusRankSearchRepository;
import ru.m2mcom.lotmicro.service.dto.BonusRankDTO;
import ru.m2mcom.lotmicro.service.mapper.BonusRankMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing BonusRank.
 */
@Service
@Transactional
public class BonusRankServiceImpl implements BonusRankService{

    private final Logger log = LoggerFactory.getLogger(BonusRankServiceImpl.class);
    
    private final BonusRankRepository bonusRankRepository;

    private final BonusRankMapper bonusRankMapper;

    private final BonusRankSearchRepository bonusRankSearchRepository;

    public BonusRankServiceImpl(BonusRankRepository bonusRankRepository, BonusRankMapper bonusRankMapper, BonusRankSearchRepository bonusRankSearchRepository) {
        this.bonusRankRepository = bonusRankRepository;
        this.bonusRankMapper = bonusRankMapper;
        this.bonusRankSearchRepository = bonusRankSearchRepository;
    }

    /**
     * Save a bonusRank.
     *
     * @param bonusRankDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public BonusRankDTO save(BonusRankDTO bonusRankDTO) {
        log.debug("Request to save BonusRank : {}", bonusRankDTO);
        BonusRank bonusRank = bonusRankMapper.bonusRankDTOToBonusRank(bonusRankDTO);
        bonusRank = bonusRankRepository.save(bonusRank);
        BonusRankDTO result = bonusRankMapper.bonusRankToBonusRankDTO(bonusRank);
        bonusRankSearchRepository.save(bonusRank);
        return result;
    }

    /**
     *  Get all the bonusRanks.
     *  
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<BonusRankDTO> findAll() {
        log.debug("Request to get all BonusRanks");
        List<BonusRankDTO> result = bonusRankRepository.findAll().stream()
            .map(bonusRankMapper::bonusRankToBonusRankDTO)
            .collect(Collectors.toCollection(LinkedList::new));

        return result;
    }

    /**
     *  Get one bonusRank by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public BonusRankDTO findOne(Long id) {
        log.debug("Request to get BonusRank : {}", id);
        BonusRank bonusRank = bonusRankRepository.findOne(id);
        BonusRankDTO bonusRankDTO = bonusRankMapper.bonusRankToBonusRankDTO(bonusRank);
        return bonusRankDTO;
    }

    /**
     *  Delete the  bonusRank by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete BonusRank : {}", id);
        bonusRankRepository.delete(id);
        bonusRankSearchRepository.delete(id);
    }

    /**
     * Search for the bonusRank corresponding to the query.
     *
     *  @param query the query of the search
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<BonusRankDTO> search(String query) {
        log.debug("Request to search BonusRanks for query {}", query);
        return StreamSupport
            .stream(bonusRankSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(bonusRankMapper::bonusRankToBonusRankDTO)
            .collect(Collectors.toList());
    }
}
