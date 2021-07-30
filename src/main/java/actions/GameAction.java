package actions;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;

import constants.AttributeConst;
import constants.ForwardConst;
import constants.JpaConst;
import constants.MessageConst;
import models.Game;
import models.validators.GameValidator;
import services.GameService;
import services.ModeService;


public class GameAction extends ActionBase {

    private GameService service;
    private ModeService modeService;

    @Override
    public void process() throws ServletException, IOException {

        service = new GameService();
        modeService = new ModeService();

        invoke();

        service.close();
        modeService.close();
    }

    public void index() throws ServletException, IOException {

        if(checkAdmin()) {
            int page = getPage();
            List<Game> games = service.getPerPage(page);
            long gameCount = service.countAll();

            //ページ、ゲームリスト、ゲーム数、最大表示数をセット
            setRequestParam(AttributeConst.PAGE, page);
            setRequestParam(AttributeConst.GAMES, games);
            setRequestParam(AttributeConst.GAME_COUNT, gameCount);
            setRequestParam(AttributeConst.MAX_ROW, JpaConst.ROW_PER_PAGE);

            //create用token
            setRequestParam(AttributeConst.TOKEN, getTokenId());

            //フラッシュ、エラーリストをセット
            moveFlush();
            moveErrors();

            forward(ForwardConst.FW_GAME_INDEX);
        }
    }

    public void create() throws ServletException, IOException {
        //管理者・トークンチェック
        if(checkAdmin() && checkToken()) {

            String name = getRequestParam(AttributeConst.GAME_NAME);
            //ゲーム名の空欄・重複チェック
            List<String> errors = GameValidator.validate(name, true);
            if(errors.size() == 0) {

                //gameのcreate
                Game g = new Game(
                            null,
                            name,
                            JpaConst.GAME_DELETE_FLAG_FALSE,
                            null
                        );
                service.create(g);
                setSessionParam(AttributeConst.FLUSH, MessageConst.S_GAME_REGISTER.getMessage());

                //modeのcreate
                List<String> modeNames = getRequestParamValues(AttributeConst.MODE);
                if(modeNames != null && 0 < modeNames.size()) {
                    for(String modeName:modeNames) {
                        if(modeName != null && !modeName.equals("")) {
                            modeService.create(g, modeName);
                        }
                    }
                }
            } else {

                setSessionParam(AttributeConst.ERRORS, errors);
            }

            redirect(ForwardConst.ACT_GAME, ForwardConst.CMD_INDEX);
        }
    }

    public void edit() throws ServletException, IOException {
        if(checkAdmin()) {

            Game g = service.getById(toNumber(getRequestParam(AttributeConst.GAME_ID)));
            // gameの取得に成功し、デリートフラグがfalseのとき
            if(g != null && g.getDeleteFlag() == JpaConst.GAME_DELETE_FLAG_FALSE) {

                setRequestParam(AttributeConst.GAME, g);
                setRequestParam(AttributeConst.TOKEN, getTokenId());

                moveFlush();
                moveErrors();

                forward(ForwardConst.FW_GAME_EDIT);
            } else {

                forward(ForwardConst.FW_ERR_UNKNOWN);
            }
        }
    }
}
