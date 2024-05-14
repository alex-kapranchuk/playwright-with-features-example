import com.microsoft.playwright.options.AriaRole;
import org.junit.jupiter.api.Test;

public class Devices extends BaseDevices {

    @Test
    public void emulationDevices() {
        NavigationHeader navigationHeader = new NavigationHeader(page);
        SearchField searchField = new SearchField(page);

        final String SEARCH_DOCS = "Search docs";
        final String SEARCH = "Search";

        navigationHeader.navigateTo()
                .clickLinkByLabel("Toggle navigation bar", false)
                .clickLinkByRole(AriaRole.BUTTON, "Switch between dark and light", false)
                .clickLinkByLabel("Close navigation bar", false);

        searchField.clickLinkByLabel(SEARCH)
                .fillByPlaceholder(SEARCH_DOCS, "emulation")
                .pressByPlaceholder(SEARCH_DOCS, "ArrowDown")
                .pressByPlaceholder(SEARCH_DOCS, "ArrowDown")
                .pressByPlaceholder(SEARCH_DOCS, "Enter")
                .expectedText("#devices", "Devices");

        navigationHeader.clickLinkByRole(AriaRole.LINK, "Next Fixtures »", false)
                .assertHeaderTextContains("h1", "Fixtures");
    }
}