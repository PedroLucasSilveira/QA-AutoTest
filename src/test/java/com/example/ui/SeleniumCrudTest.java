package com.example.ui;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SeleniumCrudTest {

    @LocalServerPort
    private int port;

    private static WebDriver driver;

    @BeforeAll
    public static void setupClass() {
        ChromeOptions opts = new ChromeOptions();
        opts.addArguments("--headless=new");
        opts.addArguments("--no-sandbox");
        opts.addArguments("--disable-dev-shm-usage");
        driver = new ChromeDriver(opts);
    }

    @AfterAll
    public static void tearDownClass() {
        if (driver != null) driver.quit();
    }

    @Test
    public void createAndRead() {
        String base = "http://localhost:" + port + "/users";
        driver.get(base + "/new");
        driver.findElement(By.name("id")).sendKeys("500");
        driver.findElement(By.name("name")).sendKeys("TesteUI");
        driver.findElement(By.name("email")).sendKeys("ui@teste.com");
        driver.findElement(By.cssSelector("form")).submit();

        driver.get(base + "/500");
        String body = driver.findElement(By.tagName("body")).getText();
        assertTrue(body.contains("TesteUI"));
        assertTrue(body.contains("ui@teste.com"));
    }
}
