package ru.m2mcom.lotmicro.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Rank.
 */
@Entity
@Table(name = "rank")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "rank")
public class Rank implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "all_numbers_rank")
    private Double allNumbersRank;

    @Column(name = "long_odd_even_rank")
    private Double longOddEvenRank;

    @Column(name = "short_odd_even_rank")
    private Double shortOddEvenRank;

    @Column(name = "short_history_sum_rank")
    private Double shortHistorySumRank;

    @Column(name = "long_hist_sum_rank")
    private Double longHistSumRank;

    @Column(name = "summ_in_hist_rank")
    private Double summInHistRank;

    @Column(name = "been_drawn_in_past")
    private Double beenDrawnInPast;

    @Column(name = "summ_in_recent_hist_rank")
    private Double summInRecentHistRank;

    @Column(name = "total_rank")
    private Double totalRank;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getAllNumbersRank() {
        return allNumbersRank;
    }

    public Rank allNumbersRank(Double allNumbersRank) {
        this.allNumbersRank = allNumbersRank;
        return this;
    }

    public void setAllNumbersRank(Double allNumbersRank) {
        this.allNumbersRank = allNumbersRank;
    }

    public Double getLongOddEvenRank() {
        return longOddEvenRank;
    }

    public Rank longOddEvenRank(Double longOddEvenRank) {
        this.longOddEvenRank = longOddEvenRank;
        return this;
    }

    public void setLongOddEvenRank(Double longOddEvenRank) {
        this.longOddEvenRank = longOddEvenRank;
    }

    public Double getShortOddEvenRank() {
        return shortOddEvenRank;
    }

    public Rank shortOddEvenRank(Double shortOddEvenRank) {
        this.shortOddEvenRank = shortOddEvenRank;
        return this;
    }

    public void setShortOddEvenRank(Double shortOddEvenRank) {
        this.shortOddEvenRank = shortOddEvenRank;
    }

    public Double getShortHistorySumRank() {
        return shortHistorySumRank;
    }

    public Rank shortHistorySumRank(Double shortHistorySumRank) {
        this.shortHistorySumRank = shortHistorySumRank;
        return this;
    }

    public void setShortHistorySumRank(Double shortHistorySumRank) {
        this.shortHistorySumRank = shortHistorySumRank;
    }

    public Double getLongHistSumRank() {
        return longHistSumRank;
    }

    public Rank longHistSumRank(Double longHistSumRank) {
        this.longHistSumRank = longHistSumRank;
        return this;
    }

    public void setLongHistSumRank(Double longHistSumRank) {
        this.longHistSumRank = longHistSumRank;
    }

    public Double getSummInHistRank() {
        return summInHistRank;
    }

    public Rank summInHistRank(Double summInHistRank) {
        this.summInHistRank = summInHistRank;
        return this;
    }

    public void setSummInHistRank(Double summInHistRank) {
        this.summInHistRank = summInHistRank;
    }

    public Double getBeenDrawnInPast() {
        return beenDrawnInPast;
    }

    public Rank beenDrawnInPast(Double beenDrawnInPast) {
        this.beenDrawnInPast = beenDrawnInPast;
        return this;
    }

    public void setBeenDrawnInPast(Double beenDrawnInPast) {
        this.beenDrawnInPast = beenDrawnInPast;
    }

    public Double getSummInRecentHistRank() {
        return summInRecentHistRank;
    }

    public Rank summInRecentHistRank(Double summInRecentHistRank) {
        this.summInRecentHistRank = summInRecentHistRank;
        return this;
    }

    public void setSummInRecentHistRank(Double summInRecentHistRank) {
        this.summInRecentHistRank = summInRecentHistRank;
    }

    public Double getTotalRank() {
        return totalRank;
    }

    public Rank totalRank(Double totalRank) {
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
        Rank rank = (Rank) o;
        if (rank.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, rank.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Rank{" +
            "id=" + id +
            ", allNumbersRank='" + allNumbersRank + "'" +
            ", longOddEvenRank='" + longOddEvenRank + "'" +
            ", shortOddEvenRank='" + shortOddEvenRank + "'" +
            ", shortHistorySumRank='" + shortHistorySumRank + "'" +
            ", longHistSumRank='" + longHistSumRank + "'" +
            ", summInHistRank='" + summInHistRank + "'" +
            ", beenDrawnInPast='" + beenDrawnInPast + "'" +
            ", summInRecentHistRank='" + summInRecentHistRank + "'" +
            ", totalRank='" + totalRank + "'" +
            '}';
    }
}
