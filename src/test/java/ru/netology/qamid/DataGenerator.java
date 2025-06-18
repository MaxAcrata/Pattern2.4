package ru.netology.qamid;

import com.github.javafaker.Faker;

import java.util.Locale;

/**
 * Класс-генератор для тестовых пользователей.
 */
public class DataGenerator {
    private static final Faker faker = new Faker();
    
public static String generatePassword() {
        RandomStringGenerator generator = new RandomStringGenerator.Builder()
                .withinRange('!', '~') // все печатаемые символы ASCII
                .filteredBy(CharacterPredicates.DIGITS,
                            CharacterPredicates.ALPHABETIC,
                            (int ch) -> ch >= 33 && ch <= 47 || ch >= 58 && ch <= 64) // спецсимволы
                .build();

        return generator.generate(10); // длина пароля
    }
    /**
     * Генерирует пользователя с заданным статусом (например, "active", "blocked").
     *
     * @param status статус пользователя
     * @return объект RegistrationDto с логином, паролем и статусом
     */
    public static RegistrationDto generateUser(String status) {
        return new RegistrationDto(
                faker.name().username().toLowerCase(), // логин
                generatePassword(),                          // стабильный пароль
                status
        );
    }
}
