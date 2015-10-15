package junyan.cucumber.step_definitions;

import com.google.gson.*;
import com.squareup.okhttp.Headers;
import cucumber.api.java8.En;
import junyan.cucumber.support.*;
import org.testng.Assert;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.sql.SQLException;
import java.util.*;

/**
 * Created by kingangeltot on 15/9/30.
 */
public class InterfaceSteps extends InterfaceEnv implements En {
    private List<String> verifyList;
    private final List<String> NO_BODY = new ArrayList<>(Arrays.asList("interfaceName", "url", "method"));
    private final List<String> BODY = new ArrayList<>(Arrays.asList("interfaceName", "url", "method",
            "contentType", "requestBody"));
    private final Mysql mysql;
    public InterfaceSteps() throws IOException, SQLException, ClassNotFoundException {
        verifyList = new ArrayList<>();
        mysql = new Mysql(getYaml("/src/main/resources/config/run.yaml").get("run").toString());
        Given("^设置接口名称 (.*)$", (String name) -> {
            setInterfaceName(name);
            verifyList.add("interfaceName");
        });

        And("^设置请求url (.*)$", (String url) -> {
            if (hasBrance(url)){
                try {
                    url = regularBrace(url, getGlobal());
                    url = url.replace("\"", "");
                } catch (InterfaceException e) {
                    Assert.assertTrue(false, e.getMessage());
                }
            }
            setUrl(url);
            verifyList.add("url");
        });

        And("^设置method (.*)$", (String method) -> {
            setMethod(method);
            verifyList.add("method");
        });

        And("^设置请求数据格式 (.*)$", (String type) -> {
            setContentType(type);
            verifyList.add("contentType");
        });

        And("^设置请求数据:$", (String testData) -> {

            try {
                if (verifyPath(testData)) {
                    Reader reader = new FileReader(System.getProperty("user.dir") + testData);
                    JsonElement jsonElement = toElement(reader);
                    testData = toJson(jsonElement);
                }
                if (hasBrance(testData)){
                    setRequestBody(toElement(regularBrace(testData, getGlobal())));
                } else {
                    setRequestBody(toElement(testData));
                }
            } catch (InterfaceException | FileNotFoundException e) {
                Assert.assertTrue(false, e.getMessage());
            }
            verifyList.add("requestBody");
        });

        Given("^设置cookies (.*)$", (String cookies) -> {
            try {
                if (hasBrance(cookies)){
                    cookies = regularBrace(cookies, getGlobal());
                    cookies = cookies.replace("\"", "");
                    setCookies(cookies);

                } else {
                    setCookies(cookies);
                }
            } catch (InterfaceException e) {
                Assert.assertEquals(false, e.getMessage());
            }
        });

        And("^设置headers (.*)$", (String json) -> {
            setRequestHeaders(toElement(json));
        });

        Given("^设置全局变量 (.*)$", (String global) -> {
            setGlobal(global);
        });

        When("^执行请求$", () -> {
            if (getMethod() == null)
                Assert.assertTrue(false, "请先设置method...");
            if (getMethod().equals("HEAD") || getMethod().equals("GET"))
                if (!verifyList.containsAll(NO_BODY))
                    Assert.assertTrue(false, "interfaceName, url, method 其中一项没有设置....");
            else
                if (!verifyList.containsAll(BODY))
                    Assert.assertTrue(false, "interfaceName, url, method, contentType, requestBody 其中一项没有设置....");
            try {
                executeHttp();
            } catch (IOException | InterfaceException e) {
                Assert.assertTrue(false, e.getMessage());
            }
        });

//        And("^从全局变量中取 (.*) 设置到请求body中的 (.*)中去$", (String key1, String key2) -> {
//            try {
//                updateRequestBody(key1, key2);
//            } catch (InterfaceException e) {
//                Assert.assertTrue(false, e.getMessage());
//            }
//        });

        Given("^查看所有cache$", () -> {
            puts(toJson(getCache()));
        });

        Given("^从接口 (.*) 的response的body中获取值,值索引为 (.*),字段名称为: (.*),设置到全局变量中$",
            (String interfaceName, String index, String name) -> {
            update("response", interfaceName, index, name);
        });

        Given("^从接口 (.*) 的request的body中获取值,值索引为: (.*),字段名称为: (.*),设置到全局变量中$",
            (String interfaceName, String index, String name) -> {
            update("request", interfaceName, index, name);
        });

        Given("^从接口 (.*) 中获取response的cookies值, 字段 (.*), 设置到全局变量中$", (String interfaceName, String list) -> {
            setCookiesToGlobal(interfaceName, list);
        });

//        Given("^从全局变量中获取 (.*), 更新到cookies中$", (String list) -> {
//            try {
//                updateCookies(list);
//            } catch (InterfaceException e) {
//                Assert.assertTrue(false, e.getMessage());
//            }
//        });

        Given("^数据库中获取数据设置到全局变量中, sql (.*),获取行数 (.*), 获取的参数 (.*)$",
            (String sql, Integer index, String list) -> {
                try {
                    JsonElement jsonElement = mysql.getDataBySql(sql, index, list);
                    updateGlobal(jsonElement.getAsJsonObject());
                } catch (InterfaceException e) {
                    Assert.assertTrue(false, e.getMessage());
                }
        });

        Given("^查看全局变量$", () -> {
            puts(getGlobal());
        });

        Then("^从全局变量中取出字段 (.*) 的值,是否等于 (.*)$", (String key, String expected) -> {
            String actual = getGlobal().getAsJsonObject().get(key).toString();
            Assert.assertEquals(actual, expected, "全局变量中的" + key + ": " + actual + ", 不等于: " + expected);
        });

        Then("^比较两个全局变量中的字段 (.*) 是否等于字段 (.*)$", (String key1, String key2) -> {
            String actual1 = getGlobal().getAsJsonObject().get(key1).toString();
            String actual2 = getGlobal().getAsJsonObject().get(key2).toString();
            Assert.assertEquals(actual1, actual2, "全局变量中的" + key1 + ": " + actual1 + ", 不等于" + key2 + ": " + actual2);
        });

        Then("^最近一次请求响应状态是否是 (.*)$", (Integer status) -> {
            Assert.assertEquals(getResponse().code(), (int)status, "接口: " + getInterfaceName() + " code是: " + getResponse().code());

        });

        Then("^接口 (.*) response的body中的 (.*) 的值是否包含这些字段 (.*)$",
        (String interfaceName, String indexList,String list) -> {
            if (!getCache().getAsJsonObject().has(interfaceName))
                Assert.assertTrue(false, "cache中没有此接口: "+interfaceName+" 的数据");
            JsonElement jsonElement = toElement(list);
            JsonElement interfaceCache = getCache().getAsJsonObject().get(interfaceName);
            JsonElement responseBody= interfaceCache.getAsJsonObject().get("responseBody");
            try {

                if (jsonElement.isJsonArray()) {
                    Iterator<JsonElement> iterator = jsonElement.getAsJsonArray().iterator();
                    while (iterator.hasNext()) {
                        JsonElement element = iterator.next();
                        if (!element.isJsonPrimitive())
                            Assert.assertTrue(false, "list中只能是原始数据....");
                        if (!element.getAsJsonPrimitive().isString())
                            Assert.assertTrue(false, "list中中的元素只能是字符串");
                        Assert.assertTrue(getDataByIndex(indexList, responseBody).getAsJsonObject().has(element.getAsJsonPrimitive().getAsString()),
                                "response的body中没有此字段: " + element.getAsJsonPrimitive().getAsString());
                    }
                }
            } catch (InterfaceException e) {
                Assert.assertEquals(false, e.getMessage());
            }

        });

        Then("^test (.*), asdfasdf$", (List jsonOb) -> {
            puts(jsonOb);
            String str = jsonOb.get(3).toString();
            puts(str);
            puts(isNumeric(str));
        });

        Then("^test1 (.*), asdfasdf$", (Map jsonOb) -> {
            puts(jsonOb.getClass());
            puts(jsonOb);
        });
    }

