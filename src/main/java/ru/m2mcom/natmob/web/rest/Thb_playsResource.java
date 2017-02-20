package ru.m2mcom.natmob.web.rest;

import com.codahale.metrics.annotation.Timed;
import ru.m2mcom.natmob.domain.Thb_plays;
import ru.m2mcom.natmob.service.Thb_playsService;
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
 * REST controller for managing Thb_plays.
 */
@RestController
@RequestMapping("/api")
public class Thb_playsResource {

    private final Logger log = LoggerFactory.getLogger(Thb_playsResource.class);

    private static final String ENTITY_NAME = "thb_plays";
        
    private final Thb_playsService thb_playsService;

    public Thb_playsResource(Thb_playsService thb_playsService) {
        this.thb_playsService = thb_playsService;
    }

    /**
     * POST  /thb-plays : Create a new thb_plays.
     *
     * @param thb_plays the thb_plays to create
     * @return the ResponseEntity with status 201 (Created) and with body the new thb_plays, or with status 400 (Bad Request) if the thb_plays has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/thb-plays")
    @Timed
    public ResponseEntity<Thb_plays> createThb_plays(@Valid @RequestBody Thb_plays thb_plays) throws URISyntaxException {
        log.debug("REST request to save Thb_plays : {}", thb_plays);
        if (thb_plays.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new thb_plays cannot already have an ID")).body(null);
        }
        Thb_plays result = thb_playsService.save(thb_plays);
        return ResponseEntity.created(new URI("/api/thb-plays/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /thb-plays : Updates an existing thb_plays.
     *
     * @param thb_plays the thb_plays to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated thb_plays,
     * or with status 400 (Bad Request) if the thb_plays is not valid,
     * or with status 500 (Internal Server Error) if the thb_plays couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/thb-plays")
    @Timed
    public ResponseEntity<Thb_plays> updateThb_plays(@Valid @RequestBody Thb_plays thb_plays) throws URISyntaxException {
        log.debug("REST request to update Thb_plays : {}", thb_plays);
        if (thb_plays.getId() == null) {
            return createThb_plays(thb_plays);
        }
        Thb_plays result = thb_playsService.save(thb_plays);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, thb_plays.getId().toString()))
            .body(result);
    }

    /**
     * GET  /thb-plays : get all the thb_plays.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of thb_plays in body
     */
    @GetMapping("/thb-plays")
    @Timed
    public List<Thb_plays> getAllThb_plays() {
        log.debug("REST request to get all Thb_plays");
        return thb_playsService.findAll();
    }

    /**
     * GET  /thb-plays/:id : get the "id" thb_plays.
     *
     * @param id the id of the thb_plays to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the thb_plays, or with status 404 (Not Found)
     */
    @GetMapping("/thb-plays/{id}")
    @Timed
    public ResponseEntity<Thb_plays> getThb_plays(@PathVariable Long id) {
        log.debug("REST request to get Thb_plays : {}", id);
        Thb_plays thb_plays = thb_playsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(thb_plays));
    }

    /**
     * DELETE  /thb-plays/:id : delete the "id" thb_plays.
     *
     * @param id the id of the thb_plays to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/thb-plays/{id}")
    @Timed
    public ResponseEntity<Void> deleteThb_plays(@PathVariable Long id) {
        log.debug("REST request to delete Thb_plays : {}", id);
        thb_playsService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/thb-plays?query=:query : search for the thb_plays corresponding
     * to the query.
     *
     * @param query the query of the thb_plays search 
     * @return the result of the search
     */
    @GetMapping("/_search/thb-plays")
    @Timed
    public List<Thb_plays> searchThb_plays(@RequestParam String query) {
        log.debug("REST request to search Thb_plays for query {}", query);
        return thb_playsService.search(query);
    }


}
