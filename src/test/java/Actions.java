import com.microsoft.playwright.Page;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.nio.file.Paths;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertNotEquals;

@Epic("Epic Name: Actions")
@Feature("Feature Name: Change theme and hover elements")
public class Actions extends Base {

    @Test
    @Feature("Some feature of hover")
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("Test: Change color of elements after hovering")
    @Description("Test description: The color of the text of the element change after hovering the cursor")
    void hoverActions() {
        NavigationHeader navigationHeader = new NavigationHeader(page);

        final String SEARCH_FIELD = "button[aria-label='Search']";
        final String TITLE = ".navbar__title.text--truncate";
        final String DOCS = "a:has-text('Docs')";
        final String API = "a:has-text('API')";
        final String ROLE = " a[role='button']";
        final String COMMUNITY = "a:has-text('Community')";
        final String GET_STARTED = ".getStarted_Sjon";

        navigationHeader
                .navigateTo();

        String[] elements = {SEARCH_FIELD, TITLE, DOCS, API, ROLE, COMMUNITY, GET_STARTED};

        Arrays.stream(elements)
                .forEach(element -> assertNotEquals(navigationHeader.getColorElement(element),
                        navigationHeader.getHoveredColorElement(element),
                        "The color of the text of the element " + element + " did not change after hovering the cursor"));
    }

    @Test
    @Feature("Some feature of theme")
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("Test: Change color of web pages after changing theme")
    @Description("Test description: Theme has be changed")
    void changeTheme() {
        NavigationHeader navigationHeader = new NavigationHeader(page);

        navigationHeader.navigateTo();
        String initialColor = page.evaluate("getComputedStyle(document.body).color").toString();

        navigationHeader.clickLinkByLabel("Switch between dark and light", false);
        String newColor = page.evaluate("getComputedStyle(document.body).color").toString();

        assertNotEquals(initialColor, newColor, "Theme has not changed");
        page.screenshot(new Page.ScreenshotOptions().setPath(Paths.get("screenshot.png")));
    }
}
