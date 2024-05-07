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


public class Steps6 {
    private ChromeDriver driver;
    private String idaccount1;
    public String dbamount;
    public String dbdescription;
    int randomamount ;

    public int generateRandomInt(int min, int max) {
        Random random = new Random();
        return random.nextInt(max - min + 1) + min;
    }
    @Given("the user is on accounts page1")
    public void the_user_is_on_accounts_page1() throws SQLException, InterruptedException {

       System.out.println("the user is on accounts page");
        driver = new ChromeDriver();
        driver.get("http://localhost:4200/accounts");
        Thread.sleep(2000);


        String dbUrl = "jdbc:mysql://localhost:3306/E-BANK";
        String dbUser = "root";
        String dbPass = "";
        Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbPass);


        String sql = "SELECT ID FROM `bank_account` LIMIT 1";
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);


        if (resultSet.next()) {
            idaccount1 = resultSet.getString("ID");

            System.out.println(idaccount1);

        }

        connection.close();
        Thread.sleep(2000);
    }


    @When("the user enters valid id account1")
    public void theUserEntersValidCredentialsOfAccount1() throws InterruptedException {

        System.out.println("Entered Valid id Of Account");
        WebElement idField = driver.findElement(By.id("idaccount"));
        idField.sendKeys(idaccount1);
        Thread.sleep(2000);

    }
    @And("hits search1")
    public void hits_search1() throws InterruptedException {

        System.out.println("Clicked on button search");
        WebElement accountsButton = driver.findElement(By.id("search2"));
        accountsButton.click();
        Thread.sleep(2000);

    }

    @And("hits check button credit")
    public void hits_check_button_credit() throws InterruptedException {

        System.out.println("Clicked on check button credit");
        WebElement creditButton = driver.findElement(By.id("credit"));
        creditButton.click();
        Thread.sleep(2000);

    }

    @And("the user enters valid amount and description1")
    public void the_user_enters_valid_amount_and_description1() throws InterruptedException {
        randomamount = generateRandomInt(1, 1000);
        System.out.println("Entered valid amount and description");
        WebElement amount = driver.findElement(By.id("amount"));
        amount.sendKeys(String.valueOf(randomamount));
        WebElement description = driver.findElement(By.id("description"));
        description.sendKeys("newcredit");
        Thread.sleep(2000);
    }
    @And("the user enters invalid amount and description1")
    public void the_user_enters_invalid_amount_and_description1() throws InterruptedException {
        System.out.println("Entered valid amount and description");
        WebElement amount = driver.findElement(By.id("amount"));
        amount.sendKeys("invalidamount");
        WebElement description = driver.findElement(By.id("description"));
        description.sendKeys("newcredit");
        Thread.sleep(2000);
    }
    @And("hits save operation1")
    public void hits_save_operation1() throws InterruptedException {
        System.out.println("Clicked on save operation");
        WebElement saveOperationButton = driver.findElement(By.id("saveoperation"));
        saveOperationButton.click();
        Thread.sleep(2000);
    }


    @Then("the user found the result in data base1")
    public void theUserFoundTheResultInDataBase1() throws SQLException, InterruptedException {
        String dbUrl = "jdbc:mysql://localhost:3306/E-BANK";
        String dbUser = "root";
        String dbPass = "";
        Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbPass);

        String sql = "SELECT amount ,description FROM `account_operation` WHERE amount=? and description='newcredit' limit 1";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, String.valueOf(randomamount));
        ResultSet resultSet = preparedStatement.executeQuery();


        if (resultSet.next()) {
            dbamount = resultSet.getString("amount");
            dbdescription = resultSet.getString("description");
            System.out.println("result found in database");
            System.out.println("Credit transaction ("+dbdescription+") of "+dbamount+" added to this account:"+idaccount1);

        }
        else {

                driver = new ChromeDriver();
                driver.get("http://localhost:4200/error");

        }

        connection.close();
        Thread.sleep(2000);
        driver.quit();
    }
    @Then("the user should see a failure message1")
    public void the_user_should_see_a_failure_message1() throws InterruptedException {
        System.out.println("credit operation failed ");
        WebElement popup = driver.findElement(By.cssSelector(".swal2-popup.swal2-modal.swal2-icon-error.swal2-show"));
        WebElement errorMessage = popup.findElement(By.cssSelector(".swal2-title"));
        Assert.assertEquals("error invalid amount.", errorMessage.getText());
        Thread.sleep(2000);
        driver.quit();
    }

}
