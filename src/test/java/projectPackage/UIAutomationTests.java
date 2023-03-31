package projectPackage;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.*;


public class UIAutomationTests {

    public static MainPage mainPage;

    public static  WebDriver driver;



    @BeforeMethod
    public void SetUp(){
        //конфигурация

        driver = new ChromeDriver();

        mainPage = new MainPage(driver);

    }


    @Test(priority = 1)
    public void SuccessAddUser(){


        mainPage.OpenPage();

        mainPage.SuccessAddingCustomer(ConfProperties.getProperty("FirstName"),

                ConfProperties.getProperty("LastName"),

                ConfProperties.getProperty("PostalCode"));


        mainPage.closeAlert();

        mainPage.FindAddingUser(ConfProperties.getProperty("FirstName"));

        Assert.assertEquals(mainPage.CheckExpectUser(),mainPage.CheckActualUser());



    }


    @Test(priority = 2)
    public void DeleteUser(){



        mainPage.OpenPage();

        mainPage.RemoveCreatingUser(ConfProperties.getProperty("DeleteFirstName"));

        Assert.assertFalse(mainPage.CheckThatUserDeleted());



    }

    @Test(priority = 3)
    public void createDuplicate(){

        mainPage.OpenPage();

        mainPage.CreateDuplicateUser(ConfProperties.getProperty("DuplicateFirstName"),

                ConfProperties.getProperty("DuplicateLastName"),

                ConfProperties.getProperty("DuplicatePostalCode"));

        Assert.assertEquals(ConfProperties.getProperty("DuplicateAlertMessage"),mainPage.getAlertText());

        mainPage.closeAlert();
    }

    @Test(priority = 4)
    public void CheckSorting(){


        mainPage.OpenPage();

        mainPage.AlphabetSorting();

        Assert.assertEquals(mainPage.CheckExpectedSorting(),mainPage.CheckActualSorting());




    }
    @Test(priority = 5)
    public void CheckReverseSorting(){



        mainPage.OpenPage();

        mainPage.ReverseAlphabetSorting();

        Assert.assertEquals(mainPage.CheckExpectedSorting(),mainPage.CheckActualSorting());


    }



    @AfterMethod
    public void tearDown(){

            if(driver!=null){

                driver.close();

                driver.quit();
            }


    }

}
