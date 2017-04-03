package ru.m2mcom.lotmicro.web.rest;

import ru.m2mcom.lotmicro.LotmicroApp;

import ru.m2mcom.lotmicro.domain.HitPredict;
import ru.m2mcom.lotmicro.repository.HitPredictRepository;
import ru.m2mcom.lotmicro.service.HitPredictService;
import ru.m2mcom.lotmicro.repository.search.HitPredictSearchRepository;
import ru.m2mcom.lotmicro.service.dto.HitPredictDTO;
import ru.m2mcom.lotmicro.service.mapper.HitPredictMapper;
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

import ru.m2mcom.lotmicro.domain.enumeration.DrawPartType;
import ru.m2mcom.lotmicro.domain.enumeration.GamesPlay;
/**
 * Test class for the HitPredictResource REST controller.
 *
 * @see HitPredictResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = LotmicroApp.class)
public class HitPredictResourceIntTest {

    private static final Integer DEFAULT_NUMBER = 1;
    private static final Integer UPDATED_NUMBER = 2;

    private static final DrawPartType DEFAULT_DRAW_PART = DrawPartType.BALLS;
    private static final DrawPartType UPDATED_DRAW_PART = DrawPartType.BONUS;

    private static final Integer DEFAULT_CURRENT_SKIPS = 1;
    private static final Integer UPDATED_CURRENT_SKIPS = 2;

    private static final Integer DEFAULT_AVERAGE_SKIPS = 1;
    private static final Integer UPDATED_AVERAGE_SKIPS = 2;

    private static final Integer DEFAULT_DRAWS_DUE = 1;
    private static final Integer UPDATED_DRAWS_DUE = 2;

    private static final String DEFAULT_ALLSKIPS = "AAAAAAAAAA";
    private static final String UPDATED_ALLSKIPS = "BBBBBBBBBB";

    private static final String DEFAULT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_STATUS = "BBBBBBBBBB";

    private static final GamesPlay DEFAULT_GAME = GamesPlay.EML;
    private static final GamesPlay UPDATED_GAME = GamesPlay.NAT;

    @Autowired
    private HitPredictRepository hitPredictRepository;

    @Autowired
    private HitPredictMapper hitPredictMapper;

    @Autowired
    private HitPredictService hitPredictService;

    @Autowired
    private HitPredictSearchRepository hitPredictSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restHitPredictMockMvc;

    private HitPredict hitPredict;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        HitPredictResource hitPredictResource = new HitPredictResource(hitPredictService);
        this.restHitPredictMockMvc = MockMvcBuilders.standaloneSetup(hitPredictResource)
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
    public static HitPredict createEntity(EntityManager em) {
        HitPredict hitPredict = new HitPredict()
            .number(DEFAULT_NUMBER)
            .drawPart(DEFAULT_DRAW_PART)
            .currentSkips(DEFAULT_CURRENT_SKIPS)
            .averageSkips(DEFAULT_AVERAGE_SKIPS)
            .drawsDue(DEFAULT_DRAWS_DUE)
            .allskips(DEFAULT_ALLSKIPS)
            .status(DEFAULT_STATUS)
            .game(DEFAULT_GAME);
        return hitPredict;
    }

    @Before
    public void initTest() {
        hitPredictSearchRepository.deleteAll();
        hitPredict = createEntity(em);
    }

    @Test
    @Transactional
    public void createHitPredict() throws Exception {
        int databaseSizeBeforeCreate = hitPredictRepository.findAll().size();

        // Create the HitPredict
        HitPredictDTO hitPredictDTO = hitPredictMapper.hitPredictToHitPredictDTO(hitPredict);
        restHitPredictMockMvc.perform(post("/api/hit-predicts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(hitPredictDTO)))
            .andExpect(status().isCreated());

        // Validate the HitPredict in the database
        List<HitPredict> hitPredictList = hitPredictRepository.findAll();
        assertThat(hitPredictList).hasSize(databaseSizeBeforeCreate + 1);
        HitPredict testHitPredict = hitPredictList.get(hitPredictList.size() - 1);
        assertThat(testHitPredict.getNumber()).isEqualTo(DEFAULT_NUMBER);
        assertThat(testHitPredict.getDrawPart()).isEqualTo(DEFAULT_DRAW_PART);
        assertThat(testHitPredict.getCurrentSkips()).isEqualTo(DEFAULT_CURRENT_SKIPS);
        assertThat(testHitPredict.getAverageSkips()).isEqualTo(DEFAULT_AVERAGE_SKIPS);
        assertThat(testHitPredict.getDrawsDue()).isEqualTo(DEFAULT_DRAWS_DUE);
        assertThat(testHitPredict.getAllskips()).isEqualTo(DEFAULT_ALLSKIPS);
        assertThat(testHitPredict.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testHitPredict.getGame()).isEqualTo(DEFAULT_GAME);

        // Validate the HitPredict in Elasticsearch
        HitPredict hitPredictEs = hitPredictSearchRepository.findOne(testHitPredict.getId());
        assertThat(hitPredictEs).isEqualToComparingFieldByField(testHitPredict);
    }

    @Test
    @Transactional
    public void createHitPredictWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = hitPredictRepository.findAll().size();

        // Create the HitPredict with an existing ID
        hitPredict.setId(1L);
        HitPredictDTO hitPredictDTO = hitPredictMapper.hitPredictToHitPredictDTO(hitPredict);

        // An entity with an existing ID cannot be created, so this API call must fail
        restHitPredictMockMvc.perform(post("/api/hit-predicts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(hitPredictDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<HitPredict> hitPredictList = hitPredictRepository.findAll();
        assertThat(hitPredictList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllHitPredicts() throws Exception {
        // Initialize the database
        hitPredictRepository.saveAndFlush(hitPredict);

        // Get all the hitPredictList
        restHitPredictMockMvc.perform(get("/api/hit-predicts?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(hitPredict.getId().intValue())))
            .andExpect(jsonPath("$.[*].number").value(hasItem(DEFAULT_NUMBER)))
            .andExpect(jsonPath("$.[*].drawPart").value(hasItem(DEFAULT_DRAW_PART.toString())))
            .andExpect(jsonPath("$.[*].currentSkips").value(hasItem(DEFAULT_CURRENT_SKIPS)))
            .andExpect(jsonPath("$.[*].averageSkips").value(hasItem(DEFAULT_AVERAGE_SKIPS)))
            .andExpect(jsonPath("$.[*].drawsDue").value(hasItem(DEFAULT_DRAWS_DUE)))
            .andExpect(jsonPath("$.[*].allskips").value(hasItem(DEFAULT_ALLSKIPS.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].game").value(hasItem(DEFAULT_GAME.toString())));
    }

    @Test
    @Transactional
    public void getHitPredict() throws Exception {
        // Initialize the database
        hitPredictRepository.saveAndFlush(hitPredict);

        // Get the hitPredict
        restHitPredictMockMvc.perform(get("/api/hit-predicts/{id}", hitPredict.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(hitPredict.getId().intValue()))
            .andExpect(jsonPath("$.number").value(DEFAULT_NUMBER))
            .andExpect(jsonPath("$.drawPart").value(DEFAULT_DRAW_PART.toString()))
            .andExpect(jsonPath("$.currentSkips").value(DEFAULT_CURRENT_SKIPS))
            .andExpect(jsonPath("$.averageSkips").value(DEFAULT_AVERAGE_SKIPS))
            .andExpect(jsonPath("$.drawsDue").value(DEFAULT_DRAWS_DUE))
            .andExpect(jsonPath("$.allskips").value(DEFAULT_ALLSKIPS.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.game").value(DEFAULT_GAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingHitPredict() throws Exception {
        // Get the hitPredict
        restHitPredictMockMvc.perform(get("/api/hit-predicts/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateHitPredict() throws Exception {
        // Initialize the database
        hitPredictRepository.saveAndFlush(hitPredict);
        hitPredictSearchRepository.save(hitPredict);
        int databaseSizeBeforeUpdate = hitPredictRepository.findAll().size();

        // Update the hitPredict
        HitPredict updatedHitPredict = hitPredictRepository.findOne(hitPredict.getId());
        updatedHitPredict
            .number(UPDATED_NUMBER)
            .drawPart(UPDATED_DRAW_PART)
            .currentSkips(UPDATED_CURRENT_SKIPS)
            .averageSkips(UPDATED_AVERAGE_SKIPS)
            .drawsDue(UPDATED_DRAWS_DUE)
            .allskips(UPDATED_ALLSKIPS)
            .status(UPDATED_STATUS)
            .game(UPDATED_GAME);
        HitPredictDTO hitPredictDTO = hitPredictMapper.hitPredictToHitPredictDTO(updatedHitPredict);

        restHitPredictMockMvc.perform(put("/api/hit-predicts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(hitPredictDTO)))
            .andExpect(status().isOk());

        // Validate the HitPredict in the database
        List<HitPredict> hitPredictList = hitPredictRepository.findAll();
        assertThat(hitPredictList).hasSize(databaseSizeBeforeUpdate);
        HitPredict testHitPredict = hitPredictList.get(hitPredictList.size() - 1);
        assertThat(testHitPredict.getNumber()).isEqualTo(UPDATED_NUMBER);
        assertThat(testHitPredict.getDrawPart()).isEqualTo(UPDATED_DRAW_PART);
        assertThat(testHitPredict.getCurrentSkips()).isEqualTo(UPDATED_CURRENT_SKIPS);
        assertThat(testHitPredict.getAverageSkips()).isEqualTo(UPDATED_AVERAGE_SKIPS);
        assertThat(testHitPredict.getDrawsDue()).isEqualTo(UPDATED_DRAWS_DUE);
        assertThat(testHitPredict.getAllskips()).isEqualTo(UPDATED_ALLSKIPS);
        assertThat(testHitPredict.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testHitPredict.getGame()).isEqualTo(UPDATED_GAME);

        // Validate the HitPredict in Elasticsearch
        HitPredict hitPredictEs = hitPredictSearchRepository.findOne(testHitPredict.getId());
        assertThat(hitPredictEs).isEqualToComparingFieldByField(testHitPredict);
    }

    @Test
    @Transactional
    public void updateNonExistingHitPredict() throws Exception {
        int databaseSizeBeforeUpdate = hitPredictRepository.findAll().size();

        // Create the HitPredict
        HitPredictDTO hitPredictDTO = hitPredictMapper.hitPredictToHitPredictDTO(hitPredict);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restHitPredictMockMvc.perform(put("/api/hit-predicts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(hitPredictDTO)))
            .andExpect(status().isCreated());

        // Validate the HitPredict in the database
        List<HitPredict> hitPredictList = hitPredictRepository.findAll();
        assertThat(hitPredictList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteHitPredict() throws Exception {
        // Initialize the database
        hitPredictRepository.saveAndFlush(hitPredict);
        hitPredictSearchRepository.save(hitPredict);
        int databaseSizeBeforeDelete = hitPredictRepository.findAll().size();

        // Get the hitPredict
        restHitPredictMockMvc.perform(delete("/api/hit-predicts/{id}", hitPredict.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean hitPredictExistsInEs = hitPredictSearchRepository.exists(hitPredict.getId());
        assertThat(hitPredictExistsInEs).isFalse();

        // Validate the database is empty
        List<HitPredict> hitPredictList = hitPredictRepository.findAll();
        assertThat(hitPredictList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchHitPredict() throws Exception {
        // Initialize the database
        hitPredictRepository.saveAndFlush(hitPredict);
        hitPredictSearchRepository.save(hitPredict);

        // Search the hitPredict
        restHitPredictMockMvc.perform(get("/api/_search/hit-predicts?query=id:" + hitPredict.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(hitPredict.getId().intValue())))
            .andExpect(jsonPath("$.[*].number").value(hasItem(DEFAULT_NUMBER)))
            .andExpect(jsonPath("$.[*].drawPart").value(hasItem(DEFAULT_DRAW_PART.toString())))
            .andExpect(jsonPath("$.[*].currentSkips").value(hasItem(DEFAULT_CURRENT_SKIPS)))
            .andExpect(jsonPath("$.[*].averageSkips").value(hasItem(DEFAULT_AVERAGE_SKIPS)))
            .andExpect(jsonPath("$.[*].drawsDue").value(hasItem(DEFAULT_DRAWS_DUE)))
            .andExpect(jsonPath("$.[*].allskips").value(hasItem(DEFAULT_ALLSKIPS.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].game").value(hasItem(DEFAULT_GAME.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(HitPredict.class);
    }
}
