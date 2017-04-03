package ru.m2mcom.lotmicro.web.rest;

import com.codahale.metrics.annotation.Timed;
import ru.m2mcom.lotmicro.service.DblTrplStatsService;
import ru.m2mcom.lotmicro.web.rest.util.HeaderUtil;
import ru.m2mcom.lotmicro.service.dto.DblTrplStatsDTO;
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
 * REST controller for managing DblTrplStats.
 */
@RestController
@RequestMapping("/api")
public class DblTrplStatsResource {

    private final Logger log = LoggerFactory.getLogger(DblTrplStatsResource.class);

    private static final String ENTITY_NAME = "dblTrplStats";
        
    private final DblTrplStatsService dblTrplStatsService;

    public DblTrplStatsResource(DblTrplStatsService dblTrplStatsService) {
        this.dblTrplStatsService = dblTrplStatsService;
    }

    /**
     * POST  /dbl-trpl-stats : Create a new dblTrplStats.
     *
     * @param dblTrplStatsDTO the dblTrplStatsDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new dblTrplStatsDTO, or with status 400 (Bad Request) if the dblTrplStats has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/dbl-trpl-stats")
    @Timed
    public ResponseEntity<DblTrplStatsDTO> createDblTrplStats(@RequestBody DblTrplStatsDTO dblTrplStatsDTO) throws URISyntaxException {
        log.debug("REST request to save DblTrplStats : {}", dblTrplStatsDTO);
        if (dblTrplStatsDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new dblTrplStats cannot already have an ID")).body(null);
        }
        DblTrplStatsDTO result = dblTrplStatsService.save(dblTrplStatsDTO);
        return ResponseEntity.created(new URI("/api/dbl-trpl-stats/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /dbl-trpl-stats : Updates an existing dblTrplStats.
     *
     * @param dblTrplStatsDTO the dblTrplStatsDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated dblTrplStatsDTO,
     * or with status 400 (Bad Request) if the dblTrplStatsDTO is not valid,
     * or with status 500 (Internal Server Error) if the dblTrplStatsDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/dbl-trpl-stats")
    @Timed
    public ResponseEntity<DblTrplStatsDTO> updateDblTrplStats(@RequestBody DblTrplStatsDTO dblTrplStatsDTO) throws URISyntaxException {
        log.debug("REST request to update DblTrplStats : {}", dblTrplStatsDTO);
        if (dblTrplStatsDTO.getId() == null) {
            return createDblTrplStats(dblTrplStatsDTO);
        }
        DblTrplStatsDTO result = dblTrplStatsService.save(dblTrplStatsDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, dblTrplStatsDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /dbl-trpl-stats : get all the dblTrplStats.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of dblTrplStats in body
     */
    @GetMapping("/dbl-trpl-stats")
    @Timed
    public List<DblTrplStatsDTO> getAllDblTrplStats() {
        log.debug("REST request to get all DblTrplStats");
        return dblTrplStatsService.findAll();
    }

    /**
     * GET  /dbl-trpl-stats/:id : get the "id" dblTrplStats.
     *
     * @param id the id of the dblTrplStatsDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the dblTrplStatsDTO, or with status 404 (Not Found)
     */
    @GetMapping("/dbl-trpl-stats/{id}")
    @Timed
    public ResponseEntity<DblTrplStatsDTO> getDblTrplStats(@PathVariable Long id) {
        log.debug("REST request to get DblTrplStats : {}", id);
        DblTrplStatsDTO dblTrplStatsDTO = dblTrplStatsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(dblTrplStatsDTO));
    }

    /**
     * DELETE  /dbl-trpl-stats/:id : delete the "id" dblTrplStats.
     *
     * @param id the id of the dblTrplStatsDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/dbl-trpl-stats/{id}")
    @Timed
    public ResponseEntity<Void> deleteDblTrplStats(@PathVariable Long id) {
        log.debug("REST request to delete DblTrplStats : {}", id);
        dblTrplStatsService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/dbl-trpl-stats?query=:query : search for the dblTrplStats corresponding
     * to the query.
     *
     * @param query the query of the dblTrplStats search 
     * @return the result of the search
     */
    @GetMapping("/_search/dbl-trpl-stats")
    @Timed
    public List<DblTrplStatsDTO> searchDblTrplStats(@RequestParam String query) {
        log.debug("REST request to search DblTrplStats for query {}", query);
        return dblTrplStatsService.search(query);
    }


}
