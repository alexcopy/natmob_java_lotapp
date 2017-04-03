package ru.m2mcom.lotmicro.service.dto;


import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Rank entity.
 */
public class RankDTO implements Serializable {

    private Long id;

    private Double allNumbersRank;

    private Double longOddEvenRank;

    private Double shortOddEvenRank;

    private Double shortHistorySumRank;

    private Double longHistSumRank;

    private Double summInHistRank;

    private Double beenDrawnInPast;

    private Double summInRecentHistRank;

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

    public void setAllNumbersRank(Double allNumbersRank) {
        this.allNumbersRank = allNumbersRank;
    }
    public Double getLongOddEvenRank() {
        return longOddEvenRank;
    }

    public void setLongOddEvenRank(Double longOddEvenRank) {
        this.longOddEvenRank = longOddEvenRank;
    }
    public Double getShortOddEvenRank() {
        return shortOddEvenRank;
    }

    public void setShortOddEvenRank(Double shortOddEvenRank) {
        this.shortOddEvenRank = shortOddEvenRank;
    }
    public Double getShortHistorySumRank() {
        return shortHistorySumRank;
    }

    public void setShortHistorySumRank(Double shortHistorySumRank) {
        this.shortHistorySumRank = shortHistorySumRank;
    }
    public Double getLongHistSumRank() {
        return longHistSumRank;
    }

    public void setLongHistSumRank(Double longHistSumRank) {
        this.longHistSumRank = longHistSumRank;
    }
    public Double getSummInHistRank() {
        return summInHistRank;
    }

    public void setSummInHistRank(Double summInHistRank) {
        this.summInHistRank = summInHistRank;
    }
    public Double getBeenDrawnInPast() {
        return beenDrawnInPast;
    }

    public void setBeenDrawnInPast(Double beenDrawnInPast) {
        this.beenDrawnInPast = beenDrawnInPast;
    }
    public Double getSummInRecentHistRank() {
        return summInRecentHistRank;
    }

    public void setSummInRecentHistRank(Double summInRecentHistRank) {
        this.summInRecentHistRank = summInRecentHistRank;
    }
    public Double getTotalRank() {
        return totalRank;
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

        RankDTO rankDTO = (RankDTO) o;

        if ( ! Objects.equals(id, rankDTO.id)) { return false; }

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "RankDTO{" +
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
