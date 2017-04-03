package ru.m2mcom.lotmicro.domain;

import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A GameRule.
 */
@Entity
@Table(name = "game_rule")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "gamerule")
public class GameRule implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "ticket_price", nullable = false)
    private Double ticketPrice;

    /**
     * json format
     */
    @ApiModelProperty(value = "json format")
    @Column(name = "prize_table")
    private String prizeTable;

    /**
     * json format
     */
    @ApiModelProperty(value = "json format")
    @Column(name = "ball_set")
    private String ballSet;

    /**
     * json format
     */
    @ApiModelProperty(value = "json format")
    @Column(name = "bonus_set")
    private String bonusSet;

    /**
     * json format
     */
    @ApiModelProperty(value = "json format")
    @Column(name = "days_play")
    private String daysPlay;

    /**
     * json format
     */
    @ApiModelProperty(value = "json format")
    @Column(name = "ball_range")
    private String ballRange;

    /**
     * json format
     */
    @ApiModelProperty(value = "json format")
    @Column(name = "bonus_range")
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

    public GameRule ticketPrice(Double ticketPrice) {
        this.ticketPrice = ticketPrice;
        return this;
    }

    public void setTicketPrice(Double ticketPrice) {
        this.ticketPrice = ticketPrice;
    }

    public String getPrizeTable() {
        return prizeTable;
    }

    public GameRule prizeTable(String prizeTable) {
        this.prizeTable = prizeTable;
        return this;
    }

    public void setPrizeTable(String prizeTable) {
        this.prizeTable = prizeTable;
    }

    public String getBallSet() {
        return ballSet;
    }

    public GameRule ballSet(String ballSet) {
        this.ballSet = ballSet;
        return this;
    }

    public void setBallSet(String ballSet) {
        this.ballSet = ballSet;
    }

    public String getBonusSet() {
        return bonusSet;
    }

    public GameRule bonusSet(String bonusSet) {
        this.bonusSet = bonusSet;
        return this;
    }

    public void setBonusSet(String bonusSet) {
        this.bonusSet = bonusSet;
    }

    public String getDaysPlay() {
        return daysPlay;
    }

    public GameRule daysPlay(String daysPlay) {
        this.daysPlay = daysPlay;
        return this;
    }

    public void setDaysPlay(String daysPlay) {
        this.daysPlay = daysPlay;
    }

    public String getBallRange() {
        return ballRange;
    }

    public GameRule ballRange(String ballRange) {
        this.ballRange = ballRange;
        return this;
    }

    public void setBallRange(String ballRange) {
        this.ballRange = ballRange;
    }

    public String getBonusRange() {
        return bonusRange;
    }

    public GameRule bonusRange(String bonusRange) {
        this.bonusRange = bonusRange;
        return this;
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
        GameRule gameRule = (GameRule) o;
        if (gameRule.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, gameRule.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "GameRule{" +
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
