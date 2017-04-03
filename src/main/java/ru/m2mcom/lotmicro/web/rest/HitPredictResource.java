package ru.m2mcom.lotmicro.web.rest;

import com.codahale.metrics.annotation.Timed;
import ru.m2mcom.lotmicro.service.HitPredictService;
import ru.m2mcom.lotmicro.web.rest.util.HeaderUtil;
import ru.m2mcom.lotmicro.web.rest.util.PaginationUtil;
import ru.m2mcom.lotmicro.service.dto.HitPredictDTO;
import io.swagger.annotations.ApiParam;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
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
 * REST controller for managing HitPredict.
 */
@RestController
@RequestMapping("/api")
public class HitPredictResource {

    private final Logger log = LoggerFactory.getLogger(HitPredictResource.class);

    private static final String ENTITY_NAME = "hitPredict";
        
    private final HitPredictService hitPredictService;

    public HitPredictResource(HitPredictService hitPredictService) {
        this.hitPredictService = hitPredictService;
    }

    /**
     * POST  /hit-predicts : Create a new hitPredict.
     *
     * @param hitPredictDTO the hitPredictDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new hitPredictDTO, or with status 400 (Bad Request) if the hitPredict has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/hit-predicts")
    @Timed
    public ResponseEntity<HitPredictDTO> createHitPredict(@RequestBody HitPredictDTO hitPredictDTO) throws URISyntaxException {
        log.debug("REST request to save HitPredict : {}", hitPredictDTO);
        if (hitPredictDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new hitPredict cannot already have an ID")).body(null);
        }
        HitPredictDTO result = hitPredictService.save(hitPredictDTO);
        return ResponseEntity.created(new URI("/api/hit-predicts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /hit-predicts : Updates an existing hitPredict.
     *
     * @param hitPredictDTO the hitPredictDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated hitPredictDTO,
     * or with status 400 (Bad Request) if the hitPredictDTO is not valid,
     * or with status 500 (Internal Server Error) if the hitPredictDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/hit-predicts")
    @Timed
    public ResponseEntity<HitPredictDTO> updateHitPredict(@RequestBody HitPredictDTO hitPredictDTO) throws URISyntaxException {
        log.debug("REST request to update HitPredict : {}", hitPredictDTO);
        if (hitPredictDTO.getId() == null) {
            return createHitPredict(hitPredictDTO);
        }
        HitPredictDTO result = hitPredictService.save(hitPredictDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, hitPredictDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /hit-predicts : get all the hitPredicts.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of hitPredicts in body
     */
    @GetMapping("/hit-predicts")
    @Timed
    public ResponseEntity<List<HitPredictDTO>> getAllHitPredicts(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of HitPredicts");
        Page<HitPredictDTO> page = hitPredictService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/hit-predicts");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /hit-predicts/:id : get the "id" hitPredict.
     *
     * @param id the id of the hitPredictDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the hitPredictDTO, or with status 404 (Not Found)
     */
    @GetMapping("/hit-predicts/{id}")
    @Timed
    public ResponseEntity<HitPredictDTO> getHitPredict(@PathVariable Long id) {
        log.debug("REST request to get HitPredict : {}", id);
        HitPredictDTO hitPredictDTO = hitPredictService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(hitPredictDTO));
    }

    /**
     * DELETE  /hit-predicts/:id : delete the "id" hitPredict.
     *
     * @param id the id of the hitPredictDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/hit-predicts/{id}")
    @Timed
    public ResponseEntity<Void> deleteHitPredict(@PathVariable Long id) {
        log.debug("REST request to delete HitPredict : {}", id);
        hitPredictService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/hit-predicts?query=:query : search for the hitPredict corresponding
     * to the query.
     *
     * @param query the query of the hitPredict search 
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/hit-predicts")
    @Timed
    public ResponseEntity<List<HitPredictDTO>> searchHitPredicts(@RequestParam String query, @ApiParam Pageable pageable) {
        log.debug("REST request to search for a page of HitPredicts for query {}", query);
        Page<HitPredictDTO> page = hitPredictService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/hit-predicts");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }


}
