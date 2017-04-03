package ru.m2mcom.lotmicro.service.dto;


import java.io.Serializable;
import java.util.Objects;
import ru.m2mcom.lotmicro.domain.enumeration.GamesPlay;

/**
 * A DTO for the LocalGenerated entity.
 */
public class LocalGeneratedDTO implements Serializable {

    private Long id;

    private String draw;

    private Integer sumB;

    private Integer sumS;

    private String hash;

    private GamesPlay game;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
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

        LocalGeneratedDTO localGeneratedDTO = (LocalGeneratedDTO) o;

        if ( ! Objects.equals(id, localGeneratedDTO.id)) { return false; }

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "LocalGeneratedDTO{" +
            "id=" + id +
            ", draw='" + draw + "'" +
            ", sumB='" + sumB + "'" +
            ", sumS='" + sumS + "'" +
            ", hash='" + hash + "'" +
            ", game='" + game + "'" +
            '}';
    }
}
