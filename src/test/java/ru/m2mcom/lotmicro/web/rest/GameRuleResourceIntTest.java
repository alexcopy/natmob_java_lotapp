package ru.m2mcom.lotmicro.web.rest;

import ru.m2mcom.lotmicro.LotmicroApp;

import ru.m2mcom.lotmicro.domain.GameRule;
import ru.m2mcom.lotmicro.repository.GameRuleRepository;
import ru.m2mcom.lotmicro.service.GameRuleService;
import ru.m2mcom.lotmicro.repository.search.GameRuleSearchRepository;
import ru.m2mcom.lotmicro.service.dto.GameRuleDTO;
import ru.m2mcom.lotmicro.service.mapper.GameRuleMapper;
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

/**
 * Test class for the GameRuleResource REST controller.
 *
 * @see GameRuleResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = LotmicroApp.class)
public class GameRuleResourceIntTest {

    private static final Double DEFAULT_TICKET_PRICE = 1D;
    private static final Double UPDATED_TICKET_PRICE = 2D;

    private static final String DEFAULT_PRIZE_TABLE = "AAAAAAAAAA";
    private static final String UPDATED_PRIZE_TABLE = "BBBBBBBBBB";

    private static final String DEFAULT_BALL_SET = "AAAAAAAAAA";
    private static final String UPDATED_BALL_SET = "BBBBBBBBBB";

    private static final String DEFAULT_BONUS_SET = "AAAAAAAAAA";
    private static final String UPDATED_BONUS_SET = "BBBBBBBBBB";

    private static final String DEFAULT_DAYS_PLAY = "AAAAAAAAAA";
    private static final String UPDATED_DAYS_PLAY = "BBBBBBBBBB";

    private static final String DEFAULT_BALL_RANGE = "AAAAAAAAAA";
    private static final String UPDATED_BALL_RANGE = "BBBBBBBBBB";

    private static final String DEFAULT_BONUS_RANGE = "AAAAAAAAAA";
    private static final String UPDATED_BONUS_RANGE = "BBBBBBBBBB";

    @Autowired
    private GameRuleRepository gameRuleRepository;

    @Autowired
    private GameRuleMapper gameRuleMapper;

    @Autowired
    private GameRuleService gameRuleService;

    @Autowired
    private GameRuleSearchRepository gameRuleSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restGameRuleMockMvc;

    private GameRule gameRule;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        GameRuleResource gameRuleResource = new GameRuleResource(gameRuleService);
        this.restGameRuleMockMvc = MockMvcBuilders.standaloneSetup(gameRuleResource)
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
    public static GameRule createEntity(EntityManager em) {
        GameRule gameRule = new GameRule()
            .ticketPrice(DEFAULT_TICKET_PRICE)
            .prizeTable(DEFAULT_PRIZE_TABLE)
            .ballSet(DEFAULT_BALL_SET)
            .bonusSet(DEFAULT_BONUS_SET)
            .daysPlay(DEFAULT_DAYS_PLAY)
            .ballRange(DEFAULT_BALL_RANGE)
            .bonusRange(DEFAULT_BONUS_RANGE);
        return gameRule;
    }

    @Before
    public void initTest() {
        gameRuleSearchRepository.deleteAll();
        gameRule = createEntity(em);
    }

    @Test
    @Transactional
    public void createGameRule() throws Exception {
        int databaseSizeBeforeCreate = gameRuleRepository.findAll().size();

        // Create the GameRule
        GameRuleDTO gameRuleDTO = gameRuleMapper.gameRuleToGameRuleDTO(gameRule);
        restGameRuleMockMvc.perform(post("/api/game-rules")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(gameRuleDTO)))
            .andExpect(status().isCreated());

        // Validate the GameRule in the database
        List<GameRule> gameRuleList = gameRuleRepository.findAll();
        assertThat(gameRuleList).hasSize(databaseSizeBeforeCreate + 1);
        GameRule testGameRule = gameRuleList.get(gameRuleList.size() - 1);
        assertThat(testGameRule.getTicketPrice()).isEqualTo(DEFAULT_TICKET_PRICE);
        assertThat(testGameRule.getPrizeTable()).isEqualTo(DEFAULT_PRIZE_TABLE);
        assertThat(testGameRule.getBallSet()).isEqualTo(DEFAULT_BALL_SET);
        assertThat(testGameRule.getBonusSet()).isEqualTo(DEFAULT_BONUS_SET);
        assertThat(testGameRule.getDaysPlay()).isEqualTo(DEFAULT_DAYS_PLAY);
        assertThat(testGameRule.getBallRange()).isEqualTo(DEFAULT_BALL_RANGE);
        assertThat(testGameRule.getBonusRange()).isEqualTo(DEFAULT_BONUS_RANGE);

        // Validate the GameRule in Elasticsearch
        GameRule gameRuleEs = gameRuleSearchRepository.findOne(testGameRule.getId());
        assertThat(gameRuleEs).isEqualToComparingFieldByField(testGameRule);
    }

    @Test
    @Transactional
    public void createGameRuleWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = gameRuleRepository.findAll().size();

        // Create the GameRule with an existing ID
        gameRule.setId(1L);
        GameRuleDTO gameRuleDTO = gameRuleMapper.gameRuleToGameRuleDTO(gameRule);

        // An entity with an existing ID cannot be created, so this API call must fail
        restGameRuleMockMvc.perform(post("/api/game-rules")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(gameRuleDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<GameRule> gameRuleList = gameRuleRepository.findAll();
        assertThat(gameRuleList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkTicketPriceIsRequired() throws Exception {
        int databaseSizeBeforeTest = gameRuleRepository.findAll().size();
        // set the field null
        gameRule.setTicketPrice(null);

        // Create the GameRule, which fails.
        GameRuleDTO gameRuleDTO = gameRuleMapper.gameRuleToGameRuleDTO(gameRule);

        restGameRuleMockMvc.perform(post("/api/game-rules")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(gameRuleDTO)))
            .andExpect(status().isBadRequest());

        List<GameRule> gameRuleList = gameRuleRepository.findAll();
        assertThat(gameRuleList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllGameRules() throws Exception {
        // Initialize the database
        gameRuleRepository.saveAndFlush(gameRule);

        // Get all the gameRuleList
        restGameRuleMockMvc.perform(get("/api/game-rules?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(gameRule.getId().intValue())))
            .andExpect(jsonPath("$.[*].ticketPrice").value(hasItem(DEFAULT_TICKET_PRICE.doubleValue())))
            .andExpect(jsonPath("$.[*].prizeTable").value(hasItem(DEFAULT_PRIZE_TABLE.toString())))
            .andExpect(jsonPath("$.[*].ballSet").value(hasItem(DEFAULT_BALL_SET.toString())))
            .andExpect(jsonPath("$.[*].bonusSet").value(hasItem(DEFAULT_BONUS_SET.toString())))
            .andExpect(jsonPath("$.[*].daysPlay").value(hasItem(DEFAULT_DAYS_PLAY.toString())))
            .andExpect(jsonPath("$.[*].ballRange").value(hasItem(DEFAULT_BALL_RANGE.toString())))
            .andExpect(jsonPath("$.[*].bonusRange").value(hasItem(DEFAULT_BONUS_RANGE.toString())));
    }

    @Test
    @Transactional
    public void getGameRule() throws Exception {
        // Initialize the database
        gameRuleRepository.saveAndFlush(gameRule);

        // Get the gameRule
        restGameRuleMockMvc.perform(get("/api/game-rules/{id}", gameRule.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(gameRule.getId().intValue()))
            .andExpect(jsonPath("$.ticketPrice").value(DEFAULT_TICKET_PRICE.doubleValue()))
            .andExpect(jsonPath("$.prizeTable").value(DEFAULT_PRIZE_TABLE.toString()))
            .andExpect(jsonPath("$.ballSet").value(DEFAULT_BALL_SET.toString()))
            .andExpect(jsonPath("$.bonusSet").value(DEFAULT_BONUS_SET.toString()))
            .andExpect(jsonPath("$.daysPlay").value(DEFAULT_DAYS_PLAY.toString()))
            .andExpect(jsonPath("$.ballRange").value(DEFAULT_BALL_RANGE.toString()))
            .andExpect(jsonPath("$.bonusRange").value(DEFAULT_BONUS_RANGE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingGameRule() throws Exception {
        // Get the gameRule
        restGameRuleMockMvc.perform(get("/api/game-rules/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateGameRule() throws Exception {
        // Initialize the database
        gameRuleRepository.saveAndFlush(gameRule);
        gameRuleSearchRepository.save(gameRule);
        int databaseSizeBeforeUpdate = gameRuleRepository.findAll().size();

        // Update the gameRule
        GameRule updatedGameRule = gameRuleRepository.findOne(gameRule.getId());
        updatedGameRule
            .ticketPrice(UPDATED_TICKET_PRICE)
            .prizeTable(UPDATED_PRIZE_TABLE)
            .ballSet(UPDATED_BALL_SET)
            .bonusSet(UPDATED_BONUS_SET)
            .daysPlay(UPDATED_DAYS_PLAY)
            .ballRange(UPDATED_BALL_RANGE)
            .bonusRange(UPDATED_BONUS_RANGE);
        GameRuleDTO gameRuleDTO = gameRuleMapper.gameRuleToGameRuleDTO(updatedGameRule);

        restGameRuleMockMvc.perform(put("/api/game-rules")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(gameRuleDTO)))
            .andExpect(status().isOk());

        // Validate the GameRule in the database
        List<GameRule> gameRuleList = gameRuleRepository.findAll();
        assertThat(gameRuleList).hasSize(databaseSizeBeforeUpdate);
        GameRule testGameRule = gameRuleList.get(gameRuleList.size() - 1);
        assertThat(testGameRule.getTicketPrice()).isEqualTo(UPDATED_TICKET_PRICE);
        assertThat(testGameRule.getPrizeTable()).isEqualTo(UPDATED_PRIZE_TABLE);
        assertThat(testGameRule.getBallSet()).isEqualTo(UPDATED_BALL_SET);
        assertThat(testGameRule.getBonusSet()).isEqualTo(UPDATED_BONUS_SET);
        assertThat(testGameRule.getDaysPlay()).isEqualTo(UPDATED_DAYS_PLAY);
        assertThat(testGameRule.getBallRange()).isEqualTo(UPDATED_BALL_RANGE);
        assertThat(testGameRule.getBonusRange()).isEqualTo(UPDATED_BONUS_RANGE);

        // Validate the GameRule in Elasticsearch
        GameRule gameRuleEs = gameRuleSearchRepository.findOne(testGameRule.getId());
        assertThat(gameRuleEs).isEqualToComparingFieldByField(testGameRule);
    }

    @Test
    @Transactional
    public void updateNonExistingGameRule() throws Exception {
        int databaseSizeBeforeUpdate = gameRuleRepository.findAll().size();

        // Create the GameRule
        GameRuleDTO gameRuleDTO = gameRuleMapper.gameRuleToGameRuleDTO(gameRule);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restGameRuleMockMvc.perform(put("/api/game-rules")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(gameRuleDTO)))
            .andExpect(status().isCreated());

        // Validate the GameRule in the database
        List<GameRule> gameRuleList = gameRuleRepository.findAll();
        assertThat(gameRuleList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteGameRule() throws Exception {
        // Initialize the database
        gameRuleRepository.saveAndFlush(gameRule);
        gameRuleSearchRepository.save(gameRule);
        int databaseSizeBeforeDelete = gameRuleRepository.findAll().size();

        // Get the gameRule
        restGameRuleMockMvc.perform(delete("/api/game-rules/{id}", gameRule.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean gameRuleExistsInEs = gameRuleSearchRepository.exists(gameRule.getId());
        assertThat(gameRuleExistsInEs).isFalse();

        // Validate the database is empty
        List<GameRule> gameRuleList = gameRuleRepository.findAll();
        assertThat(gameRuleList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchGameRule() throws Exception {
        // Initialize the database
        gameRuleRepository.saveAndFlush(gameRule);
        gameRuleSearchRepository.save(gameRule);

        // Search the gameRule
        restGameRuleMockMvc.perform(get("/api/_search/game-rules?query=id:" + gameRule.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(gameRule.getId().intValue())))
            .andExpect(jsonPath("$.[*].ticketPrice").value(hasItem(DEFAULT_TICKET_PRICE.doubleValue())))
            .andExpect(jsonPath("$.[*].prizeTable").value(hasItem(DEFAULT_PRIZE_TABLE.toString())))
            .andExpect(jsonPath("$.[*].ballSet").value(hasItem(DEFAULT_BALL_SET.toString())))
            .andExpect(jsonPath("$.[*].bonusSet").value(hasItem(DEFAULT_BONUS_SET.toString())))
            .andExpect(jsonPath("$.[*].daysPlay").value(hasItem(DEFAULT_DAYS_PLAY.toString())))
            .andExpect(jsonPath("$.[*].ballRange").value(hasItem(DEFAULT_BALL_RANGE.toString())))
            .andExpect(jsonPath("$.[*].bonusRange").value(hasItem(DEFAULT_BONUS_RANGE.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(GameRule.class);
    }
}
