package constants;

/*
 * 画面遷移に関する値を定義する
 * →リクエストパラメータの変数名、変数値、jspファイルの名前等
 */
public enum ForwardConst {

    //action
    ACT("action"),
    ACT_AUTH("Auth"),
    ACT_TOP("Top"),
    ACT_RECORD("Record"),
    ACT_USER("User"),
    ACT_GAME("Game"),
    ACT_MODE("Mode"),
    ACT_AJAX("Ajax"),

    //command
    CMD("command"),
    CMD_LOGIN_FORM("loginForm"),
    CMD_LOGIN("login"),
    CMD_LOGOUT("logout"),
    CMD_SIGNUP_FORM("signupForm"),
    CMD_SIGNUP("signup"),
    CMD_MYPAGE("mypage"),
    CMD_INDEX("index"),
    CMD_CREATE("create"),
    CMD_SHOW("show"),
    CMD_EDIT("edit"),
    CMD_UPDATE("update"),
    CMD_DESTROY("destroy"),

    //jsp
    FW_ERR_UNKNOWN("error/unknown"),
    FW_TOP_INDEX("top/index"),
    FW_LOGIN("auth/login"),
    FW_SIGNUP("auth/signup"),
    FW_GAME_INDEX("games/index"),
    FW_GAME_EDIT("games/edit"),
    FW_RECORD_SHOW("records/show"),
    FW_RECORD_EDIT("records/edit");

    /*
     * 文字列
     */
    private final String text;

    /*
     * コンストラクタ
     */
    private ForwardConst(final String text) {
        this.text = text;
    }

    /*
     * 値の取得
     */
    public String getValue() {
        return this.text;
    }
}
