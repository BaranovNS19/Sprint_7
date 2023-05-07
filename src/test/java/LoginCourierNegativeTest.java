import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.hamcrest.Matchers.equalTo;

@RunWith(Parameterized.class)
public class LoginCourierNegativeTest {
    private Courier courier;
    private CourierClient courierClient;


    private String login;
    private String password;
    private String firstName;
    private int statusCode;
    private String jsonKey;
    private String expectedResponse;

    public LoginCourierNegativeTest(String login, String password, String firstName, int statusCode, String jsonKey, String expectedResponse) {
        this.login = login;
        this.password = password;
        this.firstName = firstName;
        this.statusCode = statusCode;
        this.jsonKey = jsonKey;
        this.expectedResponse = expectedResponse;
    }
    @Parameterized.Parameters
    public static Object[][]getData(){
        return new Object[][]{
                {null, "1234599", "Sergey", 400, "message", "Недостаточно данных для входа"},
                {"log0708", null, "Sergey", 400, "message", "Недостаточно данных для входа"},
                {"  ", "  ", "Sergey", 404, "message", "Учетная запись не найдена"}

        };
    }

    @Before
    public void setUp(){
        RestAssured.baseURI = "http://qa-scooter.praktikum-services.ru";
        courier = new Courier(login, password, firstName);
        courierClient = new CourierClient();
    }

    @Test
    @DisplayName("Логин курьера негативные кейсы")
    public void loginCourier(){
        Response responseLogin = courierClient.loginCourier(courier);
        responseLogin.then().assertThat().body(jsonKey, equalTo(expectedResponse))
                .and()
                .statusCode(statusCode);
    }
}
