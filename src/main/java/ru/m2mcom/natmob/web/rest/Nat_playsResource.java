package ru.m2mcom.natmob.web.rest;

import com.codahale.metrics.annotation.Timed;
import ru.m2mcom.natmob.domain.Nat_plays;
import ru.m2mcom.natmob.service.Nat_playsService;
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
 * REST controller for managing Nat_plays.
 */
@RestController
@RequestMapping("/api")
public class Nat_playsResource {

    private final Logger log = LoggerFactory.getLogger(Nat_playsResource.class);

    private static final String ENTITY_NAME = "nat_plays";
        
    private final Nat_playsService nat_playsService;

    public Nat_playsResource(Nat_playsService nat_playsService) {
        this.nat_playsService = nat_playsService;
    }

    /**
     * POST  /nat-plays : Create a new nat_plays.
     *
     * @param nat_plays the nat_plays to create
     * @return the ResponseEntity with status 201 (Created) and with body the new nat_plays, or with status 400 (Bad Request) if the nat_plays has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/nat-plays")
    @Timed
    public ResponseEntity<Nat_plays> createNat_plays(@Valid @RequestBody Nat_plays nat_plays) throws URISyntaxException {
        log.debug("REST request to save Nat_plays : {}", nat_plays);
        if (nat_plays.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new nat_plays cannot already have an ID")).body(null);
        }
        Nat_plays result = nat_playsService.save(nat_plays);
        return ResponseEntity.created(new URI("/api/nat-plays/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /nat-plays : Updates an existing nat_plays.
     *
     * @param nat_plays the nat_plays to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated nat_plays,
     * or with status 400 (Bad Request) if the nat_plays is not valid,
     * or with status 500 (Internal Server Error) if the nat_plays couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/nat-plays")
    @Timed
    public ResponseEntity<Nat_plays> updateNat_plays(@Valid @RequestBody Nat_plays nat_plays) throws URISyntaxException {
        log.debug("REST request to update Nat_plays : {}", nat_plays);
        if (nat_plays.getId() == null) {
            return createNat_plays(nat_plays);
        }
        Nat_plays result = nat_playsService.save(nat_plays);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, nat_plays.getId().toString()))
            .body(result);
    }

    /**
     * GET  /nat-plays : get all the nat_plays.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of nat_plays in body
     */
    @GetMapping("/nat-plays")
    @Timed
    public List<Nat_plays> getAllNat_plays() {
        log.debug("REST request to get all Nat_plays");
        return nat_playsService.findAll();
    }

    /**
     * GET  /nat-plays/:id : get the "id" nat_plays.
     *
     * @param id the id of the nat_plays to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the nat_plays, or with status 404 (Not Found)
     */
    @GetMapping("/nat-plays/{id}")
    @Timed
    public ResponseEntity<Nat_plays> getNat_plays(@PathVariable Long id) {
        log.debug("REST request to get Nat_plays : {}", id);
        Nat_plays nat_plays = nat_playsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(nat_plays));
    }

    /**
     * DELETE  /nat-plays/:id : delete the "id" nat_plays.
     *
     * @param id the id of the nat_plays to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/nat-plays/{id}")
    @Timed
    public ResponseEntity<Void> deleteNat_plays(@PathVariable Long id) {
        log.debug("REST request to delete Nat_plays : {}", id);
        nat_playsService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/nat-plays?query=:query : search for the nat_plays corresponding
     * to the query.
     *
     * @param query the query of the nat_plays search 
     * @return the result of the search
     */
    @GetMapping("/_search/nat-plays")
    @Timed
    public List<Nat_plays> searchNat_plays(@RequestParam String query) {
        log.debug("REST request to search Nat_plays for query {}", query);
        return nat_playsService.search(query);
    }


}
