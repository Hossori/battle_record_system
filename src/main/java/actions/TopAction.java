package actions;

import java.io.IOException;

import javax.servlet.ServletException;

import constants.AttributeConst;
import constants.ForwardConst;
import models.User;
import services.UserService;

public class TopAction extends ActionBase {
    private UserService userService;

    @Override
    public void process() throws ServletException, IOException {

        userService = new UserService();

        invoke();

        userService.close();
    }

    public void index() throws ServletException, IOException {
        User u = userService.getById(1);
        setRequestParam(AttributeConst.USER, u);
        forward(ForwardConst.FW_TOP_INDEX);
    }

}
