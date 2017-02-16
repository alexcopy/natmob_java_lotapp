package ru.m2mcom.natmob.web.rest;

import com.codahale.metrics.annotation.Timed;
import ru.m2mcom.natmob.domain.Nat_history;
import ru.m2mcom.natmob.service.Nat_historyService;
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
 * REST controller for managing Nat_history.
 */
@RestController
@RequestMapping("/api")
public class Nat_historyResource {

    private final Logger log = LoggerFactory.getLogger(Nat_historyResource.class);

    private static final String ENTITY_NAME = "nat_history";
        
    private final Nat_historyService nat_historyService;

    public Nat_historyResource(Nat_historyService nat_historyService) {
        this.nat_historyService = nat_historyService;
    }

    /**
     * POST  /nat-histories : Create a new nat_history.
     *
     * @param nat_history the nat_history to create
     * @return the ResponseEntity with status 201 (Created) and with body the new nat_history, or with status 400 (Bad Request) if the nat_history has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/nat-histories")
    @Timed
    public ResponseEntity<Nat_history> createNat_history(@Valid @RequestBody Nat_history nat_history) throws URISyntaxException {
        log.debug("REST request to save Nat_history : {}", nat_history);
        if (nat_history.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new nat_history cannot already have an ID")).body(null);
        }
        Nat_history result = nat_historyService.save(nat_history);
        return ResponseEntity.created(new URI("/api/nat-histories/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /nat-histories : Updates an existing nat_history.
     *
     * @param nat_history the nat_history to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated nat_history,
     * or with status 400 (Bad Request) if the nat_history is not valid,
     * or with status 500 (Internal Server Error) if the nat_history couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/nat-histories")
    @Timed
    public ResponseEntity<Nat_history> updateNat_history(@Valid @RequestBody Nat_history nat_history) throws URISyntaxException {
        log.debug("REST request to update Nat_history : {}", nat_history);
        if (nat_history.getId() == null) {
            return createNat_history(nat_history);
        }
        Nat_history result = nat_historyService.save(nat_history);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, nat_history.getId().toString()))
            .body(result);
    }

    /**
     * GET  /nat-histories : get all the nat_histories.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of nat_histories in body
     */
    @GetMapping("/nat-histories")
    @Timed
    public List<Nat_history> getAllNat_histories() {
        log.debug("REST request to get all Nat_histories");
        return nat_historyService.findAll();
    }

    /**
     * GET  /nat-histories/:id : get the "id" nat_history.
     *
     * @param id the id of the nat_history to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the nat_history, or with status 404 (Not Found)
     */
    @GetMapping("/nat-histories/{id}")
    @Timed
    public ResponseEntity<Nat_history> getNat_history(@PathVariable Long id) {
        log.debug("REST request to get Nat_history : {}", id);
        Nat_history nat_history = nat_historyService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(nat_history));
    }

    /**
     * DELETE  /nat-histories/:id : delete the "id" nat_history.
     *
     * @param id the id of the nat_history to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/nat-histories/{id}")
    @Timed
    public ResponseEntity<Void> deleteNat_history(@PathVariable Long id) {
        log.debug("REST request to delete Nat_history : {}", id);
        nat_historyService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/nat-histories?query=:query : search for the nat_history corresponding
     * to the query.
     *
     * @param query the query of the nat_history search 
     * @return the result of the search
     */
    @GetMapping("/_search/nat-histories")
    @Timed
    public List<Nat_history> searchNat_histories(@RequestParam String query) {
        log.debug("REST request to search Nat_histories for query {}", query);
        return nat_historyService.search(query);
    }


}
