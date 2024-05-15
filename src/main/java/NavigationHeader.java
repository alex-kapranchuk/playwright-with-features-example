import com.microsoft.playwright.ElementHandle;
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

    public NavigationHeader fillByRole(AriaRole role, String name, String value) {
        page.getByRole(role, new Page.GetByRoleOptions().setName(name)).fill(value);
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

    public NavigationHeader clickLinkByText(String text) {
        page.getByText(text).click();
        return this;
    }

    public NavigationHeader hoverElement(String element) {
        page.querySelector(element).hover();
        return this;
    }

    public ElementHandle findElementBySelector(String selector) {
        return page.querySelector(selector);
    }

    public String getComputedColor(ElementHandle element) {
        return element.evaluate("el => getComputedStyle(el).color").toString();
    }

    public String getColorElement(String selector) {
        ElementHandle element = findElementBySelector(selector);
        return getComputedColor(element);
    }

    public String getHoveredColorElement(String selector) {
        ElementHandle element = findElementBySelector(selector);
        element.hover();
        return getComputedColor(element);
    }
}