package constants;

/*
 * メッセージの定義
 */
public enum MessageConst {

    //認証
    E_LOGINED("ログインに失敗しました。"),
    S_LOGOUT("ログアウトしました。"),
    E_NO_USERNAME("ユーザー名を入力してください。"),
    E_NO_EMAIL("メールアドレスを入力してください。"),
    E_NO_PASS("パスワードを入力してください。");

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
