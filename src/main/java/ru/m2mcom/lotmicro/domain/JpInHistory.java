package ru.m2mcom.lotmicro.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

import ru.m2mcom.lotmicro.domain.enumeration.DrawsTable;

import ru.m2mcom.lotmicro.domain.enumeration.GamesPlay;

/**
 * A JpInHistory.
 */
@Entity
@Table(name = "jp_in_history")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "jpinhistory")
public class JpInHistory implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "draw_date", nullable = false)
    private LocalDate drawDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "table_name")
    private DrawsTable tableName;

    @Column(name = "tableid")
    private Integer tableid;

    @Enumerated(EnumType.STRING)
    @Column(name = "game")
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

    public JpInHistory drawDate(LocalDate drawDate) {
        this.drawDate = drawDate;
        return this;
    }

    public void setDrawDate(LocalDate drawDate) {
        this.drawDate = drawDate;
    }

    public DrawsTable getTableName() {
        return tableName;
    }

    public JpInHistory tableName(DrawsTable tableName) {
        this.tableName = tableName;
        return this;
    }

    public void setTableName(DrawsTable tableName) {
        this.tableName = tableName;
    }

    public Integer getTableid() {
        return tableid;
    }

    public JpInHistory tableid(Integer tableid) {
        this.tableid = tableid;
        return this;
    }

    public void setTableid(Integer tableid) {
        this.tableid = tableid;
    }

    public GamesPlay getGame() {
        return game;
    }

    public JpInHistory game(GamesPlay game) {
        this.game = game;
        return this;
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
        JpInHistory jpInHistory = (JpInHistory) o;
        if (jpInHistory.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, jpInHistory.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "JpInHistory{" +
            "id=" + id +
            ", drawDate='" + drawDate + "'" +
            ", tableName='" + tableName + "'" +
            ", tableid='" + tableid + "'" +
            ", game='" + game + "'" +
            '}';
    }
}
