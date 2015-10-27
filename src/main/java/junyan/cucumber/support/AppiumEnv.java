package junyan.cucumber.support;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
    private static Object driver;
    private static JsonObject elementsData;

    private final String ANDROID_DRIVER = "io.appium.java_client.android.AndroidDriver";
    private final String IOS_DRIVER = "io.appium.java_client.ios.IOSDriver";
    private final String WEB_DRIVER = "org.openqa.selenium.remote.RemoteWebDriver";
    private static final Map runConf = toMapByYaml("/src/main/java/config/run.yaml");

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
        elementsData = getElements().getAsJsonObject();
    }

    public static JsonElement getElements(){
        String project = runConf.get("project").toString();
        List<String> list = new ArrayList<>();
        Map<String, Object> allMap = new HashMap<>();
        list = getFiles(System.getProperty("user.dir")+"/src/test/java/resources/UI_data/android_elements/" + project, list);
        for (String file:list){
            allMap = toMap(allMap, toMap(file));
        }
        return Json.toElement(new Gson().toJson(allMap));
    }

    public static JsonObject getElementsData() {
        return elementsData;
    }

    public static void setElementsData(JsonObject elementsData) {
        AppiumEnv.elementsData = elementsData;
    }

    /**
     * 初始化平台数据
     * @throws UiExceptions
     */
    public void initData() throws UiExceptions {
        initCapabilities(platform);
        setDriverName(platform);
    }

    /**
     * 初始化driver
     * @return
     * @throws UiExceptions
     */
    public Object initDriver() throws UiExceptions {
        initData();
        Object object;
        object = instantiate(driverName, new Object[]{getUrl(url), desiredCapabilities});
        setDriver((RemoteWebDriver)object);
        driver = object;
        return object;
    }

    /**
     * 查询元素
     * @param how
     * @param what
     * @return
     */
    public Object findElement(String how, String what){
        return execMethod(getDriver(), how, toCollection(toList(what)));
    }

    /**
     * 根据平台名称获取类名
     * @param driverName
     * @throws UiExceptions
     */
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

    public static Object getDriver() {
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

    /**
     * 根据浏览器获取Capabilities配置
     * @param browser
     * @return
     * @throws UiExceptions
     */
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

    /**
     * 设置driver配置
     * @param driver
     */
    private void setDriver(RemoteWebDriver driver){
        defaultTimeOut = timeOut == 0 ? defaultTimeOut : timeOut;
        driver.manage().timeouts().implicitlyWait(defaultTimeOut, TimeUnit.SECONDS);
    }

    /**
     * 获取Capabilities数据
     * @param platform
     * @throws UiExceptions
     */
    private void initCapabilities(String platform) throws UiExceptions {
        Map<String, Object> map_com = deleteNull(toMapByYaml("/src/main/java/config/capabilities_common.yaml"));
        Map<String, Object> map_and = deleteNull(toMapByYaml("/src/main/java/config/capabilities_android.yaml"));
        Map<String, Object> map_ios = deleteNull(toMapByYaml("/src/main/java/config/capabilities_ios.yaml"));
        if (platform.equals("android"))
            desiredCapabilities = new DesiredCapabilities(toMap(map_com, map_and));
        else if (platform.equals("ios"))
            desiredCapabilities = new DesiredCapabilities(toMap(map_com, map_ios));
        else if (platform.equals("web")){
            if (browser == null)
                throw new UiExceptions("还没有设置浏览器...");
            desiredCapabilities = getBrowserCap(browser);
        }
        else
            throw new UiExceptions("不支持此平台:"+platform);
    }

    public Capabilities getDesiredCapabilities(){
        return this.desiredCapabilities;
    }


}
