package projectPackageOne;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static projectPackageOne.UIAutomationTests.driver;


public class DriverWait {


    private WebDriverWait getWaiter(long timeOutInSeconds) {
        WebDriverWait webDriverWait = new WebDriverWait(driver, Duration.ofSeconds(timeOutInSeconds));
        webDriverWait.ignoring(NoSuchElementException.class)
                .ignoring(ElementNotInteractableException.class)
                .ignoring(StaleElementReferenceException.class);
        return webDriverWait;
    }

    public WebElement waitForElementClickable(WebElement findStrategy, long timeoutInSeconds) {
        getWaiter(timeoutInSeconds).until(ExpectedConditions.elementToBeClickable(findStrategy));
        return findStrategy;
    }
}
