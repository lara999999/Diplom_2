import clients.Generator;
import clients.IngredientClient;
import clients.IngredientsGenerator;
import clients.UserClient;
import clients.pojo.Ingredients;
import clients.pojo.User;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.apache.http.HttpStatus.*;
import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class CreateOrderTest {
    private User user;
    private UserClient userClient;
    private IngredientClient ingredientClient;
    private Ingredients ingredients;
    private String accessToken;
    private int statusCode;

    public CreateOrderTest(Ingredients ingredients, int statusCode) {
        this.ingredients = ingredients;
        this.statusCode = statusCode;
    }
    @Parameterized.Parameters
    public static Object[][] getTestData() {
        return new Object[][]
                {
                        {IngredientsGenerator.getDefault(), SC_OK},
                        {IngredientsGenerator.getEmpty(), SC_BAD_REQUEST},
                        {IngredientsGenerator.getIncorrect(), SC_INTERNAL_SERVER_ERROR},

                };
    }
    @Before
    public void setUp() {
        ingredientClient = new IngredientClient();
        userClient = new UserClient();
        user = Generator.getDefault();
    }

    @Test
    @DisplayName("Check status code for creation orders with auth")
    @Description("1. Test with valid values; 2. Test with invalid values, 3. Test with empty list")
    public void createOrdersWithAuth() {
        ValidatableResponse responseCreate = userClient.create(user);
        accessToken = responseCreate.extract().path("accessToken");
        ValidatableResponse responseCreateOrder = ingredientClient.createOrder(ingredients,accessToken);
        int actualStatusCode = responseCreateOrder.extract().statusCode();
        assertEquals(statusCode, actualStatusCode);
    }
    @Test
    @DisplayName("Check status code for creation orders without auth")
    @Description("1. Test with valid values; 2. Test with invalid values, 3. Test with empty list")
    public void createOrdersWithoutAuth() {
        ValidatableResponse responseCreate = userClient.create(user);
        accessToken = responseCreate.extract().path("accessToken");
        ValidatableResponse responseCreateOrder = ingredientClient.createOrderWithoutAuth(ingredients);
        int actualStatusCode = responseCreateOrder.extract().statusCode();
        assertEquals(statusCode, actualStatusCode);
    }

    @After
    public void cleanUp() {
        userClient.delete(accessToken);
    }
}
