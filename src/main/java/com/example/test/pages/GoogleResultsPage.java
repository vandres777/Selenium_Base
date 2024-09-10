package com.example.test.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class GoogleResultsPage {
    private WebDriver driver;
    private By resultsStats = By.id("result-stats");
    private By firstResult = By.cssSelector("h3");

    public GoogleResultsPage(WebDriver driver) {
        this.driver = driver;
    }

    public boolean isResultsPageDisplayed() {
        return driver.findElement(resultsStats).isDisplayed();
    }

    public void clickFirstResult() {
        WebElement firstResultElement = driver.findElement(firstResult);
        firstResultElement.click();
    }
}