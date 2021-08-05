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
    E_NO_MOD_GAMENAME("変更するゲーム名を入力してください。"),
    E_EXIST_GAMENAME("登録済みのゲームです。"),
    S_GAME_UPDATE("ゲームを更新しました。"),
    S_GAME_DESTROY("ゲームを削除しました。"),
    S_MODE_CREATE("モードを登録しました。"),
    E_NO_MODENAME("モード名を入力してください。"),
    S_MODE_DESTROY("モードを削除しました。"),
    S_RECORD_CREATE("戦績を登録しました。"),
    S_RECORD_UPDATE("戦績を編集しました。"),
    E_NO_EXIST_USER("存在しないユーザーです。");

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
