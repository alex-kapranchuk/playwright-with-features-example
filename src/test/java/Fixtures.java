import com.microsoft.playwright.Browser;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.junit.Options;
import com.microsoft.playwright.junit.OptionsFactory;
import com.microsoft.playwright.junit.UsePlaywright;
import com.microsoft.playwright.options.AriaRole;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

@Epic("Epic Name: Fixtures")
@Feature("Feature Name: Create custom options during test")
@UsePlaywright(Fixtures.CustomOptions.class)
public class Fixtures {

    @Test
    @Feature("Create new base page for test, and new options")
    @Severity(SeverityLevel.MINOR)
    @DisplayName("Create nex context for test")
    @Description("Test description: Go to gitHub page as a base page")
    public void handlingLinks(Page page) {
        NavigationHeader navigationHeader = new NavigationHeader(page);

        page.navigate("/");

        assertThat(page.getByRole(AriaRole.MAIN)).containsText("Let’s build from here");

        navigationHeader.
                clickLinkByRole(AriaRole.LINK, "Start a free enterprise trial", false);

        assertThat(page.getByRole(AriaRole.MAIN)).containsText("Pick your trial plan");
      }

    public static class CustomOptions implements OptionsFactory {

        @Override
        public Options getOptions() {
            return new Options()
                    .setHeadless(true)
                    .setContextOptions(new Browser.NewContextOptions()
                            .setBaseURL("https://github.com"));
        }
    }
}

