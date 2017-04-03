package ru.m2mcom.lotmicro.service.impl;

import ru.m2mcom.lotmicro.service.LocalPlayService;
import ru.m2mcom.lotmicro.domain.LocalPlay;
import ru.m2mcom.lotmicro.repository.LocalPlayRepository;
import ru.m2mcom.lotmicro.repository.search.LocalPlaySearchRepository;
import ru.m2mcom.lotmicro.service.dto.LocalPlayDTO;
import ru.m2mcom.lotmicro.service.mapper.LocalPlayMapper;
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
 * Service Implementation for managing LocalPlay.
 */
@Service
@Transactional
public class LocalPlayServiceImpl implements LocalPlayService{

    private final Logger log = LoggerFactory.getLogger(LocalPlayServiceImpl.class);
    
    private final LocalPlayRepository localPlayRepository;

    private final LocalPlayMapper localPlayMapper;

    private final LocalPlaySearchRepository localPlaySearchRepository;

    public LocalPlayServiceImpl(LocalPlayRepository localPlayRepository, LocalPlayMapper localPlayMapper, LocalPlaySearchRepository localPlaySearchRepository) {
        this.localPlayRepository = localPlayRepository;
        this.localPlayMapper = localPlayMapper;
        this.localPlaySearchRepository = localPlaySearchRepository;
    }

    /**
     * Save a localPlay.
     *
     * @param localPlayDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public LocalPlayDTO save(LocalPlayDTO localPlayDTO) {
        log.debug("Request to save LocalPlay : {}", localPlayDTO);
        LocalPlay localPlay = localPlayMapper.localPlayDTOToLocalPlay(localPlayDTO);
        localPlay = localPlayRepository.save(localPlay);
        LocalPlayDTO result = localPlayMapper.localPlayToLocalPlayDTO(localPlay);
        localPlaySearchRepository.save(localPlay);
        return result;
    }

    /**
     *  Get all the localPlays.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<LocalPlayDTO> findAll(Pageable pageable) {
        log.debug("Request to get all LocalPlays");
        Page<LocalPlay> result = localPlayRepository.findAll(pageable);
        return result.map(localPlay -> localPlayMapper.localPlayToLocalPlayDTO(localPlay));
    }

    /**
     *  Get one localPlay by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public LocalPlayDTO findOne(Long id) {
        log.debug("Request to get LocalPlay : {}", id);
        LocalPlay localPlay = localPlayRepository.findOne(id);
        LocalPlayDTO localPlayDTO = localPlayMapper.localPlayToLocalPlayDTO(localPlay);
        return localPlayDTO;
    }

    /**
     *  Delete the  localPlay by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete LocalPlay : {}", id);
        localPlayRepository.delete(id);
        localPlaySearchRepository.delete(id);
    }

    /**
     * Search for the localPlay corresponding to the query.
     *
     *  @param query the query of the search
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<LocalPlayDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of LocalPlays for query {}", query);
        Page<LocalPlay> result = localPlaySearchRepository.search(queryStringQuery(query), pageable);
        return result.map(localPlay -> localPlayMapper.localPlayToLocalPlayDTO(localPlay));
    }
}
