package com.project.Plantes.Medicinales.selenium;

import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class SearchTest {

    private WebDriver driver;

    @BeforeEach
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", "C:\\chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("http://localhost:4200/home"); // URL de l'application
    }

    @Test
    public void testSearchByName() {
        System.out.println("=== Test : Recherche par Nom ===");

        // Étape 1 : Sélectionner 'Nom' dans le menu déroulant
        System.out.println("Étape 1 : Sélectionner 'Nom' dans le menu déroulant...");
        WebElement searchTypeSelect = driver.findElement(By.cssSelector(".search-select mat-select"));
        searchTypeSelect.click();

        WebElement searchByNameOption = driver.findElement(By.cssSelector("mat-option[value='nom']"));
        searchByNameOption.click();

        // Étape 2 : Entrer 'Menthe poivrée' dans le champ de recherche
        System.out.println("Étape 2 : Entrer 'Menthe poivrée' dans le champ de recherche...");
        WebElement searchField = driver.findElement(By.cssSelector(".search-field input[matInput]"));
        searchField.sendKeys("Menthe poivrée");

        // Étape 3 : Vérifier que les résultats de la recherche s'affichent
        System.out.println("Étape 3 : Vérifier que les résultats de la recherche s'affichent...");
        Assertions.assertTrue(driver.findElements(By.cssSelector(".example-card")).size() > 0,
                "Aucun résultat de recherche trouvé.");

        // Étape 4 : Vérifier si un élément avec le titre 'Menthe poivrée' est affiché
        System.out.println("Étape 4 : Vérifier si un élément avec le titre 'Menthe poivrée' est affiché...");
        WebElement plantCard = driver.findElements(By.cssSelector(".card-title"))
                .stream()
                .filter(card -> card.getText().equals("Menthe poivrée"))
                .findFirst()
                .orElse(null);

        Assertions.assertNotNull(plantCard, "Plante avec le nom 'Menthe poivrée' non trouvée.");

        // Étape 5 : Cliquer sur le bouton 'Details'
        System.out.println("Étape 5 : Cliquer sur le bouton 'Details'...");
        WebElement detailsButton = plantCard.findElement(By.xpath("ancestor::mat-card//mat-card-actions//button"));
        detailsButton.click();

        // Étape 6 : Vérifier si le nom 'Menthe poivrée' s'affiche dans le dialog
        System.out.println("Étape 6 : Vérifier si le nom 'Menthe poivrée' s'affiche dans le dialog...");
        WebElement dialogTitle = driver.findElement(By.cssSelector("h2[mat-dialog-title]"));
        String dialogTitleText = dialogTitle.getText();

        System.out.println("Titre trouvé dans le dialog : " + dialogTitleText);
        Assertions.assertEquals("Menthe poivrée", dialogTitleText,
                "Le titre du dialog ne correspond pas au nom attendu 'Menthe poivrée'.");

        // Étape 7 : Fermer le dialog
        System.out.println("Étape 7 : Fermer le dialog...");
        WebElement closeButton = driver.findElement(By.cssSelector("button[mat-dialog-close]"));
        closeButton.click();
    }

    /*
    @Test
    public void testSearchByPropertyInDialog() throws InterruptedException {
        System.out.println("=== Test : Recherche par Propriété dans le Dialog ===");

        // Étape 1 : Sélectionner 'Propriétés' dans le menu déroulant
        System.out.println("Étape 1 : Sélectionner 'Propriétés' dans le menu déroulant...");
        WebElement searchTypeSelect = driver.findElement(By.cssSelector(".search-select mat-select"));
        searchTypeSelect.click();

        WebElement searchByPropertyOption = driver.findElement(By.cssSelector("mat-option[value='proprietes']"));
        searchByPropertyOption.click();

        // Étape 2 : Entrer 'antispasmodique' dans le champ de recherche
        System.out.println("Étape 2 : Entrer 'antispasmodique' dans le champ de recherche...");
        WebElement searchField = driver.findElement(By.cssSelector(".search-field input[matInput]"));
        searchField.sendKeys("antispasmodique");

        // Étape 3 : Vérifier que les résultats de la recherche s'affichent
        System.out.println("Étape 3 : Vérifier que les résultats de la recherche s'affichent...");
        Assertions.assertTrue(driver.findElements(By.cssSelector(".example-card")).size() > 0,
                "Aucun résultat de recherche trouvé.");

        // Étape 4 : Cliquer sur le bouton 'Details' de la première carte
        System.out.println("Étape 4 : Cliquer sur le bouton 'Details' de la première carte...");
        WebElement firstDetailsButton = driver.findElement(By.cssSelector(".example-card:first-of-type button"));
        firstDetailsButton.click();

        // Étape 5 : Vérifier si le dialog contient 'antispasmodique' dans la section 'Propriétés'
        System.out.println("Étape 5 : Vérifier si le dialog contient 'antispasmodique' dans la section 'Propriétés'...");
        WebElement propertiesParagraph = driver.findElement(By.xpath("//h2[text()='Propriétés']/following-sibling::p"));
        String propertiesText = propertiesParagraph.getText();

        System.out.println("Texte trouvé dans 'Propriétés' : " + propertiesText);
        Assertions.assertTrue(propertiesText.toLowerCase().contains("antispasmodique"),
                "La propriété 'antispasmodique' n'a pas été trouvée dans le dialog.");

        // Étape 6 : Fermer le dialog
        System.out.println("Étape 6 : Fermer le dialog...");
        WebElement closeButton = driver.findElement(By.cssSelector("button[mat-dialog-close]"));
        closeButton.click();
    }

     */

    @Test
    public void testSearchByUsage() {
        System.out.println("=== Test : Recherche par Utilisation ===");

        // Étape 1 : Sélectionner 'Utilisation' dans le menu déroulant
        System.out.println("Étape 1 : Sélectionner 'Utilisation' dans le menu déroulant...");
        WebElement searchTypeSelect = driver.findElement(By.cssSelector(".search-select mat-select"));
        searchTypeSelect.click();

        WebElement searchByUsageOption = driver.findElement(By.cssSelector("mat-option[value='utilisation']"));
        searchByUsageOption.click();

        // Étape 2 : Entrer 'infusion pour les troubles digestifs' dans le champ de recherche
        System.out.println("Étape 2 : Entrer 'infusion pour les troubles digestifs' dans le champ de recherche...");
        WebElement searchField = driver.findElement(By.cssSelector(".search-field input[matInput]"));
        searchField.sendKeys("infusion pour les troubles digestifs");

        // Étape 3 : Vérifier que les résultats de la recherche s'affichent
        System.out.println("Étape 3 : Vérifier que les résultats de la recherche s'affichent...");
        Assertions.assertTrue(driver.findElements(By.cssSelector(".example-card")).size() > 0,
                "Aucun résultat de recherche trouvé.");

        // Étape 4 : Vérifier que la recherche affiche un élément correspondant à l'utilisation
        System.out.println("Étape 4 : Vérifier qu'une plante avec l'utilisation 'infusion pour les troubles digestifs' est affichée...");
        WebElement plantCard = driver.findElements(By.cssSelector(".card-title"))
                .stream()
                .filter(card -> card.getText().equals("Menthe poivrée")) // Remplacer par le nom réel attendu
                .findFirst()
                .orElse(null);

        Assertions.assertNotNull(plantCard, "Plante avec l'utilisation spécifiée non trouvée.");

        // Étape 5 : Cliquer sur le bouton 'Details'
        System.out.println("Étape 5 : Cliquer sur le bouton 'Details'...");
        WebElement detailsButton = plantCard.findElement(By.xpath("ancestor::mat-card//mat-card-actions//button"));
        detailsButton.click();

        // Étape 6 : Vérifier si l'utilisation est affichée dans le dialog
        System.out.println("Étape 6 : Vérifier si l'utilisation 'infusion pour les troubles digestifs' s'affiche dans le dialog...");
        WebElement usageParagraph = driver.findElement(By.xpath("//h2[text()='Utilisation']/following-sibling::p"));
        String usageText = usageParagraph.getText();

        System.out.println("Texte trouvé dans 'Utilisation' : " + usageText);
        Assertions.assertTrue(usageText.toLowerCase().contains("infusion pour les troubles digestifs"),
                "L'utilisation spécifiée n'a pas été trouvée dans le dialog.");

        // Étape 7 : Fermer le dialog
        System.out.println("Étape 7 : Fermer le dialog...");
        WebElement closeButton = driver.findElement(By.cssSelector("button[mat-dialog-close]"));
        closeButton.click();
    }




    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit(); // Fermer le navigateur après chaque test
        }
    }
}
