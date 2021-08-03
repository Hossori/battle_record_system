package services;

import java.util.List;

import constants.JpaConst;
import models.Game;
import models.Mode;
import models.Record;
import models.User;

public class RecordService extends ServiceBase {

    public Record getById(int id) {
        return em.find(Record.class, id);
    }

    /*
     * 指定したページに表示する戦績を返却する
     */
    public List<Record> getPerPage(int page) {
        return em.createNamedQuery(JpaConst.Q_RECORD_GET_ALL, Record.class)
                                  .setFirstResult(JpaConst.ROW_PER_PAGE * (page-1))
                                  .setMaxResults(JpaConst.ROW_PER_PAGE)
                                  .getResultList();
    }

    /*
     * ゲームから指定したページに表示する戦績を返却する
     */
    public List<Record> getByGamePerPage(Game g, int page) {
        return em.createNamedQuery(JpaConst.Q_RECORD_GET_BY_GAME, Record.class)
                                  .setParameter(JpaConst.JPQL_PARAM_GAME, g)
                                  .setFirstResult(JpaConst.ROW_PER_PAGE * (page-1))
                                  .setMaxResults(JpaConst.ROW_PER_PAGE)
                                  .getResultList();
    }

    /*
     * ゲーム・モードから指定したページに表示する戦績を返却する
     */
    public List<Record> getByGameAndModePerPage(Game g, Mode m, int page) {
        return em.createNamedQuery(JpaConst.Q_RECORD_GET_BY_GAME_AND_MODE, Record.class)
                                  .setParameter(JpaConst.JPQL_PARAM_GAME, g)
                                  .setParameter(JpaConst.JPQL_PARAM_MODE, m)
                                  .setFirstResult(JpaConst.ROW_PER_PAGE * (page-1))
                                  .setMaxResults(JpaConst.ROW_PER_PAGE)
                                  .getResultList();
    }

    /*
     * ユーザーから指定したページに表示する戦績を返却する
     */
    public List<Record> getByUserPerPage(User u, int page) {
        return em.createNamedQuery(JpaConst.Q_RECORD_GET_BY_USER, Record.class)
                                  .setParameter(JpaConst.JPQL_PARAM_USER, u)
                                  .setFirstResult(JpaConst.ROW_PER_PAGE * (page-1))
                                  .setMaxResults(JpaConst.ROW_PER_PAGE)
                                  .getResultList();
    }

    /*
     * ユーザー・ゲームから指定したページに表示する戦績を返却する
     */
    public List<Record> getByUserAndGamePerPage(User u, Game g, int page) {
        return em.createNamedQuery(JpaConst.Q_RECORD_GET_BY_USER_AND_GAME, Record.class)
                                  .setParameter(JpaConst.JPQL_PARAM_USER, u)
                                  .setParameter(JpaConst.JPQL_PARAM_GAME, g)
                                  .setFirstResult(JpaConst.ROW_PER_PAGE * (page-1))
                                  .setMaxResults(JpaConst.ROW_PER_PAGE)
                                  .getResultList();
    }

    /*
     * ユーザー・ゲーム・モードから指定したページに表示する戦績を返却する
     */
    public List<Record> getByUserAndGameAndModePerPage(User u, Game g, Mode m, int page) {
        return em.createNamedQuery(JpaConst.Q_RECORD_GET_BY_USER_AND_GAME_AND_MODE, Record.class)
                                  .setParameter(JpaConst.JPQL_PARAM_USER, u)
                                  .setParameter(JpaConst.JPQL_PARAM_GAME, g)
                                  .setParameter(JpaConst.JPQL_PARAM_MODE, m)
                                  .setFirstResult(JpaConst.ROW_PER_PAGE * (page-1))
                                  .setMaxResults(JpaConst.ROW_PER_PAGE)
                                  .getResultList();
    }
}
