import com.microsoft.playwright.Browser;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.junit.Options;
import com.microsoft.playwright.junit.OptionsFactory;
import com.microsoft.playwright.junit.UsePlaywright;
import com.microsoft.playwright.options.AriaRole;
import org.junit.jupiter.api.Test;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

@UsePlaywright(Fixtures.CustomOptions.class)
public class Fixtures {

    @Test
    public void handlingLinks(Page page) {
        NavigationHeader navigationHeader = new NavigationHeader(page);

        page.navigate("/");

        assertThat(page.getByRole(AriaRole.MAIN)).containsText("Let’s build from here");

        navigationHeader.
                clickLinkByRole(AriaRole.BUTTON, "Search or jump to...", false)
                .fillByRole(AriaRole.COMBOBOX, "Search", "")
                .clickLinkByText("Copilot Learn More")
                .clickLinkByRole(AriaRole.LINK, "Get started with Copilot", false);

        assertThat(page.getByTestId("Grid-:R5b:").locator("h2")).containsText("Take flight with GitHub Copilot.");
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

