package ru.m2mcom.lotmicro.web.rest;

import com.codahale.metrics.annotation.Timed;
import ru.m2mcom.lotmicro.service.WebGeneratedService;
import ru.m2mcom.lotmicro.web.rest.util.HeaderUtil;
import ru.m2mcom.lotmicro.web.rest.util.PaginationUtil;
import ru.m2mcom.lotmicro.service.dto.WebGeneratedDTO;
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
 * REST controller for managing WebGenerated.
 */
@RestController
@RequestMapping("/api")
public class WebGeneratedResource {

    private final Logger log = LoggerFactory.getLogger(WebGeneratedResource.class);

    private static final String ENTITY_NAME = "webGenerated";
        
    private final WebGeneratedService webGeneratedService;

    public WebGeneratedResource(WebGeneratedService webGeneratedService) {
        this.webGeneratedService = webGeneratedService;
    }

    /**
     * POST  /web-generateds : Create a new webGenerated.
     *
     * @param webGeneratedDTO the webGeneratedDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new webGeneratedDTO, or with status 400 (Bad Request) if the webGenerated has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/web-generateds")
    @Timed
    public ResponseEntity<WebGeneratedDTO> createWebGenerated(@RequestBody WebGeneratedDTO webGeneratedDTO) throws URISyntaxException {
        log.debug("REST request to save WebGenerated : {}", webGeneratedDTO);
        if (webGeneratedDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new webGenerated cannot already have an ID")).body(null);
        }
        WebGeneratedDTO result = webGeneratedService.save(webGeneratedDTO);
        return ResponseEntity.created(new URI("/api/web-generateds/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /web-generateds : Updates an existing webGenerated.
     *
     * @param webGeneratedDTO the webGeneratedDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated webGeneratedDTO,
     * or with status 400 (Bad Request) if the webGeneratedDTO is not valid,
     * or with status 500 (Internal Server Error) if the webGeneratedDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/web-generateds")
    @Timed
    public ResponseEntity<WebGeneratedDTO> updateWebGenerated(@RequestBody WebGeneratedDTO webGeneratedDTO) throws URISyntaxException {
        log.debug("REST request to update WebGenerated : {}", webGeneratedDTO);
        if (webGeneratedDTO.getId() == null) {
            return createWebGenerated(webGeneratedDTO);
        }
        WebGeneratedDTO result = webGeneratedService.save(webGeneratedDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, webGeneratedDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /web-generateds : get all the webGenerateds.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of webGenerateds in body
     */
    @GetMapping("/web-generateds")
    @Timed
    public ResponseEntity<List<WebGeneratedDTO>> getAllWebGenerateds(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of WebGenerateds");
        Page<WebGeneratedDTO> page = webGeneratedService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/web-generateds");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /web-generateds/:id : get the "id" webGenerated.
     *
     * @param id the id of the webGeneratedDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the webGeneratedDTO, or with status 404 (Not Found)
     */
    @GetMapping("/web-generateds/{id}")
    @Timed
    public ResponseEntity<WebGeneratedDTO> getWebGenerated(@PathVariable Long id) {
        log.debug("REST request to get WebGenerated : {}", id);
        WebGeneratedDTO webGeneratedDTO = webGeneratedService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(webGeneratedDTO));
    }

    /**
     * DELETE  /web-generateds/:id : delete the "id" webGenerated.
     *
     * @param id the id of the webGeneratedDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/web-generateds/{id}")
    @Timed
    public ResponseEntity<Void> deleteWebGenerated(@PathVariable Long id) {
        log.debug("REST request to delete WebGenerated : {}", id);
        webGeneratedService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/web-generateds?query=:query : search for the webGenerated corresponding
     * to the query.
     *
     * @param query the query of the webGenerated search 
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/web-generateds")
    @Timed
    public ResponseEntity<List<WebGeneratedDTO>> searchWebGenerateds(@RequestParam String query, @ApiParam Pageable pageable) {
        log.debug("REST request to search for a page of WebGenerateds for query {}", query);
        Page<WebGeneratedDTO> page = webGeneratedService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/web-generateds");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }


}
