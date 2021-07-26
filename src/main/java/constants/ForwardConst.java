package constants;

/*
 * 画面遷移に関する値を定義する
 * →リクエストパラメータの変数名、変数値、jspファイルの名前等
 */
public enum ForwardConst {

    //action
    ACT("action"),

    //command
    CMD("command"),

    //jsp
    FW_ERR_UNKNOWN("error/unknown"),
    FW_TOP_INDEX("top/index");

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
