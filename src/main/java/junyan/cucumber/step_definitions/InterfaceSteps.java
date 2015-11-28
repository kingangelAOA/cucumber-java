package junyan.cucumber.step_definitions;


import com.jayway.jsonpath.JsonPath;
import cucumber.api.java.Before;
import cucumber.api.java8.En;
import cucumber.api.java8.StepdefBody;
import junyan.cucumber.support.env.Config;
import junyan.cucumber.support.script.Script;
import junyan.cucumber.support.util.*;
import junyan.cucumber.support.env.InterfaceEnv;
import junyan.cucumber.support.exceptions.InterfaceException;
import org.testng.Assert;

import java.io.IOException;

import static com.jayway.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.equalTo;

/**
 * Created by kingangeltot on 15/9/30.
 */
public class InterfaceSteps extends InterfaceEnv implements En{
    public InterfaceSteps() throws IOException {
        Given("^设置域名简称 (.*)$", (String abbreviation) -> {
            getRequestData().setHostAbbreviation(abbreviation);
        });

        Given("^设置接口名称 (.*)$", (String name) -> {
            getRequestData().setInterfaceName(name);
        });

        And("^设置path (.*)$", (String path) -> {
            if (Common.hasBrance(path)){
                try {
                    path = Common.regularBrace(path, Config.GLOBAL);
                    path = path.replace("\"", "");
                } catch (InterfaceException e) {
                    Assert.assertTrue(false, e.getMessage());
                }
            }
            getRequestData().setPath(path);
        });

        And("^设置method (.*)$", (String method) -> {
            getRequestData().setMethod(method);
        });

        Given("^设置DB:$", Config::DBinit);

        And("^设置请求数据:$", (String testData) -> {
            testData = VerifyUtil.pathOrText(testData);
            getRequestData().setBody(testData);
        });

        And("^设置headers:$", (String headers) -> {
            headers = VerifyUtil.headers(headers, Config.GLOBAL);
            getRequestData().setHeaders(headers);
        });

        And("^执行脚本,路径 (.*) 方法 (.*) 参数 (.*)$",
                (StepdefBody.A3<String, String, String>) Script::evalScript);

        Given("^设置全局变量 (.*)$", this::updateGlobal);

        Given("^查看全局变量$", () -> {
            Config.getLogger().info("全局变量:\n"+jsonPrettyPrint(Config.GLOBAL));
        });

        When("^执行请求$", this::beginHttp);

        Given("^数据库中获取数据设置到全局变量中, sql (.*),获取行数 (.*), 获取的参数 (.*)$",
            (String sql, Integer index, String list) -> {
            try {
                Config.MYSQL = new DbUtil();
                String json;
                if (Common.hasBrance(sql)){
                    sql = Common.regularBrace(sql, Config.GLOBAL);
                    json = Config.MYSQL.getDataBySql(sql, index, list);
                } else {
                    json = Config.MYSQL.getDataBySql(sql, index, list);
                }
                updateGlobal(json);
            } catch (InterfaceException e) {
                Assert.assertTrue(false, e.getMessage());
            }
        });


        Then("^从全局变量中取出字段 (.*) 的值,是否等于 (.*)$", (String index, String expected) -> {
            Object actual = JsonPath.read(Config.GLOBAL, index);
            Assert.assertEquals(actual.toString(), expected, "全局变量中的" + index + ": " + actual + ", 不等于: " + expected);
        });

        Then("^比较两个全局变量中的字段 (.*) 是否等于字段 (.*)$", (String index1, String index2) -> {
            Object actual1 = JsonPath.read(Config.GLOBAL, index1);
            Object actual2 = JsonPath.read(Config.GLOBAL, index2);
            Assert.assertEquals(actual1.toString(), actual2.toString(), "全局变量中的" + index1 + ": " + actual1 + ", 不等于" + index2 + ": " + actual2);
        });

        Then("^最近一次请求响应状态是否是 (.*)$", (Integer status) -> {
//            Assert.assertEquals(getResponse().code(), (int)status, "接口: " + getRequestData().getInterfaceName() + " code是: " + getResponse().code());
            getResponse().then().statusCode(status);
        });

        Then("^jsonSchema验证response:$",
                (String testData) -> {
            testData = VerifyUtil.pathOrText(testData);
            getResponse().then().body(matchesJsonSchemaInClasspath(testData));

        });

        Then("^当前responseBody中的 (.*) 是否等于 (.*)$",
        (String jsonPath, String content) -> {
            getResponse().then().body(jsonPath, equalTo(content));
        });

        Then("^当前responseBody中header的Content-Type是否等于 (.*)$",
                (String contentType) -> {
            getResponse().then().header("Content-Type", contentType);
        });

        Then("^当前responseBody中header的 (.*) 是否等于 (.*)$",
                (String key, String contentType) -> {
            getResponse().then().header(key, contentType);
        });

        Then("^当前responseBody中的Cookie是否包含 (.*)$",
                (String cookieKey) -> {
            getResponse().then().cookie(cookieKey);
        });

        Then("^当前responseBody中Cookie的 (.*) 是否等于 (.*)$",
                (String key, String value) -> {
            getResponse().then().cookie(key, value);
        });

    }

    @Before()
    public void test(){

    }

//    public static void puts(Object object){
//        System.out.println(object);
//    }
}
