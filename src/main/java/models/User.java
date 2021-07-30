package models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import constants.JpaConst;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/*
 * ユーザー
 */
@Table(name=JpaConst.TABLE_USER)
@NamedQueries({
    @NamedQuery(
            name = JpaConst.Q_USER_GET_BY_EMAIL_AND_PASS,
            query = JpaConst.Q_USER_GET_BY_EMAIL_AND_PASS_DEF),
    @NamedQuery(
            name = JpaConst.Q_USER_COUNT_BY_EMAIL,
            query = JpaConst.Q_USER_COUNT_BY_EMAIL_DEF)
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class User {

    @Id
    @Column(name=JpaConst.USER_COL_ID)
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id; // id

    @Column(name=JpaConst.USER_COL_EMAIL, nullable=false)
    private String email; //メールアドレス

    @Column(name=JpaConst.USER_COL_NAME, nullable=false)
    private String name; // ユーザーネーム

    @Column(name=JpaConst.USER_COL_PASS, nullable=false, length=64)
    private String password; // パスワード

    @Column(name=JpaConst.USER_COL_INTRODUCTION, nullable=true)
    @Lob
    private String introduction; // 自己紹介

    @Column(name=JpaConst.USER_COL_ADMIN_FLAG, nullable=false)
    private Integer adminFlag; // 一般:0, 管理者:1

    @Column(name=JpaConst.USER_COL_DELETE_FLAG, nullable=false)
    private Integer deleteFlag; // 現役:0, 削除済み:1
}
