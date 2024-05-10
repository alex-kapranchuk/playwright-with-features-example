import com.microsoft.playwright.options.AriaRole;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class NavigationBar extends Base {

    @Test
    void navigationBar() {
        NavigationHeader navigationHeader = new NavigationHeader(page);

        navigationHeader.navigateTo()
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
    @CsvSource({
            "Python, Playwright for Python",
            "Java, Playwright for Java",
            ".NET, Playwright for .NET"
    })
    void dropDownBar(String name, String expectedHeaderText) {
        DocsPage docsPage = new DocsPage(page);
        NavigationHeader navigationHeader = new NavigationHeader(page);

        navigationHeader
                .navigateTo()
                .clickLinkByRole(AriaRole.LINK, "API", true)
                .hoverElement("a[role='button']")
                .clickLinkByRole(AriaRole.LINK, name, false)
                .assertHeaderTextContains("b", expectedHeaderText);
        docsPage.assertText("Main", name);
    }
}