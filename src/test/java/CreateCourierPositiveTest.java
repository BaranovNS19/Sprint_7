import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import model.Courier;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import request.CourierClient;

import static org.hamcrest.Matchers.equalTo;

@RunWith(Parameterized.class)
public class CreateCourierPositiveTest {
    private Courier courier;
    private CourierClient courierClient;
    private int courierId;
    private String login;
    private String password;
    private String firstName;
    private int statusCode;
    private String jsonKey;
    private boolean expectedResponse;
    private static Logger logger = LogManager.getLogger(CreateCourierPositiveTest.class);

    public CreateCourierPositiveTest(String login, String password, String firstName, int statusCode, String jsonKey, boolean expectedResponse) {
        this.login = login;
        this.password = password;
        this.firstName = firstName;
        this.statusCode = statusCode;
        this.jsonKey = jsonKey;
        this.expectedResponse = expectedResponse;
    }

    @Parameterized.Parameters
    public static Object[][] getData() {
        return new Object[][]{
                {"Bestcourierr", "123", "Victor", 201, "ok", true},
                {"198", "bestCourier", "Nikolay", 201, "ok", true},
                {"Symbols123", "passwodr123!&*%", "Valeriy", 201, "ok", true}
        };
    }

    @Before
    public void setUp() {
        RestAssured.baseURI = "http://qa-scooter.praktikum-services.ru";
        courier = new Courier(login, password, firstName);
        courierClient = new CourierClient();
    }

    @Test
    @DisplayName("Создание курьера позитивные кейсы")
     public void createCourier() {
        Response responseCreate = courierClient.createCourier(courier);
        responseCreate.then().statusCode(statusCode).assertThat().body(jsonKey, equalTo(expectedResponse));


        //Авторизация и получение id
        Response responseLogin = courierClient.loginCourier(courier);
        responseLogin.then().statusCode(200);
        courierId = responseLogin.then().extract().path("id");
       // System.out.println(courierId);
        logger.debug("Id курьера" + courierId);
    }

    @After
    public void deleteCourier() {
        if (courierId != 0) {
            courierClient.deleteCourier(courierId);
        }
    }
}
