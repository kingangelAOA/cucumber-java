package junyan.cucumber.support.uimodule;

import io.appium.java_client.android.AndroidDriver;
import junyan.cucumber.support.UiDriverInterface;
import junyan.cucumber.support.UiExceptions;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebElement;

import java.net.URL;
import java.util.List;


/**
 * Created by kingangeltot on 15/7/21.
 */
public class MyAndroidDriver extends AndroidDriver implements UiDriverInterface {
    public MyAndroidDriver(URL remoteAddress, Capabilities desiredCapabilities){
        super(remoteAddress, desiredCapabilities);
    }

    @Override
    public void shake() throws UiExceptions {
        throw new UiExceptions("函数名:"+new Throwable().getStackTrace()[0].getMethodName()+",不是android driver方法");
    }

    @Override
    public void hideKeyboard(String keyName) throws UiExceptions {
        throw new UiExceptions("函数名:"+new Throwable().getStackTrace()[0].getMethodName()+",不是android driver方法");
    }

    @Override
    public void hideKeyboard(String strategy, String keyName) throws UiExceptions {
        throw new UiExceptions("函数名:"+new Throwable().getStackTrace()[0].getMethodName()+",不是android driver方法");
    }

    @Override
    public WebElement getNamedTextField(String name) throws UiExceptions {
        throw new UiExceptions("函数名:"+new Throwable().getStackTrace()[0].getMethodName()+",不是android driver方法");
    }

    @Override
    public WebElement findElementByIosUIAutomation(String using) throws UiExceptions {
        throw new UiExceptions("函数名:"+new Throwable().getStackTrace()[0].getMethodName()+",不是android driver方法");
    }

    @Override
    public void endTestCoverage(String String) throws UiExceptions {
        throw new UiExceptions("函数名:"+new Throwable().getStackTrace()[0].getMethodName()+",不是android driver方法");
    }

    @Override
    public List findElementsByIosUIAutomation(String using) throws UiExceptions {
        throw new UiExceptions("函数名:"+new Throwable().getStackTrace()[0].getMethodName()+",不是android driver方法");
    }
}