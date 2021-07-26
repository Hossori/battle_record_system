package constants;

/*
 * メッセージの定義
 */
public enum MessageConst {

    //認証
    S_LOGINED("ログインしました。"),
    E_LOGINED("ログインに失敗しました。"),
    S_LOGOUT("ログアウトしました。");

    /*
     * 文字列
     */
    private final String text;

    /*
     * コンストラクタ
     */
    private MessageConst(final String text) {
        this.text = text;
    }

    /*
     * 値の取得
     */
    public String getMessage() {
        return this.text;
    }
}
