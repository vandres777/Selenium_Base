package com.example.test.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class GoogleHomePage {
    private WebDriver driver;
    private By searchBox = By.name("q");
    private By searchButton = By.name("btnK");

    public GoogleHomePage(WebDriver driver) {
        this.driver = driver;
    }

    public void enterSearchTerm(String searchTerm) {
        driver.findElement(searchBox).sendKeys(searchTerm);
    }

    public void clickSearchButton() {
        driver.findElement(searchButton).submit(); // Cambiado a submit para asegurar que la búsqueda se realiza
    }
}