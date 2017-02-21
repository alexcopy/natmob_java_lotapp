package ru.m2mcom.natmob.web.rest;

import ru.m2mcom.natmob.LotappApp;

import ru.m2mcom.natmob.domain.Thb_plays;
import ru.m2mcom.natmob.repository.Thb_playsRepository;
import ru.m2mcom.natmob.service.Thb_playsService;
import ru.m2mcom.natmob.repository.search.Thb_playsSearchRepository;

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
 * Test class for the Thb_playsResource REST controller.
 *
 * @see Thb_playsResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = LotappApp.class)
public class Thb_playsResourceIntTest {

    private static final String DEFAULT_DRAW_DATE = "03-e-56";
    private static final String UPDATED_DRAW_DATE = "24-T-40";

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

    private static final Integer DEFAULT_THUNDER_BALL = 1;
    private static final Integer UPDATED_THUNDER_BALL = 2;

    private static final Integer DEFAULT_SUM_B = 1;
    private static final Integer UPDATED_SUM_B = 2;

    private static final DrawType DEFAULT_DRAW_TYPE = DrawType.manual;
    private static final DrawType UPDATED_DRAW_TYPE = DrawType.local;

    private static final String DEFAULT_GAME_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_GAME_TYPE = "BBBBBBBBBB";

    private static final Double DEFAULT_PRIZE = 1D;
    private static final Double UPDATED_PRIZE = 2D;

    private static final Long DEFAULT_RANK_ID = 1L;
    private static final Long UPDATED_RANK_ID = 2L;

    private static final Long DEFAULT_BONUS_RANK_ID = 1L;
    private static final Long UPDATED_BONUS_RANK_ID = 2L;

    private static final Boolean DEFAULT_CHECKED = false;
    private static final Boolean UPDATED_CHECKED = true;

    private static final String DEFAULT_HASH = "AAAAAAAAAA";
    private static final String UPDATED_HASH = "BBBBBBBBBB";

    private static final Integer DEFAULT_TIMESTAMP = 1;
    private static final Integer UPDATED_TIMESTAMP = 2;

    @Autowired
    private Thb_playsRepository thb_playsRepository;

    @Autowired
    private Thb_playsService thb_playsService;

    @Autowired
    private Thb_playsSearchRepository thb_playsSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private EntityManager em;

    private MockMvc restThb_playsMockMvc;

    private Thb_plays thb_plays;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Thb_playsResource thb_playsResource = new Thb_playsResource(thb_playsService);
        this.restThb_playsMockMvc = MockMvcBuilders.standaloneSetup(thb_playsResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Thb_plays createEntity(EntityManager em) {
        Thb_plays thb_plays = new Thb_plays()
                .drawDate(DEFAULT_DRAW_DATE)
                .ball1(DEFAULT_BALL_1)
                .ball2(DEFAULT_BALL_2)
                .ball3(DEFAULT_BALL_3)
                .ball4(DEFAULT_BALL_4)
                .ball5(DEFAULT_BALL_5)
                .thunderBall(DEFAULT_THUNDER_BALL)
                .sumB(DEFAULT_SUM_B)
                .drawType(DEFAULT_DRAW_TYPE)
                .gameType(DEFAULT_GAME_TYPE)
                .prize(DEFAULT_PRIZE)
                .rank_id(DEFAULT_RANK_ID)
                .bonus_rank_id(DEFAULT_BONUS_RANK_ID)
                .checked(DEFAULT_CHECKED)
                .hash(DEFAULT_HASH)
                .timestamp(DEFAULT_TIMESTAMP);
        return thb_plays;
    }

    @Before
    public void initTest() {
        thb_playsSearchRepository.deleteAll();
        thb_plays = createEntity(em);
    }

    @Test
    @Transactional
    public void createThb_plays() throws Exception {
        int databaseSizeBeforeCreate = thb_playsRepository.findAll().size();

        // Create the Thb_plays

        restThb_playsMockMvc.perform(post("/api/thb-plays")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(thb_plays)))
            .andExpect(status().isCreated());

        // Validate the Thb_plays in the database
        List<Thb_plays> thb_playsList = thb_playsRepository.findAll();
        assertThat(thb_playsList).hasSize(databaseSizeBeforeCreate + 1);
        Thb_plays testThb_plays = thb_playsList.get(thb_playsList.size() - 1);
        assertThat(testThb_plays.getDrawDate()).isEqualTo(DEFAULT_DRAW_DATE);
        assertThat(testThb_plays.getBall1()).isEqualTo(DEFAULT_BALL_1);
        assertThat(testThb_plays.getBall2()).isEqualTo(DEFAULT_BALL_2);
        assertThat(testThb_plays.getBall3()).isEqualTo(DEFAULT_BALL_3);
        assertThat(testThb_plays.getBall4()).isEqualTo(DEFAULT_BALL_4);
        assertThat(testThb_plays.getBall5()).isEqualTo(DEFAULT_BALL_5);
        assertThat(testThb_plays.getThunderBall()).isEqualTo(DEFAULT_THUNDER_BALL);
        assertThat(testThb_plays.getSumB()).isEqualTo(DEFAULT_SUM_B);
        assertThat(testThb_plays.getDrawType()).isEqualTo(DEFAULT_DRAW_TYPE);
        assertThat(testThb_plays.getGameType()).isEqualTo(DEFAULT_GAME_TYPE);
        assertThat(testThb_plays.getPrize()).isEqualTo(DEFAULT_PRIZE);
        assertThat(testThb_plays.getRank_id()).isEqualTo(DEFAULT_RANK_ID);
        assertThat(testThb_plays.getBonus_rank_id()).isEqualTo(DEFAULT_BONUS_RANK_ID);
        assertThat(testThb_plays.isChecked()).isEqualTo(DEFAULT_CHECKED);
        assertThat(testThb_plays.getHash()).isEqualTo(DEFAULT_HASH);
        assertThat(testThb_plays.getTimestamp()).isEqualTo(DEFAULT_TIMESTAMP);

        // Validate the Thb_plays in Elasticsearch
        Thb_plays thb_playsEs = thb_playsSearchRepository.findOne(testThb_plays.getId());
        assertThat(thb_playsEs).isEqualToComparingFieldByField(testThb_plays);
    }

