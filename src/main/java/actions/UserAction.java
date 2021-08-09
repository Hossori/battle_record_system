package actions;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

        int userId = toNumber(getRequestParam(AttributeConst.USER_ID));
        User u;
        if(userId == Integer.MIN_VALUE) { //user_idのパラメータない場合
            u = getSessionParam(AttributeConst.LOGIN_USER);
        } else {
            u = service.getById(userId);
        }

        //ユーザーが存在しないあるいは削除済みの場合はエラー画面に遷移
        if(u == null || u.getDeleteFlag() == JpaConst.USER_DELETE_FLAG_TRUE) {
            forward(ForwardConst.FW_ERR_UNKNOWN);
            return;
        }

        List<Game> games = gameService.getAll();

        List<Record> records;
        List<Record> all_records;
        List<Mode> modes;
        int page = getPage();
        int game_id = toNumber(getRequestParam(AttributeConst.GAME_ID));
        int mode_id = toNumber(getRequestParam(AttributeConst.MODE_ID));
        if(game_id <= 0) { //ゲームにつき未選択又は「全て」を選択
            records = recordService.getByUserPerPage(u, page);
            all_records = recordService.getByUser(u);
            modes = null;
        } else {
            Game g = gameService.getById(game_id);

            if(mode_id <= 0) { //モードにつき未選択又は「全て」を選択
                records = recordService.getByUserAndGamePerPage(u, g, page);
                all_records = recordService.getByUserAndGame(u, g);

            } else { //ゲーム及びモードを選択済み
                Mode m = modeService.getById(mode_id);
                records = recordService.getByUserAndGameAndModePerPage(u, g, m, page);
                all_records = recordService.getByUserAndGameAndMode(u, g, m);

            }

            modes = g.getModeList();
        }

        int all_count;
        if(all_records != null) {
            all_count = all_records.size();
        } else {
            all_count = 0;
        }

        int[] pagination = getPagination(all_count, page);
        int page_begin = pagination[0];
        int page_end = pagination[1];

        Map<String, Object> total = getTotal(all_records);

        moveFlush();

        setRequestParam(AttributeConst.USER, u);
        setRequestParam(AttributeConst.RECORDS, records);
        setRequestParam(AttributeConst.RECORD_COUNT, all_count);
        setRequestParam(AttributeConst.PAGE, page);
        setRequestParam(AttributeConst.MAX_ROW, JpaConst.ROW_PER_PAGE);
        setRequestParam(AttributeConst.GAMES, games);
        setRequestParam(AttributeConst.MODES, modes);
        setRequestParam(AttributeConst.GAME_ID_SELECTED, game_id);
        setRequestParam(AttributeConst.MODE_ID_SELECTED, mode_id);
        setRequestParam(AttributeConst.TOTAL, total);
        setRequestParam(AttributeConst.PAGE_BEGIN, page_begin);
        setRequestParam(AttributeConst.PAGE_END, page_end);

        forward(ForwardConst.FW_USER_MYPAGE);
    }

    private Map<String, Object> getTotal(List<Record> records) {
        Map<String, Object> total = new HashMap<>();

        Integer count = 0;
        Integer win = 0;
        Integer lose = 0;
        Integer draw = 0;
        Integer point = 0;

        for(Record r:records) {
            win += r.getWin();
            lose += r.getLose();
            draw += r.getDraw();
            count += (win+lose+draw);
            if(r.getPoint() != null) {point += r.getPoint();}
        }

        total.put(AttributeConst.WIN.getValue(), win);
        total.put(AttributeConst.LOSE.getValue(), lose);
        total.put(AttributeConst.DRAW.getValue(), draw);
        total.put(AttributeConst.COUNT.getValue(), count);
        total.put(AttributeConst.POINT.getValue(), point);

        Double winRate = 0.0;
        if(win + lose == 0) {
            if(0 < draw) {
                winRate = 50.0;
            }
        } else {
            winRate = (double)win / (win+lose) * 100;
        }
        total.put(AttributeConst.WIN_RATE.getValue(), winRate);

        return total;
    }

    //新規登録フォームの表示
    public void entry() throws ServletException, IOException {
        setRequestParam(AttributeConst.TOKEN, getTokenId());
        //エラーによる再表示の際に入力情報を引き継ぐ
        setRequestParam(AttributeConst.USER_EMAIL, getRequestParam(AttributeConst.USER_EMAIL));
        setRequestParam(AttributeConst.USER_NAME, getRequestParam(AttributeConst.USER_NAME));
        setRequestParam(AttributeConst.USER_REPASS, getRequestParam(AttributeConst.USER_REPASS));

        moveErrors();

        forward(ForwardConst.FW_USER_ENTRY);
    }

    //新規登録
    public void create() throws ServletException, IOException {
        String email = getRequestParam(AttributeConst.USER_EMAIL);
        String name = getRequestParam(AttributeConst.USER_NAME);
        String plainPass = getRequestParam(AttributeConst.USER_PASS);
        String rePlainPass = getRequestParam(AttributeConst.USER_REPASS);
        String pepper = getContextParam(PropertyConst.PEPPER);

        List<String> errors = UserValidator.validate(email, name, plainPass, rePlainPass);

        if(0 < errors.size()) {
            //forwardで入力情報を引き継ぐ
            setRequestParam(AttributeConst.USER_EMAIL, email);
            setRequestParam(AttributeConst.USER_NAME, name);
            setSessionParam(AttributeConst.ERRORS, errors);
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

            setRequestParam(AttributeConst.USER_ID, u.getId());
            forward(ForwardConst.ACT_USER, ForwardConst.CMD_MYPAGE);
        }
    }

    //ユーザーページ
    public void show() throws ServletException, IOException {

        User u = service.getById(toNumber(getRequestParam(AttributeConst.USER_ID)));
        if(u == null || u.getDeleteFlag() == JpaConst.USER_DELETE_FLAG_TRUE) {
            forward(ForwardConst.FW_ERR_UNKNOWN);
            return;
        }

        int record_count = recordService.getByUser(u).size();
        setRequestParam(AttributeConst.USER, u);
        setRequestParam(AttributeConst.RECORD_COUNT, record_count);

        forward(ForwardConst.FW_USER_SHOW);
    }

    //ユーザー情報の編集
    public void edit() throws ServletException, IOException {

        User u = getSessionParam(AttributeConst.LOGIN_USER);

        setRequestParam(AttributeConst.USER, u);
        setRequestParam(AttributeConst.TOKEN, getTokenId());
        setRequestParam(AttributeConst.USER_REPASS, getRequestParam(AttributeConst.USER_REPASS));

        moveErrors();

        forward(ForwardConst.FW_USER_EDIT);
    }

    public void update() throws ServletException, IOException {

        if(checkToken()) {

            User loginUser = getSessionParam(AttributeConst.LOGIN_USER);
            User u = service.getById(loginUser.getId()); //更新用のEntityを取得する
            String email = getRequestParam(AttributeConst.USER_EMAIL);
            String plainPass = getRequestParam(AttributeConst.USER_PASS);
            String rePlainPass = getRequestParam(AttributeConst.USER_REPASS);
            if(email == null || email.equals("")) {email = null;}
            if(plainPass == null || plainPass.equals("")) {plainPass = null;}

            User update_u = new User(
                                null,
                                email,
                                getRequestParam(AttributeConst.USER_NAME),
                                plainPass,
                                getRequestParam(AttributeConst.USER_INTRODUCTION),
                                null,
                                null
                            );
            List<String> errors = UserValidator.validate(update_u, rePlainPass);
            if(0 < errors.size()) {
                setSessionParam(AttributeConst.ERRORS, errors);
                redirect(ForwardConst.ACT_USER, ForwardConst.CMD_EDIT);
            } else {
                if(plainPass != null) {
                    String pepper = getContextParam(PropertyConst.PEPPER);
                    update_u.setPassword(EncryptUtil.getPasswordEncrypt(plainPass, pepper));
                }

                service.update(u, update_u);

                //セッションスコープのログインユーザーに更新後のEntityを入れる
                loginUser = service.getById(loginUser.getId());
                setSessionParam(AttributeConst.LOGIN_USER, loginUser);

                setRequestParam(AttributeConst.USER_ID, u.getId());
                forward(ForwardConst.ACT_USER, ForwardConst.CMD_SHOW);
            }
        }
    }

    public void destroy() throws ServletException, IOException {

        if(checkToken()) {

            User u = getSessionParam(AttributeConst.LOGIN_USER);

            service.destroy(u);

            redirect(ForwardConst.ACT_AUTH, ForwardConst.CMD_LOGOUT);
        }
    }
}
