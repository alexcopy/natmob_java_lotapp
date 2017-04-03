package ru.m2mcom.lotmicro.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the GameRule entity.
 */
public class GameRuleDTO implements Serializable {

    private Long id;

    @NotNull
    private Double ticketPrice;

    private String prizeTable;

    private String ballSet;

    private String bonusSet;

    private String daysPlay;

    private String ballRange;

    private String bonusRange;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public Double getTicketPrice() {
        return ticketPrice;
    }

    public void setTicketPrice(Double ticketPrice) {
        this.ticketPrice = ticketPrice;
    }
    public String getPrizeTable() {
        return prizeTable;
    }

    public void setPrizeTable(String prizeTable) {
        this.prizeTable = prizeTable;
    }
    public String getBallSet() {
        return ballSet;
    }

    public void setBallSet(String ballSet) {
        this.ballSet = ballSet;
    }
    public String getBonusSet() {
        return bonusSet;
    }

    public void setBonusSet(String bonusSet) {
        this.bonusSet = bonusSet;
    }
    public String getDaysPlay() {
        return daysPlay;
    }

    public void setDaysPlay(String daysPlay) {
        this.daysPlay = daysPlay;
    }
    public String getBallRange() {
        return ballRange;
    }

    public void setBallRange(String ballRange) {
        this.ballRange = ballRange;
    }
    public String getBonusRange() {
        return bonusRange;
    }

    public void setBonusRange(String bonusRange) {
        this.bonusRange = bonusRange;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        GameRuleDTO gameRuleDTO = (GameRuleDTO) o;

        if ( ! Objects.equals(id, gameRuleDTO.id)) { return false; }

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "GameRuleDTO{" +
            "id=" + id +
            ", ticketPrice='" + ticketPrice + "'" +
            ", prizeTable='" + prizeTable + "'" +
            ", ballSet='" + ballSet + "'" +
            ", bonusSet='" + bonusSet + "'" +
            ", daysPlay='" + daysPlay + "'" +
            ", ballRange='" + ballRange + "'" +
            ", bonusRange='" + bonusRange + "'" +
            '}';
    }
}
