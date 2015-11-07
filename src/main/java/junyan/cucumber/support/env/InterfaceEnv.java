package junyan.cucumber.support.env;

import com.google.gson.*;
import com.squareup.okhttp.Headers;
import com.squareup.okhttp.Response;
import junyan.cucumber.support.exceptions.InterfaceException;
import junyan.cucumber.support.models.RequestData;
import junyan.cucumber.support.util.HttpClientUtil;
import junyan.cucumber.support.util.JsonUtil;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by kingangelTOT on 15/10/1.
 */
public class InterfaceEnv {

    private String global;
    private RequestData requestData;

    public InterfaceEnv(){
        System.setProperty("logPath", (System.getProperty("user.dir")+"/target/extended-cucumber-report/cucumber.log"));
        this.requestData = new RequestData();
        this.global = "{}";
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
        try {
            responseJson.add("body", JsonUtil.toElement(response.body().string()));
            responseJson.add("headers", JsonUtil.toElement(getHeaders(response.headers())));
        } catch (IOException e) {
            e.printStackTrace();
        }
        newGlobal.add(getRequestData().getInterfaceName(), responseJson);
        this.global = newGlobal.toString();
    }

    public String getHeaders(Headers headers){
        Map map = toMap(headers.toMultimap());
        if (map.get("Set-Cookie") instanceof List) {
            Map cookies = getCookies(((List<String>)map.get("Set-Cookie")));
            map.putAll(cookies);
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
        this.global = new Gson().toJson(JsonUtil.update(newGlobal, updateJson));
    }

    public Response getResponse(){
        return HttpClientUtil.executeHttp(requestData);
    }

    public RequestData getRequestData(){
        return requestData;
    }

    public String getGlobal() {
        return global;
    }

    public void setGlobal(String global) {
        this.global = global;
    }
}
