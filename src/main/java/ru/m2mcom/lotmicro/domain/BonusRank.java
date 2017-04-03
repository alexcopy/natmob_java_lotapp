package ru.m2mcom.lotmicro.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A BonusRank.
 */
@Entity
@Table(name = "bonus_rank")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "bonusrank")
public class BonusRank implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "summ_in_recent_hist_rank")
    private Double summInRecentHistRank;

    @Column(name = "probability_and_mo")
    private Double probabilityAndMO;

    @Column(name = "long_odd_even_rank")
    private Double longOddEvenRank;

    @Column(name = "summ_analisis_with_weights")
    private Double summAnalisisWithWeights;

    @Column(name = "in_hist_due_not_due_analisis")
    private Double inHistDueNotDueAnalisis;

    @Column(name = "all_numbers_rank")
    private Double allNumbersRank;

    @Column(name = "total_rank")
    private Double totalRank;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getSummInRecentHistRank() {
        return summInRecentHistRank;
    }

    public BonusRank summInRecentHistRank(Double summInRecentHistRank) {
        this.summInRecentHistRank = summInRecentHistRank;
        return this;
    }

    public void setSummInRecentHistRank(Double summInRecentHistRank) {
        this.summInRecentHistRank = summInRecentHistRank;
    }

    public Double getProbabilityAndMO() {
        return probabilityAndMO;
    }

    public BonusRank probabilityAndMO(Double probabilityAndMO) {
        this.probabilityAndMO = probabilityAndMO;
        return this;
    }

    public void setProbabilityAndMO(Double probabilityAndMO) {
        this.probabilityAndMO = probabilityAndMO;
    }

    public Double getLongOddEvenRank() {
        return longOddEvenRank;
    }

    public BonusRank longOddEvenRank(Double longOddEvenRank) {
        this.longOddEvenRank = longOddEvenRank;
        return this;
    }

    public void setLongOddEvenRank(Double longOddEvenRank) {
        this.longOddEvenRank = longOddEvenRank;
    }

    public Double getSummAnalisisWithWeights() {
        return summAnalisisWithWeights;
    }

    public BonusRank summAnalisisWithWeights(Double summAnalisisWithWeights) {
        this.summAnalisisWithWeights = summAnalisisWithWeights;
        return this;
    }

    public void setSummAnalisisWithWeights(Double summAnalisisWithWeights) {
        this.summAnalisisWithWeights = summAnalisisWithWeights;
    }

    public Double getInHistDueNotDueAnalisis() {
        return inHistDueNotDueAnalisis;
    }

    public BonusRank inHistDueNotDueAnalisis(Double inHistDueNotDueAnalisis) {
        this.inHistDueNotDueAnalisis = inHistDueNotDueAnalisis;
        return this;
    }

    public void setInHistDueNotDueAnalisis(Double inHistDueNotDueAnalisis) {
        this.inHistDueNotDueAnalisis = inHistDueNotDueAnalisis;
    }

    public Double getAllNumbersRank() {
        return allNumbersRank;
    }

    public BonusRank allNumbersRank(Double allNumbersRank) {
        this.allNumbersRank = allNumbersRank;
        return this;
    }

    public void setAllNumbersRank(Double allNumbersRank) {
        this.allNumbersRank = allNumbersRank;
    }

    public Double getTotalRank() {
        return totalRank;
    }

    public BonusRank totalRank(Double totalRank) {
        this.totalRank = totalRank;
        return this;
    }

    public void setTotalRank(Double totalRank) {
        this.totalRank = totalRank;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        BonusRank bonusRank = (BonusRank) o;
        if (bonusRank.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, bonusRank.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "BonusRank{" +
            "id=" + id +
            ", summInRecentHistRank='" + summInRecentHistRank + "'" +
            ", probabilityAndMO='" + probabilityAndMO + "'" +
            ", longOddEvenRank='" + longOddEvenRank + "'" +
            ", summAnalisisWithWeights='" + summAnalisisWithWeights + "'" +
            ", inHistDueNotDueAnalisis='" + inHistDueNotDueAnalisis + "'" +
            ", allNumbersRank='" + allNumbersRank + "'" +
            ", totalRank='" + totalRank + "'" +
            '}';
    }
}
