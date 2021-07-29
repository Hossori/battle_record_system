package models.validators;

import java.util.ArrayList;
import java.util.List;

import constants.MessageConst;
import services.UserService;

public class UserValidator {
    private static UserService service;

    //ログイン
    public static List<String> validate(String email, String plainPass) {
        List<String> errors = new ArrayList<>();

        validateEmail(errors, email, false);
        validatePass(errors, plainPass);

        return errors;
    }

    //新規登録
    public static List<String> validate(String email, String name, String plainPass) {
        List<String> errors = new ArrayList<>();
        service = new UserService();

        validateEmail(errors, email, true);
        validateName(errors, name);
        validatePass(errors, plainPass);

        return errors;
    }

    //メアドの入力チェック
    private static void validateEmail(List<String> errors, String email, boolean chkDuplicate) {
        if(email == null || email.equals("")) {
            errors.add(MessageConst.E_NO_EMAIL.getMessage());
            return;
        }
        if(chkDuplicate) {
            if(service.isExistEmail(email)) {
                errors.add(MessageConst.E_EXIST_EMAIL.getMessage());
            }
        }
    }
    //ユーザー名の入力チェック
    private static void validateName(List<String> errors, String name) {
        if(name == null || name.equals("")) {
            errors.add(MessageConst.E_NO_USERNAME.getMessage());
        }
    }
    //パスワードの入力チェック
    private static void validatePass(List<String> errors, String plainPass) {
        if(plainPass == null || plainPass.equals("")) {
            errors.add(MessageConst.E_NO_PASS.getMessage());
        }
    }
}
