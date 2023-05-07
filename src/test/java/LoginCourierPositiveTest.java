import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;


@RunWith(Parameterized.class)
public class LoginCourierPositiveTest {
    private Courier courier;
    private CourierClient courierClient;
    private int courierId;

    private String login;
    private String password;
    private String firstName;
    private int statusCode;
    private String jsonKey;

    public LoginCourierPositiveTest(String login, String password, String firstName, int statusCode, String jsonKey) {
        this.login = login;
        this.password = password;
        this.firstName = firstName;
        this.statusCode = statusCode;
        this.jsonKey = jsonKey;
    }
    @Parameterized.Parameters
    public static Object[][]getData(){
        return new Object[][]{
                {"bestlogin", "1234599", "Sergey", 200, "id"},
                {"009965", "parol", "Petr", 200, "id"},
                {"log789", "@#$%^&*()_+", null, 200, "id"}

        };
    }

    @Before
    public void setUp(){
        RestAssured.baseURI = "http://qa-scooter.praktikum-services.ru";
        courier = new Courier(login, password, firstName);
        courierClient = new CourierClient();
    }

    @Test
    @DisplayName("Логин курьера позитивные кейсы")
    public void loginCourier(){
        courierClient.createCourier(courier);
        Response responseLogin = courierClient.loginCourier(courier);
        responseLogin.then().statusCode(statusCode);
        courierId = responseLogin.then().extract().path(jsonKey);
        System.out.println(courierId);
    }

    @After
    public void deleteCourier() {
        if (courierId != 0) {
            courierClient.deleteCourier(courierId);
        }
    }

}
