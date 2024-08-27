import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import com.microsoft.playwright.APIResponse;
import com.microsoft.playwright.Route;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertTrue;


public class Mock extends Base {

    @Disabled
    @Test
    public void mockData() {
        page.route("**/api/v1/fruits", route -> {
            route.fulfill(new Route.FulfillOptions()
                    .setBody("[{\"name\": \"Coconut\", \"id\": 21}]")
                    .setContentType("application/json"));
        });
        page.navigate("https://demo.playwright.dev/api-mocking");

        assertTrue(page.waitForSelector("text=Coconut").isVisible());
    }

    @Disabled
    @Test
    public void addMockData() {
        page.route("**/api/v1/fruits", route -> {
            APIResponse response = route.fetch();
            String existingDataString = response.text();
            Gson gson = new Gson();
            JsonArray existingData = new JsonArray();

            try {
                if (!existingDataString.isEmpty()) {
                    existingData = gson.fromJson(existingDataString, JsonArray.class);
                }
            } catch (JsonSyntaxException e) {
                System.err.println("Error parsing existing data:" + e.getMessage());
            }

            JsonObject newItem = new JsonObject();
            newItem.addProperty("name", "Coconut");
            newItem.addProperty("id", 9);

            existingData.add(newItem);

            String combinedData = gson.toJson(existingData);

            route.fulfill(new Route.FulfillOptions()
                    .setBody(combinedData)
                    .setContentType("application/json"));
        });
        page.navigate("https://demo.playwright.dev/api-mocking");

        java.util.List<String> expectedItems = Arrays.asList(
                "Strawberry", "Banana", "Tomato", "Pear", "Blackberry", "Kiwi",
                "Pineapple", "Passionfruit", "Orange", "Raspberry", "Watermelon",
                "Lemon", "Mango", "Blueberry", "Apple", "Melon", "Lime", "Coconut"
        );

        for (String item : expectedItems) {
            assertTrue(page.waitForSelector("text=" + item).isVisible(), "Element with text " + item + " is not visible.");
        }
    }
}
