package models.validators;

import java.util.ArrayList;
import java.util.List;

import constants.MessageConst;
import services.GameService;

public class GameValidator {
    private static GameService service;

    public static List<String> validate(String name, boolean chkDuplicateName) {

        service = new GameService();

        List<String> errors = new ArrayList<>();

        validateName(errors, name, chkDuplicateName);

        service.close();
        
        return errors;
    }

    private static void validateName(List<String> errors, String name, boolean chkDuplicate) {
        if(name == null || name.equals("")) {
            errors.add(MessageConst.E_NO_GAMENAME.getMessage());
        } else if(chkDuplicate) {
            if(service.isExistName(name)) {
                errors.add(MessageConst.E_EXIST_GAMENAME.getMessage());
            }
        }
    }
}
