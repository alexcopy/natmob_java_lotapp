package ru.m2mcom.natmob.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Thb_history.
 */
@Entity
@Table(name = "thb_history")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "thb_history")
public class Thb_history implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Pattern(regexp = "(^\\d\\d-\\w+-\\d\\d$)")
    @Column(name = "draw_date", nullable = false)
    private String drawDate;

    @NotNull
    @Column(name = "ball_1", nullable = false)
    private Integer ball1;

    @NotNull
    @Column(name = "ball_2", nullable = false)
    private Integer ball2;

    @NotNull
    @Column(name = "ball_3", nullable = false)
    private Integer ball3;

    @NotNull
    @Column(name = "ball_4", nullable = false)
    private Integer ball4;

    @NotNull
    @Column(name = "ball_5", nullable = false)
    private Integer ball5;

    @NotNull
    @Column(name = "thunder_ball", nullable = false)
    private Integer thunderBall;

    @NotNull
    @Column(name = "sum_b", nullable = false)
    private Integer sumB;

    @Column(name = "machine")
    private String machine;

    @Column(name = "ball_set")
    private String ballSet;

    @Column(name = "draw_number")
    private Integer drawNumber;

    @Column(name = "hash")
    private String hash;

    @Column(name = "timestamp")
    private Integer timestamp;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDrawDate() {
        return drawDate;
    }

    public Thb_history drawDate(String drawDate) {
        this.drawDate = drawDate;
        return this;
    }

    public void setDrawDate(String drawDate) {
        this.drawDate = drawDate;
    }

    public Integer getBall1() {
        return ball1;
    }

    public Thb_history ball1(Integer ball1) {
        this.ball1 = ball1;
        return this;
    }

    public void setBall1(Integer ball1) {
        this.ball1 = ball1;
    }

    public Integer getBall2() {
        return ball2;
    }

    public Thb_history ball2(Integer ball2) {
        this.ball2 = ball2;
        return this;
    }

    public void setBall2(Integer ball2) {
        this.ball2 = ball2;
    }

    public Integer getBall3() {
        return ball3;
    }

    public Thb_history ball3(Integer ball3) {
        this.ball3 = ball3;
        return this;
    }

    public void setBall3(Integer ball3) {
        this.ball3 = ball3;
    }

    public Integer getBall4() {
        return ball4;
    }

    public Thb_history ball4(Integer ball4) {
        this.ball4 = ball4;
        return this;
    }

    public void setBall4(Integer ball4) {
        this.ball4 = ball4;
    }

    public Integer getBall5() {
        return ball5;
    }

    public Thb_history ball5(Integer ball5) {
        this.ball5 = ball5;
        return this;
    }

    public void setBall5(Integer ball5) {
        this.ball5 = ball5;
    }

    public Integer getThunderBall() {
        return thunderBall;
    }

    public Thb_history thunderBall(Integer thunderBall) {
        this.thunderBall = thunderBall;
        return this;
    }

    public void setThunderBall(Integer thunderBall) {
        this.thunderBall = thunderBall;
    }

    public Integer getSumB() {
        return sumB;
    }

    public Thb_history sumB(Integer sumB) {
        this.sumB = sumB;
        return this;
    }

    public void setSumB(Integer sumB) {
        this.sumB = sumB;
    }

    public String getMachine() {
        return machine;
    }

    public Thb_history machine(String machine) {
        this.machine = machine;
        return this;
    }

    public void setMachine(String machine) {
        this.machine = machine;
    }

    public String getBallSet() {
        return ballSet;
    }

    public Thb_history ballSet(String ballSet) {
        this.ballSet = ballSet;
        return this;
    }

    public void setBallSet(String ballSet) {
        this.ballSet = ballSet;
    }

    public Integer getDrawNumber() {
        return drawNumber;
    }

    public Thb_history drawNumber(Integer drawNumber) {
        this.drawNumber = drawNumber;
        return this;
    }

    public void setDrawNumber(Integer drawNumber) {
        this.drawNumber = drawNumber;
    }

    public String getHash() {
        return hash;
    }

    public Thb_history hash(String hash) {
        this.hash = hash;
        return this;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public Integer getTimestamp() {
        return timestamp;
    }

    public Thb_history timestamp(Integer timestamp) {
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
        Thb_history thb_history = (Thb_history) o;
        if (thb_history.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, thb_history.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Thb_history{" +
            "id=" + id +
            ", drawDate='" + drawDate + "'" +
            ", ball1='" + ball1 + "'" +
            ", ball2='" + ball2 + "'" +
            ", ball3='" + ball3 + "'" +
            ", ball4='" + ball4 + "'" +
            ", ball5='" + ball5 + "'" +
            ", thunderBall='" + thunderBall + "'" +
            ", sumB='" + sumB + "'" +
            ", machine='" + machine + "'" +
            ", ballSet='" + ballSet + "'" +
            ", drawNumber='" + drawNumber + "'" +
            ", hash='" + hash + "'" +
            ", timestamp='" + timestamp + "'" +
            '}';
    }
}
