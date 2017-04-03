package ru.m2mcom.lotmicro.web.rest;

import com.codahale.metrics.annotation.Timed;
import ru.m2mcom.lotmicro.service.JpInHistoryService;
import ru.m2mcom.lotmicro.web.rest.util.HeaderUtil;
import ru.m2mcom.lotmicro.service.dto.JpInHistoryDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
 * REST controller for managing JpInHistory.
 */
@RestController
@RequestMapping("/api")
public class JpInHistoryResource {

    private final Logger log = LoggerFactory.getLogger(JpInHistoryResource.class);

    private static final String ENTITY_NAME = "jpInHistory";
        
    private final JpInHistoryService jpInHistoryService;

    public JpInHistoryResource(JpInHistoryService jpInHistoryService) {
        this.jpInHistoryService = jpInHistoryService;
    }

    /**
     * POST  /jp-in-histories : Create a new jpInHistory.
     *
     * @param jpInHistoryDTO the jpInHistoryDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new jpInHistoryDTO, or with status 400 (Bad Request) if the jpInHistory has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/jp-in-histories")
    @Timed
    public ResponseEntity<JpInHistoryDTO> createJpInHistory(@Valid @RequestBody JpInHistoryDTO jpInHistoryDTO) throws URISyntaxException {
        log.debug("REST request to save JpInHistory : {}", jpInHistoryDTO);
        if (jpInHistoryDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new jpInHistory cannot already have an ID")).body(null);
        }
        JpInHistoryDTO result = jpInHistoryService.save(jpInHistoryDTO);
        return ResponseEntity.created(new URI("/api/jp-in-histories/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /jp-in-histories : Updates an existing jpInHistory.
     *
     * @param jpInHistoryDTO the jpInHistoryDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated jpInHistoryDTO,
     * or with status 400 (Bad Request) if the jpInHistoryDTO is not valid,
     * or with status 500 (Internal Server Error) if the jpInHistoryDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/jp-in-histories")
    @Timed
    public ResponseEntity<JpInHistoryDTO> updateJpInHistory(@Valid @RequestBody JpInHistoryDTO jpInHistoryDTO) throws URISyntaxException {
        log.debug("REST request to update JpInHistory : {}", jpInHistoryDTO);
        if (jpInHistoryDTO.getId() == null) {
            return createJpInHistory(jpInHistoryDTO);
        }
        JpInHistoryDTO result = jpInHistoryService.save(jpInHistoryDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, jpInHistoryDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /jp-in-histories : get all the jpInHistories.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of jpInHistories in body
     */
    @GetMapping("/jp-in-histories")
    @Timed
    public List<JpInHistoryDTO> getAllJpInHistories() {
        log.debug("REST request to get all JpInHistories");
        return jpInHistoryService.findAll();
    }

    /**
     * GET  /jp-in-histories/:id : get the "id" jpInHistory.
     *
     * @param id the id of the jpInHistoryDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the jpInHistoryDTO, or with status 404 (Not Found)
     */
    @GetMapping("/jp-in-histories/{id}")
    @Timed
    public ResponseEntity<JpInHistoryDTO> getJpInHistory(@PathVariable Long id) {
        log.debug("REST request to get JpInHistory : {}", id);
        JpInHistoryDTO jpInHistoryDTO = jpInHistoryService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(jpInHistoryDTO));
    }

    /**
     * DELETE  /jp-in-histories/:id : delete the "id" jpInHistory.
     *
     * @param id the id of the jpInHistoryDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/jp-in-histories/{id}")
    @Timed
    public ResponseEntity<Void> deleteJpInHistory(@PathVariable Long id) {
        log.debug("REST request to delete JpInHistory : {}", id);
        jpInHistoryService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/jp-in-histories?query=:query : search for the jpInHistory corresponding
     * to the query.
     *
     * @param query the query of the jpInHistory search 
     * @return the result of the search
     */
    @GetMapping("/_search/jp-in-histories")
    @Timed
    public List<JpInHistoryDTO> searchJpInHistories(@RequestParam String query) {
        log.debug("REST request to search JpInHistories for query {}", query);
        return jpInHistoryService.search(query);
    }


}
