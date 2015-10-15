package junyan.cucumber.support.models;

import java.util.List;

/**
 * Created by kingangeltot on 15/7/23.
 */
public class Worker {
    private String workerId;
    private String platform;
    private String deviceName;
    private String udid;
    private String platformVersion;

    private boolean unicodeKeyboard;
    private boolean ignoreUnimportantViews;

    private String app;
    private List<TestSuite> testsuiteArr;

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getUdid() {
        return udid;
    }

    public void setUdid(String udid) {
        this.udid = udid;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public String getWorkerId() {
        return workerId;
    }

    public void setWorkerId(String workerId) {
        this.workerId = workerId;
    }

    public boolean isUnicodeKeyboard() {
        return unicodeKeyboard;
    }

    public void setUnicodeKeyboard(boolean unicodeKeyboard) {
        this.unicodeKeyboard = unicodeKeyboard;
    }

    public boolean isIgnoreUnimportantViews() {
        return ignoreUnimportantViews;
    }

    public void setIgnoreUnimportantViews(boolean ignoreUnimportantViews) {
        this.ignoreUnimportantViews = ignoreUnimportantViews;
    }

    public String getPlatformVersion() {
        return platformVersion;
    }

    public void setPlatformVersion(String platformVersion) {
        this.platformVersion = platformVersion;
    }

    public String getApp() {
        return app;
    }

    public void setApp(String app) {
        this.app = app;
    }

    public List<TestSuite> getTestsuiteArr() {
        return testsuiteArr;
    }

    public void setTestsuiteArr(List<TestSuite> testsuiteArr) {
        this.testsuiteArr = testsuiteArr;
    }
}
