import com.microsoft.playwright.options.AriaRole;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


@Epic("Epic Name: Emulation of devices")
@Feature("Feature Name: Open web pages on devices screen")
public class Devices extends BaseDevices {


    @Test
    @Feature("Some feature of emulation")
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("Navigation and change theme on emulation device")
    @Description("Test description: Navigation and change theme on emulation device and take search")
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
                .fillByPlaceholder(SEARCH_DOCS, "emulation devices")
                .pressByPlaceholder(SEARCH_DOCS, "ArrowDown")
                .pressByPlaceholder(SEARCH_DOCS, "Enter")
                .expectedText("#devices", "Devices");

        navigationHeader.clickLinkByRole(AriaRole.LINK, "Next Fixtures »", false)
                .assertHeaderTextContains("h1", "Fixtures");
    }
}