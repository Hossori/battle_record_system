package actions;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;

import org.json.JSONArray;
import org.json.JSONObject;

import constants.JpaConst;
import models.Game;
import models.Mode;
import services.GameService;
import services.ModeService;

public class AjaxAction extends ActionBase {

    private ModeService modeService;
    private GameService gameService;

    @Override
    public void process() throws ServletException, IOException {
        modeService = new ModeService();
        gameService = new GameService();

        invoke();

        modeService.close();
        gameService.close();
    }

    //jQuery.ajax通信用
    public void getModeListByGame() throws ServletException, IOException {

        Game g = gameService.getById(toNumber(request.getParameter("game_id")));
        List<Mode> modeList = g.getModeList();

        /*
        response.setContentType("application/json; charset=UTF-8");
        response.setHeader("pragma", "no-cache");
        response.setHeader("Cache-Control", "no-cache");
        */

        JSONArray modesJSON = new JSONArray();
        for(Mode m:modeList) {
            if(m.getDeleteFlag() == JpaConst.MODE_DELETE_FLAG_TRUE) {continue;}

            JSONObject modeJSON = new JSONObject();
            modeJSON.put("id", m.getId());
            modeJSON.put("name", m.getName());
            modesJSON.put(modeJSON);
        }

        PrintWriter out = response.getWriter();
        out.print(modesJSON);
    }

}
