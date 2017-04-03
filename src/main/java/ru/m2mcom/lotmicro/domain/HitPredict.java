package ru.m2mcom.lotmicro.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

import ru.m2mcom.lotmicro.domain.enumeration.DrawPartType;

import ru.m2mcom.lotmicro.domain.enumeration.GamesPlay;

/**
 * A HitPredict.
 */
@Entity
@Table(name = "hit_predict")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "hitpredict")
public class HitPredict implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "number")
    private Integer number;

    @Enumerated(EnumType.STRING)
    @Column(name = "draw_part")
    private DrawPartType drawPart;

    @Column(name = "current_skips")
    private Integer currentSkips;

    @Column(name = "average_skips")
    private Integer averageSkips;

    @Column(name = "draws_due")
    private Integer drawsDue;

    @Column(name = "allskips")
    private String allskips;

    @Column(name = "status")
    private String status;

    @Enumerated(EnumType.STRING)
    @Column(name = "game")
    private GamesPlay game;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getNumber() {
        return number;
    }

    public HitPredict number(Integer number) {
        this.number = number;
        return this;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public DrawPartType getDrawPart() {
        return drawPart;
    }

    public HitPredict drawPart(DrawPartType drawPart) {
        this.drawPart = drawPart;
        return this;
    }

    public void setDrawPart(DrawPartType drawPart) {
        this.drawPart = drawPart;
    }

    public Integer getCurrentSkips() {
        return currentSkips;
    }

    public HitPredict currentSkips(Integer currentSkips) {
        this.currentSkips = currentSkips;
        return this;
    }

    public void setCurrentSkips(Integer currentSkips) {
        this.currentSkips = currentSkips;
    }

    public Integer getAverageSkips() {
        return averageSkips;
    }

    public HitPredict averageSkips(Integer averageSkips) {
        this.averageSkips = averageSkips;
        return this;
    }

    public void setAverageSkips(Integer averageSkips) {
        this.averageSkips = averageSkips;
    }

    public Integer getDrawsDue() {
        return drawsDue;
    }

    public HitPredict drawsDue(Integer drawsDue) {
        this.drawsDue = drawsDue;
        return this;
    }

    public void setDrawsDue(Integer drawsDue) {
        this.drawsDue = drawsDue;
    }

    public String getAllskips() {
        return allskips;
    }

    public HitPredict allskips(String allskips) {
        this.allskips = allskips;
        return this;
    }

    public void setAllskips(String allskips) {
        this.allskips = allskips;
    }

    public String getStatus() {
        return status;
    }

    public HitPredict status(String status) {
        this.status = status;
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public GamesPlay getGame() {
        return game;
    }

    public HitPredict game(GamesPlay game) {
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
        HitPredict hitPredict = (HitPredict) o;
        if (hitPredict.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, hitPredict.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "HitPredict{" +
            "id=" + id +
            ", number='" + number + "'" +
            ", drawPart='" + drawPart + "'" +
            ", currentSkips='" + currentSkips + "'" +
            ", averageSkips='" + averageSkips + "'" +
            ", drawsDue='" + drawsDue + "'" +
            ", allskips='" + allskips + "'" +
            ", status='" + status + "'" +
            ", game='" + game + "'" +
            '}';
    }
}
