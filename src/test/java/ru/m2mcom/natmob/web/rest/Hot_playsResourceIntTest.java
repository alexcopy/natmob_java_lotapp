package ru.m2mcom.natmob.web.rest;

import ru.m2mcom.natmob.LotappApp;

import ru.m2mcom.natmob.domain.Hot_plays;
import ru.m2mcom.natmob.repository.Hot_playsRepository;
import ru.m2mcom.natmob.service.Hot_playsService;
import ru.m2mcom.natmob.repository.search.Hot_playsSearchRepository;

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

import ru.m2mcom.natmob.domain.enumeration.DrawType;
/**
 * Test class for the Hot_playsResource REST controller.
 *
 * @see Hot_playsResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = LotappApp.class)
public class Hot_playsResourceIntTest {

    private static final String DEFAULT_DRAW_DATE = "03-W1-18";
    private static final String UPDATED_DRAW_DATE = "21-p-98";

    private static final Integer DEFAULT_BALL_1 = 1;
    private static final Integer UPDATED_BALL_1 = 2;

    private static final Integer DEFAULT_BALL_2 = 1;
    private static final Integer UPDATED_BALL_2 = 2;

    private static final Integer DEFAULT_BALL_3 = 1;
    private static final Integer UPDATED_BALL_3 = 2;

    private static final Integer DEFAULT_BALL_4 = 1;
    private static final Integer UPDATED_BALL_4 = 2;

    private static final Integer DEFAULT_BALL_5 = 1;
    private static final Integer UPDATED_BALL_5 = 2;

    private static final DrawType DEFAULT_DRAW_TYPE = DrawType.local;
    private static final DrawType UPDATED_DRAW_TYPE = DrawType.manual;

    private static final String DEFAULT_GAME_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_GAME_TYPE = "BBBBBBBBBB";

    private static final Double DEFAULT_PRIZE = 1D;
    private static final Double UPDATED_PRIZE = 2D;

    private static final Boolean DEFAULT_CHECKED = false;
    private static final Boolean UPDATED_CHECKED = true;

    private static final Integer DEFAULT_RANK_ID = 1;
    private static final Integer UPDATED_RANK_ID = 2;

    private static final Integer DEFAULT_SUM_B = 1;
    private static final Integer UPDATED_SUM_B = 2;

    private static final String DEFAULT_HASH = "AAAAAAAAAA";
    private static final String UPDATED_HASH = "BBBBBBBBBB";

    private static final Integer DEFAULT_TIMESTAMP = 1;
    private static final Integer UPDATED_TIMESTAMP = 2;

    @Autowired
    private Hot_playsRepository hot_playsRepository;

    @Autowired
    private Hot_playsService hot_playsService;

    @Autowired
    private Hot_playsSearchRepository hot_playsSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private EntityManager em;

    private MockMvc restHot_playsMockMvc;

    private Hot_plays hot_plays;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Hot_playsResource hot_playsResource = new Hot_playsResource(hot_playsService);
        this.restHot_playsMockMvc = MockMvcBuilders.standaloneSetup(hot_playsResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Hot_plays createEntity(EntityManager em) {
        Hot_plays hot_plays = new Hot_plays()
                .drawDate(DEFAULT_DRAW_DATE)
                .ball1(DEFAULT_BALL_1)
                .ball2(DEFAULT_BALL_2)
                .ball3(DEFAULT_BALL_3)
                .ball4(DEFAULT_BALL_4)
                .ball5(DEFAULT_BALL_5)
                .drawType(DEFAULT_DRAW_TYPE)
                .gameType(DEFAULT_GAME_TYPE)
                .prize(DEFAULT_PRIZE)
                .checked(DEFAULT_CHECKED)
                .rank_id(DEFAULT_RANK_ID)
                .sumB(DEFAULT_SUM_B)
                .hash(DEFAULT_HASH)
                .timestamp(DEFAULT_TIMESTAMP);
        return hot_plays;
    }

    @Before
    public void initTest() {
        hot_playsSearchRepository.deleteAll();
        hot_plays = createEntity(em);
    }

    @Test
    @Transactional
    public void createHot_plays() throws Exception {
        int databaseSizeBeforeCreate = hot_playsRepository.findAll().size();

        // Create the Hot_plays

        restHot_playsMockMvc.perform(post("/api/hot-plays")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(hot_plays)))
            .andExpect(status().isCreated());

        // Validate the Hot_plays in the database
        List<Hot_plays> hot_playsList = hot_playsRepository.findAll();
        assertThat(hot_playsList).hasSize(databaseSizeBeforeCreate + 1);
        Hot_plays testHot_plays = hot_playsList.get(hot_playsList.size() - 1);
        assertThat(testHot_plays.getDrawDate()).isEqualTo(DEFAULT_DRAW_DATE);
        assertThat(testHot_plays.getBall1()).isEqualTo(DEFAULT_BALL_1);
        assertThat(testHot_plays.getBall2()).isEqualTo(DEFAULT_BALL_2);
        assertThat(testHot_plays.getBall3()).isEqualTo(DEFAULT_BALL_3);
        assertThat(testHot_plays.getBall4()).isEqualTo(DEFAULT_BALL_4);
        assertThat(testHot_plays.getBall5()).isEqualTo(DEFAULT_BALL_5);
        assertThat(testHot_plays.getDrawType()).isEqualTo(DEFAULT_DRAW_TYPE);
        assertThat(testHot_plays.getGameType()).isEqualTo(DEFAULT_GAME_TYPE);
        assertThat(testHot_plays.getPrize()).isEqualTo(DEFAULT_PRIZE);
        assertThat(testHot_plays.isChecked()).isEqualTo(DEFAULT_CHECKED);
        assertThat(testHot_plays.getRank_id()).isEqualTo(DEFAULT_RANK_ID);
        assertThat(testHot_plays.getSumB()).isEqualTo(DEFAULT_SUM_B);
        assertThat(testHot_plays.getHash()).isEqualTo(DEFAULT_HASH);
        assertThat(testHot_plays.getTimestamp()).isEqualTo(DEFAULT_TIMESTAMP);

        // Validate the Hot_plays in Elasticsearch
        Hot_plays hot_playsEs = hot_playsSearchRepository.findOne(testHot_plays.getId());
        assertThat(hot_playsEs).isEqualToComparingFieldByField(testHot_plays);
    }

    @Test
    @Transactional
    public void createHot_playsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = hot_playsRepository.findAll().size();

        // Create the Hot_plays with an existing ID
        Hot_plays existingHot_plays = new Hot_plays();
        existingHot_plays.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restHot_playsMockMvc.perform(post("/api/hot-plays")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingHot_plays)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Hot_plays> hot_playsList = hot_playsRepository.findAll();
        assertThat(hot_playsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkDrawDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = hot_playsRepository.findAll().size();
        // set the field null
        hot_plays.setDrawDate(null);

        // Create the Hot_plays, which fails.

        restHot_playsMockMvc.perform(post("/api/hot-plays")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(hot_plays)))
            .andExpect(status().isBadRequest());

        List<Hot_plays> hot_playsList = hot_playsRepository.findAll();
        assertThat(hot_playsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkBall1IsRequired() throws Exception {
        int databaseSizeBeforeTest = hot_playsRepository.findAll().size();
        // set the field null
        hot_plays.setBall1(null);

        // Create the Hot_plays, which fails.

        restHot_playsMockMvc.perform(post("/api/hot-plays")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(hot_plays)))
            .andExpect(status().isBadRequest());

        List<Hot_plays> hot_playsList = hot_playsRepository.findAll();
        assertThat(hot_playsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkSumBIsRequired() throws Exception {
        int databaseSizeBeforeTest = hot_playsRepository.findAll().size();
        // set the field null
        hot_plays.setSumB(null);

        // Create the Hot_plays, which fails.

        restHot_playsMockMvc.perform(post("/api/hot-plays")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(hot_plays)))
            .andExpect(status().isBadRequest());

        List<Hot_plays> hot_playsList = hot_playsRepository.findAll();
        assertThat(hot_playsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTimestampIsRequired() throws Exception {
        int databaseSizeBeforeTest = hot_playsRepository.findAll().size();
        // set the field null
        hot_plays.setTimestamp(null);

        // Create the Hot_plays, which fails.

        restHot_playsMockMvc.perform(post("/api/hot-plays")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(hot_plays)))
            .andExpect(status().isBadRequest());

        List<Hot_plays> hot_playsList = hot_playsRepository.findAll();
        assertThat(hot_playsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllHot_plays() throws Exception {
        // Initialize the database
        hot_playsRepository.saveAndFlush(hot_plays);

        // Get all the hot_playsList
        restHot_playsMockMvc.perform(get("/api/hot-plays?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(hot_plays.getId().intValue())))
            .andExpect(jsonPath("$.[*].drawDate").value(hasItem(DEFAULT_DRAW_DATE.toString())))
            .andExpect(jsonPath("$.[*].ball1").value(hasItem(DEFAULT_BALL_1)))
            .andExpect(jsonPath("$.[*].ball2").value(hasItem(DEFAULT_BALL_2)))
            .andExpect(jsonPath("$.[*].ball3").value(hasItem(DEFAULT_BALL_3)))
            .andExpect(jsonPath("$.[*].ball4").value(hasItem(DEFAULT_BALL_4)))
            .andExpect(jsonPath("$.[*].ball5").value(hasItem(DEFAULT_BALL_5)))
            .andExpect(jsonPath("$.[*].drawType").value(hasItem(DEFAULT_DRAW_TYPE.toString())))
            .andExpect(jsonPath("$.[*].gameType").value(hasItem(DEFAULT_GAME_TYPE.toString())))
            .andExpect(jsonPath("$.[*].prize").value(hasItem(DEFAULT_PRIZE.doubleValue())))
            .andExpect(jsonPath("$.[*].checked").value(hasItem(DEFAULT_CHECKED.booleanValue())))
            .andExpect(jsonPath("$.[*].rank_id").value(hasItem(DEFAULT_RANK_ID)))
            .andExpect(jsonPath("$.[*].sumB").value(hasItem(DEFAULT_SUM_B)))
            .andExpect(jsonPath("$.[*].hash").value(hasItem(DEFAULT_HASH.toString())))
            .andExpect(jsonPath("$.[*].timestamp").value(hasItem(DEFAULT_TIMESTAMP)));
    }

    @Test
    @Transactional
    public void getHot_plays() throws Exception {
        // Initialize the database
        hot_playsRepository.saveAndFlush(hot_plays);

        // Get the hot_plays
        restHot_playsMockMvc.perform(get("/api/hot-plays/{id}", hot_plays.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(hot_plays.getId().intValue()))
            .andExpect(jsonPath("$.drawDate").value(DEFAULT_DRAW_DATE.toString()))
            .andExpect(jsonPath("$.ball1").value(DEFAULT_BALL_1))
            .andExpect(jsonPath("$.ball2").value(DEFAULT_BALL_2))
            .andExpect(jsonPath("$.ball3").value(DEFAULT_BALL_3))
            .andExpect(jsonPath("$.ball4").value(DEFAULT_BALL_4))
            .andExpect(jsonPath("$.ball5").value(DEFAULT_BALL_5))
            .andExpect(jsonPath("$.drawType").value(DEFAULT_DRAW_TYPE.toString()))
            .andExpect(jsonPath("$.gameType").value(DEFAULT_GAME_TYPE.toString()))
            .andExpect(jsonPath("$.prize").value(DEFAULT_PRIZE.doubleValue()))
            .andExpect(jsonPath("$.checked").value(DEFAULT_CHECKED.booleanValue()))
            .andExpect(jsonPath("$.rank_id").value(DEFAULT_RANK_ID))
            .andExpect(jsonPath("$.sumB").value(DEFAULT_SUM_B))
            .andExpect(jsonPath("$.hash").value(DEFAULT_HASH.toString()))
            .andExpect(jsonPath("$.timestamp").value(DEFAULT_TIMESTAMP));
    }

    @Test
    @Transactional
    public void getNonExistingHot_plays() throws Exception {
        // Get the hot_plays
        restHot_playsMockMvc.perform(get("/api/hot-plays/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateHot_plays() throws Exception {
        // Initialize the database
        hot_playsService.save(hot_plays);

        int databaseSizeBeforeUpdate = hot_playsRepository.findAll().size();

        // Update the hot_plays
        Hot_plays updatedHot_plays = hot_playsRepository.findOne(hot_plays.getId());
        updatedHot_plays
                .drawDate(UPDATED_DRAW_DATE)
                .ball1(UPDATED_BALL_1)
                .ball2(UPDATED_BALL_2)
                .ball3(UPDATED_BALL_3)
                .ball4(UPDATED_BALL_4)
                .ball5(UPDATED_BALL_5)
                .drawType(UPDATED_DRAW_TYPE)
                .gameType(UPDATED_GAME_TYPE)
                .prize(UPDATED_PRIZE)
                .checked(UPDATED_CHECKED)
                .rank_id(UPDATED_RANK_ID)
                .sumB(UPDATED_SUM_B)
                .hash(UPDATED_HASH)
                .timestamp(UPDATED_TIMESTAMP);

        restHot_playsMockMvc.perform(put("/api/hot-plays")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedHot_plays)))
            .andExpect(status().isOk());

        // Validate the Hot_plays in the database
        List<Hot_plays> hot_playsList = hot_playsRepository.findAll();
        assertThat(hot_playsList).hasSize(databaseSizeBeforeUpdate);
        Hot_plays testHot_plays = hot_playsList.get(hot_playsList.size() - 1);
        assertThat(testHot_plays.getDrawDate()).isEqualTo(UPDATED_DRAW_DATE);
        assertThat(testHot_plays.getBall1()).isEqualTo(UPDATED_BALL_1);
        assertThat(testHot_plays.getBall2()).isEqualTo(UPDATED_BALL_2);
        assertThat(testHot_plays.getBall3()).isEqualTo(UPDATED_BALL_3);
        assertThat(testHot_plays.getBall4()).isEqualTo(UPDATED_BALL_4);
        assertThat(testHot_plays.getBall5()).isEqualTo(UPDATED_BALL_5);
        assertThat(testHot_plays.getDrawType()).isEqualTo(UPDATED_DRAW_TYPE);
        assertThat(testHot_plays.getGameType()).isEqualTo(UPDATED_GAME_TYPE);
        assertThat(testHot_plays.getPrize()).isEqualTo(UPDATED_PRIZE);
        assertThat(testHot_plays.isChecked()).isEqualTo(UPDATED_CHECKED);
        assertThat(testHot_plays.getRank_id()).isEqualTo(UPDATED_RANK_ID);
        assertThat(testHot_plays.getSumB()).isEqualTo(UPDATED_SUM_B);
        assertThat(testHot_plays.getHash()).isEqualTo(UPDATED_HASH);
        assertThat(testHot_plays.getTimestamp()).isEqualTo(UPDATED_TIMESTAMP);

        // Validate the Hot_plays in Elasticsearch
        Hot_plays hot_playsEs = hot_playsSearchRepository.findOne(testHot_plays.getId());
        assertThat(hot_playsEs).isEqualToComparingFieldByField(testHot_plays);
    }

    @Test
    @Transactional
    public void updateNonExistingHot_plays() throws Exception {
        int databaseSizeBeforeUpdate = hot_playsRepository.findAll().size();

        // Create the Hot_plays

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restHot_playsMockMvc.perform(put("/api/hot-plays")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(hot_plays)))
            .andExpect(status().isCreated());

        // Validate the Hot_plays in the database
        List<Hot_plays> hot_playsList = hot_playsRepository.findAll();
        assertThat(hot_playsList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteHot_plays() throws Exception {
        // Initialize the database
        hot_playsService.save(hot_plays);

        int databaseSizeBeforeDelete = hot_playsRepository.findAll().size();

        // Get the hot_plays
        restHot_playsMockMvc.perform(delete("/api/hot-plays/{id}", hot_plays.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean hot_playsExistsInEs = hot_playsSearchRepository.exists(hot_plays.getId());
        assertThat(hot_playsExistsInEs).isFalse();

        // Validate the database is empty
        List<Hot_plays> hot_playsList = hot_playsRepository.findAll();
        assertThat(hot_playsList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchHot_plays() throws Exception {
        // Initialize the database
        hot_playsService.save(hot_plays);

        // Search the hot_plays
        restHot_playsMockMvc.perform(get("/api/_search/hot-plays?query=id:" + hot_plays.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(hot_plays.getId().intValue())))
            .andExpect(jsonPath("$.[*].drawDate").value(hasItem(DEFAULT_DRAW_DATE.toString())))
            .andExpect(jsonPath("$.[*].ball1").value(hasItem(DEFAULT_BALL_1)))
            .andExpect(jsonPath("$.[*].ball2").value(hasItem(DEFAULT_BALL_2)))
            .andExpect(jsonPath("$.[*].ball3").value(hasItem(DEFAULT_BALL_3)))
            .andExpect(jsonPath("$.[*].ball4").value(hasItem(DEFAULT_BALL_4)))
            .andExpect(jsonPath("$.[*].ball5").value(hasItem(DEFAULT_BALL_5)))
            .andExpect(jsonPath("$.[*].drawType").value(hasItem(DEFAULT_DRAW_TYPE.toString())))
            .andExpect(jsonPath("$.[*].gameType").value(hasItem(DEFAULT_GAME_TYPE.toString())))
            .andExpect(jsonPath("$.[*].prize").value(hasItem(DEFAULT_PRIZE.doubleValue())))
            .andExpect(jsonPath("$.[*].checked").value(hasItem(DEFAULT_CHECKED.booleanValue())))
            .andExpect(jsonPath("$.[*].rank_id").value(hasItem(DEFAULT_RANK_ID)))
            .andExpect(jsonPath("$.[*].sumB").value(hasItem(DEFAULT_SUM_B)))
            .andExpect(jsonPath("$.[*].hash").value(hasItem(DEFAULT_HASH.toString())))
            .andExpect(jsonPath("$.[*].timestamp").value(hasItem(DEFAULT_TIMESTAMP)));
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Hot_plays.class);
    }
}
