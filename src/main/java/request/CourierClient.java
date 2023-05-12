package request;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import model.Courier;

import static io.restassured.RestAssured.given;

public class CourierClient {
    private static final String CREATE_COURIER_PATH = "/api/v1/courier";
    private static final String LOGIN_COURIER_PATH = "/api/v1/courier/login";
    private static final String DELETE_COURIER_PATH = "/api/v1/courier/";

   @Step("Запрос на создание курьера")
    public Response createCourier(Courier courier){
        return given()
                .header("Content-type", "application/json")
                .and()
                .body(courier)
                .when()
                .post(CREATE_COURIER_PATH);
    }

    @Step("Запрос на авторизацию курьера")
    public Response loginCourier(Courier courier){
        return given()
                .header("Content-type", "application/json")
                .and()
                .body(courier)
                .when()
                .post(LOGIN_COURIER_PATH);
    }

    @Step("Запрос на удаление курьера")
    public Response deleteCourier(int id){
        return given()
                .delete(DELETE_COURIER_PATH + id);
    }

}
