import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class CourierClient {
    private static final String CREATE_COURIER_PATH = "/api/v1/courier";
    private static final String LOGIN_COURIER_PATH = "/api/v1/courier/login";
    private static final String DELETE_COURIER_PATH = "/api/v1/courier/";

    public Response createCourier(Courier courier){
        return given()
                .header("Content-type", "application/json")
                .and()
                .body(courier)
                .when()
                .post(CREATE_COURIER_PATH);
    }

    public Response loginCourier(Courier courier){
        return given()
                .header("Content-type", "application/json")
                .and()
                .body(courier)
                .when()
                .post(LOGIN_COURIER_PATH);
    }

    public Response deleteCourier(int id){
        return given()
                .delete(DELETE_COURIER_PATH + id);
    }

}
