package services;

import java.util.List;

import constants.JpaConst;
import models.Game;

public class GameService extends ServiceBase {

    public List<Game> getAll() {
        return em.createNamedQuery(JpaConst.Q_GAME_GET_ALL, Game.class)
                                  .getResultList();
    }

    public Game getById(int id) {
        return em.find(Game.class, id);
    }

    public List<Game> getPerPage(int page) {
        List<Game> games = em.createNamedQuery(JpaConst.Q_GAME_GET_ALL, Game.class)
                                              .setFirstResult(JpaConst.ROW_PER_PAGE * (page-1))
                                              .setMaxResults(JpaConst.ROW_PER_PAGE)
                                              .getResultList();

        return games;
    }

    public long countAll() {
        return em.createNamedQuery(JpaConst.Q_GAME_COUNT_ALL, Long.class).getSingleResult();
    }

    public void create(Game g) {
        em.getTransaction().begin();
        em.persist(g);
        em.getTransaction().commit();
    }

    public boolean isExistName(String name) {
        long count = em.createNamedQuery(JpaConst.Q_GAME_COUNT_BY_NAME, Long.class)
                                        .setParameter(JpaConst.JPQL_PARAM_GAME_NAME, name)
                                        .getSingleResult();

        if(count == 0) {
            return false;
        } else {
            return true;
        }
    }

    public void update(Game g, String name) {
        em.getTransaction().begin();
        g.setName(name);
        em.getTransaction().commit();
    }

    public void destroy(Game g) {
        em.getTransaction().begin();
        g.setDeleteFlag(JpaConst.GAME_DELETE_FLAG_TRUE);
        em.getTransaction().commit();
    }
}
