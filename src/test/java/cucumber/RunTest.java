package cucumber;


import com.github.mkolisnyk.cucumber.runner.ExtendedCucumber;
import com.github.mkolisnyk.cucumber.runner.ExtendedCucumberOptions;
import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

/**
 * Created by kingangeltot on 15/9/29.
 */
@RunWith(ExtendedCucumber.class)
@ExtendedCucumberOptions(
        jsonReport = "target/cucumber-docstring.json",
        retryCount = 0,
        detailedReport = true,
        detailedAggregatedReport = true,
        overviewReport = true,
        outputFolder = "target",
        reportPrefix = "cucumber-docstring-report")
@CucumberOptions(
        plugin = { "html:target/cucumber-docstring-html-report",
        "json:target/cucumber-docstring.json", "pretty:target/cucumber-docstring-pretty.txt",
        "usage:target/cucumber-docstring-usage.json", "junit:target/cucumber-docstring-results.xml" },
        features = { "src/test/java/resources/features" },
        tags = {"@web"},
        glue = { "junyan.cucumber" }
)

public class RunTest {

}