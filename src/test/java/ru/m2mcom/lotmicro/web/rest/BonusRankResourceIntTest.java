package ru.m2mcom.lotmicro.web.rest;

import ru.m2mcom.lotmicro.LotmicroApp;

import ru.m2mcom.lotmicro.domain.BonusRank;
import ru.m2mcom.lotmicro.repository.BonusRankRepository;
import ru.m2mcom.lotmicro.service.BonusRankService;
import ru.m2mcom.lotmicro.repository.search.BonusRankSearchRepository;
import ru.m2mcom.lotmicro.service.dto.BonusRankDTO;
import ru.m2mcom.lotmicro.service.mapper.BonusRankMapper;
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
 * Test class for the BonusRankResource REST controller.
 *
 * @see BonusRankResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = LotmicroApp.class)
public class BonusRankResourceIntTest {

    private static final Double DEFAULT_SUMM_IN_RECENT_HIST_RANK = 1D;
    private static final Double UPDATED_SUMM_IN_RECENT_HIST_RANK = 2D;

    private static final Double DEFAULT_PROBABILITY_AND_MO = 1D;
    private static final Double UPDATED_PROBABILITY_AND_MO = 2D;

    private static final Double DEFAULT_LONG_ODD_EVEN_RANK = 1D;
    private static final Double UPDATED_LONG_ODD_EVEN_RANK = 2D;

    private static final Double DEFAULT_SUMM_ANALISIS_WITH_WEIGHTS = 1D;
    private static final Double UPDATED_SUMM_ANALISIS_WITH_WEIGHTS = 2D;

    private static final Double DEFAULT_IN_HIST_DUE_NOT_DUE_ANALISIS = 1D;
    private static final Double UPDATED_IN_HIST_DUE_NOT_DUE_ANALISIS = 2D;

    private static final Double DEFAULT_ALL_NUMBERS_RANK = 1D;
    private static final Double UPDATED_ALL_NUMBERS_RANK = 2D;

    private static final Double DEFAULT_TOTAL_RANK = 1D;
    private static final Double UPDATED_TOTAL_RANK = 2D;

    @Autowired
    private BonusRankRepository bonusRankRepository;

    @Autowired
    private BonusRankMapper bonusRankMapper;

    @Autowired
    private BonusRankService bonusRankService;

    @Autowired
    private BonusRankSearchRepository bonusRankSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restBonusRankMockMvc;

    private BonusRank bonusRank;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        BonusRankResource bonusRankResource = new BonusRankResource(bonusRankService);
        this.restBonusRankMockMvc = MockMvcBuilders.standaloneSetup(bonusRankResource)
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
    public static BonusRank createEntity(EntityManager em) {
        BonusRank bonusRank = new BonusRank()
            .summInRecentHistRank(DEFAULT_SUMM_IN_RECENT_HIST_RANK)
            .probabilityAndMO(DEFAULT_PROBABILITY_AND_MO)
            .longOddEvenRank(DEFAULT_LONG_ODD_EVEN_RANK)
            .summAnalisisWithWeights(DEFAULT_SUMM_ANALISIS_WITH_WEIGHTS)
            .inHistDueNotDueAnalisis(DEFAULT_IN_HIST_DUE_NOT_DUE_ANALISIS)
            .allNumbersRank(DEFAULT_ALL_NUMBERS_RANK)
            .totalRank(DEFAULT_TOTAL_RANK);
        return bonusRank;
    }

    @Before
    public void initTest() {
        bonusRankSearchRepository.deleteAll();
        bonusRank = createEntity(em);
    }

