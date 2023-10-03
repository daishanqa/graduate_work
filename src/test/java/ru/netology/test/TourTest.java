package ru.netology.test;

import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;
import ru.netology.data.DBHelper;
import ru.netology.page.MainPage;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.netology.data.DataGenerator.*;

public class TourTest {

    @BeforeAll
    static void setUpAll() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @AfterEach
    void clearTable() {
        DBHelper.cleanDatabase();
    }

    @BeforeEach
    void openPage() {
        open("http://localhost:8080");
    }

    @AfterAll
    static void tearDownAll() {
        SelenideLogger.removeListener("allure");
    }

    @Test
    @DisplayName("1. Покупка тура одобренной картой")
    void shouldDebitPaymentApprovedCard() {
        var mainPage = new MainPage();
        CardInfo card = new CardInfo(getApprovedCardNumber(), nextMonth, nextYear, generateValidName(), generateCVC());
        mainPage.payWithDebitCard();
        mainPage.fillForm(card);
        mainPage.clickContinueButton();
        mainPage.findSuccessNotification();
        assertEquals("APPROVED", DBHelper.getDebitCardStatus());
    }

    @Test
    @DisplayName("2. Покупка тура одобренной картой в кредит")
    void shouldCreditPaymentApprovedCard() {
        var mainPage = new MainPage();
        CardInfo card = new CardInfo(getApprovedCardNumber(), nextMonth, nextYear, generateValidName(), generateCVC());
        mainPage.payWithCreditCard();
        mainPage.fillForm(card);
        mainPage.clickContinueButton();
        mainPage.findSuccessNotification();
        assertEquals("APPROVED", DBHelper.getCreditStatus());
    }

    @Test
    @DisplayName("3. Покупка тура отклоненной картой")
    void shouldNotDebitPaymentDeclinedCard() {
        var mainPage = new MainPage();
        CardInfo card = new CardInfo(getDeclinedCardNumber(), nextMonth, nextYear, generateValidName(), generateCVC());
        mainPage.payWithDebitCard();
        mainPage.fillForm(card);
        mainPage.clickContinueButton();
        mainPage.findErrorNotification();
        assertEquals("DECLINED", DBHelper.getDebitCardStatus());
    }

    @Test
    @DisplayName("4. Покупка тура отклоненной картой в кредит")
    void shouldNotCreditPaymentDeclinedCard() {
        CardInfo card = new CardInfo(getDeclinedCardNumber(), nextMonth, nextYear, generateValidName(), generateCVC());
        var mainPage = new MainPage();
        mainPage.payWithCreditCard();
        mainPage.fillForm(card);
        mainPage.clickContinueButton();
        mainPage.findErrorNotification();
        assertEquals("DECLINED", DBHelper.getCreditStatus());
    }

    @Test
    @DisplayName("5. Отправка пустой формы покупки тура")
    void shouldNotDebitPaymentEmptyForm() {
        var mainPage = new MainPage();
        mainPage.payWithDebitCard();
        mainPage.clickContinueButton();
        mainPage.getCardNumberError("Поле обязательно для заполнения");
        mainPage.getMonthError("Поле обязательно для заполнения");
        mainPage.getYearError("Поле обязательно для заполнения");
        mainPage.getCardholderError("Поле обязательно для заполнения");
        mainPage.getCVCError("Поле обязательно для заполнения");
    }

    @Test
    @DisplayName("6. Отправка пустой формы покупки тура в кредит")
    void shouldNotCreditPaymentEmptyForm() {
        var mainPage = new MainPage();
        mainPage.payWithCreditCard();
        mainPage.clickContinueButton();
        mainPage.getCardNumberError("Поле обязательно для заполнения");
        mainPage.getMonthError("Поле обязательно для заполнения");
        mainPage.getYearError("Поле обязательно для заполнения");
        mainPage.getCardholderError("Поле обязательно для заполнения");
        mainPage.getCVCError("Поле обязательно для заполнения");
    }

