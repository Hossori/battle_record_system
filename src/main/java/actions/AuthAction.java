package actions;

import java.io.IOException;

import javax.servlet.ServletException;

import constants.AttributeConst;
import constants.ForwardConst;
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
        moveFlush();
        forward(ForwardConst.FW_LOGIN);
    }

    public void signupForm() throws ServletException, IOException {
        setRequestParam(AttributeConst.TOKEN, getTokenId());
        moveFlush();
        forward(ForwardConst.FW_SIGNUP);
    }

}
