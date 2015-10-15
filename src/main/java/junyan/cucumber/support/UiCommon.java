package junyan.cucumber.support;

import com.esotericsoftware.yamlbeans.YamlException;
import com.esotericsoftware.yamlbeans.YamlReader;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.Augmenter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;

/**
 * Created by kingangeltot on 15/10/10.
 */
public class UiCommon {
    public Map getKeyWordMap(String yamlPath) throws YamlException, FileNotFoundException {
        YamlReader reader;
        Object object;
        Map map = null;

        reader = new YamlReader(new FileReader(System.getProperty("user.dir") + yamlPath));
        object = reader.read();
        map = (Map)object;

        return map;
    }

    public void screenshot(WebDriver driver, String path) throws IOException {
        WebDriver driver1 = new Augmenter().augment(driver);
        File file  = ((TakesScreenshot)driver1).getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(file, new File(path));
    }
}
