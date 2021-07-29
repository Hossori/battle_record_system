package actions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;

import constants.AttributeConst;
import constants.ForwardConst;
import constants.JpaConst;
import constants.MessageConst;
import constants.PropertyConst;
import models.User;
import models.validators.UserValidator;
import services.UserService;
import utils.EncryptUtil;

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

    public void signupForm() throws ServletException, IOException {
        setRequestParam(AttributeConst.TOKEN, getTokenId());
        //エラーによる再表示の際に入力情報を引き継ぐ
        setRequestParam(AttributeConst.USER_EMAIL, getRequestParam(AttributeConst.USER_EMAIL));
        setRequestParam(AttributeConst.USER_NAME, getRequestParam(AttributeConst.USER_NAME));

        moveErrors();

        forward(ForwardConst.FW_SIGNUP);
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
                //redirect();
            }
        }
    }

    public void signup() throws ServletException, IOException {
        String email = getRequestParam(AttributeConst.USER_EMAIL);
        String name = getRequestParam(AttributeConst.USER_NAME);
        String plainPass = getRequestParam(AttributeConst.USER_PASS);
        String pepper = getContextParam(PropertyConst.PEPPER);

        List<String> errors = UserValidator.validate(email, name, plainPass);
        setSessionParam(AttributeConst.ERRORS, errors);

        if(0 < errors.size()) {
            //forwardで入力情報を引き継ぐ
            setRequestParam(AttributeConst.USER_EMAIL, email);
            setRequestParam(AttributeConst.USER_NAME, name);
            forward(ForwardConst.ACT_AUTH, ForwardConst.CMD_SIGNUP_FORM);

            return;
        }

        if(checkToken()) {
            String password = EncryptUtil.getPasswordEncrypt(plainPass, pepper);

            User u = new User(
                        null,
                        email,
                        name,
                        password,
                        null,
                        JpaConst.USER_ADMIN_FLAG_FALSE,
                        JpaConst.USER_DELETE_FLAG_FALSE
                    );

            userService.create(u);

            setSessionParam(AttributeConst.LOGIN_USER, u);

            //redirect();
        }
    }
}