package com.example.test;

import com.example.test.pages.GoogleHomePage;
import com.example.test.pages.GoogleResultsPage;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
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
        // Configurar WebDriverManager para ChromeDriver


        //Configuracion para lanzar navegador Chrome en modo incognito y maximizado, comentar las líneas que corresponden al modo headless
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--incognito");
        options.addArguments("--start-maximized");
        //options.addArguments("--headless");
        //options.addArguments("--disable-gpu"); // Opcional, pero recomendado para evitar problemas en algunas configuraciones
        //options.addArguments("--window-size=1920,1080"); // Opcional, para establecer el tamaño de la ventana

/*
        //Configuracion para lanzar navegador Edge en modo incognito y maximizado, comentar las líneas que corresponden al modo headless
        EdgeOptions options = new EdgeOptions();
        options.addArguments("--inprivate");
        options.addArguments("--start-maximized");
        options.addArguments("--headless");
        options.addArguments("--disable-gpu"); // Opcional, pero recomendado para evitar problemas en algunas configuraciones
        options.addArguments("--window-size=1920,1080"); // Opcional, para establecer el tamaño de la ventana*/

/*
        //Configuracion para lanzar navegador Firefox en modo headless
        FirefoxOptions options = new FirefoxOptions();
        options.addArguments("-private");
        options.addArguments("--start-maximized");
        options.addArguments("--headless");
        options.addArguments("--disable-gpu"); // Opcional, pero recomendado para evitar problemas en algunas configuraciones
        options.addArguments("--window-size=1920,1080"); // Opcional, para establecer el tamaño de la ventana*/

        // Inicializar ChromeDriver con las opciones configuradas
        driver = new ChromeDriver(options);
        //driver = new EdgeDriver(options);
        //driver = new FirefoxDriver(options);
        driver.get("https://www.google.com");
        googleHomePage = new GoogleHomePage(driver);
        googleResultsPage = new GoogleResultsPage(driver);
    }

    @Test
    public void testSearchWikipedia() {
        googleHomePage.enterSearchTerm("wikipedia");
        googleHomePage.clickSearchButton();

        // Espera explícita para asegurarse de que la página de resultados se ha cargado
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement resultsStats = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("result-stats")));

        //Assert.assertTrue(resultsStats.isDisplayed(), "Results page is not displayed");
        //Assert.assertTrue(resultsStats.isDisplayed(), "wikipedia - Buscar con Google");
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