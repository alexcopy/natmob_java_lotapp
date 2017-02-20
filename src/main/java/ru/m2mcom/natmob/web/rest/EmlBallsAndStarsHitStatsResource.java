package ru.m2mcom.natmob.web.rest;

import com.codahale.metrics.annotation.Timed;
import ru.m2mcom.natmob.domain.EmlBallsAndStarsHitStats;
import ru.m2mcom.natmob.service.EmlBallsAndStarsHitStatsService;
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
 * REST controller for managing EmlBallsAndStarsHitStats.
 */
@RestController
@RequestMapping("/api")
public class EmlBallsAndStarsHitStatsResource {

    private final Logger log = LoggerFactory.getLogger(EmlBallsAndStarsHitStatsResource.class);

    private static final String ENTITY_NAME = "emlBallsAndStarsHitStats";
        
    private final EmlBallsAndStarsHitStatsService emlBallsAndStarsHitStatsService;

    public EmlBallsAndStarsHitStatsResource(EmlBallsAndStarsHitStatsService emlBallsAndStarsHitStatsService) {
        this.emlBallsAndStarsHitStatsService = emlBallsAndStarsHitStatsService;
    }

    /**
     * POST  /eml-balls-and-stars-hit-stats : Create a new emlBallsAndStarsHitStats.
     *
     * @param emlBallsAndStarsHitStats the emlBallsAndStarsHitStats to create
     * @return the ResponseEntity with status 201 (Created) and with body the new emlBallsAndStarsHitStats, or with status 400 (Bad Request) if the emlBallsAndStarsHitStats has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/eml-balls-and-stars-hit-stats")
    @Timed
    public ResponseEntity<EmlBallsAndStarsHitStats> createEmlBallsAndStarsHitStats(@Valid @RequestBody EmlBallsAndStarsHitStats emlBallsAndStarsHitStats) throws URISyntaxException {
        log.debug("REST request to save EmlBallsAndStarsHitStats : {}", emlBallsAndStarsHitStats);
        if (emlBallsAndStarsHitStats.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new emlBallsAndStarsHitStats cannot already have an ID")).body(null);
        }
        EmlBallsAndStarsHitStats result = emlBallsAndStarsHitStatsService.save(emlBallsAndStarsHitStats);
        return ResponseEntity.created(new URI("/api/eml-balls-and-stars-hit-stats/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /eml-balls-and-stars-hit-stats : Updates an existing emlBallsAndStarsHitStats.
     *
     * @param emlBallsAndStarsHitStats the emlBallsAndStarsHitStats to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated emlBallsAndStarsHitStats,
     * or with status 400 (Bad Request) if the emlBallsAndStarsHitStats is not valid,
     * or with status 500 (Internal Server Error) if the emlBallsAndStarsHitStats couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/eml-balls-and-stars-hit-stats")
    @Timed
    public ResponseEntity<EmlBallsAndStarsHitStats> updateEmlBallsAndStarsHitStats(@Valid @RequestBody EmlBallsAndStarsHitStats emlBallsAndStarsHitStats) throws URISyntaxException {
        log.debug("REST request to update EmlBallsAndStarsHitStats : {}", emlBallsAndStarsHitStats);
        if (emlBallsAndStarsHitStats.getId() == null) {
            return createEmlBallsAndStarsHitStats(emlBallsAndStarsHitStats);
        }
        EmlBallsAndStarsHitStats result = emlBallsAndStarsHitStatsService.save(emlBallsAndStarsHitStats);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, emlBallsAndStarsHitStats.getId().toString()))
            .body(result);
    }

    /**
     * GET  /eml-balls-and-stars-hit-stats : get all the emlBallsAndStarsHitStats.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of emlBallsAndStarsHitStats in body
     */
    @GetMapping("/eml-balls-and-stars-hit-stats")
    @Timed
    public List<EmlBallsAndStarsHitStats> getAllEmlBallsAndStarsHitStats() {
        log.debug("REST request to get all EmlBallsAndStarsHitStats");
        return emlBallsAndStarsHitStatsService.findAll();
    }

    /**
     * GET  /eml-balls-and-stars-hit-stats/:id : get the "id" emlBallsAndStarsHitStats.
     *
     * @param id the id of the emlBallsAndStarsHitStats to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the emlBallsAndStarsHitStats, or with status 404 (Not Found)
     */
    @GetMapping("/eml-balls-and-stars-hit-stats/{id}")
    @Timed
    public ResponseEntity<EmlBallsAndStarsHitStats> getEmlBallsAndStarsHitStats(@PathVariable Long id) {
        log.debug("REST request to get EmlBallsAndStarsHitStats : {}", id);
        EmlBallsAndStarsHitStats emlBallsAndStarsHitStats = emlBallsAndStarsHitStatsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(emlBallsAndStarsHitStats));
    }

    /**
     * DELETE  /eml-balls-and-stars-hit-stats/:id : delete the "id" emlBallsAndStarsHitStats.
     *
     * @param id the id of the emlBallsAndStarsHitStats to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/eml-balls-and-stars-hit-stats/{id}")
    @Timed
    public ResponseEntity<Void> deleteEmlBallsAndStarsHitStats(@PathVariable Long id) {
        log.debug("REST request to delete EmlBallsAndStarsHitStats : {}", id);
        emlBallsAndStarsHitStatsService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/eml-balls-and-stars-hit-stats?query=:query : search for the emlBallsAndStarsHitStats corresponding
     * to the query.
     *
     * @param query the query of the emlBallsAndStarsHitStats search 
     * @return the result of the search
     */
    @GetMapping("/_search/eml-balls-and-stars-hit-stats")
    @Timed
    public List<EmlBallsAndStarsHitStats> searchEmlBallsAndStarsHitStats(@RequestParam String query) {
        log.debug("REST request to search EmlBallsAndStarsHitStats for query {}", query);
        return emlBallsAndStarsHitStatsService.search(query);
    }


}
