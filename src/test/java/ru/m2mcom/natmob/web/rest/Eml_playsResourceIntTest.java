package ru.m2mcom.natmob.web.rest;

import ru.m2mcom.natmob.LotappApp;

import ru.m2mcom.natmob.domain.Eml_plays;
import ru.m2mcom.natmob.repository.Eml_playsRepository;
import ru.m2mcom.natmob.service.Eml_playsService;
import ru.m2mcom.natmob.repository.search.Eml_playsSearchRepository;

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
 * Test class for the Eml_playsResource REST controller.
 *
 * @see Eml_playsResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = LotappApp.class)
public class Eml_playsResourceIntTest {

    private static final String DEFAULT_DRAW_DATE = "78-X9-83";
    private static final String UPDATED_DRAW_DATE = "29-J-89";

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

    private static final Integer DEFAULT_LUCKY_STAR_1 = 1;
    private static final Integer UPDATED_LUCKY_STAR_1 = 2;

    private static final Integer DEFAULT_LUCKY_STAR_2 = 1;
    private static final Integer UPDATED_LUCKY_STAR_2 = 2;

    private static final DrawType DEFAULT_DRAW_TYPE = DrawType.local;
    private static final DrawType UPDATED_DRAW_TYPE = DrawType.manual;

    private static final String DEFAULT_GAME_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_GAME_TYPE = "BBBBBBBBBB";

    private static final Integer DEFAULT_SUM_B = 1;
    private static final Integer UPDATED_SUM_B = 2;

    private static final String DEFAULT_SUM_S = "AAAAAAAAAA";
    private static final String UPDATED_SUM_S = "BBBBBBBBBB";

    private static final Double DEFAULT_PRIZE = 1D;
    private static final Double UPDATED_PRIZE = 2D;

    private static final Integer DEFAULT_RANK_ID = 1;
    private static final Integer UPDATED_RANK_ID = 2;

    private static final Integer DEFAULT_BONUS_RANK_ID = 1;
    private static final Integer UPDATED_BONUS_RANK_ID = 2;

    private static final Boolean DEFAULT_CHECKED = false;
    private static final Boolean UPDATED_CHECKED = true;

    private static final String DEFAULT_HASH = "AAAAAAAAAA";
    private static final String UPDATED_HASH = "BBBBBBBBBB";

    @Autowired
    private Eml_playsRepository eml_playsRepository;

    @Autowired
    private Eml_playsService eml_playsService;

    @Autowired
    private Eml_playsSearchRepository eml_playsSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private EntityManager em;

    private MockMvc restEml_playsMockMvc;

