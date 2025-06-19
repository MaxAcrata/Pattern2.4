package ru.netology.qamid;

import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;
import static ru.netology.qamid.DataGenerator.generateLogin;
import static ru.netology.qamid.DataGenerator.generatePassword;

//java -jar ./artifacts/app-ibank.jar -P:profile=test

/**
 * Тест на авторизацию зарегистрированного пользователя.
 */
public class AuthTest {

    @BeforeAll
    static void setupAll() {
        Configuration.baseUrl = "http://localhost:9999";
        Configuration.browserSize = "1920x1080";
    }

    @BeforeEach
    void openPage() {
        open("/");
    }

    /**
     * Позитивный тест: авторизация активного пользователя.
     */
    @Test
    void loginSuccessRegisteredUser() {
        // Генерируем и регистрируем активного пользователя через API
        RegistrationDto user = DataGenerator.generateUser("active");
        ApiHelper.registerUser(user);

        // Заполняем форму входа
        $("[data-test-id=login] input").setValue(user.getLogin());
        $("[data-test-id=password] input").setValue(user.getPassword());
        $("button.button").click();

        // Проверяем успешный вход
        $(".heading").shouldBe(visible).shouldHave(text("Личный кабинет"));
    }

    /**
     * Негативный тест: вход пользователя со статусом "blocked".
     */
    @Test
    void loginWithBlockedUser() {
        RegistrationDto user = DataGenerator.generateUser("blocked");
        ApiHelper.registerUser(user);

        $("[data-test-id=login] input").setValue(user.getLogin());
        $("[data-test-id=password] input").setValue(user.getPassword());
        $("button.button").click();

        $("[data-test-id=error-notification]")
                .shouldBe(visible)
                .shouldHave(text("Пользователь заблокирован"));
    }

    /**
     * Негативный тест: неверный логин.
     */
    @Test
    void loginWithInvalidLogin() {
        RegistrationDto validUser = DataGenerator.generateUser("active");
        ApiHelper.registerUser(validUser);

        $("[data-test-id=login] input").setValue(generateLogin()); // другой логин
        $("[data-test-id=password] input").setValue(validUser.getPassword());
        $("button.button").click();

        $("[data-test-id=error-notification]")
                .shouldBe(visible)
                .shouldHave(text("Неверно указан логин или пароль"));
    }

    /**
     * Негативный тест: неверный пароль.
     */
    @Test
    void loginWithInvalidPassword() {
        RegistrationDto validUser = DataGenerator.generateUser("active");
        ApiHelper.registerUser(validUser);

        $("[data-test-id=login] input").setValue(validUser.getLogin());
        $("[data-test-id=password] input").setValue(generatePassword()); // другой пароль
        $("button.button").click();

        $("[data-test-id=error-notification]")
                .shouldBe(visible)
                .shouldHave(text("Неверно указан логин или пароль"));
    }
}
