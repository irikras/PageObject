package ru.netology.web.test;

import com.codeborne.selenide.Condition;
import lombok.var;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.web.data.DataHelper;
import ru.netology.web.page.LoginPage;
import ru.netology.web.page.DashboardPage;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;

class MoneyTransferTest {

    @BeforeEach
    void setUp() {
        open("http://localhost:9999");
    }

    @Test
    void shouldReplenishmentOfTheFirstCard() {
        var loginPage = new LoginPage();
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        var dashboardPage = verificationPage.validVerify(verificationCode);
        int amnt = 50000;
        var balanceFirstCardAfterTransfer = (DashboardPage.getFirstCardBalance() - amnt);
        var balanceSecondCardAfterTransfer = (DashboardPage.getSecondCardBalance() + amnt);
        $("[data-test-id='0f3f5c2a-249e-4c3d-8287-09f7a039391d'] .button").click();
        $("[data-test-id=amount] input").setValue(String.valueOf(amnt));
        $("[data-test-id=from] input").setValue("5559000000000001");
        $("[data-test-id=action-transfer]").click();
        $("[data-test-id=dashboard]").shouldBe(Condition.visible);
        var newBalanceFirstCard = dashboardPage.getFirstCardBalance();
        assertEquals(balanceFirstCardAfterTransfer, newBalanceFirstCard);
        var newBalanceSecondCard = dashboardPage.getSecondCardBalance();
        assertEquals(balanceSecondCardAfterTransfer, newBalanceSecondCard);
    }

    @Test
    void shouldReplenishmentOfTheSecondCard() {
        var loginPage = new LoginPage();
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        var dashboardPage = verificationPage.validVerify(verificationCode);
        int amnt = 3000;
        var balanceFirstCardAfterTransfer = (DashboardPage.getFirstCardBalance() + amnt);
        var balanceSecondCardAfterTransfer = (DashboardPage.getSecondCardBalance() - amnt);
        $("[data-test-id='92df3f1c-a033-48e6-8390-206f6b1f56c0'] .button").click();
        $("[data-test-id=amount] input").setValue(String.valueOf(amnt));
        $("[data-test-id=from] input").setValue("5559000000000002");
        $("[data-test-id=action-transfer]").click();
        $("[data-test-id=dashboard]").shouldBe(Condition.visible);
        var newBalanceFirstCard = dashboardPage.getFirstCardBalance();
        assertEquals(balanceFirstCardAfterTransfer, newBalanceFirstCard);
        var newBalanceSecondCard = dashboardPage.getSecondCardBalance();
        assertEquals(balanceSecondCardAfterTransfer, newBalanceSecondCard);
    }
}