    public void update(String type, String interfaceName, String index, String name) {
        if (!getCache().getAsJsonObject().has(interfaceName))
            Assert.assertTrue(false, "cache中没有此接口: "+interfaceName+" 的数据");
        JsonElement jsonElement = null;
        if (type.equals("request"))
            jsonElement = getRequestBody().getAsJsonObject().get(interfaceName);
        if (type.equals("response")) {
            JsonElement interfaceCache = getCache().getAsJsonObject().get(interfaceName);
            jsonElement = interfaceCache.getAsJsonObject().get("responseBody");
        }
        try {
            JsonElement element = getDataByIndex(index, jsonElement);
            JsonObject newGlobal = new JsonParser().parse("{}").getAsJsonObject();
            newGlobal.add(name, element.getAsJsonPrimitive());
            updateGlobal(newGlobal);
        } catch (InterfaceException e) {
            Assert.assertTrue(false, e.getMessage());
        }
    }

    public void setCookiesToGlobal(String interfaceName, String list){
        JsonObject jsonObject = getCache().getAsJsonObject().get(interfaceName).getAsJsonObject();
        JsonElement headers = jsonObject.get("responseHeader");
        JsonArray jsonArray;
        try {
            jsonArray = toElement(list).getAsJsonArray();
            Iterator<JsonElement> iterator = jsonArray.iterator();
            while (iterator.hasNext()){
                Object i =  iterator.next();
                if (!(i instanceof JsonPrimitive))
                    throw new InterfaceException("list中的必须是字符串等,非对象集合");
                JsonPrimitive jsonPrimitive = (JsonPrimitive)i;
                Object type = getJsonPrimitiveType(jsonPrimitive);
                String value;
                String key = jsonPrimitive.getAsString();
                if (type instanceof String){
                    value = getCookiesValueByName(headers, key);
                }else{
                    throw new InterfaceException("list中的元素只支持string类型");
                }
                updateGlobal(key, value);
            }
        } catch (InterfaceException e) {
            Assert.assertTrue(false, e.getMessage());
        }
    }
}
