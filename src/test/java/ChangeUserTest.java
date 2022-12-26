import clients.Generator;
import clients.UserClient;
import clients.pojo.Credentials;
import clients.pojo.User;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static clients.Generator.changeData;
import static org.junit.Assert.*;

public class ChangeUserTest {
    private User user;
    private UserClient userClient;
    private String accessToken;

    @Before
    public void setUp() {
        userClient = new UserClient();
        user = Generator.getDefault();
    }

    @Test
    @DisplayName("Check status code for changing user without auth")
    public void userChangeNameAndEmailWithoutAuth_False() {
        ValidatableResponse responseCreate = userClient.create(user);
        accessToken = responseCreate.extract().path("accessToken");
        ValidatableResponse responseChangeWithoutAuth = userClient.changeDataWithoutAuth(Generator.changeData());
        int statusCode = responseChangeWithoutAuth.extract().statusCode();
        boolean isUserChangeData = responseChangeWithoutAuth.extract().path("success");
        String expectedMessage=responseChangeWithoutAuth.extract().path("message");;

        assertEquals("User login status code incorrect", 401, statusCode);
        assertFalse("Expected false", isUserChangeData);
        assertEquals("You should be authorised",expectedMessage);
    }

    @Test
    @DisplayName("Check status code for changing user with auth")
    public void userChangeNameAndEmailWithAuth_True() {
        ValidatableResponse responseCreate = userClient.create(user);
        accessToken = responseCreate.extract().path("accessToken");
        ValidatableResponse responseChangeWithAuth = userClient.changeData(accessToken,Generator.changeData());
        int statusCode = responseChangeWithAuth.extract().statusCode();
        boolean isUserChangeData = responseChangeWithAuth.extract().path("success");
        String changedEmail= responseChangeWithAuth.extract().path("user.email");
        String changedName= responseChangeWithAuth.extract().path("user.name");


        assertEquals("User login status code incorrect", 200, statusCode);
        assertTrue("Expected true", isUserChangeData);
        assertEquals("toxxic@mail.ru",changedEmail);
        assertEquals("toxxic1990",changedName);
    }

    @After
    public void cleanUp() {
        userClient.delete(accessToken);
    }
}
