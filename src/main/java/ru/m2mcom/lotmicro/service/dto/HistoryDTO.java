package ru.m2mcom.lotmicro.service.dto;

import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import ru.m2mcom.lotmicro.domain.enumeration.GamesPlay;

/**
 * A DTO for the History entity.
 */
public class HistoryDTO implements Serializable {

    private Long id;

    @NotNull
    private LocalDate drawDate;

    private Integer ball1;

    private Integer ball2;

    private Integer ball3;

    private Integer ball4;

    private Integer ball5;

    private Integer ball6;

    private Integer bonusBall1;

    private Integer bonusBall2;

    private Integer drawNumber;

    private String ballSet;

    private String wins;

    private String machine;

    private Integer sumB;

    private Integer sumS;

    private Integer jackpot;

    private String hash;

    private String drawHash;

    private GamesPlay game;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public LocalDate getDrawDate() {
        return drawDate;
    }

    public void setDrawDate(LocalDate drawDate) {
        this.drawDate = drawDate;
    }
    public Integer getBall1() {
        return ball1;
    }

    public void setBall1(Integer ball1) {
        this.ball1 = ball1;
    }
    public Integer getBall2() {
        return ball2;
    }

    public void setBall2(Integer ball2) {
        this.ball2 = ball2;
    }
    public Integer getBall3() {
        return ball3;
    }

    public void setBall3(Integer ball3) {
        this.ball3 = ball3;
    }
    public Integer getBall4() {
        return ball4;
    }

    public void setBall4(Integer ball4) {
        this.ball4 = ball4;
    }
    public Integer getBall5() {
        return ball5;
    }

    public void setBall5(Integer ball5) {
        this.ball5 = ball5;
    }
    public Integer getBall6() {
        return ball6;
    }

    public void setBall6(Integer ball6) {
        this.ball6 = ball6;
    }
    public Integer getBonusBall1() {
        return bonusBall1;
    }

    public void setBonusBall1(Integer bonusBall1) {
        this.bonusBall1 = bonusBall1;
    }
    public Integer getBonusBall2() {
        return bonusBall2;
    }

    public void setBonusBall2(Integer bonusBall2) {
        this.bonusBall2 = bonusBall2;
    }
    public Integer getDrawNumber() {
        return drawNumber;
    }

    public void setDrawNumber(Integer drawNumber) {
        this.drawNumber = drawNumber;
    }
    public String getBallSet() {
        return ballSet;
    }

    public void setBallSet(String ballSet) {
        this.ballSet = ballSet;
    }
    public String getWins() {
        return wins;
    }

    public void setWins(String wins) {
        this.wins = wins;
    }
    public String getMachine() {
        return machine;
    }

    public void setMachine(String machine) {
        this.machine = machine;
    }
    public Integer getSumB() {
        return sumB;
    }

    public void setSumB(Integer sumB) {
        this.sumB = sumB;
    }
    public Integer getSumS() {
        return sumS;
    }

    public void setSumS(Integer sumS) {
        this.sumS = sumS;
    }
    public Integer getJackpot() {
        return jackpot;
    }

    public void setJackpot(Integer jackpot) {
        this.jackpot = jackpot;
    }
    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }
    public String getDrawHash() {
        return drawHash;
    }

    public void setDrawHash(String drawHash) {
        this.drawHash = drawHash;
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

        HistoryDTO historyDTO = (HistoryDTO) o;

        if ( ! Objects.equals(id, historyDTO.id)) { return false; }

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "HistoryDTO{" +
            "id=" + id +
            ", drawDate='" + drawDate + "'" +
            ", ball1='" + ball1 + "'" +
            ", ball2='" + ball2 + "'" +
            ", ball3='" + ball3 + "'" +
            ", ball4='" + ball4 + "'" +
            ", ball5='" + ball5 + "'" +
            ", ball6='" + ball6 + "'" +
            ", bonusBall1='" + bonusBall1 + "'" +
            ", bonusBall2='" + bonusBall2 + "'" +
            ", drawNumber='" + drawNumber + "'" +
            ", ballSet='" + ballSet + "'" +
            ", wins='" + wins + "'" +
            ", machine='" + machine + "'" +
            ", sumB='" + sumB + "'" +
            ", sumS='" + sumS + "'" +
            ", jackpot='" + jackpot + "'" +
            ", hash='" + hash + "'" +
            ", drawHash='" + drawHash + "'" +
            ", game='" + game + "'" +
            '}';
    }
}
