package ru.netology;

import org.junit.jupiter.api.*;
import org.openqa.selenium.Keys;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.Duration;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;


public class MeetingTest {
    public String dateFormatted (int days){
        LocalDate today = LocalDate.now();
        LocalDate meetingDate = today.plusDays(days);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        String formattedDate = meetingDate.format(formatter);
        return formattedDate;
    }

    @BeforeEach
    void setup() {
        open("http://localhost:9999");
        $("[data-test-id='date'] .input__control").sendKeys(Keys.chord(Keys.CONTROL + "a", Keys.DELETE));
    }

    @Test
    void shouldSubmitRequest() {

        $("[data-test-id='city']").$("[placeholder='Город']").setValue("Москва");
        $("[data-test-id='date'] .input__control").setValue(dateFormatted(3));
        $("[data-test-id= 'name']").$("[name ='name']").setValue("Имя Фамилия");
        $("[data-test-id='phone']").$("[name='phone']").setValue("+70123456789");
        $("[data-test-id=agreement]").click();
        $$("button").find(exactText("Забронировать")).click();
        $("[data-test-id=notification] .notification__content").shouldBe(visible, Duration.ofSeconds(15)).shouldBe(exactText("Встреча успешно забронирована на " + dateFormatted(3)));
    }

    @Test
    void shouldNotSubmitIfLess3Days() {
        $("[data-test-id='city']").$("[placeholder='Город']").setValue("Москва");
        $("[data-test-id='date'] .input__control").setValue(dateFormatted(2));
        $("[data-test-id= 'name']").$("[name ='name']").setValue("Имя Фамилия");
        $("[data-test-id='phone']").$("[name='phone']").setValue("+70123456789");
        $("[data-test-id=agreement]").click();
        $$("button").find(exactText("Забронировать")).click();
        $("[data-test-id=date] .input_invalid .input__sub").shouldBe(visible).shouldBe(exactText("Заказ на выбранную дату невозможен"));
    }

    @Test
    void shouldNotSubmitIfEmptyDate() {
        $("[data-test-id='city']").$("[placeholder='Город']").setValue("Москва");
        $("[data-test-id= 'name']").$("[name ='name']").setValue("Имя Фамилия");
        $("[data-test-id='phone']").$("[name='phone']").setValue("+70123456789");
        $("[data-test-id=agreement]").click();
        $$("button").find(exactText("Забронировать")).click();
        $("[data-test-id=date] .input_invalid .input__sub").shouldBe(visible).shouldBe(exactText("Неверно введена дата"));
    }
    @Test
    void shouldSubmit5Days() {
        $("[data-test-id='city']").$("[placeholder='Город']").setValue("Москва");
        $("[data-test-id='date'] .input__control").setValue(dateFormatted(5));
        $("[data-test-id= 'name']").$("[name ='name']").setValue("Имя Фамилия");
        $("[data-test-id='phone']").$("[name='phone']").setValue("+70123456789");
        $("[data-test-id=agreement]").click();
        $$("button").find(exactText("Забронировать")).click();
        $("[data-test-id=notification] .notification__content").shouldBe(visible, Duration.ofSeconds(15)).shouldBe(exactText("Встреча успешно забронирована на " + dateFormatted(5)));
    }

    @Test
    void shouldNotSubmitWithEmptyCity() {
        $("[data-test-id='city']").$("[placeholder='Город']").setValue("");
        $("[data-test-id='date'] .input__control").setValue(dateFormatted(3));
        $("[data-test-id= 'name']").$("[name ='name']").setValue("Имя Фамилия");
        $("[data-test-id='phone']").$("[name='phone']").setValue("+70123456789");
        $("[data-test-id=agreement]").click();
        $$("button").find(exactText("Забронировать")).click();
        $("[data-test-id=city].input_invalid .input__sub").shouldBe(visible).shouldBe(exactText("Поле обязательно для заполнения"));
    }

    @Test
    void shouldNotSubmitWithEmptyName() {
        $("[data-test-id='city']").$("[placeholder='Город']").setValue("Москва");
        $("[data-test-id='date'] .input__control").setValue(dateFormatted(3));
        $("[data-test-id= 'name']").$("[name ='name']").setValue("");
        $("[data-test-id='phone']").$("[name='phone']").setValue("+70123456789");
        $("[data-test-id=agreement]").click();
        $$("button").find(exactText("Забронировать")).click();
        $("[data-test-id=name].input_invalid .input__sub").shouldBe(visible).shouldBe(exactText("Поле обязательно для заполнения"));
    }

