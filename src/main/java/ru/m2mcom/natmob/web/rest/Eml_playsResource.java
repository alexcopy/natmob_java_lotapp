package ru.m2mcom.natmob.web.rest;

import com.codahale.metrics.annotation.Timed;
import ru.m2mcom.natmob.domain.Eml_plays;
import ru.m2mcom.natmob.service.Eml_playsService;
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
 * REST controller for managing Eml_plays.
 */
@RestController
@RequestMapping("/api")
public class Eml_playsResource {

    private final Logger log = LoggerFactory.getLogger(Eml_playsResource.class);

    private static final String ENTITY_NAME = "eml_plays";
        
    private final Eml_playsService eml_playsService;

    public Eml_playsResource(Eml_playsService eml_playsService) {
        this.eml_playsService = eml_playsService;
    }

    /**
     * POST  /eml-plays : Create a new eml_plays.
     *
     * @param eml_plays the eml_plays to create
     * @return the ResponseEntity with status 201 (Created) and with body the new eml_plays, or with status 400 (Bad Request) if the eml_plays has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/eml-plays")
    @Timed
    public ResponseEntity<Eml_plays> createEml_plays(@Valid @RequestBody Eml_plays eml_plays) throws URISyntaxException {
        log.debug("REST request to save Eml_plays : {}", eml_plays);
        if (eml_plays.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new eml_plays cannot already have an ID")).body(null);
        }
        Eml_plays result = eml_playsService.save(eml_plays);
        return ResponseEntity.created(new URI("/api/eml-plays/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /eml-plays : Updates an existing eml_plays.
     *
     * @param eml_plays the eml_plays to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated eml_plays,
     * or with status 400 (Bad Request) if the eml_plays is not valid,
     * or with status 500 (Internal Server Error) if the eml_plays couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/eml-plays")
    @Timed
    public ResponseEntity<Eml_plays> updateEml_plays(@Valid @RequestBody Eml_plays eml_plays) throws URISyntaxException {
        log.debug("REST request to update Eml_plays : {}", eml_plays);
        if (eml_plays.getId() == null) {
            return createEml_plays(eml_plays);
        }
        Eml_plays result = eml_playsService.save(eml_plays);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, eml_plays.getId().toString()))
            .body(result);
    }

    /**
     * GET  /eml-plays : get all the eml_plays.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of eml_plays in body
     */
    @GetMapping("/eml-plays")
    @Timed
    public List<Eml_plays> getAllEml_plays() {
        log.debug("REST request to get all Eml_plays");
        return eml_playsService.findAll();
    }

    /**
     * GET  /eml-plays/:id : get the "id" eml_plays.
     *
     * @param id the id of the eml_plays to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the eml_plays, or with status 404 (Not Found)
     */
    @GetMapping("/eml-plays/{id}")
    @Timed
    public ResponseEntity<Eml_plays> getEml_plays(@PathVariable Long id) {
        log.debug("REST request to get Eml_plays : {}", id);
        Eml_plays eml_plays = eml_playsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(eml_plays));
    }

    /**
     * DELETE  /eml-plays/:id : delete the "id" eml_plays.
     *
     * @param id the id of the eml_plays to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/eml-plays/{id}")
    @Timed
    public ResponseEntity<Void> deleteEml_plays(@PathVariable Long id) {
        log.debug("REST request to delete Eml_plays : {}", id);
        eml_playsService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/eml-plays?query=:query : search for the eml_plays corresponding
     * to the query.
     *
     * @param query the query of the eml_plays search 
     * @return the result of the search
     */
    @GetMapping("/_search/eml-plays")
    @Timed
    public List<Eml_plays> searchEml_plays(@RequestParam String query) {
        log.debug("REST request to search Eml_plays for query {}", query);
        return eml_playsService.search(query);
    }


}
