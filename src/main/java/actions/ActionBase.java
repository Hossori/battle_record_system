package actions;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import constants.AttributeConst;
import constants.ForwardConst;
import constants.JpaConst;
import constants.PropertyConst;
import models.User;

/*
 * 各Actionの親クラス。共通処理を行う。
 */
public abstract class ActionBase {
    protected ServletContext context;
    protected HttpServletRequest request;
    protected HttpServletResponse response;

    /*
     * 初期化処理
     * サーブレットコンテキスト、リクエスト、レスポンスをクラスフィールドに設定
     */
    public void init(ServletContext servletContext,
                     HttpServletRequest servletRequest,
                     HttpServletResponse servletResponse) {
        this.context = servletContext;
        this.request = servletRequest;
        this.response = servletResponse;
    }

    /*
     * フロントコントローラから呼び出されるメソッド
     * @throws ServletException, IOException
     */
    public abstract void process() throws ServletException, IOException;

    /*
     * commandの値に該当するメソッドを実行する
     * @throws ServletException, IOException
     */
    protected void invoke() throws ServletException, IOException {

        Method commandMethod;
        try {
            //パラメータからcommandを取得
            String command = request.getParameter(ForwardConst.CMD.getValue());

            //commandに該当するメソッドを実行する
            commandMethod = this.getClass().getDeclaredMethod(command, new Class[0]);
            commandMethod.invoke(this, new Object[0]); //メソッドに渡す引数はなし
        } catch(NoSuchMethodException | SecurityException | IllegalAccessException
                | IllegalArgumentException | InvocationTargetException | NullPointerException e) {
            //commandの値が不正で実行できない場合、エラー画面を呼び出し
            System.err.println("ActionBaseでエラーをキャッチ");
            e.printStackTrace();
            forward(ForwardConst.FW_ERR_UNKNOWN);
        }
    }

    /*
     * フォーワードでjspを呼び出す
     * @param target 遷移先アドレス（拡張子を除く）
     * @throws ServletException, IOException
     */
    protected void forward(ForwardConst target) throws ServletException, IOException {

        //jspファイルの相対パスでリクエストディスパッチャーオブジェクトを作成
        String forward = String.format("/WEB-INF/views/%s.jsp", target.getValue());
        RequestDispatcher rd = request.getRequestDispatcher(forward);

        //jspファイルの呼び出し
        rd.forward(request, response);
    }

    /*
     * フォーワードでServletを呼び出す
     * @param action, command
     * @throws ServletException, IOException
     */
    protected void forward(ForwardConst action, ForwardConst command) throws ServletException, IOException {

        //リクエストディスパッチャーオブジェクトを作成
        String forward = request.getContextPath() + "/?action=" + action.getValue()
                                                  + "&command=" + command.getValue();
        RequestDispatcher rd = request.getRequestDispatcher(forward);

        //jspファイルの呼び出し
        rd.forward(request, response);
    }

    /*
     * URLを構築しリダイレクトを行う
     * @param action, command
     * @throws ServletException, IOException
     */
    protected void redirect(ForwardConst action, ForwardConst command) throws ServletException, IOException {

        //URL構築
        String redirectUrl = request.getContextPath() + "/?action=" + action.getValue();
        if(command != null) {
            redirectUrl = redirectUrl + "&command=" + command.getValue();
        }

        //URLへリダイレクト
        response.sendRedirect(redirectUrl);
    }

    /*
     * CSRF対策 token不正の場合はエラー画面を表示
     * @return true: token有効 false: token不正
     * @throws ServletException, IOException
     */
    protected boolean checkToken() throws ServletException, IOException {

        //パラメータからtokenの値を取得
        String _token = getRequestParam(AttributeConst.TOKEN);

        if(_token == null || !(_token.equals(getTokenId()))) {

            //tokenにつき未設定あるいはセッションIDと一致しない場合はエラー画面を表示
            forward(ForwardConst.FW_ERR_UNKNOWN);
            System.err.println("checkTokenがfalseを返しました。");

            return false;
        } else {
            return true;
        }
    }

    /*
     * セッションIDを取得する
     * return セッションID
     */
    protected String getTokenId() {
        return request.getSession().getId();
    }

