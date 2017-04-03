package ru.m2mcom.lotmicro.service.impl;

import ru.m2mcom.lotmicro.service.GameService;
import ru.m2mcom.lotmicro.domain.Game;
import ru.m2mcom.lotmicro.repository.GameRepository;
import ru.m2mcom.lotmicro.repository.search.GameSearchRepository;
import ru.m2mcom.lotmicro.service.dto.GameDTO;
import ru.m2mcom.lotmicro.service.mapper.GameMapper;
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
 * Service Implementation for managing Game.
 */
@Service
@Transactional
public class GameServiceImpl implements GameService{

    private final Logger log = LoggerFactory.getLogger(GameServiceImpl.class);
    
    private final GameRepository gameRepository;

    private final GameMapper gameMapper;

    private final GameSearchRepository gameSearchRepository;

    public GameServiceImpl(GameRepository gameRepository, GameMapper gameMapper, GameSearchRepository gameSearchRepository) {
        this.gameRepository = gameRepository;
        this.gameMapper = gameMapper;
        this.gameSearchRepository = gameSearchRepository;
    }

    /**
     * Save a game.
     *
     * @param gameDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public GameDTO save(GameDTO gameDTO) {
        log.debug("Request to save Game : {}", gameDTO);
        Game game = gameMapper.gameDTOToGame(gameDTO);
        game = gameRepository.save(game);
        GameDTO result = gameMapper.gameToGameDTO(game);
        gameSearchRepository.save(game);
        return result;
    }

    /**
     *  Get all the games.
     *  
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<GameDTO> findAll() {
        log.debug("Request to get all Games");
        List<GameDTO> result = gameRepository.findAll().stream()
            .map(gameMapper::gameToGameDTO)
            .collect(Collectors.toCollection(LinkedList::new));

        return result;
    }

    /**
     *  Get one game by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public GameDTO findOne(Long id) {
        log.debug("Request to get Game : {}", id);
        Game game = gameRepository.findOne(id);
        GameDTO gameDTO = gameMapper.gameToGameDTO(game);
        return gameDTO;
    }

    /**
     *  Delete the  game by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Game : {}", id);
        gameRepository.delete(id);
        gameSearchRepository.delete(id);
    }

    /**
     * Search for the game corresponding to the query.
     *
     *  @param query the query of the search
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<GameDTO> search(String query) {
        log.debug("Request to search Games for query {}", query);
        return StreamSupport
            .stream(gameSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(gameMapper::gameToGameDTO)
            .collect(Collectors.toList());
    }
}
