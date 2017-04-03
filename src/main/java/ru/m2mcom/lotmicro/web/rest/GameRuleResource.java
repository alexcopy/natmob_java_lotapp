package ru.m2mcom.lotmicro.web.rest;

import com.codahale.metrics.annotation.Timed;
import ru.m2mcom.lotmicro.service.GameRuleService;
import ru.m2mcom.lotmicro.web.rest.util.HeaderUtil;
import ru.m2mcom.lotmicro.service.dto.GameRuleDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing GameRule.
 */
@RestController
@RequestMapping("/api")
public class GameRuleResource {

    private final Logger log = LoggerFactory.getLogger(GameRuleResource.class);

    private static final String ENTITY_NAME = "gameRule";
        
    private final GameRuleService gameRuleService;

    public GameRuleResource(GameRuleService gameRuleService) {
        this.gameRuleService = gameRuleService;
    }

    /**
     * POST  /game-rules : Create a new gameRule.
     *
     * @param gameRuleDTO the gameRuleDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new gameRuleDTO, or with status 400 (Bad Request) if the gameRule has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/game-rules")
    @Timed
    public ResponseEntity<GameRuleDTO> createGameRule(@Valid @RequestBody GameRuleDTO gameRuleDTO) throws URISyntaxException {
        log.debug("REST request to save GameRule : {}", gameRuleDTO);
        if (gameRuleDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new gameRule cannot already have an ID")).body(null);
        }
        GameRuleDTO result = gameRuleService.save(gameRuleDTO);
        return ResponseEntity.created(new URI("/api/game-rules/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /game-rules : Updates an existing gameRule.
     *
     * @param gameRuleDTO the gameRuleDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated gameRuleDTO,
     * or with status 400 (Bad Request) if the gameRuleDTO is not valid,
     * or with status 500 (Internal Server Error) if the gameRuleDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/game-rules")
    @Timed
    public ResponseEntity<GameRuleDTO> updateGameRule(@Valid @RequestBody GameRuleDTO gameRuleDTO) throws URISyntaxException {
        log.debug("REST request to update GameRule : {}", gameRuleDTO);
        if (gameRuleDTO.getId() == null) {
            return createGameRule(gameRuleDTO);
        }
        GameRuleDTO result = gameRuleService.save(gameRuleDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, gameRuleDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /game-rules : get all the gameRules.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of gameRules in body
     */
    @GetMapping("/game-rules")
    @Timed
    public List<GameRuleDTO> getAllGameRules() {
        log.debug("REST request to get all GameRules");
        return gameRuleService.findAll();
    }

    /**
     * GET  /game-rules/:id : get the "id" gameRule.
     *
     * @param id the id of the gameRuleDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the gameRuleDTO, or with status 404 (Not Found)
     */
    @GetMapping("/game-rules/{id}")
    @Timed
    public ResponseEntity<GameRuleDTO> getGameRule(@PathVariable Long id) {
        log.debug("REST request to get GameRule : {}", id);
        GameRuleDTO gameRuleDTO = gameRuleService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(gameRuleDTO));
    }

    /**
     * DELETE  /game-rules/:id : delete the "id" gameRule.
     *
     * @param id the id of the gameRuleDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/game-rules/{id}")
    @Timed
    public ResponseEntity<Void> deleteGameRule(@PathVariable Long id) {
        log.debug("REST request to delete GameRule : {}", id);
        gameRuleService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/game-rules?query=:query : search for the gameRule corresponding
     * to the query.
     *
     * @param query the query of the gameRule search 
     * @return the result of the search
     */
    @GetMapping("/_search/game-rules")
    @Timed
    public List<GameRuleDTO> searchGameRules(@RequestParam String query) {
        log.debug("REST request to search GameRules for query {}", query);
        return gameRuleService.search(query);
    }


}
