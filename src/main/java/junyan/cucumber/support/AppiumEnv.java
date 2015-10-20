package junyan.cucumber.support;

import com.esotericsoftware.yamlbeans.YamlException;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.io.FileNotFoundException;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Created by kingangeltot on 15/10/10.
 */
public class AppiumEnv extends Common{
    private String url;
    private String platform;
    private String browser;
    private Capabilities desiredCapabilities;
    private int defaultTimeOut = 10;
    private String driverName;
    private int timeOut = 0;
    private Object driver;

    private final String ANDROID_DRIVER = "io.appium.java_client.android.AndroidDriver";
    private final String IOS_DRIVER = "io.appium.java_client.ios.IOSDriver";
    private final String WEB_DRIVER = "org.openqa.selenium.remote.RemoteWebDriver";

    protected final String[] FIND_ELEMENT_METHOD = {
            "getNamedTextField",
            "findElementByIosUIAutomation",
            "findElementByAndroidUIAutomator",
            "scrollTo",
            "findElementByClassName",
            "findElementByAccessibilityId",
            "scrollToExact",
            "findElementByPartialLinkText",
            "findElementByLinkText",
            "findElementByCssSelector",
            "findElementByTagName",
            "findElementById",
            "findElementByXPath",
            "findElementByName"
    };

    protected final String[] FIND_ELEMENTS_METHOD = {
            "findElementsByAndroidUIAutomator",
            "findElementsByIosUIAutomation",
            "findElementsById",
            "findElementsByLinkText",
            "findElementsByPartialLinkText",
            "findElementsByTagName",
            "findElementsByName",
            "findElementsByClassName",
            "findElementsByCssSelector",
            "findElementsByXPath",
            "findElementsByAccessibilityId",
            "findElementsByIosUIAutomation"
    };

    public AppiumEnv() {
    }

    public void initData() throws UiExceptions, FileNotFoundException, YamlException {
        this.desiredCapabilities = initCapabilities(platform);
        setDriverName(platform);
    }

    public Object initDriver() throws InvocationTargetException, NoSuchMethodException, ClassNotFoundException, InstantiationException, IllegalAccessException, FileNotFoundException, YamlException, UiExceptions {
        initData();

        Object object = instantiate(driverName, new Class[] {URL.class, Capabilities.class}, new Object[] {getUrl(url), desiredCapabilities});
        setDriver((RemoteWebDriver)object);
        this.driver = object;
        return object;
    }

    public Object findElement(String how, String what) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        return execMethod(driver, how, toCollection(toList(what)));
    }

    private void setDriver(RemoteWebDriver driver){
        defaultTimeOut = timeOut == 0 ? defaultTimeOut : timeOut;
        driver.manage().timeouts().implicitlyWait(defaultTimeOut, TimeUnit.SECONDS);
    }

    private Capabilities initCapabilities(String platform) throws FileNotFoundException, YamlException, UiExceptions {
        Map<String, Object> map_com = deleteNull(toMapByYaml("/src/main/java/config/capabilities_common.yaml"));
        Map<String, Object> map_and = deleteNull(toMapByYaml("/src/main/java/config/capabilities_android.yaml"));
        Map<String, Object> map_ios = deleteNull(toMapByYaml("/src/main/java/config/capabilities_ios.yaml"));
        Capabilities capabilities;
        if (platform.equals("android"))
            capabilities = new DesiredCapabilities(toMap(map_com, map_and));
        else if (platform.equals("ios"))
            capabilities = new DesiredCapabilities(toMap(map_com, map_ios));
        else if (platform.equals("web")){
            if (browser == null)
                throw new UiExceptions("还没有设置浏览器...");
            capabilities = getBrowserCap(browser);
        }
        else
            throw new UiExceptions("不支持此平台:"+platform);
        return capabilities;
    }

    public void setDriverName(String driverName) throws UiExceptions {
        if (driverName.equals("android"))
            this.driverName = ANDROID_DRIVER;
        else if (driverName.equals("ios"))
            this.driverName = IOS_DRIVER;
        else if (driverName.equals("web"))
            this.driverName = WEB_DRIVER;
        else
            throw new UiExceptions("不支持次driver");
    }

    public String getDriverName() {
        return driverName;
    }

    public int getTimeOut() {
        return timeOut;
    }

    public void setTimeOut(int timeOut) {
        this.timeOut = timeOut;
    }

    public Object getDriver() {
        return driver;
    }

    public void setUrl(String url) {
        this.url = url+"/wd/hub";
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public void setBrowser(String browser){
        this.browser = browser;
    }

    private DesiredCapabilities getBrowserCap(String browser) throws UiExceptions {
        if (browser.equals("chrome"))
            return DesiredCapabilities.chrome();
        else if (browser.equals("firefox"))
            return DesiredCapabilities.firefox();
        else if (browser.equals("ie"))
            return DesiredCapabilities.internetExplorer();
        else if (browser.equals("safari"))
            return DesiredCapabilities.safari();
        else
            throw new UiExceptions("浏览器: "+browser+" 暂时不支持");

    }

}
