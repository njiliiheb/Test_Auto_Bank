package StepDefinitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.junit.Assert;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.sql.*;
import java.time.Duration;
import java.util.Random;
import java.util.UUID;

public class Steps2 {

    private String dbname;
    private String dbemail;

    private ChromeDriver driver;
    private String randomEmail;
    private String name;

    private static final String ALPHABET = "abcdefghijklmnopqrstuvwxyz";
    private static final int MIN_NAME_LENGTH = 3;
    private static final int MAX_NAME_LENGTH = 10;
    private Random random = new Random();
    private String generateRandomName() {
        int nameLength = random.nextInt(MAX_NAME_LENGTH - MIN_NAME_LENGTH + 1) + MIN_NAME_LENGTH;
        StringBuilder name = new StringBuilder(nameLength);
        for (int i = 0; i < nameLength; i++) {
            int index = random.nextInt(ALPHABET.length());
            char randomChar = ALPHABET.charAt(index);
            name.append(randomChar);
        }
        return name.toString();
    }
    private String generateRandomEmail() {
        return UUID.randomUUID() + "@gmail.com";
    }
    @Given("the user is on new customer page")
    public void the_user_is_on_new_customer_page() {
        System.out.println("the user is on new customer page");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("http://localhost:4200/new-customer");

    }

    @When("the user enters valid name")
    public void the_user_enters_valid_name() throws InterruptedException {
        name = generateRandomName();
        System.out.println("the user enters valid name ");
        WebElement passwordField = driver.findElement(By.id("name"));
        passwordField.sendKeys(name);
        Thread.sleep(2000);
    }
    @When("the user enters invalid name")
    public void the_user_enters_invalid_name() throws InterruptedException {
        System.out.println("the user enters invalid name");
        WebElement passwordField = driver.findElement(By.id("name"));
        passwordField.sendKeys("1256362@");
        Thread.sleep(2000);
    }
    @When("the user enters valid email")
    public void the_user_enters_valid_email() throws InterruptedException {
        randomEmail = generateRandomEmail();
        System.out.println("Entered valid valid email ");
        WebElement email = driver.findElement(By.id("email"));
        email.sendKeys(randomEmail);
        Thread.sleep(2000);
    }
    @When("the user enters email already exists")
    public void the_user_enters_email_already_exits() throws InterruptedException, SQLException {
        String dbUrl = "jdbc:mysql://localhost:3306/E-BANK";
        String dbUser = "root";
        String dbPass = "";
        Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbPass);
        String sql = "SELECT email FROM `customer`";
        PreparedStatement statement = connection.prepareStatement(sql);
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            dbemail = resultSet.getString("email");
            System.out.println(dbemail);
        }
        System.out.println("Entered valid valid email ");
        WebElement passwordField = driver.findElement(By.id("email"));
        passwordField.sendKeys(dbemail);
        Thread.sleep(2000);
        connection.close();
    }



    @When("the user enters invalid email")
    public void the_user_enters_invalid_email() throws InterruptedException {
        System.out.println("Entered invalid email ");
        WebElement passwordField = driver.findElement(By.id("email"));
        passwordField.sendKeys("ihebaklafhnbkeafk");
        Thread.sleep(2000);
    }

    @When("hits save")
    public void hits_save() throws InterruptedException {
        System.out.println("Clicked on save");
        WebElement saveButton = driver.findElement(By.id("Save"));
        saveButton.click();
        Thread.sleep(2000);
    }

    @Then("the user should see a success message")
    public void the_user_should_see_a_success_message() throws InterruptedException {

        System.out.println("User created successfully");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement popup = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".swal2-popup.swal2-modal.my-popup-class.swal2-icon-success.swal2-show")));
        WebElement successMessage = popup.findElement(By.cssSelector(".swal2-title"));
        Assert.assertEquals("Customer has been successfully saved!", successMessage.getText());
        Thread.sleep(2000);
        driver.quit();



    }
    @Then("the user should see a failure message with invalid name")
    public void the_user_should_see_a_failure_message1() throws InterruptedException {
        System.out.println("creation failed ");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement popup = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".swal2-popup.swal2-modal.my-popup-class.swal2-icon-error.swal2-show")));
        WebElement errorMessage = popup.findElement(By.cssSelector(".swal2-title"));
        Assert.assertEquals("Name is not valid! It should be at least 3 characters long.", errorMessage.getText());
        Thread.sleep(2000);
        driver.quit();
    }
    @Then("the user should see a failure message with invalid email")
    public void the_user_should_see_a_failure_message2() throws InterruptedException {
        System.out.println("creation failed ");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement popup = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".swal2-popup.swal2-modal.my-popup-class.swal2-icon-error.swal2-show")));
        WebElement errorMessage = popup.findElement(By.cssSelector(".swal2-title"));
        Assert.assertEquals("Email is not valid!", errorMessage.getText());
        Thread.sleep(2000);
        driver.quit();
    }


    @Then("the user should see a failure message with email already exists")
    public void the_user_should_see_a_failure_message4() throws InterruptedException {
        System.out.println("creation failed ");
        WebElement popup = driver.findElement(By.cssSelector(".swal2-popup.swal2-modal.my-popup-class.swal2-icon-error.swal2-show"));
        WebElement errorMessage = popup.findElement(By.cssSelector(".swal2-title"));
        Assert.assertEquals("Email already exists!", errorMessage.getText());
        Thread.sleep(2000);
        driver.quit();
    }

    @Then("the user should see the result imported form the database")
    public void the_user_should_see_the_result_imported_form_the_database() throws SQLException, InterruptedException {
        String dbUrl = "jdbc:mysql://localhost:3306/E-BANK";
        String dbUser = "root";
        String dbPass = "";
        Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbPass);

        // Execute a SQL query to retrieve the username and password
        String sql = "SELECT email,name FROM `customer` WHERE name=? and email=?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1,name);
        statement.setString(2,randomEmail);
        ResultSet resultSet = statement.executeQuery();

        if (resultSet.next()) {
            dbemail = resultSet.getString("email");
            dbname = resultSet.getString("name");
            System.out.println("result found in database");
            System.out.println(dbemail);
            System.out.println(dbname);
        }
        else {
            driver = new ChromeDriver();
            driver.get("http://localhost:4200/error");
        }


        connection.close();
        Thread.sleep(2000);
    }
}