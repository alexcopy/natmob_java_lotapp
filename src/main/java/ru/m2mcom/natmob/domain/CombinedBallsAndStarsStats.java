package ru.m2mcom.natmob.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A CombinedBallsAndStarsStats.
 */
@Entity
@Table(name = "combined_balls_and_stars_stats")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "combinedballsandstarsstats")
public class CombinedBallsAndStarsStats implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "ball_1", nullable = false)
    private Integer ball1;

    @Column(name = "ball_2")
    private Integer ball2;

    @Column(name = "ball_3")
    private Integer ball3;

    @Column(name = "ball_4")
    private Integer ball4;

    @NotNull
    @Column(name = "times_s", nullable = false)
    private Integer timesS;

    @NotNull
    @Column(name = "times_l", nullable = false)
    private Integer timesL;

    @NotNull
    @Column(name = "sum", nullable = false)
    private Integer sum;

    @Column(name = "evens")
    private Integer evens;

    @NotNull
    @Column(name = "balls_type", nullable = false)
    private String ballsType;

    @Column(name = "game_type")
    private String gameType;

    @NotNull
    @Column(name = "num_of_balls", nullable = false)
    private Integer numOfBalls;

    @Column(name = "timestamp")
    private Integer timestamp;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getBall1() {
        return ball1;
    }

    public CombinedBallsAndStarsStats ball1(Integer ball1) {
        this.ball1 = ball1;
        return this;
    }

    public void setBall1(Integer ball1) {
        this.ball1 = ball1;
    }

    public Integer getBall2() {
        return ball2;
    }

    public CombinedBallsAndStarsStats ball2(Integer ball2) {
        this.ball2 = ball2;
        return this;
    }

    public void setBall2(Integer ball2) {
        this.ball2 = ball2;
    }

    public Integer getBall3() {
        return ball3;
    }

    public CombinedBallsAndStarsStats ball3(Integer ball3) {
        this.ball3 = ball3;
        return this;
    }

    public void setBall3(Integer ball3) {
        this.ball3 = ball3;
    }

    public Integer getBall4() {
        return ball4;
    }

    public CombinedBallsAndStarsStats ball4(Integer ball4) {
        this.ball4 = ball4;
        return this;
    }

    public void setBall4(Integer ball4) {
        this.ball4 = ball4;
    }

    public Integer getTimesS() {
        return timesS;
    }

    public CombinedBallsAndStarsStats timesS(Integer timesS) {
        this.timesS = timesS;
        return this;
    }

    public void setTimesS(Integer timesS) {
        this.timesS = timesS;
    }

    public Integer getTimesL() {
        return timesL;
    }

    public CombinedBallsAndStarsStats timesL(Integer timesL) {
        this.timesL = timesL;
        return this;
    }

    public void setTimesL(Integer timesL) {
        this.timesL = timesL;
    }

    public Integer getSum() {
        return sum;
    }

    public CombinedBallsAndStarsStats sum(Integer sum) {
        this.sum = sum;
        return this;
    }

    public void setSum(Integer sum) {
        this.sum = sum;
    }

    public Integer getEvens() {
        return evens;
    }

    public CombinedBallsAndStarsStats evens(Integer evens) {
        this.evens = evens;
        return this;
    }

    public void setEvens(Integer evens) {
        this.evens = evens;
    }

    public String getBallsType() {
        return ballsType;
    }

    public CombinedBallsAndStarsStats ballsType(String ballsType) {
        this.ballsType = ballsType;
        return this;
    }

    public void setBallsType(String ballsType) {
        this.ballsType = ballsType;
    }

    public String getGameType() {
        return gameType;
    }

    public CombinedBallsAndStarsStats gameType(String gameType) {
        this.gameType = gameType;
        return this;
    }

    public void setGameType(String gameType) {
        this.gameType = gameType;
    }

    public Integer getNumOfBalls() {
        return numOfBalls;
    }

    public CombinedBallsAndStarsStats numOfBalls(Integer numOfBalls) {
        this.numOfBalls = numOfBalls;
        return this;
    }

    public void setNumOfBalls(Integer numOfBalls) {
        this.numOfBalls = numOfBalls;
    }

    public Integer getTimestamp() {
        return timestamp;
    }

    public CombinedBallsAndStarsStats timestamp(Integer timestamp) {
        this.timestamp = timestamp;
        return this;
    }

    public void setTimestamp(Integer timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CombinedBallsAndStarsStats combinedBallsAndStarsStats = (CombinedBallsAndStarsStats) o;
        if (combinedBallsAndStarsStats.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, combinedBallsAndStarsStats.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "CombinedBallsAndStarsStats{" +
            "id=" + id +
            ", ball1='" + ball1 + "'" +
            ", ball2='" + ball2 + "'" +
            ", ball3='" + ball3 + "'" +
            ", ball4='" + ball4 + "'" +
            ", timesS='" + timesS + "'" +
            ", timesL='" + timesL + "'" +
            ", sum='" + sum + "'" +
            ", evens='" + evens + "'" +
            ", ballsType='" + ballsType + "'" +
            ", gameType='" + gameType + "'" +
            ", numOfBalls='" + numOfBalls + "'" +
            ", timestamp='" + timestamp + "'" +
            '}';
    }
}
