package ru.m2mcom.natmob.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Eml_history.
 */
@Entity
@Table(name = "eml_history")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "eml_history")
public class Eml_history implements Serializable {

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
    @Column(name = "lucky_star_1", nullable = false)
    private Integer luckyStar1;

    @NotNull
    @Column(name = "lucky_star_2", nullable = false)
    private Integer luckyStar2;

    @Column(name = "raffle")
    private String raffle;

    @Column(name = "draw_number")
    private String drawNumber;

    @Column(name = "sum_b")
    private Integer sumB;

    @Column(name = "sum_s")
    private Integer sumS;

    @Column(name = "hash")
    private String hash;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDrawDate() {
        return drawDate;
    }

    public Eml_history drawDate(String drawDate) {
        this.drawDate = drawDate;
        return this;
    }

    public void setDrawDate(String drawDate) {
        this.drawDate = drawDate;
    }

    public Integer getBall1() {
        return ball1;
    }

    public Eml_history ball1(Integer ball1) {
        this.ball1 = ball1;
        return this;
    }

    public void setBall1(Integer ball1) {
        this.ball1 = ball1;
    }

    public Integer getBall2() {
        return ball2;
    }

    public Eml_history ball2(Integer ball2) {
        this.ball2 = ball2;
        return this;
    }

    public void setBall2(Integer ball2) {
        this.ball2 = ball2;
    }

    public Integer getBall3() {
        return ball3;
    }

    public Eml_history ball3(Integer ball3) {
        this.ball3 = ball3;
        return this;
    }

    public void setBall3(Integer ball3) {
        this.ball3 = ball3;
    }

    public Integer getBall4() {
        return ball4;
    }

    public Eml_history ball4(Integer ball4) {
        this.ball4 = ball4;
        return this;
    }

    public void setBall4(Integer ball4) {
        this.ball4 = ball4;
    }

    public Integer getBall5() {
        return ball5;
    }

    public Eml_history ball5(Integer ball5) {
        this.ball5 = ball5;
        return this;
    }

    public void setBall5(Integer ball5) {
        this.ball5 = ball5;
    }

    public Integer getLuckyStar1() {
        return luckyStar1;
    }

    public Eml_history luckyStar1(Integer luckyStar1) {
        this.luckyStar1 = luckyStar1;
        return this;
    }

    public void setLuckyStar1(Integer luckyStar1) {
        this.luckyStar1 = luckyStar1;
    }

    public Integer getLuckyStar2() {
        return luckyStar2;
    }

    public Eml_history luckyStar2(Integer luckyStar2) {
        this.luckyStar2 = luckyStar2;
        return this;
    }

    public void setLuckyStar2(Integer luckyStar2) {
        this.luckyStar2 = luckyStar2;
    }

    public String getRaffle() {
        return raffle;
    }

    public Eml_history raffle(String raffle) {
        this.raffle = raffle;
        return this;
    }

    public void setRaffle(String raffle) {
        this.raffle = raffle;
    }

    public String getDrawNumber() {
        return drawNumber;
    }

    public Eml_history drawNumber(String drawNumber) {
        this.drawNumber = drawNumber;
        return this;
    }

    public void setDrawNumber(String drawNumber) {
        this.drawNumber = drawNumber;
    }

    public Integer getSumB() {
        return sumB;
    }

    public Eml_history sumB(Integer sumB) {
        this.sumB = sumB;
        return this;
    }

    public void setSumB(Integer sumB) {
        this.sumB = sumB;
    }

    public Integer getSumS() {
        return sumS;
    }

    public Eml_history sumS(Integer sumS) {
        this.sumS = sumS;
        return this;
    }

    public void setSumS(Integer sumS) {
        this.sumS = sumS;
    }

    public String getHash() {
        return hash;
    }

    public Eml_history hash(String hash) {
        this.hash = hash;
        return this;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Eml_history eml_history = (Eml_history) o;
        if (eml_history.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, eml_history.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Eml_history{" +
            "id=" + id +
            ", drawDate='" + drawDate + "'" +
            ", ball1='" + ball1 + "'" +
            ", ball2='" + ball2 + "'" +
            ", ball3='" + ball3 + "'" +
            ", ball4='" + ball4 + "'" +
            ", ball5='" + ball5 + "'" +
            ", luckyStar1='" + luckyStar1 + "'" +
            ", luckyStar2='" + luckyStar2 + "'" +
            ", raffle='" + raffle + "'" +
            ", drawNumber='" + drawNumber + "'" +
            ", sumB='" + sumB + "'" +
            ", sumS='" + sumS + "'" +
            ", hash='" + hash + "'" +
            '}';
    }
}
