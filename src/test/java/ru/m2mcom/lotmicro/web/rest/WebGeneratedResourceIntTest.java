package ru.m2mcom.lotmicro.web.rest;

import ru.m2mcom.lotmicro.LotmicroApp;

import ru.m2mcom.lotmicro.domain.WebGenerated;
import ru.m2mcom.lotmicro.repository.WebGeneratedRepository;
import ru.m2mcom.lotmicro.service.WebGeneratedService;
import ru.m2mcom.lotmicro.repository.search.WebGeneratedSearchRepository;
import ru.m2mcom.lotmicro.service.dto.WebGeneratedDTO;
import ru.m2mcom.lotmicro.service.mapper.WebGeneratedMapper;
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
 * Test class for the WebGeneratedResource REST controller.
 *
 * @see WebGeneratedResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = LotmicroApp.class)
public class WebGeneratedResourceIntTest {

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
    private WebGeneratedRepository webGeneratedRepository;

    @Autowired
    private WebGeneratedMapper webGeneratedMapper;

    @Autowired
    private WebGeneratedService webGeneratedService;

    @Autowired
    private WebGeneratedSearchRepository webGeneratedSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restWebGeneratedMockMvc;

    private WebGenerated webGenerated;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        WebGeneratedResource webGeneratedResource = new WebGeneratedResource(webGeneratedService);
        this.restWebGeneratedMockMvc = MockMvcBuilders.standaloneSetup(webGeneratedResource)
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
    public static WebGenerated createEntity(EntityManager em) {
        WebGenerated webGenerated = new WebGenerated()
            .draw(DEFAULT_DRAW)
            .sumB(DEFAULT_SUM_B)
            .sumS(DEFAULT_SUM_S)
            .hash(DEFAULT_HASH)
            .game(DEFAULT_GAME);
        return webGenerated;
    }

    @Before
    public void initTest() {
        webGeneratedSearchRepository.deleteAll();
        webGenerated = createEntity(em);
    }

    @Test
    @Transactional
    public void createWebGenerated() throws Exception {
        int databaseSizeBeforeCreate = webGeneratedRepository.findAll().size();

        // Create the WebGenerated
        WebGeneratedDTO webGeneratedDTO = webGeneratedMapper.webGeneratedToWebGeneratedDTO(webGenerated);
        restWebGeneratedMockMvc.perform(post("/api/web-generateds")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(webGeneratedDTO)))
            .andExpect(status().isCreated());

        // Validate the WebGenerated in the database
        List<WebGenerated> webGeneratedList = webGeneratedRepository.findAll();
        assertThat(webGeneratedList).hasSize(databaseSizeBeforeCreate + 1);
        WebGenerated testWebGenerated = webGeneratedList.get(webGeneratedList.size() - 1);
        assertThat(testWebGenerated.getDraw()).isEqualTo(DEFAULT_DRAW);
        assertThat(testWebGenerated.getSumB()).isEqualTo(DEFAULT_SUM_B);
        assertThat(testWebGenerated.getSumS()).isEqualTo(DEFAULT_SUM_S);
        assertThat(testWebGenerated.getHash()).isEqualTo(DEFAULT_HASH);
        assertThat(testWebGenerated.getGame()).isEqualTo(DEFAULT_GAME);

