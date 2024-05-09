import com.microsoft.playwright.*;
import org.junit.jupiter.api.*;

import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

public class Sidebar {

    static Playwright playwright;
    static Browser browser;

    // New instance for each test method.
    BrowserContext context;
    Page page;

    @BeforeAll
    static void launchBrowser() {
        playwright = Playwright.create();
        browser = playwright.chromium()
                .launch(new BrowserType.LaunchOptions().setHeadless(false).setSlowMo(150));
    }

    @AfterAll
    static void closeBrowser() {
        playwright.close();
    }

    @BeforeEach
    void createContextAndPage() {
        context = browser.newContext();

        // Start tracing before creating / navigating a page.
        context.tracing().start(new Tracing.StartOptions()
                .setScreenshots(true)
                .setSnapshots(true)
                .setSources(true));

        page = context.newPage();
    }

    @Test
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

    @AfterEach
    void closeContext() {
        context.tracing().stop(new Tracing.StopOptions()
                .setPath(Paths.get("trace.zip")));
        context.close();
    }
}