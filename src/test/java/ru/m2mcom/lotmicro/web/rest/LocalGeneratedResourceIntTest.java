package ru.m2mcom.lotmicro.web.rest;

import ru.m2mcom.lotmicro.LotmicroApp;

import ru.m2mcom.lotmicro.domain.LocalGenerated;
import ru.m2mcom.lotmicro.repository.LocalGeneratedRepository;
import ru.m2mcom.lotmicro.service.LocalGeneratedService;
import ru.m2mcom.lotmicro.repository.search.LocalGeneratedSearchRepository;
import ru.m2mcom.lotmicro.service.dto.LocalGeneratedDTO;
import ru.m2mcom.lotmicro.service.mapper.LocalGeneratedMapper;
import ru.m2mcom.lotmicro.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import ru.m2mcom.lotmicro.domain.enumeration.GamesPlay;
/**
 * Test class for the LocalGeneratedResource REST controller.
 *
 * @see LocalGeneratedResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = LotmicroApp.class)
public class LocalGeneratedResourceIntTest {

    private static final String DEFAULT_DRAW = "AAAAAAAAAA";
    private static final String UPDATED_DRAW = "BBBBBBBBBB";

    private static final Integer DEFAULT_SUM_B = 1;
    private static final Integer UPDATED_SUM_B = 2;

    private static final Integer DEFAULT_SUM_S = 1;
    private static final Integer UPDATED_SUM_S = 2;

    private static final String DEFAULT_HASH = "AAAAAAAAAA";
    private static final String UPDATED_HASH = "BBBBBBBBBB";

    private static final GamesPlay DEFAULT_GAME = GamesPlay.EML;
    private static final GamesPlay UPDATED_GAME = GamesPlay.NAT;

    @Autowired
    private LocalGeneratedRepository localGeneratedRepository;

    @Autowired
    private LocalGeneratedMapper localGeneratedMapper;

    @Autowired
    private LocalGeneratedService localGeneratedService;

    @Autowired
    private LocalGeneratedSearchRepository localGeneratedSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restLocalGeneratedMockMvc;

    private LocalGenerated localGenerated;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        LocalGeneratedResource localGeneratedResource = new LocalGeneratedResource(localGeneratedService);
        this.restLocalGeneratedMockMvc = MockMvcBuilders.standaloneSetup(localGeneratedResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LocalGenerated createEntity(EntityManager em) {
        LocalGenerated localGenerated = new LocalGenerated()
            .draw(DEFAULT_DRAW)
            .sumB(DEFAULT_SUM_B)
            .sumS(DEFAULT_SUM_S)
            .hash(DEFAULT_HASH)
            .game(DEFAULT_GAME);
        return localGenerated;
    }

    @Before
    public void initTest() {
        localGeneratedSearchRepository.deleteAll();
        localGenerated = createEntity(em);
    }

    @Test
    @Transactional
    public void createLocalGenerated() throws Exception {
        int databaseSizeBeforeCreate = localGeneratedRepository.findAll().size();

        // Create the LocalGenerated
        LocalGeneratedDTO localGeneratedDTO = localGeneratedMapper.localGeneratedToLocalGeneratedDTO(localGenerated);
        restLocalGeneratedMockMvc.perform(post("/api/local-generateds")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(localGeneratedDTO)))
            .andExpect(status().isCreated());

        // Validate the LocalGenerated in the database
        List<LocalGenerated> localGeneratedList = localGeneratedRepository.findAll();
        assertThat(localGeneratedList).hasSize(databaseSizeBeforeCreate + 1);
        LocalGenerated testLocalGenerated = localGeneratedList.get(localGeneratedList.size() - 1);
        assertThat(testLocalGenerated.getDraw()).isEqualTo(DEFAULT_DRAW);
        assertThat(testLocalGenerated.getSumB()).isEqualTo(DEFAULT_SUM_B);
        assertThat(testLocalGenerated.getSumS()).isEqualTo(DEFAULT_SUM_S);
        assertThat(testLocalGenerated.getHash()).isEqualTo(DEFAULT_HASH);
        assertThat(testLocalGenerated.getGame()).isEqualTo(DEFAULT_GAME);

        // Validate the LocalGenerated in Elasticsearch
        LocalGenerated localGeneratedEs = localGeneratedSearchRepository.findOne(testLocalGenerated.getId());
        assertThat(localGeneratedEs).isEqualToComparingFieldByField(testLocalGenerated);
    }

    @Test
    @Transactional
    public void createLocalGeneratedWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = localGeneratedRepository.findAll().size();

        // Create the LocalGenerated with an existing ID
        localGenerated.setId(1L);
        LocalGeneratedDTO localGeneratedDTO = localGeneratedMapper.localGeneratedToLocalGeneratedDTO(localGenerated);

        // An entity with an existing ID cannot be created, so this API call must fail
        restLocalGeneratedMockMvc.perform(post("/api/local-generateds")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(localGeneratedDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<LocalGenerated> localGeneratedList = localGeneratedRepository.findAll();
        assertThat(localGeneratedList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllLocalGenerateds() throws Exception {
        // Initialize the database
        localGeneratedRepository.saveAndFlush(localGenerated);

        // Get all the localGeneratedList
        restLocalGeneratedMockMvc.perform(get("/api/local-generateds?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(localGenerated.getId().intValue())))
            .andExpect(jsonPath("$.[*].draw").value(hasItem(DEFAULT_DRAW.toString())))
            .andExpect(jsonPath("$.[*].sumB").value(hasItem(DEFAULT_SUM_B)))
            .andExpect(jsonPath("$.[*].sumS").value(hasItem(DEFAULT_SUM_S)))
            .andExpect(jsonPath("$.[*].hash").value(hasItem(DEFAULT_HASH.toString())))
            .andExpect(jsonPath("$.[*].game").value(hasItem(DEFAULT_GAME.toString())));
    }

    @Test
    @Transactional
    public void getLocalGenerated() throws Exception {
        // Initialize the database
        localGeneratedRepository.saveAndFlush(localGenerated);

        // Get the localGenerated
        restLocalGeneratedMockMvc.perform(get("/api/local-generateds/{id}", localGenerated.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(localGenerated.getId().intValue()))
            .andExpect(jsonPath("$.draw").value(DEFAULT_DRAW.toString()))
            .andExpect(jsonPath("$.sumB").value(DEFAULT_SUM_B))
            .andExpect(jsonPath("$.sumS").value(DEFAULT_SUM_S))
            .andExpect(jsonPath("$.hash").value(DEFAULT_HASH.toString()))
            .andExpect(jsonPath("$.game").value(DEFAULT_GAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingLocalGenerated() throws Exception {
        // Get the localGenerated
        restLocalGeneratedMockMvc.perform(get("/api/local-generateds/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateLocalGenerated() throws Exception {
        // Initialize the database
        localGeneratedRepository.saveAndFlush(localGenerated);
        localGeneratedSearchRepository.save(localGenerated);
        int databaseSizeBeforeUpdate = localGeneratedRepository.findAll().size();

        // Update the localGenerated
        LocalGenerated updatedLocalGenerated = localGeneratedRepository.findOne(localGenerated.getId());
        updatedLocalGenerated
            .draw(UPDATED_DRAW)
            .sumB(UPDATED_SUM_B)
            .sumS(UPDATED_SUM_S)
            .hash(UPDATED_HASH)
            .game(UPDATED_GAME);
        LocalGeneratedDTO localGeneratedDTO = localGeneratedMapper.localGeneratedToLocalGeneratedDTO(updatedLocalGenerated);

        restLocalGeneratedMockMvc.perform(put("/api/local-generateds")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(localGeneratedDTO)))
            .andExpect(status().isOk());

        // Validate the LocalGenerated in the database
        List<LocalGenerated> localGeneratedList = localGeneratedRepository.findAll();
        assertThat(localGeneratedList).hasSize(databaseSizeBeforeUpdate);
        LocalGenerated testLocalGenerated = localGeneratedList.get(localGeneratedList.size() - 1);
        assertThat(testLocalGenerated.getDraw()).isEqualTo(UPDATED_DRAW);
        assertThat(testLocalGenerated.getSumB()).isEqualTo(UPDATED_SUM_B);
        assertThat(testLocalGenerated.getSumS()).isEqualTo(UPDATED_SUM_S);
        assertThat(testLocalGenerated.getHash()).isEqualTo(UPDATED_HASH);
        assertThat(testLocalGenerated.getGame()).isEqualTo(UPDATED_GAME);

        // Validate the LocalGenerated in Elasticsearch
        LocalGenerated localGeneratedEs = localGeneratedSearchRepository.findOne(testLocalGenerated.getId());
        assertThat(localGeneratedEs).isEqualToComparingFieldByField(testLocalGenerated);
    }

    @Test
    @Transactional
    public void updateNonExistingLocalGenerated() throws Exception {
        int databaseSizeBeforeUpdate = localGeneratedRepository.findAll().size();

        // Create the LocalGenerated
        LocalGeneratedDTO localGeneratedDTO = localGeneratedMapper.localGeneratedToLocalGeneratedDTO(localGenerated);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restLocalGeneratedMockMvc.perform(put("/api/local-generateds")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(localGeneratedDTO)))
            .andExpect(status().isCreated());

        // Validate the LocalGenerated in the database
        List<LocalGenerated> localGeneratedList = localGeneratedRepository.findAll();
        assertThat(localGeneratedList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteLocalGenerated() throws Exception {
        // Initialize the database
        localGeneratedRepository.saveAndFlush(localGenerated);
        localGeneratedSearchRepository.save(localGenerated);
        int databaseSizeBeforeDelete = localGeneratedRepository.findAll().size();

        // Get the localGenerated
        restLocalGeneratedMockMvc.perform(delete("/api/local-generateds/{id}", localGenerated.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean localGeneratedExistsInEs = localGeneratedSearchRepository.exists(localGenerated.getId());
        assertThat(localGeneratedExistsInEs).isFalse();

        // Validate the database is empty
        List<LocalGenerated> localGeneratedList = localGeneratedRepository.findAll();
        assertThat(localGeneratedList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchLocalGenerated() throws Exception {
        // Initialize the database
        localGeneratedRepository.saveAndFlush(localGenerated);
        localGeneratedSearchRepository.save(localGenerated);

        // Search the localGenerated
        restLocalGeneratedMockMvc.perform(get("/api/_search/local-generateds?query=id:" + localGenerated.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(localGenerated.getId().intValue())))
            .andExpect(jsonPath("$.[*].draw").value(hasItem(DEFAULT_DRAW.toString())))
            .andExpect(jsonPath("$.[*].sumB").value(hasItem(DEFAULT_SUM_B)))
            .andExpect(jsonPath("$.[*].sumS").value(hasItem(DEFAULT_SUM_S)))
            .andExpect(jsonPath("$.[*].hash").value(hasItem(DEFAULT_HASH.toString())))
            .andExpect(jsonPath("$.[*].game").value(hasItem(DEFAULT_GAME.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(LocalGenerated.class);
    }
}
