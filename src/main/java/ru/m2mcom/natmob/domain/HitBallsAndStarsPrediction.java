package ru.m2mcom.natmob.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A HitBallsAndStarsPrediction.
 */
@Entity
@Table(name = "hit_balls_and_stars_prediction")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "hitballsandstarsprediction")
public class HitBallsAndStarsPrediction implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "number", nullable = false)
    private Integer number;

    @NotNull
    @Column(name = "current_skips", nullable = false)
    private Integer currentSkips;

    @NotNull
    @Column(name = "average_skips", nullable = false)
    private Integer averageSkips;

    @NotNull
    @Column(name = "draws_due", nullable = false)
    private Integer drawsDue;

    @NotNull
    @Column(name = "status", nullable = false)
    private String status;

    @NotNull
    @Column(name = "ball_type", nullable = false)
    private String ballType;

    @NotNull
    @Column(name = "game_type", nullable = false)
    private String gameType;

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

    public HitBallsAndStarsPrediction number(Integer number) {
        this.number = number;
        return this;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Integer getCurrentSkips() {
        return currentSkips;
    }

    public HitBallsAndStarsPrediction currentSkips(Integer currentSkips) {
        this.currentSkips = currentSkips;
        return this;
    }

    public void setCurrentSkips(Integer currentSkips) {
        this.currentSkips = currentSkips;
    }

    public Integer getAverageSkips() {
        return averageSkips;
    }

    public HitBallsAndStarsPrediction averageSkips(Integer averageSkips) {
        this.averageSkips = averageSkips;
        return this;
    }

    public void setAverageSkips(Integer averageSkips) {
        this.averageSkips = averageSkips;
    }

    public Integer getDrawsDue() {
        return drawsDue;
    }

    public HitBallsAndStarsPrediction drawsDue(Integer drawsDue) {
        this.drawsDue = drawsDue;
        return this;
    }

    public void setDrawsDue(Integer drawsDue) {
        this.drawsDue = drawsDue;
    }

    public String getStatus() {
        return status;
    }

    public HitBallsAndStarsPrediction status(String status) {
        this.status = status;
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getBallType() {
        return ballType;
    }

    public HitBallsAndStarsPrediction ballType(String ballType) {
        this.ballType = ballType;
        return this;
    }

    public void setBallType(String ballType) {
        this.ballType = ballType;
    }

    public String getGameType() {
        return gameType;
    }

    public HitBallsAndStarsPrediction gameType(String gameType) {
        this.gameType = gameType;
        return this;
    }

    public void setGameType(String gameType) {
        this.gameType = gameType;
    }

    public Integer getTimestamp() {
        return timestamp;
    }

    public HitBallsAndStarsPrediction timestamp(Integer timestamp) {
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
        HitBallsAndStarsPrediction hitBallsAndStarsPrediction = (HitBallsAndStarsPrediction) o;
        if (hitBallsAndStarsPrediction.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, hitBallsAndStarsPrediction.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "HitBallsAndStarsPrediction{" +
            "id=" + id +
            ", number='" + number + "'" +
            ", currentSkips='" + currentSkips + "'" +
            ", averageSkips='" + averageSkips + "'" +
            ", drawsDue='" + drawsDue + "'" +
            ", status='" + status + "'" +
            ", ballType='" + ballType + "'" +
            ", gameType='" + gameType + "'" +
            ", timestamp='" + timestamp + "'" +
            '}';
    }
}
