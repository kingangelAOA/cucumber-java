package junyan.cucumber.support.models;

import java.util.List;

/**
 * Created by kingangeltot on 15/7/28.
 */
public class TestSuite {
    private String appActivity;
    private String appPackage;
    private String bundleId;
    private String testsuiteId;
    private String testsuiteName;
    private String projectName;
    private List<Case> caseArr;

    public String getAppActivity() {
        return appActivity;
    }

    public void setAppActivity(String appActivity) {
        this.appActivity = appActivity;
    }

    public String getAppPackage() {
        return appPackage;
    }

    public void setAppPackage(String appPackage) {
        this.appPackage = appPackage;
    }

    public String getBundleId() {
        return bundleId;
    }

    public void setBundleId(String bundleId) {
        this.bundleId = bundleId;
    }

    public String getTestsuiteId() {
        return testsuiteId;
    }

    public void setTestsuiteId(String testsuiteId) {
        this.testsuiteId = testsuiteId;
    }

    public String getTestsuiteName() {
        return testsuiteName;
    }

    public void setTestsuiteName(String testsuiteName) {
        this.testsuiteName = testsuiteName;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public List<Case> getCaseArr() {
        return caseArr;
    }

    public void setCaseArr(List<Case> caseArr) {
        this.caseArr = caseArr;
    }
}
