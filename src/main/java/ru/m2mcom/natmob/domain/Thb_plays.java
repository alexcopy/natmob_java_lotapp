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
 * A Thb_plays.
 */
@Entity
@Table(name = "thb_plays")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "thb_plays")
public class Thb_plays implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Pattern(regexp = "(\\d\\d-\\w+-\\d\\d)")
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

    @Column(name = "sum_b")
    private Integer sumB;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "draw_type", nullable = false)
    private DrawType drawType;

    @NotNull
    @Column(name = "game_type", nullable = false)
    private String gameType;

    @Column(name = "prize")
    private Double prize;

    @Column(name = "rank_id")
    private Long rank_id;

    @Column(name = "bonus_rank_id")
    private Long bonus_rank_id;

    @Column(name = "checked")
    private Boolean checked=false;

    @NotNull
    @Column(name = "hash", nullable = false)
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

    public Thb_plays drawDate(String drawDate) {
        this.drawDate = drawDate;
        return this;
    }

    public void setDrawDate(String drawDate) {
        this.drawDate = drawDate;
    }

    public Integer getBall1() {
        return ball1;
    }

    public Thb_plays ball1(Integer ball1) {
        this.ball1 = ball1;
        return this;
    }

    public void setBall1(Integer ball1) {
        this.ball1 = ball1;
    }

    public Integer getBall2() {
        return ball2;
    }

    public Thb_plays ball2(Integer ball2) {
        this.ball2 = ball2;
        return this;
    }

    public void setBall2(Integer ball2) {
        this.ball2 = ball2;
    }

    public Integer getBall3() {
        return ball3;
    }

    public Thb_plays ball3(Integer ball3) {
        this.ball3 = ball3;
        return this;
    }

    public void setBall3(Integer ball3) {
        this.ball3 = ball3;
    }

    public Integer getBall4() {
        return ball4;
    }

    public Thb_plays ball4(Integer ball4) {
        this.ball4 = ball4;
        return this;
    }

    public void setBall4(Integer ball4) {
        this.ball4 = ball4;
    }

    public Integer getBall5() {
        return ball5;
    }

    public Thb_plays ball5(Integer ball5) {
        this.ball5 = ball5;
        return this;
    }

    public void setBall5(Integer ball5) {
        this.ball5 = ball5;
    }

    public Integer getThunderBall() {
        return thunderBall;
    }

    public Thb_plays thunderBall(Integer thunderBall) {
        this.thunderBall = thunderBall;
        return this;
    }

    public void setThunderBall(Integer thunderBall) {
        this.thunderBall = thunderBall;
    }

    public Integer getSumB() {
        return sumB;
    }

    public Thb_plays sumB(Integer sumB) {
        this.sumB = sumB;
        return this;
    }

    public void setSumB(Integer sumB) {
        this.sumB = sumB;
    }

    public DrawType getDrawType() {
        return drawType;
    }

    public Thb_plays drawType(DrawType drawType) {
        this.drawType = drawType;
        return this;
    }

    public void setDrawType(DrawType drawType) {
        this.drawType = drawType;
    }

    public String getGameType() {
        return gameType;
    }

    public Thb_plays gameType(String gameType) {
        this.gameType = gameType;
        return this;
    }

    public void setGameType(String gameType) {
        this.gameType = gameType;
    }

    public Double getPrize() {
        return prize;
    }

    public Thb_plays prize(Double prize) {
        this.prize = prize;
        return this;
    }

    public void setPrize(Double prize) {
        this.prize = prize;
    }

    public Long getRank_id() {
        return rank_id;
    }

    public Thb_plays rank_id(Long rank_id) {
        this.rank_id = rank_id;
        return this;
    }

    public void setRank_id(Long rank_id) {
        this.rank_id = rank_id;
    }

    public Long getBonus_rank_id() {
        return bonus_rank_id;
    }

    public Thb_plays bonus_rank_id(Long bonus_rank_id) {
        this.bonus_rank_id = bonus_rank_id;
        return this;
    }

    public void setBonus_rank_id(Long bonus_rank_id) {
        this.bonus_rank_id = bonus_rank_id;
    }

    public Boolean isChecked() {
        return checked;
    }

    public Thb_plays checked(Boolean checked) {
        this.checked = checked;
        return this;
    }

    public void setChecked(Boolean checked) {
        this.checked = checked;
    }

    public String getHash() {
        return hash;
    }

    public Thb_plays hash(String hash) {
        this.hash = hash;
        return this;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public Integer getTimestamp() {
        return timestamp;
    }

    public Thb_plays timestamp(Integer timestamp) {
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
        Thb_plays thb_plays = (Thb_plays) o;
        if (thb_plays.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, thb_plays.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Thb_plays{" +
            "id=" + id +
            ", drawDate='" + drawDate + "'" +
            ", ball1='" + ball1 + "'" +
            ", ball2='" + ball2 + "'" +
            ", ball3='" + ball3 + "'" +
            ", ball4='" + ball4 + "'" +
            ", ball5='" + ball5 + "'" +
            ", thunderBall='" + thunderBall + "'" +
            ", sumB='" + sumB + "'" +
            ", drawType='" + drawType + "'" +
            ", gameType='" + gameType + "'" +
            ", prize='" + prize + "'" +
            ", rank_id='" + rank_id + "'" +
            ", bonus_rank_id='" + bonus_rank_id + "'" +
            ", checked='" + checked + "'" +
            ", hash='" + hash + "'" +
            ", timestamp='" + timestamp + "'" +
            '}';
    }
}
