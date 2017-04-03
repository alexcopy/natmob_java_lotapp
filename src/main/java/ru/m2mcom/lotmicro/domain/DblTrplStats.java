package ru.m2mcom.lotmicro.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

import ru.m2mcom.lotmicro.domain.enumeration.GamesPlay;

/**
 * A DblTrplStats.
 */
@Entity
@Table(name = "dbl_trpl_stats")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "dbltrplstats")
public class DblTrplStats implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "num_1")
    private Integer num1;

    @Column(name = "num_2")
    private Integer num2;

    @Column(name = "num_3")
    private Integer num3;

    @Column(name = "times_l")
    private Integer timesL;

    @Column(name = "times_s")
    private Integer timesS;

    @Column(name = "sum")
    private Integer sum;

    @Column(name = "evens")
    private Integer evens;

    @Enumerated(EnumType.STRING)
    @Column(name = "game")
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

    public DblTrplStats num1(Integer num1) {
        this.num1 = num1;
        return this;
    }

    public void setNum1(Integer num1) {
        this.num1 = num1;
    }

    public Integer getNum2() {
        return num2;
    }

    public DblTrplStats num2(Integer num2) {
        this.num2 = num2;
        return this;
    }

    public void setNum2(Integer num2) {
        this.num2 = num2;
    }

    public Integer getNum3() {
        return num3;
    }

    public DblTrplStats num3(Integer num3) {
        this.num3 = num3;
        return this;
    }

    public void setNum3(Integer num3) {
        this.num3 = num3;
    }

    public Integer getTimesL() {
        return timesL;
    }

    public DblTrplStats timesL(Integer timesL) {
        this.timesL = timesL;
        return this;
    }

    public void setTimesL(Integer timesL) {
        this.timesL = timesL;
    }

    public Integer getTimesS() {
        return timesS;
    }

    public DblTrplStats timesS(Integer timesS) {
        this.timesS = timesS;
        return this;
    }

    public void setTimesS(Integer timesS) {
        this.timesS = timesS;
    }

    public Integer getSum() {
        return sum;
    }

    public DblTrplStats sum(Integer sum) {
        this.sum = sum;
        return this;
    }

    public void setSum(Integer sum) {
        this.sum = sum;
    }

    public Integer getEvens() {
        return evens;
    }

    public DblTrplStats evens(Integer evens) {
        this.evens = evens;
        return this;
    }

    public void setEvens(Integer evens) {
        this.evens = evens;
    }

    public GamesPlay getGame() {
        return game;
    }

    public DblTrplStats game(GamesPlay game) {
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
        DblTrplStats dblTrplStats = (DblTrplStats) o;
        if (dblTrplStats.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, dblTrplStats.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "DblTrplStats{" +
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
