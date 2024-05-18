package StepDefinitions;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import org.junit.Assert;
import org.openqa.selenium.chrome.ChromeDriver;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import java.sql.*;
import java.util.Random;

public class Steps7 {
    private ChromeDriver driver;
    private String idaccount;
    private String idaccount2;
    private String typeaccount;
    private String customerid;
    public String dbamount;
    public String dbdescription;
     int randomamount ;

    public int generateRandomInt(int min, int max) {
        Random random = new Random();
        return random.nextInt(max - min + 1) + min;
    }
    @Given("the user is on accounts page2")
    public void the_user_is_on_accounts_page2() throws SQLException, InterruptedException {
        System.out.println("the user is on accounts page");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("http://localhost:4200/accounts");
        Thread.sleep(2000);


        String dbUrl = "jdbc:mysql://localhost:3306/E-BANK";
        String dbUser = "root";
        String dbPass = "";
        Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbPass);


        String sql = "SELECT TYPE ,ID ,customer_id FROM `bank_account` WHERE customer_id=1 LIMIT 1";
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);


        if (resultSet.next()) {
            idaccount = resultSet.getString("ID");
            typeaccount = resultSet.getString("TYPE");
            customerid = resultSet.getString("customer_id");
            System.out.println(idaccount);
            System.out.println(typeaccount);
            System.out.println(customerid);

        }

        connection.close();
        Thread.sleep(2000);
    }


    @When("the user enters valid id account2")
    public void theUserEntersValidCredentialsOfAccount2() throws InterruptedException {

        System.out.println("Entered Valid id Of Account");
        WebElement idField = driver.findElement(By.id("idaccount"));
        idField.sendKeys(idaccount);
        Thread.sleep(2000);

    }
    @And("hits search2")
    public void hits_search2() throws InterruptedException {

        System.out.println("Clicked on button search");
        WebElement accountsButton = driver.findElement(By.id("search2"));
        accountsButton.click();
        Thread.sleep(2000);

    }

    @And("hits check button transfer")
    public void hits_check_button_transfer() throws InterruptedException {

        System.out.println("Clicked on check button transfer");
        WebElement accountsButton = driver.findElement(By.id("transfer"));
        accountsButton.click();
        Thread.sleep(2000);

    }

    @And("the user enters valid account destination amount and description")
    public void the_user_enters_valid_account_destination_amount_and_description() throws InterruptedException, SQLException {

      randomamount = generateRandomInt(1, 1000);
        String dbUrl = "jdbc:mysql://localhost:3306/E-BANK";
        String dbUser = "root";
        String dbPass = "";
        Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbPass);


        String sql = "SELECT TYPE ,ID ,customer_id FROM `bank_account` WHERE customer_id=1 LIMIT 1 OFFSET 1";
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);

        if (resultSet.next()) {
            idaccount2 = resultSet.getString("ID");
            typeaccount = resultSet.getString("TYPE");
            customerid = resultSet.getString("customer_id");
            System.out.println(idaccount2);
            System.out.println(typeaccount);
            System.out.println(customerid);
        }

        connection.close();
        Thread.sleep(2000);

        System.out.println("Entered valid amount,account destination  and description");
        WebElement destination = driver.findElement(By.id("accountdestination"));
        destination.sendKeys(idaccount2);

        WebElement amount = driver.findElement(By.id("amount"));
        amount.sendKeys(String.valueOf(randomamount));

        WebElement description = driver.findElement(By.id("description"));
        description.sendKeys("newtransfer");
        Thread.sleep(2000);
    }
    @And("the user enters invalid account destination amount and description")
    public void the_user_enters_invalid_amount_and_description() throws InterruptedException {

        System.out.println("Entered invalid amount,account destination  and description");
        WebElement destination = driver.findElement(By.id("accountdestination"));
        destination.sendKeys("idaccount2");
        WebElement amount = driver.findElement(By.id("amount"));
        amount.sendKeys("invalidamount");
        WebElement description = driver.findElement(By.id("description"));
        description.sendKeys("newtransfer");
        Thread.sleep(2000);
    }
    @And("hits save operation2")
    public void hits_save_operation2() throws InterruptedException {
        System.out.println("Clicked on save operation");
        WebElement saveOperationButton = driver.findElement(By.id("saveoperation"));
        saveOperationButton.click();
        Thread.sleep(2000);
    }


    @Then("the user found the result in data base2")
    public void theUserFoundTheResultInDataBase2() throws SQLException, InterruptedException {
        String dbUrl = "jdbc:mysql://localhost:3306/E-BANK";
        String dbUser = "root";
        String dbPass = "";
        Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbPass);

        String sql = "SELECT amount,description,bank_account_id FROM `account_operation` WHERE amount='1000' and description='Transfer to a6c5c52d-66e9-4ddf-968c-fe050e2c692b 'limit 1";
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);


        if (resultSet.next()) {
            dbamount = resultSet.getString("amount");
            dbdescription = resultSet.getString("description");
            System.out.println("Transfer "+randomamount+" from "+idaccount+" to "+idaccount2);

        }
        else {
            driver = new ChromeDriver();
            driver.get("http://localhost:4200/error");
        }

        connection.close();
        Thread.sleep(2000);
        driver.quit();
    }
    @Then("the user should see a failure message2")
    public void the_user_should_see_a_failure_message2() throws InterruptedException {
        System.out.println("transfert operation failed ");
        WebElement popup = driver.findElement(By.cssSelector(".swal2-popup.swal2-modal.swal2-icon-error.swal2-show"));
        WebElement errorMessage = popup.findElement(By.cssSelector(".swal2-title"));
        Assert.assertEquals("An error while transferring.", errorMessage.getText());
        Thread.sleep(2000);
        driver.quit();
    }

}

