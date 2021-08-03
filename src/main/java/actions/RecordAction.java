package actions;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;

import constants.AttributeConst;
import constants.ForwardConst;
import models.Game;
import models.Record;
import models.User;
import services.GameService;
import services.RecordService;

public class RecordAction extends ActionBase {
    private RecordService service;
    private GameService gameService;

    @Override
    public void process() throws ServletException, IOException {
        service = new RecordService();
        gameService = new GameService();

        invoke();

        service.close();
        gameService.close();
    }

    public void show() throws ServletException, IOException {

        Record r = service.getById(toNumber(getRequestParam(AttributeConst.RECORD_ID)));
        setRequestParam(AttributeConst.RECORD, r);

        moveFlush();

        forward(ForwardConst.FW_RECORD_SHOW);
    }

    public void edit() throws ServletException, IOException {

        Record r = service.getById(toNumber(getRequestParam(AttributeConst.RECORD_ID)));
        User loginUser = getSessionParam(AttributeConst.LOGIN_USER);

        if(r.getUser().getId() != loginUser.getId()) {
            forward(ForwardConst.FW_ERR_UNKNOWN);
            return;
        }

        List<Game> games = gameService.getAll();

        setRequestParam(AttributeConst.RECORD, r);
        setRequestParam(AttributeConst.TOKEN, getTokenId());
        setRequestParam(AttributeConst.GAMES, games);

        moveErrors();

        forward(ForwardConst.FW_RECORD_EDIT);
    }

}
