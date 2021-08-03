package models.validators;

import models.Record;

public class RecordValidator {

    public static void validate(Record r) {

        //マイナス値の入力又はtoNumberによりnull又は不正な値からMIN_VALUEになっている場合
        if(r.getWin() < 0){r.setWin(0);}
        if(r.getLose() < 0){r.setLose(0);}
        if(r.getDraw() < 0){r.setDraw(0);}
        //勝率
        r.setWinRate(calcWinRate(r.getWin(), r.getLose(), r.getDraw()));

        //toNumberによりnullあるいは不正な値からMIN_VALUEになっている場合
        if(r.getPoint() == Integer.MIN_VALUE) {r.setPoint(null);}
    }

    private static Double calcWinRate(int win, int lose, int draw) {

        Double winRate;
        if(win == 0 && lose == 0) {
            winRate = 0.0;
            if(0 < draw) {
                winRate = 50.0;
            }
        } else {
            winRate = ((double)win / (win+lose)) * 100;
        }

        return winRate;
    }
}
