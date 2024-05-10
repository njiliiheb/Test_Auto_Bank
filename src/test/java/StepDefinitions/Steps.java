
package StepDefinitions;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.sql.*;
import java.time.Duration;

public class Steps {
    private ChromeDriver driver;

    private String dbUsername;
    private String dbPassword;


    @Given("the user is on login page")
    public void the_user_is_on_login_page() throws InterruptedException, SQLException {
        System.setProperty("webdriver.chrome.driver", "/usr/bin/chromedriver");
        ChromeOptions options = new ChromeOptions();
        System.out.println("the user is on login page");
        driver = new ChromeDriver(options);
        driver.get("http://localhost:4200/login");
        Thread.sleep(2000);


        // Establish a connection to your database
        String dbUrl = "jdbc:mysql://localhost:3306/E-BANK";
        String dbUser = "root";
        String dbPass = "";
        Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbPass);

        // Execute a SQL query to retrieve the username and password
        String sql = "SELECT username, password FROM `e-bank`.app_user WHERE id = 1";
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);

        // Retrieve the username and password from the result set
        if (resultSet.next()) {
            dbUsername = resultSet.getString("username");
            dbPassword = resultSet.getString("password");
            System.out.println(dbUsername);
            System.out.println(dbPassword);
        }

        connection.close();
        Thread.sleep(2000);

    }




    @When("the user enters valid credentials")
    public void the_user_enters_valid_credentials() throws InterruptedException {

        System.out.println("the user enters valid credentials");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement usernameField = driver.findElement(By.id("username"));
        WebElement passwordField = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("password")));
        System.out.println("Entered valid username and password");
        usernameField.sendKeys(dbUsername);
        passwordField.sendKeys("iheb");
        Thread.sleep(2000);
    }
    @And("the user enters valid username")
    public void the_user_enters_valid_username() throws InterruptedException {

        System.out.println("Entered valid username ");
        WebElement passwordField = driver.findElement(By.id("username"));
        passwordField.sendKeys(dbUsername);
        Thread.sleep(2000);
    }
    @And("the user enters valid password")
    public void the_user_enters_valid_password() throws InterruptedException {
        System.out.println("Entered valid password ");
        WebElement passwordField = driver.findElement(By.id("password"));
        passwordField.sendKeys("iheb");
        Thread.sleep(2000);
    }

    @When("the user enters incorrect password")
    public void the_user_enters_incorrect_password() throws InterruptedException {
        System.out.println("Entered valid username and incorrect password");
        WebElement passwordField = driver.findElement(By.id("password"));
        passwordField.sendKeys("incorrectPassword");
        Thread.sleep(2000);
    }
    @When("the user enters incorrect username")
    public void the_user_enters_incorrect_username() throws InterruptedException {
        System.out.println("Entered valid username and incorrect username");
        WebElement usernameField = driver.findElement(By.id("username"));
        usernameField.sendKeys("incorrectUsername");
        Thread.sleep(2000);
    }

    @When("hits submit")
    public void hits_submit() throws InterruptedException {
        System.out.println("Clicked on submit");
        WebElement submitButton = driver.findElement(By.id("submit_button"));
        submitButton.click();
        Thread.sleep(2000);

    }

    @Then("the user should be logged in successfully")
    public void the_user_should_be_logged_in_successfully() throws InterruptedException {
        WebElement popup = driver.findElement(By.cssSelector(".swal2-popup.swal2-modal.my-popup-class.swal2-icon-success.swal2-show"));

        WebElement successMessage = popup.findElement(By.cssSelector(".swal2-title"));
        Assert.assertEquals("Connexion réussie", successMessage.getText());
        Thread.sleep(2000);
        driver.quit();
    }

    @Then("the user should see a login failure message incorrect username")
    public void the_user_should_see_a_login_failure_message_incorrect_username() throws InterruptedException {

        WebElement popup = driver.findElement(By.cssSelector(".swal2-popup.swal2-modal.my-popup-class.swal2-icon-error.swal2-show"));


        WebElement errorMessage = popup.findElement(By.cssSelector(".swal2-title"));
        Assert.assertEquals("Incorrect username", errorMessage.getText());
        Thread.sleep(2000);
        driver.quit();
    }
    @Then("the user should see a login failure message incorrect password")
    public void the_user_should_see_a_login_failure_message_incorrect_password() throws InterruptedException {

        WebElement popup = driver.findElement(By.cssSelector(".swal2-popup.swal2-modal.my-popup-class.swal2-icon-error.swal2-show"));


        WebElement errorMessage = popup.findElement(By.cssSelector(".swal2-title"));
        Assert.assertEquals("Incorrect password", errorMessage.getText());
        Thread.sleep(2000);
        driver.quit();
    }
}
