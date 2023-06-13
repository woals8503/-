package com.oneline.shimpyo.utils;

import com.oneline.shimpyo.domain.member.dto.MemberReq;

import java.util.regex.Pattern;

public class RegexValidator {
    public static final Pattern EMAIL_REGEX = Pattern.compile("^[a-zA-Z0-9+-.]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]{1,4}$");
    public static final Pattern FIRST_PASSWORD_REGEX = Pattern.compile("^(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,20}$");
    public static final Pattern SECOND_PASSWORD_REGEX = Pattern.compile("(\\\\w)\\\\1\\\\1");
    public static final Pattern NICKNAME_REGEX = Pattern.compile("^[a-zA-Zㄱ-힣0-9]{2,8}$");
    public static final Pattern PHONE_NUMBER_REGEX = Pattern.compile("^(01[016789]{1})[0-9]{3,4}[0-9]{4}$");

    public static boolean validateRequest(MemberReq request) {
        boolean a = validateEmail(request.getEmail());
        boolean b = validateFirstPassword(request.getFirstPassword());
        boolean c = validateSecondPassword(request.getSecondPassword());
        boolean d = validateNickname(request.getNickname());
        boolean e = validatePhoneNumber(request.getPhoneNumber());
        if(!a || !b || !c || !d || !e) {
            return false;
        }
        return true;
    }

    public static boolean validatePassword(String first, String second) {
        boolean a = validateFirstPassword(first);
        boolean b = validateSecondPassword(second);
        if(!a || !b) return false;

        return true;
    }

    public static boolean validateEmail(String email) {
        return EMAIL_REGEX.matcher(email).matches();
    }

    public static boolean validateFirstPassword(String password) {
        return FIRST_PASSWORD_REGEX.matcher(password).matches();
    }

    public static boolean validateSecondPassword(String password) {
        return SECOND_PASSWORD_REGEX.matcher(password).matches();
    }

    public static boolean validateNickname(String nickname) {
        return NICKNAME_REGEX.matcher(nickname).matches();
    }

    public static boolean validatePhoneNumber(String phoneNumber) {
        return PHONE_NUMBER_REGEX.matcher(phoneNumber).matches();
    }
}
