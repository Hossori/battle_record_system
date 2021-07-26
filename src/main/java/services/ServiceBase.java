package services;

import javax.persistence.EntityManager;

import utils.DBUtil;

/*
 * DB接続にかかわる共通処理を行う
 */
public class ServiceBase {

    /*
     * EntityManagerインスタンスを取得
     */
    protected EntityManager em = DBUtil.createEntityManager();

    /*
     * EntityManagerのクローズ
     */
    public void close() {
        if(em.isOpen()) {
            em.close();
        }
    }
}
