import com.microsoft.playwright.options.AriaRole;
import com.microsoft.playwright.options.LoadState;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

@Epic("Epic Name: Navigation")
@Feature("Feature Name: Mock Data and add new data")
public class Navigation extends Base {

    @Test
    @Feature("Navigation via site menu")
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("Test: Navigation")
    @Description("Test description: Go throw site menu and check elements")
    void navigationBar() {
        NavigationHeader navigationHeader = new NavigationHeader(page);
        page.waitForLoadState(LoadState.NETWORKIDLE);

        navigationHeader
                .navigateTo()
                .clickLinkByRole(AriaRole.LINK, "Docs", false)
                .clickLinkByRole(AriaRole.HEADING, "Installation", false)
                .assertHeaderTextContains("h1", "Installation")
                .clickLinkByRole(AriaRole.LINK, "API", true)
                .assertHeaderTextContains("h1", "Playwright Library")
                .clickLinkByRole(AriaRole.LINK, "Community", false)
                .assertHeaderTextContains("h1", "Welcome")
                .clickLinkByLabel("Main", true);
    }

    @ParameterizedTest
    @Feature("Check drop down")
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("Test: DropDown via ParameterizedTest")
    @Description("Test description: Go throw drop down and check elements")
    @CsvSource({
            "Python, Playwright for Python",
            "Java, Playwright for Java",
            ".NET, Playwright for .NET"
    })
    void dropDownBar(String name, String expectedHeaderText) {
        DocsPage docsPage = new DocsPage(page);
        NavigationHeader navigationHeader = new NavigationHeader(page);

        navigationHeader
                .navigateTo();

        page.waitForLoadState(LoadState.NETWORKIDLE);

        navigationHeader
                .clickLinkByRole(AriaRole.LINK, "API", true)
                .hoverElement("a[role='button']")
                .clickLinkByRole(AriaRole.LINK, name, false)
                .assertHeaderTextContains("b", expectedHeaderText);
        docsPage.assertText("Main", name);
    }
}