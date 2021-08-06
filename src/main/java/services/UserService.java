package services;

import javax.persistence.NoResultException;

import constants.AttributeConst;
import constants.JpaConst;
import models.User;
import utils.EncryptUtil;

/*
 * ユーザーテーブルの操作に関わる処理を行う
 */
public class UserService extends ServiceBase {

    /*
     * メアド、パスワードから未削除のユーザーを取得
     * @param email, plainPass, pepper
     * @return ユーザーインスタンス
     */
    public User getByEmailAndPass(String email, String plainPass, String pepper) {

        User u = null;
        try {
            //パスワードのハッシュ化
            String pass = EncryptUtil.getPasswordEncrypt(plainPass, pepper);

            //ユーザー名とハッシュ化したパスワードで未削除のユーザーを検索
            u = em.createNamedQuery(JpaConst.Q_USER_GET_BY_EMAIL_AND_PASS, User.class)
                                    .setParameter(JpaConst.JPQL_PARAM_EMAIL, email)
                                    .setParameter(JpaConst.JPQL_PARAM_PASS, pass)
                                    .getSingleResult();

        } catch(NoResultException ex) {
        }

        return u;
    }

    /*
     * 主キーから未削除のユーザーを取得
     * @param id
     * @return ユーザーインスタンス
     */
    public User getById(int id) {
        User u = em.find(User.class, id);
        return u;
    }

    /*
     * 同じメアドで登録している未削除のユーザーがいるかどうかをboolean値で返却する
     * @param email
     * @return 存否
     */
    public boolean isExistEmail(String email) {
        long count = em.createNamedQuery(JpaConst.Q_USER_COUNT_BY_EMAIL, Long.class)
                                        .setParameter(JpaConst.JPQL_PARAM_EMAIL, email)
                                        .getSingleResult();
        if(count == 0) {
            return false;
        } else {
            return true;
        }
    }

    /*
     * ユーザーの新規登録
     * @param u ユーザーエンティティ
     */
    public void create(User u) {
        em.getTransaction().begin();
        em.persist(u);
        em.getTransaction().commit();
    }

    /*
     * ユーザー情報の更新
     */
    public void update(User u, User update_u) {
        em.getTransaction().begin();

        u.setName(update_u.getName());
        String email = update_u.getEmail();
        if(email != null) {u.setEmail(email);}
        String pass = update_u.getPassword();
        if(pass != null) {u.setPassword(pass);}
        u.setIntroduction(update_u.getIntroduction());

        em.getTransaction().commit();
    }

    /*
     * ユーザーの論理削除
     */
    public void destroy(User u) {
        em.getTransaction().begin();
        u.setDeleteFlag(AttributeConst.DELETE_TRUE.getIntegerValue());
        em.getTransaction().commit();
    }
}
