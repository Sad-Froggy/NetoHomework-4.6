package ru.netology.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import ru.netology.data.DataHelper;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;

public class VerificationPage {

    private SelenideElement code = $("[data-test-id='code'] input");
    private SelenideElement actionButton = $("[data-test-id='action-verify']");
    private SelenideElement notification = $("[data-test-id='error-notification'] .notification__content");

    public VerificationPage() {
    }

    public DashboardPage validVerify(String verificationCode) {
        code.setValue(verificationCode);
        actionButton.click();
        return new DashboardPage();
    }

    public void notificationVisibility() {
        notification.shouldBe(Condition.visible).shouldHave(text("Ошибка! Неверно указан код! Попробуйте ещё раз."));
    }
}
