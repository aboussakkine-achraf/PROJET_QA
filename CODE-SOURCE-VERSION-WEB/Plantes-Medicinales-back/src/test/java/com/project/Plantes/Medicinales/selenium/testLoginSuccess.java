package com.project.Plantes.Medicinales.selenium;


import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.time.Duration;

public class testLoginSuccess {

    private WebDriver driver;

    @BeforeEach
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", "C:\\chromedriver.exe");
        driver = new ChromeDriver();
        driver.get("http://localhost:4200/login");
    }

    @Test
    public void testLoginSuccess() {
        // Find the input fields and the login button
        WebElement emailField = driver.findElement(By.id("email"));
        WebElement passwordField = driver.findElement(By.id("password"));
        WebElement loginButton = driver.findElement(By.xpath("//button[contains(text(), 'Login')]"));

        // Enter credentials and click login
        emailField.sendKeys("useryt@gmail.com");
        passwordField.sendKeys("useryt23aew23");
        loginButton.click();

        // Handle the alert (if it appears)
        try {
            // Wait for the alert to be present
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.alertIsPresent());
            Alert alert = driver.switchTo().alert();
            System.out.println("Alert message: " + alert.getText());  // Optional: Print alert text
            alert.accept();  // Accept the alert
        } catch (NoAlertPresentException e) {
            System.out.println("No alert present");
        }

        // Wait until the URL changes to the expected URL
        WebDriverWait waitForUrl = new WebDriverWait(driver, Duration.ofSeconds(10));
        waitForUrl.until(ExpectedConditions.urlToBe("http://localhost:4200/home"));

        // Assert the current URL is the expected URL
        String currentUrl = driver.getCurrentUrl();
        Assertions.assertEquals("http://localhost:4200/home", currentUrl);
    }

    @AfterEach
    public void tearDown() {
        driver.quit();
    }
}
