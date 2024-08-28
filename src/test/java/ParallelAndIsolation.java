import com.microsoft.playwright.*;
import com.microsoft.playwright.junit.Options;
import com.microsoft.playwright.junit.OptionsFactory;
import com.microsoft.playwright.junit.UsePlaywright;
import com.microsoft.playwright.options.AriaRole;
import com.microsoft.playwright.options.LoadState;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

@Epic("Epic Name: Parallel and isolation")
@Feature("Feature Name: Parallel and isolation tests")
@Execution(ExecutionMode.CONCURRENT)
@UsePlaywright(ParallelAndIsolation.CustomOptions.class)
public class ParallelAndIsolation {

    @Test
    @Feature("Tests should running parallel")
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("Test: Simple navigation on site")
    @Description("Test description: Go throw site and check elements")
    public void goToParallel(Page page) {
        NavigationHeader navigationHeader = new NavigationHeader(page);
        DocsPage docsPage = new DocsPage(page);

        navigationHeader
                .navigateTo()
                .hoverElement("a[role='button']");
        docsPage.clickByMain("Java");
        navigationHeader
                .clickLinkByRole(AriaRole.LINK, "Docs", false)
                .clickLinkByRole(AriaRole.LINK, "JUnit (experimental)", false);

        assertThat(page.locator("#running-tests-in-parallel")).containsText("Running Tests in Parallel");
    }

    @Test
    @Feature("Tests should running parallel")
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("Test:Contex for test in test class")
    @Description("Test description: Create new contex for test in test class and parallel running")
    public void twoContexts() {
        try (Playwright playwright = Playwright.create()) {
            BrowserType firefox = playwright.chromium();
            Browser browser = firefox.launch();


            BrowserContext userContext = browser.newContext();
            Page userPage = userContext.newPage();
            userPage.navigate("http://playwright.dev");

            userPage.waitForLoadState(LoadState.NETWORKIDLE);
            Assertions.assertEquals("Fast and reliable end-to-end testing for modern web apps | Playwright", userPage.title());

            BrowserContext adminContext = browser.newContext();
            Page adminPage = adminContext.newPage();
            adminPage.navigate("https://github.com/");

            Assertions.assertEquals("GitHub: Let’s build from here · GitHub", adminPage.title());
        }
    }

    public static class CustomOptions implements OptionsFactory {

        @Override
        public Options getOptions() {
            return new Options()
                    .setHeadless(true);
        }
    }
}
