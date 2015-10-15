package junyan.cucumber.support.uimodule;

import io.appium.java_client.NetworkConnectionSetting;
import io.appium.java_client.ios.IOSDriver;
import junyan.cucumber.support.UiDriverInterface;
import junyan.cucumber.support.UiExceptions;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebElement;

import java.net.URL;
import java.util.List;

/**
 * Created by kingangeltot on 15/7/21.
 */
public class MyIosDriver extends IOSDriver implements UiDriverInterface {
    public MyIosDriver(URL remoteAddress, Capabilities desiredCapabilities) {
        super(remoteAddress, desiredCapabilities);
    }

    @Override
    public boolean isLocked() throws UiExceptions {
        throw new UiExceptions("函数名:"+new Throwable().getStackTrace()[0].getMethodName()+",不是IOS driver方法");
    }

    @Override
    public WebElement findElementByAndroidUIAutomator(String using) throws UiExceptions  {
        throw new UiExceptions("函数名:"+new Throwable().getStackTrace()[0].getMethodName()+",不是IOS driver方法");
    }

    @Override
    public void sendKeyEvent(int key, Integer metastate) throws UiExceptions {
        throw new UiExceptions("函数名:"+new Throwable().getStackTrace()[0].getMethodName()+",不是IOS driver方法");
    }

    @Override
    public void sendKeyEvent(int key) throws UiExceptions {
        throw new UiExceptions("函数名:"+new Throwable().getStackTrace()[0].getMethodName()+",不是IOS driver方法");
    }

    @Override
    public void ignoreUnimportantViews(Boolean compress) throws UiExceptions {
        throw new UiExceptions("函数名:"+new Throwable().getStackTrace()[0].getMethodName()+",不是IOS driver方法");
    }

    @Override
    public List findElementsByAndroidUIAutomator(String using) throws UiExceptions {
        throw new UiExceptions("函数名:"+new Throwable().getStackTrace()[0].getMethodName()+",不是IOS driver方法");
    }

    @Override
    public String currentActivity() throws UiExceptions {
        throw new UiExceptions("函数名:"+new Throwable().getStackTrace()[0].getMethodName()+",不是IOS driver方法");
    }

    @Override
    public void pushFile(String remotePath, byte[] base64Data) throws UiExceptions {
        throw new UiExceptions("函数名:"+new Throwable().getStackTrace()[0].getMethodName()+",不是IOS driver方法");
    }

    @Override
    public void endTestCoverage(String String) throws UiExceptions {
        throw new UiExceptions("函数名:"+new Throwable().getStackTrace()[0].getMethodName()+",不是IOS driver方法");
    }

    @Override
    public void openNotifications() throws UiExceptions {
        throw new UiExceptions("函数名:"+new Throwable().getStackTrace()[0].getMethodName()+",不是IOS driver方法");
    }

    @Override
    public NetworkConnectionSetting getNetworkConnection() throws UiExceptions {
        throw new UiExceptions("函数名:"+new Throwable().getStackTrace()[0].getMethodName()+",不是IOS driver方法");
    }

    @Override
    public void setNetworkConnection(NetworkConnectionSetting connection) throws UiExceptions {
        throw new UiExceptions("函数名:"+new Throwable().getStackTrace()[0].getMethodName()+",不是IOS driver方法");
    }

    @Override
    public void startActivity(String appPackage, String appActivity, String appWaitPackage, String appWaitActivity) throws UiExceptions {
        throw new UiExceptions("函数名:"+new Throwable().getStackTrace()[0].getMethodName()+",不是IOS driver方法");
    }

    @Override
    public void startActivity(String appPackage, String appActivity) throws UiExceptions {
        throw new UiExceptions("函数名:"+new Throwable().getStackTrace()[0].getMethodName()+",不是IOS driver方法");
    }

    @Override
    public void toggleLocationServices() throws UiExceptions {
        throw new UiExceptions("函数名:"+new Throwable().getStackTrace()[0].getMethodName()+",不是IOS driver方法");
    }
}