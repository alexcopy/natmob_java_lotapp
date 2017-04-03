package ru.m2mcom.lotmicro.domain;

import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

import ru.m2mcom.lotmicro.domain.enumeration.GamesPlay;

/**
 * A LocalGenerated.
 */
@Entity
@Table(name = "local_generated")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "localgenerated")
public class LocalGenerated implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    /**
     * json Format
     */
    @ApiModelProperty(value = "json Format")
    @Column(name = "draw")
    private String draw;

    @Column(name = "sum_b")
    private Integer sumB;

    @Column(name = "sum_s")
    private Integer sumS;

    @Column(name = "hash")
    private String hash;

    @Enumerated(EnumType.STRING)
    @Column(name = "game")
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

    public LocalGenerated draw(String draw) {
        this.draw = draw;
        return this;
    }

    public void setDraw(String draw) {
        this.draw = draw;
    }

    public Integer getSumB() {
        return sumB;
    }

    public LocalGenerated sumB(Integer sumB) {
        this.sumB = sumB;
        return this;
    }

    public void setSumB(Integer sumB) {
        this.sumB = sumB;
    }

    public Integer getSumS() {
        return sumS;
    }

    public LocalGenerated sumS(Integer sumS) {
        this.sumS = sumS;
        return this;
    }

    public void setSumS(Integer sumS) {
        this.sumS = sumS;
    }

    public String getHash() {
        return hash;
    }

    public LocalGenerated hash(String hash) {
        this.hash = hash;
        return this;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public GamesPlay getGame() {
        return game;
    }

    public LocalGenerated game(GamesPlay game) {
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
        LocalGenerated localGenerated = (LocalGenerated) o;
        if (localGenerated.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, localGenerated.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "LocalGenerated{" +
            "id=" + id +
            ", draw='" + draw + "'" +
            ", sumB='" + sumB + "'" +
            ", sumS='" + sumS + "'" +
            ", hash='" + hash + "'" +
            ", game='" + game + "'" +
            '}';
    }
}
