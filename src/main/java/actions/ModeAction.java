package actions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;

import constants.AttributeConst;
import constants.ForwardConst;
import constants.JpaConst;
import constants.MessageConst;
import models.Game;
import models.Mode;
import services.GameService;
import services.ModeService;

public class ModeAction extends ActionBase {

    private ModeService service;
    private GameService gameService;

    @Override
    public void process() throws ServletException, IOException {

        service = new ModeService();
        gameService = new GameService();

        invoke();

        service.close();
        gameService.close();
    }

    public void create() throws ServletException, IOException {
        if(checkAdmin() && checkToken()) {

            Game g = gameService.getById(toNumber(getRequestParam(AttributeConst.GAME_ID)));
            setRequestParam(AttributeConst.GAME_ID, g.getId()); //forwardでedit画面に戻るため
            if(g != null && g.getDeleteFlag() == JpaConst.MODE_DELETE_FLAG_FALSE) {
                String name = getRequestParam(AttributeConst.MODE_NAME);
                if(name != null && !name.equals("")) {

                    Mode m = new Mode(
                            null,
                            g,
                            name,
                            JpaConst.MODE_DELETE_FLAG_FALSE
                        );
                    service.create(m);

                    setSessionParam(AttributeConst.FLUSH, MessageConst.S_MODE_CREATE.getMessage());
                    forward(ForwardConst.ACT_GAME, ForwardConst.CMD_EDIT);
                } else { //モード名未入力の場合

                    List<String> errors = new ArrayList<>();
                    errors.add(MessageConst.E_NO_MODENAME.getMessage());

                    setSessionParam(AttributeConst.ERRORS, errors);

                    forward(ForwardConst.ACT_GAME, ForwardConst.CMD_EDIT);
                }
            } else { //Gameが取得できないあるいは削除済みの場合
                forward(ForwardConst.FW_ERR_UNKNOWN);
            }
        }
    }

    public void destroy() throws ServletException, IOException {

        if(checkAdmin() && checkToken()) {

            Mode m = service.getById(toNumber(getRequestParam(AttributeConst.MODE_ID)));
            if(m != null) {
                setSessionParam(AttributeConst.FLUSH, MessageConst.S_MODE_DESTROY.getMessage());
                service.destroy(m);
                forward(ForwardConst.ACT_GAME, ForwardConst.CMD_EDIT);
            } else {
                forward(ForwardConst.FW_ERR_UNKNOWN);
            }
        }
    }
}
