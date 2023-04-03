package projectPackageOne;


import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;


public class MainPage {
    public static DriverWait driverWait;
    public static WebDriver driver;
    protected List<String> expectedResult;
    protected List<String> actualResult;
    protected List<String> expectedResultUser;
    protected List<String> actualResultUser;

    @FindBy(xpath = "//*[contains(text(),'Add Customer')]")
    private WebElement buttonAddCustomer;

    @FindBy(css = "input[placeholder=\"First Name\"]")
    private WebElement firstNameField;

    @FindBy(css = "input[placeholder=\"Last Name\"]")
    private WebElement lastNameField;

    @FindBy(css = "input[placeholder=\"Post Code\"]")
    private WebElement postCodeField;

    @FindBy(xpath = "//button[text()='Add Customer']")
    private WebElement addCustomer;

    @FindBy(xpath = "//*[contains(text(),'Customers')]")
    private WebElement buttonCustomers;

    @FindBy(xpath = "//*[contains(text(),'First Name')]")
    private WebElement sortedByButtonFirstName;

    @FindBy(xpath = "//tr//td[@class][1]")
    private List<WebElement> listOfCustomers;

    @FindBy(css = "input[placeholder=\"Search Customer\"]")
    private WebElement searchCustomer;

    @FindBy(xpath = "//button[text()='Delete']")
    private WebElement buttonDelete;

    public MainPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
    }


    public MainPage configurationWait() {
        driverWait = new DriverWait();
        return this;
    }

    public MainPage openPage(String url) {
        driver.get(url);
        return this;
    }


    public MainPage successAddingCustomer(String firstName, String lastName, String postalCode) {
        buttonAddCustomer = driverWait.waitForElementClickable(buttonAddCustomer, 5);
        buttonAddCustomer.click();
        firstNameField = driverWait.waitForElementClickable(firstNameField, 5);
        firstNameField.sendKeys(firstName);
        lastNameField.sendKeys(lastName);
        postCodeField.sendKeys(postalCode);
        addCustomer.click();
        return this;
    }


    public MainPage findAddingUser(String user) {
        buttonCustomers = driverWait.waitForElementClickable(buttonCustomers, 5);
        buttonCustomers.click();
        searchCustomer = driverWait.waitForElementClickable(searchCustomer, 5);
        searchCustomer.sendKeys(user);
        actualResultUser = listOfCustomers.stream().map(x -> x.getText()).
                filter(y -> y.contains(user)).collect(Collectors.toList());
        expectedResultUser = new ArrayList<>();
        expectedResultUser.add(user);
        return this;
    }

    public MainPage removeCreatingUser(String user) {
        buttonCustomers = driverWait.waitForElementClickable(buttonCustomers, 5);
        buttonCustomers.click();
        searchCustomer = driverWait.waitForElementClickable(searchCustomer, 5);
        searchCustomer.sendKeys(user);
        buttonDelete.click();
        return this;
    }

    public MainPage invalidCreationOfUser() {
        buttonAddCustomer = driverWait.waitForElementClickable(buttonAddCustomer, 5);
        buttonAddCustomer.click();
        addCustomer = driverWait.waitForElementClickable(addCustomer, 5);
        addCustomer.click();
        return this;
    }


    public MainPage alphabetSorting() {
        buttonCustomers = driverWait.waitForElementClickable(buttonCustomers, 5);
        buttonCustomers.click();
        sortedByButtonFirstName = driverWait.waitForElementClickable(sortedByButtonFirstName, 5);
        sortedByButtonFirstName.click();
        //Сортировка в алфавитном порядке
        sortedByButtonFirstName.click();
        actualResult = listOfCustomers.stream().map(x -> x.getText()).collect(Collectors.toList());
        expectedResult = listOfCustomers.stream().map(x -> x.getText()).sorted().collect(Collectors.toList());
        return this;
    }

    public MainPage reverseAlphabetSorting() {
        buttonCustomers = driverWait.waitForElementClickable(buttonCustomers, 5);
        buttonCustomers.click();
        sortedByButtonFirstName = driverWait.waitForElementClickable(sortedByButtonFirstName, 5);
        sortedByButtonFirstName.click();
        actualResult = listOfCustomers.stream().map(x -> x.getText()).collect(Collectors.toList());
        expectedResult = listOfCustomers.stream().map(x -> x.getText()).
                sorted(Collections.reverseOrder()).collect(Collectors.toList());
        return this;
    }


    public MainPage closeAlert() {
        driver.switchTo().alert().accept();
        return this;
    }


    public boolean checkThatUserDeleted(String user) {
        return listOfCustomers.stream().map(x -> x.getText()).collect(Collectors.toList()).
                contains(user);
    }

    public String getAlertText() {
        return driver.switchTo().alert().getText();
    }

    public boolean checkEmptyUser() {
        return listOfCustomers.stream().map(x -> x.getText()).
                filter(x -> x.contains(" ")).collect(Collectors.toList()).isEmpty();
    }

    public boolean isAlertPresent() {
        try {
            driver.switchTo().alert();
            return true;
        } catch (NoAlertPresentException Ex) {
            return false;
        }
    }
}
