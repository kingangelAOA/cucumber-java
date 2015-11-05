package junyan.cucumber.support;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.builder.RequestSpecBuilder;
import com.jayway.restassured.specification.RequestSpecification;

import static org.hamcrest.Matchers.equalTo;
import static com.jayway.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.hasXPath;

/**
 * Created by kingangeltot on 15/11/4.
 */
public class Rest extends RestAssured {
    public void test(){
        given().contentType(JSON).accept(JSON).
            body("{ \"message\" : \"hello world\"}").
        when().
            post("http://localhost:3000/test1").
        then().
//            body("message",equalTo("hello world"));
        body(hasXPath("/call_back/message[text()='hello world']"),equalTo("hello world"));
    }
}
