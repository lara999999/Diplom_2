import clients.Generator;
import clients.UserClient;
import clients.pojo.Credentials;
import clients.pojo.User;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class LoginUserTest {
    private User user;
    private UserClient userClient;
    private String accessToken;

    @Before
    public void setUp() {
        userClient = new UserClient();
        user = Generator.getDefault();

    }

    @Test
    @DisplayName("Check status code for login user")
    public void userCanLogin_True() {
        ValidatableResponse responseCreate = userClient.create(user);
        ValidatableResponse responseLogin = userClient.login(Credentials.from(user));
        accessToken = responseLogin.extract().path("accessToken");
        int statusCode= responseLogin.extract().statusCode();
        String changedEmail=responseLogin.extract().path("email");
        assertEquals("User login status code incorrect", 200, statusCode);

    }
    @After
    public void cleanUp() {
        userClient.delete(accessToken);
    }
}
