package models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import constants.JpaConst;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name=JpaConst.TABLE_MODE)
@NamedQueries({
    @NamedQuery(name="Mode.getAll",
                query="SELECT m FROM Mode AS m")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Mode {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name=JpaConst.MODE_COL_ID)
    private Integer id;

    @ManyToOne
    @JoinColumn(name=JpaConst.MODE_COL_GAME)
    private Game game;

    @Column(name=JpaConst.MODE_COL_NAME)
    private String name;

    @Column(name=JpaConst.MODE_COL_DELETE_FLAG)
    private Integer deleteFlag;
}
