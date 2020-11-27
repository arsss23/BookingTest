package runner;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions
        ( 		features="src/test/resource",
                glue="stepdefs",
                plugin = {"pretty",
                        "html:target/html/",
                        "json:target/json/file.json"},

                dryRun=false

        )
public class TestRunner {

}
