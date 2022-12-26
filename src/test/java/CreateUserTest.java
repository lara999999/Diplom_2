import clients.Generator;
import clients.UserClient;
import clients.pojo.Credentials;
import clients.pojo.User;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CreateUserTest {
    private User user;
    private UserClient userClient;
    private String accessToken;

    @Before
    public void setUp() {
        userClient = new UserClient();
        user = Generator.getDefault();

    }

    @Test
    @DisplayName("Check status code for creating user ")
    public void userCanBeCreated_True() {
        ValidatableResponse responseCreate = userClient.create(user);
        ValidatableResponse responseLogin = userClient.login(Credentials.from(user));
        accessToken = responseLogin.extract().path("accessToken");
        int statusCode = responseCreate.extract().statusCode();
        assertEquals("User created status code incorrect", 200, statusCode);

    }

    @Test
    @DisplayName("Check status code for creating 2 users with the same data")
    public void createTwoUsersWithTheSameEmail_Error() {
        ValidatableResponse responseCreate = userClient.create(user);
        ValidatableResponse responseCreateSecondUser = userClient.create(user);
        int actualStatusCode = responseCreateSecondUser.extract().statusCode();
        assertEquals("User created, status code 200.", 403, actualStatusCode);

        ValidatableResponse responseLogin = userClient.login(Credentials.from(user));
        accessToken = responseLogin.extract().path("accessToken");

    }

    @After
    public void cleanUp() {
        userClient.delete(accessToken);
    }
}
