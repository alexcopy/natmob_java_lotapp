package ru.m2mcom.natmob.web.rest;

import com.codahale.metrics.annotation.Timed;
import ru.m2mcom.natmob.domain.CombinedBallsAndStarsStats;
import ru.m2mcom.natmob.service.CombinedBallsAndStarsStatsService;
import ru.m2mcom.natmob.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing CombinedBallsAndStarsStats.
 */
@RestController
@RequestMapping("/api")
public class CombinedBallsAndStarsStatsResource {

    private final Logger log = LoggerFactory.getLogger(CombinedBallsAndStarsStatsResource.class);

    private static final String ENTITY_NAME = "combinedBallsAndStarsStats";
        
    private final CombinedBallsAndStarsStatsService combinedBallsAndStarsStatsService;

    public CombinedBallsAndStarsStatsResource(CombinedBallsAndStarsStatsService combinedBallsAndStarsStatsService) {
        this.combinedBallsAndStarsStatsService = combinedBallsAndStarsStatsService;
    }

    /**
     * POST  /combined-balls-and-stars-stats : Create a new combinedBallsAndStarsStats.
     *
     * @param combinedBallsAndStarsStats the combinedBallsAndStarsStats to create
     * @return the ResponseEntity with status 201 (Created) and with body the new combinedBallsAndStarsStats, or with status 400 (Bad Request) if the combinedBallsAndStarsStats has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/combined-balls-and-stars-stats")
    @Timed
    public ResponseEntity<CombinedBallsAndStarsStats> createCombinedBallsAndStarsStats(@Valid @RequestBody CombinedBallsAndStarsStats combinedBallsAndStarsStats) throws URISyntaxException {
        log.debug("REST request to save CombinedBallsAndStarsStats : {}", combinedBallsAndStarsStats);
        if (combinedBallsAndStarsStats.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new combinedBallsAndStarsStats cannot already have an ID")).body(null);
        }
        CombinedBallsAndStarsStats result = combinedBallsAndStarsStatsService.save(combinedBallsAndStarsStats);
        return ResponseEntity.created(new URI("/api/combined-balls-and-stars-stats/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /combined-balls-and-stars-stats : Updates an existing combinedBallsAndStarsStats.
     *
     * @param combinedBallsAndStarsStats the combinedBallsAndStarsStats to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated combinedBallsAndStarsStats,
     * or with status 400 (Bad Request) if the combinedBallsAndStarsStats is not valid,
     * or with status 500 (Internal Server Error) if the combinedBallsAndStarsStats couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/combined-balls-and-stars-stats")
    @Timed
    public ResponseEntity<CombinedBallsAndStarsStats> updateCombinedBallsAndStarsStats(@Valid @RequestBody CombinedBallsAndStarsStats combinedBallsAndStarsStats) throws URISyntaxException {
        log.debug("REST request to update CombinedBallsAndStarsStats : {}", combinedBallsAndStarsStats);
        if (combinedBallsAndStarsStats.getId() == null) {
            return createCombinedBallsAndStarsStats(combinedBallsAndStarsStats);
        }
        CombinedBallsAndStarsStats result = combinedBallsAndStarsStatsService.save(combinedBallsAndStarsStats);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, combinedBallsAndStarsStats.getId().toString()))
            .body(result);
    }

    /**
     * GET  /combined-balls-and-stars-stats : get all the combinedBallsAndStarsStats.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of combinedBallsAndStarsStats in body
     */
    @GetMapping("/combined-balls-and-stars-stats")
    @Timed
    public List<CombinedBallsAndStarsStats> getAllCombinedBallsAndStarsStats() {
        log.debug("REST request to get all CombinedBallsAndStarsStats");
        return combinedBallsAndStarsStatsService.findAll();
    }

    /**
     * GET  /combined-balls-and-stars-stats/:id : get the "id" combinedBallsAndStarsStats.
     *
     * @param id the id of the combinedBallsAndStarsStats to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the combinedBallsAndStarsStats, or with status 404 (Not Found)
     */
    @GetMapping("/combined-balls-and-stars-stats/{id}")
    @Timed
    public ResponseEntity<CombinedBallsAndStarsStats> getCombinedBallsAndStarsStats(@PathVariable Long id) {
        log.debug("REST request to get CombinedBallsAndStarsStats : {}", id);
        CombinedBallsAndStarsStats combinedBallsAndStarsStats = combinedBallsAndStarsStatsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(combinedBallsAndStarsStats));
    }

    /**
     * DELETE  /combined-balls-and-stars-stats/:id : delete the "id" combinedBallsAndStarsStats.
     *
     * @param id the id of the combinedBallsAndStarsStats to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/combined-balls-and-stars-stats/{id}")
    @Timed
    public ResponseEntity<Void> deleteCombinedBallsAndStarsStats(@PathVariable Long id) {
        log.debug("REST request to delete CombinedBallsAndStarsStats : {}", id);
        combinedBallsAndStarsStatsService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/combined-balls-and-stars-stats?query=:query : search for the combinedBallsAndStarsStats corresponding
     * to the query.
     *
     * @param query the query of the combinedBallsAndStarsStats search 
     * @return the result of the search
     */
    @GetMapping("/_search/combined-balls-and-stars-stats")
    @Timed
    public List<CombinedBallsAndStarsStats> searchCombinedBallsAndStarsStats(@RequestParam String query) {
        log.debug("REST request to search CombinedBallsAndStarsStats for query {}", query);
        return combinedBallsAndStarsStatsService.search(query);
    }


}
