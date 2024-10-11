package ru.netology.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import lombok.val;
import ru.netology.data.DataHelper;

import static com.codeborne.selenide.Condition.attribute;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class DashboardPage {
    private ElementsCollection cards = $$(".list__item div");
    private String balanceStart = "баланс: ";
    private String balanceFinish = " р.";
    private SelenideElement reloadButton = $("[data-test-id='action-reload']");

    public int getCardBalance(String id) {
        var text = cards.findBy(Condition.attribute("data-test-id", id)).getText();
        return extractBalance(text);
    }

    public int extractBalance(String text) {
        val start = text.indexOf(balanceStart);
        val finish = text.indexOf(balanceFinish);
        val value = text.substring(start + balanceStart.length(), finish);
        return Integer.parseInt(value);
    }

    public TransferPage moneyTransferTo(DataHelper.CardInfo card) {
        String id = card.getTestId();
        SelenideElement nCard = cards.find(attribute("data-test-id", id));
        nCard.$(".button[data-test-id='action-deposit']").click();
        return new TransferPage();
    }

}
