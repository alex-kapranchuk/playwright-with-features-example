import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class SearchField {
    private final Page page;

    public SearchField(Page page) {
        this.page = page;
    }

    public SearchField clickLinkByLabel(String label) {
        page.getByLabel(label).click();
        return this;
    }

    public SearchField clickLinkByLocator(String selector, AriaRole role, String name) {
        page.locator(selector).getByRole(role, new Locator.GetByRoleOptions().setName(name))
                .click();
        return this;
    }


    public SearchField fillByPlaceholder(String placeholder, String fill) {
        page.getByPlaceholder(placeholder).fill(fill);
        return this;
    }

    public SearchField pressByPlaceholder(String placeholder, String press) {
        page.getByPlaceholder(placeholder).press(press);
        return this;
    }


    public SearchField expectedText(String locator, String containsText) {
        assertThat(page.locator(locator)).containsText(containsText);
        return this;
    }
}
