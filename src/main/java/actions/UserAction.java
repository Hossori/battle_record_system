package actions;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;

import constants.AttributeConst;
import constants.ForwardConst;
import constants.JpaConst;
import constants.PropertyConst;
import models.User;
import models.validators.UserValidator;
import services.UserService;
import utils.EncryptUtil;

public class UserAction extends ActionBase {
    private UserService service;

    @Override
    public void process() throws ServletException, IOException {
        service = new UserService();

        invoke();

        service.close();
    }

    //新規登録フォームの表示
    public void entry() throws ServletException, IOException {
        setRequestParam(AttributeConst.TOKEN, getTokenId());
        //エラーによる再表示の際に入力情報を引き継ぐ
        setRequestParam(AttributeConst.USER_EMAIL, getRequestParam(AttributeConst.USER_EMAIL));
        setRequestParam(AttributeConst.USER_NAME, getRequestParam(AttributeConst.USER_NAME));

        moveErrors();

        forward(ForwardConst.FW_USER_ENTRY);
    }

    //新規登録
    public void create() throws ServletException, IOException {
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
            forward(ForwardConst.ACT_USER, ForwardConst.CMD_ENTRY);

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

            service.create(u);

            setSessionParam(AttributeConst.LOGIN_USER, u);

            //redirect();
        }
    }



}
