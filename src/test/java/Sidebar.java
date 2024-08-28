import com.microsoft.playwright.ElementHandle;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Collectors;

@Epic("Epic Name: Check side bar")
@Feature("Feature Name: Navigation and search")
public class Sidebar extends Base {

    @Test
    @Feature("Check sidebar elements")
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("Test: Take sidebar elements")
    @Description("Test description: Compare two lists of element that present in elements list but is not on site")
    void guidesABCSort() {
        DocsPage docsPage = new DocsPage(page);
        docsPage.navigateToDocsPage()
                .clickNodeJsButton();

        List<ElementHandle> guidesLibrary = page.querySelectorAll(".theme-doc-sidebar-item-link a");

        List<String> listOfGuides = guidesLibrary.stream()
                .map(ElementHandle::innerText)
                .dropWhile(element -> !element.equals("API testing"))
                .takeWhile(element -> !element.equals("Supported languages"))
                .filter(element -> !element.equals("Visual comparisons")) // Element that present in elements list but is not on site
                .collect(Collectors.toList());

        Assertions.assertEquals(listOfGuides, listOfGuides.stream().sorted().toList());
    }

    @Test
    @Feature("Navigation via sidebar")
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("Test: Navigation via sidebar")
    @Description("Test description: Navigation via sidebar and check elements")
    void checkTitlesGettingStarted() {
        DocsPage docsPage = new DocsPage(page);
        docsPage.navigateToDocsPage()
                .clickNodeJsButton()
                .clickJavaLink()
                .assertText("Main", "Playwright for Java")
                .clickInstallationLink()
                .assertPageContent(page, "Installation", "Installation")
                .clickWritingTestsLink()
                .assertPageContent(page, "Writing tests", "Writing tests")
                .clickGeneratingTestsLink()
                .assertPageContent(page, "Generating tests", "Generating tests")
                .clickRunningAndDebuggingTestsLink()
                .assertPageContent(page, "Running and debugging tests", "Running and debugging tests")
                .clickTraceViewerLink()
                .assertPageContent(page, "Trace viewer", "Trace viewer")
                .clickCIGitHubActionsLink()
                .assertPageContent(page, "CI GitHub Actions", "CI GitHub Actions")
                .clickTestRunnersLink()
                .assertPageContent(page, "Test Runners", "Test Runners")
                .clickJUnitExperimentalLink()
                .assertPageContent(page, "JUnit (experimental)", "JUnit (experimental)");
    }
}