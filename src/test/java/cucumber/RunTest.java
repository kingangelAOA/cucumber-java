package cucumber;


import com.github.mkolisnyk.cucumber.runner.ExtendedCucumber;
import com.github.mkolisnyk.cucumber.runner.ExtendedCucumberOptions;
import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

/**
 * Created by kingangeltot on 15/9/29.
 */
@RunWith(Cucumber.class)
@ExtendedCucumberOptions(
        jsonReport = "target/cucumber-report/cucumber-docstring.json",
        jsonUsageReport = "target/cucumber-report/cucumber-docstring-usage.json",
        usageReport = false,
        retryCount = 2,
        detailedReport = true,
        detailedAggregatedReport = true,
        overviewReport = true,
        outputFolder = "target/extended-cucumber-report",
        reportPrefix = "cucumber-docstring-report")
@CucumberOptions(
        plugin = { "html:target/cucumber-docstring-html-report",
        "json:target/cucumber-report/cucumber-docstring.json", "pretty:target/cucumber-report/cucumber-docstring-pretty.txt",
        "usage:target/cucumber-report/cucumber-docstring-usage.json", "junit:target/cucumber-report/cucumber-docstring-results.xml" },
        features = { "src/test/java/resources/features" },
        tags = {"@interface"},
        glue = { "junyan.cucumber" }
)

public class RunTest {

}