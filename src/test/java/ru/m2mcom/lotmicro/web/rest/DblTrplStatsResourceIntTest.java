package ru.m2mcom.lotmicro.web.rest;

import ru.m2mcom.lotmicro.LotmicroApp;

import ru.m2mcom.lotmicro.domain.DblTrplStats;
import ru.m2mcom.lotmicro.repository.DblTrplStatsRepository;
import ru.m2mcom.lotmicro.service.DblTrplStatsService;
import ru.m2mcom.lotmicro.repository.search.DblTrplStatsSearchRepository;
import ru.m2mcom.lotmicro.service.dto.DblTrplStatsDTO;
import ru.m2mcom.lotmicro.service.mapper.DblTrplStatsMapper;
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
 * Test class for the DblTrplStatsResource REST controller.
 *
 * @see DblTrplStatsResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = LotmicroApp.class)
public class DblTrplStatsResourceIntTest {

    private static final Integer DEFAULT_NUM_1 = 1;
    private static final Integer UPDATED_NUM_1 = 2;

    private static final Integer DEFAULT_NUM_2 = 1;
    private static final Integer UPDATED_NUM_2 = 2;

    private static final Integer DEFAULT_NUM_3 = 1;
    private static final Integer UPDATED_NUM_3 = 2;

    private static final Integer DEFAULT_TIMES_L = 1;
    private static final Integer UPDATED_TIMES_L = 2;

    private static final Integer DEFAULT_TIMES_S = 1;
    private static final Integer UPDATED_TIMES_S = 2;

    private static final Integer DEFAULT_SUM = 1;
    private static final Integer UPDATED_SUM = 2;

    private static final Integer DEFAULT_EVENS = 1;
    private static final Integer UPDATED_EVENS = 2;

    private static final GamesPlay DEFAULT_GAME = GamesPlay.EML;
    private static final GamesPlay UPDATED_GAME = GamesPlay.NAT;

    @Autowired
    private DblTrplStatsRepository dblTrplStatsRepository;

    @Autowired
    private DblTrplStatsMapper dblTrplStatsMapper;

    @Autowired
    private DblTrplStatsService dblTrplStatsService;

    @Autowired
    private DblTrplStatsSearchRepository dblTrplStatsSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restDblTrplStatsMockMvc;

    private DblTrplStats dblTrplStats;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        DblTrplStatsResource dblTrplStatsResource = new DblTrplStatsResource(dblTrplStatsService);
        this.restDblTrplStatsMockMvc = MockMvcBuilders.standaloneSetup(dblTrplStatsResource)
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
    public static DblTrplStats createEntity(EntityManager em) {
        DblTrplStats dblTrplStats = new DblTrplStats()
            .num1(DEFAULT_NUM_1)
            .num2(DEFAULT_NUM_2)
            .num3(DEFAULT_NUM_3)
            .timesL(DEFAULT_TIMES_L)
            .timesS(DEFAULT_TIMES_S)
            .sum(DEFAULT_SUM)
            .evens(DEFAULT_EVENS)
            .game(DEFAULT_GAME);
        return dblTrplStats;
    }

    @Before
    public void initTest() {
        dblTrplStatsSearchRepository.deleteAll();
        dblTrplStats = createEntity(em);
    }

