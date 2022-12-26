import clients.Generator;
import clients.UserClient;
import clients.pojo.User;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.apache.http.HttpStatus.SC_BAD_REQUEST;
import static org.apache.http.HttpStatus.SC_FORBIDDEN;
import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class NegativeParametrizedCreationUserTest {
    private User user;
    private UserClient userClient;
    private int statusCode;

    public NegativeParametrizedCreationUserTest(User user, int statusCode) {
        this.user = user;
        this.statusCode = statusCode;
    }
    @Parameterized.Parameters
    public static Object[][] getTestData()
    {
        return new Object[][]
                {
                        {Generator.getWithoutName(), SC_FORBIDDEN},
                        {Generator.getWithoutEmail(), SC_FORBIDDEN},
                        {Generator.getWithoutPassword(), SC_FORBIDDEN},

                };
    }
    @Before
    public void setUp(){
        userClient = new UserClient();
    }

    @Test
    @DisplayName("Check status code for creation users without params")
    public void createUserWithNullParams(){
        ValidatableResponse responseCreate = userClient.create(user);
        int actualStatusCode = responseCreate.extract().statusCode();


        assertEquals(statusCode,actualStatusCode);

    }
}
