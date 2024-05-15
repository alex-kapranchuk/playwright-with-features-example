import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class DocsPage {

    private final Page page;

    public DocsPage(Page page) {
        this.page = page;
    }

    public DocsPage navigateToDocsPage() {
        page.navigate("http://playwright.dev");
        page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Docs")).click();
        return this;
    }

    public DocsPage clickNodeJsButton() {
        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Node.js")).click();
        return this;
    }
    public DocsPage clickByMain(String name) {
        page.getByLabel("Main", new Page.GetByLabelOptions().setExact(true)).getByRole(AriaRole.LINK, new Locator.GetByRoleOptions().setName(name)).click();
        return this;
    }

    public DocsPage clickJavaLink() {
        page.getByLabel("Main", new Page.GetByLabelOptions().setExact(true)).getByRole(AriaRole.LINK, new Locator.GetByRoleOptions().setName("Java")).click();
        return this;
    }

    public DocsPage clickInstallationLink() {
        page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Installation").setExact(true)).click();
        return this;
    }

    public DocsPage clickWritingTestsLink() {
        page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Writing tests").setExact(true)).click();
        return this;
    }

    public DocsPage clickGeneratingTestsLink() {
        page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Generating tests").setExact(true)).click();
        return this;
    }

    public DocsPage clickRunningAndDebuggingTestsLink() {
        page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Running and debugging tests").setExact(true)).click();
        return this;
    }

    public DocsPage clickTraceViewerLink() {
        page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Trace viewer")).first().click();
        return this;
    }

    public DocsPage clickCIGitHubActionsLink() {
        page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("CI GitHub Actions").setExact(true)).click();
        return this;
    }

    public DocsPage clickTestRunnersLink() {
        page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Test Runners").setExact(true)).click();
        return this;
    }

    public DocsPage clickJUnitExperimentalLink() {
        page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("JUnit (experimental)").setExact(true)).click();
        return this;
    }


    public DocsPage assertPageContent(Page page, String expectedHeader, String expectedSidebarText) {
        assertThat(page.locator("h1").first()).containsText(expectedHeader);
        assertThat(page.getByLabel("Docs sidebar")).containsText(expectedSidebarText);
        return this;
    }


    public DocsPage assertText(String label, String text) {
        assertThat(page.getByLabel(label, new Page.GetByLabelOptions().setExact(true))).containsText(text);
        return this;
    }
}
