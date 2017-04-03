package ru.m2mcom.lotmicro.web.rest;

import com.codahale.metrics.annotation.Timed;
import ru.m2mcom.lotmicro.service.LocalPlayService;
import ru.m2mcom.lotmicro.web.rest.util.HeaderUtil;
import ru.m2mcom.lotmicro.web.rest.util.PaginationUtil;
import ru.m2mcom.lotmicro.service.dto.LocalPlayDTO;
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

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing LocalPlay.
 */
@RestController
@RequestMapping("/api")
public class LocalPlayResource {

    private final Logger log = LoggerFactory.getLogger(LocalPlayResource.class);

    private static final String ENTITY_NAME = "localPlay";
        
    private final LocalPlayService localPlayService;

    public LocalPlayResource(LocalPlayService localPlayService) {
        this.localPlayService = localPlayService;
    }

    /**
     * POST  /local-plays : Create a new localPlay.
     *
     * @param localPlayDTO the localPlayDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new localPlayDTO, or with status 400 (Bad Request) if the localPlay has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/local-plays")
    @Timed
    public ResponseEntity<LocalPlayDTO> createLocalPlay(@Valid @RequestBody LocalPlayDTO localPlayDTO) throws URISyntaxException {
        log.debug("REST request to save LocalPlay : {}", localPlayDTO);
        if (localPlayDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new localPlay cannot already have an ID")).body(null);
        }
        LocalPlayDTO result = localPlayService.save(localPlayDTO);
        return ResponseEntity.created(new URI("/api/local-plays/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /local-plays : Updates an existing localPlay.
     *
     * @param localPlayDTO the localPlayDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated localPlayDTO,
     * or with status 400 (Bad Request) if the localPlayDTO is not valid,
     * or with status 500 (Internal Server Error) if the localPlayDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/local-plays")
    @Timed
    public ResponseEntity<LocalPlayDTO> updateLocalPlay(@Valid @RequestBody LocalPlayDTO localPlayDTO) throws URISyntaxException {
        log.debug("REST request to update LocalPlay : {}", localPlayDTO);
        if (localPlayDTO.getId() == null) {
            return createLocalPlay(localPlayDTO);
        }
        LocalPlayDTO result = localPlayService.save(localPlayDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, localPlayDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /local-plays : get all the localPlays.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of localPlays in body
     */
    @GetMapping("/local-plays")
    @Timed
    public ResponseEntity<List<LocalPlayDTO>> getAllLocalPlays(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of LocalPlays");
        Page<LocalPlayDTO> page = localPlayService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/local-plays");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /local-plays/:id : get the "id" localPlay.
     *
     * @param id the id of the localPlayDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the localPlayDTO, or with status 404 (Not Found)
     */
    @GetMapping("/local-plays/{id}")
    @Timed
    public ResponseEntity<LocalPlayDTO> getLocalPlay(@PathVariable Long id) {
        log.debug("REST request to get LocalPlay : {}", id);
        LocalPlayDTO localPlayDTO = localPlayService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(localPlayDTO));
    }

    /**
     * DELETE  /local-plays/:id : delete the "id" localPlay.
     *
     * @param id the id of the localPlayDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/local-plays/{id}")
    @Timed
    public ResponseEntity<Void> deleteLocalPlay(@PathVariable Long id) {
        log.debug("REST request to delete LocalPlay : {}", id);
        localPlayService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/local-plays?query=:query : search for the localPlay corresponding
     * to the query.
     *
     * @param query the query of the localPlay search 
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/local-plays")
    @Timed
    public ResponseEntity<List<LocalPlayDTO>> searchLocalPlays(@RequestParam String query, @ApiParam Pageable pageable) {
        log.debug("REST request to search for a page of LocalPlays for query {}", query);
        Page<LocalPlayDTO> page = localPlayService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/local-plays");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }


}
