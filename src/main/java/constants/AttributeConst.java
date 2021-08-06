package constants;

/*
 * 画面の項目値を定義
 */
public enum AttributeConst {

    //フラッシュメッセージ
    FLUSH("flush"),
    //エラーメッセージ
    ERRORS("errors"),

    //一覧画面共通
    MAX_ROW("maxRow"),
    PAGE("page"),
    PAGE_BEGIN("page_begin"),
    PAGE_END("page_end"),

    //入力フォーム共通
    TOKEN("_token"),
    ERR("errors"),

    //削除フラグ
    DELETE_FALSE(0),
    DELETE_TRUE(1),

    //管理者フラグ
    ADMIN_FALSE(0),
    ADMIN_TRUE(1),

    //ログイン中のユーザー
    LOGIN_USER("login_user"),

    //マイページ
    TOTAL("total"),
    WIN_RATE("win_rate"),
    WIN("win"),
    LOSE("lose"),
    DRAW("draw"),
    POINT("point"),
    COUNT("count"),

    //ユーザー
    USER("user"),
    USER_ID("user_id"),
    USER_EMAIL("user_email"),
    USER_NAME("user_name"),
    USER_PASS("user_password"),
    USER_INTRODUCTION("user_introduction"),
    USER_ADMIN_FLAG("user_admin_flag"),
    USER_DELETE_FLAG("user_delete_flag"),

    //ゲーム
    GAME("game"),
    GAMES("games"),
    GAME_COUNT("game_count"),
    GAME_ID("game_id"),
    GAME_ID_SELECTED("game_id_selected"),
    GAME_NAME("game_name"),

    //モード
    MODE("mode"),
    MODES("modes"),
    MODE_ID("mode_id"),
    MODE_ID_SELECTED("mode_id_selected"),
    MODE_NAME("mode_name"),

    //レコード
    RECORD("record"),
    RECORDS("records"),
    RECORD_ID("record_id"),
    RECORD_DATETIME("record_datetime"),
    RECORD_WIN_OR_LOSE("record_win_or_lose"),
    RECORD_WIN("record_win"),
    RECORD_LOSE("record_lose"),
    RECORD_DRAW("record_draw"),
    RECORD_POINT("record_point"),
    RECORD_MEMO("record_memo"),
    RECORD_COUNT("record_count");

    //文字列
    private final String text;
    private AttributeConst(final String text) {
        this.text = text;
        this.i = null;
    }

    public String getValue() {
        return this.text;
    }

    //数値
    private final Integer i;
    private AttributeConst(final Integer i) {
        this.text = null;
        this.i = i;
    }

    public Integer getIntegerValue() {
        return this.i;
    }
}
