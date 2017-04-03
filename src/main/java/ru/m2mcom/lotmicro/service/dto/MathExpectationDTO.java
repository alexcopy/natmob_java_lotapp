package ru.m2mcom.lotmicro.service.dto;

import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import ru.m2mcom.lotmicro.domain.enumeration.DrawType;
import ru.m2mcom.lotmicro.domain.enumeration.GamesPlay;

/**
 * A DTO for the MathExpectation entity.
 */
public class MathExpectationDTO implements Serializable {

    private Long id;

    private LocalDate drawDate;

    @NotNull
    private String draw;

    private Double total;

    @NotNull
    private DrawType drawType;

    private String strategy;

    private String predictDataDate;

    private String hash;

    @NotNull
    private Integer drawid;

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

    public void setDrawDate(LocalDate drawDate) {
        this.drawDate = drawDate;
    }
    public String getDraw() {
        return draw;
    }

    public void setDraw(String draw) {
        this.draw = draw;
    }
    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }
    public DrawType getDrawType() {
        return drawType;
    }

    public void setDrawType(DrawType drawType) {
        this.drawType = drawType;
    }
    public String getStrategy() {
        return strategy;
    }

    public void setStrategy(String strategy) {
        this.strategy = strategy;
    }
    public String getPredictDataDate() {
        return predictDataDate;
    }

    public void setPredictDataDate(String predictDataDate) {
        this.predictDataDate = predictDataDate;
    }
    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }
    public Integer getDrawid() {
        return drawid;
    }

    public void setDrawid(Integer drawid) {
        this.drawid = drawid;
    }
    public GamesPlay getGame() {
        return game;
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

        MathExpectationDTO mathExpectationDTO = (MathExpectationDTO) o;

        if ( ! Objects.equals(id, mathExpectationDTO.id)) { return false; }

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "MathExpectationDTO{" +
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
