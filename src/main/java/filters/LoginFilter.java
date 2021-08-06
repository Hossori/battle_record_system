package filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import constants.AttributeConst;
import constants.ForwardConst;
import models.User;

/**
 * Servlet Filter implementation class LoginFilter
 */
@WebFilter("/*")
public class LoginFilter implements Filter {

    /**
     * Default constructor.
     */
    public LoginFilter() {
    }

    /**
     * @see Filter#destroy()
     */
    public void destroy() {
    }

    /**
     * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
     */
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        String contextPath = ((HttpServletRequest) request).getContextPath();
        String servletPath = ((HttpServletRequest) request).getServletPath();

        if(servletPath.matches("/css.*")) {
            //cssフォルダは認証対象外
            chain.doFilter(request, response);
        } else {
            HttpSession session = ((HttpServletRequest) request).getSession();

            //クエリパラメータからactionとcommandを取得
            String action = request.getParameter(ForwardConst.ACT.getValue());
            String command = request.getParameter(ForwardConst.CMD.getValue());

            //セッションからログインしているユーザーの情報を取得
            User u = (User) session.getAttribute(AttributeConst.LOGIN_USER.getValue());
            if(u == null) { //未ログイン
                if(
                    !(ForwardConst.ACT_AUTH.getValue().equals(action) &&
                       (ForwardConst.CMD_LOGIN_FORM.getValue().equals(command) ||
                        ForwardConst.CMD_LOGIN.getValue().equals(command))
                     ) &&
                    !(ForwardConst.ACT_USER.getValue().equals(action) &&
                       (ForwardConst.CMD_ENTRY.getValue().equals(command) ||
                        ForwardConst.CMD_CREATE.getValue().equals(command) ||
                        ForwardConst.CMD_SHOW.getValue().equals(command) ||
                        ForwardConst.CMD_MYPAGE.getValue().equals(command))
                    ) &&
                    !(ForwardConst.ACT_RECORD.getValue().equals(action) &&
                       (ForwardConst.CMD_INDEX.getValue().equals(command) ||
                        ForwardConst.CMD_SHOW.getValue().equals(command))
                    ) &&
                    !(ForwardConst.ACT_TOP.getValue().equals(action)) &&
                    !(ForwardConst.ACT_AJAX.getValue().equals(action))
                  ) {

                    //ログイン・新規登録・トップページ・戦績のindex,show・ajax以外はログインページにリダイレクト
                    ((HttpServletResponse) response).sendRedirect(
                            contextPath + "?action=" + ForwardConst.ACT_AUTH.getValue()
                                        + "&command=" + ForwardConst.CMD_LOGIN_FORM.getValue());

                    return;
                }
            } else { //ログイン済み
                if(ForwardConst.ACT_AUTH.getValue().equals(action)) { //認証系Action
                    if(ForwardConst.CMD_LOGIN_FORM.getValue().equals(command)) {
                        //ログインページの表示 => トップへ
                        ((HttpServletResponse) response).sendRedirect(
                                contextPath + "?action=" + ForwardConst.ACT_TOP.getValue()
                                            + "&command=" + ForwardConst.CMD_INDEX.getValue());
                        return;

                    } else if(ForwardConst.CMD_LOGOUT.getValue().equals(command)) {
                        //ログアウトは許可

                    } else {
                        //上記以外はエラー画面へ
                        String forward = String.format("/WEB-INF/views/%s.jsp", ForwardConst.FW_ERR_UNKNOWN.getValue());
                        RequestDispatcher rd = request.getRequestDispatcher(forward);
                        rd.forward(request, response);

                        return;
                    }
                }
            }
        }

        //次のフィルタ又はサーブレットを呼び出し
        chain.doFilter(request, response);
    }

    /**
     * @see Filter#init(FilterConfig)
     */
    public void init(FilterConfig fConfig) throws ServletException {
    }

}
