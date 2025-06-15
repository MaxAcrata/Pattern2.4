package ru.netology.qamid;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.parsing.Parser;
import io.restassured.specification.RequestSpecification;
import static io.restassured.RestAssured.*;

public class ApiHelper {

    private static final String BASE_URI = "http://localhost:9999";
    private static final int PORT = 9999;

    // Настройки спецификации запроса
    private static final RequestSpecification requestSpec = new RequestSpecBuilder()
            .setBaseUri(BASE_URI)
            .setPort(PORT)
            .setContentType(ContentType.JSON)
            .build();

    static {
        defaultParser = Parser.JSON;
    }

    /**
     * Отправляет POST-запрос для регистрации пользователя.
     *
     * @param user объект с данными регистрации
     */
    public static void registerUser(RegistrationDto user) {
        given()
                .spec(requestSpec)
                .body(user)
                .when()
                .post("/api/system/users")
                .then()
                .statusCode(200) // Статус, можно адаптировать
                .extract()
                .response();
    }
}
