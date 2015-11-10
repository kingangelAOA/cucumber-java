package junyan.cucumber.support.env;

import com.google.gson.*;
import com.squareup.okhttp.Headers;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.Response;
import junyan.cucumber.support.exceptions.InterfaceException;
import junyan.cucumber.support.models.RequestData;
import junyan.cucumber.support.util.Common;
import junyan.cucumber.support.util.HttpClientUtil;
import junyan.cucumber.support.util.JsonUtil;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by kingangelTOT on 15/10/1.
 */
public class InterfaceEnv {

    public static String global = "{}";
    private RequestData requestData;
    public InterfaceEnv(){
        System.setProperty("logPath", (System.getProperty("user.dir")+"/target/extended-cucumber-report/cucumber.log"));
        this.requestData = new RequestData();
    }

    /**
     *执行http请求
     * @throws IOException
     * @throws InterfaceException
     */
    public void beginHttp(){
        Response response = getResponse();
        JsonObject newGlobal = JsonUtil.toElement(global).getAsJsonObject();
        JsonObject responseJson = new JsonObject();
        responseJson.add("responseBody", JsonUtil.toElement(getResponseBody(response)));
        responseJson.add("headers", JsonUtil.toElement(getHeaders(response.headers())));
        responseJson.add("requestBody", JsonUtil.toElement(requestData.getBody()));
        newGlobal.add(getRequestData().getInterfaceName(), responseJson);
        this.global = newGlobal.toString();
    }

    public String getResponseBody(Response response){
        String body = "";
        MediaType type = response.body().contentType();
        try {
            if (type.subtype().equals("json"))
                body = response.body().string();
            else
                body = "{}";
        } catch (IOException e) {
            e.printStackTrace();
        }
        return body;
    }

    public String getHeaders(Headers headers){
        Map map = toMap(headers.toMultimap());
        if (map.get("Set-Cookie") instanceof List) {
            Map<String, String> cookies = getCookies(((List<String>)map.get("Set-Cookie")));
            Map<String, Map<String, String>> cookie = new HashMap<>();
            cookie.put("Cookie", cookies);
            map.putAll(cookie);
            map.remove("Set-Cookie");
        }
        return new GsonBuilder().create().toJson(map, map.getClass());
    }

    public static Map toMap(Map<String, List<String>> map){
        Map newMap = new HashMap<>();
        for (String key : map.keySet()){
            List<String> list = map.get(key);
            if (list.size() == 1)
                newMap.put(key, list.get(0));
            else
                newMap.put(key, list);
        }
        return newMap;
    }

    public static Map<String, String> parseCookies(String cookie){
        return JsonUtil.toMap(cookie, "; ");
    }

    public static Map<String, String> getCookies(List<String> list){
        Map<String, String> cookies = new HashMap<>();
        for (String string:list){
            cookies.putAll(parseCookies(string));
        }
        return cookies;
    }

    public void updateGlobal(String json){
        JsonObject newGlobal = JsonUtil.toElement(global).getAsJsonObject();
        JsonObject updateJson = JsonUtil.toElement(json).getAsJsonObject();
        global = new Gson().toJson(JsonUtil.update(newGlobal, updateJson));
    }

    public Response getResponse(){
        return HttpClientUtil.executeHttp(requestData);
    }

    public RequestData getRequestData(){
        return requestData;
    }

}
