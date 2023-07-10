package com.oneline.shimpyo.modules;

import com.oneline.shimpyo.domain.member.dto.MemberReq;
import com.oneline.shimpyo.domain.member.dto.OAuthInfoReq;

import java.util.regex.Pattern;

public class RegexValidator {
    public static final Pattern EMAIL_REGEX = Pattern.compile("^[a-zA-Z0-9+-.]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]{1,4}$");
    public static final Pattern FIRST_PASSWORD_REGEX = Pattern.compile("^(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,20}$");
    public static final Pattern NICKNAME_REGEX = Pattern.compile("^[a-zA-Zㄱ-힣0-9]{2,8}$");
    public static final Pattern PHONE_NUMBER_REGEX = Pattern.compile("^(01[016789]{1})[0-9]{3,4}[0-9]{4}$");

    public static boolean validateRequest(MemberReq request) {
        boolean a = validateEmail(request.getEmail());
        boolean b = validateFirstPassword(request.getPassword());
//        boolean c = validateSecondPassword(request.getSecondPassword(), request.getFirstPassword());
        boolean d = validateNickname(request.getNickname());
        boolean e = validatePhoneNumber(request.getPhoneNumber());

        if(!a || !b || !d || !e) {
            return false;
        }
        return true;
    }

    public static boolean validatePassword(String password) {
        if(!validateFirstPassword(password))
            return false;
        return true;
    }

    public static boolean validateEmail(String email) {
        return EMAIL_REGEX.matcher(email).matches();
    }

    public static boolean validateFirstPassword(String password) {
        return FIRST_PASSWORD_REGEX.matcher(password).matches();
    }

    public static boolean validateSecondPassword(String password2, String password1) {
        return password1.equals(password2) ? true : false;
    }

    public static boolean validateNickname(String nickname) {
        return NICKNAME_REGEX.matcher(nickname).matches();
    }

    public static boolean validatePhoneNumber(String phoneNumber) {
        return PHONE_NUMBER_REGEX.matcher(phoneNumber).matches();
    }
}
