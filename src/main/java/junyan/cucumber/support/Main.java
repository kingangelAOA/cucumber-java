package junyan.cucumber.support;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.path.json.JsonPath;

import java.io.File;

/**
 * Created by kingangeltot on 15/11/4.
 */
public class Main extends RestAssured{
    public static void puts(Object object){
        System.out.println(object);
    }
    public static void main(String[] args){
//        System.setProperty("logPath", (System.getProperty("user.dir")+"/target/rest/rest.log"));
//        Rest rest = new Rest();
//        rest.test();
//        puts(System.getProperty("user.dir")+"/src/main/java/config/test.yaml");
//        puts(toMapByYaml("/src/main/java/config/test.yaml"));
//
//
//        JsonPath jsonPath = new JsonPath(new File(System.getProperty("user.dir")+"/src/test/java/resources/interface_data/test_data.json"));
//        puts(jsonPath.get());
//        puts(jsonPath.get("g[0].cc"));

        JsonPath jsonPath = new JsonPath("{\"message\":\"hello world\"}");
        puts(jsonPath.get("aa"));
    }
}
