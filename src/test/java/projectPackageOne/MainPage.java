package projectPackageOne;


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
    private List<String> expectedResult;
    private List<String> actualResult;
    private List<String> expectedResultUser;
    private List<String> actualResultUser;

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


    public void configurationWait() {
        driverWait = new DriverWait();
    }

    public void openPage() {
        driver.get(ConfProperties.getProperty("url"));
    }


    public void successAddingCustomer(String firstname, String LastName, String PostalCode) {
        buttonAddCustomer = driverWait.waitForElementClickable(buttonAddCustomer, 5);
        buttonAddCustomer.click();
        firstNameField = driverWait.waitForElementClickable(firstNameField, 5);
        firstNameField.sendKeys(firstname);
        lastNameField.sendKeys(LastName);
        postCodeField.sendKeys(PostalCode);
        addCustomer.click();
    }


    public void findAddingUser(String user) {
        buttonCustomers = driverWait.waitForElementClickable(buttonCustomers, 5);
        buttonCustomers.click();
        searchCustomer = driverWait.waitForElementClickable(searchCustomer, 5);
        searchCustomer.sendKeys(user);
        actualResultUser = listOfCustomers.stream().map(x -> x.getText()).
                filter(y -> y.contains(ConfProperties.getProperty("FirstName"))).collect(Collectors.toList());
        expectedResultUser = new ArrayList<>();
        expectedResultUser.add(ConfProperties.getProperty("FirstName"));
    }

    public void removeCreatingUser(String user) {
        buttonCustomers = driverWait.waitForElementClickable(buttonCustomers, 5);
        buttonCustomers.click();
        searchCustomer = driverWait.waitForElementClickable(searchCustomer, 5);
        searchCustomer.sendKeys(user);
        buttonDelete.click();
    }

    public void createDuplicateUser(String firstname, String LastName, String PostalCode) {
        buttonAddCustomer = driverWait.waitForElementClickable(buttonAddCustomer, 5);
        buttonAddCustomer.click();
        firstNameField = driverWait.waitForElementClickable(firstNameField, 5);
        firstNameField.sendKeys(firstname);
        lastNameField.sendKeys(LastName);
        postCodeField.sendKeys(PostalCode);
        addCustomer.click();
    }


    public void alphabetSorting() {
        buttonCustomers = driverWait.waitForElementClickable(buttonCustomers, 5);
        buttonCustomers.click();
        sortedByButtonFirstName = driverWait.waitForElementClickable(sortedByButtonFirstName, 5);
        sortedByButtonFirstName.click();
        //Сортировка в алфавитном порядке
        sortedByButtonFirstName.click();
        actualResult = listOfCustomers.stream().map(x -> x.getText()).collect(Collectors.toList());
        expectedResult = listOfCustomers.stream().map(x -> x.getText()).sorted().collect(Collectors.toList());
    }

    public void reverseAlphabetSorting() {
        buttonCustomers = driverWait.waitForElementClickable(buttonCustomers, 5);
        buttonCustomers.click();
        sortedByButtonFirstName = driverWait.waitForElementClickable(sortedByButtonFirstName, 5);
        sortedByButtonFirstName.click();
        actualResult = listOfCustomers.stream().map(x -> x.getText()).collect(Collectors.toList());
        expectedResult = listOfCustomers.stream().map(x -> x.getText()).sorted(Collections.reverseOrder()).collect(Collectors.toList());
    }

    public void closeAlert() {
        driver.switchTo().alert().accept();
    }


    public List CheckActualSorting() {
        return actualResult;
    }

    public List CheckExpectedSorting() {
        return expectedResult;
    }

    public List CheckExpectUser() {
        return expectedResultUser;
    }

    public List CheckActualUser() {
        return actualResultUser;
    }

    public boolean CheckThatUserDeleted() {
        return listOfCustomers.stream().map(x -> x.getText()).collect(Collectors.toList()).
                contains(ConfProperties.getProperty("DeleteFirstName"));
    }

    public String getAlertText() {
        return driver.switchTo().alert().getText();
    }

}
