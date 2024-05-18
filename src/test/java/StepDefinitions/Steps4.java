package StepDefinitions;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.sql.*;
import java.time.Duration;
public class Steps4 {

    private ChromeDriver driver;
    private String idaccount;
    @Given("the user is on home pages")
    public void theUserIsOnHomePages() {
        System.out.println("the user is on home page");

        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("http://localhost:4200/");

    }

    @When("the user hits accounts")
    public void theUserHitsAccounts() {
        System.out.println("Clicked on Accounts");
        WebElement accountsLink = driver.findElement(By.id("account"));
        JavascriptExecutor js = driver;
        js.executeScript("arguments[0].click();", accountsLink);
    }


    @And("the user enters valid credentials of account")
    public void theUserEntersValidCredentialsOfAccount() throws InterruptedException, SQLException {
        String dbUrl = "jdbc:mysql://localhost:3306/E-BANK";
        String dbUser = "root";
        String dbPass = "";
        Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbPass);


        String sql = "SELECT ID FROM `bank_account` LIMIT 1";
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);


        if (resultSet.next()) {
            idaccount = resultSet.getString("ID");

            System.out.println(idaccount);

        }
        WebElement idField = driver.findElement(By.id("idaccount"));
        System.out.println("Entered Valid id Of Account");
        idField.sendKeys(idaccount);
        Thread.sleep(2000);
    }
    @And("the user enters invalid credentials of account")
    public void theUserEntersInvalidCredentialsOfAccount() throws InterruptedException {
        WebElement idField = driver.findElement(By.id("idaccount"));
        System.out.println(" the user enters invalid credentials of account");
        idField.sendKeys("00000000000000000000000000");
        Thread.sleep(2000);
    }

    @And("hits button search of id account")
    public void hitsButtonSearchOfIdAccount() throws InterruptedException {
        System.out.println("Clicked on button search");

        // Use WebDriverWait for waiting until the element is visible
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

        WebElement searchButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("search2")));
        searchButton.click();
        Thread.sleep(2000);
    }

    @Then("transactions are displayed")
    public void transactionsAreDisplayed() throws InterruptedException {
        System.out.println("transactions are displayed");
        Thread.sleep(2000);
        driver.quit();

    }
    @Then("the user should see a failure message customer dont have transactions in this account or account not found")
    public void the_user_should_see_a_failure_message_customer_dont_have_transactions_in_this_account() throws InterruptedException {
        System.out.println("customer dont have transactions in this account  or account not found ");
        WebElement popup = driver.findElement(By.cssSelector(".swal2-popup.swal2-modal.my-popup-class.swal2-icon-error.swal2-show"));
        WebElement errorMessage = popup.findElement(By.cssSelector(".swal2-title"));
        Assert.assertEquals("customer dont have transactions in this account!", errorMessage.getText());
        Thread.sleep(2000);
        driver.quit();
    }

}

