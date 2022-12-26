import clients.Generator;
import clients.IngredientClient;
import clients.IngredientsGenerator;
import clients.UserClient;
import clients.pojo.Ingredients;
import clients.pojo.User;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class GetOrderTest {
    private User user;
    private UserClient userClient;
    private IngredientClient ingredientClient;
    private Ingredients ingredients;
    private String accessToken;
    private ArrayList<String> orders;

    @Before
    public void setUp() {
        ingredientClient = new IngredientClient();
        userClient = new UserClient();
        user = Generator.getDefault();
        ingredients= IngredientsGenerator.getDefault();
    }
    @Test
    @DisplayName("Check status code for getting orders without auth")
    public void getOrderWithoutAuth_False(){
        ValidatableResponse responseCreate = userClient.create(user);
        accessToken = responseCreate.extract().path("accessToken");
       ValidatableResponse responseCreateOrder = ingredientClient.createOrder(ingredients,accessToken);
        ValidatableResponse responseGetOrder = ingredientClient.getOrderWithoutAuth();
        int actualStatusCode = responseGetOrder.extract().statusCode();
        String message=responseGetOrder.extract().path("message");
        assertEquals(401, actualStatusCode);
        assertEquals("You should be authorised",message);
    }
    @Test
    @DisplayName("Check status code for getting orders with auth")
    public void getOrderWithAuth_True(){
        ValidatableResponse responseCreate = userClient.create(user);
        accessToken = responseCreate.extract().path("accessToken");
        ValidatableResponse responseCreateOrder = ingredientClient.createOrder(ingredients,accessToken);
        ValidatableResponse responseGetOrder = ingredientClient.getOrderWithAuth(accessToken);
        int actualStatusCode = responseGetOrder.extract().statusCode();
        orders=responseGetOrder.extract().path("orders");
        assertEquals(200, actualStatusCode);
        assertEquals(false, orders.isEmpty());
    }
    @After
    public void cleanUp() {
        userClient.delete(accessToken);
    }
}
