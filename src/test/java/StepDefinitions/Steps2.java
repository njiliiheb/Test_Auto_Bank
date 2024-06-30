package StepDefinitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.util.Random;
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




    public String generateRandomUsername(int length) {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        Random random = new Random();
        StringBuilder username = new StringBuilder(length);

        for (int i = 0; i < length; i++) {
            int index = random.nextInt(characters.length());
            char randomChar = characters.charAt(index);
            username.append(randomChar);
        }

        return username.toString();
    }
    public String generateRandomEmail() {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        Random random = new Random();
        StringBuilder email = new StringBuilder(10);

        for (int i = 0; i < 10; i++) {
            int index = random.nextInt(characters.length());
            char randomChar = characters.charAt(index);
            email.append(randomChar);
        }

        email.append("@gmail.com");

        return email.toString();
    }
    @Given("the user on the web page")
    public void the_user_is_on_web_page() {
        System.out.println("the user on the web page");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://xrp.pub/share?code=n3UEI5HBnMOtpLEAENAbHLfyM9SBwggp");

    }

    @When("the user clicked on start")
    public void the_user_clicked_on_start()  {
        System.out.println("Clicked on start");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement startButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[@class='hero__text-btn btn']")));
        startButton.click();

    }
@When("the user enters valid username")
public void the_user_enters_valid_username()  {
    String randomUsername = generateRandomUsername(8);
    System.out.println("the user enters valid username: " + randomUsername);
    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    WebElement usernameField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("userName")));
    usernameField.sendKeys(randomUsername);
}
   @When("the user enters valid email")
public void the_user_enters_valid_email()  {
    randomEmail = generateRandomEmail();
    System.out.println("Entered valid email: " + randomEmail);
    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    WebElement emailField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("email")));
    emailField.sendKeys(randomEmail);
}



    @When("the user enters valid password")
    public void the_user_enters_valid_password()  {

        System.out.println("Entered valid password ");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement password = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("password")));
        password.sendKeys("12345678");

    }
    @When("the user enters valid password confirmation")
    public void the_user_enters_valid_password_confirmation()  {

        System.out.println("Entered valid password ");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement email = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("password2")));
        email.sendKeys("12345678");

    }




@When("the user hits i agree")
public void the_user_hits_i_agree() throws InterruptedException {
    System.out.println("Clicked on i agree");
    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    WebElement agree = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("agreement")));
    JavascriptExecutor executor = (JavascriptExecutor)driver;
    executor.executeScript("arguments[0].click();", agree);
}
@When("the user hits save")
public void hits_save() throws InterruptedException {
    System.out.println("Clicked on save");
    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    WebElement saveButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button/span[contains(text(), 'Register')]")));
    saveButton.click();
    Thread.sleep(2000);
}
@When("the user hits spin")
public void hits_spin() {
    System.out.println("Clicked on spin");
    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    WebElement spinButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[contains(text(), 'Spin')]")));
    spinButton.click();

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