package ru.m2mcom.lotmicro.web.rest;

import com.codahale.metrics.annotation.Timed;
import ru.m2mcom.lotmicro.service.RankService;
import ru.m2mcom.lotmicro.web.rest.util.HeaderUtil;
import ru.m2mcom.lotmicro.service.dto.RankDTO;
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
 * REST controller for managing Rank.
 */
@RestController
@RequestMapping("/api")
public class RankResource {

    private final Logger log = LoggerFactory.getLogger(RankResource.class);

    private static final String ENTITY_NAME = "rank";
        
    private final RankService rankService;

    public RankResource(RankService rankService) {
        this.rankService = rankService;
    }

    /**
     * POST  /ranks : Create a new rank.
     *
     * @param rankDTO the rankDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new rankDTO, or with status 400 (Bad Request) if the rank has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/ranks")
    @Timed
    public ResponseEntity<RankDTO> createRank(@RequestBody RankDTO rankDTO) throws URISyntaxException {
        log.debug("REST request to save Rank : {}", rankDTO);
        if (rankDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new rank cannot already have an ID")).body(null);
        }
        RankDTO result = rankService.save(rankDTO);
        return ResponseEntity.created(new URI("/api/ranks/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /ranks : Updates an existing rank.
     *
     * @param rankDTO the rankDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated rankDTO,
     * or with status 400 (Bad Request) if the rankDTO is not valid,
     * or with status 500 (Internal Server Error) if the rankDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/ranks")
    @Timed
    public ResponseEntity<RankDTO> updateRank(@RequestBody RankDTO rankDTO) throws URISyntaxException {
        log.debug("REST request to update Rank : {}", rankDTO);
        if (rankDTO.getId() == null) {
            return createRank(rankDTO);
        }
        RankDTO result = rankService.save(rankDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, rankDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /ranks : get all the ranks.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of ranks in body
     */
    @GetMapping("/ranks")
    @Timed
    public List<RankDTO> getAllRanks() {
        log.debug("REST request to get all Ranks");
        return rankService.findAll();
    }

    /**
     * GET  /ranks/:id : get the "id" rank.
     *
     * @param id the id of the rankDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the rankDTO, or with status 404 (Not Found)
     */
    @GetMapping("/ranks/{id}")
    @Timed
    public ResponseEntity<RankDTO> getRank(@PathVariable Long id) {
        log.debug("REST request to get Rank : {}", id);
        RankDTO rankDTO = rankService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(rankDTO));
    }

    /**
     * DELETE  /ranks/:id : delete the "id" rank.
     *
     * @param id the id of the rankDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/ranks/{id}")
    @Timed
    public ResponseEntity<Void> deleteRank(@PathVariable Long id) {
        log.debug("REST request to delete Rank : {}", id);
        rankService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/ranks?query=:query : search for the rank corresponding
     * to the query.
     *
     * @param query the query of the rank search 
     * @return the result of the search
     */
    @GetMapping("/_search/ranks")
    @Timed
    public List<RankDTO> searchRanks(@RequestParam String query) {
        log.debug("REST request to search Ranks for query {}", query);
        return rankService.search(query);
    }


}
