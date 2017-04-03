package ru.m2mcom.lotmicro.service.impl;

import ru.m2mcom.lotmicro.service.GameRuleService;
import ru.m2mcom.lotmicro.domain.GameRule;
import ru.m2mcom.lotmicro.repository.GameRuleRepository;
import ru.m2mcom.lotmicro.repository.search.GameRuleSearchRepository;
import ru.m2mcom.lotmicro.service.dto.GameRuleDTO;
import ru.m2mcom.lotmicro.service.mapper.GameRuleMapper;
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
 * Service Implementation for managing GameRule.
 */
@Service
@Transactional
public class GameRuleServiceImpl implements GameRuleService{

    private final Logger log = LoggerFactory.getLogger(GameRuleServiceImpl.class);
    
    private final GameRuleRepository gameRuleRepository;

    private final GameRuleMapper gameRuleMapper;

    private final GameRuleSearchRepository gameRuleSearchRepository;

    public GameRuleServiceImpl(GameRuleRepository gameRuleRepository, GameRuleMapper gameRuleMapper, GameRuleSearchRepository gameRuleSearchRepository) {
        this.gameRuleRepository = gameRuleRepository;
        this.gameRuleMapper = gameRuleMapper;
        this.gameRuleSearchRepository = gameRuleSearchRepository;
    }

    /**
     * Save a gameRule.
     *
     * @param gameRuleDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public GameRuleDTO save(GameRuleDTO gameRuleDTO) {
        log.debug("Request to save GameRule : {}", gameRuleDTO);
        GameRule gameRule = gameRuleMapper.gameRuleDTOToGameRule(gameRuleDTO);
        gameRule = gameRuleRepository.save(gameRule);
        GameRuleDTO result = gameRuleMapper.gameRuleToGameRuleDTO(gameRule);
        gameRuleSearchRepository.save(gameRule);
        return result;
    }

    /**
     *  Get all the gameRules.
     *  
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<GameRuleDTO> findAll() {
        log.debug("Request to get all GameRules");
        List<GameRuleDTO> result = gameRuleRepository.findAll().stream()
            .map(gameRuleMapper::gameRuleToGameRuleDTO)
            .collect(Collectors.toCollection(LinkedList::new));

        return result;
    }

    /**
     *  Get one gameRule by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public GameRuleDTO findOne(Long id) {
        log.debug("Request to get GameRule : {}", id);
        GameRule gameRule = gameRuleRepository.findOne(id);
        GameRuleDTO gameRuleDTO = gameRuleMapper.gameRuleToGameRuleDTO(gameRule);
        return gameRuleDTO;
    }

    /**
     *  Delete the  gameRule by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete GameRule : {}", id);
        gameRuleRepository.delete(id);
        gameRuleSearchRepository.delete(id);
    }

    /**
     * Search for the gameRule corresponding to the query.
     *
     *  @param query the query of the search
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<GameRuleDTO> search(String query) {
        log.debug("Request to search GameRules for query {}", query);
        return StreamSupport
            .stream(gameRuleSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(gameRuleMapper::gameRuleToGameRuleDTO)
            .collect(Collectors.toList());
    }
}
