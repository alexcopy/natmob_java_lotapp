package ru.m2mcom.natmob.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Ranks.
 */
@Entity
@Table(name = "ranks")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "ranks")
public class Ranks implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "game", nullable = false)
    private String game;

    @Column(name = "all_numbers_rank")
    private Integer allNumbersRank;

    @Column(name = "long_odd_even_rank")
    private Integer longOddEvenRank;

    @Column(name = "short_odd_even_rank")
    private Integer shortOddEvenRank;

    @Column(name = "short_history_sum_rank")
    private Integer shortHistorySumRank;

    @Column(name = "long_hist_sum_rank")
    private Integer longHistSumRank;

    @Column(name = "summ_in_hist_rank")
    private Integer summInHistRank;

    @Column(name = "been_drawn_in_past")
    private Integer beenDrawnInPast;

    @Column(name = "summ_in_recent_hist_rank")
    private Integer summInRecentHistRank;

    @Column(name = "total_rank")
    private Integer totalRank;

    @OneToOne
    @JoinColumn(unique = true)
    private Eml_plays eml_plays;

    @OneToOne
    @JoinColumn(unique = true)
    private Thb_plays thb_plays;

    @OneToOne
    @JoinColumn(unique = true)
    private Nat_plays nat_plays;

    @OneToOne
    @JoinColumn(unique = true)
    private Hot_plays hot_plays;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGame() {
        return game;
    }

    public Ranks game(String game) {
        this.game = game;
        return this;
    }

    public void setGame(String game) {
        this.game = game;
    }

    public Integer getAllNumbersRank() {
        return allNumbersRank;
    }

    public Ranks allNumbersRank(Integer allNumbersRank) {
        this.allNumbersRank = allNumbersRank;
        return this;
    }

    public void setAllNumbersRank(Integer allNumbersRank) {
        this.allNumbersRank = allNumbersRank;
    }

    public Integer getLongOddEvenRank() {
        return longOddEvenRank;
    }

    public Ranks longOddEvenRank(Integer longOddEvenRank) {
        this.longOddEvenRank = longOddEvenRank;
        return this;
    }

    public void setLongOddEvenRank(Integer longOddEvenRank) {
        this.longOddEvenRank = longOddEvenRank;
    }

    public Integer getShortOddEvenRank() {
        return shortOddEvenRank;
    }

    public Ranks shortOddEvenRank(Integer shortOddEvenRank) {
        this.shortOddEvenRank = shortOddEvenRank;
        return this;
    }

    public void setShortOddEvenRank(Integer shortOddEvenRank) {
        this.shortOddEvenRank = shortOddEvenRank;
    }

    public Integer getShortHistorySumRank() {
        return shortHistorySumRank;
    }

    public Ranks shortHistorySumRank(Integer shortHistorySumRank) {
        this.shortHistorySumRank = shortHistorySumRank;
        return this;
    }

    public void setShortHistorySumRank(Integer shortHistorySumRank) {
        this.shortHistorySumRank = shortHistorySumRank;
    }

    public Integer getLongHistSumRank() {
        return longHistSumRank;
    }

    public Ranks longHistSumRank(Integer longHistSumRank) {
        this.longHistSumRank = longHistSumRank;
        return this;
    }

    public void setLongHistSumRank(Integer longHistSumRank) {
        this.longHistSumRank = longHistSumRank;
    }

    public Integer getSummInHistRank() {
        return summInHistRank;
    }

    public Ranks summInHistRank(Integer summInHistRank) {
        this.summInHistRank = summInHistRank;
        return this;
    }

    public void setSummInHistRank(Integer summInHistRank) {
        this.summInHistRank = summInHistRank;
    }

    public Integer getBeenDrawnInPast() {
        return beenDrawnInPast;
    }

    public Ranks beenDrawnInPast(Integer beenDrawnInPast) {
        this.beenDrawnInPast = beenDrawnInPast;
        return this;
    }

    public void setBeenDrawnInPast(Integer beenDrawnInPast) {
        this.beenDrawnInPast = beenDrawnInPast;
    }

    public Integer getSummInRecentHistRank() {
        return summInRecentHistRank;
    }

    public Ranks summInRecentHistRank(Integer summInRecentHistRank) {
        this.summInRecentHistRank = summInRecentHistRank;
        return this;
    }

    public void setSummInRecentHistRank(Integer summInRecentHistRank) {
        this.summInRecentHistRank = summInRecentHistRank;
    }

    public Integer getTotalRank() {
        return totalRank;
    }

    public Ranks totalRank(Integer totalRank) {
        this.totalRank = totalRank;
        return this;
    }

    public void setTotalRank(Integer totalRank) {
        this.totalRank = totalRank;
    }

    public Eml_plays getEml_plays() {
        return eml_plays;
    }

    public Ranks eml_plays(Eml_plays eml_plays) {
        this.eml_plays = eml_plays;
        return this;
    }

    public void setEml_plays(Eml_plays eml_plays) {
        this.eml_plays = eml_plays;
    }

    public Thb_plays getThb_plays() {
        return thb_plays;
    }

    public Ranks thb_plays(Thb_plays thb_plays) {
        this.thb_plays = thb_plays;
        return this;
    }

    public void setThb_plays(Thb_plays thb_plays) {
        this.thb_plays = thb_plays;
    }

    public Nat_plays getNat_plays() {
        return nat_plays;
    }

    public Ranks nat_plays(Nat_plays nat_plays) {
        this.nat_plays = nat_plays;
        return this;
    }

    public void setNat_plays(Nat_plays nat_plays) {
        this.nat_plays = nat_plays;
    }

    public Hot_plays getHot_plays() {
        return hot_plays;
    }

    public Ranks hot_plays(Hot_plays hot_plays) {
        this.hot_plays = hot_plays;
        return this;
    }

    public void setHot_plays(Hot_plays hot_plays) {
        this.hot_plays = hot_plays;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Ranks ranks = (Ranks) o;
        if (ranks.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, ranks.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Ranks{" +
            "id=" + id +
            ", game='" + game + "'" +
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
