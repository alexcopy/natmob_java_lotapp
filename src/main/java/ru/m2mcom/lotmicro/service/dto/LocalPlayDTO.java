package ru.m2mcom.lotmicro.service.dto;

import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;
import ru.m2mcom.lotmicro.domain.enumeration.GameType;
import ru.m2mcom.lotmicro.domain.enumeration.GamesPlay;

/**
 * A DTO for the LocalPlay entity.
 */
public class LocalPlayDTO implements Serializable {

    private Long id;

    @NotNull
    private LocalDate drawDate;

    @NotNull
    private String draw;

    private Integer sumB;

    private Integer sumS;

    private String drawType;

    private GameType gameType;

    private String hash;

    private Double prize;

    private Integer checked;

    private Integer bonusrankid;

    private GamesPlay game;

    private Long rankId;

    private Long bonusrankId;

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
    public String getDraw() {
        return draw;
    }

    public void setDraw(String draw) {
        this.draw = draw;
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
    public String getDrawType() {
        return drawType;
    }

    public void setDrawType(String drawType) {
        this.drawType = drawType;
    }
    public GameType getGameType() {
        return gameType;
    }

    public void setGameType(GameType gameType) {
        this.gameType = gameType;
    }
    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }
    public Double getPrize() {
        return prize;
    }

    public void setPrize(Double prize) {
        this.prize = prize;
    }
    public Integer getChecked() {
        return checked;
    }

    public void setChecked(Integer checked) {
        this.checked = checked;
    }
    public Integer getBonusrankid() {
        return bonusrankid;
    }

    public void setBonusrankid(Integer bonusrankid) {
        this.bonusrankid = bonusrankid;
    }
    public GamesPlay getGame() {
        return game;
    }

    public void setGame(GamesPlay game) {
        this.game = game;
    }

    public Long getRankId() {
        return rankId;
    }

    public void setRankId(Long rankId) {
        this.rankId = rankId;
    }

    public Long getBonusrankId() {
        return bonusrankId;
    }

    public void setBonusrankId(Long bonusRankId) {
        this.bonusrankId = bonusRankId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        LocalPlayDTO localPlayDTO = (LocalPlayDTO) o;

        if ( ! Objects.equals(id, localPlayDTO.id)) { return false; }

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "LocalPlayDTO{" +
            "id=" + id +
            ", drawDate='" + drawDate + "'" +
            ", draw='" + draw + "'" +
            ", sumB='" + sumB + "'" +
            ", sumS='" + sumS + "'" +
            ", drawType='" + drawType + "'" +
            ", gameType='" + gameType + "'" +
            ", hash='" + hash + "'" +
            ", prize='" + prize + "'" +
            ", checked='" + checked + "'" +
            ", bonusrankid='" + bonusrankid + "'" +
            ", game='" + game + "'" +
            '}';
    }
}
