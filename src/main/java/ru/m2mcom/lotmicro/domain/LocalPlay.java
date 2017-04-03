package ru.m2mcom.lotmicro.domain;

import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

import ru.m2mcom.lotmicro.domain.enumeration.GameType;

import ru.m2mcom.lotmicro.domain.enumeration.GamesPlay;

/**
 * A LocalPlay.
 */
@Entity
@Table(name = "local_play")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "localplay")
public class LocalPlay implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "draw_date", nullable = false)
    private LocalDate drawDate;

    /**
     * json format
     */
    @NotNull
    @ApiModelProperty(value = "json format", required = true)
    @Column(name = "draw", nullable = false)
    private String draw;

    @Column(name = "sum_b")
    private Integer sumB;

    @Column(name = "sum_s")
    private Integer sumS;

    @Column(name = "draw_type")
    private String drawType;

    @Enumerated(EnumType.STRING)
    @Column(name = "game_type")
    private GameType gameType;

    @Column(name = "hash")
    private String hash;

    @Column(name = "prize")
    private Double prize;

    @Column(name = "checked")
    private Integer checked;

    @Column(name = "bonusrankid")
    private Integer bonusrankid;

    @Enumerated(EnumType.STRING)
    @Column(name = "game")
    private GamesPlay game;

    @OneToOne
    @JoinColumn(unique = true)
    private Rank rank;

    @OneToOne
    @JoinColumn(unique = true)
    private BonusRank bonusrank;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDrawDate() {
        return drawDate;
    }

    public LocalPlay drawDate(LocalDate drawDate) {
        this.drawDate = drawDate;
        return this;
    }

    public void setDrawDate(LocalDate drawDate) {
        this.drawDate = drawDate;
    }

    public String getDraw() {
        return draw;
    }

    public LocalPlay draw(String draw) {
        this.draw = draw;
        return this;
    }

    public void setDraw(String draw) {
        this.draw = draw;
    }

    public Integer getSumB() {
        return sumB;
    }

    public LocalPlay sumB(Integer sumB) {
        this.sumB = sumB;
        return this;
    }

    public void setSumB(Integer sumB) {
        this.sumB = sumB;
    }

    public Integer getSumS() {
        return sumS;
    }

    public LocalPlay sumS(Integer sumS) {
        this.sumS = sumS;
        return this;
    }

    public void setSumS(Integer sumS) {
        this.sumS = sumS;
    }

    public String getDrawType() {
        return drawType;
    }

    public LocalPlay drawType(String drawType) {
        this.drawType = drawType;
        return this;
    }

    public void setDrawType(String drawType) {
        this.drawType = drawType;
    }

    public GameType getGameType() {
        return gameType;
    }

    public LocalPlay gameType(GameType gameType) {
        this.gameType = gameType;
        return this;
    }

    public void setGameType(GameType gameType) {
        this.gameType = gameType;
    }

    public String getHash() {
        return hash;
    }

    public LocalPlay hash(String hash) {
        this.hash = hash;
        return this;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public Double getPrize() {
        return prize;
    }

    public LocalPlay prize(Double prize) {
        this.prize = prize;
        return this;
    }

    public void setPrize(Double prize) {
        this.prize = prize;
    }

    public Integer getChecked() {
        return checked;
    }

    public LocalPlay checked(Integer checked) {
        this.checked = checked;
        return this;
    }

    public void setChecked(Integer checked) {
        this.checked = checked;
    }

    public Integer getBonusrankid() {
        return bonusrankid;
    }

    public LocalPlay bonusrankid(Integer bonusrankid) {
        this.bonusrankid = bonusrankid;
        return this;
    }

    public void setBonusrankid(Integer bonusrankid) {
        this.bonusrankid = bonusrankid;
    }

    public GamesPlay getGame() {
        return game;
    }

    public LocalPlay game(GamesPlay game) {
        this.game = game;
        return this;
    }

    public void setGame(GamesPlay game) {
        this.game = game;
    }

    public Rank getRank() {
        return rank;
    }

    public LocalPlay rank(Rank rank) {
        this.rank = rank;
        return this;
    }

    public void setRank(Rank rank) {
        this.rank = rank;
    }

    public BonusRank getBonusrank() {
        return bonusrank;
    }

    public LocalPlay bonusrank(BonusRank bonusRank) {
        this.bonusrank = bonusRank;
        return this;
    }

    public void setBonusrank(BonusRank bonusRank) {
        this.bonusrank = bonusRank;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        LocalPlay localPlay = (LocalPlay) o;
        if (localPlay.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, localPlay.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "LocalPlay{" +
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
