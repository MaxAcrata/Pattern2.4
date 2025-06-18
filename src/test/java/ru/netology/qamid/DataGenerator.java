package ru.netology.qamid;

import com.github.javafaker.Faker;

import java.util.Locale;

/**
 * Класс-генератор для тестовых пользователей.
 */
public class DataGenerator {
    private static final Faker faker = new Faker();

    /**
     * Генерирует пользователя с заданным статусом (например, "active", "blocked").
     *
     * @param status статус пользователя
     * @return объект RegistrationDto с логином, паролем и статусом
     */
    public static RegistrationDto generateUser(String status) {
        return new RegistrationDto(
                faker.name().username().toLowerCase(), // логин
                "Qwerty123!",                          // стабильный пароль
                status
        );
    }
}