        // Validate the WebGenerated in Elasticsearch
        WebGenerated webGeneratedEs = webGeneratedSearchRepository.findOne(testWebGenerated.getId());
        assertThat(webGeneratedEs).isEqualToComparingFieldByField(testWebGenerated);
    }

    @Test
    @Transactional
    public void createWebGeneratedWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = webGeneratedRepository.findAll().size();

        // Create the WebGenerated with an existing ID
        webGenerated.setId(1L);
        WebGeneratedDTO webGeneratedDTO = webGeneratedMapper.webGeneratedToWebGeneratedDTO(webGenerated);

        // An entity with an existing ID cannot be created, so this API call must fail
        restWebGeneratedMockMvc.perform(post("/api/web-generateds")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(webGeneratedDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<WebGenerated> webGeneratedList = webGeneratedRepository.findAll();
        assertThat(webGeneratedList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllWebGenerateds() throws Exception {
        // Initialize the database
        webGeneratedRepository.saveAndFlush(webGenerated);

        // Get all the webGeneratedList
        restWebGeneratedMockMvc.perform(get("/api/web-generateds?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(webGenerated.getId().intValue())))
            .andExpect(jsonPath("$.[*].draw").value(hasItem(DEFAULT_DRAW.toString())))
            .andExpect(jsonPath("$.[*].sumB").value(hasItem(DEFAULT_SUM_B)))
            .andExpect(jsonPath("$.[*].sumS").value(hasItem(DEFAULT_SUM_S)))
            .andExpect(jsonPath("$.[*].hash").value(hasItem(DEFAULT_HASH.toString())))
            .andExpect(jsonPath("$.[*].game").value(hasItem(DEFAULT_GAME.toString())));
    }

    @Test
    @Transactional
    public void getWebGenerated() throws Exception {
        // Initialize the database
        webGeneratedRepository.saveAndFlush(webGenerated);

        // Get the webGenerated
        restWebGeneratedMockMvc.perform(get("/api/web-generateds/{id}", webGenerated.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(webGenerated.getId().intValue()))
            .andExpect(jsonPath("$.draw").value(DEFAULT_DRAW.toString()))
            .andExpect(jsonPath("$.sumB").value(DEFAULT_SUM_B))
            .andExpect(jsonPath("$.sumS").value(DEFAULT_SUM_S))
            .andExpect(jsonPath("$.hash").value(DEFAULT_HASH.toString()))
            .andExpect(jsonPath("$.game").value(DEFAULT_GAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingWebGenerated() throws Exception {
        // Get the webGenerated
        restWebGeneratedMockMvc.perform(get("/api/web-generateds/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateWebGenerated() throws Exception {
        // Initialize the database
        webGeneratedRepository.saveAndFlush(webGenerated);
        webGeneratedSearchRepository.save(webGenerated);
        int databaseSizeBeforeUpdate = webGeneratedRepository.findAll().size();

        // Update the webGenerated
        WebGenerated updatedWebGenerated = webGeneratedRepository.findOne(webGenerated.getId());
        updatedWebGenerated
            .draw(UPDATED_DRAW)
            .sumB(UPDATED_SUM_B)
            .sumS(UPDATED_SUM_S)
            .hash(UPDATED_HASH)
            .game(UPDATED_GAME);
        WebGeneratedDTO webGeneratedDTO = webGeneratedMapper.webGeneratedToWebGeneratedDTO(updatedWebGenerated);

        restWebGeneratedMockMvc.perform(put("/api/web-generateds")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(webGeneratedDTO)))
            .andExpect(status().isOk());

        // Validate the WebGenerated in the database
        List<WebGenerated> webGeneratedList = webGeneratedRepository.findAll();
        assertThat(webGeneratedList).hasSize(databaseSizeBeforeUpdate);
        WebGenerated testWebGenerated = webGeneratedList.get(webGeneratedList.size() - 1);
        assertThat(testWebGenerated.getDraw()).isEqualTo(UPDATED_DRAW);
        assertThat(testWebGenerated.getSumB()).isEqualTo(UPDATED_SUM_B);
        assertThat(testWebGenerated.getSumS()).isEqualTo(UPDATED_SUM_S);
        assertThat(testWebGenerated.getHash()).isEqualTo(UPDATED_HASH);
        assertThat(testWebGenerated.getGame()).isEqualTo(UPDATED_GAME);

        // Validate the WebGenerated in Elasticsearch
        WebGenerated webGeneratedEs = webGeneratedSearchRepository.findOne(testWebGenerated.getId());
        assertThat(webGeneratedEs).isEqualToComparingFieldByField(testWebGenerated);
    }

    @Test
    @Transactional
    public void updateNonExistingWebGenerated() throws Exception {
        int databaseSizeBeforeUpdate = webGeneratedRepository.findAll().size();

        // Create the WebGenerated
        WebGeneratedDTO webGeneratedDTO = webGeneratedMapper.webGeneratedToWebGeneratedDTO(webGenerated);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restWebGeneratedMockMvc.perform(put("/api/web-generateds")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(webGeneratedDTO)))
            .andExpect(status().isCreated());

        // Validate the WebGenerated in the database
        List<WebGenerated> webGeneratedList = webGeneratedRepository.findAll();
        assertThat(webGeneratedList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteWebGenerated() throws Exception {
        // Initialize the database
        webGeneratedRepository.saveAndFlush(webGenerated);
        webGeneratedSearchRepository.save(webGenerated);
        int databaseSizeBeforeDelete = webGeneratedRepository.findAll().size();

        // Get the webGenerated
        restWebGeneratedMockMvc.perform(delete("/api/web-generateds/{id}", webGenerated.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean webGeneratedExistsInEs = webGeneratedSearchRepository.exists(webGenerated.getId());
        assertThat(webGeneratedExistsInEs).isFalse();

        // Validate the database is empty
        List<WebGenerated> webGeneratedList = webGeneratedRepository.findAll();
        assertThat(webGeneratedList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchWebGenerated() throws Exception {
        // Initialize the database
        webGeneratedRepository.saveAndFlush(webGenerated);
        webGeneratedSearchRepository.save(webGenerated);

        // Search the webGenerated
        restWebGeneratedMockMvc.perform(get("/api/_search/web-generateds?query=id:" + webGenerated.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(webGenerated.getId().intValue())))
            .andExpect(jsonPath("$.[*].draw").value(hasItem(DEFAULT_DRAW.toString())))
            .andExpect(jsonPath("$.[*].sumB").value(hasItem(DEFAULT_SUM_B)))
            .andExpect(jsonPath("$.[*].sumS").value(hasItem(DEFAULT_SUM_S)))
            .andExpect(jsonPath("$.[*].hash").value(hasItem(DEFAULT_HASH.toString())))
            .andExpect(jsonPath("$.[*].game").value(hasItem(DEFAULT_GAME.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(WebGenerated.class);
    }
}