    @Test
    @DisplayName("7. Покупка тура с невалидными значениями номера карты")
    void shouldNOtPayCardNumberShortDebitPayment() {
        var mainPage = new MainPage();
        CardInfo card = new CardInfo(generateShortCardNumber(), nextMonth, nextYear, generateValidName(), generateCVC());
        mainPage.payWithDebitCard();
        mainPage.fillForm(card);
        mainPage.clickContinueButton();
        mainPage.getCardNumberError("Неверный формат");
    }

    @Test
    @DisplayName("8. Покупка тура с невалидными значениями номера карты в кредит ")
    void shouldNOtPayCardNumberShortCreditPayment() {
        var mainPage = new MainPage();
        CardInfo card = new CardInfo(generateShortCardNumber(), nextMonth, nextYear, generateValidName(), generateCVC());
        mainPage.payWithCreditCard();
        mainPage.fillForm(card);
        mainPage.clickContinueButton();
        mainPage.getCardNumberError("Неверный формат");
    }

    @Test
    @DisplayName("9. Покупка тура несуществующей картой")
    void shouldNOtPayRandomCardDebitPayment() {
        var mainPage = new MainPage();
        CardInfo card = new CardInfo(generateCreditCard(), nextMonth, nextYear, generateValidName(), generateCVC());
        mainPage.payWithDebitCard();
        mainPage.fillForm(card);
        mainPage.clickContinueButton();
        mainPage.findErrorNotification();
    }

    @Test
    @DisplayName("10. Покупка тура несуществующей картой в кредит")
    void shouldNOtPayRandomCardCreditPayment() {
        var mainPage = new MainPage();
        CardInfo card = new CardInfo(generateCreditCard(), nextMonth, nextYear, generateValidName(), generateCVC());
        mainPage.payWithCreditCard();
        mainPage.fillForm(card);
        mainPage.clickContinueButton();
        mainPage.findErrorNotification();
    }

    @Test
    @DisplayName("11. Отправка формы покупки тура с пустым полем Номер карты")
    void shouldNOtPayCardNumberEmptyDebitPayment() {
        var mainPage = new MainPage();
        CardInfo card = new CardInfo(getEmptyCArd(), nextMonth, nextYear, generateValidName(), generateCVC());
        mainPage.payWithDebitCard();
        mainPage.fillForm(card);
        mainPage.clickContinueButton();
        mainPage.getCardNumberError("Неверный формат");
    }


    @Test
    @DisplayName("12. Отправка формы покупки тура с пустым полем Номер карты в кредит")
    void shouldNOtPayCardNumberEmptyCreditPayment() {
        var mainPage = new MainPage();
        CardInfo card = new CardInfo(getEmptyCArd(), nextMonth, nextYear, generateValidName(), generateCVC());
        mainPage.payWithCreditCard();
        mainPage.fillForm(card);
        mainPage.clickContinueButton();
        mainPage.getCardNumberError("Неверный формат");
    }

    @Test
    @DisplayName("13. Покупка тура по карте с истекшим сроком действия в поле Месяц")
    void shouldNotPayPreviousMonthDebitPayment() {
        var mainPage = new MainPage();
        CardInfo card = new CardInfo(getApprovedCardNumber(), previousMonth, currentYear, generateValidName(), generateCVC());
        mainPage.payWithDebitCard();
        mainPage.fillForm(card);
        mainPage.clickContinueButton();
        mainPage.getMonthError("Неверно указан срок действия карты");
    }

    @Test
    @DisplayName("14. Покупка тура по карте с истекшим сроком действия в поле Месяц в кредит")
    void shouldNotPayPreviousMonthCreditPayment() {
        var mainPage = new MainPage();
        CardInfo card = new CardInfo(getApprovedCardNumber(), previousMonth, currentYear, generateValidName(), generateCVC());
        mainPage.payWithCreditCard();
        mainPage.fillForm(card);
        mainPage.clickContinueButton();
        mainPage.getMonthError("Неверно указан срок действия карты");
    }

