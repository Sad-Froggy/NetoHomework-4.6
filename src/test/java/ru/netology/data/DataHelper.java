package ru.netology.data;

import lombok.Value;

public class DataHelper {

    public static AuthInfo getAuthInfo() {
        return new AuthInfo("vasya", "qwerty123");
    }

    public static AuthInfo getOtherAuthInfo() {
        return new AuthInfo("vasya", "qwerty12321");
    }

    public static CardInfo getCardInfo(int cardId) {
        switch (cardId) {
            case (1) :
                return new CardInfo("5559 0000 0000 0001", "92df3f1c-a033-48e6-8390-206f6b1f56c0");
            case (2) :
                return new CardInfo("5559 0000 0000 0002", "0f3f5c2a-249e-4c3d-8287-09f7a039391d");
            default:
                throw new IllegalArgumentException();
        }
    }

    public static String getVerificationCode() {
        return "12345";
    }

    public static String getOtherVerificationCode() {
        return "54321";
    }

    @Value
    public static class CardInfo {
        String cardNum;
        String testId;
    }

    @Value
    public static class AuthInfo {
        String login;
        String password;
    }

}
