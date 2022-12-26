package clients;

import clients.pojo.Changes;
import clients.pojo.User;
import clients.pojo.Credentials;
import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;

public class UserClient extends Client{
    private static final String PATH_CREATE = "https://stellarburgers.nomoreparties.site/api/auth/register";
    private static final String PATH_LOGIN = "https://stellarburgers.nomoreparties.site/api/auth/login";
    private static final String PATH_DELETE_GET_PATCH_USER = "https://stellarburgers.nomoreparties.site/api/auth/user";
    public ValidatableResponse create (User user){
        return given()
                .spec(getSpec())
                .body(user)
                .when()
                .post(PATH_CREATE)
                .then();
    }
    public ValidatableResponse login(Credentials credentials){
        return given()
                .spec(getSpec())
                .body(credentials)
                .when()
                .post(PATH_LOGIN)
                .then();


    }

    public ValidatableResponse delete(String accessToken){
        return given()
                .spec(getSpec())
                .header("Authorization",accessToken)
                .when()
                .delete(PATH_DELETE_GET_PATCH_USER)
                .then();
    }
    public ValidatableResponse getData(String accessToken){
        return given()
                .spec(getSpec())
                .header("Authorization",accessToken)
                .when()
                .get(PATH_DELETE_GET_PATCH_USER)
                .then();
    }
    public ValidatableResponse changeData(String accessToken, Changes changes){
        return given()
                .spec(getSpec())
                .header("Authorization",accessToken)
                .body(changes)
                .when()
                .patch(PATH_DELETE_GET_PATCH_USER)
                .then();
    }
    public ValidatableResponse changeDataWithoutAuth(Changes changes){
        return given()
                .spec(getSpec())
                .body(changes)
                .when()
                .patch(PATH_DELETE_GET_PATCH_USER)
                .then();
    }

}
