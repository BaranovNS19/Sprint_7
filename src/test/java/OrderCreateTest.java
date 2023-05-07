import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import java.util.List;

@RunWith(Parameterized.class)
public class OrderCreateTest {
    private String firstName;
    private String lastName;
    private String address;
    private int metroStation;
    private String phone;
    private int rentTime;
    private String deliveryDate;
    private String comment;
    private List<String> color;
    private Order order;
    private OrderClient orderClient;
    private int orderTrack;

    public OrderCreateTest(String firstName, String lastName, String address, int metroStation, String phone, int rentTime, String deliveryDate, String comment, List<String> color) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.metroStation = metroStation;
        this.phone = phone;
        this.rentTime = rentTime;
        this.deliveryDate = deliveryDate;
        this.comment = comment;
        this.color = color;
    }

    @Parameterized.Parameters
    public static Object[][]getData(){
        return new Object[][]{
                {"Сергей", "Иванов", "Москва", 10, "+79803456892", 3, "2023-05-09", "комент", List.of("GREY")},
                {"Николай", "Смирнов", "Москва, Тверская 1", 23, "+79803446892", 1, "2023-05-09", "комент2", List.of("BLACK")},
                {"Евгений", "Большаков", "Москва, Тверская 1", 27, "+79803446892", 1, "2023-05-09", null, List.of("BLACK, GREY")},
                {"Виктор", "Сергеев", "Москва, Тверская 1", 45, "+79803446892", 1, "2023-05-09", "без цвета", null}
        };
    }

    @Before
    public void setUp(){
        RestAssured.baseURI = "http://qa-scooter.praktikum-services.ru";
        order = new Order(firstName, lastName, address, metroStation, phone, rentTime, deliveryDate, comment, color);
        orderClient = new OrderClient();

    }

    @Test
    @DisplayName("Создание заказа")
    public void createOrder(){
        Response responseOrder = orderClient.createOrder(order);
        responseOrder.then().statusCode(201);
        orderTrack = responseOrder.then().extract().path("track");
        System.out.println(orderTrack);
    }
    @After
    public void cancelOrder(){
        orderClient.cancelOrder(orderTrack);
    }
}
