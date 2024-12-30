package com.project.Plantes.Medicinales.selenium;

import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class RecommendationFormTest {

    private WebDriver driver;
    private WebDriverWait wait;

    @BeforeEach
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", "C:\\chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.get("http://localhost:4200/recommendation-form");
    }

    @Test
    public void testRecommendationFormSubmission() {
        System.out.println("=== Test : Soumission du formulaire de recommandation ===");

        // Étape 1 : Remplir le champ "Besoins de santé particuliers"
        System.out.println("Étape 1 : Remplir le champ 'Besoins de santé particuliers'...");
        WebElement propertiesInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("properties")));
        propertiesInput.sendKeys("améliorer l’humeur");

        // Étape 2 : Remplir le champ "Antécédents médicaux"
        System.out.println("Étape 2 : Remplir le champ 'Antécédents médicaux'...");
        WebElement medicalHistoryInput = driver.findElement(By.id("medicalHistory"));
        medicalHistoryInput.sendKeys("oui");

        // Étape 3 : Remplir le champ "Utilisation de plantes médicinales"
        System.out.println("Étape 3 : Remplir le champ 'Utilisation de plantes médicinales'...");
        WebElement previousUseInput = driver.findElement(By.id("previousUse"));
        previousUseInput.sendKeys("oui");

        // Étape 4 : Remplir le champ "Symptômes actuels"
        System.out.println("Étape 4 : Remplir le champ 'Symptômes actuels'...");
        WebElement currentSymptomsInput = driver.findElement(By.id("currentSymptoms"));
        currentSymptomsInput.sendKeys("allaitantes");

        // Étape 5 : Cliquer sur le bouton "Submit"
        System.out.println("Étape 5 : Cliquer sur le bouton 'Submit'...");
        WebElement submitButton = driver.findElement(By.id("submit"));

        // Use JavascriptExecutor to click the button, bypassing potential blocking elements
        JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
        jsExecutor.executeScript("arguments[0].click();", submitButton);

        // Étape 6 : Vérifier la redirection vers la page "Plant List"
        System.out.println("Étape 6 : Vérifier la redirection vers la page 'Plant List'...");
        wait.until(ExpectedConditions.urlToBe("http://localhost:4200/plant-list"));
        String currentUrl = driver.getCurrentUrl();
        Assertions.assertEquals("http://localhost:4200/plant-list", currentUrl,
                "Redirection échouée. URL actuelle : " + currentUrl);
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
