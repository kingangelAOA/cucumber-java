package junyan.cucumber.step_definitions;

import com.esotericsoftware.yamlbeans.YamlException;
import com.google.gson.JsonObject;
import cucumber.api.java8.En;
import junyan.cucumber.support.AppiumEnv;
import junyan.cucumber.support.UiExceptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.RemoteWebElement;
import org.testng.Assert;

import java.io.FileNotFoundException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by kingangeltot on 15/10/10.
 */
public class AppiumSteps extends AppiumEnv implements En {
    private List<String> verifyList;
    private Map<String, Object> elements;
    public AppiumSteps(){
        verifyList = new ArrayList<>();
        elements = new HashMap<>();
        Given("^设置远程url (.*)$", (String url) -> {
            setUrl(url);
            verifyList.add("url");
        });

        And("^设置platform (.*)$", (String platform) -> {
            setPlatform(platform);
            verifyList.add("platform");
        });

        And("^设置web浏览器 (.*)$", (String browser) -> {
            setBrowser(browser);
            verifyList.add("url");
        });

        And("初始化driver", () -> {
            if (!(verifyList.contains("url") && verifyList.contains("platform")))
                Assert.assertTrue(false, "请先设置url或者platform");
            try {
                initDriver();
                verifyList.add("initDriver");
            } catch (InvocationTargetException | NoSuchMethodException | ClassNotFoundException | IllegalAccessException | InstantiationException | FileNotFoundException | YamlException | UiExceptions e) {
                e.printStackTrace();
                Assert.assertTrue(false, e.getMessage());
            }
        });
        And("^跳转到网页address (.*)$", (String address) -> {
            RemoteWebDriver driver = (RemoteWebDriver)getDriver();
            driver.get(address);
        });

        Given("^查询单个元素 (.*), 查询方法 (.*), 查询条件 (.*)$", (String elementName, String how, String what) -> {
            if (!verifyList.contains("initDriver"))
                Assert.assertTrue(false, "请先初始化driver");
            if (!toList(FIND_ELEMENT_METHOD).contains(how))
                Assert.assertTrue(false, "查询方法错误,或者不是查询单个元素的方法....");
            try {
                elements.put(elementName, findElement(how, what));
            } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                Assert.assertTrue(false, e.getMessage());
            }
        });

        Given("^设置超时时间 (.*)", (Integer time) -> {
            setTimeOut(time);
        });

        Given("^查看缓存的元素$", (String url) -> {
            puts(elements);
        });

//        Given("^查询多个元素 (.*), 查询方法 (.*), 查询条件 (.*)$", (String elementName, String how, String what) -> {
//            if (!verifyList.contains("initDriver"))
//                Assert.assertTrue(false, "请先初始化driver");
//            if (!toList(FIND_ELEMENTS_METHOD).contains(how))
//                Assert.assertTrue(false, "查询方法错误,或者不是查询多个元素的方法....");
//            try {
//                elements.put(elementName, findElement(how, what));
//            } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
//                Assert.assertTrue(false, e.getMessage());
//            }
//        });

        And("^点击元素 (.*)$", (String elementName) -> {
            if (!elementName.contains(elementName))
                Assert.assertTrue(false, "元素: " + elementName + " 不存在");
            try {
                execMethod(elements.get(elementName), "click", new Object[]{});
            } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
                Assert.assertTrue(false, e.getMessage());
            }
        });

        Given("^输入 (.*) 到元素 (.*)中$", (String value, String elementName) -> {
            if (!elementName.contains(elementName))
                Assert.assertTrue(false, "元素: "+elementName+" 不存在");
            try {
                execMethod(elements.get(elementName), "click", new Object[]{});
                execMethod(elements.get(elementName), "sendKeys", new Object[]{value});
            } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
                Assert.assertTrue(false, e.getMessage());
            }
        });

        Then("^元素 (.*) 的文本信息是否等于 (.*)$", (String elementName, String target) -> {
            if (!elementName.contains(elementName))
                Assert.assertTrue(false, "元素: " + elementName + " 不存在");
            try {
                String str = execMethod(elements.get(elementName), "getText", new Object[]{}).toString();
                Assert.assertEquals(str, target, "元素: " + elementName + " 的文本信息的只 " + str + " 不等于 " + target);
            } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
                Assert.assertTrue(false, e.getMessage());
            }
        });
    }
}
