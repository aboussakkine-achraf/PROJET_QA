package com.project.Plantes.Medicinales.selenium;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class AiAssistantTest {

    private WebDriver driver;
    private WebDriverWait wait;

    @BeforeEach
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", "C:\\chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.get("http://localhost:4200/assistantform");
    }

    @Test
    public void testAskAiAssistant() {

        // Trouver les éléments du formulaire
        WebElement userQuestionInput = driver.findElement(By.id("userQuestion"));  // Champ de question
        WebElement submitButton = driver.findElement(By.xpath("//button[@type='submit']"));  // Bouton de soumission

        // Remplir le champ avec la question
        userQuestionInput.sendKeys("Je veux soulager des spasmes musculaires");

        // Soumettre le formulaire
        submitButton.click();

        // Attendre que la réponse de l'assistant soit affichée (en fonction de la structure de la page)
        WebElement assistantResponse = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("assistant-response")));

        // Optionnel : Vérifier que la réponse est correcte ou afficher la réponse
        System.out.println("Réponse de l'assistant AI: " + assistantResponse.getText());
    }
}