    @Test
    void shouldNotSubmitWithEmptyPhone() {
        $("[data-test-id='city']").$("[placeholder='Город']").setValue("Москва");
        $("[data-test-id='date'] .input__control").setValue(dateFormatted(3));
        $("[data-test-id= 'name']").$("[name ='name']").setValue("Имя Фамилия");
        $("[data-test-id='phone']").$("[name='phone']").setValue("");
        $("[data-test-id=agreement]").click();
        $$("button").find(exactText("Забронировать")).click();
        $("[data-test-id=phone].input_invalid .input__sub").shouldBe(visible).shouldBe(exactText("Поле обязательно для заполнения"));
    }

    @Test
    void shouldNotSubmitWithEmptyAgreement() {
        $("[data-test-id='city']").$("[placeholder='Город']").setValue("Москва");
        $("[data-test-id='date'] .input__control").setValue(dateFormatted(3));
        $("[data-test-id= 'name']").$("[name ='name']").setValue("Имя Фамилия");
        $("[data-test-id='phone']").$("[name='phone']").setValue("+70123456789");
        $$("button").find(exactText("Забронировать")).click();
        $("[data-test-id=agreement].input_invalid .checkbox__text").shouldBe(visible).shouldBe(exactText("Я соглашаюсь с условиями обработки и использования моих персональных данных"));
    }

    @Test
    void shouldNotSubmitWithEnglishName() {
        $("[data-test-id='city']").$("[placeholder='Город']").setValue("Москва");
        $("[data-test-id='date'] .input__control").setValue(dateFormatted(3));
        $("[data-test-id= 'name']").$("[name ='name']").setValue("Name");
        $("[data-test-id='phone']").$("[name='phone']").setValue("+70123456789");
        $("[data-test-id=agreement]").click();
        $$("button").find(exactText("Забронировать")).click();
        $("[data-test-id=name].input_invalid .input__sub").shouldBe(visible).shouldBe(exactText("Имя и Фамилия указаны неверно. Допустимы только русские буквы, пробелы и дефисы."));
    }

    @Test
    void shouldNotSubmitWithSymbolName() {
        $("[data-test-id='city']").$("[placeholder='Город']").setValue("Москва");
        $("[data-test-id='date'] .input__control").setValue(dateFormatted(3));
        $("[data-test-id= 'name']").$("[name ='name']").setValue("%Имя%");
        $("[data-test-id='phone']").$("[name='phone']").setValue("+70123456789");
        $("[data-test-id=agreement]").click();
        $$("button").find(exactText("Забронировать")).click();
        $("[data-test-id=name].input_invalid .input__sub").shouldBe(visible).shouldBe(exactText("Имя и Фамилия указаны неверно. Допустимы только русские буквы, пробелы и дефисы."));
    }

