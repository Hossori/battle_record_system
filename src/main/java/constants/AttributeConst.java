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

    //入力フォーム共通
    TOKEN("_token"),
    ERR("errors"),

    //ログイン中のユーザー
    LOGIN_USER("login_user"),

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
    GAME_NAME("game_name"),

    //モード
    MODE("mode"),
    MODE_ID("mode_id"),
    MODE_NAME("mode_name");

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
