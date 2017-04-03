package ru.m2mcom.lotmicro.service.dto;


import java.io.Serializable;
import java.util.Objects;
import ru.m2mcom.lotmicro.domain.enumeration.GamesPlay;

/**
 * A DTO for the DblTrplStats entity.
 */
public class DblTrplStatsDTO implements Serializable {

    private Long id;

    private Integer num1;

    private Integer num2;

    private Integer num3;

    private Integer timesL;

    private Integer timesS;

    private Integer sum;

    private Integer evens;

    private GamesPlay game;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public Integer getNum1() {
        return num1;
    }

    public void setNum1(Integer num1) {
        this.num1 = num1;
    }
    public Integer getNum2() {
        return num2;
    }

    public void setNum2(Integer num2) {
        this.num2 = num2;
    }
    public Integer getNum3() {
        return num3;
    }

    public void setNum3(Integer num3) {
        this.num3 = num3;
    }
    public Integer getTimesL() {
        return timesL;
    }

    public void setTimesL(Integer timesL) {
        this.timesL = timesL;
    }
    public Integer getTimesS() {
        return timesS;
    }

    public void setTimesS(Integer timesS) {
        this.timesS = timesS;
    }
    public Integer getSum() {
        return sum;
    }

    public void setSum(Integer sum) {
        this.sum = sum;
    }
    public Integer getEvens() {
        return evens;
    }

    public void setEvens(Integer evens) {
        this.evens = evens;
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

        DblTrplStatsDTO dblTrplStatsDTO = (DblTrplStatsDTO) o;

        if ( ! Objects.equals(id, dblTrplStatsDTO.id)) { return false; }

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "DblTrplStatsDTO{" +
            "id=" + id +
            ", num1='" + num1 + "'" +
            ", num2='" + num2 + "'" +
            ", num3='" + num3 + "'" +
            ", timesL='" + timesL + "'" +
            ", timesS='" + timesS + "'" +
            ", sum='" + sum + "'" +
            ", evens='" + evens + "'" +
            ", game='" + game + "'" +
            '}';
    }
}
