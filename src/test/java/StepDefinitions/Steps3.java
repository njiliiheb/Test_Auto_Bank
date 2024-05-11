package StepDefinitions;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.sql.*;
import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import java.util.logging.Level;
import io.cucumber.java.Scenario;



public class Steps3 {
    private WebDriver driver;
    private String dbname;


    @Given("the user is on home page")
    public void the_user_is_on_home_page() {
        System.out.println("the user is on home page");


        driver = new ChromeDriver();
        driver.get("http://localhost:4200/");

    }

       @When("the user hits customers")
      public void theUserHitsCustomers() throws InterruptedException {

        System.out.println("Clicked on Customers");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
           WebElement customersButton = driver.findElement(By.id("navbarDropdown"));
          customersButton.click();

        Thread.sleep(2000);
      }

    @And("hits search customers")
    public void hitsSearchCustomers() throws InterruptedException {
        System.out.println("Clicked on Search Customers");
        WebElement SearchCustomersButton = driver.findElement(By.id("Search customer"));
        SearchCustomersButton.click();
        Thread.sleep(2000);
    }

    @And("the user enters valid credentials of customers")
    public void theUserEntersValidCredentialsOfCustomers() throws InterruptedException, SQLException {
        String dbUrl = "jdbc:mysql://localhost:3306/E-BANK";
        String dbUser = "root";
        String dbPass = "";
        Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbPass);

        // Execute a SQL query to retrieve the username and password
        String sql = "SELECT name FROM `customer` limit 1";
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);

        // Retrieve the username and password from the result set
        if (resultSet.next()) {
            dbname= resultSet.getString("name");


        }

        connection.close();
        Thread.sleep(2000);


    WebElement nameField = driver.findElement(By.id("search1"));
        System.out.println("Entered name of client");
        nameField.sendKeys(dbname);
        Thread.sleep(2000);

    }
    @And("the user enters valid credentials of customers2")
    public void theUserEntersValidCredentialsOfCustomers2() throws InterruptedException, SQLException {
        String dbUrl = "jdbc:mysql://localhost:3306/E-BANK";
        String dbUser = "root";
        String dbPass = "";
        Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbPass);

        // Execute a SQL query to retrieve the username and password
        String sql = "SELECT name FROM `customer`  WHERE name !='iheb' limit 1 ";
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);

        // Retrieve the username and password from the result set
        if (resultSet.next()) {
            dbname= resultSet.getString("name");


        }

        connection.close();
        Thread.sleep(2000);
        WebElement nameField = driver.findElement(By.id("search1"));
        System.out.println("Entered name of client dont have accounts");
        nameField.sendKeys(dbname);
        Thread.sleep(2000);

    }

    @And("the user enters invalid credentials of customers")
    public void theUserEntersInvalidCredentialsOfCustomers() throws InterruptedException {

        WebElement nameField = driver.findElement(By.id("search1"));
        System.out.println("the user enters invalid credentials of customers");
        nameField.sendKeys("invalidname");
        Thread.sleep(2000);
    }

    @And("hits button search of account")
    public void hits_button_of_search() throws InterruptedException {
        System.out.println("Clicked on button search");

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        WebElement searchButton = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("searchButton")));
        searchButton.click();
        Thread.sleep(2000);
    }

    @And("hits Accounts")
    public void hitsAccounts() throws InterruptedException {
        System.out.println("Clicked on button accounts");


            WebElement accountsButton = driver.findElement(By.id("accounts"));
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
            accountsButton.click();
            Thread.sleep(2000);


    }



    @Then("the user should see a failure message customer dont have accounts")
    public void the_user_should_see_a_failure_message_customer_dont_have_accounts() throws InterruptedException {
        System.out.println("the user should see a failure message customer dont have accounts");


        WebElement popup = driver.findElement(By.cssSelector(".swal2-popup.swal2-modal.my-popup-class.swal2-icon-error.swal2-show"));
        WebElement errorMessage = popup.findElement(By.cssSelector(".swal2-title"));
        Assert.assertEquals("No accounts found for this customer!", errorMessage.getText());
        Thread.sleep(2000);
        driver.quit();
    }

    @Then("the user should see a failure message of invalid name customers")
    public void the_user_should_see_a_failure_message_of_invalid_name_customers() throws InterruptedException {
        System.out.println("invalid name of customer");
        WebElement popup = driver.findElement(By.cssSelector(".swal2-popup.swal2-modal.my-popup-class.swal2-icon-error.swal2-show"));
        WebElement errorMessage = popup.findElement(By.cssSelector(".swal2-title"));
        Assert.assertEquals("No customers found!", errorMessage.getText());
        Thread.sleep(2000);
        driver.quit();
    }

    @Then("Accounts are displayed")
    public void accountsAreDisplayed() throws InterruptedException {
        System.out.println("Accounts are displayed");
        Thread.sleep(2000);
        driver.quit();
    }


}
