package ru.m2mcom.natmob.web.rest;

import com.codahale.metrics.annotation.Timed;
import ru.m2mcom.natmob.domain.Eml_history;
import ru.m2mcom.natmob.service.Eml_historyService;
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
 * REST controller for managing Eml_history.
 */
@RestController
@RequestMapping("/api")
public class Eml_historyResource {

    private final Logger log = LoggerFactory.getLogger(Eml_historyResource.class);

    private static final String ENTITY_NAME = "eml_history";
        
    private final Eml_historyService eml_historyService;

    public Eml_historyResource(Eml_historyService eml_historyService) {
        this.eml_historyService = eml_historyService;
    }

    /**
     * POST  /eml-histories : Create a new eml_history.
     *
     * @param eml_history the eml_history to create
     * @return the ResponseEntity with status 201 (Created) and with body the new eml_history, or with status 400 (Bad Request) if the eml_history has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/eml-histories")
    @Timed
    public ResponseEntity<Eml_history> createEml_history(@Valid @RequestBody Eml_history eml_history) throws URISyntaxException {
        log.debug("REST request to save Eml_history : {}", eml_history);
        if (eml_history.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new eml_history cannot already have an ID")).body(null);
        }
        Eml_history result = eml_historyService.save(eml_history);
        return ResponseEntity.created(new URI("/api/eml-histories/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /eml-histories : Updates an existing eml_history.
     *
     * @param eml_history the eml_history to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated eml_history,
     * or with status 400 (Bad Request) if the eml_history is not valid,
     * or with status 500 (Internal Server Error) if the eml_history couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/eml-histories")
    @Timed
    public ResponseEntity<Eml_history> updateEml_history(@Valid @RequestBody Eml_history eml_history) throws URISyntaxException {
        log.debug("REST request to update Eml_history : {}", eml_history);
        if (eml_history.getId() == null) {
            return createEml_history(eml_history);
        }
        Eml_history result = eml_historyService.save(eml_history);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, eml_history.getId().toString()))
            .body(result);
    }

    /**
     * GET  /eml-histories : get all the eml_histories.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of eml_histories in body
     */
    @GetMapping("/eml-histories")
    @Timed
    public List<Eml_history> getAllEml_histories() {
        log.debug("REST request to get all Eml_histories");
        return eml_historyService.findAll();
    }

    /**
     * GET  /eml-histories/:id : get the "id" eml_history.
     *
     * @param id the id of the eml_history to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the eml_history, or with status 404 (Not Found)
     */
    @GetMapping("/eml-histories/{id}")
    @Timed
    public ResponseEntity<Eml_history> getEml_history(@PathVariable Long id) {
        log.debug("REST request to get Eml_history : {}", id);
        Eml_history eml_history = eml_historyService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(eml_history));
    }

    /**
     * DELETE  /eml-histories/:id : delete the "id" eml_history.
     *
     * @param id the id of the eml_history to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/eml-histories/{id}")
    @Timed
    public ResponseEntity<Void> deleteEml_history(@PathVariable Long id) {
        log.debug("REST request to delete Eml_history : {}", id);
        eml_historyService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/eml-histories?query=:query : search for the eml_history corresponding
     * to the query.
     *
     * @param query the query of the eml_history search 
     * @return the result of the search
     */
    @GetMapping("/_search/eml-histories")
    @Timed
    public List<Eml_history> searchEml_histories(@RequestParam String query) {
        log.debug("REST request to search Eml_histories for query {}", query);
        return eml_historyService.search(query);
    }


}
