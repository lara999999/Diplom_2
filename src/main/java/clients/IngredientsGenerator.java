package clients;

import clients.pojo.Ingredients;
import org.apache.commons.lang3.RandomStringUtils;

import java.util.List;

public class IngredientsGenerator {
    public static Ingredients getDefault() {
        return new Ingredients(List.of("61c0c5a71d1f82001bdaaa6d", "61c0c5a71d1f82001bdaaa6f"));
    }

    public static Ingredients getIncorrect() {
        return new Ingredients(List.of(RandomStringUtils.randomAlphabetic(7)));
    }

    public static Ingredients getEmpty() {
        return new Ingredients(List.of());

    }
}
