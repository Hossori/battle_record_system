package models.validators;

import java.util.ArrayList;
import java.util.List;

import constants.MessageConst;
import models.User;
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
    public static List<String> validate(String email, String name, String plainPass, String rePlainPass) {
        List<String> errors = new ArrayList<>();
        service = new UserService();

        validatePass(errors, plainPass, rePlainPass);
        validateEmail(errors, email, true);
        validateName(errors, name);
        validatePass(errors, plainPass);

        return errors;
    }

    //ユーザー情報の更新
    public static List<String> validate(User u, String rePlainPass) {
        List<String> errors = new ArrayList<>();
        service = new UserService();

        validatePass(errors, u.getPassword(), rePlainPass);

        String email = u.getEmail();
        if(email != null && !email.equals("")) {
            validateEmail(errors, email, true);
        }

        String name = u.getName();
        validateName(errors, name);

        return errors;
    }

    //パスワードの再入力チェック
    private static void validatePass(List<String> errors, String plainPass, String rePlainPass) {
        if(!plainPass.equals(rePlainPass)) {
            errors.add(MessageConst.E_NO_MATCH_REPASS.getMessage());
        }
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
