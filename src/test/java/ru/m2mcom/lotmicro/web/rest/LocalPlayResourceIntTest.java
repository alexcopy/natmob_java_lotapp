package ru.m2mcom.lotmicro.web.rest;

import ru.m2mcom.lotmicro.LotmicroApp;

import ru.m2mcom.lotmicro.domain.LocalPlay;
import ru.m2mcom.lotmicro.repository.LocalPlayRepository;
import ru.m2mcom.lotmicro.service.LocalPlayService;
import ru.m2mcom.lotmicro.repository.search.LocalPlaySearchRepository;
import ru.m2mcom.lotmicro.service.dto.LocalPlayDTO;
import ru.m2mcom.lotmicro.service.mapper.LocalPlayMapper;
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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import ru.m2mcom.lotmicro.domain.enumeration.GameType;
import ru.m2mcom.lotmicro.domain.enumeration.GamesPlay;
/**
 * Test class for the LocalPlayResource REST controller.
 *
 * @see LocalPlayResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = LotmicroApp.class)
public class LocalPlayResourceIntTest {

    private static final LocalDate DEFAULT_DRAW_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DRAW_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_DRAW = "AAAAAAAAAA";
    private static final String UPDATED_DRAW = "BBBBBBBBBB";

    private static final Integer DEFAULT_SUM_B = 1;
    private static final Integer UPDATED_SUM_B = 2;

    private static final Integer DEFAULT_SUM_S = 1;
    private static final Integer UPDATED_SUM_S = 2;

    private static final String DEFAULT_DRAW_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_DRAW_TYPE = "BBBBBBBBBB";

    private static final GameType DEFAULT_GAME_TYPE = GameType.LOCAL;
    private static final GameType UPDATED_GAME_TYPE = GameType.MANUAL;

    private static final String DEFAULT_HASH = "AAAAAAAAAA";
    private static final String UPDATED_HASH = "BBBBBBBBBB";

    private static final Double DEFAULT_PRIZE = 1D;
    private static final Double UPDATED_PRIZE = 2D;

    private static final Integer DEFAULT_CHECKED = 1;
    private static final Integer UPDATED_CHECKED = 2;

    private static final Integer DEFAULT_BONUSRANKID = 1;
    private static final Integer UPDATED_BONUSRANKID = 2;

    private static final GamesPlay DEFAULT_GAME = GamesPlay.EML;
    private static final GamesPlay UPDATED_GAME = GamesPlay.NAT;

    @Autowired
    private LocalPlayRepository localPlayRepository;

    @Autowired
    private LocalPlayMapper localPlayMapper;

    @Autowired
    private LocalPlayService localPlayService;

    @Autowired
    private LocalPlaySearchRepository localPlaySearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restLocalPlayMockMvc;

    private LocalPlay localPlay;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        LocalPlayResource localPlayResource = new LocalPlayResource(localPlayService);
        this.restLocalPlayMockMvc = MockMvcBuilders.standaloneSetup(localPlayResource)
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
    public static LocalPlay createEntity(EntityManager em) {
        LocalPlay localPlay = new LocalPlay()
            .drawDate(DEFAULT_DRAW_DATE)
            .draw(DEFAULT_DRAW)
            .sumB(DEFAULT_SUM_B)
            .sumS(DEFAULT_SUM_S)
            .drawType(DEFAULT_DRAW_TYPE)
            .gameType(DEFAULT_GAME_TYPE)
            .hash(DEFAULT_HASH)
            .prize(DEFAULT_PRIZE)
            .checked(DEFAULT_CHECKED)
            .bonusrankid(DEFAULT_BONUSRANKID)
            .game(DEFAULT_GAME);
        return localPlay;
    }

    @Before
    public void initTest() {
        localPlaySearchRepository.deleteAll();
        localPlay = createEntity(em);
    }

    @Test
    @Transactional
    public void createLocalPlay() throws Exception {
        int databaseSizeBeforeCreate = localPlayRepository.findAll().size();

        // Create the LocalPlay
        LocalPlayDTO localPlayDTO = localPlayMapper.localPlayToLocalPlayDTO(localPlay);
        restLocalPlayMockMvc.perform(post("/api/local-plays")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(localPlayDTO)))
            .andExpect(status().isCreated());

        // Validate the LocalPlay in the database
        List<LocalPlay> localPlayList = localPlayRepository.findAll();
        assertThat(localPlayList).hasSize(databaseSizeBeforeCreate + 1);
        LocalPlay testLocalPlay = localPlayList.get(localPlayList.size() - 1);
        assertThat(testLocalPlay.getDrawDate()).isEqualTo(DEFAULT_DRAW_DATE);
        assertThat(testLocalPlay.getDraw()).isEqualTo(DEFAULT_DRAW);
        assertThat(testLocalPlay.getSumB()).isEqualTo(DEFAULT_SUM_B);
        assertThat(testLocalPlay.getSumS()).isEqualTo(DEFAULT_SUM_S);
        assertThat(testLocalPlay.getDrawType()).isEqualTo(DEFAULT_DRAW_TYPE);
        assertThat(testLocalPlay.getGameType()).isEqualTo(DEFAULT_GAME_TYPE);
        assertThat(testLocalPlay.getHash()).isEqualTo(DEFAULT_HASH);
        assertThat(testLocalPlay.getPrize()).isEqualTo(DEFAULT_PRIZE);
        assertThat(testLocalPlay.getChecked()).isEqualTo(DEFAULT_CHECKED);
        assertThat(testLocalPlay.getBonusrankid()).isEqualTo(DEFAULT_BONUSRANKID);
        assertThat(testLocalPlay.getGame()).isEqualTo(DEFAULT_GAME);

        // Validate the LocalPlay in Elasticsearch
        LocalPlay localPlayEs = localPlaySearchRepository.findOne(testLocalPlay.getId());
        assertThat(localPlayEs).isEqualToComparingFieldByField(testLocalPlay);
    }

    @Test
    @Transactional
    public void createLocalPlayWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = localPlayRepository.findAll().size();

        // Create the LocalPlay with an existing ID
        localPlay.setId(1L);
        LocalPlayDTO localPlayDTO = localPlayMapper.localPlayToLocalPlayDTO(localPlay);

        // An entity with an existing ID cannot be created, so this API call must fail
        restLocalPlayMockMvc.perform(post("/api/local-plays")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(localPlayDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<LocalPlay> localPlayList = localPlayRepository.findAll();
        assertThat(localPlayList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkDrawDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = localPlayRepository.findAll().size();
        // set the field null
        localPlay.setDrawDate(null);

        // Create the LocalPlay, which fails.
        LocalPlayDTO localPlayDTO = localPlayMapper.localPlayToLocalPlayDTO(localPlay);

        restLocalPlayMockMvc.perform(post("/api/local-plays")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(localPlayDTO)))
            .andExpect(status().isBadRequest());

        List<LocalPlay> localPlayList = localPlayRepository.findAll();
        assertThat(localPlayList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDrawIsRequired() throws Exception {
        int databaseSizeBeforeTest = localPlayRepository.findAll().size();
        // set the field null
        localPlay.setDraw(null);

        // Create the LocalPlay, which fails.
        LocalPlayDTO localPlayDTO = localPlayMapper.localPlayToLocalPlayDTO(localPlay);

        restLocalPlayMockMvc.perform(post("/api/local-plays")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(localPlayDTO)))
            .andExpect(status().isBadRequest());

        List<LocalPlay> localPlayList = localPlayRepository.findAll();
        assertThat(localPlayList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllLocalPlays() throws Exception {
        // Initialize the database
        localPlayRepository.saveAndFlush(localPlay);

        // Get all the localPlayList
        restLocalPlayMockMvc.perform(get("/api/local-plays?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(localPlay.getId().intValue())))
            .andExpect(jsonPath("$.[*].drawDate").value(hasItem(DEFAULT_DRAW_DATE.toString())))
            .andExpect(jsonPath("$.[*].draw").value(hasItem(DEFAULT_DRAW.toString())))
            .andExpect(jsonPath("$.[*].sumB").value(hasItem(DEFAULT_SUM_B)))
            .andExpect(jsonPath("$.[*].sumS").value(hasItem(DEFAULT_SUM_S)))
            .andExpect(jsonPath("$.[*].drawType").value(hasItem(DEFAULT_DRAW_TYPE.toString())))
            .andExpect(jsonPath("$.[*].gameType").value(hasItem(DEFAULT_GAME_TYPE.toString())))
            .andExpect(jsonPath("$.[*].hash").value(hasItem(DEFAULT_HASH.toString())))
            .andExpect(jsonPath("$.[*].prize").value(hasItem(DEFAULT_PRIZE.doubleValue())))
            .andExpect(jsonPath("$.[*].checked").value(hasItem(DEFAULT_CHECKED)))
            .andExpect(jsonPath("$.[*].bonusrankid").value(hasItem(DEFAULT_BONUSRANKID)))
            .andExpect(jsonPath("$.[*].game").value(hasItem(DEFAULT_GAME.toString())));
    }

    @Test
    @Transactional
    public void getLocalPlay() throws Exception {
        // Initialize the database
        localPlayRepository.saveAndFlush(localPlay);

        // Get the localPlay
        restLocalPlayMockMvc.perform(get("/api/local-plays/{id}", localPlay.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(localPlay.getId().intValue()))
            .andExpect(jsonPath("$.drawDate").value(DEFAULT_DRAW_DATE.toString()))
            .andExpect(jsonPath("$.draw").value(DEFAULT_DRAW.toString()))
            .andExpect(jsonPath("$.sumB").value(DEFAULT_SUM_B))
            .andExpect(jsonPath("$.sumS").value(DEFAULT_SUM_S))
            .andExpect(jsonPath("$.drawType").value(DEFAULT_DRAW_TYPE.toString()))
            .andExpect(jsonPath("$.gameType").value(DEFAULT_GAME_TYPE.toString()))
            .andExpect(jsonPath("$.hash").value(DEFAULT_HASH.toString()))
            .andExpect(jsonPath("$.prize").value(DEFAULT_PRIZE.doubleValue()))
            .andExpect(jsonPath("$.checked").value(DEFAULT_CHECKED))
            .andExpect(jsonPath("$.bonusrankid").value(DEFAULT_BONUSRANKID))
            .andExpect(jsonPath("$.game").value(DEFAULT_GAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingLocalPlay() throws Exception {
        // Get the localPlay
        restLocalPlayMockMvc.perform(get("/api/local-plays/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateLocalPlay() throws Exception {
        // Initialize the database
        localPlayRepository.saveAndFlush(localPlay);
        localPlaySearchRepository.save(localPlay);
        int databaseSizeBeforeUpdate = localPlayRepository.findAll().size();

        // Update the localPlay
        LocalPlay updatedLocalPlay = localPlayRepository.findOne(localPlay.getId());
        updatedLocalPlay
            .drawDate(UPDATED_DRAW_DATE)
            .draw(UPDATED_DRAW)
            .sumB(UPDATED_SUM_B)
            .sumS(UPDATED_SUM_S)
            .drawType(UPDATED_DRAW_TYPE)
            .gameType(UPDATED_GAME_TYPE)
            .hash(UPDATED_HASH)
            .prize(UPDATED_PRIZE)
            .checked(UPDATED_CHECKED)
            .bonusrankid(UPDATED_BONUSRANKID)
            .game(UPDATED_GAME);
        LocalPlayDTO localPlayDTO = localPlayMapper.localPlayToLocalPlayDTO(updatedLocalPlay);

        restLocalPlayMockMvc.perform(put("/api/local-plays")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(localPlayDTO)))
            .andExpect(status().isOk());

        // Validate the LocalPlay in the database
        List<LocalPlay> localPlayList = localPlayRepository.findAll();
        assertThat(localPlayList).hasSize(databaseSizeBeforeUpdate);
        LocalPlay testLocalPlay = localPlayList.get(localPlayList.size() - 1);
        assertThat(testLocalPlay.getDrawDate()).isEqualTo(UPDATED_DRAW_DATE);
        assertThat(testLocalPlay.getDraw()).isEqualTo(UPDATED_DRAW);
        assertThat(testLocalPlay.getSumB()).isEqualTo(UPDATED_SUM_B);
        assertThat(testLocalPlay.getSumS()).isEqualTo(UPDATED_SUM_S);
        assertThat(testLocalPlay.getDrawType()).isEqualTo(UPDATED_DRAW_TYPE);
        assertThat(testLocalPlay.getGameType()).isEqualTo(UPDATED_GAME_TYPE);
        assertThat(testLocalPlay.getHash()).isEqualTo(UPDATED_HASH);
        assertThat(testLocalPlay.getPrize()).isEqualTo(UPDATED_PRIZE);
        assertThat(testLocalPlay.getChecked()).isEqualTo(UPDATED_CHECKED);
        assertThat(testLocalPlay.getBonusrankid()).isEqualTo(UPDATED_BONUSRANKID);
        assertThat(testLocalPlay.getGame()).isEqualTo(UPDATED_GAME);

        // Validate the LocalPlay in Elasticsearch
        LocalPlay localPlayEs = localPlaySearchRepository.findOne(testLocalPlay.getId());
        assertThat(localPlayEs).isEqualToComparingFieldByField(testLocalPlay);
    }

    @Test
    @Transactional
    public void updateNonExistingLocalPlay() throws Exception {
        int databaseSizeBeforeUpdate = localPlayRepository.findAll().size();

        // Create the LocalPlay
        LocalPlayDTO localPlayDTO = localPlayMapper.localPlayToLocalPlayDTO(localPlay);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restLocalPlayMockMvc.perform(put("/api/local-plays")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(localPlayDTO)))
            .andExpect(status().isCreated());

        // Validate the LocalPlay in the database
        List<LocalPlay> localPlayList = localPlayRepository.findAll();
        assertThat(localPlayList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteLocalPlay() throws Exception {
        // Initialize the database
        localPlayRepository.saveAndFlush(localPlay);
        localPlaySearchRepository.save(localPlay);
        int databaseSizeBeforeDelete = localPlayRepository.findAll().size();

        // Get the localPlay
        restLocalPlayMockMvc.perform(delete("/api/local-plays/{id}", localPlay.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean localPlayExistsInEs = localPlaySearchRepository.exists(localPlay.getId());
        assertThat(localPlayExistsInEs).isFalse();

        // Validate the database is empty
        List<LocalPlay> localPlayList = localPlayRepository.findAll();
        assertThat(localPlayList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchLocalPlay() throws Exception {
        // Initialize the database
        localPlayRepository.saveAndFlush(localPlay);
        localPlaySearchRepository.save(localPlay);

        // Search the localPlay
        restLocalPlayMockMvc.perform(get("/api/_search/local-plays?query=id:" + localPlay.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(localPlay.getId().intValue())))
            .andExpect(jsonPath("$.[*].drawDate").value(hasItem(DEFAULT_DRAW_DATE.toString())))
            .andExpect(jsonPath("$.[*].draw").value(hasItem(DEFAULT_DRAW.toString())))
            .andExpect(jsonPath("$.[*].sumB").value(hasItem(DEFAULT_SUM_B)))
            .andExpect(jsonPath("$.[*].sumS").value(hasItem(DEFAULT_SUM_S)))
            .andExpect(jsonPath("$.[*].drawType").value(hasItem(DEFAULT_DRAW_TYPE.toString())))
            .andExpect(jsonPath("$.[*].gameType").value(hasItem(DEFAULT_GAME_TYPE.toString())))
            .andExpect(jsonPath("$.[*].hash").value(hasItem(DEFAULT_HASH.toString())))
            .andExpect(jsonPath("$.[*].prize").value(hasItem(DEFAULT_PRIZE.doubleValue())))
            .andExpect(jsonPath("$.[*].checked").value(hasItem(DEFAULT_CHECKED)))
            .andExpect(jsonPath("$.[*].bonusrankid").value(hasItem(DEFAULT_BONUSRANKID)))
            .andExpect(jsonPath("$.[*].game").value(hasItem(DEFAULT_GAME.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(LocalPlay.class);
    }
}
