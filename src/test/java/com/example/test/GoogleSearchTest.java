package com.example.test;

import com.example.test.config.WebDriverConfig;
import com.example.test.pages.GoogleHomePage;
import com.example.test.pages.GoogleResultsPage;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.Duration;

public class GoogleSearchTest {
    private WebDriver driver;
    private GoogleHomePage googleHomePage;
    private GoogleResultsPage googleResultsPage;

    @BeforeClass
    public void setUp() {
        driver = WebDriverConfig.getDriver();
        driver.get("https://www.google.com");
        googleHomePage = new GoogleHomePage(driver);
        googleResultsPage = new GoogleResultsPage(driver);
    }

    @Test
    public void testSearchWikipedia() {
        googleHomePage.enterSearchTerm("wikipedia");
        googleHomePage.clickSearchButton();

        // Espera explícita para asegurarse de que la página de resultados se ha cargado
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        WebElement resultsStats = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("result-stats")));

// Hacer clic en el primer resultado
        googleResultsPage.clickFirstResult();

        // Hacer scroll hacia abajo y hacer clic en el enlace de Política de privacidad
        String privacyPolicyXPath = "(//a[normalize-space()='Política de privacidad'])[1]";
        WebElement privacyPolicyLink = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(privacyPolicyXPath)));

        // Scroll hacia el elemento
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", privacyPolicyLink);

        // Hacer clic en el enlace
        privacyPolicyLink.click();

        // Esperar a que la nueva página se cargue y verificar que el título contiene "Wikimedia Foundation Privacy Policy"
        try {
            wait.until(ExpectedConditions.titleContains("Wikimedia Foundation Privacy Policy"));
            String expectedTitle = "Wikimedia Foundation Privacy Policy";
            String actualTitle = driver.getTitle();
            Assert.assertTrue(actualTitle.contains(expectedTitle), "The title 'Wikimedia Foundation Privacy Policy' is not displayed");
        } catch (org.openqa.selenium.TimeoutException e) {
            Assert.fail("The title 'Wikimedia Foundation Privacy Policy' is not displayed");
        }
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            try {
                // Espera de 5 segundos antes de cerrar el navegador
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            driver.quit();
        }
    }
}