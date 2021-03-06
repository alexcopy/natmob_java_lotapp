package ru.m2mcom.lotmicro.service.impl;

import ru.m2mcom.lotmicro.service.WebGeneratedService;
import ru.m2mcom.lotmicro.domain.WebGenerated;
import ru.m2mcom.lotmicro.repository.WebGeneratedRepository;
import ru.m2mcom.lotmicro.repository.search.WebGeneratedSearchRepository;
import ru.m2mcom.lotmicro.service.dto.WebGeneratedDTO;
import ru.m2mcom.lotmicro.service.mapper.WebGeneratedMapper;
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
 * Service Implementation for managing WebGenerated.
 */
@Service
@Transactional
public class WebGeneratedServiceImpl implements WebGeneratedService{

    private final Logger log = LoggerFactory.getLogger(WebGeneratedServiceImpl.class);
    
    private final WebGeneratedRepository webGeneratedRepository;

    private final WebGeneratedMapper webGeneratedMapper;

    private final WebGeneratedSearchRepository webGeneratedSearchRepository;

    public WebGeneratedServiceImpl(WebGeneratedRepository webGeneratedRepository, WebGeneratedMapper webGeneratedMapper, WebGeneratedSearchRepository webGeneratedSearchRepository) {
        this.webGeneratedRepository = webGeneratedRepository;
        this.webGeneratedMapper = webGeneratedMapper;
        this.webGeneratedSearchRepository = webGeneratedSearchRepository;
    }

    /**
     * Save a webGenerated.
     *
     * @param webGeneratedDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public WebGeneratedDTO save(WebGeneratedDTO webGeneratedDTO) {
        log.debug("Request to save WebGenerated : {}", webGeneratedDTO);
        WebGenerated webGenerated = webGeneratedMapper.webGeneratedDTOToWebGenerated(webGeneratedDTO);
        webGenerated = webGeneratedRepository.save(webGenerated);
        WebGeneratedDTO result = webGeneratedMapper.webGeneratedToWebGeneratedDTO(webGenerated);
        webGeneratedSearchRepository.save(webGenerated);
        return result;
    }

    /**
     *  Get all the webGenerateds.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<WebGeneratedDTO> findAll(Pageable pageable) {
        log.debug("Request to get all WebGenerateds");
        Page<WebGenerated> result = webGeneratedRepository.findAll(pageable);
        return result.map(webGenerated -> webGeneratedMapper.webGeneratedToWebGeneratedDTO(webGenerated));
    }

    /**
     *  Get one webGenerated by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public WebGeneratedDTO findOne(Long id) {
        log.debug("Request to get WebGenerated : {}", id);
        WebGenerated webGenerated = webGeneratedRepository.findOne(id);
        WebGeneratedDTO webGeneratedDTO = webGeneratedMapper.webGeneratedToWebGeneratedDTO(webGenerated);
        return webGeneratedDTO;
    }

    /**
     *  Delete the  webGenerated by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete WebGenerated : {}", id);
        webGeneratedRepository.delete(id);
        webGeneratedSearchRepository.delete(id);
    }

    /**
     * Search for the webGenerated corresponding to the query.
     *
     *  @param query the query of the search
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<WebGeneratedDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of WebGenerateds for query {}", query);
        Page<WebGenerated> result = webGeneratedSearchRepository.search(queryStringQuery(query), pageable);
        return result.map(webGenerated -> webGeneratedMapper.webGeneratedToWebGeneratedDTO(webGenerated));
    }
}
