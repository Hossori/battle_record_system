package models.validators;

import java.util.ArrayList;
import java.util.List;

import constants.MessageConst;

public class UserValidator {

    //ユーザー情報更新用
    public static List<String> validate(String email, String name) {
        List<String> errors = new ArrayList<>();

        validateEmail(errors, email);
        validateName(errors, name);

        return errors;
    }

    //新規登録用
    public static List<String> validate(String email, String name, String plainPass) {
        List<String> errors = new ArrayList<>();

        validateEmail(errors, email);
        validateName(errors, name);
        validatePass(errors, plainPass);

        return errors;
    }

    //メアドの入力チェック
    private static void validateEmail(List<String> errors, String email) {
        if(email == null || email.equals("")) {
            errors.add(MessageConst.E_NO_EMAIL.getMessage());
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
