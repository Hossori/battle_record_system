package actions;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;

import constants.AttributeConst;
import constants.ForwardConst;
import constants.JpaConst;
import constants.PropertyConst;
import models.Game;
import models.Mode;
import models.Record;
import models.User;
import models.validators.UserValidator;
import services.GameService;
import services.ModeService;
import services.RecordService;
import services.UserService;
import utils.EncryptUtil;

public class UserAction extends ActionBase {
    private UserService service;
    private GameService gameService;
    private RecordService recordService;
    private ModeService modeService;

    @Override
    public void process() throws ServletException, IOException {
        service = new UserService();
        gameService = new GameService();
        recordService = new RecordService();
        modeService = new ModeService();

        invoke();

        service.close();
        gameService.close();
        recordService.close();
        modeService.close();
    }

    //マイページの表示
    public void mypage() throws ServletException, IOException {
        User loginUser = getSessionParam(AttributeConst.LOGIN_USER);
        List<Game> games = gameService.getAll();

        List<Record> records;
        List<Mode> modes;
        int page = getPage();
        int game_id = toNumber(getRequestParam(AttributeConst.GAME_ID));
        int mode_id = toNumber(getRequestParam(AttributeConst.MODE_ID));
        if(game_id <= 0) { //ゲームにつき未選択又は「全て」を選択
            records = recordService.getByUserPerPage(loginUser, page);
            modes = null;
        } else {
            Game g = gameService.getById(game_id);

            if(mode_id <= 0) { //モードにつき未選択又は「全て」を選択
                records = recordService.getByUserAndGamePerPage(loginUser, g, page);

            } else { //ゲーム及びモードを選択済み
                Mode m = modeService.getById(mode_id);
                records = recordService.getByUserAndGameAndModePerPage(loginUser, g, m, page);

            }

            modes = g.getModeList();
        }
        int count;
        if(records != null) {
            count = records.size();
        } else {
            count = 0;
        }

        moveFlush();

        setRequestParam(AttributeConst.RECORDS, records);
        setRequestParam(AttributeConst.RECORD_COUNT, count);
        setRequestParam(AttributeConst.GAMES, games);
        setRequestParam(AttributeConst.MODES, modes);
        setRequestParam(AttributeConst.GAME_ID_SELECTED, game_id);
        setRequestParam(AttributeConst.MODE_ID_SELECTED, mode_id);

        //setRequestParam(AttributeConst, );

        forward(ForwardConst.FW_USER_MYPAGE);
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

            redirect(ForwardConst.ACT_USER, ForwardConst.CMD_MYPAGE);
        }
    }



}
