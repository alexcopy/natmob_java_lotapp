package ru.m2mcom.natmob.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Hot_plays.
 */
@Entity
@Table(name = "hot_plays")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "hot_plays")
public class Hot_plays implements Serializable {

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

    @Column(name = "ball_2")
    private Integer ball2;

    @Column(name = "ball_3")
    private Integer ball3;

    @Column(name = "ball_4")
    private Integer ball4;

    @Column(name = "ball_5")
    private Integer ball5;

    @Column(name = "draw_type")
    private String drawType;

    @Column(name = "game_type")
    private String gameType;

    @Column(name = "prize")
    private Double prize;

    @Column(name = "checked")
    private Boolean checked;

    @Column(name = "rank_id")
    private Integer rankId;

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

    public Hot_plays drawDate(String drawDate) {
        this.drawDate = drawDate;
        return this;
    }

    public void setDrawDate(String drawDate) {
        this.drawDate = drawDate;
    }

    public Integer getBall1() {
        return ball1;
    }

    public Hot_plays ball1(Integer ball1) {
        this.ball1 = ball1;
        return this;
    }

    public void setBall1(Integer ball1) {
        this.ball1 = ball1;
    }

    public Integer getBall2() {
        return ball2;
    }

    public Hot_plays ball2(Integer ball2) {
        this.ball2 = ball2;
        return this;
    }

    public void setBall2(Integer ball2) {
        this.ball2 = ball2;
    }

    public Integer getBall3() {
        return ball3;
    }

    public Hot_plays ball3(Integer ball3) {
        this.ball3 = ball3;
        return this;
    }

    public void setBall3(Integer ball3) {
        this.ball3 = ball3;
    }

    public Integer getBall4() {
        return ball4;
    }

    public Hot_plays ball4(Integer ball4) {
        this.ball4 = ball4;
        return this;
    }

    public void setBall4(Integer ball4) {
        this.ball4 = ball4;
    }

    public Integer getBall5() {
        return ball5;
    }

    public Hot_plays ball5(Integer ball5) {
        this.ball5 = ball5;
        return this;
    }

    public void setBall5(Integer ball5) {
        this.ball5 = ball5;
    }

    public String getDrawType() {
        return drawType;
    }

    public Hot_plays drawType(String drawType) {
        this.drawType = drawType;
        return this;
    }

    public void setDrawType(String drawType) {
        this.drawType = drawType;
    }

    public String getGameType() {
        return gameType;
    }

    public Hot_plays gameType(String gameType) {
        this.gameType = gameType;
        return this;
    }

    public void setGameType(String gameType) {
        this.gameType = gameType;
    }

    public Double getPrize() {
        return prize;
    }

    public Hot_plays prize(Double prize) {
        this.prize = prize;
        return this;
    }

    public void setPrize(Double prize) {
        this.prize = prize;
    }

    public Boolean isChecked() {
        return checked;
    }

    public Hot_plays checked(Boolean checked) {
        this.checked = checked;
        return this;
    }

    public void setChecked(Boolean checked) {
        this.checked = checked;
    }

    public Integer getRankId() {
        return rankId;
    }

    public Hot_plays rankId(Integer rankId) {
        this.rankId = rankId;
        return this;
    }

    public void setRankId(Integer rankId) {
        this.rankId = rankId;
    }

    public Integer getSumB() {
        return sumB;
    }

    public Hot_plays sumB(Integer sumB) {
        this.sumB = sumB;
        return this;
    }

    public void setSumB(Integer sumB) {
        this.sumB = sumB;
    }

    public String getHash() {
        return hash;
    }

    public Hot_plays hash(String hash) {
        this.hash = hash;
        return this;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public Integer getTimestamp() {
        return timestamp;
    }

    public Hot_plays timestamp(Integer timestamp) {
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
        Hot_plays hot_plays = (Hot_plays) o;
        if (hot_plays.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, hot_plays.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Hot_plays{" +
            "id=" + id +
            ", drawDate='" + drawDate + "'" +
            ", ball1='" + ball1 + "'" +
            ", ball2='" + ball2 + "'" +
            ", ball3='" + ball3 + "'" +
            ", ball4='" + ball4 + "'" +
            ", ball5='" + ball5 + "'" +
            ", drawType='" + drawType + "'" +
            ", gameType='" + gameType + "'" +
            ", prize='" + prize + "'" +
            ", checked='" + checked + "'" +
            ", rankId='" + rankId + "'" +
            ", sumB='" + sumB + "'" +
            ", hash='" + hash + "'" +
            ", timestamp='" + timestamp + "'" +
            '}';
    }
}
