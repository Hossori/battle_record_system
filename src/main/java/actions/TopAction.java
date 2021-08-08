package actions;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;

import constants.AttributeConst;
import constants.ForwardConst;
import constants.JpaConst;
import models.Game;
import models.Mode;
import models.Record;
import services.GameService;
import services.ModeService;
import services.RecordService;
import services.UserService;

public class TopAction extends ActionBase {
    private UserService userService;
    private RecordService recordService;
    private GameService gameService;
    private ModeService modeService;

    @Override
    public void process() throws ServletException, IOException {

        userService = new UserService();
        recordService = new RecordService();
        gameService = new GameService();
        modeService = new ModeService();

        invoke();

        userService.close();
        recordService.close();
        gameService.close();
        modeService.close();
    }

    public void index() throws ServletException, IOException {

        int page = getPage();

        List<Record> records;
        List<Mode> modes;
        int game_id = toNumber(getRequestParam(AttributeConst.GAME_ID));
        int mode_id = toNumber(getRequestParam(AttributeConst.MODE_ID));
        if(game_id <= 0) { //ゲームにつき未選択又は「全て」を選択
            records = recordService.getPerPage(page);
            modes = null;
        } else {
            Game g = gameService.getById(game_id);

            if(mode_id <= 0) { //モードにつき未選択又は「全て」を選択
                records = recordService.getByGamePerPage(g, page);

            } else { //ゲーム及びモードを選択済み
                Mode m = modeService.getById(mode_id);
                records = recordService.getByGameAndModePerPage(g, m, page);

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
        moveErrors();

        List<Game> games = gameService.getAll();

        int[] pagination = getPagination(count, page);
        int page_begin = pagination[0];
        int page_end = pagination[1];

        setRequestParam(AttributeConst.GAMES, games);
        setRequestParam(AttributeConst.MODES, modes);
        setRequestParam(AttributeConst.RECORDS, records);
        setRequestParam(AttributeConst.RECORD_COUNT, count);
        setRequestParam(AttributeConst.PAGE, page);
        setRequestParam(AttributeConst.MAX_ROW, JpaConst.ROW_PER_PAGE);
        setRequestParam(AttributeConst.GAME_ID_SELECTED, game_id);
        setRequestParam(AttributeConst.MODE_ID_SELECTED, mode_id);
        setRequestParam(AttributeConst.PAGE_BEGIN, page_begin);
        setRequestParam(AttributeConst.PAGE_END, page_end);

        forward(ForwardConst.FW_TOP_INDEX);
    }
}
