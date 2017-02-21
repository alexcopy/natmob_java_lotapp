package ru.m2mcom.natmob.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

import ru.m2mcom.natmob.domain.enumeration.DrawType;

/**
 * A Nat_plays.
 */
@Entity
@Table(name = "nat_plays")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "nat_plays")
public class Nat_plays implements Serializable {

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

    @Column(name = "ball_5")
    private Integer ball5;

    @NotNull
    @Column(name = "ball_6", nullable = false)
    private Integer ball6;

    @Column(name = "bonus_ball")
    private Integer bonusBall;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "draw_type", nullable = false)
    private DrawType drawType;

    @NotNull
    @Column(name = "game_type", nullable = false)
    private String gameType;

    @Column(name = "prize")
    private Double prize;

    @Column(name = "checked")
    private Boolean checked=false;

    @Column(name = "rank_id")
    private Long rank_id;

    @Column(name = "sum_b")
    private Integer sumB;

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

    public Nat_plays drawDate(String drawDate) {
        this.drawDate = drawDate;
        return this;
    }

    public void setDrawDate(String drawDate) {
        this.drawDate = drawDate;
    }

    public Integer getBall1() {
        return ball1;
    }

    public Nat_plays ball1(Integer ball1) {
        this.ball1 = ball1;
        return this;
    }

    public void setBall1(Integer ball1) {
        this.ball1 = ball1;
    }

    public Integer getBall2() {
        return ball2;
    }

    public Nat_plays ball2(Integer ball2) {
        this.ball2 = ball2;
        return this;
    }

    public void setBall2(Integer ball2) {
        this.ball2 = ball2;
    }

    public Integer getBall3() {
        return ball3;
    }

    public Nat_plays ball3(Integer ball3) {
        this.ball3 = ball3;
        return this;
    }

    public void setBall3(Integer ball3) {
        this.ball3 = ball3;
    }

    public Integer getBall4() {
        return ball4;
    }

    public Nat_plays ball4(Integer ball4) {
        this.ball4 = ball4;
        return this;
    }

    public void setBall4(Integer ball4) {
        this.ball4 = ball4;
    }

    public Integer getBall5() {
        return ball5;
    }

    public Nat_plays ball5(Integer ball5) {
        this.ball5 = ball5;
        return this;
    }

    public void setBall5(Integer ball5) {
        this.ball5 = ball5;
    }

    public Integer getBall6() {
        return ball6;
    }

    public Nat_plays ball6(Integer ball6) {
        this.ball6 = ball6;
        return this;
    }

    public void setBall6(Integer ball6) {
        this.ball6 = ball6;
    }

    public Integer getBonusBall() {
        return bonusBall;
    }

    public Nat_plays bonusBall(Integer bonusBall) {
        this.bonusBall = bonusBall;
        return this;
    }

    public void setBonusBall(Integer bonusBall) {
        this.bonusBall = bonusBall;
    }

    public DrawType getDrawType() {
        return drawType;
    }

    public Nat_plays drawType(DrawType drawType) {
        this.drawType = drawType;
        return this;
    }

    public void setDrawType(DrawType drawType) {
        this.drawType = drawType;
    }

    public String getGameType() {
        return gameType;
    }

    public Nat_plays gameType(String gameType) {
        this.gameType = gameType;
        return this;
    }

    public void setGameType(String gameType) {
        this.gameType = gameType;
    }

    public Double getPrize() {
        return prize;
    }

    public Nat_plays prize(Double prize) {
        this.prize = prize;
        return this;
    }

    public void setPrize(Double prize) {
        this.prize = prize;
    }

    public Boolean isChecked() {
        return checked;
    }

    public Nat_plays checked(Boolean checked) {
        this.checked = checked;
        return this;
    }

    public void setChecked(Boolean checked) {
        this.checked = checked;
    }

    public Long getRank_id() {
        return rank_id;
    }

    public Nat_plays rank_id(Long rank_id) {
        this.rank_id = rank_id;
        return this;
    }

    public void setRank_id(Long rank_id) {
        this.rank_id = rank_id;
    }

    public Integer getSumB() {
        return sumB;
    }

    public Nat_plays sumB(Integer sumB) {
        this.sumB = sumB;
        return this;
    }

    public void setSumB(Integer sumB) {
        this.sumB = sumB;
    }

    public String getHash() {
        return hash;
    }

    public Nat_plays hash(String hash) {
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
        Nat_plays nat_plays = (Nat_plays) o;
        if (nat_plays.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, nat_plays.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Nat_plays{" +
            "id=" + id +
            ", drawDate='" + drawDate + "'" +
            ", ball1='" + ball1 + "'" +
            ", ball2='" + ball2 + "'" +
            ", ball3='" + ball3 + "'" +
            ", ball4='" + ball4 + "'" +
            ", ball5='" + ball5 + "'" +
            ", ball6='" + ball6 + "'" +
            ", bonusBall='" + bonusBall + "'" +
            ", drawType='" + drawType + "'" +
            ", gameType='" + gameType + "'" +
            ", prize='" + prize + "'" +
            ", checked='" + checked + "'" +
            ", rank_id='" + rank_id + "'" +
            ", sumB='" + sumB + "'" +
            ", hash='" + hash + "'" +
            '}';
    }
}