    @Test
    @Transactional
    public void createDblTrplStats() throws Exception {
        int databaseSizeBeforeCreate = dblTrplStatsRepository.findAll().size();

        // Create the DblTrplStats
        DblTrplStatsDTO dblTrplStatsDTO = dblTrplStatsMapper.dblTrplStatsToDblTrplStatsDTO(dblTrplStats);
        restDblTrplStatsMockMvc.perform(post("/api/dbl-trpl-stats")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dblTrplStatsDTO)))
            .andExpect(status().isCreated());

        // Validate the DblTrplStats in the database
        List<DblTrplStats> dblTrplStatsList = dblTrplStatsRepository.findAll();
        assertThat(dblTrplStatsList).hasSize(databaseSizeBeforeCreate + 1);
        DblTrplStats testDblTrplStats = dblTrplStatsList.get(dblTrplStatsList.size() - 1);
        assertThat(testDblTrplStats.getNum1()).isEqualTo(DEFAULT_NUM_1);
        assertThat(testDblTrplStats.getNum2()).isEqualTo(DEFAULT_NUM_2);
        assertThat(testDblTrplStats.getNum3()).isEqualTo(DEFAULT_NUM_3);
        assertThat(testDblTrplStats.getTimesL()).isEqualTo(DEFAULT_TIMES_L);
        assertThat(testDblTrplStats.getTimesS()).isEqualTo(DEFAULT_TIMES_S);
        assertThat(testDblTrplStats.getSum()).isEqualTo(DEFAULT_SUM);
        assertThat(testDblTrplStats.getEvens()).isEqualTo(DEFAULT_EVENS);
        assertThat(testDblTrplStats.getGame()).isEqualTo(DEFAULT_GAME);

        // Validate the DblTrplStats in Elasticsearch
        DblTrplStats dblTrplStatsEs = dblTrplStatsSearchRepository.findOne(testDblTrplStats.getId());
        assertThat(dblTrplStatsEs).isEqualToComparingFieldByField(testDblTrplStats);
    }

    @Test
    @Transactional
    public void createDblTrplStatsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = dblTrplStatsRepository.findAll().size();

        // Create the DblTrplStats with an existing ID
        dblTrplStats.setId(1L);
        DblTrplStatsDTO dblTrplStatsDTO = dblTrplStatsMapper.dblTrplStatsToDblTrplStatsDTO(dblTrplStats);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDblTrplStatsMockMvc.perform(post("/api/dbl-trpl-stats")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dblTrplStatsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<DblTrplStats> dblTrplStatsList = dblTrplStatsRepository.findAll();
        assertThat(dblTrplStatsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllDblTrplStats() throws Exception {
        // Initialize the database
        dblTrplStatsRepository.saveAndFlush(dblTrplStats);

        // Get all the dblTrplStatsList
        restDblTrplStatsMockMvc.perform(get("/api/dbl-trpl-stats?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(dblTrplStats.getId().intValue())))
            .andExpect(jsonPath("$.[*].num1").value(hasItem(DEFAULT_NUM_1)))
            .andExpect(jsonPath("$.[*].num2").value(hasItem(DEFAULT_NUM_2)))
            .andExpect(jsonPath("$.[*].num3").value(hasItem(DEFAULT_NUM_3)))
            .andExpect(jsonPath("$.[*].timesL").value(hasItem(DEFAULT_TIMES_L)))
            .andExpect(jsonPath("$.[*].timesS").value(hasItem(DEFAULT_TIMES_S)))
            .andExpect(jsonPath("$.[*].sum").value(hasItem(DEFAULT_SUM)))
            .andExpect(jsonPath("$.[*].evens").value(hasItem(DEFAULT_EVENS)))
            .andExpect(jsonPath("$.[*].game").value(hasItem(DEFAULT_GAME.toString())));
    }

    @Test
    @Transactional
    public void getDblTrplStats() throws Exception {
        // Initialize the database
        dblTrplStatsRepository.saveAndFlush(dblTrplStats);

        // Get the dblTrplStats
        restDblTrplStatsMockMvc.perform(get("/api/dbl-trpl-stats/{id}", dblTrplStats.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(dblTrplStats.getId().intValue()))
            .andExpect(jsonPath("$.num1").value(DEFAULT_NUM_1))
            .andExpect(jsonPath("$.num2").value(DEFAULT_NUM_2))
            .andExpect(jsonPath("$.num3").value(DEFAULT_NUM_3))
            .andExpect(jsonPath("$.timesL").value(DEFAULT_TIMES_L))
            .andExpect(jsonPath("$.timesS").value(DEFAULT_TIMES_S))
            .andExpect(jsonPath("$.sum").value(DEFAULT_SUM))
            .andExpect(jsonPath("$.evens").value(DEFAULT_EVENS))
            .andExpect(jsonPath("$.game").value(DEFAULT_GAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingDblTrplStats() throws Exception {
        // Get the dblTrplStats
        restDblTrplStatsMockMvc.perform(get("/api/dbl-trpl-stats/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDblTrplStats() throws Exception {
        // Initialize the database
        dblTrplStatsRepository.saveAndFlush(dblTrplStats);
        dblTrplStatsSearchRepository.save(dblTrplStats);
        int databaseSizeBeforeUpdate = dblTrplStatsRepository.findAll().size();

        // Update the dblTrplStats
        DblTrplStats updatedDblTrplStats = dblTrplStatsRepository.findOne(dblTrplStats.getId());
        updatedDblTrplStats
            .num1(UPDATED_NUM_1)
            .num2(UPDATED_NUM_2)
            .num3(UPDATED_NUM_3)
            .timesL(UPDATED_TIMES_L)
            .timesS(UPDATED_TIMES_S)
            .sum(UPDATED_SUM)
            .evens(UPDATED_EVENS)
            .game(UPDATED_GAME);
        DblTrplStatsDTO dblTrplStatsDTO = dblTrplStatsMapper.dblTrplStatsToDblTrplStatsDTO(updatedDblTrplStats);

        restDblTrplStatsMockMvc.perform(put("/api/dbl-trpl-stats")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dblTrplStatsDTO)))
            .andExpect(status().isOk());

        // Validate the DblTrplStats in the database
        List<DblTrplStats> dblTrplStatsList = dblTrplStatsRepository.findAll();
        assertThat(dblTrplStatsList).hasSize(databaseSizeBeforeUpdate);
        DblTrplStats testDblTrplStats = dblTrplStatsList.get(dblTrplStatsList.size() - 1);
        assertThat(testDblTrplStats.getNum1()).isEqualTo(UPDATED_NUM_1);
        assertThat(testDblTrplStats.getNum2()).isEqualTo(UPDATED_NUM_2);
        assertThat(testDblTrplStats.getNum3()).isEqualTo(UPDATED_NUM_3);
        assertThat(testDblTrplStats.getTimesL()).isEqualTo(UPDATED_TIMES_L);
        assertThat(testDblTrplStats.getTimesS()).isEqualTo(UPDATED_TIMES_S);
        assertThat(testDblTrplStats.getSum()).isEqualTo(UPDATED_SUM);
        assertThat(testDblTrplStats.getEvens()).isEqualTo(UPDATED_EVENS);
        assertThat(testDblTrplStats.getGame()).isEqualTo(UPDATED_GAME);

        // Validate the DblTrplStats in Elasticsearch
        DblTrplStats dblTrplStatsEs = dblTrplStatsSearchRepository.findOne(testDblTrplStats.getId());
        assertThat(dblTrplStatsEs).isEqualToComparingFieldByField(testDblTrplStats);
    }

    @Test
    @Transactional
    public void updateNonExistingDblTrplStats() throws Exception {
        int databaseSizeBeforeUpdate = dblTrplStatsRepository.findAll().size();

        // Create the DblTrplStats
        DblTrplStatsDTO dblTrplStatsDTO = dblTrplStatsMapper.dblTrplStatsToDblTrplStatsDTO(dblTrplStats);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restDblTrplStatsMockMvc.perform(put("/api/dbl-trpl-stats")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dblTrplStatsDTO)))
            .andExpect(status().isCreated());

        // Validate the DblTrplStats in the database
        List<DblTrplStats> dblTrplStatsList = dblTrplStatsRepository.findAll();
        assertThat(dblTrplStatsList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteDblTrplStats() throws Exception {
        // Initialize the database
        dblTrplStatsRepository.saveAndFlush(dblTrplStats);
        dblTrplStatsSearchRepository.save(dblTrplStats);
        int databaseSizeBeforeDelete = dblTrplStatsRepository.findAll().size();

        // Get the dblTrplStats
        restDblTrplStatsMockMvc.perform(delete("/api/dbl-trpl-stats/{id}", dblTrplStats.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean dblTrplStatsExistsInEs = dblTrplStatsSearchRepository.exists(dblTrplStats.getId());
        assertThat(dblTrplStatsExistsInEs).isFalse();

        // Validate the database is empty
        List<DblTrplStats> dblTrplStatsList = dblTrplStatsRepository.findAll();
        assertThat(dblTrplStatsList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchDblTrplStats() throws Exception {
        // Initialize the database
        dblTrplStatsRepository.saveAndFlush(dblTrplStats);
        dblTrplStatsSearchRepository.save(dblTrplStats);

        // Search the dblTrplStats
        restDblTrplStatsMockMvc.perform(get("/api/_search/dbl-trpl-stats?query=id:" + dblTrplStats.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(dblTrplStats.getId().intValue())))
            .andExpect(jsonPath("$.[*].num1").value(hasItem(DEFAULT_NUM_1)))
            .andExpect(jsonPath("$.[*].num2").value(hasItem(DEFAULT_NUM_2)))
            .andExpect(jsonPath("$.[*].num3").value(hasItem(DEFAULT_NUM_3)))
            .andExpect(jsonPath("$.[*].timesL").value(hasItem(DEFAULT_TIMES_L)))
            .andExpect(jsonPath("$.[*].timesS").value(hasItem(DEFAULT_TIMES_S)))
            .andExpect(jsonPath("$.[*].sum").value(hasItem(DEFAULT_SUM)))
            .andExpect(jsonPath("$.[*].evens").value(hasItem(DEFAULT_EVENS)))
            .andExpect(jsonPath("$.[*].game").value(hasItem(DEFAULT_GAME.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DblTrplStats.class);
    }
}
