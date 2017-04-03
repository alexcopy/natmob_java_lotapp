package ru.m2mcom.lotmicro.service.dto;


import java.io.Serializable;
import java.util.Objects;
import ru.m2mcom.lotmicro.domain.enumeration.DrawPartType;
import ru.m2mcom.lotmicro.domain.enumeration.GamesPlay;

/**
 * A DTO for the HitPredict entity.
 */
public class HitPredictDTO implements Serializable {

    private Long id;

    private Integer number;

    private DrawPartType drawPart;

    private Integer currentSkips;

    private Integer averageSkips;

    private Integer drawsDue;

    private String allskips;

    private String status;

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

    public void setNumber(Integer number) {
        this.number = number;
    }
    public DrawPartType getDrawPart() {
        return drawPart;
    }

    public void setDrawPart(DrawPartType drawPart) {
        this.drawPart = drawPart;
    }
    public Integer getCurrentSkips() {
        return currentSkips;
    }

    public void setCurrentSkips(Integer currentSkips) {
        this.currentSkips = currentSkips;
    }
    public Integer getAverageSkips() {
        return averageSkips;
    }

    public void setAverageSkips(Integer averageSkips) {
        this.averageSkips = averageSkips;
    }
    public Integer getDrawsDue() {
        return drawsDue;
    }

    public void setDrawsDue(Integer drawsDue) {
        this.drawsDue = drawsDue;
    }
    public String getAllskips() {
        return allskips;
    }

    public void setAllskips(String allskips) {
        this.allskips = allskips;
    }
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    public GamesPlay getGame() {
        return game;
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

        HitPredictDTO hitPredictDTO = (HitPredictDTO) o;

        if ( ! Objects.equals(id, hitPredictDTO.id)) { return false; }

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "HitPredictDTO{" +
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
