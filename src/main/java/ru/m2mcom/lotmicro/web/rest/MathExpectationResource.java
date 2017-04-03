package ru.m2mcom.lotmicro.web.rest;

import com.codahale.metrics.annotation.Timed;
import ru.m2mcom.lotmicro.service.MathExpectationService;
import ru.m2mcom.lotmicro.web.rest.util.HeaderUtil;
import ru.m2mcom.lotmicro.web.rest.util.PaginationUtil;
import ru.m2mcom.lotmicro.service.dto.MathExpectationDTO;
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
 * REST controller for managing MathExpectation.
 */
@RestController
@RequestMapping("/api")
public class MathExpectationResource {

    private final Logger log = LoggerFactory.getLogger(MathExpectationResource.class);

    private static final String ENTITY_NAME = "mathExpectation";
        
    private final MathExpectationService mathExpectationService;

    public MathExpectationResource(MathExpectationService mathExpectationService) {
        this.mathExpectationService = mathExpectationService;
    }

    /**
     * POST  /math-expectations : Create a new mathExpectation.
     *
     * @param mathExpectationDTO the mathExpectationDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new mathExpectationDTO, or with status 400 (Bad Request) if the mathExpectation has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/math-expectations")
    @Timed
    public ResponseEntity<MathExpectationDTO> createMathExpectation(@Valid @RequestBody MathExpectationDTO mathExpectationDTO) throws URISyntaxException {
        log.debug("REST request to save MathExpectation : {}", mathExpectationDTO);
        if (mathExpectationDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new mathExpectation cannot already have an ID")).body(null);
        }
        MathExpectationDTO result = mathExpectationService.save(mathExpectationDTO);
        return ResponseEntity.created(new URI("/api/math-expectations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /math-expectations : Updates an existing mathExpectation.
     *
     * @param mathExpectationDTO the mathExpectationDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated mathExpectationDTO,
     * or with status 400 (Bad Request) if the mathExpectationDTO is not valid,
     * or with status 500 (Internal Server Error) if the mathExpectationDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/math-expectations")
    @Timed
    public ResponseEntity<MathExpectationDTO> updateMathExpectation(@Valid @RequestBody MathExpectationDTO mathExpectationDTO) throws URISyntaxException {
        log.debug("REST request to update MathExpectation : {}", mathExpectationDTO);
        if (mathExpectationDTO.getId() == null) {
            return createMathExpectation(mathExpectationDTO);
        }
        MathExpectationDTO result = mathExpectationService.save(mathExpectationDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, mathExpectationDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /math-expectations : get all the mathExpectations.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of mathExpectations in body
     */
    @GetMapping("/math-expectations")
    @Timed
    public ResponseEntity<List<MathExpectationDTO>> getAllMathExpectations(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of MathExpectations");
        Page<MathExpectationDTO> page = mathExpectationService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/math-expectations");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /math-expectations/:id : get the "id" mathExpectation.
     *
     * @param id the id of the mathExpectationDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the mathExpectationDTO, or with status 404 (Not Found)
     */
    @GetMapping("/math-expectations/{id}")
    @Timed
    public ResponseEntity<MathExpectationDTO> getMathExpectation(@PathVariable Long id) {
        log.debug("REST request to get MathExpectation : {}", id);
        MathExpectationDTO mathExpectationDTO = mathExpectationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(mathExpectationDTO));
    }

    /**
     * DELETE  /math-expectations/:id : delete the "id" mathExpectation.
     *
     * @param id the id of the mathExpectationDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/math-expectations/{id}")
    @Timed
    public ResponseEntity<Void> deleteMathExpectation(@PathVariable Long id) {
        log.debug("REST request to delete MathExpectation : {}", id);
        mathExpectationService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/math-expectations?query=:query : search for the mathExpectation corresponding
     * to the query.
     *
     * @param query the query of the mathExpectation search 
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/math-expectations")
    @Timed
    public ResponseEntity<List<MathExpectationDTO>> searchMathExpectations(@RequestParam String query, @ApiParam Pageable pageable) {
        log.debug("REST request to search for a page of MathExpectations for query {}", query);
        Page<MathExpectationDTO> page = mathExpectationService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/math-expectations");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }


}
