package cucumber;


import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

/**
 * Created by kingangeltot on 15/9/29.
 */
@RunWith(Cucumber.class)
@CucumberOptions(  monochrome = true,
        features = "src/test/java/resources/features",
        format = { "html: cucumber-html-reports",
                "json: cucumber-html-reports/cucumber.json" },
        dryRun = false,
        plugin = {"pretty"},
        glue = "junyan.cucumber"
)
public class RunTest {
}