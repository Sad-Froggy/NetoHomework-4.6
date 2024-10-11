package ru.netology.test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.netology.data.DataHelper;
import ru.netology.page.LoginPage;
import ru.netology.page.TransferPage;

import static com.codeborne.selenide.Selenide.*;

public class MoneyTransferTest {

    @Test
    void shouldTestTransferBetweenTwoOwnCards() throws InterruptedException {
        var authInfo = DataHelper.getAuthInfo();
        Thread.sleep(3000);
        var verificationCode = DataHelper.getVerificationCode();
        Thread.sleep(3000);
        var loginPage = open("http://localhost:9999", LoginPage.class);
        Thread.sleep(3000);
        var verificationPage = loginPage.validLogin(authInfo);
        Thread.sleep(3000);
        var dashboardPage = verificationPage.validVerify(verificationCode);
        Thread.sleep(3000);
        //сохранить начальные балансы на картах
        int startBalFirst = dashboardPage.getCardBalance(DataHelper.getCardInfo(1).getTestId());
        Thread.sleep(3000);
        int startBalSecond = dashboardPage.getCardBalance(DataHelper.getCardInfo(2).getTestId());
        Thread.sleep(3000);
        //выбрать карту для пополнения (1 или 2)
        TransferPage transferPage = dashboardPage.moneyTransferTo(DataHelper.getCardInfo(2));
        int amount = startBalFirst / 2;
        //перевести на вторую карту с первой
        transferPage.transferBetweenCards(DataHelper.getCardInfo(1), amount);
        int finalBalanceFirst = dashboardPage.getCardBalance(DataHelper.getCardInfo(1).getTestId());
        int finalBalanceSecond = dashboardPage.getCardBalance(DataHelper.getCardInfo(2).getTestId());
        Assertions.assertEquals(startBalFirst - amount, finalBalanceFirst);
        Assertions.assertEquals(startBalSecond + amount, finalBalanceSecond);
    }

    @Test
    void shouldTestLoginWithWrongPassword() {
        var authInfo = DataHelper.getOtherAuthInfo();
        var loginPage = open("http://localhost:9999", LoginPage.class);
        loginPage.validLogin(authInfo);
        loginPage.notificationVisibility();
    }

    @Test
    public void shouldTestCancellationOfTransfer() {
        var authInfo = DataHelper.getAuthInfo();
        var verificationCode = DataHelper.getVerificationCode();
        var dashboardPage = open("http://localhost:9999", LoginPage.class).validLogin(authInfo).validVerify(verificationCode);
        //сохранить начальные балансы на картах
        int startBalFirst = dashboardPage.getCardBalance(DataHelper.getCardInfo(1).getTestId());
        int startBalSecond = dashboardPage.getCardBalance(DataHelper.getCardInfo(2).getTestId());
        TransferPage transferPage = dashboardPage.moneyTransferTo(DataHelper.getCardInfo(2));
        transferPage.transferCancellation();
        Assertions.assertEquals(startBalFirst, dashboardPage.getCardBalance(DataHelper.getCardInfo(1).getTestId()));
        Assertions.assertEquals(startBalSecond, dashboardPage.getCardBalance(DataHelper.getCardInfo(2).getTestId()));
    }

    @Test
    public void shouldTestTransferZeroAmount() {
        var authInfo = DataHelper.getAuthInfo();
        var verificationCode = DataHelper.getVerificationCode();
        var dashboardPage = open("http://localhost:9999", LoginPage.class).validLogin(authInfo).validVerify(verificationCode);
        int startBalFirst = dashboardPage.getCardBalance(DataHelper.getCardInfo(1).getTestId());
        int startBalSecond = dashboardPage.getCardBalance(DataHelper.getCardInfo(2).getTestId());
        TransferPage transferPage = dashboardPage.moneyTransferTo(DataHelper.getCardInfo(1));
        transferPage.transferBetweenCards(DataHelper.getCardInfo(1), 0);
        Assertions.assertEquals(startBalFirst, dashboardPage.getCardBalance(DataHelper.getCardInfo(1).getTestId()));
        Assertions.assertEquals(startBalSecond, dashboardPage.getCardBalance(DataHelper.getCardInfo(2).getTestId()));
    }

    @Test
    void shouldTestTransferWithEmptyFields() {
        var authInfo = DataHelper.getAuthInfo();
        var verificationCode = DataHelper.getOtherVerificationCode();
        var dashboardPage = open("http://localhost:9999", LoginPage.class).validLogin(authInfo).validVerify(verificationCode);
        TransferPage transferPage = dashboardPage.moneyTransferTo(DataHelper.getCardInfo(2));
        transferPage.notificationVisibility();
    }

    @Test
    void shouldTestLoginWithWrongVerificationCode() {
        var authInfo = DataHelper.getAuthInfo();
        var verificationCode = DataHelper.getOtherVerificationCode();
        var verificationPage = open("http://localhost:9999", LoginPage.class).validLogin(authInfo);
        verificationPage.validVerify(verificationCode);
        verificationPage.notificationVisibility();
    }

}
