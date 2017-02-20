package ru.m2mcom.natmob.web.rest;

import com.codahale.metrics.annotation.Timed;
import ru.m2mcom.natmob.domain.Hot_plays;
import ru.m2mcom.natmob.service.Hot_playsService;
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
 * REST controller for managing Hot_plays.
 */
@RestController
@RequestMapping("/api")
public class Hot_playsResource {

    private final Logger log = LoggerFactory.getLogger(Hot_playsResource.class);

    private static final String ENTITY_NAME = "hot_plays";
        
    private final Hot_playsService hot_playsService;

    public Hot_playsResource(Hot_playsService hot_playsService) {
        this.hot_playsService = hot_playsService;
    }

    /**
     * POST  /hot-plays : Create a new hot_plays.
     *
     * @param hot_plays the hot_plays to create
     * @return the ResponseEntity with status 201 (Created) and with body the new hot_plays, or with status 400 (Bad Request) if the hot_plays has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/hot-plays")
    @Timed
    public ResponseEntity<Hot_plays> createHot_plays(@Valid @RequestBody Hot_plays hot_plays) throws URISyntaxException {
        log.debug("REST request to save Hot_plays : {}", hot_plays);
        if (hot_plays.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new hot_plays cannot already have an ID")).body(null);
        }
        Hot_plays result = hot_playsService.save(hot_plays);
        return ResponseEntity.created(new URI("/api/hot-plays/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /hot-plays : Updates an existing hot_plays.
     *
     * @param hot_plays the hot_plays to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated hot_plays,
     * or with status 400 (Bad Request) if the hot_plays is not valid,
     * or with status 500 (Internal Server Error) if the hot_plays couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/hot-plays")
    @Timed
    public ResponseEntity<Hot_plays> updateHot_plays(@Valid @RequestBody Hot_plays hot_plays) throws URISyntaxException {
        log.debug("REST request to update Hot_plays : {}", hot_plays);
        if (hot_plays.getId() == null) {
            return createHot_plays(hot_plays);
        }
        Hot_plays result = hot_playsService.save(hot_plays);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, hot_plays.getId().toString()))
            .body(result);
    }

    /**
     * GET  /hot-plays : get all the hot_plays.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of hot_plays in body
     */
    @GetMapping("/hot-plays")
    @Timed
    public List<Hot_plays> getAllHot_plays() {
        log.debug("REST request to get all Hot_plays");
        return hot_playsService.findAll();
    }

    /**
     * GET  /hot-plays/:id : get the "id" hot_plays.
     *
     * @param id the id of the hot_plays to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the hot_plays, or with status 404 (Not Found)
     */
    @GetMapping("/hot-plays/{id}")
    @Timed
    public ResponseEntity<Hot_plays> getHot_plays(@PathVariable Long id) {
        log.debug("REST request to get Hot_plays : {}", id);
        Hot_plays hot_plays = hot_playsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(hot_plays));
    }

    /**
     * DELETE  /hot-plays/:id : delete the "id" hot_plays.
     *
     * @param id the id of the hot_plays to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/hot-plays/{id}")
    @Timed
    public ResponseEntity<Void> deleteHot_plays(@PathVariable Long id) {
        log.debug("REST request to delete Hot_plays : {}", id);
        hot_playsService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/hot-plays?query=:query : search for the hot_plays corresponding
     * to the query.
     *
     * @param query the query of the hot_plays search 
     * @return the result of the search
     */
    @GetMapping("/_search/hot-plays")
    @Timed
    public List<Hot_plays> searchHot_plays(@RequestParam String query) {
        log.debug("REST request to search Hot_plays for query {}", query);
        return hot_playsService.search(query);
    }


}