    @Test
    @DisplayName("15. Покупка тура,с указанием в поле Месяц: (00)")
    void shouldNotPayZeroMonthDebitPayment() {
        var mainPage = new MainPage();
        CardInfo card = new CardInfo(getApprovedCardNumber(), zeroMonth, currentYear, generateValidName(), generateCVC());
        mainPage.payWithDebitCard();
        mainPage.fillForm(card);
        mainPage.clickContinueButton();
        mainPage.getMonthError("Неверно указан срок действия карты");

    }

    @Test
    @DisplayName("16. Покупка тура,с указанием в поле Месяц: (00), в кредит")
    void shouldNotPayZeroMonthCreditPayment() {
        var mainPage = new MainPage();
        CardInfo card = new CardInfo(getApprovedCardNumber(), zeroMonth, currentYear, generateValidName(), generateCVC());
        mainPage.payWithCreditCard();
        mainPage.fillForm(card);
        mainPage.clickContinueButton();
        mainPage.getMonthError("Неверно указан срок действия карты");
    }

    @Test
    @DisplayName("17. Покупка тура, с указанием в поле Месяц: (13+)")
    void shouldNotPayMonthGreaterThanTwelveDebitPayment() {
        var mainPage = new MainPage();
        CardInfo card = new CardInfo(getApprovedCardNumber(), generateMonthNumberGreaterThanTwelve(), currentYear, generateValidName(), generateCVC());
        mainPage.payWithDebitCard();
        mainPage.fillForm(card);
        mainPage.clickContinueButton();
        mainPage.getMonthError("Неверно указан срок действия карты");
    }

    @Test
    @DisplayName("18. Покупка тура, с указанием в поле Месяц: (13+), в кредит")
    void shouldNotPayMonthGreaterMoreTwelveCreditPayment() {
        var mainPage = new MainPage();
        CardInfo card = new CardInfo(getApprovedCardNumber(), generateMonthNumberGreaterThanTwelve(), currentYear, generateValidName(), generateCVC());
        mainPage.payWithCreditCard();
        mainPage.fillForm(card);
        mainPage.clickContinueButton();
        mainPage.getMonthError("Неверно указан срок действия карты");
    }

    @Test
    @DisplayName("19. Покупка тура с пустым полем Месяц")
    void shouldNotPayEmptyMonthDebitPayment() {
        var mainPage = new MainPage();
        CardInfo card = new CardInfo(getApprovedCardNumber(), emptyMonth, currentYear, generateValidName(), generateCVC());
        mainPage.payWithDebitCard();
        mainPage.fillForm(card);
        mainPage.clickContinueButton();
        mainPage.getMonthError("Неверный формат");
    }

    @Test
    @DisplayName("20. Покупка тура с пустым полем Месяц в кредит")
    void shouldNotPayEmptyMonthCreditPayment() {
        var mainPage = new MainPage();
        CardInfo card = new CardInfo(getApprovedCardNumber(), emptyMonth, currentYear, generateValidName(), generateCVC());
        mainPage.payWithCreditCard();
        mainPage.fillForm(card);
        mainPage.clickContinueButton();
        mainPage.getMonthError("Неверный формат");
    }

    @Test
    @DisplayName("21. Покупка тура по карте с истекшим сроком действия в поле Год")
    void shouldNotPayPreviousYearDebitPayment() {
        var mainPage = new MainPage();
        CardInfo card = new CardInfo(getApprovedCardNumber(), nextMonth, previousYear, generateValidName(), generateCVC());
        mainPage.payWithDebitCard();
        mainPage.fillForm(card);
        mainPage.clickContinueButton();
        mainPage.getYearError("Истёк срок действия карты");
    }

    @Test
    @DisplayName("22.Покупка тура по карте с истекшим сроком действия в поле Год в кредит")
    void shouldNotPayPreviousYearCreditPayment() {
        var mainPage = new MainPage();
        CardInfo card = new CardInfo(getApprovedCardNumber(), currentMonth, previousYear, generateValidName(), generateCVC());
        mainPage.payWithCreditCard();
        mainPage.fillForm(card);
        mainPage.clickContinueButton();
        mainPage.getYearError("Истёк срок действия карты");
    }

