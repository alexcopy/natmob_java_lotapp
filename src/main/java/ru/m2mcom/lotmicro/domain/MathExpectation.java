package ru.m2mcom.lotmicro.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

import ru.m2mcom.lotmicro.domain.enumeration.DrawType;

import ru.m2mcom.lotmicro.domain.enumeration.GamesPlay;

/**
 * A MathExpectation.
 */
@Entity
@Table(name = "math_expectation")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "mathexpectation")
public class MathExpectation implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "draw_date")
    private LocalDate drawDate;

    @NotNull
    @Column(name = "draw", nullable = false)
    private String draw;

    @Column(name = "total")
    private Double total;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "draw_type", nullable = false)
    private DrawType drawType;

    @Column(name = "strategy")
    private String strategy;

    @Column(name = "predict_data_date")
    private String predictDataDate;

    @Column(name = "hash")
    private String hash;

    @NotNull
    @Column(name = "drawid", nullable = false)
    private Integer drawid;

    @Enumerated(EnumType.STRING)
    @Column(name = "game")
    private GamesPlay game;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDrawDate() {
        return drawDate;
    }

    public MathExpectation drawDate(LocalDate drawDate) {
        this.drawDate = drawDate;
        return this;
    }

    public void setDrawDate(LocalDate drawDate) {
        this.drawDate = drawDate;
    }

    public String getDraw() {
        return draw;
    }

    public MathExpectation draw(String draw) {
        this.draw = draw;
        return this;
    }

    public void setDraw(String draw) {
        this.draw = draw;
    }

    public Double getTotal() {
        return total;
    }

    public MathExpectation total(Double total) {
        this.total = total;
        return this;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public DrawType getDrawType() {
        return drawType;
    }

    public MathExpectation drawType(DrawType drawType) {
        this.drawType = drawType;
        return this;
    }

    public void setDrawType(DrawType drawType) {
        this.drawType = drawType;
    }

    public String getStrategy() {
        return strategy;
    }

    public MathExpectation strategy(String strategy) {
        this.strategy = strategy;
        return this;
    }

    public void setStrategy(String strategy) {
        this.strategy = strategy;
    }

    public String getPredictDataDate() {
        return predictDataDate;
    }

    public MathExpectation predictDataDate(String predictDataDate) {
        this.predictDataDate = predictDataDate;
        return this;
    }

    public void setPredictDataDate(String predictDataDate) {
        this.predictDataDate = predictDataDate;
    }

    public String getHash() {
        return hash;
    }

    public MathExpectation hash(String hash) {
        this.hash = hash;
        return this;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public Integer getDrawid() {
        return drawid;
    }

    public MathExpectation drawid(Integer drawid) {
        this.drawid = drawid;
        return this;
    }

    public void setDrawid(Integer drawid) {
        this.drawid = drawid;
    }

    public GamesPlay getGame() {
        return game;
    }

    public MathExpectation game(GamesPlay game) {
        this.game = game;
        return this;
    }

    public void setGame(GamesPlay game) {
        this.game = game;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        MathExpectation mathExpectation = (MathExpectation) o;
        if (mathExpectation.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, mathExpectation.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "MathExpectation{" +
            "id=" + id +
            ", drawDate='" + drawDate + "'" +
            ", draw='" + draw + "'" +
            ", total='" + total + "'" +
            ", drawType='" + drawType + "'" +
            ", strategy='" + strategy + "'" +
            ", predictDataDate='" + predictDataDate + "'" +
            ", hash='" + hash + "'" +
            ", drawid='" + drawid + "'" +
            ", game='" + game + "'" +
            '}';
    }
}
