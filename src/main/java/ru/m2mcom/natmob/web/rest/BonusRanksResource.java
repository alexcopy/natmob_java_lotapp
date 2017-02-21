package ru.m2mcom.natmob.web.rest;

import com.codahale.metrics.annotation.Timed;
import ru.m2mcom.natmob.domain.BonusRanks;
import ru.m2mcom.natmob.service.BonusRanksService;
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
 * REST controller for managing BonusRanks.
 */
@RestController
@RequestMapping("/api")
public class BonusRanksResource {

    private final Logger log = LoggerFactory.getLogger(BonusRanksResource.class);

    private static final String ENTITY_NAME = "bonusRanks";
        
    private final BonusRanksService bonusRanksService;

    public BonusRanksResource(BonusRanksService bonusRanksService) {
        this.bonusRanksService = bonusRanksService;
    }

    /**
     * POST  /bonus-ranks : Create a new bonusRanks.
     *
     * @param bonusRanks the bonusRanks to create
     * @return the ResponseEntity with status 201 (Created) and with body the new bonusRanks, or with status 400 (Bad Request) if the bonusRanks has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/bonus-ranks")
    @Timed
    public ResponseEntity<BonusRanks> createBonusRanks(@Valid @RequestBody BonusRanks bonusRanks) throws URISyntaxException {
        log.debug("REST request to save BonusRanks : {}", bonusRanks);
        if (bonusRanks.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new bonusRanks cannot already have an ID")).body(null);
        }
        BonusRanks result = bonusRanksService.save(bonusRanks);
        return ResponseEntity.created(new URI("/api/bonus-ranks/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /bonus-ranks : Updates an existing bonusRanks.
     *
     * @param bonusRanks the bonusRanks to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated bonusRanks,
     * or with status 400 (Bad Request) if the bonusRanks is not valid,
     * or with status 500 (Internal Server Error) if the bonusRanks couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/bonus-ranks")
    @Timed
    public ResponseEntity<BonusRanks> updateBonusRanks(@Valid @RequestBody BonusRanks bonusRanks) throws URISyntaxException {
        log.debug("REST request to update BonusRanks : {}", bonusRanks);
        if (bonusRanks.getId() == null) {
            return createBonusRanks(bonusRanks);
        }
        BonusRanks result = bonusRanksService.save(bonusRanks);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, bonusRanks.getId().toString()))
            .body(result);
    }

    /**
     * GET  /bonus-ranks : get all the bonusRanks.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of bonusRanks in body
     */
    @GetMapping("/bonus-ranks")
    @Timed
    public List<BonusRanks> getAllBonusRanks() {
        log.debug("REST request to get all BonusRanks");
        return bonusRanksService.findAll();
    }

    /**
     * GET  /bonus-ranks/:id : get the "id" bonusRanks.
     *
     * @param id the id of the bonusRanks to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the bonusRanks, or with status 404 (Not Found)
     */
    @GetMapping("/bonus-ranks/{id}")
    @Timed
    public ResponseEntity<BonusRanks> getBonusRanks(@PathVariable Long id) {
        log.debug("REST request to get BonusRanks : {}", id);
        BonusRanks bonusRanks = bonusRanksService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(bonusRanks));
    }

    /**
     * DELETE  /bonus-ranks/:id : delete the "id" bonusRanks.
     *
     * @param id the id of the bonusRanks to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/bonus-ranks/{id}")
    @Timed
    public ResponseEntity<Void> deleteBonusRanks(@PathVariable Long id) {
        log.debug("REST request to delete BonusRanks : {}", id);
        bonusRanksService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/bonus-ranks?query=:query : search for the bonusRanks corresponding
     * to the query.
     *
     * @param query the query of the bonusRanks search 
     * @return the result of the search
     */
    @GetMapping("/_search/bonus-ranks")
    @Timed
    public List<BonusRanks> searchBonusRanks(@RequestParam String query) {
        log.debug("REST request to search BonusRanks for query {}", query);
        return bonusRanksService.search(query);
    }


}
