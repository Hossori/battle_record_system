package constants;

/*
 * 画面の項目値を定義
 */
public enum AttributeConst {

    //フラッシュメッセージ
    FLUSH("flush"),

    //一覧画面共通
    MAX_ROW("maxRow"),
    PAGE("page"),

    //入力フォーム共通
    TOKEN("_token"),
    ERR("errors"),

    //ユーザー
    USER("user"),
    USER_ID("id"),
    USER_EMAIL("email"),
    USER_NAME("name"),
    USER_PASS("password"),
    USER_INTRODUCTION("introduction"),
    USER_ADMIN_FLAG("admin_flag"),
    USER_DELETE_FLAG("delete_flag");

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
