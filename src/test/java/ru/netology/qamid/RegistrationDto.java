package ru.netology.qamid;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;

/**
 * Класс-контейнер для пользователя.
 * Используем @Value для неизменяемого класса
 */
@Value
public class RegistrationDto {
    String login;
    String password;
    String status;

}
