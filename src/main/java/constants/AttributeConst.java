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
    ERR("errors");

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
