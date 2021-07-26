package constants;

/*
 * DB関連の項目値を定義
 */
public interface JpaConst {

    //persistence-unit名
    String PERSISTENCE_UNIT_NAME = "battle_record_system";

    //データ取得件数の最大値
    int ROW_PER_PAGE = 15; //1ページに表示するレコードの数

    //テーブル
    String TABLE_USER = "users";

    //usersテーブルのカラム
    String USER_COL_ID = "id";
    String USER_COL_EMAIL = "email";
    String USER_COL_NAME = "name";
    String USER_COL_PASS = "password";
    String USER_COL_INTRODUCTION = "introduction";
    String USER_COL_ADMIN_FLAG = "admin_flag";
    String USER_COL_DELETE_FLAG = "delete_flag";

    //usersの管理者フラグ
    int USER_ADMIN_FLAG_FALSE = 0;
    int USER_ADMIN_FLAG_TRUE = 1;
    //usersの論理削除フラグ
    int USER_DELETE_FLAG_FALSE = 0;
    int USER_DELETE_FLAG_TRUE = 1;

    //エンティティ
    String ENTITY_USER = "User";

    //JPQLパラメータ
    String JPQL_PARAM_EMAIL = "email";
    String JPQL_PARAM_PASS = "password";

    //JPQL
    //メアドとパスワードで未削除のユーザーを検索する
    String Q_USER_GET_BY_EMAIL_AND_PASS = ENTITY_USER + ".getByEmailAndPass";
    String Q_USER_GET_BY_EMAIL_AND_PASS_DEF = "SELECT u FROM User AS u WHERE u.email = :" + JPQL_PARAM_EMAIL
                                                                      + " AND u.password = :" + JPQL_PARAM_PASS
                                                                      + " AND u.deleteFlag = " + USER_DELETE_FLAG_FALSE;
    //メアドで未削除のユーザーを検索し、その件数を取得する（重複回避用）
    String Q_USER_COUNT_BY_EMAIL = ENTITY_USER + ".countByEmail";
    String Q_USER_COUNT_BY_EMAIL_DEF = "SELECT COUNT(u) FROM User AS u WHERE u.email = :" + JPQL_PARAM_EMAIL
                                                                       + " AND u.deleteFlag = " + USER_DELETE_FLAG_FALSE;
}
