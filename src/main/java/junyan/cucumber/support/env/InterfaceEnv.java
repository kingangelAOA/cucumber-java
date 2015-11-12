package junyan.cucumber.support.env;

import com.google.gson.*;
import com.squareup.okhttp.Headers;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.Response;
import junyan.cucumber.support.exceptions.InterfaceException;
import junyan.cucumber.support.models.RequestData;
import junyan.cucumber.support.util.Common;
import junyan.cucumber.support.util.Config;
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
        System.setProperty("logPath", Config.API_LOG_PATH);
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
        responseJson.add("response", getResponse(response));
        responseJson.add("request", getRequest());
        newGlobal.add(getRequestData().getInterfaceName(), responseJson);
        this.global = newGlobal.toString();
        Config.getLogger().info("全局变量: "+global);
    }

    public JsonElement getResponse(Response response){
        String body = "";
        JsonObject responseOb = new JsonObject();
        responseOb.add("headers", JsonUtil.toElement(HttpClientUtil.getHeaders(response.headers())));
        MediaType type = response.body().contentType();
        try {
            if (type.subtype().equals("json"))
                body = response.body().string();
            else
                body = "{}";
        } catch (IOException e) {
            e.printStackTrace();
        }
        responseOb.add("body", JsonUtil.toElement(body));
        responseOb.add("code", JsonUtil.toElement(String.valueOf(response.code())));
        setResponseLog(responseOb);
        return responseOb;
    }

    public JsonElement getRequest(){
        JsonObject jsonObject = new JsonObject();
        jsonObject.add("body", JsonUtil.toElement(requestData.getBody()));
        jsonObject.add("headers", JsonUtil.toElement(requestData.getHeaders()));
        return jsonObject;
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

    private void setResponseLog(JsonObject response){
        Config.getLogger().info("**************response begin****************");
        if (response.get("code").getAsString().equals("200")) {
            Config.getLogger().info("headers: " + response.get("headers"));
            Config.getLogger().info("body: " + response.get("body"));
        }else{
            Config.getLogger().error("*******************error**********************");
            Config.getLogger().error("interfaceName: "+requestData.getInterfaceName());
            Config.getLogger().error("url: "+requestData.getUrl());
            Config.getLogger().error("headers: "+HttpClientUtil.getHeader(requestData.getHeaders()));
            Config.getLogger().error("method: "+requestData.getMethod());
            Config.getLogger().error("request_body: "+requestData.getBody());
            Config.getLogger().error("code: "+ response.get("code"));
            Config.getLogger().error("*******************error**********************\n");
        }
        Config.getLogger().info("**************response end****************\n");
    }


    public void updateGlobal(String json){
        JsonObject newGlobal = JsonUtil.toElement(global).getAsJsonObject();
        JsonObject updateJson = JsonUtil.toElement(json).getAsJsonObject();
        global = new Gson().toJson(JsonUtil.update(newGlobal, updateJson));
    }

    public Response getResponse(){
        setRequestLog();
        return HttpClientUtil.executeHttp(requestData);
    }

    public RequestData getRequestData(){
        return requestData;
    }

    private void setRequestLog(){
        Config.getLogger().info("**************request begin****************");
        Config.getLogger().info("interfaceName: "+requestData.getInterfaceName());
        Config.getLogger().info("url: "+requestData.getUrl());
        Config.getLogger().info("headers: "+HttpClientUtil.getHeader(requestData.getHeaders()));
        Config.getLogger().info("method: "+requestData.getMethod());
        Config.getLogger().info("body: "+requestData.getBody());
        Config.getLogger().info("**************request end****************\n");
    }

}
