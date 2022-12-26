package clients;

import clients.pojo.Ingredients;
import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;

public class IngredientClient extends Client {
    private static final String PATH_CREATE_ORDER = " https://stellarburgers.nomoreparties.site/api/orders";
    private static final String PATH_GET_ORDER = "https://stellarburgers.nomoreparties.site/api/orders";

    public ValidatableResponse createOrder(Ingredients ingredients,String accessToken) {
        return given()
                .spec(getSpec())
                .body(ingredients)
                .header("Authorization",accessToken)
                .when()
                .post(PATH_CREATE_ORDER)
                .then();
    }
    public ValidatableResponse createOrderWithoutAuth(Ingredients ingredients) {
        return given()
                .spec(getSpec())
                .body(ingredients)
                .when()
                .post(PATH_CREATE_ORDER)
                .then();
    }
    public ValidatableResponse getOrderWithoutAuth() {
        return given()
                .spec(getSpec())
                .when()
                .get(PATH_GET_ORDER)
                .then();
    }
    public ValidatableResponse getOrderWithAuth(String accessToken) {
        return given()
                .spec(getSpec())
                .header("Authorization",accessToken)
                .when()
                .get(PATH_GET_ORDER)
                .then();
    }
}
