package ru.netology.web.page;

import com.codeborne.selenide.SelenideElement;
import ru.netology.web.data.DataHelper;

import static com.codeborne.selenide.Selenide.$;

public class TransferPage {
    private SelenideElement amountField = $("[data-test-id=amount] input");
    private SelenideElement fromField = $("[data-test-id=from] input");
    private SelenideElement transferButton = $("[data-test-id=action-transfer]");

    public void transferCard(DataHelper.CardInfo CardInfo, int amountToTransfer) {
        amountField.setValue(String.valueOf(amountToTransfer));
        fromField.setValue(CardInfo.getCardNumber());
        transferButton.click();
    }
}
