package junyan.cucumber.support.util;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import com.jayway.restassured.path.json.JsonPath;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.RequestBody;
import junyan.cucumber.support.exceptions.InterfaceException;

import java.io.*;
import java.lang.reflect.Type;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by kingangeltot on 15/9/30.
 */
public class JsonUtil extends Common {

    /**
     * string转换成map, 分隔符是spacer
     */
    public static Map<String, String> toMap(String input, String spacer) {
        Map<String, String> map = new HashMap<>();

        String[] nameValuePairs = input.split(spacer);
        for (String nameValuePair : nameValuePairs) {
            String[] nameValue = nameValuePair.split("=");
            try {
                map.put(URLDecoder.decode(nameValue[0], "UTF-8"), nameValue.length > 1 ? URLDecoder.decode(
                        nameValue[1], "UTF-8") : "");
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException("This method requires UTF-8 encoding support", e);
            }
        }
        return map;
    }

    /**
     * JsonElement对象转map对象
     */
    public static Map<String, String> toMap(JsonElement jsonElement){
        Type type = new TypeToken<Map<String, ?>>(){}.getType();
        return new Gson().fromJson(jsonElement, type);
    }

    public static JsonElement tojson(String form){
        return toElement(tojson(toMap(form, "&")));
    }

    public static String tojson(Map map){
        Gson gson = new GsonBuilder().create();
        return gson.toJson(map);
    }

    /**
     * json字符串转JsonElement对象
     */
    public static JsonElement toElement(String json){
        try {
            return new JsonParser().parse(json);
        }catch (JsonSyntaxException e){
            return null;
        }
    }

    /**
     * 字符串转JsonPrimitive
     */
    public static JsonPrimitive toJsonPrimitive(String string) {
        return new JsonPrimitive(string);
    }

    /**
     * newData更新到target对象中
     */
    public static JsonElement update(JsonObject target, JsonObject newData){
        for (Map.Entry<String, JsonElement> map : newData.entrySet()) {
            target.add(map.getKey(), map.getValue());
        }
        return target;
    }
}