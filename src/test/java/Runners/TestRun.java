package Runners;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = {
                "src/test/resources/",

        },
        glue = {"StepDefinitions"},
        plugin = {"pretty", "json:target/cucumber-reports/cucumber.json" , "html:target/cucumber-reports/cucumber.html"}
)
public class TestRun {


}



