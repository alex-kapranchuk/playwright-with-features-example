import com.microsoft.playwright.Browser;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.junit.Options;
import com.microsoft.playwright.junit.OptionsFactory;
import com.microsoft.playwright.junit.UsePlaywright;
import com.microsoft.playwright.options.AriaRole;
import org.junit.jupiter.api.Disabled;
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

