import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

public class LinksPage {

    final String GIT_HUB = "GitHub - microsoft/playwright: Playwright is a framework for Web Testing and Automation. It allows testing Chromium, Firefox and WebKit with a single API.";
    final String DISCORD = "Playwright - Discord Servers";
    final String ALGOLIA = "Integrate your tech stack with our libraries | Algolia";
    final String INITIAL_PAGE = "Fast and reliable end-to-end testing for modern web apps | Playwright";

    final String GITHUB_LABEL = "GitHub repository";
    final String DISCORD_LABEL = "Discord server";
    final String ALGOLIA_LABEL = "Search by Algolia";

    private final Page page;

    public LinksPage(Page page) {
        this.page = page;
    }

    public Page navigateToLinkAndReturnPopup(String linkLabel) {
        return page.waitForPopup(() ->
                page.getByLabel(linkLabel).click()
        );
    }

    public Page navigateToRoleAndReturnPopup(AriaRole role,String linkLabel) {
        return page.waitForPopup(() ->
                page.getByRole(role, new Page.GetByRoleOptions().setName(linkLabel)).click()
        );
    }

    public Page navigateToGitHub() {
        return navigateToLinkAndReturnPopup(GITHUB_LABEL);
    }

    public Page navigateToDiscord() {
        return navigateToLinkAndReturnPopup(DISCORD_LABEL);
    }

    public Page navigateToAlgolia() {
        Page algoliaPage = navigateToRoleAndReturnPopup(AriaRole.LINK,ALGOLIA_LABEL);
        if ("Vercel Security Checkpoint".equals(algoliaPage.title())) {
            System.out.println("Waiting for page to load...");
            algoliaPage.waitForFunction("(expectedTitle) => document.title === expectedTitle", ALGOLIA, new Page.WaitForFunctionOptions().setTimeout(60000));
        }
        return algoliaPage;
    }
}