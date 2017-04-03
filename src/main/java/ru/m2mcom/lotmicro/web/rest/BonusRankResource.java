package ru.m2mcom.lotmicro.web.rest;

import com.codahale.metrics.annotation.Timed;
import ru.m2mcom.lotmicro.service.BonusRankService;
import ru.m2mcom.lotmicro.web.rest.util.HeaderUtil;
import ru.m2mcom.lotmicro.service.dto.BonusRankDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing BonusRank.
 */
@RestController
@RequestMapping("/api")
public class BonusRankResource {

    private final Logger log = LoggerFactory.getLogger(BonusRankResource.class);

    private static final String ENTITY_NAME = "bonusRank";
        
    private final BonusRankService bonusRankService;

    public BonusRankResource(BonusRankService bonusRankService) {
        this.bonusRankService = bonusRankService;
    }

    /**
     * POST  /bonus-ranks : Create a new bonusRank.
     *
     * @param bonusRankDTO the bonusRankDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new bonusRankDTO, or with status 400 (Bad Request) if the bonusRank has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/bonus-ranks")
    @Timed
    public ResponseEntity<BonusRankDTO> createBonusRank(@RequestBody BonusRankDTO bonusRankDTO) throws URISyntaxException {
        log.debug("REST request to save BonusRank : {}", bonusRankDTO);
        if (bonusRankDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new bonusRank cannot already have an ID")).body(null);
        }
        BonusRankDTO result = bonusRankService.save(bonusRankDTO);
        return ResponseEntity.created(new URI("/api/bonus-ranks/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /bonus-ranks : Updates an existing bonusRank.
     *
     * @param bonusRankDTO the bonusRankDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated bonusRankDTO,
     * or with status 400 (Bad Request) if the bonusRankDTO is not valid,
     * or with status 500 (Internal Server Error) if the bonusRankDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/bonus-ranks")
    @Timed
    public ResponseEntity<BonusRankDTO> updateBonusRank(@RequestBody BonusRankDTO bonusRankDTO) throws URISyntaxException {
        log.debug("REST request to update BonusRank : {}", bonusRankDTO);
        if (bonusRankDTO.getId() == null) {
            return createBonusRank(bonusRankDTO);
        }
        BonusRankDTO result = bonusRankService.save(bonusRankDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, bonusRankDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /bonus-ranks : get all the bonusRanks.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of bonusRanks in body
     */
    @GetMapping("/bonus-ranks")
    @Timed
    public List<BonusRankDTO> getAllBonusRanks() {
        log.debug("REST request to get all BonusRanks");
        return bonusRankService.findAll();
    }

    /**
     * GET  /bonus-ranks/:id : get the "id" bonusRank.
     *
     * @param id the id of the bonusRankDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the bonusRankDTO, or with status 404 (Not Found)
     */
    @GetMapping("/bonus-ranks/{id}")
    @Timed
    public ResponseEntity<BonusRankDTO> getBonusRank(@PathVariable Long id) {
        log.debug("REST request to get BonusRank : {}", id);
        BonusRankDTO bonusRankDTO = bonusRankService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(bonusRankDTO));
    }

    /**
     * DELETE  /bonus-ranks/:id : delete the "id" bonusRank.
     *
     * @param id the id of the bonusRankDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/bonus-ranks/{id}")
    @Timed
    public ResponseEntity<Void> deleteBonusRank(@PathVariable Long id) {
        log.debug("REST request to delete BonusRank : {}", id);
        bonusRankService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/bonus-ranks?query=:query : search for the bonusRank corresponding
     * to the query.
     *
     * @param query the query of the bonusRank search 
     * @return the result of the search
     */
    @GetMapping("/_search/bonus-ranks")
    @Timed
    public List<BonusRankDTO> searchBonusRanks(@RequestParam String query) {
        log.debug("REST request to search BonusRanks for query {}", query);
        return bonusRankService.search(query);
    }


}