    /*
     * リクエストパラメータのpageを取得し、返却する
     * @return page ない場合は1
     */
    protected int getPage() {
        int page;
        page = toNumber(getRequestParam(AttributeConst.PAGE));
        if(page == Integer.MIN_VALUE) {
            page = 1;
        }

        return page;
    }

    /*
     * 文字列を数値に変換する
     * @param strNumber 文字列
     * @return 数値 エラーの場合はInteger.MIN_VALUEを返す
     */
    protected int toNumber(String strNumber) {
        int number = 0;
        try {
            number = Integer.parseInt(strNumber);
        } catch(Exception e) {
            number = Integer.MIN_VALUE;
        }

        return number;
    }

    /*
     * yyyy/MM/dd HH:mmの文字列をLocalDateTime型に変換する
     * @param strDate 文字列
     * @return LocalDateインスタンス parseに失敗した場合は今日の日付を返す
     */
    protected LocalDateTime toLocalDateTime(String strDateTime) {
        LocalDateTime datetime;

        strDateTime = strDateTime.replaceAll("/", "-").replace(" ", "T") + ":00.00";

        try {
            datetime = LocalDateTime.parse(strDateTime);
        } catch(Exception e) {
            datetime = LocalDateTime.now();
        }

        return datetime;
    }

    /*
     * リクエストパラメータのフラッシュメッセージをセッションスコープに移す
     */
    protected void moveFlush() {
        String flush = getSessionParam(AttributeConst.FLUSH);
        if(flush != null) {
            setRequestParam(AttributeConst.FLUSH, flush);
            removeSessionParam(AttributeConst.FLUSH);
        }
    }

    protected void moveErrors() {
        List<String> errors = getSessionParam(AttributeConst.ERRORS);
        if(errors != null && 0 < errors.size()) {
            setRequestParam(AttributeConst.ERRORS, errors);
            removeSessionParam(AttributeConst.ERRORS);
        }
    }

    protected boolean checkAdmin() throws ServletException, IOException {
        User loginUser = getSessionParam(AttributeConst.LOGIN_USER);

        if(loginUser != null
                && loginUser.getAdminFlag() == JpaConst.USER_ADMIN_FLAG_TRUE) {
            return true;
        } else {
            forward(ForwardConst.FW_ERR_UNKNOWN);
            return false;
        }
    }

    /*
     * リクエストパラメータを取得し返却する　
     * @param key パラメータ名
     * @return パラメータの値
     */
    protected String getRequestParam(AttributeConst key) {
        return request.getParameter(key.getValue());
    }

    /*
     * リクエストパラメータを配列で取得し、リストに変換して返却する
     * @param key パラメータ名
     * @return パラメータの値のリスト
     */
    protected List<String> getRequestParamValues(AttributeConst key) {
        String[] arr = request.getParameterValues(key.getValue());
        if(arr != null) {
            return Arrays.asList(arr);
        } else {
            return null;
        }
    }

    /*
     * リクエストパラメータを設定する
     * @param key パラメータ名
     * @param value パラメータの値
     */
    protected <V> void setRequestParam(AttributeConst key, V value) {
        request.setAttribute(key.getValue(), value);
    }

    /*
     * セッションパラメータを取得し返却する
     * @param key パラメータ名
     * @return パラメータの値
     */
    @SuppressWarnings("unchecked")
    protected <R> R getSessionParam(AttributeConst key) {
        return (R) request.getSession().getAttribute(key.getValue());
    }

    /*
     * セッションパラメータを設定する
     * @param key パラメータ名
     * @param value パラメータの値
     */
    protected <V> void setSessionParam(AttributeConst key, V value) {
        request.getSession().setAttribute(key.getValue(), value);
    }

    /*
     * 指定したセッションパラメータを除去する
     * @param key パラメータ名
     */
    protected void removeSessionParam(AttributeConst key) {
        request.getSession().removeAttribute(key.getValue());
    }

    /*
     * アプリケーションスコープのパラメータを取得し返却する
     * @param key パラメータ名
     * @return パラメータの値
     */
    @SuppressWarnings("unchecked")
    protected <R> R getContextParam(PropertyConst key) {
        return (R) context.getAttribute(key.getValue());
    }
}
