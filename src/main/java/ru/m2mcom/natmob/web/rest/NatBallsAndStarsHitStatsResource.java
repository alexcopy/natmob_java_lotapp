package ru.m2mcom.natmob.web.rest;

import com.codahale.metrics.annotation.Timed;
import ru.m2mcom.natmob.domain.NatBallsAndStarsHitStats;
import ru.m2mcom.natmob.service.NatBallsAndStarsHitStatsService;
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
 * REST controller for managing NatBallsAndStarsHitStats.
 */
@RestController
@RequestMapping("/api")
public class NatBallsAndStarsHitStatsResource {

    private final Logger log = LoggerFactory.getLogger(NatBallsAndStarsHitStatsResource.class);

    private static final String ENTITY_NAME = "natBallsAndStarsHitStats";
        
    private final NatBallsAndStarsHitStatsService natBallsAndStarsHitStatsService;

    public NatBallsAndStarsHitStatsResource(NatBallsAndStarsHitStatsService natBallsAndStarsHitStatsService) {
        this.natBallsAndStarsHitStatsService = natBallsAndStarsHitStatsService;
    }

    /**
     * POST  /nat-balls-and-stars-hit-stats : Create a new natBallsAndStarsHitStats.
     *
     * @param natBallsAndStarsHitStats the natBallsAndStarsHitStats to create
     * @return the ResponseEntity with status 201 (Created) and with body the new natBallsAndStarsHitStats, or with status 400 (Bad Request) if the natBallsAndStarsHitStats has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/nat-balls-and-stars-hit-stats")
    @Timed
    public ResponseEntity<NatBallsAndStarsHitStats> createNatBallsAndStarsHitStats(@Valid @RequestBody NatBallsAndStarsHitStats natBallsAndStarsHitStats) throws URISyntaxException {
        log.debug("REST request to save NatBallsAndStarsHitStats : {}", natBallsAndStarsHitStats);
        if (natBallsAndStarsHitStats.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new natBallsAndStarsHitStats cannot already have an ID")).body(null);
        }
        NatBallsAndStarsHitStats result = natBallsAndStarsHitStatsService.save(natBallsAndStarsHitStats);
        return ResponseEntity.created(new URI("/api/nat-balls-and-stars-hit-stats/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /nat-balls-and-stars-hit-stats : Updates an existing natBallsAndStarsHitStats.
     *
     * @param natBallsAndStarsHitStats the natBallsAndStarsHitStats to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated natBallsAndStarsHitStats,
     * or with status 400 (Bad Request) if the natBallsAndStarsHitStats is not valid,
     * or with status 500 (Internal Server Error) if the natBallsAndStarsHitStats couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/nat-balls-and-stars-hit-stats")
    @Timed
    public ResponseEntity<NatBallsAndStarsHitStats> updateNatBallsAndStarsHitStats(@Valid @RequestBody NatBallsAndStarsHitStats natBallsAndStarsHitStats) throws URISyntaxException {
        log.debug("REST request to update NatBallsAndStarsHitStats : {}", natBallsAndStarsHitStats);
        if (natBallsAndStarsHitStats.getId() == null) {
            return createNatBallsAndStarsHitStats(natBallsAndStarsHitStats);
        }
        NatBallsAndStarsHitStats result = natBallsAndStarsHitStatsService.save(natBallsAndStarsHitStats);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, natBallsAndStarsHitStats.getId().toString()))
            .body(result);
    }

    /**
     * GET  /nat-balls-and-stars-hit-stats : get all the natBallsAndStarsHitStats.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of natBallsAndStarsHitStats in body
     */
    @GetMapping("/nat-balls-and-stars-hit-stats")
    @Timed
    public List<NatBallsAndStarsHitStats> getAllNatBallsAndStarsHitStats() {
        log.debug("REST request to get all NatBallsAndStarsHitStats");
        return natBallsAndStarsHitStatsService.findAll();
    }

    /**
     * GET  /nat-balls-and-stars-hit-stats/:id : get the "id" natBallsAndStarsHitStats.
     *
     * @param id the id of the natBallsAndStarsHitStats to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the natBallsAndStarsHitStats, or with status 404 (Not Found)
     */
    @GetMapping("/nat-balls-and-stars-hit-stats/{id}")
    @Timed
    public ResponseEntity<NatBallsAndStarsHitStats> getNatBallsAndStarsHitStats(@PathVariable Long id) {
        log.debug("REST request to get NatBallsAndStarsHitStats : {}", id);
        NatBallsAndStarsHitStats natBallsAndStarsHitStats = natBallsAndStarsHitStatsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(natBallsAndStarsHitStats));
    }

    /**
     * DELETE  /nat-balls-and-stars-hit-stats/:id : delete the "id" natBallsAndStarsHitStats.
     *
     * @param id the id of the natBallsAndStarsHitStats to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/nat-balls-and-stars-hit-stats/{id}")
    @Timed
    public ResponseEntity<Void> deleteNatBallsAndStarsHitStats(@PathVariable Long id) {
        log.debug("REST request to delete NatBallsAndStarsHitStats : {}", id);
        natBallsAndStarsHitStatsService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/nat-balls-and-stars-hit-stats?query=:query : search for the natBallsAndStarsHitStats corresponding
     * to the query.
     *
     * @param query the query of the natBallsAndStarsHitStats search 
     * @return the result of the search
     */
    @GetMapping("/_search/nat-balls-and-stars-hit-stats")
    @Timed
    public List<NatBallsAndStarsHitStats> searchNatBallsAndStarsHitStats(@RequestParam String query) {
        log.debug("REST request to search NatBallsAndStarsHitStats for query {}", query);
        return natBallsAndStarsHitStatsService.search(query);
    }


}
