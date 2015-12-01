package junyan.cucumber.support.env;

import com.google.gson.*;
import com.jayway.restassured.response.Response;
import junyan.cucumber.support.models.RequestData;
import junyan.cucumber.support.util.*;


/**
 * Created by kingangelTOT on 15/10/1.
 */
public class InterfaceEnv {
    private RequestData requestData;
    private Response response;

    public InterfaceEnv(){
        System.setProperty("logPath", Config.API_LOG_PATH);
        this.requestData = new RequestData();
    }

    /**
     *执行http请求
     */
    public void beginHttp(){
        Response response = request();
        JsonObject newGlobal = JsonUtil.toElement(Config.GLOBAL).getAsJsonObject();
        JsonObject responseJson = new JsonObject();
        responseJson.add("response", getResponseJson(response));
        responseJson.add("request", getRequest());
        newGlobal.add(getRequestData().getInterfaceName(), responseJson);
        Config.GLOBAL = newGlobal.toString();
        this.response = response;
    }

    public JsonElement getResponseJson(Response response){
        String body;
        JsonObject responseOb = new JsonObject();
        responseOb.add("headers", JsonUtil.toElement(RestAssuredClientUtil.getHeaders(response.headers())));
        String contentType = response.getContentType();
        if (contentType.contains("application/json"))
            body = response.body().asString();
        else
            body = "{}";
        responseOb.add("body", JsonUtil.toElement(body));
        responseOb.add("code", JsonUtil.toElement(String.valueOf(response.statusCode())));
        setResponseLog(responseOb);
        return responseOb;
    }

    public JsonElement getRequest(){
        String requestBody = requestData.getBody();
        JsonObject jsonObject = new JsonObject();
        JsonElement body = JsonUtil.toElement(requestBody);
        if (body == null)
            if (Common.isForm(requestBody))
                body = JsonUtil.tojson(requestBody);
            else
                body = null;
        jsonObject.add("body", body);
        jsonObject.add("headers", JsonUtil.toElement(requestData.getHeaders()));
        return jsonObject;
    }

    private void setResponseLog(JsonObject response){
        Config.getLogger().info("**************response begin****************");
        if (response.get("code").getAsString().equals("200")) {
            Config.getLogger().info("headers:\n" + jsonPrettyPrint(response.get("headers").toString()));
            Config.getLogger().info("body:\n" + jsonPrettyPrint(response.get("body").toString()));
        }else{
            Config.getLogger().error("*******************error**********************");
            Config.getLogger().error("interfaceName: "+requestData.getInterfaceName());
            Config.getLogger().error("url: "+requestData.getUrl());
            Config.getLogger().error("headers:\n"+jsonPrettyPrint(RestAssuredClientUtil.getHeaders(RestAssuredClientUtil.getHeaderList(requestData.getHeaders())).toString()));
            Config.getLogger().error("method: "+requestData.getMethod());
            Config.getLogger().error("request_body:\n"+jsonPrettyPrint(requestData.getBody()));
            Config.getLogger().error("code: "+ response.get("code"));
            Config.getLogger().error("*******************error**********************\n");
        }
        Config.getLogger().info("**************response end****************\n");
    }


    public void updateGlobal(String json){
        JsonObject newGlobal = JsonUtil.toElement(Config.GLOBAL).getAsJsonObject();
        JsonObject updateJson = JsonUtil.toElement(json).getAsJsonObject();
        Config.GLOBAL = JsonUtil.update(newGlobal, updateJson).toString();
    }

    public Response request(){
        setRequestLog();
        return RestAssuredClientUtil.executeHttp(requestData);
    }

    public RequestData getRequestData(){
        return requestData;
    }

    private void setRequestLog(){
        Config.getLogger().info("**************request begin****************");
        Config.getLogger().info("interfaceName: "+requestData.getInterfaceName());
        Config.getLogger().info("url: "+requestData.getUrl());
        Config.getLogger().info("headers:\n"+jsonPrettyPrint(RestAssuredClientUtil.getHeaders(RestAssuredClientUtil.getHeaderList(requestData.getHeaders())).toString()));
        Config.getLogger().info("method: "+requestData.getMethod());
        Config.getLogger().info("body:\n"+jsonPrettyPrint(requestData.getBody()));
        Config.getLogger().info("**************request end****************\n");
    }

    public Response getResponse() {
        return response;
    }

    public static String jsonPrettyPrint(String json){
        if (json == null)
            return null;
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(json);
    }
}
