import com.microsoft.playwright.Page;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@Epic("Epic Name: Handling links")
@Feature("Feature Name: Use playwright methods for handling links")
public class Links extends Base {

    @Test
    @Feature("Test with handling links")
    @Severity(SeverityLevel.MINOR)
    @DisplayName("Handling links")
    @Description("Test description: Go to different pages and open them in different tabs")
    public void handlingLinks() {
        NavigationHeader navigationHeader = new NavigationHeader(page);
        LinksPage linksPage = new LinksPage(page);

        navigationHeader.navigateTo();

        Page gitHubPage = linksPage.navigateToGitHub();
        Assertions.assertEquals(linksPage.GIT_HUB, gitHubPage.title());

        Page discordPage = linksPage.navigateToDiscord();
        Assertions.assertEquals(linksPage.DISCORD, discordPage.title());

        page.bringToFront();
        page.getByLabel("Search").click();

        Page algoliaPage = linksPage.navigateToAlgolia();
        Assertions.assertEquals(linksPage.ALGOLIA, algoliaPage.title());

        gitHubPage.close();
        discordPage.close();
        algoliaPage.close();

        Assertions.assertEquals(linksPage.INITIAL_PAGE, page.title());
    }
}