    private Eml_plays eml_plays;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Eml_playsResource eml_playsResource = new Eml_playsResource(eml_playsService);
        this.restEml_playsMockMvc = MockMvcBuilders.standaloneSetup(eml_playsResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Eml_plays createEntity(EntityManager em) {
        Eml_plays eml_plays = new Eml_plays()
                .drawDate(DEFAULT_DRAW_DATE)
                .ball1(DEFAULT_BALL_1)
                .ball2(DEFAULT_BALL_2)
                .ball3(DEFAULT_BALL_3)
                .ball4(DEFAULT_BALL_4)
                .ball5(DEFAULT_BALL_5)
                .luckyStar1(DEFAULT_LUCKY_STAR_1)
                .luckyStar2(DEFAULT_LUCKY_STAR_2)
                .drawType(DEFAULT_DRAW_TYPE)
                .gameType(DEFAULT_GAME_TYPE)
                .sumB(DEFAULT_SUM_B)
                .sumS(DEFAULT_SUM_S)
                .prize(DEFAULT_PRIZE)
                .rank_id(DEFAULT_RANK_ID)
                .bonus_rank_id(DEFAULT_BONUS_RANK_ID)
                .checked(DEFAULT_CHECKED)
                .hash(DEFAULT_HASH);
        return eml_plays;
    }

    @Before
    public void initTest() {
        eml_playsSearchRepository.deleteAll();
        eml_plays = createEntity(em);
    }

    @Test
    @Transactional
    public void createEml_plays() throws Exception {
        int databaseSizeBeforeCreate = eml_playsRepository.findAll().size();

        // Create the Eml_plays

        restEml_playsMockMvc.perform(post("/api/eml-plays")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(eml_plays)))
            .andExpect(status().isCreated());

        // Validate the Eml_plays in the database
        List<Eml_plays> eml_playsList = eml_playsRepository.findAll();
        assertThat(eml_playsList).hasSize(databaseSizeBeforeCreate + 1);
        Eml_plays testEml_plays = eml_playsList.get(eml_playsList.size() - 1);
        assertThat(testEml_plays.getDrawDate()).isEqualTo(DEFAULT_DRAW_DATE);
        assertThat(testEml_plays.getBall1()).isEqualTo(DEFAULT_BALL_1);
        assertThat(testEml_plays.getBall2()).isEqualTo(DEFAULT_BALL_2);
        assertThat(testEml_plays.getBall3()).isEqualTo(DEFAULT_BALL_3);
        assertThat(testEml_plays.getBall4()).isEqualTo(DEFAULT_BALL_4);
        assertThat(testEml_plays.getBall5()).isEqualTo(DEFAULT_BALL_5);
        assertThat(testEml_plays.getLuckyStar1()).isEqualTo(DEFAULT_LUCKY_STAR_1);
        assertThat(testEml_plays.getLuckyStar2()).isEqualTo(DEFAULT_LUCKY_STAR_2);
        assertThat(testEml_plays.getDrawType()).isEqualTo(DEFAULT_DRAW_TYPE);
        assertThat(testEml_plays.getGameType()).isEqualTo(DEFAULT_GAME_TYPE);
        assertThat(testEml_plays.getSumB()).isEqualTo(DEFAULT_SUM_B);
        assertThat(testEml_plays.getSumS()).isEqualTo(DEFAULT_SUM_S);
        assertThat(testEml_plays.getPrize()).isEqualTo(DEFAULT_PRIZE);
        assertThat(testEml_plays.getRank_id()).isEqualTo(DEFAULT_RANK_ID);
        assertThat(testEml_plays.getBonus_rank_id()).isEqualTo(DEFAULT_BONUS_RANK_ID);
        assertThat(testEml_plays.isChecked()).isEqualTo(DEFAULT_CHECKED);
        assertThat(testEml_plays.getHash()).isEqualTo(DEFAULT_HASH);

        // Validate the Eml_plays in Elasticsearch
        Eml_plays eml_playsEs = eml_playsSearchRepository.findOne(testEml_plays.getId());
        assertThat(eml_playsEs).isEqualToComparingFieldByField(testEml_plays);
    }

    @Test
    @Transactional
    public void createEml_playsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = eml_playsRepository.findAll().size();

        // Create the Eml_plays with an existing ID
        Eml_plays existingEml_plays = new Eml_plays();
        existingEml_plays.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEml_playsMockMvc.perform(post("/api/eml-plays")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingEml_plays)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Eml_plays> eml_playsList = eml_playsRepository.findAll();
        assertThat(eml_playsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkDrawDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = eml_playsRepository.findAll().size();
        // set the field null
        eml_plays.setDrawDate(null);

        // Create the Eml_plays, which fails.

        restEml_playsMockMvc.perform(post("/api/eml-plays")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(eml_plays)))
            .andExpect(status().isBadRequest());

