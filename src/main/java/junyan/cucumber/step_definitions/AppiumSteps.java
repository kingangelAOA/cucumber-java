package junyan.cucumber.step_definitions;

import com.google.gson.JsonObject;
import cucumber.api.java8.En;
import junyan.cucumber.support.env.AppiumEnv;
import junyan.cucumber.support.exceptions.UiExceptions;
import junyan.cucumber.support.util.Reflect;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Assert;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by kingangeltot on 15/10/10.
 */
public class AppiumSteps extends AppiumEnv implements En{
    private List<String> verifyList;
    private Map<String, Object> elements;
    private Map<String, Object> global;
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
            } catch (UiExceptions uiExceptions) {
                Assert.assertTrue(false, uiExceptions.getMessage());
            }
            verifyList.add("initDriver");
        });

        Given("^打开应用 (.*) Activity (.*)$", (String app, String activity) -> {
            Reflect.execMethod(getDriver(), "startActivity", new Object[]{app, activity});
        });

        And("^跳转到网页address (.*)$", (String address) -> {
            RemoteWebDriver driver = (RemoteWebDriver)getDriver();
            driver.get(address);
        });

        Given("^查询单个元素 (.*), 查询方法 (.*), 查询条件 (.*)$", (String elementName, String how, String what) -> {
            if (!toList(FIND_ELEMENT_METHOD).contains(how))
                Assert.assertTrue(false, "查询方法错误,或者不是查询单个元素的方法....");
            elements.put(elementName, findElement(how, what));
        });

        Given("^查询单个元素 (.*)$", (String elementName) -> {
            if (getScenario() == null)
                Assert.assertTrue(false, "请先设置场景名称.....");
            if (!getElementsData().has(elementName))
                Assert.assertTrue(false, "元素: "+elementName+" 不存在");
            JsonObject jsonObject = getElementsData().get(elementName).getAsJsonObject();
            if (!toList(FIND_ELEMENT_METHOD).contains(jsonObject.get("method").getAsJsonPrimitive().getAsString()))
                Assert.assertTrue(false, "查询方法错误,或者不是查询单个元素的方法....");
            elements.put(elementName, findElement(jsonObject.get("method").getAsJsonPrimitive().getAsString(), jsonObject.get("value").getAsJsonPrimitive().getAsString()));

        });

        Given("^设置超时时间 (.*)", (Integer time) -> {
            setTimeOut(time);
        });

        Given("^启动log", () -> {
            String directory = System.getProperty("user.dir")+"/target/"+System.getProperty("project")+"/log";
            createDirectory(directory);
            runLogCat();
        });

        Given("^查看缓存的元素$", () -> {
            puts(elements);
        });

        Given("^设置场景名称 (.*)$", (String name) -> {
            deleteDir(new File(System.getProperty("user.dir")+"/target/"+System.getProperty("project")+"/pictures/"+getScenario()));
            setScenario(name);
        });

        Given("^截图", () -> {
            try {
                screenshot((WebDriver)getDriver(), System.getProperty("user.dir")+"/target/"+System.getProperty("project")+"/pictures/"+getScenario()+"/"+getTime()+".jpg");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        And("^点击元素 (.*)$", (String elementName) -> {
            if (!elementName.contains(elementName))
                Assert.assertTrue(false, "元素: " + elementName + " 不存在");
            Reflect.execMethod(elements.get(elementName), "click", new Object[]{});
        });

        And("^点击返回键$", () -> {
            Reflect.execMethod(getDriver(), "sendKeyEvent", new Object[]{4});
        });

        Given("^输入 (.*) 到元素 (.*) 中$", (String value, String elementName) -> {
            if (!elementName.contains(elementName))
                Assert.assertTrue(false, "元素: "+elementName+" 不存在");
            Reflect.execMethod(elements.get(elementName), "click", new Object[]{});
            Reflect.execMethod(elements.get(elementName), "sendKeys", new Object[]{new CharSequence[]{value}});

        });

        Then("^元素 (.*) 的文本信息是否等于 (.*)$", (String elementName, String target) -> {
            if (!elementName.contains(elementName))
                Assert.assertTrue(false, "元素: " + elementName + " 不存在");

            String str = Reflect.execMethod(elements.get(elementName), "getText", new Object[]{}).toString();
            Assert.assertEquals(str, target, "元素: " + elementName + " 的文本信息的只 " + str + " 不等于 " + target);
        });

        Given("^退出当前应用$", () -> {
            Reflect.execMethod(getDriver(), "quit", new Object[]{});
        });

        Given("^关闭log", () -> {
//            getThread().stop();
        });
    }
}
