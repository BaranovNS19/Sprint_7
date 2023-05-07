import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;



public class GetListOrdersTest {
    private OrderClient orderClient;
    @Before
    public void setUp(){
        RestAssured.baseURI = "http://qa-scooter.praktikum-services.ru";
        orderClient = new OrderClient();
    }


    @Test
    @DisplayName("Получить список заказов")
    public void getOrders(){
        Response response = orderClient.getListOrder();
        response.then().extract().path("orders");
                response.then().and().statusCode(200);


    }
}
