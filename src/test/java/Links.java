import com.microsoft.playwright.Page;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

public class Links extends Base {

    @Disabled
    @Test
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