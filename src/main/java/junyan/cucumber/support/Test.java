package junyan.cucumber.support;

import org.openqa.selenium.Capabilities;
import sun.swing.CachedPainter;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.nio.channels.FileLock;
import java.util.*;

/**
 * Created by kingangelTOT on 15/10/6.
 */
public class Test extends Common{
    public static void test(CharSequence... cs){
        puts(cs);
        puts(cs.length);
        puts(cs.getClass());
    }
    public static void main(String[] args) throws IOException, InterfaceException, UiExceptions, InvocationTargetException, NoSuchMethodException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        AppiumEnv appiumEnv = new AppiumEnv();
//        appiumEnv.setBrowser("firefox");
        appiumEnv.setPlatform("android");
        appiumEnv.initData();
        URL url = getUrl("http://localhost:4723/wd/hub");
        Capabilities capabilities = appiumEnv.getDesiredCapabilities();
        Object driver = instantiate("org.openqa.selenium.remote.RemoteWebDriver", new Object[]{url, capabilities});
        execMethod(driver, "sendKeyEvent", new Object[]{4});
    }
}
