package ru.m2mcom.lotmicro.service.impl;

import ru.m2mcom.lotmicro.service.MathExpectationService;
import ru.m2mcom.lotmicro.domain.MathExpectation;
import ru.m2mcom.lotmicro.repository.MathExpectationRepository;
import ru.m2mcom.lotmicro.repository.search.MathExpectationSearchRepository;
import ru.m2mcom.lotmicro.service.dto.MathExpectationDTO;
import ru.m2mcom.lotmicro.service.mapper.MathExpectationMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing MathExpectation.
 */
@Service
@Transactional
public class MathExpectationServiceImpl implements MathExpectationService{

    private final Logger log = LoggerFactory.getLogger(MathExpectationServiceImpl.class);
    
    private final MathExpectationRepository mathExpectationRepository;

    private final MathExpectationMapper mathExpectationMapper;

    private final MathExpectationSearchRepository mathExpectationSearchRepository;

    public MathExpectationServiceImpl(MathExpectationRepository mathExpectationRepository, MathExpectationMapper mathExpectationMapper, MathExpectationSearchRepository mathExpectationSearchRepository) {
        this.mathExpectationRepository = mathExpectationRepository;
        this.mathExpectationMapper = mathExpectationMapper;
        this.mathExpectationSearchRepository = mathExpectationSearchRepository;
    }

    /**
     * Save a mathExpectation.
     *
     * @param mathExpectationDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public MathExpectationDTO save(MathExpectationDTO mathExpectationDTO) {
        log.debug("Request to save MathExpectation : {}", mathExpectationDTO);
        MathExpectation mathExpectation = mathExpectationMapper.mathExpectationDTOToMathExpectation(mathExpectationDTO);
        mathExpectation = mathExpectationRepository.save(mathExpectation);
        MathExpectationDTO result = mathExpectationMapper.mathExpectationToMathExpectationDTO(mathExpectation);
        mathExpectationSearchRepository.save(mathExpectation);
        return result;
    }

    /**
     *  Get all the mathExpectations.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<MathExpectationDTO> findAll(Pageable pageable) {
        log.debug("Request to get all MathExpectations");
        Page<MathExpectation> result = mathExpectationRepository.findAll(pageable);
        return result.map(mathExpectation -> mathExpectationMapper.mathExpectationToMathExpectationDTO(mathExpectation));
    }

    /**
     *  Get one mathExpectation by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public MathExpectationDTO findOne(Long id) {
        log.debug("Request to get MathExpectation : {}", id);
        MathExpectation mathExpectation = mathExpectationRepository.findOne(id);
        MathExpectationDTO mathExpectationDTO = mathExpectationMapper.mathExpectationToMathExpectationDTO(mathExpectation);
        return mathExpectationDTO;
    }

    /**
     *  Delete the  mathExpectation by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete MathExpectation : {}", id);
        mathExpectationRepository.delete(id);
        mathExpectationSearchRepository.delete(id);
    }

    /**
     * Search for the mathExpectation corresponding to the query.
     *
     *  @param query the query of the search
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<MathExpectationDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of MathExpectations for query {}", query);
        Page<MathExpectation> result = mathExpectationSearchRepository.search(queryStringQuery(query), pageable);
        return result.map(mathExpectation -> mathExpectationMapper.mathExpectationToMathExpectationDTO(mathExpectation));
    }
}
