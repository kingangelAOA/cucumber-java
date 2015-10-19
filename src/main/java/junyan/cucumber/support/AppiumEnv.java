package junyan.cucumber.support;

import com.esotericsoftware.yamlbeans.YamlException;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.io.FileNotFoundException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.rmi.Remote;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Created by kingangeltot on 15/10/10.
 */
public class AppiumEnv {
    private URL url;
    private Capabilities desiredCapabilities;
    private int defaultTimeOut = 10;
    public AppiumEnv(String url, String platform) throws FileNotFoundException, UiExceptions, YamlException {
        this.url = Common.getUrl(url);
        this.desiredCapabilities = initCapabilities(platform);
    }

    public Object AppiumDriver(String className, String method, Object[] args) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException, ClassNotFoundException {

        Class<?> classType = Class.forName(className);
        classType.getConstructors();
        Constructor<?> constructor = classType
                .getConstructor(new Class<?>[]{URL.class, Capabilities.class});
        Object driver = constructor.newInstance(url, desiredCapabilities);
        RemoteWebDriver webDriver = (RemoteWebDriver)driver;
        webDriver.manage().timeouts().implicitlyWait(defaultTimeOut, TimeUnit.SECONDS);
        Object[] convertedArgs = new Object[args.length];
        Class<?>[] paramsClass = new Class[args.length];
        for (int i = 0; i < convertedArgs.length; i++) {
            paramsClass[i] = args[i].getClass();
        }

        Method getMethod = classType.getMethod(method, paramsClass);
        Object value = getMethod.invoke(driver, args);
        return value;
    }

    private Capabilities initCapabilities(String platform) throws FileNotFoundException, YamlException, UiExceptions {
        Map<String, Object> map_com = Common.deleteNull(Common.toMapByYaml("/src/main/java/config/capabilities_common.yaml"));
        Map<String, Object> map_and = Common.deleteNull(Common.toMapByYaml("/src/main/java/config/capabilities_android.yaml"));
        Map<String, Object> map_ios = Common.deleteNull(Common.toMapByYaml("/src/main/java/config/capabilities_ios.yaml"));
        Capabilities capabilities;
        if (platform.equals("android"))
            capabilities = new DesiredCapabilities(Common.toMap(map_com, map_and));
        else if (platform.equals("ios"))
            capabilities = new DesiredCapabilities(Common.toMap(map_com, map_ios));
        else
            throw new UiExceptions("不支持此平台:"+platform);
        return capabilities;
    }
}
