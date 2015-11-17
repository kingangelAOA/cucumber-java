package junyan.cucumber.step_definitions;


import com.esotericsoftware.yamlbeans.YamlException;
import com.jayway.jsonpath.JsonPath;
import cucumber.api.java8.En;
import junyan.cucumber.support.util.*;
import junyan.cucumber.support.env.InterfaceEnv;
import junyan.cucumber.support.exceptions.InterfaceException;
import org.testng.Assert;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.*;

import static com.jayway.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.equalTo;

/**
 * Created by kingangeltot on 15/9/30.
 */
public class InterfaceSteps extends InterfaceEnv implements En {
    private List<String> verifyList;
    private DbUtil mysql;
    public InterfaceSteps() throws IOException {
        verifyList = new ArrayList<>();
        Given("^设置接口名称 (.*)$", (String name) -> {
            getRequestData().setInterfaceName(name);
            verifyList.add("interfaceName");
        });

        And("^设置请求url (.*)$", (String url) -> {
            if (Common.hasBrance(url)){
                try {
                    url = Common.regularBrace(url, InterfaceEnv.global);
                    url = url.replace("\"", "");
                } catch (InterfaceException e) {
                    Assert.assertTrue(false, e.getMessage());
                }
            }
            getRequestData().setUrl(url);
            verifyList.add("url");
        });

        And("^设置method (.*)$", (String method) -> {
            getRequestData().setMethod(method);
            verifyList.add("method");
        });

        Given("^设置DB:$", (String json) -> {
            Config.DBinit(json);
        });

        And("^设置请求数据:$", (String testData) -> {
            try {
                if (Common.verifyPath(testData))
                    testData = Common.readFile(testData);
                if (Common.hasBrance(testData)){
                    testData = Common.regularBrace(testData, InterfaceEnv.global);
                    getRequestData().setBody(testData);
                }
                if (testData.isEmpty())
                    Assert.assertEquals(false, "未加载到请求数据");
                getRequestData().setBody(testData);
            } catch (InterfaceException e) {
                Assert.assertTrue(false, e.getMessage());
            }
            verifyList.add("requestBody");
        });

        And("^设置headers:$", (String headers) -> {
                headers = VerifyUtil.headers(headers, InterfaceEnv.global);
                getRequestData().setHeaders(headers);
        });

        Given("^设置全局变量 (.*)$", (String global) -> {
            updateGlobal(global);
        });

        When("^执行请求$", () -> {
            beginHttp();
        });

        Given("^数据库中获取数据设置到全局变量中, sql (.*),获取行数 (.*), 获取的参数 (.*)$",
            (String sql, Integer index, String list) -> {
            try {
//                    mysql = new DbUtil(System.getProperty("env"));
                mysql = new DbUtil("debug");
                String json;
                if (Common.hasBrance(sql)){
                    sql = Common.regularBrace(sql, InterfaceEnv.global);
                    json = mysql.getDataBySql(sql, index, list);
                } else {
                    json = mysql.getDataBySql(sql, index, list);
                }
                updateGlobal(json);
            } catch (InterfaceException e) {
                Assert.assertTrue(false, e.getMessage());
            }
        });


        Then("^从全局变量中取出字段 (.*) 的值,是否等于 (.*)$", (String index, String expected) -> {
            Object actual = JsonPath.read(InterfaceEnv.global, index);
            Assert.assertEquals(actual.toString(), expected, "全局变量中的" + index + ": " + actual + ", 不等于: " + expected);
        });

        Then("^比较两个全局变量中的字段 (.*) 是否等于字段 (.*)$", (String index1, String index2) -> {
            Object actual1 = JsonPath.read(InterfaceEnv.global, index1);
            Object actual2 = JsonPath.read(InterfaceEnv.global, index2);
            Assert.assertEquals(actual1.toString(), actual2.toString(), "全局变量中的" + index1 + ": " + actual1 + ", 不等于" + index2 + ": " + actual2);
        });

        Then("^最近一次请求响应状态是否是 (.*)$", (Integer status) -> {
//            Assert.assertEquals(getResponse().code(), (int)status, "接口: " + getRequestData().getInterfaceName() + " code是: " + getResponse().code());
            getResponse().then().statusCode(status);
        });

        Then("^jsonSchema验证response (.*)$",
                (String classPath) -> {
            getResponse().then().body(matchesJsonSchemaInClasspath(classPath));
        });

        Then("^responseBody中的 (.*) 是否等于 (.*)$",
        (String jsonPath, String content) -> {
            getResponse().then().body(jsonPath, equalTo(content));
        });

        Then("^responseBody中header中的Content-Type是否等于 (.*)$",
                (String contentType) -> {
            getResponse().then().header("Content-Type", contentType);
        });

        Then("^responseBody中header中的 (.*) 是否等于 (.*)$",
                (String key, String contentType) -> {
            getResponse().then().header(key, contentType);
        });

        Then("^responseBody中的Cookie是否包含 (.*)$",
                (String cookieKey) -> {
            getResponse().then().cookie(cookieKey);
        });

        Then("^responseBody中Cookie的 (.*) 是否等于 (.*)$",
                (String key, String value) -> {
            getResponse().then().cookie(key, value);
        });

    }
    public static void puts(Object object){
        System.out.println(object);
    }
}
