package Runners;
import Utils.EmailReport;
import Utils.ReportGenerator;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import javax.mail.MessagingException;
import java.io.IOException;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = {
                "src/test/resources/",

        },
        glue = {"StepDefinitions"},
        plugin = {"pretty", "json:target/cucumber-reports/cucumber.json" , "html:target/cucumber-reports/cucumber.html"}
)
public class Run {
    @BeforeClass
    public static void setup() {
    }
    @AfterClass
    public static void run() throws MessagingException, IOException {
        ReportGenerator.generateCucumberReport();
        String host = "smtp.gmail.com";
        String port = "587"; //
        String userName = "ihebnjili1@gmail.com";
        String password = "javu klnc alxk tvxq";
        String[] toAddresses = {"ihebnjili0@gmail.com", "ihebnjili00@gmail.com"};

        String subject = "Cucumber Report";
        String message = "Here is the Cucumber report.";
        String attachFile = "target/cucumber-reports/cucumber.html";
        EmailReport.sendEmailWithAttachment(host, port, userName, password, toAddresses, subject, message, attachFile);
    }
}



