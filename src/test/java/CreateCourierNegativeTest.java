import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import model.Courier;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import request.CourierClient;

import static org.hamcrest.Matchers.equalTo;

@RunWith(Parameterized.class)
public class CreateCourierNegativeTest {
    private Courier courier;
    private CourierClient courierClient;
    private int courierId;
    private String login;
    private String password;
    private String firstName;
    private int statusCode;
    private String jsonKey;
    private String expectedResponse;

    public CreateCourierNegativeTest(String login, String password, String firstName, int statusCode, String jsonKey, String expectedResponse) {
        this.login = login;
        this.password = password;
        this.firstName = firstName;
        this.statusCode = statusCode;
        this.jsonKey = jsonKey;
        this.expectedResponse = expectedResponse;
    }

    @Parameterized.Parameters
    public static Object[][] getData() {
        return new Object[][]{{null, "123", "Victor", 400, "message", "Недостаточно данных для создания учетной записи"}, {"Bestcourier", null, "Victor", 400, "message", "Недостаточно данных для создания учетной записи"}, {null, null, "Victor", 400, "message", "Недостаточно данных для создания учетной записи"}, {null, null, null, 400, "message", "Недостаточно данных для создания учетной записи"}, {"Login19", "3405", "есть такой", 409, "message", "Этот логин уже используется. Попробуйте другой."}, {"Login19", "34052345", "такой же логин", 409, "message", "Этот логин уже используется. Попробуйте другой."}};
    }

    @Before
    public void setUp() {
        RestAssured.baseURI = "http://qa-scooter.praktikum-services.ru";
        courier = new Courier(login, password, firstName);
        courierClient = new CourierClient();
    }

    @Test
    @DisplayName("Создание курьера негативные кейсы")
    public void createCourier() {
        courierClient.createCourier(courier);
        Response responseCreate = courierClient.createCourier(courier);
        responseCreate.then().statusCode(statusCode).assertThat().body(jsonKey, equalTo(expectedResponse));
        //Авторизация и получение id
        if (responseCreate.statusCode() == 201) {
            Response responseLogin = courierClient.loginCourier(courier);
            responseLogin.then().statusCode(200);
            courierId = responseLogin.then().extract().path("id");
            System.out.println(courierId);
        }
    }

    @After
    public void deleteCourier() {
        if (courierId != 0) {
            courierClient.deleteCourier(courierId);
        }
    }

}
