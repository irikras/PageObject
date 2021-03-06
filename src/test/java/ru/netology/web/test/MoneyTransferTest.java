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
        int amnt = 4000;
        var balanceFirstCardAfterTransfer = dashboardPage.getFirstCardBalance() + amnt;
        var balanceSecondCardAfterTransfer = dashboardPage.getSecondCardBalance() - amnt;
        var transferPage = dashboardPage.findCardToTransfer(DataHelper.getFirstCardNumber());
        transferPage.transferCard(DataHelper.getSecondCardNumber(), amnt);
        var newBalanceFirstCard = DashboardPage.getFirstCardBalance();
        var newBalanceSecondCard = DashboardPage.getSecondCardBalance();
        assertEquals(balanceFirstCardAfterTransfer, newBalanceFirstCard);
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
        var balanceFirstCardAfterTransfer = dashboardPage.getFirstCardBalance() - amnt;
        var balanceSecondCardAfterTransfer = dashboardPage.getSecondCardBalance() + amnt;
        var transferPage = dashboardPage.findCardToTransfer(DataHelper.getSecondCardNumber());
        transferPage.transferCard(DataHelper.getFirstCardNumber(), amnt);
        var newBalanceFirstCard = DashboardPage.getFirstCardBalance();
        var newBalanceSecondCard = DashboardPage.getSecondCardBalance();
        assertEquals(balanceFirstCardAfterTransfer, newBalanceFirstCard);
        assertEquals(balanceSecondCardAfterTransfer, newBalanceSecondCard);
    }
}
