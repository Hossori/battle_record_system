package actions;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;

import constants.AttributeConst;
import constants.ForwardConst;
import constants.JpaConst;
import constants.MessageConst;
import models.Game;
import models.Mode;
import models.Record;
import models.User;
import models.validators.RecordValidator;
import services.GameService;
import services.ModeService;
import services.RecordService;

public class RecordAction extends ActionBase {
    private RecordService service;
    private GameService gameService;
    private ModeService modeService;

    @Override
    public void process() throws ServletException, IOException {
        service = new RecordService();
        gameService = new GameService();
        modeService = new ModeService();

        invoke();

        service.close();
        gameService.close();
        modeService.close();
    }

    public void show() throws ServletException, IOException {

        Record r = service.getById(toNumber(getRequestParam(AttributeConst.RECORD_ID)));
        setRequestParam(AttributeConst.RECORD, r);

        moveFlush();

        forward(ForwardConst.FW_RECORD_SHOW);
    }

    public void entry() throws ServletException, IOException {

        List<Game> games = gameService.getAll();
        List<Mode> modes = null;
        if(0 < games.size()) {
            modes = games.get(0).getModeList();
        }

        User loginUser = getSessionParam(AttributeConst.LOGIN_USER);

        if(loginUser == null) { //
            forward(ForwardConst.FW_ERR_UNKNOWN);
            return;
        }

        setRequestParam(AttributeConst.TOKEN, getTokenId());
        setRequestParam(AttributeConst.GAMES, games);
        setRequestParam(AttributeConst.MODES, modes);

        forward(ForwardConst.FW_RECORD_ENTRY);
    }

    public void create() throws ServletException, IOException {

        if(checkToken()) {
            Game g = gameService.getById(toNumber(getRequestParam(AttributeConst.GAME_ID)));
            Mode m = modeService.getById(toNumber(getRequestParam(AttributeConst.MODE_ID)));
            if(g.getDeleteFlag() == JpaConst.GAME_DELETE_FLAG_TRUE ||
               m.getDeleteFlag() == JpaConst.MODE_DELETE_FLAG_TRUE) {
                forward(ForwardConst.FW_ERR_UNKNOWN);
                return;
            }

            Record r = new Record(
                                null,
                                toLocalDateTime(getRequestParam(AttributeConst.RECORD_DATETIME)),
                                getSessionParam(AttributeConst.LOGIN_USER),
                                g,
                                m,
                                null,
                                toNumber(getRequestParam(AttributeConst.RECORD_WIN)),
                                toNumber(getRequestParam(AttributeConst.RECORD_LOSE)),
                                toNumber(getRequestParam(AttributeConst.RECORD_DRAW)),
                                toNumber(getRequestParam(AttributeConst.RECORD_POINT)),
                                getRequestParam(AttributeConst.RECORD_MEMO)
                            );

            RecordValidator.validate(r);

            service.create(r);

            setSessionParam(AttributeConst.FLUSH, MessageConst.S_RECORD_CREATE.getMessage());

            redirect(ForwardConst.ACT_USER, ForwardConst.CMD_MYPAGE);
        }
    }

    public void edit() throws ServletException, IOException {

        Record r = service.getById(toNumber(getRequestParam(AttributeConst.RECORD_ID)));
        User loginUser = getSessionParam(AttributeConst.LOGIN_USER);

        if(r == null || r.getUser().getId() != loginUser.getId()) {
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

    public void update() throws ServletException, IOException {

        if(checkToken()) {

            Record r = service.getById(toNumber(getRequestParam(AttributeConst.RECORD_ID)));
            if(checkUser(r)) {

                Game g = gameService.getById(toNumber(getRequestParam(AttributeConst.GAME_ID)));
                Mode m = modeService.getById(toNumber(getRequestParam(AttributeConst.MODE_ID)));
                if(g.getDeleteFlag() == JpaConst.GAME_DELETE_FLAG_TRUE ||
                   m.getDeleteFlag() == JpaConst.MODE_DELETE_FLAG_TRUE) {
                    forward(ForwardConst.FW_ERR_UNKNOWN);
                    return;
                }

                Record update_r = new Record(
                        null,
                        toLocalDateTime(getRequestParam(AttributeConst.RECORD_DATETIME)),
                        null,
                        g,
                        m,
                        null,
                        toNumber(getRequestParam(AttributeConst.RECORD_WIN)),
                        toNumber(getRequestParam(AttributeConst.RECORD_LOSE)),
                        toNumber(getRequestParam(AttributeConst.RECORD_DRAW)),
                        toNumber(getRequestParam(AttributeConst.RECORD_POINT)),
                        getRequestParam(AttributeConst.RECORD_MEMO)
                    );

                RecordValidator.validate(update_r);

                service.update(r, update_r);

                setSessionParam(AttributeConst.FLUSH, MessageConst.S_RECORD_UPDATE.getMessage());

                redirect(ForwardConst.ACT_USER, ForwardConst.CMD_MYPAGE);
            }
        }
    }

    public void destroy() throws ServletException, IOException {

        if(checkToken()) {

            Record r = service.getById(toNumber(getRequestParam(AttributeConst.RECORD_ID)));
            if(r == null) {
                forward(ForwardConst.FW_ERR_UNKNOWN);
                return;
            }

            if(checkUser(r)) {
                service.destroy(r);
                redirect(ForwardConst.ACT_USER, ForwardConst.CMD_MYPAGE);
            }
        }
    }

    /*
     * ログイン中のユーザーがRecord作成者かどうか
     */
    private boolean checkUser(Record r) throws ServletException, IOException {

        User loginUser = getSessionParam(AttributeConst.LOGIN_USER);
        if(loginUser.getId() == r.getUser().getId()) {
            return true;
        } else {
            forward(ForwardConst.FW_ERR_UNKNOWN);
            return false;
        }
    }

}
