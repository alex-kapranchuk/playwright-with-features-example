import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class NavigationHeader {

    private final Page page;

    public NavigationHeader(Page page) {
        this.page = page;
    }

    public NavigationHeader navigateTo() {
        page.navigate("http://playwright.dev");
        return this;
    }

    public NavigationHeader clickLinkByRole(AriaRole role, String name, boolean exact) {
        page.getByRole(role, new Page.GetByRoleOptions().setName(name).setExact(exact)).click();
        return this;
    }

    public NavigationHeader assertHeaderTextContains(String selector, String expectedText) {
        assertThat(page.locator(selector)).containsText(expectedText);
        return this;
    }

    public NavigationHeader clickLinkByLabel(String label, boolean exact) {
        page.getByLabel(label, new Page.GetByLabelOptions().setExact(exact)).click();
        return this;
    }

    public NavigationHeader hoverElement(String element) {
        page.querySelector(element).hover();
        return this;
    }
}