    @Test
    void shouldNotSubmitWith10Phone() {
        $("[data-test-id='city']").$("[placeholder='Город']").setValue("Москва");
        $("[data-test-id='date'] .input__control").setValue(dateFormatted(3));
        $("[data-test-id= 'name']").$("[name ='name']").setValue("Имя Фамилия");
        $("[data-test-id='phone']").$("[name='phone']").setValue("+7012345678");
        $("[data-test-id=agreement]").click();
        $$("button").find(exactText("Забронировать")).click();
        $("[data-test-id=phone].input_invalid .input__sub").shouldBe(visible).shouldBe(exactText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
    }

    @Test
    void shouldNotSubmitWith12Phone() {
        $("[data-test-id='city']").$("[placeholder='Город']").setValue("Москва");
        $("[data-test-id='date'] .input__control").setValue(dateFormatted(3));
        $("[data-test-id= 'name']").$("[name ='name']").setValue("Имя Фамилия");
        $("[data-test-id='phone']").$("[name='phone']").setValue("+701234567890");
        $("[data-test-id=agreement]").click();
        $$("button").find(exactText("Забронировать")).click();
        $("[data-test-id=phone].input_invalid .input__sub").shouldBe(visible).shouldBe(exactText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
    }

    @Test
    void shouldNotSubmitWithoutPlusPhone() {
        $("[data-test-id='city']").$("[placeholder='Город']").setValue("Москва");
        $("[data-test-id='date'] .input__control").setValue(dateFormatted(3));
        $("[data-test-id= 'name']").$("[name ='name']").setValue("Имя Фамилия");
        $("[data-test-id='phone']").$("[name='phone']").setValue("70123456789");
        $("[data-test-id=agreement]").click();
        $$("button").find(exactText("Забронировать")).click();
        $("[data-test-id=phone].input_invalid .input__sub").shouldBe(visible).shouldBe(exactText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
    }

    @Test
    void shouldNotSubmitWrongCity() {
        $("[data-test-id='city']").$("[placeholder='Город']").setValue("Мос");
        $("[data-test-id='date'] .input__control").setValue(dateFormatted(3));
        $("[data-test-id= 'name']").$("[name ='name']").setValue("Имя Фамилия");
        $("[data-test-id='phone']").$("[name='phone']").setValue("+70123456789");
        $("[data-test-id=agreement]").click();
        $$("button").find(exactText("Забронировать")).click();
        $("[data-test-id=city].input_invalid .input__sub").shouldBe(visible).shouldBe(exactText("Доставка в выбранный город недоступна"));
    }

    @Test
    void shouldNotSubmitEnglishCity() {
        $("[data-test-id='city']").$("[placeholder='Город']").setValue("Moscow");
        $("[data-test-id='date'] .input__control").setValue(dateFormatted(3));
        $("[data-test-id= 'name']").$("[name ='name']").setValue("Имя Фамилия");
        $("[data-test-id='phone']").$("[name='phone']").setValue("+70123456789");
        $("[data-test-id=agreement]").click();
        $$("button").find(exactText("Забронировать")).click();
        $("[data-test-id=city].input_invalid .input__sub").shouldBe(visible).shouldBe(exactText("Доставка в выбранный город недоступна"));
    }

    @Test
    void shouldNotSubmitForeignCity() {
        $("[data-test-id='city']").$("[placeholder='Город']").setValue("Киев");
        $("[data-test-id='date'] .input__control").setValue(dateFormatted(3));
        $("[data-test-id= 'name']").$("[name ='name']").setValue("Имя Фамилия");
        $("[data-test-id='phone']").$("[name='phone']").setValue("+70123456789");
        $("[data-test-id=agreement]").click();
        $$("button").find(exactText("Забронировать")).click();
        $("[data-test-id=city].input_invalid .input__sub").shouldBe(visible).shouldBe(exactText("Доставка в выбранный город недоступна"));
    }

    @Test
    void shouldNotSubmitSymbolCity() {
        $("[data-test-id='city']").$("[placeholder='Город']").setValue("Мос%ква");
        $("[data-test-id='date'] .input__control").setValue(dateFormatted(3));
        $("[data-test-id= 'name']").$("[name ='name']").setValue("Имя Фамилия");
        $("[data-test-id='phone']").$("[name='phone']").setValue("+70123456789");
        $("[data-test-id=agreement]").click();
        $$("button").find(exactText("Забронировать")).click();
        $("[data-test-id=city].input_invalid .input__sub").shouldBe(visible).shouldBe(exactText("Доставка в выбранный город недоступна"));
    }

    @Test
    void shouldNotSubmitWithSingleName() {
        $("[data-test-id='city']").$("[placeholder='Город']").setValue("Москва");
        $("[data-test-id='date'] .input__control").setValue(dateFormatted(3));
        $("[data-test-id= 'name']").$("[name ='name']").setValue("Имя");
        $("[data-test-id='phone']").$("[name='phone']").setValue("+70123456789");
        $("[data-test-id=agreement]").click();
        $$("button").find(exactText("Забронировать")).click();
        $("[data-test-id=name].input_invalid .input__sub").shouldBe(visible).shouldBe(exactText("Имя и Фамилия указаны неверно. Проверьте, что введённые данные совпадают с паспортными."));
    }
    @Test
    void shouldSubmitRequestWithDoubleName() {
        $("[data-test-id='city']").$("[placeholder='Город']").setValue("Москва");
        $("[data-test-id='date'] .input__control").setValue(dateFormatted(3));
        $("[data-test-id= 'name']").$("[name ='name']").setValue("Имя-имя Фамилия");
        $("[data-test-id='phone']").$("[name='phone']").setValue("+70123456789");
        $("[data-test-id=agreement]").click();
        $$("button").find(exactText("Забронировать")).click();
        $("[data-test-id=notification] .notification__content").shouldBe(visible, Duration.ofSeconds(15)).shouldBe(exactText("Встреча успешно забронирована на " + dateFormatted(3)));
    }
}
