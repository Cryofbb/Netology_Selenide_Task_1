package ru.netology;

import org.junit.jupiter.api.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.Duration;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;


public class MeetingTest {
    @BeforeEach
    void setup() {
        open("http://localhost:9999");

    }

    @Test
    void shouldSubmitRequest() {
        LocalDate today = LocalDate.now();
        LocalDate meetingDate = today.plusDays(3);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        String dateFormatted = meetingDate.format(formatter);
        $("[data-test-id='city']").$("[placeholder='Город']").setValue("Москва");
        $("[data-test-id='date']").$("[placeholder='Дата встречи']").setValue(dateFormatted);
        $("[data-test-id= 'name']").$("[name ='name']").setValue("Имя Фамилия");
        $("[data-test-id='phone']").$("[name='phone']").setValue("+70123456789");
        $("[data-test-id=agreement]").click();
        $$("button").find(exactText("Забронировать")).click();
        $("[data-test-id='notification']").shouldBe(visible, Duration.ofSeconds(15));
    }
}
// [data-test-id=city].input_invalid .input__sub City error
// [data-test-id=name].input_invalid .input__sub Name
// [data-test-id=date] .input_invalid .input__sub Date
// [data-test-id=phone].input_invalid .input__sub Phone
// [data-test-id=agreement].input_invalid .checkbox__text