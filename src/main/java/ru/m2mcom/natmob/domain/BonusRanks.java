package ru.m2mcom.natmob.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A BonusRanks.
 */
@Entity
@Table(name = "bonus_ranks")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "bonusranks")
public class BonusRanks implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "game", nullable = false)
    private String game;

    @Column(name = "summ_in_recent_hist_rank")
    private Integer summInRecentHistRank;

    @Column(name = "probability_and_mo")
    private Integer probabilityAndMO;

    @Column(name = "long_odd_even_rank")
    private Integer longOddEvenRank;

    @Column(name = "summ_analisis_with_weights")
    private Integer summAnalisisWithWeights;

    @Column(name = "in_hist_due_not_due_analisis")
    private Integer inHistDueNotDueAnalisis;

    @Column(name = "all_numbers_rank")
    private Integer allNumbersRank;

    @Column(name = "total_rank")
    private Integer totalRank;

    @Column(name = "timestamp")
    private Integer timestamp;

    @OneToOne
    @JoinColumn(unique = true)
    private Eml_plays eml_plays;

    @OneToOne
    @JoinColumn(unique = true)
    private Thb_plays thb_plays;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGame() {
        return game;
    }

    public BonusRanks game(String game) {
        this.game = game;
        return this;
    }

    public void setGame(String game) {
        this.game = game;
    }

    public Integer getSummInRecentHistRank() {
        return summInRecentHistRank;
    }

    public BonusRanks summInRecentHistRank(Integer summInRecentHistRank) {
        this.summInRecentHistRank = summInRecentHistRank;
        return this;
    }

    public void setSummInRecentHistRank(Integer summInRecentHistRank) {
        this.summInRecentHistRank = summInRecentHistRank;
    }

    public Integer getProbabilityAndMO() {
        return probabilityAndMO;
    }

    public BonusRanks probabilityAndMO(Integer probabilityAndMO) {
        this.probabilityAndMO = probabilityAndMO;
        return this;
    }

    public void setProbabilityAndMO(Integer probabilityAndMO) {
        this.probabilityAndMO = probabilityAndMO;
    }

    public Integer getLongOddEvenRank() {
        return longOddEvenRank;
    }

    public BonusRanks longOddEvenRank(Integer longOddEvenRank) {
        this.longOddEvenRank = longOddEvenRank;
        return this;
    }

    public void setLongOddEvenRank(Integer longOddEvenRank) {
        this.longOddEvenRank = longOddEvenRank;
    }

    public Integer getSummAnalisisWithWeights() {
        return summAnalisisWithWeights;
    }

    public BonusRanks summAnalisisWithWeights(Integer summAnalisisWithWeights) {
        this.summAnalisisWithWeights = summAnalisisWithWeights;
        return this;
    }

    public void setSummAnalisisWithWeights(Integer summAnalisisWithWeights) {
        this.summAnalisisWithWeights = summAnalisisWithWeights;
    }

    public Integer getInHistDueNotDueAnalisis() {
        return inHistDueNotDueAnalisis;
    }

    public BonusRanks inHistDueNotDueAnalisis(Integer inHistDueNotDueAnalisis) {
        this.inHistDueNotDueAnalisis = inHistDueNotDueAnalisis;
        return this;
    }

    public void setInHistDueNotDueAnalisis(Integer inHistDueNotDueAnalisis) {
        this.inHistDueNotDueAnalisis = inHistDueNotDueAnalisis;
    }

    public Integer getAllNumbersRank() {
        return allNumbersRank;
    }

    public BonusRanks allNumbersRank(Integer allNumbersRank) {
        this.allNumbersRank = allNumbersRank;
        return this;
    }

    public void setAllNumbersRank(Integer allNumbersRank) {
        this.allNumbersRank = allNumbersRank;
    }

    public Integer getTotalRank() {
        return totalRank;
    }

    public BonusRanks totalRank(Integer totalRank) {
        this.totalRank = totalRank;
        return this;
    }

    public void setTotalRank(Integer totalRank) {
        this.totalRank = totalRank;
    }

    public Integer getTimestamp() {
        return timestamp;
    }

    public BonusRanks timestamp(Integer timestamp) {
        this.timestamp = timestamp;
        return this;
    }

    public void setTimestamp(Integer timestamp) {
        this.timestamp = timestamp;
    }

    public Eml_plays getEml_plays() {
        return eml_plays;
    }

    public BonusRanks eml_plays(Eml_plays eml_plays) {
        this.eml_plays = eml_plays;
        return this;
    }

    public void setEml_plays(Eml_plays eml_plays) {
        this.eml_plays = eml_plays;
    }

    public Thb_plays getThb_plays() {
        return thb_plays;
    }

    public BonusRanks thb_plays(Thb_plays thb_plays) {
        this.thb_plays = thb_plays;
        return this;
    }

    public void setThb_plays(Thb_plays thb_plays) {
        this.thb_plays = thb_plays;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        BonusRanks bonusRanks = (BonusRanks) o;
        if (bonusRanks.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, bonusRanks.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "BonusRanks{" +
            "id=" + id +
            ", game='" + game + "'" +
            ", summInRecentHistRank='" + summInRecentHistRank + "'" +
            ", probabilityAndMO='" + probabilityAndMO + "'" +
            ", longOddEvenRank='" + longOddEvenRank + "'" +
            ", summAnalisisWithWeights='" + summAnalisisWithWeights + "'" +
            ", inHistDueNotDueAnalisis='" + inHistDueNotDueAnalisis + "'" +
            ", allNumbersRank='" + allNumbersRank + "'" +
            ", totalRank='" + totalRank + "'" +
            ", timestamp='" + timestamp + "'" +
            '}';
    }
}
