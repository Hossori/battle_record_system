package models;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import constants.JpaConst;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/*
 * ゲーム
 */
@Table(name=JpaConst.TABLE_GAME)
@NamedQueries({
    @NamedQuery(name=JpaConst.Q_GAME_GET_ALL,
                query=JpaConst.Q_GAME_GET_ALL_DEF),
    @NamedQuery(name=JpaConst.Q_GAME_COUNT_ALL,
                query=JpaConst.Q_GAME_COUNT_ALL_DEF),
    @NamedQuery(name=JpaConst.Q_GAME_COUNT_BY_NAME,
                query=JpaConst.Q_GAME_COUNT_BY_NAME_DEF)
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Game {
    @Id
    @Column(name=JpaConst.GAME_COL_ID)
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;

    @Column(name=JpaConst.GAME_COL_NAME, nullable=false)
    private String name;

    @Column(name=JpaConst.GAME_COL_DELETE_FLAG, nullable=false)
    private Integer deleteFlag;

    @OneToMany(mappedBy="game")
    private List<Mode> modeList;
}