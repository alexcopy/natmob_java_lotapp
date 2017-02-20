package ru.m2mcom.natmob.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A NatBallsAndStarsHitStats.
 */
@Entity
@Table(name = "nat_balls_and_stars_hit_stats")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "natballsandstarshitstats")
public class NatBallsAndStarsHitStats implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "number", nullable = false)
    private Integer number;

    @NotNull
    @Column(name = "stat_s", nullable = false)
    private Integer statS;

    @Column(name = "stat_l")
    private Integer statL;

    @NotNull
    @Column(name = "ball_type", nullable = false)
    private String ballType;

    @Column(name = "timestamp")
    private Integer timestamp;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getNumber() {
        return number;
    }

    public NatBallsAndStarsHitStats number(Integer number) {
        this.number = number;
        return this;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Integer getStatS() {
        return statS;
    }

    public NatBallsAndStarsHitStats statS(Integer statS) {
        this.statS = statS;
        return this;
    }

    public void setStatS(Integer statS) {
        this.statS = statS;
    }

    public Integer getStatL() {
        return statL;
    }

    public NatBallsAndStarsHitStats statL(Integer statL) {
        this.statL = statL;
        return this;
    }

    public void setStatL(Integer statL) {
        this.statL = statL;
    }

    public String getBallType() {
        return ballType;
    }

    public NatBallsAndStarsHitStats ballType(String ballType) {
        this.ballType = ballType;
        return this;
    }

    public void setBallType(String ballType) {
        this.ballType = ballType;
    }

    public Integer getTimestamp() {
        return timestamp;
    }

    public NatBallsAndStarsHitStats timestamp(Integer timestamp) {
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
        NatBallsAndStarsHitStats natBallsAndStarsHitStats = (NatBallsAndStarsHitStats) o;
        if (natBallsAndStarsHitStats.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, natBallsAndStarsHitStats.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "NatBallsAndStarsHitStats{" +
            "id=" + id +
            ", number='" + number + "'" +
            ", statS='" + statS + "'" +
            ", statL='" + statL + "'" +
            ", ballType='" + ballType + "'" +
            ", timestamp='" + timestamp + "'" +
            '}';
    }
}
