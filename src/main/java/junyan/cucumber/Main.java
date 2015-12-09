package junyan.cucumber;

import com.fasterxml.jackson.databind.JsonNode;
import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.internal.JsonReader;
import com.jayway.jsonpath.spi.json.JacksonJsonNodeJsonProvider;
import com.jayway.jsonpath.spi.mapper.JacksonMappingProvider;
import junyan.cucumber.support.script.Python;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by kingangelTOT on 15/11/19.
 */
public class Main {
    private static final Configuration configuration = Configuration.builder()
            .jsonProvider(new JacksonJsonNodeJsonProvider())
            .mappingProvider(new JacksonMappingProvider())
            .build();

    public static void main(String[] args) throws FileNotFoundException, UnsupportedEncodingException, ScriptException {
//        String str = "{\"username\": {\"cc\":\"asdfasf\"}, \"name\": \"\\u989c\\u519b\", \"permission\": \"root\", \"created_at\": null, \"updated_at\": null, \"id\": 1, \"password\": \"111111\", \"email\": \"adfsadf\"}";
//        JsonNode updatedJson = JsonPath.using(configuration).parse(str).put("$.username", "aaa", true).json();
//        puts(updatedJson.toString());
        ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine engine = manager.getEngineByExtension("py");

// Using the eval() method on the engine causes a direct
// interpretataion and execution of the code string passed into it
        engine.eval("import sys");
       List<String> lists = (List<String>) engine.eval("sys.path");
        puts(lists);
//        List<String> lists = new ArrayList<>();
//        Python.initPython(lists);
//        Python.evalFunction("/Users/kingangeltot/Applications/git_work/cucumber-java/cucumber_project/eleme/python/test/test2.py", "get_data", "asf");
    }

    public static void puts(Object object){
        System.out.println(object);
    }

}
