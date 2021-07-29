package services;

import constants.JpaConst;
import models.Game;
import models.Mode;

public class ModeService extends ServiceBase {

    public void create(Game g, String name) {
        Mode m = new Mode(
                null,
                g,
                name,
                JpaConst.MODE_DELETE_FLAG_FALSE
                );
        create(m);
    }

    private void create(Mode m) {
        em.getTransaction().begin();
        em.persist(m);
        em.getTransaction().commit();
    }
}
