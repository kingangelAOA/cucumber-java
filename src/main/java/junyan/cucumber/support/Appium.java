package junyan.cucumber.support;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebElement;

import java.net.URL;

/**
 * Created by kingangeltot on 15/10/10.
 */
public class Appium extends AppiumDriver {

    public Appium(URL remoteAddress, Capabilities desiredCapabilities) {
        super(remoteAddress, desiredCapabilities);
    }

    @Override
    public WebElement scrollTo(String text) {
        return null;
    }

    @Override
    public WebElement scrollToExact(String text) {
        return null;
    }
}