    @Test
    @DisplayName("23. Покупка тура с указанием года в поле Год более 5ти лет от текущего")
    void shouldNotPayFiveYearFutureDebitPayment() {
        var mainPage = new MainPage();
        CardInfo card = new CardInfo(getApprovedCardNumber(), nextMonth, generateInvalidYearFuture, generateValidName(), generateCVC());
        mainPage.payWithDebitCard();
        mainPage.fillForm(card);
        mainPage.clickContinueButton();
        mainPage.getYearError("Неверно указан срок действия карты");
    }

    @Test
    @DisplayName("24. Покупка тура с указанием года в поле Год более 5ти лет от текущего в кредит")
    void shouldNotPayFiveYearFutureCreditPayment() {
        var mainPage = new MainPage();
        CardInfo card = new CardInfo(getApprovedCardNumber(), nextMonth, generateInvalidYearFuture, generateValidName(), generateCVC());
        mainPage.payWithCreditCard();
        mainPage.fillForm(card);
        mainPage.clickContinueButton();
        mainPage.getYearError("Неверно указан срок действия карты");
    }

    @Test
    @DisplayName("25. Покупка тура с пустым полем Год")
    void shouldNotPayEmptyYearDebitPayment() {
        var mainPage = new MainPage();
        CardInfo card = new CardInfo(getApprovedCardNumber(), nextMonth, emptyYear, generateValidName(), generateCVC());
        mainPage.payWithDebitCard();
        mainPage.fillForm(card);
        mainPage.clickContinueButton();
        mainPage.getYearError("Неверный формат");
    }

    @Test
    @DisplayName("26. Покупка тура с пустым полем Год в кредит")
    void shouldNotPayEmptyYearCreditPayment() {
        var mainPage = new MainPage();
        CardInfo card = new CardInfo(getApprovedCardNumber(), nextMonth, emptyYear, generateValidName(), generateCVC());
        mainPage.payWithCreditCard();
        mainPage.fillForm(card);
        mainPage.clickContinueButton();
        mainPage.getYearError("Неверный формат");
    }

    @Test
    @DisplayName("27. Покупка тура с указанием одного слова в поле Владелец")
    void shouldNotPayOnlyNameDebitPayment() {
        var mainPage = new MainPage();
        CardInfo card = new CardInfo(getApprovedCardNumber(), nextMonth, currentYear, generateFirstName(), generateCVC());
        mainPage.payWithDebitCard();
        mainPage.fillForm(card);
        mainPage.clickContinueButton();
        mainPage.getCardholderError("Неверный формат");
    }

    @Test
    @DisplayName("28. Покупка тура с указанием одного слова в поле Владелец в кредит")
    void shouldNotPayOnlyNameCreditPayment() {
        var mainPage = new MainPage();
        CardInfo card = new CardInfo(getApprovedCardNumber(), nextMonth, currentYear, generateFirstName(), generateCVC());
        mainPage.payWithCreditCard();
        mainPage.fillForm(card);
        mainPage.clickContinueButton();
        mainPage.getCardholderError("Неверный формат");
    }

    @Test
    @DisplayName("29. Покупка тура с пустым полем Владелец")
    void shouldNotPayEmptyHolderDebitPayment() {
        var mainPage = new MainPage();
        CardInfo card = new CardInfo(getApprovedCardNumber(), nextMonth, currentYear, emptyCardholder, generateCVC());
        mainPage.payWithDebitCard();
        mainPage.fillForm(card);
        mainPage.clickContinueButton();
        mainPage.getCardholderError("Поле обязательно для заполнения");
    }

    @Test
    @DisplayName("30. Покупка тура с пустым полем Владелец в кредит")
    void shouldNotPayEmptyHolderCreditPayment() {
        var mainPage = new MainPage();
        CardInfo card = new CardInfo(getApprovedCardNumber(), nextMonth, currentYear, emptyCardholder, generateCVC());
        mainPage.payWithCreditCard();
        mainPage.fillForm(card);
        mainPage.clickContinueButton();
        mainPage.getCardholderError("Поле обязательно для заполнения");
    }

