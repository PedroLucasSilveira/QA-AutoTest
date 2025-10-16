package com.example.ui;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.extension.AfterTestExecutionCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SeleniumCrudTest {

    @LocalServerPort
    private int port;

    private static WebDriver driver;
    private static java.nio.file.Path userDataDir;

    // Screenshot capture is provided by a ServiceLoader-registered extension (ScreenshotOnFailureExtension)

    @BeforeAll
    public static void setupClass() throws Exception {
        // Allow skipping UI tests unless explicitly enabled or browser exists
        String runUi = System.getenv("RUN_UI_TESTS");
        boolean shouldRun = "1".equals(runUi) || "true".equalsIgnoreCase(runUi);
        String chromeBinary = null;
        // common chromium locations
        String[] candidates = {"/usr/bin/chromium", "/usr/bin/chromium-browser", "/usr/bin/google-chrome", "/usr/bin/google-chrome-stable"};
        for (String c : candidates) {
            if (java.nio.file.Files.isExecutable(java.nio.file.Path.of(c))) {
                chromeBinary = c; break;
            }
        }
        Assumptions.assumeTrue(shouldRun || chromeBinary != null, "Skipping UI tests: Chromium/Chrome not available and RUN_UI_TESTS not set");

        ChromeOptions opts = new ChromeOptions();
        if (chromeBinary != null) opts.setBinary(chromeBinary);
        opts.addArguments("--headless=new");
        opts.addArguments("--no-sandbox");
        opts.addArguments("--disable-dev-shm-usage");
        // do not pass a fixed user-data-dir here; some environments (codespaces / CI) may
        // have conflicts if the directory is already in use. Let Chrome use a temporary
        // profile managed by the driver.
        // avoid some environments complaining
        opts.addArguments("--disable-gpu");
        try {
            driver = new ChromeDriver(opts);
        } catch (org.openqa.selenium.SessionNotCreatedException ex) {
            System.err.println("Could not start ChromeDriver: " + ex.getMessage());
            // Skip UI tests when we can't start a browser instance instead of failing the build
            Assumptions.assumeTrue(false, "Skipping UI tests: cannot start ChromeDriver: " + ex.getMessage());
        } catch (Exception ex) {
            System.err.println("Error starting ChromeDriver: " + ex.getMessage());
            Assumptions.assumeTrue(false, "Skipping UI tests: error starting ChromeDriver: " + ex.getMessage());
        }
    }

    @AfterAll
    public static void tearDownClass() {
        if (driver != null) driver.quit();
        if (userDataDir != null) {
            try {
                java.nio.file.Files.walk(userDataDir)
                        .sorted(java.util.Comparator.reverseOrder())
                        .forEach(p -> p.toFile().delete());
            } catch (Exception ignored) {}
        }
    }

    @Test
    public void createAndRead() {
        String base = "http://localhost:" + port + "/users";
    driver.get(base + "/new");
    driver.findElement(By.name("id")).sendKeys("500");
    driver.findElement(By.name("name")).sendKeys("TesteUI");
    driver.findElement(By.name("email")).sendKeys("ui@teste.com");
    driver.findElement(By.cssSelector("form")).submit();

    // after creation, go to detail page
    driver.get(base + "/500");
    String idText = driver.findElement(By.cssSelector("#user-detail p span")).getText();
    String nameText = driver.findElement(By.xpath("//p[starts-with(., 'Name')]/span")).getText();
    String emailText = driver.findElement(By.xpath("//p[starts-with(., 'Email')]/span")).getText();
    assertTrue(nameText.contains("TesteUI"));
    assertTrue(emailText.contains("ui@teste.com"));
        
        // Edit user
        driver.get(base + "/500/edit");
        driver.findElement(By.name("name")).clear();
        driver.findElement(By.name("name")).sendKeys("TesteUIEdited");
        driver.findElement(By.name("email")).clear();
        driver.findElement(By.name("email")).sendKeys("edited@teste.com");
        driver.findElement(By.cssSelector("form")).submit();

        driver.get(base + "/500");
        String nameText2 = driver.findElement(By.xpath("//p[starts-with(., 'Name')]/span")).getText();
        assertTrue(nameText2.contains("TesteUIEdited"));

    // Delete user
    driver.get(base);
    // find delete form for the row with ID 500 and submit it (more reliable than click in some CI envs)
    By deleteForm = By.xpath("//tr[td[normalize-space() = '500']]//form");
    driver.findElement(deleteForm).submit();

    // wait until the row disappears (give the server a short time to process)
    try {
        new org.openqa.selenium.support.ui.WebDriverWait(driver, java.time.Duration.ofSeconds(5))
            .until(org.openqa.selenium.support.ui.ExpectedConditions
                .invisibilityOfElementLocated(By.xpath("//tr[td[normalize-space() = '500']]")));
    } catch (Exception ignored) {}

    // after deletion, list should not contain 500
    driver.get(base);
    String page = driver.findElement(By.tagName("body")).getText();
    assertTrue(!page.contains("500"));
    }
}
