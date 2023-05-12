package request;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import model.Order;

import static io.restassured.RestAssured.given;

public class OrderClient {
    private static final String CREATE_ORDER_PATH = "/api/v1/orders";
    private static final String CANCEL_ORDER_PATH = "/api/v1/orders/cancel";
    private static final String LIST_ORDER_PATH = "/api/v1/orders";

    @Step("Запрос на создание заказа")
    public Response createOrder(Order order){
        return given()
                .header("Content-type", "application/json")
                .and()
                .body(order)
                .when()
                .post(CREATE_ORDER_PATH);

    }

   @Step("Запрос на отмену заказа")
    public Response cancelOrder(int track){
        return given()
                .header("Content-type", "application/json")
                .and()
                .body(track)
                .when()
                .put(CANCEL_ORDER_PATH);
    }

    @Step("Запрос на получение списка заказов")
    public Response getListOrder(){
        return given()
                .get(LIST_ORDER_PATH);
    }
}
