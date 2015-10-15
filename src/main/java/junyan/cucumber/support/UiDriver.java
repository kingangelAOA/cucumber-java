package junyan.cucumber.support;

import io.appium.java_client.MobileElement;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kingangeltot on 15/10/10.
 */
public class UiDriver<D extends UiDriverInterface, E extends MobileElement> {
    private D driver;
    public UiDriver(D driver){
        this.driver = driver;
    }

    public MobileElement locationElement(String method, String content) throws UiExceptions {
        MobileElement mobileElement = null;
        switch (method){
            case "findElementById":
                mobileElement = (MobileElement) driver.findElementById(content);
                break;
            case "getNamedTextField":
                mobileElement = (MobileElement) driver.getNamedTextField(content);
                break;
            case "findElementByIosUIAutomation":
                mobileElement = (MobileElement) driver.findElementByIosUIAutomation(content);
                break;
            case "findElementByAndroidUIAutomator":
                mobileElement = (MobileElement) driver.findElementByAndroidUIAutomator(content);
                break;
            case "scrollTo":
                mobileElement = (MobileElement) driver.scrollTo(content);
                break;
            case "findElementByClassName":
                mobileElement = (MobileElement) driver.findElementByClassName(content);
                break;
            case "findElementByAccessibilityId":
                mobileElement = (MobileElement) driver.findElementByAccessibilityId(content);
                break;
            case "scrollToExact":
                mobileElement = (MobileElement) driver.scrollToExact(content);
                break;
            case "findElementByPartialLinkText":
                mobileElement = (MobileElement) driver.findElementByPartialLinkText(content);
                break;
            case "findElementByLinkText":
                mobileElement = (MobileElement) driver.findElementByLinkText(content);
                break;
            case "findElementByCssSelector":
                mobileElement = (MobileElement) driver.findElementByCssSelector(content);
                break;
            case "findElementByTagName":
                mobileElement = (MobileElement) driver.findElementByTagName(content);
                break;
            case "findElementByName":
                mobileElement = (MobileElement) driver.findElementByName(content);
                break;

            case "findElementByXPath":
                mobileElement = (MobileElement) driver.findElementByXPath(content);
                break;
        }
        if (mobileElement == null)
            throw new UiExceptions("定位单个元素关键字未找到.....请确定关键字.......");

        return mobileElement;
    }

    public List<MobileElement> locationElements(String method, String content) throws UiExceptions {
        List<MobileElement> list = new ArrayList<MobileElement>();

        switch (method){
            case "findElementsByAndroidUIAutomator":
                list = driver.findElementsByAndroidUIAutomator(content);
            case "findElementsById":
                list = driver.findElementsById(content);
            case "findElementsByLinkText":
                list = driver.findElementsByLinkText(content);
            case "findElementsByPartialLinkText":
                list = driver.findElementsByPartialLinkText(content);
            case "findElementsByTagName":
                list = driver.findElementsByTagName(content);
            case "findElementsByName":
                list = driver.findElementsByName(content);
            case "findElementsByClassName":
                list = driver.findElementsByClassName(content);
            case "findElementsByCssSelector":
                list = driver.findElementsByCssSelector(content);
            case "findElementsByXPath":
                list = driver.findElementsByXPath(content);
            case "findElementsByAccessibilityId":
                list = driver.findElementsByAccessibilityId(content);
            case "findElementsByIosUIAutomation":
                list = driver.findElementsByIosUIAutomation(content);
        }
        if (list.size() == 0)
            throw new UiExceptions("定位多个元素关键字未找到.....请确定关键字.......");

        return list;
    }
    public Object driverOperation(String keyWord, String content) throws UiExceptions {
        Object object = null;
        switch (keyWord){
            case "isLocked":
                return driver.isLocked();
            case "quite":
                driver.quit();
                break;
            case "close":
                driver.close();;
                break;
            case "sendKeyEvent":
                driver.sendKeyEvent((int)parseContent(content, keyWord));
                break;
            case "installApp":
                driver.installApp(content);
                break;
            case "removeApp":
                driver.removeApp(content);
                break;
            case "closeApp":
                driver.closeApp();
                break;
            case "resetApp":
                driver.resetApp();
                break;
            case "lockScreen":
                driver.lockScreen((int)parseContent(content, keyWord));
                break;
            case "getRemoteStatus":
                return driver.getRemoteStatus().toString();
        }
        return object;
    }

    public Object elementOperation(String keyWord, String content, E element){
        Object object = null;
        switch (keyWord) {
            case "click":
                element.click();
                break;
            case "getText":
                return element.getText();
            case "isDisplayed":
                return element.isDisplayed();
            case "sendKeys":
                element.click();
                element.sendKeys(content);
        }
        return object;
    }


    public Object parseContent(String contentStr, String keyWord) throws UiExceptions {
        if (keyWord.equals("sendKeyEvent") || keyWord.equals("lockScreen")){
            return Integer.parseInt(contentStr);
        }else
            throw new UiExceptions("关键字函数不支持解析次入参类型");
    }

}