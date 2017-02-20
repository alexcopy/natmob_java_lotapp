package ru.m2mcom.natmob.web.rest;

import com.codahale.metrics.annotation.Timed;
import ru.m2mcom.natmob.domain.ThbBallsAndStarsHitStats;
import ru.m2mcom.natmob.service.ThbBallsAndStarsHitStatsService;
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
 * REST controller for managing ThbBallsAndStarsHitStats.
 */
@RestController
@RequestMapping("/api")
public class ThbBallsAndStarsHitStatsResource {

    private final Logger log = LoggerFactory.getLogger(ThbBallsAndStarsHitStatsResource.class);

    private static final String ENTITY_NAME = "thbBallsAndStarsHitStats";
        
    private final ThbBallsAndStarsHitStatsService thbBallsAndStarsHitStatsService;

    public ThbBallsAndStarsHitStatsResource(ThbBallsAndStarsHitStatsService thbBallsAndStarsHitStatsService) {
        this.thbBallsAndStarsHitStatsService = thbBallsAndStarsHitStatsService;
    }

    /**
     * POST  /thb-balls-and-stars-hit-stats : Create a new thbBallsAndStarsHitStats.
     *
     * @param thbBallsAndStarsHitStats the thbBallsAndStarsHitStats to create
     * @return the ResponseEntity with status 201 (Created) and with body the new thbBallsAndStarsHitStats, or with status 400 (Bad Request) if the thbBallsAndStarsHitStats has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/thb-balls-and-stars-hit-stats")
    @Timed
    public ResponseEntity<ThbBallsAndStarsHitStats> createThbBallsAndStarsHitStats(@Valid @RequestBody ThbBallsAndStarsHitStats thbBallsAndStarsHitStats) throws URISyntaxException {
        log.debug("REST request to save ThbBallsAndStarsHitStats : {}", thbBallsAndStarsHitStats);
        if (thbBallsAndStarsHitStats.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new thbBallsAndStarsHitStats cannot already have an ID")).body(null);
        }
        ThbBallsAndStarsHitStats result = thbBallsAndStarsHitStatsService.save(thbBallsAndStarsHitStats);
        return ResponseEntity.created(new URI("/api/thb-balls-and-stars-hit-stats/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /thb-balls-and-stars-hit-stats : Updates an existing thbBallsAndStarsHitStats.
     *
     * @param thbBallsAndStarsHitStats the thbBallsAndStarsHitStats to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated thbBallsAndStarsHitStats,
     * or with status 400 (Bad Request) if the thbBallsAndStarsHitStats is not valid,
     * or with status 500 (Internal Server Error) if the thbBallsAndStarsHitStats couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/thb-balls-and-stars-hit-stats")
    @Timed
    public ResponseEntity<ThbBallsAndStarsHitStats> updateThbBallsAndStarsHitStats(@Valid @RequestBody ThbBallsAndStarsHitStats thbBallsAndStarsHitStats) throws URISyntaxException {
        log.debug("REST request to update ThbBallsAndStarsHitStats : {}", thbBallsAndStarsHitStats);
        if (thbBallsAndStarsHitStats.getId() == null) {
            return createThbBallsAndStarsHitStats(thbBallsAndStarsHitStats);
        }
        ThbBallsAndStarsHitStats result = thbBallsAndStarsHitStatsService.save(thbBallsAndStarsHitStats);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, thbBallsAndStarsHitStats.getId().toString()))
            .body(result);
    }

    /**
     * GET  /thb-balls-and-stars-hit-stats : get all the thbBallsAndStarsHitStats.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of thbBallsAndStarsHitStats in body
     */
    @GetMapping("/thb-balls-and-stars-hit-stats")
    @Timed
    public List<ThbBallsAndStarsHitStats> getAllThbBallsAndStarsHitStats() {
        log.debug("REST request to get all ThbBallsAndStarsHitStats");
        return thbBallsAndStarsHitStatsService.findAll();
    }

    /**
     * GET  /thb-balls-and-stars-hit-stats/:id : get the "id" thbBallsAndStarsHitStats.
     *
     * @param id the id of the thbBallsAndStarsHitStats to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the thbBallsAndStarsHitStats, or with status 404 (Not Found)
     */
    @GetMapping("/thb-balls-and-stars-hit-stats/{id}")
    @Timed
    public ResponseEntity<ThbBallsAndStarsHitStats> getThbBallsAndStarsHitStats(@PathVariable Long id) {
        log.debug("REST request to get ThbBallsAndStarsHitStats : {}", id);
        ThbBallsAndStarsHitStats thbBallsAndStarsHitStats = thbBallsAndStarsHitStatsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(thbBallsAndStarsHitStats));
    }

    /**
     * DELETE  /thb-balls-and-stars-hit-stats/:id : delete the "id" thbBallsAndStarsHitStats.
     *
     * @param id the id of the thbBallsAndStarsHitStats to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/thb-balls-and-stars-hit-stats/{id}")
    @Timed
    public ResponseEntity<Void> deleteThbBallsAndStarsHitStats(@PathVariable Long id) {
        log.debug("REST request to delete ThbBallsAndStarsHitStats : {}", id);
        thbBallsAndStarsHitStatsService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/thb-balls-and-stars-hit-stats?query=:query : search for the thbBallsAndStarsHitStats corresponding
     * to the query.
     *
     * @param query the query of the thbBallsAndStarsHitStats search 
     * @return the result of the search
     */
    @GetMapping("/_search/thb-balls-and-stars-hit-stats")
    @Timed
    public List<ThbBallsAndStarsHitStats> searchThbBallsAndStarsHitStats(@RequestParam String query) {
        log.debug("REST request to search ThbBallsAndStarsHitStats for query {}", query);
        return thbBallsAndStarsHitStatsService.search(query);
    }


}
