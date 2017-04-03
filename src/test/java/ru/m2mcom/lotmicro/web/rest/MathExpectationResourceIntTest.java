package ru.m2mcom.lotmicro.web.rest;

import ru.m2mcom.lotmicro.LotmicroApp;

import ru.m2mcom.lotmicro.domain.MathExpectation;
import ru.m2mcom.lotmicro.repository.MathExpectationRepository;
import ru.m2mcom.lotmicro.service.MathExpectationService;
import ru.m2mcom.lotmicro.repository.search.MathExpectationSearchRepository;
import ru.m2mcom.lotmicro.service.dto.MathExpectationDTO;
import ru.m2mcom.lotmicro.service.mapper.MathExpectationMapper;
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

import ru.m2mcom.lotmicro.domain.enumeration.DrawType;
import ru.m2mcom.lotmicro.domain.enumeration.GamesPlay;
/**
 * Test class for the MathExpectationResource REST controller.
 *
 * @see MathExpectationResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = LotmicroApp.class)
public class MathExpectationResourceIntTest {

    private static final LocalDate DEFAULT_DRAW_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DRAW_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_DRAW = "AAAAAAAAAA";
    private static final String UPDATED_DRAW = "BBBBBBBBBB";

    private static final Double DEFAULT_TOTAL = 1D;
    private static final Double UPDATED_TOTAL = 2D;

    private static final DrawType DEFAULT_DRAW_TYPE = DrawType.HISTORY;
    private static final DrawType UPDATED_DRAW_TYPE = DrawType.LOCALPLAY;

    private static final String DEFAULT_STRATEGY = "AAAAAAAAAA";
    private static final String UPDATED_STRATEGY = "BBBBBBBBBB";

    private static final String DEFAULT_PREDICT_DATA_DATE = "AAAAAAAAAA";
    private static final String UPDATED_PREDICT_DATA_DATE = "BBBBBBBBBB";

    private static final String DEFAULT_HASH = "AAAAAAAAAA";
    private static final String UPDATED_HASH = "BBBBBBBBBB";

    private static final Integer DEFAULT_DRAWID = 1;
    private static final Integer UPDATED_DRAWID = 2;

    private static final GamesPlay DEFAULT_GAME = GamesPlay.EML;
    private static final GamesPlay UPDATED_GAME = GamesPlay.NAT;

    @Autowired
    private MathExpectationRepository mathExpectationRepository;

    @Autowired
    private MathExpectationMapper mathExpectationMapper;

    @Autowired
    private MathExpectationService mathExpectationService;

    @Autowired
    private MathExpectationSearchRepository mathExpectationSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restMathExpectationMockMvc;

    private MathExpectation mathExpectation;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        MathExpectationResource mathExpectationResource = new MathExpectationResource(mathExpectationService);
        this.restMathExpectationMockMvc = MockMvcBuilders.standaloneSetup(mathExpectationResource)
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
    public static MathExpectation createEntity(EntityManager em) {
        MathExpectation mathExpectation = new MathExpectation()
            .drawDate(DEFAULT_DRAW_DATE)
            .draw(DEFAULT_DRAW)
            .total(DEFAULT_TOTAL)
            .drawType(DEFAULT_DRAW_TYPE)
            .strategy(DEFAULT_STRATEGY)
            .predictDataDate(DEFAULT_PREDICT_DATA_DATE)
            .hash(DEFAULT_HASH)
            .drawid(DEFAULT_DRAWID)
            .game(DEFAULT_GAME);
        return mathExpectation;
    }

    @Before
    public void initTest() {
        mathExpectationSearchRepository.deleteAll();
        mathExpectation = createEntity(em);
    }

    @Test
    @Transactional
    public void createMathExpectation() throws Exception {
        int databaseSizeBeforeCreate = mathExpectationRepository.findAll().size();

        // Create the MathExpectation
        MathExpectationDTO mathExpectationDTO = mathExpectationMapper.mathExpectationToMathExpectationDTO(mathExpectation);
        restMathExpectationMockMvc.perform(post("/api/math-expectations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mathExpectationDTO)))
            .andExpect(status().isCreated());

        // Validate the MathExpectation in the database
        List<MathExpectation> mathExpectationList = mathExpectationRepository.findAll();
        assertThat(mathExpectationList).hasSize(databaseSizeBeforeCreate + 1);
        MathExpectation testMathExpectation = mathExpectationList.get(mathExpectationList.size() - 1);
        assertThat(testMathExpectation.getDrawDate()).isEqualTo(DEFAULT_DRAW_DATE);
        assertThat(testMathExpectation.getDraw()).isEqualTo(DEFAULT_DRAW);
        assertThat(testMathExpectation.getTotal()).isEqualTo(DEFAULT_TOTAL);
        assertThat(testMathExpectation.getDrawType()).isEqualTo(DEFAULT_DRAW_TYPE);
        assertThat(testMathExpectation.getStrategy()).isEqualTo(DEFAULT_STRATEGY);
        assertThat(testMathExpectation.getPredictDataDate()).isEqualTo(DEFAULT_PREDICT_DATA_DATE);
        assertThat(testMathExpectation.getHash()).isEqualTo(DEFAULT_HASH);
        assertThat(testMathExpectation.getDrawid()).isEqualTo(DEFAULT_DRAWID);
        assertThat(testMathExpectation.getGame()).isEqualTo(DEFAULT_GAME);

        // Validate the MathExpectation in Elasticsearch
        MathExpectation mathExpectationEs = mathExpectationSearchRepository.findOne(testMathExpectation.getId());
        assertThat(mathExpectationEs).isEqualToComparingFieldByField(testMathExpectation);
    }

    @Test
    @Transactional
    public void createMathExpectationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = mathExpectationRepository.findAll().size();

        // Create the MathExpectation with an existing ID
        mathExpectation.setId(1L);
        MathExpectationDTO mathExpectationDTO = mathExpectationMapper.mathExpectationToMathExpectationDTO(mathExpectation);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMathExpectationMockMvc.perform(post("/api/math-expectations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mathExpectationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<MathExpectation> mathExpectationList = mathExpectationRepository.findAll();
        assertThat(mathExpectationList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkDrawIsRequired() throws Exception {
        int databaseSizeBeforeTest = mathExpectationRepository.findAll().size();
        // set the field null
        mathExpectation.setDraw(null);

        // Create the MathExpectation, which fails.
        MathExpectationDTO mathExpectationDTO = mathExpectationMapper.mathExpectationToMathExpectationDTO(mathExpectation);

        restMathExpectationMockMvc.perform(post("/api/math-expectations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mathExpectationDTO)))
            .andExpect(status().isBadRequest());

        List<MathExpectation> mathExpectationList = mathExpectationRepository.findAll();
        assertThat(mathExpectationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDrawTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = mathExpectationRepository.findAll().size();
        // set the field null
        mathExpectation.setDrawType(null);

        // Create the MathExpectation, which fails.
        MathExpectationDTO mathExpectationDTO = mathExpectationMapper.mathExpectationToMathExpectationDTO(mathExpectation);

        restMathExpectationMockMvc.perform(post("/api/math-expectations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mathExpectationDTO)))
            .andExpect(status().isBadRequest());

        List<MathExpectation> mathExpectationList = mathExpectationRepository.findAll();
        assertThat(mathExpectationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDrawidIsRequired() throws Exception {
        int databaseSizeBeforeTest = mathExpectationRepository.findAll().size();
        // set the field null
        mathExpectation.setDrawid(null);

        // Create the MathExpectation, which fails.
        MathExpectationDTO mathExpectationDTO = mathExpectationMapper.mathExpectationToMathExpectationDTO(mathExpectation);

        restMathExpectationMockMvc.perform(post("/api/math-expectations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mathExpectationDTO)))
            .andExpect(status().isBadRequest());

        List<MathExpectation> mathExpectationList = mathExpectationRepository.findAll();
        assertThat(mathExpectationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllMathExpectations() throws Exception {
        // Initialize the database
        mathExpectationRepository.saveAndFlush(mathExpectation);

        // Get all the mathExpectationList
        restMathExpectationMockMvc.perform(get("/api/math-expectations?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mathExpectation.getId().intValue())))
            .andExpect(jsonPath("$.[*].drawDate").value(hasItem(DEFAULT_DRAW_DATE.toString())))
            .andExpect(jsonPath("$.[*].draw").value(hasItem(DEFAULT_DRAW.toString())))
            .andExpect(jsonPath("$.[*].total").value(hasItem(DEFAULT_TOTAL.doubleValue())))
            .andExpect(jsonPath("$.[*].drawType").value(hasItem(DEFAULT_DRAW_TYPE.toString())))
            .andExpect(jsonPath("$.[*].strategy").value(hasItem(DEFAULT_STRATEGY.toString())))
            .andExpect(jsonPath("$.[*].predictDataDate").value(hasItem(DEFAULT_PREDICT_DATA_DATE.toString())))
            .andExpect(jsonPath("$.[*].hash").value(hasItem(DEFAULT_HASH.toString())))
            .andExpect(jsonPath("$.[*].drawid").value(hasItem(DEFAULT_DRAWID)))
            .andExpect(jsonPath("$.[*].game").value(hasItem(DEFAULT_GAME.toString())));
    }

    @Test
    @Transactional
    public void getMathExpectation() throws Exception {
        // Initialize the database
        mathExpectationRepository.saveAndFlush(mathExpectation);

        // Get the mathExpectation
        restMathExpectationMockMvc.perform(get("/api/math-expectations/{id}", mathExpectation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(mathExpectation.getId().intValue()))
            .andExpect(jsonPath("$.drawDate").value(DEFAULT_DRAW_DATE.toString()))
            .andExpect(jsonPath("$.draw").value(DEFAULT_DRAW.toString()))
            .andExpect(jsonPath("$.total").value(DEFAULT_TOTAL.doubleValue()))
            .andExpect(jsonPath("$.drawType").value(DEFAULT_DRAW_TYPE.toString()))
            .andExpect(jsonPath("$.strategy").value(DEFAULT_STRATEGY.toString()))
            .andExpect(jsonPath("$.predictDataDate").value(DEFAULT_PREDICT_DATA_DATE.toString()))
            .andExpect(jsonPath("$.hash").value(DEFAULT_HASH.toString()))
            .andExpect(jsonPath("$.drawid").value(DEFAULT_DRAWID))
            .andExpect(jsonPath("$.game").value(DEFAULT_GAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingMathExpectation() throws Exception {
        // Get the mathExpectation
        restMathExpectationMockMvc.perform(get("/api/math-expectations/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMathExpectation() throws Exception {
        // Initialize the database
        mathExpectationRepository.saveAndFlush(mathExpectation);
        mathExpectationSearchRepository.save(mathExpectation);
        int databaseSizeBeforeUpdate = mathExpectationRepository.findAll().size();

        // Update the mathExpectation
        MathExpectation updatedMathExpectation = mathExpectationRepository.findOne(mathExpectation.getId());
        updatedMathExpectation
            .drawDate(UPDATED_DRAW_DATE)
            .draw(UPDATED_DRAW)
            .total(UPDATED_TOTAL)
            .drawType(UPDATED_DRAW_TYPE)
            .strategy(UPDATED_STRATEGY)
            .predictDataDate(UPDATED_PREDICT_DATA_DATE)
            .hash(UPDATED_HASH)
            .drawid(UPDATED_DRAWID)
            .game(UPDATED_GAME);
        MathExpectationDTO mathExpectationDTO = mathExpectationMapper.mathExpectationToMathExpectationDTO(updatedMathExpectation);

        restMathExpectationMockMvc.perform(put("/api/math-expectations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mathExpectationDTO)))
            .andExpect(status().isOk());

        // Validate the MathExpectation in the database
        List<MathExpectation> mathExpectationList = mathExpectationRepository.findAll();
        assertThat(mathExpectationList).hasSize(databaseSizeBeforeUpdate);
        MathExpectation testMathExpectation = mathExpectationList.get(mathExpectationList.size() - 1);
        assertThat(testMathExpectation.getDrawDate()).isEqualTo(UPDATED_DRAW_DATE);
        assertThat(testMathExpectation.getDraw()).isEqualTo(UPDATED_DRAW);
        assertThat(testMathExpectation.getTotal()).isEqualTo(UPDATED_TOTAL);
        assertThat(testMathExpectation.getDrawType()).isEqualTo(UPDATED_DRAW_TYPE);
        assertThat(testMathExpectation.getStrategy()).isEqualTo(UPDATED_STRATEGY);
        assertThat(testMathExpectation.getPredictDataDate()).isEqualTo(UPDATED_PREDICT_DATA_DATE);
        assertThat(testMathExpectation.getHash()).isEqualTo(UPDATED_HASH);
        assertThat(testMathExpectation.getDrawid()).isEqualTo(UPDATED_DRAWID);
        assertThat(testMathExpectation.getGame()).isEqualTo(UPDATED_GAME);

        // Validate the MathExpectation in Elasticsearch
        MathExpectation mathExpectationEs = mathExpectationSearchRepository.findOne(testMathExpectation.getId());
        assertThat(mathExpectationEs).isEqualToComparingFieldByField(testMathExpectation);
    }

    @Test
    @Transactional
    public void updateNonExistingMathExpectation() throws Exception {
        int databaseSizeBeforeUpdate = mathExpectationRepository.findAll().size();

        // Create the MathExpectation
        MathExpectationDTO mathExpectationDTO = mathExpectationMapper.mathExpectationToMathExpectationDTO(mathExpectation);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restMathExpectationMockMvc.perform(put("/api/math-expectations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mathExpectationDTO)))
            .andExpect(status().isCreated());

        // Validate the MathExpectation in the database
        List<MathExpectation> mathExpectationList = mathExpectationRepository.findAll();
        assertThat(mathExpectationList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteMathExpectation() throws Exception {
        // Initialize the database
        mathExpectationRepository.saveAndFlush(mathExpectation);
        mathExpectationSearchRepository.save(mathExpectation);
        int databaseSizeBeforeDelete = mathExpectationRepository.findAll().size();

        // Get the mathExpectation
        restMathExpectationMockMvc.perform(delete("/api/math-expectations/{id}", mathExpectation.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean mathExpectationExistsInEs = mathExpectationSearchRepository.exists(mathExpectation.getId());
        assertThat(mathExpectationExistsInEs).isFalse();

        // Validate the database is empty
        List<MathExpectation> mathExpectationList = mathExpectationRepository.findAll();
        assertThat(mathExpectationList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchMathExpectation() throws Exception {
        // Initialize the database
        mathExpectationRepository.saveAndFlush(mathExpectation);
        mathExpectationSearchRepository.save(mathExpectation);

        // Search the mathExpectation
        restMathExpectationMockMvc.perform(get("/api/_search/math-expectations?query=id:" + mathExpectation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mathExpectation.getId().intValue())))
            .andExpect(jsonPath("$.[*].drawDate").value(hasItem(DEFAULT_DRAW_DATE.toString())))
            .andExpect(jsonPath("$.[*].draw").value(hasItem(DEFAULT_DRAW.toString())))
            .andExpect(jsonPath("$.[*].total").value(hasItem(DEFAULT_TOTAL.doubleValue())))
            .andExpect(jsonPath("$.[*].drawType").value(hasItem(DEFAULT_DRAW_TYPE.toString())))
            .andExpect(jsonPath("$.[*].strategy").value(hasItem(DEFAULT_STRATEGY.toString())))
            .andExpect(jsonPath("$.[*].predictDataDate").value(hasItem(DEFAULT_PREDICT_DATA_DATE.toString())))
            .andExpect(jsonPath("$.[*].hash").value(hasItem(DEFAULT_HASH.toString())))
            .andExpect(jsonPath("$.[*].drawid").value(hasItem(DEFAULT_DRAWID)))
            .andExpect(jsonPath("$.[*].game").value(hasItem(DEFAULT_GAME.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MathExpectation.class);
    }
}
