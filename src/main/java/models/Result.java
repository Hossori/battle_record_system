package models;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import constants.JpaConst;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name=JpaConst.TABLE_RESULT)
@NamedQueries({
    @NamedQuery(
            name = JpaConst.Q_RESULT_GET_BY_USER_AND_GAME_AND_MODE,
            query = JpaConst.Q_RESULT_GET_BY_USER_AND_GAME_AND_MODE_DEF)
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Result {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name=JpaConst.RESULT_COL_ID)
    private Integer id;

    @Column(name=JpaConst.RESULT_COL_DATE)
    private LocalDateTime date;

    @ManyToOne
    @JoinColumn(name=JpaConst.RESULT_COL_USER)
    private User user;

    @ManyToOne
    @JoinColumn(name=JpaConst.RESULT_COL_GAME)
    private Game game;

    @ManyToOne
    @JoinColumn(name=JpaConst.RESULT_COL_MODE)
    private Mode mode;

    @Column(name=JpaConst.RESULT_COL_WIN_OR_LOSE)
    private String winOrLose;

    @Column(name=JpaConst.RESULT_COL_POINT)
    private Integer point;

    @Column(name=JpaConst.RESULT_COL_MEMO)
    @Lob
    private String memo;
}
