package com.example.ui;

import org.junit.jupiter.api.extension.AfterTestExecutionCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

public class ScreenshotOnFailureExtension implements AfterTestExecutionCallback {

    private static final java.nio.file.Path SCREENSHOT_DIR = java.nio.file.Path.of("target", "test-screenshots");

    @Override
    public void afterTestExecution(ExtensionContext context) throws Exception {
        boolean failed = context.getExecutionException().isPresent();
        // try to access driver from SeleniumCrudTest if available
        try {
            java.lang.reflect.Field f = Class.forName("com.example.ui.SeleniumCrudTest").getDeclaredField("driver");
            f.setAccessible(true);
            Object driver = f.get(null);
            if (failed && driver instanceof org.openqa.selenium.TakesScreenshot) {
                try {
                    java.nio.file.Files.createDirectories(SCREENSHOT_DIR);
                    byte[] bytes = ((org.openqa.selenium.TakesScreenshot) driver).getScreenshotAs(org.openqa.selenium.OutputType.BYTES);
                    String file = context.getDisplayName().replaceAll("[^a-zA-Z0-9_.-]", "_") + ".png";
                    java.nio.file.Path dest = SCREENSHOT_DIR.resolve(file);
                    java.nio.file.Files.write(dest, bytes);
                    System.err.println("Saved screenshot: " + dest.toAbsolutePath());
                } catch (Exception ex) {
                    System.err.println("Failed to save screenshot: " + ex.getMessage());
                }
            }
        } catch (ClassNotFoundException | NoSuchFieldException ex) {
            // ignore if test structure different
        }
    }
}
