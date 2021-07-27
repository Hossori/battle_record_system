package actions;

import java.io.IOException;
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
        moveFlush();
        forward(ForwardConst.FW_LOGIN);
    }

    public void signupForm() throws ServletException, IOException {
        setRequestParam(AttributeConst.TOKEN, getTokenId());
        moveFlush();
        moveErrors();
        forward(ForwardConst.FW_SIGNUP);
    }

    public void login() throws ServletException, IOException {
        String email = getRequestParam(AttributeConst.USER_EMAIL);
        String plainPass = getRequestParam(AttributeConst.USER_PASS);
        String pepper = getContextParam(PropertyConst.PEPPER);

        User u = userService.getByEmailAndPass(email, plainPass, pepper);

        if(u == null) {
            setSessionParam(AttributeConst.FLUSH, MessageConst.E_LOGINED.getMessage());
            redirect(ForwardConst.ACT_AUTH, ForwardConst.CMD_LOGIN_FORM);
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

        if(email == null || email.equals("")
           || name == null || name.equals("")
           || plainPass == null || plainPass.equals("")) {

            List<String> errors = UserValidator.validate(email, name, plainPass);
            setSessionParam(AttributeConst.ERRORS, errors);

            redirect(ForwardConst.ACT_AUTH, ForwardConst.CMD_SIGNUP_FORM);
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