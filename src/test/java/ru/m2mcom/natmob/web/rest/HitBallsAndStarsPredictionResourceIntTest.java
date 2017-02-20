package ru.m2mcom.natmob.web.rest;

import ru.m2mcom.natmob.LotappApp;

import ru.m2mcom.natmob.domain.HitBallsAndStarsPrediction;
import ru.m2mcom.natmob.repository.HitBallsAndStarsPredictionRepository;
import ru.m2mcom.natmob.service.HitBallsAndStarsPredictionService;
import ru.m2mcom.natmob.repository.search.HitBallsAndStarsPredictionSearchRepository;

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

/**
 * Test class for the HitBallsAndStarsPredictionResource REST controller.
 *
 * @see HitBallsAndStarsPredictionResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = LotappApp.class)
public class HitBallsAndStarsPredictionResourceIntTest {

    private static final Integer DEFAULT_NUMBER = 1;
    private static final Integer UPDATED_NUMBER = 2;

    private static final Integer DEFAULT_CURRENT_SKIPS = 1;
    private static final Integer UPDATED_CURRENT_SKIPS = 2;

    private static final Integer DEFAULT_AVERAGE_SKIPS = 1;
    private static final Integer UPDATED_AVERAGE_SKIPS = 2;

    private static final Integer DEFAULT_DRAWS_DUE = 1;
    private static final Integer UPDATED_DRAWS_DUE = 2;

    private static final String DEFAULT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_STATUS = "BBBBBBBBBB";

    private static final String DEFAULT_BALL_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_BALL_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_GAME_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_GAME_TYPE = "BBBBBBBBBB";

    private static final Integer DEFAULT_TIMESTAMP = 1;
    private static final Integer UPDATED_TIMESTAMP = 2;

    @Autowired
    private HitBallsAndStarsPredictionRepository hitBallsAndStarsPredictionRepository;

    @Autowired
    private HitBallsAndStarsPredictionService hitBallsAndStarsPredictionService;

    @Autowired
    private HitBallsAndStarsPredictionSearchRepository hitBallsAndStarsPredictionSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private EntityManager em;

    private MockMvc restHitBallsAndStarsPredictionMockMvc;

    private HitBallsAndStarsPrediction hitBallsAndStarsPrediction;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        HitBallsAndStarsPredictionResource hitBallsAndStarsPredictionResource = new HitBallsAndStarsPredictionResource(hitBallsAndStarsPredictionService);
        this.restHitBallsAndStarsPredictionMockMvc = MockMvcBuilders.standaloneSetup(hitBallsAndStarsPredictionResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static HitBallsAndStarsPrediction createEntity(EntityManager em) {
        HitBallsAndStarsPrediction hitBallsAndStarsPrediction = new HitBallsAndStarsPrediction()
                .number(DEFAULT_NUMBER)
                .currentSkips(DEFAULT_CURRENT_SKIPS)
                .averageSkips(DEFAULT_AVERAGE_SKIPS)
                .drawsDue(DEFAULT_DRAWS_DUE)
                .status(DEFAULT_STATUS)
                .ballType(DEFAULT_BALL_TYPE)
                .gameType(DEFAULT_GAME_TYPE)
                .timestamp(DEFAULT_TIMESTAMP);
        return hitBallsAndStarsPrediction;
    }

    @Before
    public void initTest() {
        hitBallsAndStarsPredictionSearchRepository.deleteAll();
        hitBallsAndStarsPrediction = createEntity(em);
    }

    @Test
    @Transactional
    public void createHitBallsAndStarsPrediction() throws Exception {
        int databaseSizeBeforeCreate = hitBallsAndStarsPredictionRepository.findAll().size();

        // Create the HitBallsAndStarsPrediction

        restHitBallsAndStarsPredictionMockMvc.perform(post("/api/hit-balls-and-stars-predictions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(hitBallsAndStarsPrediction)))
            .andExpect(status().isCreated());

        // Validate the HitBallsAndStarsPrediction in the database
        List<HitBallsAndStarsPrediction> hitBallsAndStarsPredictionList = hitBallsAndStarsPredictionRepository.findAll();
        assertThat(hitBallsAndStarsPredictionList).hasSize(databaseSizeBeforeCreate + 1);
        HitBallsAndStarsPrediction testHitBallsAndStarsPrediction = hitBallsAndStarsPredictionList.get(hitBallsAndStarsPredictionList.size() - 1);
        assertThat(testHitBallsAndStarsPrediction.getNumber()).isEqualTo(DEFAULT_NUMBER);
        assertThat(testHitBallsAndStarsPrediction.getCurrentSkips()).isEqualTo(DEFAULT_CURRENT_SKIPS);
        assertThat(testHitBallsAndStarsPrediction.getAverageSkips()).isEqualTo(DEFAULT_AVERAGE_SKIPS);
        assertThat(testHitBallsAndStarsPrediction.getDrawsDue()).isEqualTo(DEFAULT_DRAWS_DUE);
        assertThat(testHitBallsAndStarsPrediction.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testHitBallsAndStarsPrediction.getBallType()).isEqualTo(DEFAULT_BALL_TYPE);
        assertThat(testHitBallsAndStarsPrediction.getGameType()).isEqualTo(DEFAULT_GAME_TYPE);
        assertThat(testHitBallsAndStarsPrediction.getTimestamp()).isEqualTo(DEFAULT_TIMESTAMP);

        // Validate the HitBallsAndStarsPrediction in Elasticsearch
        HitBallsAndStarsPrediction hitBallsAndStarsPredictionEs = hitBallsAndStarsPredictionSearchRepository.findOne(testHitBallsAndStarsPrediction.getId());
        assertThat(hitBallsAndStarsPredictionEs).isEqualToComparingFieldByField(testHitBallsAndStarsPrediction);
    }

    @Test
    @Transactional
    public void createHitBallsAndStarsPredictionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = hitBallsAndStarsPredictionRepository.findAll().size();

        // Create the HitBallsAndStarsPrediction with an existing ID
        HitBallsAndStarsPrediction existingHitBallsAndStarsPrediction = new HitBallsAndStarsPrediction();
        existingHitBallsAndStarsPrediction.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restHitBallsAndStarsPredictionMockMvc.perform(post("/api/hit-balls-and-stars-predictions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingHitBallsAndStarsPrediction)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<HitBallsAndStarsPrediction> hitBallsAndStarsPredictionList = hitBallsAndStarsPredictionRepository.findAll();
        assertThat(hitBallsAndStarsPredictionList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNumberIsRequired() throws Exception {
        int databaseSizeBeforeTest = hitBallsAndStarsPredictionRepository.findAll().size();
        // set the field null
        hitBallsAndStarsPrediction.setNumber(null);

        // Create the HitBallsAndStarsPrediction, which fails.

        restHitBallsAndStarsPredictionMockMvc.perform(post("/api/hit-balls-and-stars-predictions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(hitBallsAndStarsPrediction)))
            .andExpect(status().isBadRequest());

        List<HitBallsAndStarsPrediction> hitBallsAndStarsPredictionList = hitBallsAndStarsPredictionRepository.findAll();
        assertThat(hitBallsAndStarsPredictionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCurrentSkipsIsRequired() throws Exception {
        int databaseSizeBeforeTest = hitBallsAndStarsPredictionRepository.findAll().size();
        // set the field null
        hitBallsAndStarsPrediction.setCurrentSkips(null);

        // Create the HitBallsAndStarsPrediction, which fails.

        restHitBallsAndStarsPredictionMockMvc.perform(post("/api/hit-balls-and-stars-predictions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(hitBallsAndStarsPrediction)))
            .andExpect(status().isBadRequest());

        List<HitBallsAndStarsPrediction> hitBallsAndStarsPredictionList = hitBallsAndStarsPredictionRepository.findAll();
        assertThat(hitBallsAndStarsPredictionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAverageSkipsIsRequired() throws Exception {
        int databaseSizeBeforeTest = hitBallsAndStarsPredictionRepository.findAll().size();
        // set the field null
        hitBallsAndStarsPrediction.setAverageSkips(null);

        // Create the HitBallsAndStarsPrediction, which fails.

        restHitBallsAndStarsPredictionMockMvc.perform(post("/api/hit-balls-and-stars-predictions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(hitBallsAndStarsPrediction)))
            .andExpect(status().isBadRequest());

        List<HitBallsAndStarsPrediction> hitBallsAndStarsPredictionList = hitBallsAndStarsPredictionRepository.findAll();
        assertThat(hitBallsAndStarsPredictionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDrawsDueIsRequired() throws Exception {
        int databaseSizeBeforeTest = hitBallsAndStarsPredictionRepository.findAll().size();
        // set the field null
        hitBallsAndStarsPrediction.setDrawsDue(null);

        // Create the HitBallsAndStarsPrediction, which fails.

        restHitBallsAndStarsPredictionMockMvc.perform(post("/api/hit-balls-and-stars-predictions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(hitBallsAndStarsPrediction)))
            .andExpect(status().isBadRequest());

        List<HitBallsAndStarsPrediction> hitBallsAndStarsPredictionList = hitBallsAndStarsPredictionRepository.findAll();
        assertThat(hitBallsAndStarsPredictionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = hitBallsAndStarsPredictionRepository.findAll().size();
        // set the field null
        hitBallsAndStarsPrediction.setStatus(null);

        // Create the HitBallsAndStarsPrediction, which fails.

        restHitBallsAndStarsPredictionMockMvc.perform(post("/api/hit-balls-and-stars-predictions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(hitBallsAndStarsPrediction)))
            .andExpect(status().isBadRequest());

        List<HitBallsAndStarsPrediction> hitBallsAndStarsPredictionList = hitBallsAndStarsPredictionRepository.findAll();
        assertThat(hitBallsAndStarsPredictionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkBallTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = hitBallsAndStarsPredictionRepository.findAll().size();
        // set the field null
        hitBallsAndStarsPrediction.setBallType(null);

        // Create the HitBallsAndStarsPrediction, which fails.

        restHitBallsAndStarsPredictionMockMvc.perform(post("/api/hit-balls-and-stars-predictions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(hitBallsAndStarsPrediction)))
            .andExpect(status().isBadRequest());

        List<HitBallsAndStarsPrediction> hitBallsAndStarsPredictionList = hitBallsAndStarsPredictionRepository.findAll();
        assertThat(hitBallsAndStarsPredictionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkGameTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = hitBallsAndStarsPredictionRepository.findAll().size();
        // set the field null
        hitBallsAndStarsPrediction.setGameType(null);

        // Create the HitBallsAndStarsPrediction, which fails.

        restHitBallsAndStarsPredictionMockMvc.perform(post("/api/hit-balls-and-stars-predictions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(hitBallsAndStarsPrediction)))
            .andExpect(status().isBadRequest());

        List<HitBallsAndStarsPrediction> hitBallsAndStarsPredictionList = hitBallsAndStarsPredictionRepository.findAll();
        assertThat(hitBallsAndStarsPredictionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllHitBallsAndStarsPredictions() throws Exception {
        // Initialize the database
        hitBallsAndStarsPredictionRepository.saveAndFlush(hitBallsAndStarsPrediction);

        // Get all the hitBallsAndStarsPredictionList
        restHitBallsAndStarsPredictionMockMvc.perform(get("/api/hit-balls-and-stars-predictions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(hitBallsAndStarsPrediction.getId().intValue())))
            .andExpect(jsonPath("$.[*].number").value(hasItem(DEFAULT_NUMBER)))
            .andExpect(jsonPath("$.[*].currentSkips").value(hasItem(DEFAULT_CURRENT_SKIPS)))
            .andExpect(jsonPath("$.[*].averageSkips").value(hasItem(DEFAULT_AVERAGE_SKIPS)))
            .andExpect(jsonPath("$.[*].drawsDue").value(hasItem(DEFAULT_DRAWS_DUE)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].ballType").value(hasItem(DEFAULT_BALL_TYPE.toString())))
            .andExpect(jsonPath("$.[*].gameType").value(hasItem(DEFAULT_GAME_TYPE.toString())))
            .andExpect(jsonPath("$.[*].timestamp").value(hasItem(DEFAULT_TIMESTAMP)));
    }

    @Test
    @Transactional
    public void getHitBallsAndStarsPrediction() throws Exception {
        // Initialize the database
        hitBallsAndStarsPredictionRepository.saveAndFlush(hitBallsAndStarsPrediction);

        // Get the hitBallsAndStarsPrediction
        restHitBallsAndStarsPredictionMockMvc.perform(get("/api/hit-balls-and-stars-predictions/{id}", hitBallsAndStarsPrediction.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(hitBallsAndStarsPrediction.getId().intValue()))
            .andExpect(jsonPath("$.number").value(DEFAULT_NUMBER))
            .andExpect(jsonPath("$.currentSkips").value(DEFAULT_CURRENT_SKIPS))
            .andExpect(jsonPath("$.averageSkips").value(DEFAULT_AVERAGE_SKIPS))
            .andExpect(jsonPath("$.drawsDue").value(DEFAULT_DRAWS_DUE))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.ballType").value(DEFAULT_BALL_TYPE.toString()))
            .andExpect(jsonPath("$.gameType").value(DEFAULT_GAME_TYPE.toString()))
            .andExpect(jsonPath("$.timestamp").value(DEFAULT_TIMESTAMP));
    }

    @Test
    @Transactional
    public void getNonExistingHitBallsAndStarsPrediction() throws Exception {
        // Get the hitBallsAndStarsPrediction
        restHitBallsAndStarsPredictionMockMvc.perform(get("/api/hit-balls-and-stars-predictions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateHitBallsAndStarsPrediction() throws Exception {
        // Initialize the database
        hitBallsAndStarsPredictionService.save(hitBallsAndStarsPrediction);

        int databaseSizeBeforeUpdate = hitBallsAndStarsPredictionRepository.findAll().size();

        // Update the hitBallsAndStarsPrediction
        HitBallsAndStarsPrediction updatedHitBallsAndStarsPrediction = hitBallsAndStarsPredictionRepository.findOne(hitBallsAndStarsPrediction.getId());
        updatedHitBallsAndStarsPrediction
                .number(UPDATED_NUMBER)
                .currentSkips(UPDATED_CURRENT_SKIPS)
                .averageSkips(UPDATED_AVERAGE_SKIPS)
                .drawsDue(UPDATED_DRAWS_DUE)
                .status(UPDATED_STATUS)
                .ballType(UPDATED_BALL_TYPE)
                .gameType(UPDATED_GAME_TYPE)
                .timestamp(UPDATED_TIMESTAMP);

        restHitBallsAndStarsPredictionMockMvc.perform(put("/api/hit-balls-and-stars-predictions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedHitBallsAndStarsPrediction)))
            .andExpect(status().isOk());

        // Validate the HitBallsAndStarsPrediction in the database
        List<HitBallsAndStarsPrediction> hitBallsAndStarsPredictionList = hitBallsAndStarsPredictionRepository.findAll();
        assertThat(hitBallsAndStarsPredictionList).hasSize(databaseSizeBeforeUpdate);
        HitBallsAndStarsPrediction testHitBallsAndStarsPrediction = hitBallsAndStarsPredictionList.get(hitBallsAndStarsPredictionList.size() - 1);
        assertThat(testHitBallsAndStarsPrediction.getNumber()).isEqualTo(UPDATED_NUMBER);
        assertThat(testHitBallsAndStarsPrediction.getCurrentSkips()).isEqualTo(UPDATED_CURRENT_SKIPS);
        assertThat(testHitBallsAndStarsPrediction.getAverageSkips()).isEqualTo(UPDATED_AVERAGE_SKIPS);
        assertThat(testHitBallsAndStarsPrediction.getDrawsDue()).isEqualTo(UPDATED_DRAWS_DUE);
        assertThat(testHitBallsAndStarsPrediction.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testHitBallsAndStarsPrediction.getBallType()).isEqualTo(UPDATED_BALL_TYPE);
        assertThat(testHitBallsAndStarsPrediction.getGameType()).isEqualTo(UPDATED_GAME_TYPE);
        assertThat(testHitBallsAndStarsPrediction.getTimestamp()).isEqualTo(UPDATED_TIMESTAMP);

        // Validate the HitBallsAndStarsPrediction in Elasticsearch
        HitBallsAndStarsPrediction hitBallsAndStarsPredictionEs = hitBallsAndStarsPredictionSearchRepository.findOne(testHitBallsAndStarsPrediction.getId());
        assertThat(hitBallsAndStarsPredictionEs).isEqualToComparingFieldByField(testHitBallsAndStarsPrediction);
    }

    @Test
    @Transactional
    public void updateNonExistingHitBallsAndStarsPrediction() throws Exception {
        int databaseSizeBeforeUpdate = hitBallsAndStarsPredictionRepository.findAll().size();

        // Create the HitBallsAndStarsPrediction

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restHitBallsAndStarsPredictionMockMvc.perform(put("/api/hit-balls-and-stars-predictions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(hitBallsAndStarsPrediction)))
            .andExpect(status().isCreated());

        // Validate the HitBallsAndStarsPrediction in the database
        List<HitBallsAndStarsPrediction> hitBallsAndStarsPredictionList = hitBallsAndStarsPredictionRepository.findAll();
        assertThat(hitBallsAndStarsPredictionList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteHitBallsAndStarsPrediction() throws Exception {
        // Initialize the database
        hitBallsAndStarsPredictionService.save(hitBallsAndStarsPrediction);

        int databaseSizeBeforeDelete = hitBallsAndStarsPredictionRepository.findAll().size();

        // Get the hitBallsAndStarsPrediction
        restHitBallsAndStarsPredictionMockMvc.perform(delete("/api/hit-balls-and-stars-predictions/{id}", hitBallsAndStarsPrediction.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean hitBallsAndStarsPredictionExistsInEs = hitBallsAndStarsPredictionSearchRepository.exists(hitBallsAndStarsPrediction.getId());
        assertThat(hitBallsAndStarsPredictionExistsInEs).isFalse();

        // Validate the database is empty
        List<HitBallsAndStarsPrediction> hitBallsAndStarsPredictionList = hitBallsAndStarsPredictionRepository.findAll();
        assertThat(hitBallsAndStarsPredictionList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchHitBallsAndStarsPrediction() throws Exception {
        // Initialize the database
        hitBallsAndStarsPredictionService.save(hitBallsAndStarsPrediction);

        // Search the hitBallsAndStarsPrediction
        restHitBallsAndStarsPredictionMockMvc.perform(get("/api/_search/hit-balls-and-stars-predictions?query=id:" + hitBallsAndStarsPrediction.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(hitBallsAndStarsPrediction.getId().intValue())))
            .andExpect(jsonPath("$.[*].number").value(hasItem(DEFAULT_NUMBER)))
            .andExpect(jsonPath("$.[*].currentSkips").value(hasItem(DEFAULT_CURRENT_SKIPS)))
            .andExpect(jsonPath("$.[*].averageSkips").value(hasItem(DEFAULT_AVERAGE_SKIPS)))
            .andExpect(jsonPath("$.[*].drawsDue").value(hasItem(DEFAULT_DRAWS_DUE)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].ballType").value(hasItem(DEFAULT_BALL_TYPE.toString())))
            .andExpect(jsonPath("$.[*].gameType").value(hasItem(DEFAULT_GAME_TYPE.toString())))
            .andExpect(jsonPath("$.[*].timestamp").value(hasItem(DEFAULT_TIMESTAMP)));
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(HitBallsAndStarsPrediction.class);
    }
}
