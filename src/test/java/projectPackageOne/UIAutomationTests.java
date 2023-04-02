package projectPackageOne;


import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.*;


public class UIAutomationTests {

    public static MainPage mainPage;
    public static WebDriver driver;


    @BeforeMethod
    public void setUp() {
        driver = new ChromeDriver();
        mainPage = new MainPage(driver);
        mainPage.configurationWait();
    }

    @Description("Успешное создание пользователя")
    @Severity(SeverityLevel.BLOCKER)
    @Test(priority = 1, retryAnalyzer = TestNGRetry.class)
    public void successAddUser() {
        mainPage.openPage();
        mainPage.successAddingCustomer(ConfProperties.getProperty("FirstName"),
                ConfProperties.getProperty("LastName"),
                ConfProperties.getProperty("PostalCode"));
        mainPage.closeAlert();
        mainPage.findAddingUser(ConfProperties.getProperty("FirstName"));
        Assert.assertEquals(mainPage.CheckExpectUser(), mainPage.CheckActualUser());
    }

    @Description("Удаление пользователя")
    @Severity(SeverityLevel.BLOCKER)
    @Test(priority = 2, retryAnalyzer = TestNGRetry.class)
    public void deleteUser() {
        mainPage.openPage();
        mainPage.removeCreatingUser(ConfProperties.getProperty("DeleteFirstName"));
        Assert.assertFalse(mainPage.CheckThatUserDeleted());
    }

    @Description("создание дубликата")
    @Severity(SeverityLevel.CRITICAL)
    @Test(priority = 3, retryAnalyzer = TestNGRetry.class)
    public void createDuplicate() {
        mainPage.openPage();
        mainPage.createDuplicateUser(ConfProperties.getProperty("DuplicateFirstName"),
                ConfProperties.getProperty("DuplicateLastName"),
                ConfProperties.getProperty("DuplicatePostalCode"));
        Assert.assertEquals(ConfProperties.getProperty("DuplicateAlertMessage"), mainPage.getAlertText());
        mainPage.closeAlert();
    }

    @Description("Сортировка пользователей в алфавмитном порядке")
    @Severity(SeverityLevel.NORMAL)
    @Test(priority = 4, retryAnalyzer = TestNGRetry.class)
    public void checkSorting() {
        mainPage.openPage();
        mainPage.alphabetSorting();
        Assert.assertEquals(mainPage.CheckExpectedSorting(), mainPage.CheckActualSorting());
    }

    @Description("Сортировка пользователей в обратном порядке")
    @Severity(SeverityLevel.NORMAL)
    @Test(priority = 5, retryAnalyzer = TestNGRetry.class)
    public void checkReverseSorting() {
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
