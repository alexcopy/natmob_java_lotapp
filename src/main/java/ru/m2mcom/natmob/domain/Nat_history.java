package ru.m2mcom.natmob.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Nat_history.
 */
@Entity
@Table(name = "nat_history")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "nat_history")
public class Nat_history implements Serializable {

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
    @Column(name = "ball_6", nullable = false)
    private Integer ball6;

    @NotNull
    @Column(name = "bonus_ball", nullable = false)
    private Integer bonusBall;

    @Column(name = "machine")
    private String machine;

    @Column(name = "raffles")
    private String raffles;

    @Column(name = "draw_number")
    private String drawNumber;

    @Column(name = "sum_b")
    private Integer sumB;

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

    public Nat_history drawDate(String drawDate) {
        this.drawDate = drawDate;
        return this;
    }

    public void setDrawDate(String drawDate) {
        this.drawDate = drawDate;
    }

    public Integer getBall1() {
        return ball1;
    }

    public Nat_history ball1(Integer ball1) {
        this.ball1 = ball1;
        return this;
    }

    public void setBall1(Integer ball1) {
        this.ball1 = ball1;
    }

    public Integer getBall2() {
        return ball2;
    }

    public Nat_history ball2(Integer ball2) {
        this.ball2 = ball2;
        return this;
    }

    public void setBall2(Integer ball2) {
        this.ball2 = ball2;
    }

    public Integer getBall3() {
        return ball3;
    }

    public Nat_history ball3(Integer ball3) {
        this.ball3 = ball3;
        return this;
    }

    public void setBall3(Integer ball3) {
        this.ball3 = ball3;
    }

    public Integer getBall4() {
        return ball4;
    }

    public Nat_history ball4(Integer ball4) {
        this.ball4 = ball4;
        return this;
    }

    public void setBall4(Integer ball4) {
        this.ball4 = ball4;
    }

    public Integer getBall5() {
        return ball5;
    }

    public Nat_history ball5(Integer ball5) {
        this.ball5 = ball5;
        return this;
    }

    public void setBall5(Integer ball5) {
        this.ball5 = ball5;
    }

    public Integer getBall6() {
        return ball6;
    }

    public Nat_history ball6(Integer ball6) {
        this.ball6 = ball6;
        return this;
    }

    public void setBall6(Integer ball6) {
        this.ball6 = ball6;
    }

    public Integer getBonusBall() {
        return bonusBall;
    }

    public Nat_history bonusBall(Integer bonusBall) {
        this.bonusBall = bonusBall;
        return this;
    }

    public void setBonusBall(Integer bonusBall) {
        this.bonusBall = bonusBall;
    }

    public String getMachine() {
        return machine;
    }

    public Nat_history machine(String machine) {
        this.machine = machine;
        return this;
    }

    public void setMachine(String machine) {
        this.machine = machine;
    }

    public String getRaffles() {
        return raffles;
    }

    public Nat_history raffles(String raffles) {
        this.raffles = raffles;
        return this;
    }

    public void setRaffles(String raffles) {
        this.raffles = raffles;
    }

    public String getDrawNumber() {
        return drawNumber;
    }

    public Nat_history drawNumber(String drawNumber) {
        this.drawNumber = drawNumber;
        return this;
    }

    public void setDrawNumber(String drawNumber) {
        this.drawNumber = drawNumber;
    }

    public Integer getSumB() {
        return sumB;
    }

    public Nat_history sumB(Integer sumB) {
        this.sumB = sumB;
        return this;
    }

    public void setSumB(Integer sumB) {
        this.sumB = sumB;
    }

    public String getHash() {
        return hash;
    }

    public Nat_history hash(String hash) {
        this.hash = hash;
        return this;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public Integer getTimestamp() {
        return timestamp;
    }

    public Nat_history timestamp(Integer timestamp) {
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
        Nat_history nat_history = (Nat_history) o;
        if (nat_history.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, nat_history.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Nat_history{" +
            "id=" + id +
            ", drawDate='" + drawDate + "'" +
            ", ball1='" + ball1 + "'" +
            ", ball2='" + ball2 + "'" +
            ", ball3='" + ball3 + "'" +
            ", ball4='" + ball4 + "'" +
            ", ball5='" + ball5 + "'" +
            ", ball6='" + ball6 + "'" +
            ", bonusBall='" + bonusBall + "'" +
            ", machine='" + machine + "'" +
            ", raffles='" + raffles + "'" +
            ", drawNumber='" + drawNumber + "'" +
            ", sumB='" + sumB + "'" +
            ", hash='" + hash + "'" +
            ", timestamp='" + timestamp + "'" +
            '}';
    }
}