    @Test
    @Transactional
    public void createBonusRank() throws Exception {
        int databaseSizeBeforeCreate = bonusRankRepository.findAll().size();

        // Create the BonusRank
        BonusRankDTO bonusRankDTO = bonusRankMapper.bonusRankToBonusRankDTO(bonusRank);
        restBonusRankMockMvc.perform(post("/api/bonus-ranks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bonusRankDTO)))
            .andExpect(status().isCreated());

        // Validate the BonusRank in the database
        List<BonusRank> bonusRankList = bonusRankRepository.findAll();
        assertThat(bonusRankList).hasSize(databaseSizeBeforeCreate + 1);
        BonusRank testBonusRank = bonusRankList.get(bonusRankList.size() - 1);
        assertThat(testBonusRank.getSummInRecentHistRank()).isEqualTo(DEFAULT_SUMM_IN_RECENT_HIST_RANK);
        assertThat(testBonusRank.getProbabilityAndMO()).isEqualTo(DEFAULT_PROBABILITY_AND_MO);
        assertThat(testBonusRank.getLongOddEvenRank()).isEqualTo(DEFAULT_LONG_ODD_EVEN_RANK);
        assertThat(testBonusRank.getSummAnalisisWithWeights()).isEqualTo(DEFAULT_SUMM_ANALISIS_WITH_WEIGHTS);
        assertThat(testBonusRank.getInHistDueNotDueAnalisis()).isEqualTo(DEFAULT_IN_HIST_DUE_NOT_DUE_ANALISIS);
        assertThat(testBonusRank.getAllNumbersRank()).isEqualTo(DEFAULT_ALL_NUMBERS_RANK);
        assertThat(testBonusRank.getTotalRank()).isEqualTo(DEFAULT_TOTAL_RANK);

        // Validate the BonusRank in Elasticsearch
        BonusRank bonusRankEs = bonusRankSearchRepository.findOne(testBonusRank.getId());
        assertThat(bonusRankEs).isEqualToComparingFieldByField(testBonusRank);
    }

    @Test
    @Transactional
    public void createBonusRankWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = bonusRankRepository.findAll().size();

        // Create the BonusRank with an existing ID
        bonusRank.setId(1L);
        BonusRankDTO bonusRankDTO = bonusRankMapper.bonusRankToBonusRankDTO(bonusRank);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBonusRankMockMvc.perform(post("/api/bonus-ranks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bonusRankDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<BonusRank> bonusRankList = bonusRankRepository.findAll();
        assertThat(bonusRankList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllBonusRanks() throws Exception {
        // Initialize the database
        bonusRankRepository.saveAndFlush(bonusRank);

        // Get all the bonusRankList
        restBonusRankMockMvc.perform(get("/api/bonus-ranks?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(bonusRank.getId().intValue())))
            .andExpect(jsonPath("$.[*].summInRecentHistRank").value(hasItem(DEFAULT_SUMM_IN_RECENT_HIST_RANK.doubleValue())))
            .andExpect(jsonPath("$.[*].probabilityAndMO").value(hasItem(DEFAULT_PROBABILITY_AND_MO.doubleValue())))
            .andExpect(jsonPath("$.[*].longOddEvenRank").value(hasItem(DEFAULT_LONG_ODD_EVEN_RANK.doubleValue())))
            .andExpect(jsonPath("$.[*].summAnalisisWithWeights").value(hasItem(DEFAULT_SUMM_ANALISIS_WITH_WEIGHTS.doubleValue())))
            .andExpect(jsonPath("$.[*].inHistDueNotDueAnalisis").value(hasItem(DEFAULT_IN_HIST_DUE_NOT_DUE_ANALISIS.doubleValue())))
            .andExpect(jsonPath("$.[*].allNumbersRank").value(hasItem(DEFAULT_ALL_NUMBERS_RANK.doubleValue())))
            .andExpect(jsonPath("$.[*].totalRank").value(hasItem(DEFAULT_TOTAL_RANK.doubleValue())));
    }

    @Test
    @Transactional
    public void getBonusRank() throws Exception {
        // Initialize the database
        bonusRankRepository.saveAndFlush(bonusRank);

        // Get the bonusRank
        restBonusRankMockMvc.perform(get("/api/bonus-ranks/{id}", bonusRank.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(bonusRank.getId().intValue()))
            .andExpect(jsonPath("$.summInRecentHistRank").value(DEFAULT_SUMM_IN_RECENT_HIST_RANK.doubleValue()))
            .andExpect(jsonPath("$.probabilityAndMO").value(DEFAULT_PROBABILITY_AND_MO.doubleValue()))
            .andExpect(jsonPath("$.longOddEvenRank").value(DEFAULT_LONG_ODD_EVEN_RANK.doubleValue()))
            .andExpect(jsonPath("$.summAnalisisWithWeights").value(DEFAULT_SUMM_ANALISIS_WITH_WEIGHTS.doubleValue()))
            .andExpect(jsonPath("$.inHistDueNotDueAnalisis").value(DEFAULT_IN_HIST_DUE_NOT_DUE_ANALISIS.doubleValue()))
            .andExpect(jsonPath("$.allNumbersRank").value(DEFAULT_ALL_NUMBERS_RANK.doubleValue()))
            .andExpect(jsonPath("$.totalRank").value(DEFAULT_TOTAL_RANK.doubleValue()));
    }

    @Test
    @Transactional
    public void getNonExistingBonusRank() throws Exception {
        // Get the bonusRank
        restBonusRankMockMvc.perform(get("/api/bonus-ranks/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBonusRank() throws Exception {
        // Initialize the database
        bonusRankRepository.saveAndFlush(bonusRank);
        bonusRankSearchRepository.save(bonusRank);
        int databaseSizeBeforeUpdate = bonusRankRepository.findAll().size();

        // Update the bonusRank
        BonusRank updatedBonusRank = bonusRankRepository.findOne(bonusRank.getId());
        updatedBonusRank
            .summInRecentHistRank(UPDATED_SUMM_IN_RECENT_HIST_RANK)
            .probabilityAndMO(UPDATED_PROBABILITY_AND_MO)
            .longOddEvenRank(UPDATED_LONG_ODD_EVEN_RANK)
            .summAnalisisWithWeights(UPDATED_SUMM_ANALISIS_WITH_WEIGHTS)
            .inHistDueNotDueAnalisis(UPDATED_IN_HIST_DUE_NOT_DUE_ANALISIS)
            .allNumbersRank(UPDATED_ALL_NUMBERS_RANK)
            .totalRank(UPDATED_TOTAL_RANK);
        BonusRankDTO bonusRankDTO = bonusRankMapper.bonusRankToBonusRankDTO(updatedBonusRank);

        restBonusRankMockMvc.perform(put("/api/bonus-ranks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bonusRankDTO)))
            .andExpect(status().isOk());

        // Validate the BonusRank in the database
        List<BonusRank> bonusRankList = bonusRankRepository.findAll();
        assertThat(bonusRankList).hasSize(databaseSizeBeforeUpdate);
        BonusRank testBonusRank = bonusRankList.get(bonusRankList.size() - 1);
        assertThat(testBonusRank.getSummInRecentHistRank()).isEqualTo(UPDATED_SUMM_IN_RECENT_HIST_RANK);
        assertThat(testBonusRank.getProbabilityAndMO()).isEqualTo(UPDATED_PROBABILITY_AND_MO);
        assertThat(testBonusRank.getLongOddEvenRank()).isEqualTo(UPDATED_LONG_ODD_EVEN_RANK);
        assertThat(testBonusRank.getSummAnalisisWithWeights()).isEqualTo(UPDATED_SUMM_ANALISIS_WITH_WEIGHTS);
        assertThat(testBonusRank.getInHistDueNotDueAnalisis()).isEqualTo(UPDATED_IN_HIST_DUE_NOT_DUE_ANALISIS);
        assertThat(testBonusRank.getAllNumbersRank()).isEqualTo(UPDATED_ALL_NUMBERS_RANK);
        assertThat(testBonusRank.getTotalRank()).isEqualTo(UPDATED_TOTAL_RANK);

        // Validate the BonusRank in Elasticsearch
        BonusRank bonusRankEs = bonusRankSearchRepository.findOne(testBonusRank.getId());
        assertThat(bonusRankEs).isEqualToComparingFieldByField(testBonusRank);
    }

    @Test
    @Transactional
    public void updateNonExistingBonusRank() throws Exception {
        int databaseSizeBeforeUpdate = bonusRankRepository.findAll().size();

        // Create the BonusRank
        BonusRankDTO bonusRankDTO = bonusRankMapper.bonusRankToBonusRankDTO(bonusRank);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restBonusRankMockMvc.perform(put("/api/bonus-ranks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bonusRankDTO)))
            .andExpect(status().isCreated());

        // Validate the BonusRank in the database
        List<BonusRank> bonusRankList = bonusRankRepository.findAll();
        assertThat(bonusRankList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteBonusRank() throws Exception {
        // Initialize the database
        bonusRankRepository.saveAndFlush(bonusRank);
        bonusRankSearchRepository.save(bonusRank);
        int databaseSizeBeforeDelete = bonusRankRepository.findAll().size();

        // Get the bonusRank
        restBonusRankMockMvc.perform(delete("/api/bonus-ranks/{id}", bonusRank.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean bonusRankExistsInEs = bonusRankSearchRepository.exists(bonusRank.getId());
        assertThat(bonusRankExistsInEs).isFalse();

        // Validate the database is empty
        List<BonusRank> bonusRankList = bonusRankRepository.findAll();
        assertThat(bonusRankList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchBonusRank() throws Exception {
        // Initialize the database
        bonusRankRepository.saveAndFlush(bonusRank);
        bonusRankSearchRepository.save(bonusRank);

        // Search the bonusRank
        restBonusRankMockMvc.perform(get("/api/_search/bonus-ranks?query=id:" + bonusRank.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(bonusRank.getId().intValue())))
            .andExpect(jsonPath("$.[*].summInRecentHistRank").value(hasItem(DEFAULT_SUMM_IN_RECENT_HIST_RANK.doubleValue())))
            .andExpect(jsonPath("$.[*].probabilityAndMO").value(hasItem(DEFAULT_PROBABILITY_AND_MO.doubleValue())))
            .andExpect(jsonPath("$.[*].longOddEvenRank").value(hasItem(DEFAULT_LONG_ODD_EVEN_RANK.doubleValue())))
            .andExpect(jsonPath("$.[*].summAnalisisWithWeights").value(hasItem(DEFAULT_SUMM_ANALISIS_WITH_WEIGHTS.doubleValue())))
            .andExpect(jsonPath("$.[*].inHistDueNotDueAnalisis").value(hasItem(DEFAULT_IN_HIST_DUE_NOT_DUE_ANALISIS.doubleValue())))
            .andExpect(jsonPath("$.[*].allNumbersRank").value(hasItem(DEFAULT_ALL_NUMBERS_RANK.doubleValue())))
            .andExpect(jsonPath("$.[*].totalRank").value(hasItem(DEFAULT_TOTAL_RANK.doubleValue())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(BonusRank.class);
    }
}