        List<Eml_plays> eml_playsList = eml_playsRepository.findAll();
        assertThat(eml_playsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkBall1IsRequired() throws Exception {
        int databaseSizeBeforeTest = eml_playsRepository.findAll().size();
        // set the field null
        eml_plays.setBall1(null);

        // Create the Eml_plays, which fails.

        restEml_playsMockMvc.perform(post("/api/eml-plays")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(eml_plays)))
            .andExpect(status().isBadRequest());

        List<Eml_plays> eml_playsList = eml_playsRepository.findAll();
        assertThat(eml_playsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkBall2IsRequired() throws Exception {
        int databaseSizeBeforeTest = eml_playsRepository.findAll().size();
        // set the field null
        eml_plays.setBall2(null);

        // Create the Eml_plays, which fails.

        restEml_playsMockMvc.perform(post("/api/eml-plays")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(eml_plays)))
            .andExpect(status().isBadRequest());

        List<Eml_plays> eml_playsList = eml_playsRepository.findAll();
        assertThat(eml_playsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkBall3IsRequired() throws Exception {
        int databaseSizeBeforeTest = eml_playsRepository.findAll().size();
        // set the field null
        eml_plays.setBall3(null);

        // Create the Eml_plays, which fails.

        restEml_playsMockMvc.perform(post("/api/eml-plays")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(eml_plays)))
            .andExpect(status().isBadRequest());

        List<Eml_plays> eml_playsList = eml_playsRepository.findAll();
        assertThat(eml_playsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkBall4IsRequired() throws Exception {
        int databaseSizeBeforeTest = eml_playsRepository.findAll().size();
        // set the field null
        eml_plays.setBall4(null);

        // Create the Eml_plays, which fails.

        restEml_playsMockMvc.perform(post("/api/eml-plays")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(eml_plays)))
            .andExpect(status().isBadRequest());

        List<Eml_plays> eml_playsList = eml_playsRepository.findAll();
        assertThat(eml_playsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkBall5IsRequired() throws Exception {
        int databaseSizeBeforeTest = eml_playsRepository.findAll().size();
        // set the field null
        eml_plays.setBall5(null);

        // Create the Eml_plays, which fails.

        restEml_playsMockMvc.perform(post("/api/eml-plays")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(eml_plays)))
            .andExpect(status().isBadRequest());

        List<Eml_plays> eml_playsList = eml_playsRepository.findAll();
        assertThat(eml_playsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLuckyStar1IsRequired() throws Exception {
        int databaseSizeBeforeTest = eml_playsRepository.findAll().size();
        // set the field null
        eml_plays.setLuckyStar1(null);

        // Create the Eml_plays, which fails.

        restEml_playsMockMvc.perform(post("/api/eml-plays")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(eml_plays)))
            .andExpect(status().isBadRequest());

        List<Eml_plays> eml_playsList = eml_playsRepository.findAll();
        assertThat(eml_playsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLuckyStar2IsRequired() throws Exception {
        int databaseSizeBeforeTest = eml_playsRepository.findAll().size();
        // set the field null
        eml_plays.setLuckyStar2(null);

        // Create the Eml_plays, which fails.

        restEml_playsMockMvc.perform(post("/api/eml-plays")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(eml_plays)))
            .andExpect(status().isBadRequest());

        List<Eml_plays> eml_playsList = eml_playsRepository.findAll();
        assertThat(eml_playsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDrawTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = eml_playsRepository.findAll().size();
        // set the field null
        eml_plays.setDrawType(null);

        // Create the Eml_plays, which fails.

        restEml_playsMockMvc.perform(post("/api/eml-plays")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(eml_plays)))
            .andExpect(status().isBadRequest());

        List<Eml_plays> eml_playsList = eml_playsRepository.findAll();
        assertThat(eml_playsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkGameTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = eml_playsRepository.findAll().size();
        // set the field null
        eml_plays.setGameType(null);

        // Create the Eml_plays, which fails.

        restEml_playsMockMvc.perform(post("/api/eml-plays")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(eml_plays)))
            .andExpect(status().isBadRequest());

        List<Eml_plays> eml_playsList = eml_playsRepository.findAll();
        assertThat(eml_playsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkSumBIsRequired() throws Exception {
        int databaseSizeBeforeTest = eml_playsRepository.findAll().size();
        // set the field null
        eml_plays.setSumB(null);

        // Create the Eml_plays, which fails.

        restEml_playsMockMvc.perform(post("/api/eml-plays")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(eml_plays)))
            .andExpect(status().isBadRequest());

        List<Eml_plays> eml_playsList = eml_playsRepository.findAll();
        assertThat(eml_playsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkSumSIsRequired() throws Exception {
        int databaseSizeBeforeTest = eml_playsRepository.findAll().size();
        // set the field null
        eml_plays.setSumS(null);

        // Create the Eml_plays, which fails.

        restEml_playsMockMvc.perform(post("/api/eml-plays")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(eml_plays)))
            .andExpect(status().isBadRequest());

        List<Eml_plays> eml_playsList = eml_playsRepository.findAll();
        assertThat(eml_playsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllEml_plays() throws Exception {
        // Initialize the database
        eml_playsRepository.saveAndFlush(eml_plays);

        // Get all the eml_playsList
        restEml_playsMockMvc.perform(get("/api/eml-plays?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(eml_plays.getId().intValue())))
            .andExpect(jsonPath("$.[*].drawDate").value(hasItem(DEFAULT_DRAW_DATE.toString())))
            .andExpect(jsonPath("$.[*].ball1").value(hasItem(DEFAULT_BALL_1)))
            .andExpect(jsonPath("$.[*].ball2").value(hasItem(DEFAULT_BALL_2)))
            .andExpect(jsonPath("$.[*].ball3").value(hasItem(DEFAULT_BALL_3)))
            .andExpect(jsonPath("$.[*].ball4").value(hasItem(DEFAULT_BALL_4)))
            .andExpect(jsonPath("$.[*].ball5").value(hasItem(DEFAULT_BALL_5)))
            .andExpect(jsonPath("$.[*].luckyStar1").value(hasItem(DEFAULT_LUCKY_STAR_1)))
            .andExpect(jsonPath("$.[*].luckyStar2").value(hasItem(DEFAULT_LUCKY_STAR_2)))
            .andExpect(jsonPath("$.[*].drawType").value(hasItem(DEFAULT_DRAW_TYPE.toString())))
            .andExpect(jsonPath("$.[*].gameType").value(hasItem(DEFAULT_GAME_TYPE.toString())))
            .andExpect(jsonPath("$.[*].sumB").value(hasItem(DEFAULT_SUM_B)))
            .andExpect(jsonPath("$.[*].sumS").value(hasItem(DEFAULT_SUM_S.toString())))
            .andExpect(jsonPath("$.[*].prize").value(hasItem(DEFAULT_PRIZE.doubleValue())))
            .andExpect(jsonPath("$.[*].rank_id").value(hasItem(DEFAULT_RANK_ID)))
            .andExpect(jsonPath("$.[*].bonus_rank_id").value(hasItem(DEFAULT_BONUS_RANK_ID)))
            .andExpect(jsonPath("$.[*].checked").value(hasItem(DEFAULT_CHECKED.booleanValue())))
            .andExpect(jsonPath("$.[*].hash").value(hasItem(DEFAULT_HASH.toString())));
    }

    @Test
    @Transactional
    public void getEml_plays() throws Exception {
        // Initialize the database
        eml_playsRepository.saveAndFlush(eml_plays);

        // Get the eml_plays
        restEml_playsMockMvc.perform(get("/api/eml-plays/{id}", eml_plays.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(eml_plays.getId().intValue()))
            .andExpect(jsonPath("$.drawDate").value(DEFAULT_DRAW_DATE.toString()))
            .andExpect(jsonPath("$.ball1").value(DEFAULT_BALL_1))
            .andExpect(jsonPath("$.ball2").value(DEFAULT_BALL_2))
            .andExpect(jsonPath("$.ball3").value(DEFAULT_BALL_3))
            .andExpect(jsonPath("$.ball4").value(DEFAULT_BALL_4))
            .andExpect(jsonPath("$.ball5").value(DEFAULT_BALL_5))
            .andExpect(jsonPath("$.luckyStar1").value(DEFAULT_LUCKY_STAR_1))
            .andExpect(jsonPath("$.luckyStar2").value(DEFAULT_LUCKY_STAR_2))
            .andExpect(jsonPath("$.drawType").value(DEFAULT_DRAW_TYPE.toString()))
            .andExpect(jsonPath("$.gameType").value(DEFAULT_GAME_TYPE.toString()))
            .andExpect(jsonPath("$.sumB").value(DEFAULT_SUM_B))
            .andExpect(jsonPath("$.sumS").value(DEFAULT_SUM_S.toString()))
            .andExpect(jsonPath("$.prize").value(DEFAULT_PRIZE.doubleValue()))
            .andExpect(jsonPath("$.rank_id").value(DEFAULT_RANK_ID))
            .andExpect(jsonPath("$.bonus_rank_id").value(DEFAULT_BONUS_RANK_ID))
            .andExpect(jsonPath("$.checked").value(DEFAULT_CHECKED.booleanValue()))
            .andExpect(jsonPath("$.hash").value(DEFAULT_HASH.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingEml_plays() throws Exception {
        // Get the eml_plays
        restEml_playsMockMvc.perform(get("/api/eml-plays/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEml_plays() throws Exception {
        // Initialize the database
        eml_playsService.save(eml_plays);

        int databaseSizeBeforeUpdate = eml_playsRepository.findAll().size();

        // Update the eml_plays
        Eml_plays updatedEml_plays = eml_playsRepository.findOne(eml_plays.getId());
        updatedEml_plays
                .drawDate(UPDATED_DRAW_DATE)
                .ball1(UPDATED_BALL_1)
                .ball2(UPDATED_BALL_2)
                .ball3(UPDATED_BALL_3)
                .ball4(UPDATED_BALL_4)
                .ball5(UPDATED_BALL_5)
                .luckyStar1(UPDATED_LUCKY_STAR_1)
                .luckyStar2(UPDATED_LUCKY_STAR_2)
                .drawType(UPDATED_DRAW_TYPE)
                .gameType(UPDATED_GAME_TYPE)
                .sumB(UPDATED_SUM_B)
                .sumS(UPDATED_SUM_S)
                .prize(UPDATED_PRIZE)
                .rank_id(UPDATED_RANK_ID)
                .bonus_rank_id(UPDATED_BONUS_RANK_ID)
                .checked(UPDATED_CHECKED)
                .hash(UPDATED_HASH);

        restEml_playsMockMvc.perform(put("/api/eml-plays")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedEml_plays)))
            .andExpect(status().isOk());

        // Validate the Eml_plays in the database
        List<Eml_plays> eml_playsList = eml_playsRepository.findAll();
        assertThat(eml_playsList).hasSize(databaseSizeBeforeUpdate);
        Eml_plays testEml_plays = eml_playsList.get(eml_playsList.size() - 1);
        assertThat(testEml_plays.getDrawDate()).isEqualTo(UPDATED_DRAW_DATE);
        assertThat(testEml_plays.getBall1()).isEqualTo(UPDATED_BALL_1);
        assertThat(testEml_plays.getBall2()).isEqualTo(UPDATED_BALL_2);
        assertThat(testEml_plays.getBall3()).isEqualTo(UPDATED_BALL_3);
        assertThat(testEml_plays.getBall4()).isEqualTo(UPDATED_BALL_4);
        assertThat(testEml_plays.getBall5()).isEqualTo(UPDATED_BALL_5);
        assertThat(testEml_plays.getLuckyStar1()).isEqualTo(UPDATED_LUCKY_STAR_1);
        assertThat(testEml_plays.getLuckyStar2()).isEqualTo(UPDATED_LUCKY_STAR_2);
        assertThat(testEml_plays.getDrawType()).isEqualTo(UPDATED_DRAW_TYPE);
        assertThat(testEml_plays.getGameType()).isEqualTo(UPDATED_GAME_TYPE);
        assertThat(testEml_plays.getSumB()).isEqualTo(UPDATED_SUM_B);
        assertThat(testEml_plays.getSumS()).isEqualTo(UPDATED_SUM_S);
        assertThat(testEml_plays.getPrize()).isEqualTo(UPDATED_PRIZE);
        assertThat(testEml_plays.getRank_id()).isEqualTo(UPDATED_RANK_ID);
        assertThat(testEml_plays.getBonus_rank_id()).isEqualTo(UPDATED_BONUS_RANK_ID);
        assertThat(testEml_plays.isChecked()).isEqualTo(UPDATED_CHECKED);
        assertThat(testEml_plays.getHash()).isEqualTo(UPDATED_HASH);

        // Validate the Eml_plays in Elasticsearch
        Eml_plays eml_playsEs = eml_playsSearchRepository.findOne(testEml_plays.getId());
        assertThat(eml_playsEs).isEqualToComparingFieldByField(testEml_plays);
    }

    @Test
    @Transactional
    public void updateNonExistingEml_plays() throws Exception {
        int databaseSizeBeforeUpdate = eml_playsRepository.findAll().size();

        // Create the Eml_plays

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restEml_playsMockMvc.perform(put("/api/eml-plays")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(eml_plays)))
            .andExpect(status().isCreated());

        // Validate the Eml_plays in the database
        List<Eml_plays> eml_playsList = eml_playsRepository.findAll();
        assertThat(eml_playsList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteEml_plays() throws Exception {
        // Initialize the database
        eml_playsService.save(eml_plays);

        int databaseSizeBeforeDelete = eml_playsRepository.findAll().size();

        // Get the eml_plays
        restEml_playsMockMvc.perform(delete("/api/eml-plays/{id}", eml_plays.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean eml_playsExistsInEs = eml_playsSearchRepository.exists(eml_plays.getId());
        assertThat(eml_playsExistsInEs).isFalse();

        // Validate the database is empty
        List<Eml_plays> eml_playsList = eml_playsRepository.findAll();
        assertThat(eml_playsList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchEml_plays() throws Exception {
        // Initialize the database
        eml_playsService.save(eml_plays);

        // Search the eml_plays
        restEml_playsMockMvc.perform(get("/api/_search/eml-plays?query=id:" + eml_plays.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(eml_plays.getId().intValue())))
            .andExpect(jsonPath("$.[*].drawDate").value(hasItem(DEFAULT_DRAW_DATE.toString())))
            .andExpect(jsonPath("$.[*].ball1").value(hasItem(DEFAULT_BALL_1)))
            .andExpect(jsonPath("$.[*].ball2").value(hasItem(DEFAULT_BALL_2)))
            .andExpect(jsonPath("$.[*].ball3").value(hasItem(DEFAULT_BALL_3)))
            .andExpect(jsonPath("$.[*].ball4").value(hasItem(DEFAULT_BALL_4)))
            .andExpect(jsonPath("$.[*].ball5").value(hasItem(DEFAULT_BALL_5)))
            .andExpect(jsonPath("$.[*].luckyStar1").value(hasItem(DEFAULT_LUCKY_STAR_1)))
            .andExpect(jsonPath("$.[*].luckyStar2").value(hasItem(DEFAULT_LUCKY_STAR_2)))
            .andExpect(jsonPath("$.[*].drawType").value(hasItem(DEFAULT_DRAW_TYPE.toString())))
            .andExpect(jsonPath("$.[*].gameType").value(hasItem(DEFAULT_GAME_TYPE.toString())))
            .andExpect(jsonPath("$.[*].sumB").value(hasItem(DEFAULT_SUM_B)))
            .andExpect(jsonPath("$.[*].sumS").value(hasItem(DEFAULT_SUM_S.toString())))
            .andExpect(jsonPath("$.[*].prize").value(hasItem(DEFAULT_PRIZE.doubleValue())))
            .andExpect(jsonPath("$.[*].rank_id").value(hasItem(DEFAULT_RANK_ID)))
            .andExpect(jsonPath("$.[*].bonus_rank_id").value(hasItem(DEFAULT_BONUS_RANK_ID)))
            .andExpect(jsonPath("$.[*].checked").value(hasItem(DEFAULT_CHECKED.booleanValue())))
            .andExpect(jsonPath("$.[*].hash").value(hasItem(DEFAULT_HASH.toString())));
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Eml_plays.class);
    }
}
