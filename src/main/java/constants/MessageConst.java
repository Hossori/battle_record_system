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
    E_EXIST_EMAIL("既に登録されているメールアドレスです。"),
    E_NO_PASS("パスワードを入力してください。"),
    S_GAME_REGISTER("ゲームを登録しました。"),
    E_NO_GAMENAME("ゲーム名を入力してください。"),
    E_EXIST_GAMENAME("登録済みのゲームです。");

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
