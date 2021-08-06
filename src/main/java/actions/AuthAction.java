package actions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;

import constants.AttributeConst;
import constants.ForwardConst;
import constants.MessageConst;
import constants.PropertyConst;
import models.User;
import services.UserService;

public class AuthAction extends ActionBase {
    private UserService userService;

    @Override
    public void process() throws ServletException, IOException {
        userService = new UserService();

        invoke();

        userService.close();
    }

    public void loginForm() throws ServletException, IOException {
        setRequestParam(AttributeConst.TOKEN, getTokenId());
        //エラーによる再表示の際に入力情報を引き継ぐ
        setRequestParam(AttributeConst.USER_EMAIL, getRequestParam(AttributeConst.USER_EMAIL));
        moveFlush();
        moveErrors();

        forward(ForwardConst.FW_LOGIN);
    }

    public void login() throws ServletException, IOException {
        String email = getRequestParam(AttributeConst.USER_EMAIL);
        String plainPass = getRequestParam(AttributeConst.USER_PASS);
        String pepper = getContextParam(PropertyConst.PEPPER);

        User u = userService.getByEmailAndPass(email, plainPass, pepper);

        if(u == null) {
            List<String> errors = new ArrayList<>();
            errors.add(MessageConst.E_LOGINED.getMessage());
            setSessionParam(AttributeConst.ERRORS, errors);

            //forwardで入力情報を引き継ぐ
            setRequestParam(AttributeConst.USER_EMAIL, email);
            forward(ForwardConst.ACT_AUTH, ForwardConst.CMD_LOGIN_FORM);

            return;
        } else {
            if(checkToken()) {
                setSessionParam(AttributeConst.LOGIN_USER, u);
                forward(ForwardConst.ACT_USER, ForwardConst.CMD_MYPAGE);
            }
        }
    }

    public void logout() throws ServletException, IOException {
        removeSessionParam(AttributeConst.LOGIN_USER);

        redirect(ForwardConst.ACT_TOP, ForwardConst.CMD_INDEX);
    }
}