package services;

import constants.JpaConst;
import models.Mode;

public class ModeService extends ServiceBase {

    public Mode getById(int id) {
        return em.find(Mode.class, id);
    }

    public void create(Mode m) {
        em.getTransaction().begin();
        em.persist(m);
        em.getTransaction().commit();
    }

    public void update(Mode m, String modeName) {
        em.getTransaction().begin();
        m.setName(modeName);
        em.getTransaction().commit();
    }

    public void destroy(Mode m) {
        em.getTransaction().begin();
        m.setDeleteFlag(JpaConst.MODE_DELETE_FLAG_TRUE);
        em.getTransaction().commit();
    }
}
