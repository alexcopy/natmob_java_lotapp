package ru.m2mcom.lotmicro.web.rest;

import com.codahale.metrics.annotation.Timed;
import ru.m2mcom.lotmicro.service.LocalGeneratedService;
import ru.m2mcom.lotmicro.web.rest.util.HeaderUtil;
import ru.m2mcom.lotmicro.web.rest.util.PaginationUtil;
import ru.m2mcom.lotmicro.service.dto.LocalGeneratedDTO;
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
 * REST controller for managing LocalGenerated.
 */
@RestController
@RequestMapping("/api")
public class LocalGeneratedResource {

    private final Logger log = LoggerFactory.getLogger(LocalGeneratedResource.class);

    private static final String ENTITY_NAME = "localGenerated";
        
    private final LocalGeneratedService localGeneratedService;

    public LocalGeneratedResource(LocalGeneratedService localGeneratedService) {
        this.localGeneratedService = localGeneratedService;
    }

    /**
     * POST  /local-generateds : Create a new localGenerated.
     *
     * @param localGeneratedDTO the localGeneratedDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new localGeneratedDTO, or with status 400 (Bad Request) if the localGenerated has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/local-generateds")
    @Timed
    public ResponseEntity<LocalGeneratedDTO> createLocalGenerated(@RequestBody LocalGeneratedDTO localGeneratedDTO) throws URISyntaxException {
        log.debug("REST request to save LocalGenerated : {}", localGeneratedDTO);
        if (localGeneratedDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new localGenerated cannot already have an ID")).body(null);
        }
        LocalGeneratedDTO result = localGeneratedService.save(localGeneratedDTO);
        return ResponseEntity.created(new URI("/api/local-generateds/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /local-generateds : Updates an existing localGenerated.
     *
     * @param localGeneratedDTO the localGeneratedDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated localGeneratedDTO,
     * or with status 400 (Bad Request) if the localGeneratedDTO is not valid,
     * or with status 500 (Internal Server Error) if the localGeneratedDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/local-generateds")
    @Timed
    public ResponseEntity<LocalGeneratedDTO> updateLocalGenerated(@RequestBody LocalGeneratedDTO localGeneratedDTO) throws URISyntaxException {
        log.debug("REST request to update LocalGenerated : {}", localGeneratedDTO);
        if (localGeneratedDTO.getId() == null) {
            return createLocalGenerated(localGeneratedDTO);
        }
        LocalGeneratedDTO result = localGeneratedService.save(localGeneratedDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, localGeneratedDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /local-generateds : get all the localGenerateds.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of localGenerateds in body
     */
    @GetMapping("/local-generateds")
    @Timed
    public ResponseEntity<List<LocalGeneratedDTO>> getAllLocalGenerateds(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of LocalGenerateds");
        Page<LocalGeneratedDTO> page = localGeneratedService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/local-generateds");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /local-generateds/:id : get the "id" localGenerated.
     *
     * @param id the id of the localGeneratedDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the localGeneratedDTO, or with status 404 (Not Found)
     */
    @GetMapping("/local-generateds/{id}")
    @Timed
    public ResponseEntity<LocalGeneratedDTO> getLocalGenerated(@PathVariable Long id) {
        log.debug("REST request to get LocalGenerated : {}", id);
        LocalGeneratedDTO localGeneratedDTO = localGeneratedService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(localGeneratedDTO));
    }

    /**
     * DELETE  /local-generateds/:id : delete the "id" localGenerated.
     *
     * @param id the id of the localGeneratedDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/local-generateds/{id}")
    @Timed
    public ResponseEntity<Void> deleteLocalGenerated(@PathVariable Long id) {
        log.debug("REST request to delete LocalGenerated : {}", id);
        localGeneratedService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/local-generateds?query=:query : search for the localGenerated corresponding
     * to the query.
     *
     * @param query the query of the localGenerated search 
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/local-generateds")
    @Timed
    public ResponseEntity<List<LocalGeneratedDTO>> searchLocalGenerateds(@RequestParam String query, @ApiParam Pageable pageable) {
        log.debug("REST request to search for a page of LocalGenerateds for query {}", query);
        Page<LocalGeneratedDTO> page = localGeneratedService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/local-generateds");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }


}
