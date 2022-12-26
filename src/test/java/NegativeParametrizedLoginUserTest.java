import clients.Generator;
import clients.UserClient;
import clients.pojo.Credentials;
import clients.pojo.User;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.apache.http.HttpStatus.SC_FORBIDDEN;
import static org.apache.http.HttpStatus.SC_UNAUTHORIZED;
import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class NegativeParametrizedLoginUserTest {
    private User user;
    private Credentials credentials;
    private UserClient userClient;
    private int statusCode;

    private String accessToken;

    public NegativeParametrizedLoginUserTest(Credentials credentials, int statusCode) {
        this.credentials = credentials;
        this.statusCode = statusCode;
    }

    @Parameterized.Parameters
    public static Object[][] getTestData() {
        return new Object[][]
                {
                        {Generator.getLoginWithIncorrectEmail(), SC_UNAUTHORIZED},
                        {Generator.getLoginWithIncorrectPassword(), SC_UNAUTHORIZED},

                };
    }

    @Before
    public void setUp() {
        userClient = new UserClient();
        user = Generator.getDefault();
    }

    @Test
    @DisplayName("Check status code for login user without params")
    public void LoginUserWithIncorrectParams() {
        ValidatableResponse responseCreate = userClient.create(user);
        ValidatableResponse responseLogin = userClient.login(credentials);
        accessToken = responseCreate.extract().path("accessToken");
        int actualStatusCode = responseLogin.extract().statusCode();


        assertEquals(statusCode, actualStatusCode);

    }

    @After
    public void cleanUp() {
        userClient.delete(accessToken);
    }
}
