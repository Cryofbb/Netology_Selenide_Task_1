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
        $("[data-test-id='date'] .input__control").setValue(dateFormatted);
        $("[data-test-id= 'name']").$("[name ='name']").setValue("Имя Фамилия");
        $("[data-test-id='phone']").$("[name='phone']").setValue("+70123456789");
        $("[data-test-id=agreement]").click();
        $$("button").find(exactText("Забронировать")).click();
        $("[data-test-id='notification']").shouldBe(visible, Duration.ofSeconds(15));
    }

    @Test
    void shouldNotSubmitIfLess3Days() {
        LocalDate today = LocalDate.now();
        LocalDate meetingDate = today.plusDays(1);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        String dateFormatted = meetingDate.format(formatter);
        $("[data-test-id='city']").$("[placeholder='Город']").setValue("Москва");
        $("[data-test-id='date'] .input__control").sendKeys(Keys.chord(Keys.CONTROL + "a", Keys.DELETE));
        $("[data-test-id='date'] .input__control").setValue(dateFormatted);
        $("[data-test-id= 'name']").$("[name ='name']").setValue("Имя Фамилия");
        $("[data-test-id='phone']").$("[name='phone']").setValue("+70123456789");
        $("[data-test-id=agreement]").click();
        $$("button").find(exactText("Забронировать")).click();
        $("[data-test-id=date] .input_invalid .input__sub").shouldBe(visible).shouldBe(exactText("Заказ на выбранную дату невозможен"));
    }

    @Test
    void shouldNotSubmitIfEmptyDate() {
        LocalDate today = LocalDate.now();
        LocalDate meetingDate = today.plusDays(1);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        String dateFormatted = meetingDate.format(formatter);
        $("[data-test-id='city']").$("[placeholder='Город']").setValue("Москва");
        $("[data-test-id='date'] .input__control").sendKeys(Keys.chord(Keys.CONTROL + "a", Keys.DELETE));
        $("[data-test-id= 'name']").$("[name ='name']").setValue("Имя Фамилия");
        $("[data-test-id='phone']").$("[name='phone']").setValue("+70123456789");
        $("[data-test-id=agreement]").click();
        $$("button").find(exactText("Забронировать")).click();
        $("[data-test-id=date] .input_invalid .input__sub").shouldBe(visible).shouldBe(exactText("Неверно введена дата"));
    }
    @Test
    void shouldSubmit5Days() {
        LocalDate today = LocalDate.now();
        LocalDate meetingDate = today.plusDays(5);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        String dateFormatted = meetingDate.format(formatter);
        $("[data-test-id='city']").$("[placeholder='Город']").setValue("Москва");
        $("[data-test-id='date'] .input__control").setValue(dateFormatted);
        $("[data-test-id= 'name']").$("[name ='name']").setValue("Имя Фамилия");
        $("[data-test-id='phone']").$("[name='phone']").setValue("+70123456789");
        $("[data-test-id=agreement]").click();
        $$("button").find(exactText("Забронировать")).click();
        $("[data-test-id='notification']").shouldBe(visible, Duration.ofSeconds(15));
    }

    @Test
    void shouldNotSubmitWithEmptyCity() {
        LocalDate today = LocalDate.now();
        LocalDate meetingDate = today.plusDays(3);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        String dateFormatted = meetingDate.format(formatter);
        $("[data-test-id='city']").$("[placeholder='Город']").setValue("");
        $("[data-test-id='date'] .input__control").setValue(dateFormatted);
        $("[data-test-id= 'name']").$("[name ='name']").setValue("Имя Фамилия");
        $("[data-test-id='phone']").$("[name='phone']").setValue("+70123456789");
        $("[data-test-id=agreement]").click();
        $$("button").find(exactText("Забронировать")).click();
        $("[data-test-id=city].input_invalid .input__sub").shouldBe(visible).shouldBe(exactText("Поле обязательно для заполнения"));
    }

    @Test
    void shouldNotSubmitWithEmptyName() {
        LocalDate today = LocalDate.now();
        LocalDate meetingDate = today.plusDays(3);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        String dateFormatted = meetingDate.format(formatter);
        $("[data-test-id='city']").$("[placeholder='Город']").setValue("Москва");
        $("[data-test-id='date'] .input__control").setValue(dateFormatted);
        $("[data-test-id= 'name']").$("[name ='name']").setValue("");
        $("[data-test-id='phone']").$("[name='phone']").setValue("+70123456789");
        $("[data-test-id=agreement]").click();
        $$("button").find(exactText("Забронировать")).click();
        $("[data-test-id=name].input_invalid .input__sub").shouldBe(visible).shouldBe(exactText("Поле обязательно для заполнения"));
    }

    @Test
    void shouldNotSubmitWithEmptyPhone() {
        LocalDate today = LocalDate.now();
        LocalDate meetingDate = today.plusDays(3);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        String dateFormatted = meetingDate.format(formatter);
        $("[data-test-id='city']").$("[placeholder='Город']").setValue("Москва");
        $("[data-test-id='date'] .input__control").setValue(dateFormatted);
        $("[data-test-id= 'name']").$("[name ='name']").setValue("Имя Фамилия");
        $("[data-test-id='phone']").$("[name='phone']").setValue("");
        $("[data-test-id=agreement]").click();
        $$("button").find(exactText("Забронировать")).click();
        $("[data-test-id=phone].input_invalid .input__sub").shouldBe(visible).shouldBe(exactText("Поле обязательно для заполнения"));
    }

    @Test
    void shouldNotSubmitWithEmptyAgreement() {
        LocalDate today = LocalDate.now();
        LocalDate meetingDate = today.plusDays(3);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        String dateFormatted = meetingDate.format(formatter);
        $("[data-test-id='city']").$("[placeholder='Город']").setValue("Москва");
        $("[data-test-id='date'] .input__control").setValue(dateFormatted);
        $("[data-test-id= 'name']").$("[name ='name']").setValue("Имя Фамилия");
        $("[data-test-id='phone']").$("[name='phone']").setValue("+70123456789");
        $$("button").find(exactText("Забронировать")).click();
        $("[data-test-id=agreement].input_invalid .checkbox__text").shouldBe(visible).shouldBe(exactText("Я соглашаюсь с условиями обработки и использования моих персональных данных"));
    }

    @Test
    void shouldNotSubmitWithEnglishName() {
        LocalDate today = LocalDate.now();
        LocalDate meetingDate = today.plusDays(3);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        String dateFormatted = meetingDate.format(formatter);
        $("[data-test-id='city']").$("[placeholder='Город']").setValue("Москва");
        $("[data-test-id='date'] .input__control").setValue(dateFormatted);
        $("[data-test-id= 'name']").$("[name ='name']").setValue("Name");
        $("[data-test-id='phone']").$("[name='phone']").setValue("+70123456789");
        $("[data-test-id=agreement]").click();
        $$("button").find(exactText("Забронировать")).click();
        $("[data-test-id=name].input_invalid .input__sub").shouldBe(visible).shouldBe(exactText("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
    }

    @Test
    void shouldNotSubmitWithSymbolName() {
        LocalDate today = LocalDate.now();
        LocalDate meetingDate = today.plusDays(3);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        String dateFormatted = meetingDate.format(formatter);
        $("[data-test-id='city']").$("[placeholder='Город']").setValue("Москва");
        $("[data-test-id='date'] .input__control").setValue(dateFormatted);
        $("[data-test-id= 'name']").$("[name ='name']").setValue("%Имя%");
        $("[data-test-id='phone']").$("[name='phone']").setValue("+70123456789");
        $("[data-test-id=agreement]").click();
        $$("button").find(exactText("Забронировать")).click();
        $("[data-test-id=name].input_invalid .input__sub").shouldBe(visible).shouldBe(exactText("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
    }

    @Test
    void shouldNotSubmitWith10Phone() {
        LocalDate today = LocalDate.now();
        LocalDate meetingDate = today.plusDays(3);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        String dateFormatted = meetingDate.format(formatter);
        $("[data-test-id='city']").$("[placeholder='Город']").setValue("Москва");
        $("[data-test-id='date'] .input__control").setValue(dateFormatted);
        $("[data-test-id= 'name']").$("[name ='name']").setValue("Имя Фамилия");
        $("[data-test-id='phone']").$("[name='phone']").setValue("+7012345678");
        $("[data-test-id=agreement]").click();
        $$("button").find(exactText("Забронировать")).click();
        $("[data-test-id=phone].input_invalid .input__sub").shouldBe(visible).shouldBe(exactText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
    }

    @Test
    void shouldNotSubmitWith12Phone() {
        LocalDate today = LocalDate.now();
        LocalDate meetingDate = today.plusDays(3);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        String dateFormatted = meetingDate.format(formatter);
        $("[data-test-id='city']").$("[placeholder='Город']").setValue("Москва");
        $("[data-test-id='date'] .input__control").setValue(dateFormatted);
        $("[data-test-id= 'name']").$("[name ='name']").setValue("Имя Фамилия");
        $("[data-test-id='phone']").$("[name='phone']").setValue("+701234567890");
        $("[data-test-id=agreement]").click();
        $$("button").find(exactText("Забронировать")).click();
        $("[data-test-id=phone].input_invalid .input__sub").shouldBe(visible).shouldBe(exactText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
    }

    @Test
    void shouldNotSubmitWithoutPlusPhone() {
        LocalDate today = LocalDate.now();
        LocalDate meetingDate = today.plusDays(3);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        String dateFormatted = meetingDate.format(formatter);
        $("[data-test-id='city']").$("[placeholder='Город']").setValue("Москва");
        $("[data-test-id='date'] .input__control").setValue(dateFormatted);
        $("[data-test-id= 'name']").$("[name ='name']").setValue("Имя Фамилия");
        $("[data-test-id='phone']").$("[name='phone']").setValue("70123456789");
        $("[data-test-id=agreement]").click();
        $$("button").find(exactText("Забронировать")).click();
        $("[data-test-id=phone].input_invalid .input__sub").shouldBe(visible).shouldBe(exactText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
    }

    @Test
    void shouldNotSubmitWrongCity() {
        LocalDate today = LocalDate.now();
        LocalDate meetingDate = today.plusDays(3);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        String dateFormatted = meetingDate.format(formatter);
        $("[data-test-id='city']").$("[placeholder='Город']").setValue("Мос");
        $("[data-test-id='date'] .input__control").setValue(dateFormatted);
        $("[data-test-id= 'name']").$("[name ='name']").setValue("Имя Фамилия");
        $("[data-test-id='phone']").$("[name='phone']").setValue("+70123456789");
        $("[data-test-id=agreement]").click();
        $$("button").find(exactText("Забронировать")).click();
        $("[data-test-id=city].input_invalid .input__sub").shouldBe(visible).shouldBe(exactText("Доставка в выбранный город недоступна"));
    }

    @Test
    void shouldNotSubmitEnglishCity() {
        LocalDate today = LocalDate.now();
        LocalDate meetingDate = today.plusDays(3);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        String dateFormatted = meetingDate.format(formatter);
        $("[data-test-id='city']").$("[placeholder='Город']").setValue("Moscow");
        $("[data-test-id='date'] .input__control").setValue(dateFormatted);
        $("[data-test-id= 'name']").$("[name ='name']").setValue("Имя Фамилия");
        $("[data-test-id='phone']").$("[name='phone']").setValue("+70123456789");
        $("[data-test-id=agreement]").click();
        $$("button").find(exactText("Забронировать")).click();
        $("[data-test-id=city].input_invalid .input__sub").shouldBe(visible).shouldBe(exactText("Доставка в выбранный город недоступна"));
    }

    @Test
    void shouldNotSubmitForeignCity() {
        LocalDate today = LocalDate.now();
        LocalDate meetingDate = today.plusDays(3);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        String dateFormatted = meetingDate.format(formatter);
        $("[data-test-id='city']").$("[placeholder='Город']").setValue("Киев");
        $("[data-test-id='date'] .input__control").setValue(dateFormatted);
        $("[data-test-id= 'name']").$("[name ='name']").setValue("Имя Фамилия");
        $("[data-test-id='phone']").$("[name='phone']").setValue("+70123456789");
        $("[data-test-id=agreement]").click();
        $$("button").find(exactText("Забронировать")).click();
        $("[data-test-id=city].input_invalid .input__sub").shouldBe(visible).shouldBe(exactText("Доставка в выбранный город недоступна"));
    }

    @Test
    void shouldNotSubmitSymbolCity() {
        LocalDate today = LocalDate.now();
        LocalDate meetingDate = today.plusDays(3);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        String dateFormatted = meetingDate.format(formatter);
        $("[data-test-id='city']").$("[placeholder='Город']").setValue("Мос%ква");
        $("[data-test-id='date'] .input__control").setValue(dateFormatted);
        $("[data-test-id= 'name']").$("[name ='name']").setValue("Имя Фамилия");
        $("[data-test-id='phone']").$("[name='phone']").setValue("+70123456789");
        $("[data-test-id=agreement]").click();
        $$("button").find(exactText("Забронировать")).click();
        $("[data-test-id=city].input_invalid .input__sub").shouldBe(visible).shouldBe(exactText("Доставка в выбранный город недоступна"));
    }

    @Test
    void shouldNotSubmitWithSingleName() {
        LocalDate today = LocalDate.now();
        LocalDate meetingDate = today.plusDays(3);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        String dateFormatted = meetingDate.format(formatter);
        $("[data-test-id='city']").$("[placeholder='Город']").setValue("Москва");
        $("[data-test-id='date'] .input__control").setValue(dateFormatted);
        $("[data-test-id= 'name']").$("[name ='name']").setValue("Имя");
        $("[data-test-id='phone']").$("[name='phone']").setValue("+70123456789");
        $("[data-test-id=agreement]").click();
        $$("button").find(exactText("Забронировать")).click();
        $("[data-test-id=name].input_invalid .input__sub").shouldBe(visible).shouldBe(exactText("Имя и Фамилия указаны неверно. Проверьте, что введённые данные совпадают с паспортными."));
    }
    @Test
    void shouldSubmitRequestWithDoubleName() {
        LocalDate today = LocalDate.now();
        LocalDate meetingDate = today.plusDays(3);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        String dateFormatted = meetingDate.format(formatter);
        $("[data-test-id='city']").$("[placeholder='Город']").setValue("Москва");
        $("[data-test-id='date'] .input__control").setValue(dateFormatted);
        $("[data-test-id= 'name']").$("[name ='name']").setValue("Имя-имя Фамилия");
        $("[data-test-id='phone']").$("[name='phone']").setValue("+70123456789");
        $("[data-test-id=agreement]").click();
        $$("button").find(exactText("Забронировать")).click();
        $("[data-test-id='notification']").shouldBe(visible, Duration.ofSeconds(15));
    }
}
