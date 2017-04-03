package ru.m2mcom.lotmicro.service.dto;


import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the BonusRank entity.
 */
public class BonusRankDTO implements Serializable {

    private Long id;

    private Double summInRecentHistRank;

    private Double probabilityAndMO;

    private Double longOddEvenRank;

    private Double summAnalisisWithWeights;

    private Double inHistDueNotDueAnalisis;

    private Double allNumbersRank;

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

    public void setSummInRecentHistRank(Double summInRecentHistRank) {
        this.summInRecentHistRank = summInRecentHistRank;
    }
    public Double getProbabilityAndMO() {
        return probabilityAndMO;
    }

    public void setProbabilityAndMO(Double probabilityAndMO) {
        this.probabilityAndMO = probabilityAndMO;
    }
    public Double getLongOddEvenRank() {
        return longOddEvenRank;
    }

    public void setLongOddEvenRank(Double longOddEvenRank) {
        this.longOddEvenRank = longOddEvenRank;
    }
    public Double getSummAnalisisWithWeights() {
        return summAnalisisWithWeights;
    }

    public void setSummAnalisisWithWeights(Double summAnalisisWithWeights) {
        this.summAnalisisWithWeights = summAnalisisWithWeights;
    }
    public Double getInHistDueNotDueAnalisis() {
        return inHistDueNotDueAnalisis;
    }

    public void setInHistDueNotDueAnalisis(Double inHistDueNotDueAnalisis) {
        this.inHistDueNotDueAnalisis = inHistDueNotDueAnalisis;
    }
    public Double getAllNumbersRank() {
        return allNumbersRank;
    }

    public void setAllNumbersRank(Double allNumbersRank) {
        this.allNumbersRank = allNumbersRank;
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

        BonusRankDTO bonusRankDTO = (BonusRankDTO) o;

        if ( ! Objects.equals(id, bonusRankDTO.id)) { return false; }

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "BonusRankDTO{" +
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
