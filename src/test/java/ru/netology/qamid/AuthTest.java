package ru.netology.qamid;

import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;

/**
 * Тест на авторизацию зарегистрированного пользователя.
 */
public class AuthTest {

    @BeforeAll
    static void setupAll() {
        Configuration.baseUrl = "http://localhost:9999";
        //Configuration.headless = true; //  false, для того чтобы видеть браузер
        Configuration.browserSize = "1920x1080";
    }

    @BeforeEach
    void openPage() {
        open("/");
    }

    @Test
    void loginSuccessRegisteredUser() {
        // Генерируем и регистрируем пользователя через API
        RegistrationDto user = DataGenerator.generateUser("active");
        ApiHelper.registerUser(user);

        // Заполняем форму входа в UI
        $("[data-test-id=login] input").setValue(user.getLogin());
        $("[data-test-id=password] input").setValue(user.getPassword());
        $("button.button").click();

        // Проверяем успешную авторизацию (заголовок/сообщение/редирект)
        $(".heading").shouldBe(visible).shouldHave(text("Личный кабинет"));
    }

    @Test
    void loginWithBlockedUser() {
        // Генерация и регистрация пользователя со статусом "blocked"
        RegistrationDto user = DataGenerator.generateUser("blocked");
        ApiHelper.registerUser(user);

        // Попытка авторизации
        $("[data-test-id=login] input").setValue(user.getLogin());
        $("[data-test-id=password] input").setValue(user.getPassword());
        $("button.button").click();

        // Проверка сообщения об ошибке
        $("[data-test-id=error-notification]")
                .shouldBe(visible)
                .shouldHave(text("Пользователь заблокирован"));
    }

    @Test
    void loginWithInvalidLogin() {
        // Регистрируем корректного пользователя
        RegistrationDto validUser = DataGenerator.generateUser("active");
        ApiHelper.registerUser(validUser);

        // Вводим несуществующий логин
        $("[data-test-id=login] input").setValue("wrongLogin123");
        $("[data-test-id=password] input").setValue(validUser.getPassword());
        $("button.button").click();

        // Проверка сообщения об ошибке
        $("[data-test-id=error-notification]")
                .shouldBe(visible)
                .shouldHave(text("Неверно указан логин или пароль"));
    }

    @Test
    void loginWithInvalidPassword() {
        // Регистрируем корректного пользователя
        RegistrationDto validUser = DataGenerator.generateUser("active");
        ApiHelper.registerUser(validUser);

        // Вводим неверный пароль
        $("[data-test-id=login] input").setValue(validUser.getLogin());
        $("[data-test-id=password] input").setValue("WrongPassword123!");
        $("button.button").click();

        // Проверка сообщения об ошибке
        $("[data-test-id=error-notification]")
                .shouldBe(visible)
                .shouldHave(text("Неверно указан логин или пароль"));
    }


}
