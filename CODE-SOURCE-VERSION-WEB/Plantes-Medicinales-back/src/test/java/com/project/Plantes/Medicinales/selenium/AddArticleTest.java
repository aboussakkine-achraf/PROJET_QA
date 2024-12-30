package com.project.Plantes.Medicinales.selenium;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class AddArticleTest {

    private WebDriver driver;
    private WebDriverWait wait;

    @BeforeEach
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", "C:\\chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.get("http://localhost:4200/addArticle");
    }

    @Test
    public void testAddArticle() {


        // Find the input fields
        WebElement title = driver.findElement(By.id("title"));
        WebElement content = driver.findElement(By.id("content"));
        WebElement fileInput = driver.findElement(By.id("image")); // File input element
        WebElement submitButton = driver.findElement(By.id("submitbt")); // Submit button

        // Fill out the form fields
        title.sendKeys("The Power of Medicinal Plants: Nature’s Healing Secrets");
        content.sendKeys("Medicinal plants have been used for centuries by various cultures across the globe for their healing properties...");

        // Specify the path to the image file
        String imagePath = "C:\\Users\\essao\\Downloads\\medic.jpeg";  // Full path to the image file

        // Select the image file
        fileInput.sendKeys(imagePath);  // Send the file path to the file input

        // Submit the form
        submitButton.click();

        // Optionally, verify that the article was submitted successfully,
        // for example by checking if a confirmation page appears or the article is displayed
    }
}
