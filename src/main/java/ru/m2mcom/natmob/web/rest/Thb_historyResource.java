package ru.m2mcom.natmob.web.rest;

import com.codahale.metrics.annotation.Timed;
import ru.m2mcom.natmob.domain.Thb_history;
import ru.m2mcom.natmob.service.Thb_historyService;
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
 * REST controller for managing Thb_history.
 */
@RestController
@RequestMapping("/api")
public class Thb_historyResource {

    private final Logger log = LoggerFactory.getLogger(Thb_historyResource.class);

    private static final String ENTITY_NAME = "thb_history";
        
    private final Thb_historyService thb_historyService;

    public Thb_historyResource(Thb_historyService thb_historyService) {
        this.thb_historyService = thb_historyService;
    }

    /**
     * POST  /thb-histories : Create a new thb_history.
     *
     * @param thb_history the thb_history to create
     * @return the ResponseEntity with status 201 (Created) and with body the new thb_history, or with status 400 (Bad Request) if the thb_history has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/thb-histories")
    @Timed
    public ResponseEntity<Thb_history> createThb_history(@Valid @RequestBody Thb_history thb_history) throws URISyntaxException {
        log.debug("REST request to save Thb_history : {}", thb_history);
        if (thb_history.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new thb_history cannot already have an ID")).body(null);
        }
        Thb_history result = thb_historyService.save(thb_history);
        return ResponseEntity.created(new URI("/api/thb-histories/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /thb-histories : Updates an existing thb_history.
     *
     * @param thb_history the thb_history to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated thb_history,
     * or with status 400 (Bad Request) if the thb_history is not valid,
     * or with status 500 (Internal Server Error) if the thb_history couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/thb-histories")
    @Timed
    public ResponseEntity<Thb_history> updateThb_history(@Valid @RequestBody Thb_history thb_history) throws URISyntaxException {
        log.debug("REST request to update Thb_history : {}", thb_history);
        if (thb_history.getId() == null) {
            return createThb_history(thb_history);
        }
        Thb_history result = thb_historyService.save(thb_history);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, thb_history.getId().toString()))
            .body(result);
    }

    /**
     * GET  /thb-histories : get all the thb_histories.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of thb_histories in body
     */
    @GetMapping("/thb-histories")
    @Timed
    public List<Thb_history> getAllThb_histories() {
        log.debug("REST request to get all Thb_histories");
        return thb_historyService.findAll();
    }

    /**
     * GET  /thb-histories/:id : get the "id" thb_history.
     *
     * @param id the id of the thb_history to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the thb_history, or with status 404 (Not Found)
     */
    @GetMapping("/thb-histories/{id}")
    @Timed
    public ResponseEntity<Thb_history> getThb_history(@PathVariable Long id) {
        log.debug("REST request to get Thb_history : {}", id);
        Thb_history thb_history = thb_historyService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(thb_history));
    }

    /**
     * DELETE  /thb-histories/:id : delete the "id" thb_history.
     *
     * @param id the id of the thb_history to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/thb-histories/{id}")
    @Timed
    public ResponseEntity<Void> deleteThb_history(@PathVariable Long id) {
        log.debug("REST request to delete Thb_history : {}", id);
        thb_historyService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/thb-histories?query=:query : search for the thb_history corresponding
     * to the query.
     *
     * @param query the query of the thb_history search 
     * @return the result of the search
     */
    @GetMapping("/_search/thb-histories")
    @Timed
    public List<Thb_history> searchThb_histories(@RequestParam String query) {
        log.debug("REST request to search Thb_histories for query {}", query);
        return thb_historyService.search(query);
    }


}
