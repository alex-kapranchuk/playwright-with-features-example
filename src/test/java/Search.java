import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import com.microsoft.playwright.options.LoadState;
import org.junit.jupiter.api.Test;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class Search extends Base {

    @Test
    public void generalSearch() {
        NavigationHeader navigationHeader = new NavigationHeader(page);
        SearchField searchField = new SearchField(page);

        final String SEARCH_DOCS = "Search docs";
        final String SEARCH = "Search";

        navigationHeader.navigateTo();

        searchField
                .clickLinkByLabel(SEARCH)
                .fillByPlaceholder(SEARCH_DOCS, "keybo")
                .pressByPlaceholder(SEARCH_DOCS, "ArrowDown")
                .pressByPlaceholder(SEARCH_DOCS, "Enter")
                .expectedText("#keyboard-down", "down")
                .clickLinkByLabel(SEARCH);

        navigationHeader
                .clickLinkByRole(AriaRole.LINK, "keyboard", false);

        searchField
                .expectedText("#keyboard-down", "down")
                .clickLinkByLabel(SEARCH)
                .pressByPlaceholder(SEARCH_DOCS, "Escape");

        assertThat(page.getByPlaceholder(SEARCH_DOCS)).isHidden();
    }

    @Test
    public void favoriteSearch() {
        NavigationHeader navigationHeader = new NavigationHeader(page);
        SearchField searchField = new SearchField(page);

        final String SEARCH_DOCS = "Search docs";
        final String SEARCH = "Search";

        navigationHeader.navigateTo();

        searchField
                .clickLinkByLabel(SEARCH)
                .fillByPlaceholder(SEARCH_DOCS, "Actions")
                .pressByPlaceholder(SEARCH_DOCS, "ArrowDown")
                .pressByPlaceholder(SEARCH_DOCS, "Enter")
                .expectedText("#introduction", "Introduction") // First search is not saved to the history
                .clickLinkByLabel(SEARCH)
                .fillByPlaceholder(SEARCH_DOCS, "Mock APIs")
                .pressByPlaceholder(SEARCH_DOCS, "Enter")
                .expectedText("h1", "Mock APIs")
                .clickLinkByLabel(SEARCH)
                .clickLinkByLocator("#docsearch-item-0", AriaRole.BUTTON, "Save this search")
                .pressByPlaceholder(SEARCH_DOCS, "Escape");

        searchField
                .clickLinkByLabel(SEARCH);

        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Remove this search from favorites")).click();

        searchField.
                expectedText("h1", "Mock APIs");

        assertThat(page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Mock APIs Remove this search"))).isHidden();
    }
}
