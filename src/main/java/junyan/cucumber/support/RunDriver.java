package junyan.cucumber.support;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import io.appium.java_client.MobileElement;
import io.appium.java_client.remote.MobileCapabilityType;
import junyan.cucumber.support.models.*;
import junyan.cucumber.support.uimodule.MyAndroidDriver;
import junyan.cucumber.support.uimodule.MyIosDriver;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Created by kingangeltot on 15/10/10.
 */
public class RunDriver {
    private UiDriverInterface driver;
    private String platform;
    private URL defaultUrl;
    private DesiredCapabilities defaultCapabilities;
    private UiCommon uc;
    //    private Map keyWordMap;
    private UiDriver<UiDriverInterface, MobileElement> ud;
    private Element elementData;
    private Map transData;
    private MobileElement element;
    private Worker workerData;
    private Step runningStep;
    private TestSuite testSuite;
//    private List ran_step;

    private static final String CALLBACKURL = "http://127.0.0.1:3000";
    private static final String MSGPATH = "/msg_call_back";
    private static final String CASEBEGINPATH = "/case_begin";

    public RunDriver(Worker workerData){
        this.workerData = workerData;
        this.platform = workerData.getPlatform();
        this.defaultUrl = getLocalHost();

        this.uc = new UiCommon();
        this.transData = new HashMap();
//        this.keyWordMap = uc.getKeyWordMap("/config/key_word_map.yml");
    }

    public void runWorker(){

        for (TestSuite testSuite : workerData.getTestsuiteArr()){
            this.testSuite = testSuite;
            this.defaultCapabilities = getCapabilities();
            runTestsuites(testSuite);
        }
    }

    public void runTestsuites(TestSuite testSuite){

        runCase(testSuite.getCaseArr(), testSuite.getTestsuiteId());
    }



