package ru.netology;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.*;


public class CardDeliveryTest {
    private RegistrationByCardInfo registrationInfo;

    @BeforeEach
    void setUpAll() {
        registrationInfo = DataGenerator.Registration.generateByCard("ru");
    }



    @Test
    void shouldSubmitRequestWithDateAsString() {
        open("http://localhost:9999");
        String day3 = DataGenerator.Registration.generateDatePlus(3);
        String day10 = DataGenerator.Registration.generateDatePlus(10);
        $("input[placeholder=\"Город\"").setValue(registrationInfo.getCity());
        $("input[placeholder=\"Дата встречи\"]").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("input[placeholder=\"Дата встречи\"]").setValue(day3);
        $("input[name=\"name\"]").setValue(registrationInfo.getName());
        $("input[name=\"phone\"]").setValue(registrationInfo.getPhone().toString());
        $("[data-test-id=agreement]").click();
        $$("span.button__text").find(exactText("Запланировать")).click();
        $$("div.notification__content").first().shouldHave(text(day3));
        $("input[placeholder=\"Дата встречи\"]").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("input[placeholder=\"Дата встречи\"]").sendKeys(day10);
        $$("span.button__text").find(exactText("Запланировать")).click();
        //$$("div.notification__content").first().shouldHave(text(day10));
        $ (withText("успешно запланирована")).shouldHave(text(day10));
        $$("span.button__text").find(exactText("Запланировать")).waitUntil(visible, 10000);
        $$("span.button__text").find(exactText("Перепланировать")).click();
        $("[data-test-id=success-notification]").waitUntil(visible, 15000);
    }

}
