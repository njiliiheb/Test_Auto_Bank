package Utils;

import net.masterthought.cucumber.Configuration;
import net.masterthought.cucumber.ReportBuilder;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ReportGenerator {
    public static void generateCucumberReport() {
        File reportOutputDirectory = new File("target/cucumber-reports");
        List<String> jsonFiles = new ArrayList<>();
        jsonFiles.add("target/cucumber-reports/cucumber.json");

        Configuration configuration = new Configuration(reportOutputDirectory, "CucumberProject");
        configuration.setBuildNumber("1");


        configuration.addClassifications("Platform", "Windows");
        configuration.addClassifications("Browser", "Chrome");


        ReportBuilder reportBuilder = new ReportBuilder(jsonFiles, configuration);
        reportBuilder.generateReports();
    }
}