package services;

import javax.persistence.NoResultException;

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
        long count = em.createNamedQuery(JpaConst.Q_USER_COUNT_BY_EMAIL, long.class)
                                        .setParameter(JpaConst.JPQL_PARAM_EMAIL, email)
                                        .getSingleResult();
        if(count == 0) {
            return false;
        } else {
            return true;
        }
    }

    /*
     * ログイン認証 メアド、パスワードからデータが取得できるかどうかで判断し返却する
     * @param email, plainPass, pepper
     * @return 成功:true 失敗:false
     */
    public boolean validateLogin(String email, String plainPass, String pepper) {

        if(email != null && !email.equals("")
           && plainPass != null && !plainPass.equals("")) {

            User u = getByEmailAndPass(email, plainPass, pepper);

            if(u != null && u.getId() != null) { //データが取得できた場合
                return true;
            }
        }

        return false;
    }
}
