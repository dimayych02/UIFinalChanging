package projectPackageOne;


import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.*;


public class UIAutomationTests  {

    public static MainPage mainPage;
    public static WebDriver driver;


    @BeforeMethod
    public void SetUp() {
        driver = new ChromeDriver();
        mainPage = new MainPage(driver);
        mainPage.configurationWait();
    }

    @Test(priority = 1,retryAnalyzer = TestNGRetry.class)
    public void SuccessAddUser() {
        mainPage.openPage();
        mainPage.successAddingCustomer(ConfProperties.getProperty("FirstName"),
                ConfProperties.getProperty("LastName"),
                ConfProperties.getProperty("PostalCode"));
        mainPage.closeAlert();
        mainPage.findAddingUser(ConfProperties.getProperty("FirstName"));
        Assert.assertEquals(mainPage.CheckExpectUser(), mainPage.CheckActualUser());
    }


    @Test(priority = 2,retryAnalyzer = TestNGRetry.class)
    public void DeleteUser() {
        mainPage.openPage();
        mainPage.removeCreatingUser(ConfProperties.getProperty("DeleteFirstName"));
        Assert.assertFalse(mainPage.CheckThatUserDeleted());
    }

    @Test(priority = 3,retryAnalyzer = TestNGRetry.class)
    public void createDuplicate() {
        mainPage.openPage();
        mainPage.createDuplicateUser(ConfProperties.getProperty("DuplicateFirstName"),
                ConfProperties.getProperty("DuplicateLastName"),
                ConfProperties.getProperty("DuplicatePostalCode"));
        Assert.assertEquals(ConfProperties.getProperty("DuplicateAlertMessage"), mainPage.getAlertText());
        mainPage.closeAlert();
    }

    @Test(priority = 4,retryAnalyzer = TestNGRetry.class)
    public void CheckSorting() {
        mainPage.openPage();
        mainPage.alphabetSorting();
        Assert.assertEquals(mainPage.CheckExpectedSorting(), mainPage.CheckActualSorting());
    }

    @Test(priority = 5,retryAnalyzer = TestNGRetry.class)
    public void CheckReverseSorting() {
        mainPage.openPage();
        mainPage.reverseAlphabetSorting();
        Assert.assertEquals(mainPage.CheckExpectedSorting(), mainPage.CheckActualSorting());
    }


    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.close();
            driver.quit();
        }
    }
}
