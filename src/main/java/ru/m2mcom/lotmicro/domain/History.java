package ru.m2mcom.lotmicro.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

import ru.m2mcom.lotmicro.domain.enumeration.GamesPlay;

/**
 * A History.
 */
@Entity
@Table(name = "history")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "history")
public class History implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "draw_date", nullable = false)
    private LocalDate drawDate;

    @Column(name = "ball_1")
    private Integer ball1;

    @Column(name = "ball_2")
    private Integer ball2;

    @Column(name = "ball_3")
    private Integer ball3;

    @Column(name = "ball_4")
    private Integer ball4;

    @Column(name = "ball_5")
    private Integer ball5;

    @Column(name = "ball_6")
    private Integer ball6;

    @Column(name = "bonus_ball_1")
    private Integer bonusBall1;

    @Column(name = "bonus_ball_2")
    private Integer bonusBall2;

    @Column(name = "draw_number")
    private Integer drawNumber;

    @Column(name = "ball_set")
    private String ballSet;

    @Column(name = "wins")
    private String wins;

    @Column(name = "machine")
    private String machine;

    @Column(name = "sum_b")
    private Integer sumB;

    @Column(name = "sum_s")
    private Integer sumS;

    @Column(name = "jackpot")
    private Integer jackpot;

    @Column(name = "hash")
    private String hash;

    @Column(name = "draw_hash")
    private String drawHash;

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

    public History drawDate(LocalDate drawDate) {
        this.drawDate = drawDate;
        return this;
    }

    public void setDrawDate(LocalDate drawDate) {
        this.drawDate = drawDate;
    }

    public Integer getBall1() {
        return ball1;
    }

    public History ball1(Integer ball1) {
        this.ball1 = ball1;
        return this;
    }

    public void setBall1(Integer ball1) {
        this.ball1 = ball1;
    }

    public Integer getBall2() {
        return ball2;
    }

    public History ball2(Integer ball2) {
        this.ball2 = ball2;
        return this;
    }

    public void setBall2(Integer ball2) {
        this.ball2 = ball2;
    }

    public Integer getBall3() {
        return ball3;
    }

    public History ball3(Integer ball3) {
        this.ball3 = ball3;
        return this;
    }

    public void setBall3(Integer ball3) {
        this.ball3 = ball3;
    }

    public Integer getBall4() {
        return ball4;
    }

    public History ball4(Integer ball4) {
        this.ball4 = ball4;
        return this;
    }

    public void setBall4(Integer ball4) {
        this.ball4 = ball4;
    }

    public Integer getBall5() {
        return ball5;
    }

    public History ball5(Integer ball5) {
        this.ball5 = ball5;
        return this;
    }

    public void setBall5(Integer ball5) {
        this.ball5 = ball5;
    }

    public Integer getBall6() {
        return ball6;
    }

    public History ball6(Integer ball6) {
        this.ball6 = ball6;
        return this;
    }

    public void setBall6(Integer ball6) {
        this.ball6 = ball6;
    }

    public Integer getBonusBall1() {
        return bonusBall1;
    }

    public History bonusBall1(Integer bonusBall1) {
        this.bonusBall1 = bonusBall1;
        return this;
    }

    public void setBonusBall1(Integer bonusBall1) {
        this.bonusBall1 = bonusBall1;
    }

    public Integer getBonusBall2() {
        return bonusBall2;
    }

    public History bonusBall2(Integer bonusBall2) {
        this.bonusBall2 = bonusBall2;
        return this;
    }

    public void setBonusBall2(Integer bonusBall2) {
        this.bonusBall2 = bonusBall2;
    }

    public Integer getDrawNumber() {
        return drawNumber;
    }

    public History drawNumber(Integer drawNumber) {
        this.drawNumber = drawNumber;
        return this;
    }

    public void setDrawNumber(Integer drawNumber) {
        this.drawNumber = drawNumber;
    }

    public String getBallSet() {
        return ballSet;
    }

    public History ballSet(String ballSet) {
        this.ballSet = ballSet;
        return this;
    }

    public void setBallSet(String ballSet) {
        this.ballSet = ballSet;
    }

    public String getWins() {
        return wins;
    }

    public History wins(String wins) {
        this.wins = wins;
        return this;
    }

    public void setWins(String wins) {
        this.wins = wins;
    }

    public String getMachine() {
        return machine;
    }

    public History machine(String machine) {
        this.machine = machine;
        return this;
    }

    public void setMachine(String machine) {
        this.machine = machine;
    }

    public Integer getSumB() {
        return sumB;
    }

    public History sumB(Integer sumB) {
        this.sumB = sumB;
        return this;
    }

    public void setSumB(Integer sumB) {
        this.sumB = sumB;
    }

    public Integer getSumS() {
        return sumS;
    }

    public History sumS(Integer sumS) {
        this.sumS = sumS;
        return this;
    }

    public void setSumS(Integer sumS) {
        this.sumS = sumS;
    }

    public Integer getJackpot() {
        return jackpot;
    }

    public History jackpot(Integer jackpot) {
        this.jackpot = jackpot;
        return this;
    }

    public void setJackpot(Integer jackpot) {
        this.jackpot = jackpot;
    }

    public String getHash() {
        return hash;
    }

    public History hash(String hash) {
        this.hash = hash;
        return this;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public String getDrawHash() {
        return drawHash;
    }

    public History drawHash(String drawHash) {
        this.drawHash = drawHash;
        return this;
    }

    public void setDrawHash(String drawHash) {
        this.drawHash = drawHash;
    }

    public GamesPlay getGame() {
        return game;
    }

    public History game(GamesPlay game) {
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
        History history = (History) o;
        if (history.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, history.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "History{" +
            "id=" + id +
            ", drawDate='" + drawDate + "'" +
            ", ball1='" + ball1 + "'" +
            ", ball2='" + ball2 + "'" +
            ", ball3='" + ball3 + "'" +
            ", ball4='" + ball4 + "'" +
            ", ball5='" + ball5 + "'" +
            ", ball6='" + ball6 + "'" +
            ", bonusBall1='" + bonusBall1 + "'" +
            ", bonusBall2='" + bonusBall2 + "'" +
            ", drawNumber='" + drawNumber + "'" +
            ", ballSet='" + ballSet + "'" +
            ", wins='" + wins + "'" +
            ", machine='" + machine + "'" +
            ", sumB='" + sumB + "'" +
            ", sumS='" + sumS + "'" +
            ", jackpot='" + jackpot + "'" +
            ", hash='" + hash + "'" +
            ", drawHash='" + drawHash + "'" +
            ", game='" + game + "'" +
            '}';
    }
}
