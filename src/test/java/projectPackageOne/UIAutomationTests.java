package projectPackageOne;


import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.*;

import java.util.Collections;
import java.util.stream.Collectors;


public class UIAutomationTests {

    public static MainPage mainPage;
    public static WebDriver driver;
    protected static String url;
    protected static String firstname;
    protected static String lastname;
    protected static String postalCode;
    protected static String deleteOrDuplicateUserName;
    protected static String duplicateLastName;
    protected static String duplicatePostalCode;
    protected static String duplicateAlertWindow;


    @BeforeMethod
    public void setUp() {
        url = ConfProperties.getProperty("url");
        firstname = ConfProperties.getProperty("FirstName");
        lastname = ConfProperties.getProperty("LastName");
        postalCode = ConfProperties.getProperty("PostalCode");
        deleteOrDuplicateUserName = ConfProperties.getProperty("DeleteOrDuplicateFirstName");
        duplicateLastName = ConfProperties.getProperty("DuplicateLastName");
        duplicatePostalCode = ConfProperties.getProperty("DuplicatePostalCode");
        duplicateAlertWindow = ConfProperties.getProperty("DuplicateAlertMessage");
        driver = new ChromeDriver();
        mainPage = new MainPage(driver);
        mainPage.configurationWait();
    }

    @Description("Успешное создание пользователя")
    @Severity(SeverityLevel.BLOCKER)
    @Test(priority = 1, retryAnalyzer = TestNGRetry.class)
    public void successAddUser() {
        mainPage.openPage(url);
        mainPage.successAddingCustomer(firstname, lastname, postalCode);
        mainPage.closeAlert();
        mainPage.findAddingUser(firstname);
        Assert.assertEquals(mainPage.getUsersName().stream().
                        filter(y -> y.contains(firstname)).collect(Collectors.toList()),
                mainPage.expectedResult,
                "Пользователь не был создан!");
    }

    @Description("Удаление пользователя")
    @Severity(SeverityLevel.BLOCKER)
    @Test(priority = 2, retryAnalyzer = TestNGRetry.class)
    public void deleteUser() {
        mainPage.openPage(url);
        mainPage.removeCreatingUser(deleteOrDuplicateUserName);
        Assert.assertFalse(mainPage.checkThatUserDeleted(deleteOrDuplicateUserName),
                "Пользователь не был удален!");
    }

    @Description("Создание дубликата")
    @Severity(SeverityLevel.CRITICAL)
    @Test(priority = 3, retryAnalyzer = TestNGRetry.class)
    public void createDuplicate() {
        mainPage.openPage(url);
        mainPage.successAddingCustomer(deleteOrDuplicateUserName, duplicateLastName,
                duplicatePostalCode);
        Assert.assertEquals(duplicateAlertWindow, mainPage.getAlertText(),
                "Этот пользователь не является дубликатом!");
        mainPage.closeAlert();
    }

    @Description("Создание пользователя с пустыми параметрами")
    @Severity(SeverityLevel.TRIVIAL)
    @Test(priority = 7, retryAnalyzer = TestNGRetry.class)
    public void emptyUser() {
        mainPage.openPage(url);
        mainPage.successAddingCustomer(" ", " ", " ");
        Assert.assertEquals(duplicateAlertWindow, mainPage.getAlertText(),
                "alert-окно 'Please check the details. Customer may be duplicate' не появилось!");
        mainPage.closeAlert();
        Assert.assertTrue(mainPage.checkEmptyUser(), "Пользователь с пустыми параметрами был найден!");
    }

    @Description("Пользователь не создан")
    @Severity(SeverityLevel.NORMAL)
    @Test(priority = 4, retryAnalyzer = TestNGRetry.class)
    public void userIsNotCreated() {
        mainPage.openPage(url);
        mainPage.successAddingCustomer("", "", "");
        Assert.assertFalse(mainPage.isAlertPresent());
    }

    @Description("Сортировка пользователей в алфавмитном порядке")
    @Severity(SeverityLevel.NORMAL)
    @Test(priority = 5, retryAnalyzer = TestNGRetry.class)
    public void checkSorting() {
        mainPage.openPage(url);
        mainPage.alphabetSorting();
        Assert.assertEquals(mainPage.getUsersName().stream()
                        .sorted().collect(Collectors.toList()),
                mainPage.getUsersName(),
                "Сортировка в алафвитном порядке зафейлилась!");
    }

    @Description("Сортировка пользователей в обратном порядке")
    @Severity(SeverityLevel.NORMAL)
    @Test(priority = 6, retryAnalyzer = TestNGRetry.class)
    public void checkReverseSorting() {
        mainPage.openPage(url);
        mainPage.reverseAlphabetSorting();
        Assert.assertEquals(mainPage.getUsersName().stream()
                        .sorted(Collections.reverseOrder()).collect(Collectors.toList()), mainPage.getUsersName(),
                "Сортировка в обратном порядке зафейлилась!");
    }


    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.close();
            driver.quit();
        }
    }
}
