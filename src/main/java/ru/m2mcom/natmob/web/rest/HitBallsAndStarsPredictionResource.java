package ru.m2mcom.natmob.web.rest;

import com.codahale.metrics.annotation.Timed;
import ru.m2mcom.natmob.domain.HitBallsAndStarsPrediction;
import ru.m2mcom.natmob.service.HitBallsAndStarsPredictionService;
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
 * REST controller for managing HitBallsAndStarsPrediction.
 */
@RestController
@RequestMapping("/api")
public class HitBallsAndStarsPredictionResource {

    private final Logger log = LoggerFactory.getLogger(HitBallsAndStarsPredictionResource.class);

    private static final String ENTITY_NAME = "hitBallsAndStarsPrediction";
        
    private final HitBallsAndStarsPredictionService hitBallsAndStarsPredictionService;

    public HitBallsAndStarsPredictionResource(HitBallsAndStarsPredictionService hitBallsAndStarsPredictionService) {
        this.hitBallsAndStarsPredictionService = hitBallsAndStarsPredictionService;
    }

    /**
     * POST  /hit-balls-and-stars-predictions : Create a new hitBallsAndStarsPrediction.
     *
     * @param hitBallsAndStarsPrediction the hitBallsAndStarsPrediction to create
     * @return the ResponseEntity with status 201 (Created) and with body the new hitBallsAndStarsPrediction, or with status 400 (Bad Request) if the hitBallsAndStarsPrediction has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/hit-balls-and-stars-predictions")
    @Timed
    public ResponseEntity<HitBallsAndStarsPrediction> createHitBallsAndStarsPrediction(@Valid @RequestBody HitBallsAndStarsPrediction hitBallsAndStarsPrediction) throws URISyntaxException {
        log.debug("REST request to save HitBallsAndStarsPrediction : {}", hitBallsAndStarsPrediction);
        if (hitBallsAndStarsPrediction.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new hitBallsAndStarsPrediction cannot already have an ID")).body(null);
        }
        HitBallsAndStarsPrediction result = hitBallsAndStarsPredictionService.save(hitBallsAndStarsPrediction);
        return ResponseEntity.created(new URI("/api/hit-balls-and-stars-predictions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /hit-balls-and-stars-predictions : Updates an existing hitBallsAndStarsPrediction.
     *
     * @param hitBallsAndStarsPrediction the hitBallsAndStarsPrediction to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated hitBallsAndStarsPrediction,
     * or with status 400 (Bad Request) if the hitBallsAndStarsPrediction is not valid,
     * or with status 500 (Internal Server Error) if the hitBallsAndStarsPrediction couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/hit-balls-and-stars-predictions")
    @Timed
    public ResponseEntity<HitBallsAndStarsPrediction> updateHitBallsAndStarsPrediction(@Valid @RequestBody HitBallsAndStarsPrediction hitBallsAndStarsPrediction) throws URISyntaxException {
        log.debug("REST request to update HitBallsAndStarsPrediction : {}", hitBallsAndStarsPrediction);
        if (hitBallsAndStarsPrediction.getId() == null) {
            return createHitBallsAndStarsPrediction(hitBallsAndStarsPrediction);
        }
        HitBallsAndStarsPrediction result = hitBallsAndStarsPredictionService.save(hitBallsAndStarsPrediction);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, hitBallsAndStarsPrediction.getId().toString()))
            .body(result);
    }

    /**
     * GET  /hit-balls-and-stars-predictions : get all the hitBallsAndStarsPredictions.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of hitBallsAndStarsPredictions in body
     */
    @GetMapping("/hit-balls-and-stars-predictions")
    @Timed
    public List<HitBallsAndStarsPrediction> getAllHitBallsAndStarsPredictions() {
        log.debug("REST request to get all HitBallsAndStarsPredictions");
        return hitBallsAndStarsPredictionService.findAll();
    }

    /**
     * GET  /hit-balls-and-stars-predictions/:id : get the "id" hitBallsAndStarsPrediction.
     *
     * @param id the id of the hitBallsAndStarsPrediction to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the hitBallsAndStarsPrediction, or with status 404 (Not Found)
     */
    @GetMapping("/hit-balls-and-stars-predictions/{id}")
    @Timed
    public ResponseEntity<HitBallsAndStarsPrediction> getHitBallsAndStarsPrediction(@PathVariable Long id) {
        log.debug("REST request to get HitBallsAndStarsPrediction : {}", id);
        HitBallsAndStarsPrediction hitBallsAndStarsPrediction = hitBallsAndStarsPredictionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(hitBallsAndStarsPrediction));
    }

    /**
     * DELETE  /hit-balls-and-stars-predictions/:id : delete the "id" hitBallsAndStarsPrediction.
     *
     * @param id the id of the hitBallsAndStarsPrediction to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/hit-balls-and-stars-predictions/{id}")
    @Timed
    public ResponseEntity<Void> deleteHitBallsAndStarsPrediction(@PathVariable Long id) {
        log.debug("REST request to delete HitBallsAndStarsPrediction : {}", id);
        hitBallsAndStarsPredictionService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/hit-balls-and-stars-predictions?query=:query : search for the hitBallsAndStarsPrediction corresponding
     * to the query.
     *
     * @param query the query of the hitBallsAndStarsPrediction search 
     * @return the result of the search
     */
    @GetMapping("/_search/hit-balls-and-stars-predictions")
    @Timed
    public List<HitBallsAndStarsPrediction> searchHitBallsAndStarsPredictions(@RequestParam String query) {
        log.debug("REST request to search HitBallsAndStarsPredictions for query {}", query);
        return hitBallsAndStarsPredictionService.search(query);
    }


}
