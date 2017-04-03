package ru.m2mcom.lotmicro.service.dto;

import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import ru.m2mcom.lotmicro.domain.enumeration.DrawsTable;
import ru.m2mcom.lotmicro.domain.enumeration.GamesPlay;

/**
 * A DTO for the JpInHistory entity.
 */
public class JpInHistoryDTO implements Serializable {

    private Long id;

    @NotNull
    private LocalDate drawDate;

    private DrawsTable tableName;

    private Integer tableid;

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
    public DrawsTable getTableName() {
        return tableName;
    }

    public void setTableName(DrawsTable tableName) {
        this.tableName = tableName;
    }
    public Integer getTableid() {
        return tableid;
    }

    public void setTableid(Integer tableid) {
        this.tableid = tableid;
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

        JpInHistoryDTO jpInHistoryDTO = (JpInHistoryDTO) o;

        if ( ! Objects.equals(id, jpInHistoryDTO.id)) { return false; }

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "JpInHistoryDTO{" +
            "id=" + id +
            ", drawDate='" + drawDate + "'" +
            ", tableName='" + tableName + "'" +
            ", tableid='" + tableid + "'" +
            ", game='" + game + "'" +
            '}';
    }
}