    public void runCase(List<Case> caseArr, String testsuiteId){
        UiDriverInterface driver = createDriver();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        String msg = null;
        String resultId = null;
        String imagePath = null;
        String url;
        try {
            for (Case caseOb : caseArr) {
                try {
                    url = CALLBACKURL + CASEBEGINPATH;
                    msg = "case begin";
//                    postMsg(url, setCaseBeginMsg(caseOb.getCaseId(), msg, testsuiteId));
                    driver.startActivity(testSuite.getAppPackage(), testSuite.getAppActivity());
                    this.ud = new UiDriver(driver);
                    List<Step> stepArr = caseOb.getSteps();
                    runStep(stepArr, ud);

                    List<Map> result = runAssert(caseOb.getAsserts());
                    msg = MaptoJsonStr(result);
                    System.out.println(msg);
                    resultId = "200";
//                    transData.clear();
//                    driver.closeApp();
                } catch (UiExceptions uiExceptons){
                    resultId = "201";
                    msg = uiExceptons.getMessage();
                    imagePath = getImagePath();
                    uc.screenshot((WebDriver) driver, imagePath);
                } catch (NoSuchElementException noSuchElementException){
                    resultId = "202";
                    msg = "by:"+runningStep.getElement().getBy()+"; value:"+runningStep.getElement().getValue()+"; 元素未找到,请确认.";
                    imagePath = getImagePath();
                    uc.screenshot((WebDriver) driver, imagePath);
                } catch (WebDriverException webDriverException){
                    resultId = "203";
                    msg = "使用uiautomator定位,但是值是用id的值引起这个未知的错误; "+"msg: "+webDriverException.getMessage();
                    imagePath = getImagePath();
                    uc.screenshot((WebDriver) driver, imagePath);
                }
//                finally {
//                    url = CALLBACKURL+ MSGPATH;
//                    if (resultId == null)
//                        throw new UiExceptions("未知错误");
//                    if (resultId.equals("200"))
//                        postMsg(url, setResultMsg(caseOb.getCaseId(), msg, resultId));
//                    else
//                        postImageMsg(url, setResultMsg(caseOb.getCaseId(), msg, resultId), imagePath);
//                    File file = new File(imagePath);
//                    file.delete();
//                    continue;
//                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            driver.quit();
        }
    }

    private String getImagePath() throws IOException {
        File file =new File(System.getProperty("user.dir")+"/huatuo-driver/image");
        if  (!file .exists()  && !file .isDirectory())
            file .mkdir();
        return String.valueOf(System.getProperty("user.dir")+"/huatuo-driver/image/"+System.currentTimeMillis())+".png";
    }

//    private void postImageMsg(String url, String json, String path) throws IOException {
//        HttpClient httpClient = new HttpClient();
//        String result = httpClient.postImage(url, path, json);
//        System.out.println(result);
//    }
//
//    private void postMsg(String url, String json) throws IOException {
//        HttpClient httpClient = new HttpClient();
//        String result = httpClient.post(url, json);
//        System.out.println(result);
//
//    }

    private String setCaseBeginMsg(String caseId, String msg, String testsuiteId){
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("code", "200");
        jsonObject.addProperty("msg", msg);
        jsonObject.addProperty("caseId", caseId);
        jsonObject.addProperty("testsuiteId", testsuiteId);
        return new Gson().toJson(jsonObject);
    }

    private String setResultMsg(String caseId, String msg, String resultId){
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("code", resultId);
        jsonObject.addProperty("msg", msg);
        jsonObject.addProperty("caseId", caseId);
        jsonObject.addProperty("flowStepId", runningStep.getFlowStepId());
        jsonObject.addProperty("testsuiteId", testSuite.getTestsuiteId());
        return new Gson().toJson(jsonObject);
    }

    public void runStep(List<Step> stepArr, UiDriver ud) throws UiExceptions {
        for(Step step: stepArr){
            String stepKeyWord = step.getKeyWord();

            if (step.getStepType().equals("element")){
                elementData = step.getElement();
//                    Map keyData = (Map)keyWordMap.get(elementData.getBy());
                List<MobileElement> elements = getElementList(elementData);
                if (elements.size() == 1)
                    element = elements.get(0);
                ud.elementOperation(step.getKeyWord(), step.getContent(), element);
            } else if(step.getStepType().equals("no element")){
                ud.driverOperation(stepKeyWord, step.getContent());
            }
            if (step.getTrans().size() > 0){
                for(Trans trans: step.getTrans()){
                    saveTrans(trans, String.valueOf(step.getFlowStepId()));
                }
            }
            runningStep = step;
//            ran_step.add(step.getFlowStepId());
        }
    }

    public List<MobileElement> getElementList(Element elementData) throws UiExceptions {
        String keyWord = elementData.getBy();
        List<MobileElement> list = new ArrayList();
        if (elementData.getAmount().equals("single")) {
            MobileElement element = ud.locationElement(keyWord, elementData.getValue());
            list.add(element);
        } else if (elementData.getAmount().equals("multiple")){
            list = ud.locationElements(keyWord, elementData.getValue());
        }
        return list;
    }

    public void saveTrans(Trans trans, String flowStepId){
        String transType = trans.getTransType();
        String transId = String.valueOf(trans.getTransId());
        try {
            if (transType.equals("element")){
                elementData = trans.getElement();
//                Map keyData = (Map)keyWordMap.get(elementData.getBy());
                List<MobileElement> elements =  getElementList(elementData);
                MobileElement  element = null;
                if (elements.size() == 1)
                    element = elements.get(0);
                Object object = ud.elementOperation(trans.getKeyWord(), trans.getTransValue(), element);
                putDataToTrans(transId, object, flowStepId);
            } else if (transType.equals("common")){
                putDataToTrans(transId, trans.getTransValue(), flowStepId);
            } else if (transType.equals("no element")){
                Object object = ud.driverOperation(trans.getKeyWord(), trans.getTransValue());
                putDataToTrans(transId, object, flowStepId);
            }
        } catch (UiExceptions uiExceptons) {
            uiExceptons.printStackTrace();
        }
    }

    public Map putDataToTrans(String transId, Object object, String flowStepId){
        Map map = new HashMap();
        if (object instanceof String){
            map.put(transId, object.toString());
        }else if (object instanceof Boolean){
            map.put(transId, ((Boolean)object).booleanValue());
        }
        if (transData.containsKey(flowStepId)) {
            Map result = (Map) transData.get(flowStepId);
            result.putAll(map);
        }
        else
            transData.put(flowStepId, map);
        return transData;
    }

    public List<Map> runAssert(List<Assert> assertArr){
        List<Map> resultList = new ArrayList<>();
        for(Assert assertOb: assertArr){
            Map result = new HashMap();
            Right right = assertOb.getRight();
            Left left = assertOb.getLeft();
            System.out.println("transData:"+transData);

            List<String> rightList = new ArrayList<>();
            List<String> leftList = new ArrayList<>();
            rightList.add(String.valueOf(right.getFlowStepId()));
            rightList.add(String.valueOf(right.getTransId()));
            leftList.add(String.valueOf(left.getFlowStepId()));
            leftList.add(String.valueOf(left.getTransId()));
            String assertType = assertOb.getAssertType();
            if (assertType.equals("String")){
                System.out.println("rightList:"+rightList);
                System.out.println("leftList:"+leftList);
                String rightResult = (String)getData(rightList);
                String leftResult = (String)getData(leftList);
                result.put("assertId", assertOb.getAssertId());
                result.put("rightResult", rightResult);
                result.put("leftResult", leftResult);
                if (rightResult.equals(leftResult)){
                    result.put("result", true);
                }else{
                    result.put("result", false);
                }
            }
            resultList.add(result);
        }
        return resultList;
    }

    private Object getData(List<String> index){
        Map map = (Map)transData.get(index.get(0));
        return map.get(index.get(1));
    }

    public DesiredCapabilities getCapabilities(){

        DesiredCapabilities capabilities = new DesiredCapabilities();
        if (workerData.getPlatform().equals("Android") || workerData.getPlatform().equals("ios")){
            capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, workerData.getPlatform());
            capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, workerData.getPlatformVersion());
            //            capabilities.setCapability(MobileCapabilityType.BROWSER_NAME, map.get(TestDataKey.BROWSER_NAME).toString());
            capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, workerData.getDeviceName());
//            capabilities.setCapability(MobileCapabilityType.UDID, workerData.getUdid());
            if (workerData.getPlatform().equals("Android")){
                capabilities.setCapability("unicodeKeyboard", workerData.isUnicodeKeyboard());
                capabilities.setCapability("ignoreUnimportantViews", workerData.isIgnoreUnimportantViews());
                capabilities.setCapability(MobileCapabilityType.APP_PACKAGE, testSuite.getAppPackage());
                capabilities.setCapability(MobileCapabilityType.APP_ACTIVITY, testSuite.getAppActivity());

            }else if(workerData.getPlatform().equals("ios")) {
                capabilities.setCapability("bundleId", testSuite.getBundleId());
                capabilities.setCapability("app", workerData.getApp());
            }
        }else if(workerData.getPlatform().equals("web")){
            //            capabilities.setCapability(CapabilityType.BROWSER_NAME, map.get(TestDataKey.BROWSER_NAME).toString());
            //            capabilities.setCapability(CapabilityType.PLATFORM, map.get(TestDataKey.PLATFORM).toString());
        }
        return capabilities;
    }

    private UiDriverInterface createDriver(){
        if (platform.equals("Android")) {
            driver = new MyAndroidDriver(defaultUrl, defaultCapabilities);
        }
        else if(platform.equals("ios"))
            driver = new MyIosDriver(defaultUrl, defaultCapabilities);
        return driver;
    }

    public URL getLocalHost(){
        URL caseUrl = null;
        try {
            caseUrl = new URL("http://localhost:4723/wd/hub");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return caseUrl;
    }

    public String MaptoJsonStr(List<Map> listMap) {
        Gson gson = new Gson();
        String jsonStr = gson.toJson(listMap);
        return jsonStr;
    }
}