    @Test
    @DisplayName("31. Покупка тура со специальными сиволами в поле Владелец")
    void shouldNotPaySpecialCharactersOnlyDebitPayment() {
        var mainPage = new MainPage();
        String specialCharactersName = generateSpecialCharactersOnly(10);
        CardInfo card = new CardInfo(getApprovedCardNumber(), nextMonth, currentYear, specialCharactersName, generateCVC());
        mainPage.payWithDebitCard();
        mainPage.fillForm(card);
        mainPage.clickContinueButton();
        mainPage.getCardholderError("Неверный формат");
    }

    @Test
    @DisplayName("32. Покупка тура со специальными сиволами в поле Владелец в кредит")
    void shouldNotPaySpecialCharactersOnlyCreditPayment() {
        var mainPage = new MainPage();
        String specialCharactersName = generateSpecialCharactersOnly(10);
        CardInfo card = new CardInfo(getApprovedCardNumber(), nextMonth, currentYear, specialCharactersName, generateCVC());
        mainPage.payWithCreditCard();
        mainPage.fillForm(card);
        mainPage.clickContinueButton();
        mainPage.getCardholderError("Неверный формат");
    }

    @Test
    @DisplayName("33. Покупка тура с заполненным кирилицей полем Владелец")
    void shouldNotPayCyrillicNameDebitPayment() {
        var mainPage = new MainPage();
        CardInfo card = new CardInfo(getApprovedCardNumber(), nextMonth, currentYear, generateCyrillicName(), generateCVC());
        mainPage.payWithDebitCard();
        mainPage.fillForm(card);
        mainPage.clickContinueButton();
        mainPage.getCardholderError("Неверный формат");
    }

    @Test
    @DisplayName("34. Покупка тура с заполненным кирилицей полем Владелец в кредит")
    void shouldNotPayCyrillicNameCreditPayment() {
        var mainPage = new MainPage();
        CardInfo card = new CardInfo(getApprovedCardNumber(), nextMonth, currentYear, generateCyrillicName(), generateCVC());
        mainPage.payWithCreditCard();
        mainPage.fillForm(card);
        mainPage.clickContinueButton();
        mainPage.getCardholderError("Неверный формат");
    }

    @Test
    @DisplayName("35. Покупка тура с невалидным значением в поле CVC/CVV")
    void shouldNotPayInvalidCVVDebitPayment() {
        var mainPage = new MainPage();
        CardInfo card = new CardInfo(getApprovedCardNumber(), nextMonth, currentYear, generateValidName(), generateInvalidCVC());
        mainPage.payWithDebitCard();
        mainPage.fillForm(card);
        mainPage.clickContinueButton();
        mainPage.getCVCError("Неверный формат");
    }

    @Test
    @DisplayName("36. Покупка тура с невалидным значением в поле CVC/CVV в кредит")
    void shouldNotPayInvalidCVVCreditPayment() {
        var mainPage = new MainPage();
        CardInfo card = new CardInfo(getApprovedCardNumber(), nextMonth, currentYear, generateValidName(), generateInvalidCVC());
        mainPage.payWithCreditCard();
        mainPage.fillForm(card);
        mainPage.clickContinueButton();
        mainPage.getCVCError("Неверный формат");
    }

    @Test
    @DisplayName("37. Покупка тура с пустым полем CVC/CVV")
    void shouldNotPayEmptyCVVDebitPayment() {
        var mainPage = new MainPage();
        CardInfo card = new CardInfo(getApprovedCardNumber(), nextMonth, currentYear, generateValidName(), emptyCvv);
        mainPage.payWithDebitCard();
        mainPage.fillForm(card);
        mainPage.clickContinueButton();
        mainPage.getCVCError("Поле обязательно для заполнения");
    }

    @Test
    @DisplayName("38. Покупка тура с пустым полем CVC/CVV в кредит")
    void shouldNotPayEmptyCVVCreditPayment() {
        var mainPage = new MainPage();
        CardInfo card = new CardInfo(getApprovedCardNumber(), nextMonth, currentYear, generateValidName(), emptyCvv);
        mainPage.payWithCreditCard();
        mainPage.fillForm(card);
        mainPage.clickContinueButton();
        mainPage.getCVCError("Поле обязательно для заполнения");
    }


}
