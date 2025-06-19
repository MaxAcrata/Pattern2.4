package ru.netology.qamid;

import com.github.javafaker.Faker;
import org.apache.commons.text.CharacterPredicates;
import org.apache.commons.text.RandomStringGenerator;


/**
 * Класс-генератор для тестовых пользователей.
 */
public class DataGenerator {
    private static final Faker faker = new Faker();

    public static int passwordLength = 10; // длина пароля

    /**
     * Генерирует случайный логин (только латинские символы и цифры, в нижнем регистре).
     *
     * @return логин пользователя
     */
    public static String generateLogin() {
        return faker.name().username().toLowerCase().replaceAll("[^a-z0-9]", "");
    }

    public static String generatePassword() {
        RandomStringGenerator generator = new RandomStringGenerator.Builder()
                .withinRange('!', '~') // все печатаемые символы ASCII
                .filteredBy(CharacterPredicates.DIGITS,
                        CharacterPredicates.LETTERS,
                        (int ch) -> ch >= 33 && ch <= 47 || ch >= 58 && ch <= 64) // спецсимволы
                .build();

        return generator.generate(passwordLength);
    }

    /**
     * Генерирует пользователя с заданным статусом ("active" или "blocked").
     *
     * @param status статус пользователя
     * @return объект пользователя (логин, пароль, статус)
     */
    public static RegistrationDto generateUser(String status) {
        return new RegistrationDto(
                generateLogin(),
                generatePassword(),
                status
        );
    }
}
