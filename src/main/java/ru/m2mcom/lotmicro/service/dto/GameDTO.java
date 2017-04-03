package ru.m2mcom.lotmicro.service.dto;


import java.io.Serializable;
import java.util.Objects;
import ru.m2mcom.lotmicro.domain.enumeration.GamesPlay;

/**
 * A DTO for the Game entity.
 */
public class GameDTO implements Serializable {

    private Long id;

    private GamesPlay gameName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public GamesPlay getGameName() {
        return gameName;
    }

    public void setGameName(GamesPlay gameName) {
        this.gameName = gameName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        GameDTO gameDTO = (GameDTO) o;

        if ( ! Objects.equals(id, gameDTO.id)) { return false; }

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "GameDTO{" +
            "id=" + id +
            ", gameName='" + gameName + "'" +
            '}';
    }
}
