package projectPackage;


import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;



public class MainPage {
    public WebDriver driver;

    public MainPage(WebDriver driver){
        PageFactory.initElements(driver,this);
        this.driver=driver;

    }

    @FindBy(xpath = "//*[contains(text(),'Add Customer')]")
    private WebElement ButtonAddCustomer;


    @FindBy(css = "input[placeholder=\"First Name\"]")
    private WebElement FirstNameField;


    @FindBy(css="input[placeholder=\"Last Name\"]")
    private WebElement LastNameField;


    @FindBy(css = "input[placeholder=\"Post Code\"]")
    private WebElement PostCodeField;


    @FindBy(xpath="//button[text()='Add Customer']")
    private WebElement AddCustomer;



    @FindBy(xpath="//*[contains(text(),'Customers')]")
    private WebElement ButtonCustomers;


    @FindBy(xpath="//*[contains(text(),'First Name')]")
    private WebElement SortedByButtonFirstName;


    @FindBy(xpath="//tr[@ng-repeat=\"cust in Customers | orderBy:sortType:sortReverse | filter:searchCustomer\"]/td[1]")
    private List<WebElement> ListOfCustomers;

    @FindBy(css="input[placeholder=\"Search Customer\"]")
    private WebElement SearchCustomer;

    @FindBy(xpath="//button[text()='Delete']")
    private WebElement ButtonDelete;



    private List<String> ExpectedResult;

    private List<String> ActualResult;

    private List<String> ExpectedResultUser;

    private List<String> ActualResultUser;



    public void OpenPage(){

        driver.get(ConfProperties.getProperty("url"));

    }


    public void SuccessAddingCustomer(String firstname,String LastName,String PostalCode){

        ButtonAddCustomer = new WebDriverWait(driver, Duration.ofSeconds(5)).until(ExpectedConditions.elementToBeClickable(By.xpath("//*[contains(text(),'Add Customer')]")));

        ButtonAddCustomer.click();

        FirstNameField= new WebDriverWait(driver, Duration.ofSeconds(5)).until(ExpectedConditions.elementToBeClickable(By.cssSelector("input[placeholder=\"First Name\"]")));

        FirstNameField.sendKeys(firstname);

        LastNameField.sendKeys(LastName);

        PostCodeField.sendKeys(PostalCode);

        AddCustomer.click();



    }


    public void FindAddingUser(String user){

        ButtonCustomers = new WebDriverWait(driver, Duration.ofSeconds(5)).until(ExpectedConditions.elementToBeClickable(By.xpath("//*[contains(text(),'Customers')]")));

        ButtonCustomers.click();

        SearchCustomer = new WebDriverWait(driver, Duration.ofSeconds(5)).until(ExpectedConditions.elementToBeClickable(By.cssSelector("input[placeholder=\"Search Customer\"]")));

        SearchCustomer.sendKeys(user);

        ActualResultUser = ListOfCustomers.stream().map(x->x.getText()).

                filter(y->y.contains(ConfProperties.getProperty("FirstName"))).collect(Collectors.toList());

        ExpectedResultUser = new ArrayList<>();

        ExpectedResultUser.add(ConfProperties.getProperty("FirstName"));


    }

    public void RemoveCreatingUser(String user){

        ButtonCustomers = new WebDriverWait(driver, Duration.ofSeconds(5)).until(ExpectedConditions.elementToBeClickable(By.xpath("//*[contains(text(),'Customers')]")));

        ButtonCustomers.click();

        SearchCustomer = new WebDriverWait(driver, Duration.ofSeconds(5)).until(ExpectedConditions.elementToBeClickable(By.cssSelector("input[placeholder=\"Search Customer\"]")));

        SearchCustomer.sendKeys(user);

        ButtonDelete.click();


    }

    public void CreateDuplicateUser(String firstname,String LastName,String PostalCode){


            ButtonAddCustomer = new WebDriverWait(driver, Duration.ofSeconds(5)).until(ExpectedConditions.elementToBeClickable(By.xpath("//*[contains(text(),'Add Customer')]")));

            ButtonAddCustomer.click();

            FirstNameField= new WebDriverWait(driver, Duration.ofSeconds(5)).until(ExpectedConditions.elementToBeClickable(By.cssSelector("input[placeholder=\"First Name\"]")));

            FirstNameField.sendKeys(firstname);

            LastNameField.sendKeys(LastName);

            PostCodeField.sendKeys(PostalCode);

            AddCustomer.click();



        }




    public void AlphabetSorting(){


        ButtonCustomers = new WebDriverWait(driver, Duration.ofSeconds(5)).until(ExpectedConditions.elementToBeClickable(By.xpath("//*[contains(text(),'Customers')]")));

        ButtonCustomers.click();

        SortedByButtonFirstName = new WebDriverWait(driver, Duration.ofSeconds(5)).until(ExpectedConditions.elementToBeClickable(By.xpath("//*[contains(text(),'First Name')]")));

        SortedByButtonFirstName.click();
                                            //Сортировка в алфавитном порядке при помощи двойного щелчка
        SortedByButtonFirstName.click();

        ActualResult = ListOfCustomers.stream().map(x->x.getText()).collect(Collectors.toList());

        ExpectedResult= ListOfCustomers.stream().map(x->x.getText()).sorted().collect(Collectors.toList());


    }

    public void ReverseAlphabetSorting(){


        ButtonCustomers = new WebDriverWait(driver, Duration.ofSeconds(5)).until(ExpectedConditions.elementToBeClickable(By.xpath("//*[contains(text(),'Customers')]")));

        ButtonCustomers.click();

        SortedByButtonFirstName = new WebDriverWait(driver, Duration.ofSeconds(5)).until(ExpectedConditions.elementToBeClickable(By.xpath("//*[contains(text(),'First Name')]")));

        SortedByButtonFirstName.click();
        //Обратная сортировка


        ActualResult = ListOfCustomers.stream().map(x->x.getText()).collect(Collectors.toList());

        ExpectedResult= ListOfCustomers.stream().map(x->x.getText()).sorted(Collections.reverseOrder()).collect(Collectors.toList());


    }

    public void closeAlert(){

        driver.switchTo().alert().accept();
    }




    public List CheckActualSorting(){

        return ActualResult;
    }

    public List CheckExpectedSorting(){

        return ExpectedResult;
    }

    public List CheckExpectUser(){

        return ExpectedResultUser;
    }

    public List CheckActualUser(){

        return ActualResultUser;
    }

    public boolean CheckThatUserDeleted(){

        boolean CheckIt = ListOfCustomers.stream().map(x->x.getText()).collect(Collectors.toList()).

                contains(ConfProperties.getProperty("DeleteFirstName"));

          return CheckIt;
    }

    public String getAlertText(){

        return driver.switchTo().alert().getText();
    }

}
