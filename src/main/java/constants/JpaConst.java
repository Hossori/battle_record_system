package constants;

/*
 * DB関連の項目値を定義
 */
public interface JpaConst {

    //persistence-unit名
    String PERSISTENCE_UNIT_NAME = "battle_record_system";

    //データ取得件数の最大値
    int ROW_PER_PAGE = 10; //1ページに表示するレコードの数

    //テーブル
    String TABLE_USER = "users";
    String TABLE_GAME = "games";
    String TABLE_MODE = "modes";
    String TABLE_RECORD = "records";

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

    //gamesテーブルのカラム
    String GAME_COL_ID = "id";
    String GAME_COL_NAME = "name";
    String GAME_COL_DELETE_FLAG = "delete_flag";

    //gamesの論理削除フラグ
    int GAME_DELETE_FLAG_FALSE = 0;
    int GAME_DELETE_FLAG_TRUE = 1;

    //modesテーブルのカラム
    String MODE_COL_ID = "id";
    String MODE_COL_GAME = "game_id";
    String MODE_COL_NAME = "name";
    String MODE_COL_DELETE_FLAG = "delete_flag";

    //modesの論理削除フラグ
    int MODE_DELETE_FLAG_FALSE = 0;
    int MODE_DELETE_FLAG_TRUE = 1;

    //recordsテーブルのカラム
    String RECORD_COL_ID = "id";
    String RECORD_COL_DATE = "date";
    String RECORD_COL_USER = "user_id";
    String RECORD_COL_GAME = "game_id";
    String RECORD_COL_MODE = "mode_id";
    String RECORD_COL_WIN_OR_LOSE = "win_or_lose";
    String RECORD_COL_POINT = "point";
    String RECORD_COL_MEMO = "memo";

    //エンティティ
    String ENTITY_USER = "User";
    String ENTITY_GAME = "Game";
    String ENTITY_MODE = "Mode";
    String ENTITY_RECORD = "Record";

    //JPQLパラメータ
    String JPQL_PARAM_EMAIL = "email";
    String JPQL_PARAM_PASS = "password";
    String JPQL_PARAM_GAME_NAME = "game_name";
    String JPQL_PARAM_MODE_NAME = "mode_name";
    String JPQL_PARAM_USER = "user";
    String JPQL_PARAM_GAME = "game";
    String JPQL_PARAM_MODE = "mode";

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
    //全ての未削除のゲームをidの降順で取得
    String Q_GAME_GET_ALL = ENTITY_GAME + ".getAll";
    String Q_GAME_GET_ALL_DEF = "SELECT g FROM Game AS g WHERE g.deleteFlag = " + GAME_DELETE_FLAG_FALSE + " ORDER BY g.id DESC";

    //未削除の全ゲーム数を取得
    String Q_GAME_COUNT_ALL = ENTITY_GAME + ".countAll";
    String Q_GAME_COUNT_ALL_DEF = "SELECT COUNT(g) FROM Game AS g WHERE g.deleteFlag = " + GAME_DELETE_FLAG_FALSE;

    //名前で未削除のゲームを検索し、その件数を取得する（重複回避用）
    String Q_GAME_COUNT_BY_NAME = ENTITY_GAME + ".countByName";
    String Q_GAME_COUNT_BY_NAME_DEF = "SELECT COUNT(g) FROM Game AS g WHERE g.name = :" + JPQL_PARAM_GAME_NAME
                                                                      + " AND g.deleteFlag = " + GAME_DELETE_FLAG_FALSE;

    //全ての戦績を日時の新しい順で取得
    String Q_RECORD_GET_ALL = ENTITY_RECORD + ".getAll";
    String Q_RECORD_GET_ALL_DEF = "SELECT r FROM Record AS r ORDER BY r.date DESC";

    //ゲームを指定し、戦績を日時の新しい順で取得
    String Q_RECORD_GET_BY_GAME = ENTITY_RECORD + ".getByGame";
    String Q_RECORD_GET_BY_GAME_DEF =
        "SELECT r FROM Record AS r WHERE r.game = :" + JPQL_PARAM_GAME
                               + " ORDER BY r.date DESC";

    //ゲーム・モードを指定し、戦績を日時の新しい順で取得
    String Q_RECORD_GET_BY_GAME_AND_MODE = ENTITY_RECORD + ".getByGameAndMode";
    String Q_RECORD_GET_BY_GAME_AND_MODE_DEF =
        "SELECT r FROM Record AS r WHERE r.game = :" + JPQL_PARAM_GAME
                               + " AND r.mode = :" + JPQL_PARAM_MODE
                               + " ORDER BY r.date DESC";

    //ユーザーを指定し、戦績を日時の新しい順で取得
    String Q_RECORD_GET_BY_USER = ENTITY_RECORD + ".getByUser";
    String Q_RECORD_GET_BY_USER_DEF =
        "SELECT r FROM Record AS r WHERE r.user = :" + JPQL_PARAM_USER
                               + " ORDER BY r.date DESC";

    //ユーザー・ゲームを指定し、戦績を日時の新しい順で取得
    String Q_RECORD_GET_BY_USER_AND_GAME = ENTITY_RECORD + ".getByUserAndGame";
    String Q_RECORD_GET_BY_USER_AND_GAME_DEF =
        "SELECT r FROM Record AS r WHERE r.user = :" + JPQL_PARAM_USER
                               + " ORDER BY r.date DESC";

    //ユーザー・ゲーム・モードを指定し、戦績を日時の新しい順で取得
    String Q_RECORD_GET_BY_USER_AND_GAME_AND_MODE = ENTITY_RECORD + ".getByUserAndGameAndMode";
    String Q_RECORD_GET_BY_USER_AND_GAME_AND_MODE_DEF =
        "SELECT r FROM Record AS r WHERE r.user = :" + JPQL_PARAM_USER
                               + " AND r.game = :" + JPQL_PARAM_GAME
                               + " AND r.mode = :" + JPQL_PARAM_MODE
                               + " ORDER BY r.date DESC";
}