    @Test
    @Transactional
    public void createThb_playsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = thb_playsRepository.findAll().size();

        // Create the Thb_plays with an existing ID
        Thb_plays existingThb_plays = new Thb_plays();
        existingThb_plays.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restThb_playsMockMvc.perform(post("/api/thb-plays")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingThb_plays)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Thb_plays> thb_playsList = thb_playsRepository.findAll();
        assertThat(thb_playsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkDrawDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = thb_playsRepository.findAll().size();
        // set the field null
        thb_plays.setDrawDate(null);

        // Create the Thb_plays, which fails.

        restThb_playsMockMvc.perform(post("/api/thb-plays")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(thb_plays)))
            .andExpect(status().isBadRequest());

        List<Thb_plays> thb_playsList = thb_playsRepository.findAll();
        assertThat(thb_playsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkBall1IsRequired() throws Exception {
        int databaseSizeBeforeTest = thb_playsRepository.findAll().size();
        // set the field null
        thb_plays.setBall1(null);

        // Create the Thb_plays, which fails.

        restThb_playsMockMvc.perform(post("/api/thb-plays")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(thb_plays)))
            .andExpect(status().isBadRequest());

        List<Thb_plays> thb_playsList = thb_playsRepository.findAll();
        assertThat(thb_playsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkBall2IsRequired() throws Exception {
        int databaseSizeBeforeTest = thb_playsRepository.findAll().size();
        // set the field null
        thb_plays.setBall2(null);

        // Create the Thb_plays, which fails.

        restThb_playsMockMvc.perform(post("/api/thb-plays")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(thb_plays)))
            .andExpect(status().isBadRequest());

        List<Thb_plays> thb_playsList = thb_playsRepository.findAll();
        assertThat(thb_playsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkBall3IsRequired() throws Exception {
        int databaseSizeBeforeTest = thb_playsRepository.findAll().size();
        // set the field null
        thb_plays.setBall3(null);

        // Create the Thb_plays, which fails.

        restThb_playsMockMvc.perform(post("/api/thb-plays")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(thb_plays)))
            .andExpect(status().isBadRequest());

        List<Thb_plays> thb_playsList = thb_playsRepository.findAll();
        assertThat(thb_playsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkBall4IsRequired() throws Exception {
        int databaseSizeBeforeTest = thb_playsRepository.findAll().size();
        // set the field null
        thb_plays.setBall4(null);

        // Create the Thb_plays, which fails.

        restThb_playsMockMvc.perform(post("/api/thb-plays")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(thb_plays)))
            .andExpect(status().isBadRequest());

        List<Thb_plays> thb_playsList = thb_playsRepository.findAll();
        assertThat(thb_playsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkBall5IsRequired() throws Exception {
        int databaseSizeBeforeTest = thb_playsRepository.findAll().size();
        // set the field null
        thb_plays.setBall5(null);

        // Create the Thb_plays, which fails.

        restThb_playsMockMvc.perform(post("/api/thb-plays")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(thb_plays)))
            .andExpect(status().isBadRequest());

        List<Thb_plays> thb_playsList = thb_playsRepository.findAll();
        assertThat(thb_playsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkThunderBallIsRequired() throws Exception {
        int databaseSizeBeforeTest = thb_playsRepository.findAll().size();
        // set the field null
        thb_plays.setThunderBall(null);

        // Create the Thb_plays, which fails.

        restThb_playsMockMvc.perform(post("/api/thb-plays")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(thb_plays)))
            .andExpect(status().isBadRequest());

        List<Thb_plays> thb_playsList = thb_playsRepository.findAll();
        assertThat(thb_playsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDrawTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = thb_playsRepository.findAll().size();
        // set the field null
        thb_plays.setDrawType(null);

        // Create the Thb_plays, which fails.

        restThb_playsMockMvc.perform(post("/api/thb-plays")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(thb_plays)))
            .andExpect(status().isBadRequest());

        List<Thb_plays> thb_playsList = thb_playsRepository.findAll();
        assertThat(thb_playsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkGameTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = thb_playsRepository.findAll().size();
        // set the field null
        thb_plays.setGameType(null);

        // Create the Thb_plays, which fails.

        restThb_playsMockMvc.perform(post("/api/thb-plays")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(thb_plays)))
            .andExpect(status().isBadRequest());

        List<Thb_plays> thb_playsList = thb_playsRepository.findAll();
        assertThat(thb_playsList).hasSize(databaseSizeBeforeTest);
    }


    @Test
    @Transactional
    public void checkHashIsRequired() throws Exception {
        int databaseSizeBeforeTest = thb_playsRepository.findAll().size();
        // set the field null
        thb_plays.setHash(null);

        // Create the Thb_plays, which fails.

        restThb_playsMockMvc.perform(post("/api/thb-plays")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(thb_plays)))
            .andExpect(status().isBadRequest());

        List<Thb_plays> thb_playsList = thb_playsRepository.findAll();
        assertThat(thb_playsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllThb_plays() throws Exception {
        // Initialize the database
        thb_playsRepository.saveAndFlush(thb_plays);

        // Get all the thb_playsList
        restThb_playsMockMvc.perform(get("/api/thb-plays?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(thb_plays.getId().intValue())))
            .andExpect(jsonPath("$.[*].drawDate").value(hasItem(DEFAULT_DRAW_DATE.toString())))
            .andExpect(jsonPath("$.[*].ball1").value(hasItem(DEFAULT_BALL_1)))
            .andExpect(jsonPath("$.[*].ball2").value(hasItem(DEFAULT_BALL_2)))
            .andExpect(jsonPath("$.[*].ball3").value(hasItem(DEFAULT_BALL_3)))
            .andExpect(jsonPath("$.[*].ball4").value(hasItem(DEFAULT_BALL_4)))
            .andExpect(jsonPath("$.[*].ball5").value(hasItem(DEFAULT_BALL_5)))
            .andExpect(jsonPath("$.[*].thunderBall").value(hasItem(DEFAULT_THUNDER_BALL)))
            .andExpect(jsonPath("$.[*].sumB").value(hasItem(DEFAULT_SUM_B)))
            .andExpect(jsonPath("$.[*].drawType").value(hasItem(DEFAULT_DRAW_TYPE.toString())))
            .andExpect(jsonPath("$.[*].gameType").value(hasItem(DEFAULT_GAME_TYPE.toString())))
            .andExpect(jsonPath("$.[*].prize").value(hasItem(DEFAULT_PRIZE.doubleValue())))
            .andExpect(jsonPath("$.[*].rank_id").value(hasItem(DEFAULT_RANK_ID.intValue())))
            .andExpect(jsonPath("$.[*].bonus_rank_id").value(hasItem(DEFAULT_BONUS_RANK_ID.intValue())))
            .andExpect(jsonPath("$.[*].checked").value(hasItem(DEFAULT_CHECKED.booleanValue())))
            .andExpect(jsonPath("$.[*].hash").value(hasItem(DEFAULT_HASH.toString())))
            .andExpect(jsonPath("$.[*].timestamp").value(hasItem(DEFAULT_TIMESTAMP)));
    }

    @Test
    @Transactional
    public void getThb_plays() throws Exception {
        // Initialize the database
        thb_playsRepository.saveAndFlush(thb_plays);

        // Get the thb_plays
        restThb_playsMockMvc.perform(get("/api/thb-plays/{id}", thb_plays.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(thb_plays.getId().intValue()))
            .andExpect(jsonPath("$.drawDate").value(DEFAULT_DRAW_DATE.toString()))
            .andExpect(jsonPath("$.ball1").value(DEFAULT_BALL_1))
            .andExpect(jsonPath("$.ball2").value(DEFAULT_BALL_2))
            .andExpect(jsonPath("$.ball3").value(DEFAULT_BALL_3))
            .andExpect(jsonPath("$.ball4").value(DEFAULT_BALL_4))
            .andExpect(jsonPath("$.ball5").value(DEFAULT_BALL_5))
            .andExpect(jsonPath("$.thunderBall").value(DEFAULT_THUNDER_BALL))
            .andExpect(jsonPath("$.sumB").value(DEFAULT_SUM_B))
            .andExpect(jsonPath("$.drawType").value(DEFAULT_DRAW_TYPE.toString()))
            .andExpect(jsonPath("$.gameType").value(DEFAULT_GAME_TYPE.toString()))
            .andExpect(jsonPath("$.prize").value(DEFAULT_PRIZE.doubleValue()))
            .andExpect(jsonPath("$.rank_id").value(DEFAULT_RANK_ID.intValue()))
            .andExpect(jsonPath("$.bonus_rank_id").value(DEFAULT_BONUS_RANK_ID.intValue()))
            .andExpect(jsonPath("$.checked").value(DEFAULT_CHECKED.booleanValue()))
            .andExpect(jsonPath("$.hash").value(DEFAULT_HASH.toString()))
            .andExpect(jsonPath("$.timestamp").value(DEFAULT_TIMESTAMP));
    }

    @Test
    @Transactional
    public void getNonExistingThb_plays() throws Exception {
        // Get the thb_plays
        restThb_playsMockMvc.perform(get("/api/thb-plays/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateThb_plays() throws Exception {
        // Initialize the database
        thb_playsService.save(thb_plays);

        int databaseSizeBeforeUpdate = thb_playsRepository.findAll().size();

        // Update the thb_plays
        Thb_plays updatedThb_plays = thb_playsRepository.findOne(thb_plays.getId());
        updatedThb_plays
                .drawDate(UPDATED_DRAW_DATE)
                .ball1(UPDATED_BALL_1)
                .ball2(UPDATED_BALL_2)
                .ball3(UPDATED_BALL_3)
                .ball4(UPDATED_BALL_4)
                .ball5(UPDATED_BALL_5)
                .thunderBall(UPDATED_THUNDER_BALL)
                .sumB(UPDATED_SUM_B)
                .drawType(UPDATED_DRAW_TYPE)
                .gameType(UPDATED_GAME_TYPE)
                .prize(UPDATED_PRIZE)
                .rank_id(UPDATED_RANK_ID)
                .bonus_rank_id(UPDATED_BONUS_RANK_ID)
                .checked(UPDATED_CHECKED)
                .hash(UPDATED_HASH)
                .timestamp(UPDATED_TIMESTAMP);

        restThb_playsMockMvc.perform(put("/api/thb-plays")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedThb_plays)))
            .andExpect(status().isOk());

        // Validate the Thb_plays in the database
        List<Thb_plays> thb_playsList = thb_playsRepository.findAll();
        assertThat(thb_playsList).hasSize(databaseSizeBeforeUpdate);
        Thb_plays testThb_plays = thb_playsList.get(thb_playsList.size() - 1);
        assertThat(testThb_plays.getDrawDate()).isEqualTo(UPDATED_DRAW_DATE);
        assertThat(testThb_plays.getBall1()).isEqualTo(UPDATED_BALL_1);
        assertThat(testThb_plays.getBall2()).isEqualTo(UPDATED_BALL_2);
        assertThat(testThb_plays.getBall3()).isEqualTo(UPDATED_BALL_3);
        assertThat(testThb_plays.getBall4()).isEqualTo(UPDATED_BALL_4);
        assertThat(testThb_plays.getBall5()).isEqualTo(UPDATED_BALL_5);
        assertThat(testThb_plays.getThunderBall()).isEqualTo(UPDATED_THUNDER_BALL);
        assertThat(testThb_plays.getSumB()).isEqualTo(UPDATED_SUM_B);
        assertThat(testThb_plays.getDrawType()).isEqualTo(UPDATED_DRAW_TYPE);
        assertThat(testThb_plays.getGameType()).isEqualTo(UPDATED_GAME_TYPE);
        assertThat(testThb_plays.getPrize()).isEqualTo(UPDATED_PRIZE);
        assertThat(testThb_plays.getRank_id()).isEqualTo(UPDATED_RANK_ID);
        assertThat(testThb_plays.getBonus_rank_id()).isEqualTo(UPDATED_BONUS_RANK_ID);
        assertThat(testThb_plays.isChecked()).isEqualTo(UPDATED_CHECKED);
        assertThat(testThb_plays.getHash()).isEqualTo(UPDATED_HASH);
        assertThat(testThb_plays.getTimestamp()).isEqualTo(UPDATED_TIMESTAMP);

        // Validate the Thb_plays in Elasticsearch
        Thb_plays thb_playsEs = thb_playsSearchRepository.findOne(testThb_plays.getId());
        assertThat(thb_playsEs).isEqualToComparingFieldByField(testThb_plays);
    }

    @Test
    @Transactional
    public void updateNonExistingThb_plays() throws Exception {
        int databaseSizeBeforeUpdate = thb_playsRepository.findAll().size();

        // Create the Thb_plays

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restThb_playsMockMvc.perform(put("/api/thb-plays")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(thb_plays)))
            .andExpect(status().isCreated());

        // Validate the Thb_plays in the database
        List<Thb_plays> thb_playsList = thb_playsRepository.findAll();
        assertThat(thb_playsList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteThb_plays() throws Exception {
        // Initialize the database
        thb_playsService.save(thb_plays);

        int databaseSizeBeforeDelete = thb_playsRepository.findAll().size();

        // Get the thb_plays
        restThb_playsMockMvc.perform(delete("/api/thb-plays/{id}", thb_plays.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean thb_playsExistsInEs = thb_playsSearchRepository.exists(thb_plays.getId());
        assertThat(thb_playsExistsInEs).isFalse();

        // Validate the database is empty
        List<Thb_plays> thb_playsList = thb_playsRepository.findAll();
        assertThat(thb_playsList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchThb_plays() throws Exception {
        // Initialize the database
        thb_playsService.save(thb_plays);

        // Search the thb_plays
        restThb_playsMockMvc.perform(get("/api/_search/thb-plays?query=id:" + thb_plays.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(thb_plays.getId().intValue())))
            .andExpect(jsonPath("$.[*].drawDate").value(hasItem(DEFAULT_DRAW_DATE.toString())))
            .andExpect(jsonPath("$.[*].ball1").value(hasItem(DEFAULT_BALL_1)))
            .andExpect(jsonPath("$.[*].ball2").value(hasItem(DEFAULT_BALL_2)))
            .andExpect(jsonPath("$.[*].ball3").value(hasItem(DEFAULT_BALL_3)))
            .andExpect(jsonPath("$.[*].ball4").value(hasItem(DEFAULT_BALL_4)))
            .andExpect(jsonPath("$.[*].ball5").value(hasItem(DEFAULT_BALL_5)))
            .andExpect(jsonPath("$.[*].thunderBall").value(hasItem(DEFAULT_THUNDER_BALL)))
            .andExpect(jsonPath("$.[*].sumB").value(hasItem(DEFAULT_SUM_B)))
            .andExpect(jsonPath("$.[*].drawType").value(hasItem(DEFAULT_DRAW_TYPE.toString())))
            .andExpect(jsonPath("$.[*].gameType").value(hasItem(DEFAULT_GAME_TYPE.toString())))
            .andExpect(jsonPath("$.[*].prize").value(hasItem(DEFAULT_PRIZE.doubleValue())))
            .andExpect(jsonPath("$.[*].rank_id").value(hasItem(DEFAULT_RANK_ID.intValue())))
            .andExpect(jsonPath("$.[*].bonus_rank_id").value(hasItem(DEFAULT_BONUS_RANK_ID.intValue())))
            .andExpect(jsonPath("$.[*].checked").value(hasItem(DEFAULT_CHECKED.booleanValue())))
            .andExpect(jsonPath("$.[*].hash").value(hasItem(DEFAULT_HASH.toString())))
            .andExpect(jsonPath("$.[*].timestamp").value(hasItem(DEFAULT_TIMESTAMP)));
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Thb_plays.class);
    }
}
