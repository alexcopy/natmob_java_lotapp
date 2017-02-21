package ru.m2mcom.natmob.web.rest;

import com.codahale.metrics.annotation.Timed;
import ru.m2mcom.natmob.domain.Ranks;
import ru.m2mcom.natmob.service.RanksService;
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
 * REST controller for managing Ranks.
 */
@RestController
@RequestMapping("/api")
public class RanksResource {

    private final Logger log = LoggerFactory.getLogger(RanksResource.class);

    private static final String ENTITY_NAME = "ranks";
        
    private final RanksService ranksService;

    public RanksResource(RanksService ranksService) {
        this.ranksService = ranksService;
    }

    /**
     * POST  /ranks : Create a new ranks.
     *
     * @param ranks the ranks to create
     * @return the ResponseEntity with status 201 (Created) and with body the new ranks, or with status 400 (Bad Request) if the ranks has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/ranks")
    @Timed
    public ResponseEntity<Ranks> createRanks(@Valid @RequestBody Ranks ranks) throws URISyntaxException {
        log.debug("REST request to save Ranks : {}", ranks);
        if (ranks.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new ranks cannot already have an ID")).body(null);
        }
        Ranks result = ranksService.save(ranks);
        return ResponseEntity.created(new URI("/api/ranks/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /ranks : Updates an existing ranks.
     *
     * @param ranks the ranks to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated ranks,
     * or with status 400 (Bad Request) if the ranks is not valid,
     * or with status 500 (Internal Server Error) if the ranks couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/ranks")
    @Timed
    public ResponseEntity<Ranks> updateRanks(@Valid @RequestBody Ranks ranks) throws URISyntaxException {
        log.debug("REST request to update Ranks : {}", ranks);
        if (ranks.getId() == null) {
            return createRanks(ranks);
        }
        Ranks result = ranksService.save(ranks);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, ranks.getId().toString()))
            .body(result);
    }

    /**
     * GET  /ranks : get all the ranks.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of ranks in body
     */
    @GetMapping("/ranks")
    @Timed
    public List<Ranks> getAllRanks() {
        log.debug("REST request to get all Ranks");
        return ranksService.findAll();
    }

    /**
     * GET  /ranks/:id : get the "id" ranks.
     *
     * @param id the id of the ranks to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the ranks, or with status 404 (Not Found)
     */
    @GetMapping("/ranks/{id}")
    @Timed
    public ResponseEntity<Ranks> getRanks(@PathVariable Long id) {
        log.debug("REST request to get Ranks : {}", id);
        Ranks ranks = ranksService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(ranks));
    }

    /**
     * DELETE  /ranks/:id : delete the "id" ranks.
     *
     * @param id the id of the ranks to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/ranks/{id}")
    @Timed
    public ResponseEntity<Void> deleteRanks(@PathVariable Long id) {
        log.debug("REST request to delete Ranks : {}", id);
        ranksService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/ranks?query=:query : search for the ranks corresponding
     * to the query.
     *
     * @param query the query of the ranks search 
     * @return the result of the search
     */
    @GetMapping("/_search/ranks")
    @Timed
    public List<Ranks> searchRanks(@RequestParam String query) {
        log.debug("REST request to search Ranks for query {}", query);
        return ranksService.search(query);
    }


}
