package junyan.cucumber.support;

import com.github.mkolisnyk.cucumber.reporting.CucumberUsageReporting;
import com.google.gson.*;
import com.squareup.okhttp.Headers;
import com.squareup.okhttp.Response;
import cucumber.api.java.After;
import org.testng.Assert;

import javax.annotation.PreDestroy;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by kingangelTOT on 15/10/1.
 */
public class InterfaceEnv extends Http {

//    private Http http;
    private String url;
    private String method;
    private String contentType;
    private JsonElement global;
    private JsonElement requestHeaders;
    private JsonElement requestBody;
    private String interfaceName;
    private JsonElement cache;
    private Response response;

    public InterfaceEnv() throws IOException {
        this.global = new JsonParser().parse("{}");
        this.cache = new JsonParser().parse("{}");
        this.requestHeaders = new JsonParser().parse("{}");
        this.requestBody = new JsonParser().parse("{}");
    }


    /**
     *执行http请求
     *
     * @throws IOException
     * @throws InterfaceException
     */
    public void executeHttp() throws IOException, InterfaceException {
        Http http = new Http.Creater()
                    .method(method, setBodyByType(toJson(requestBody), contentType))
                    .url(url)
                    .headers(toMap(requestHeaders))
                    .create();
        Response response = http.myExecute();
        this.response = response;
        JsonElement jsonElement = new JsonParser().parse("{}");
        JsonObject interfaceCache = jsonElement.getAsJsonObject();
        interfaceCache.add("responseHeader", toElement(toJson(toMap(response.headers().toMultimap()))));
        interfaceCache.add("responseBody", toElement(response.body().string()));
        interfaceCache.add("requestHeaders", requestHeaders);
        interfaceCache.add("requestBody", requestBody);
        interfaceCache.add("code", toElement(String.valueOf(response.code())));
        cache.getAsJsonObject().add(interfaceName, interfaceCache);
    }

    /**
     * 设置请求headers
     * @param Headers
     *
     */
    public void setRequestHeaders(JsonElement Headers) {
        requestHeaders = update(requestHeaders.getAsJsonObject(), Headers.getAsJsonObject());
    }

    /**
     * 设置请求cookies
     * @param cookies
     * @throws InterfaceException
     */
    public void setCookies(String cookies) throws InterfaceException {
        cookies = regularBrace(cookies, global.getAsJsonObject());
        JsonObject cookiesOb =  new JsonParser().parse("{}").getAsJsonObject();
        cookiesOb.add("Cookie", toJsonPrimitive(cookies));
        requestHeaders = update(requestHeaders.getAsJsonObject(), cookiesOb);
    }

//    public void updateCookies(String list) throws InterfaceException {
//        JsonObject headersOb = requestHeaders.getAsJsonObject();
//        if (!headersOb.has("Cookie"))
//            throw new InterfaceException("在更新cookies前,先设置cookies");
//        JsonElement jsonElement = toElement(list);
//        Iterator<JsonElement> iterator = jsonElement.getAsJsonArray().iterator();
//        Map map = toMap(headersOb.get("Cookie").getAsString(), ";");
//        while (iterator.hasNext()){
//            JsonElement i =  iterator.next();
//            if (!(i instanceof JsonPrimitive))
//                throw new InterfaceException("list中的必须是字符串等,非对象集合");
//            JsonPrimitive jsonPrimitive = i.getAsJsonPrimitive();
//            String value;
//            String key = jsonPrimitive.getAsString();
//            if (jsonPrimitive.isString()){
//                value = global.getAsJsonObject().get(key).getAsString();
//            }else{
//                throw new InterfaceException("list中的元素只支持string类型");
//            }
//            if (!global.getAsJsonObject().has(key))
//                throw new InterfaceException("要获取的cookies不再全局变量中,请查看全局变量...");
//            map.put(key, value);
//        }
//    }

    /**
     * 获取缓存数据
     * @return 返回缓存数据
     */
    public JsonElement getCache(){
        return cache;
    }

    /**
     * 更新全局变量
     * @param newData
     * @return 全局变量
     */
    public JsonElement updateGlobal(JsonObject newData){
        this.global = update(global.getAsJsonObject(), newData);
        return  global;
    }

    /**
     * 设置全局变量
     * @param global
     * @return 全局变量
     */
    public JsonElement setGlobal(String global){
        this.global = toElement(global);
        return this.global;
    }

    /**
     * 更新全局变量
     * @param key
     * @param value
     * @return 全局变量
     * @throws InterfaceException
     */
    public JsonElement updateGlobal(String key, Object value) throws InterfaceException {
        global.getAsJsonObject().add(key,  getJsonPrimitiveType(value));
        return global;
    }

    /**
     *
     * @param url
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     *
     * @param method
     */
    public void setMethod(String method) {
        this.method = method;
    }

    /**
     *
     * @return
     */
    public String getMethod(){
        return this.method;
    }

    /**
     * 设置请求数据类型
     * @param contentType
     */
    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getContentType(){
        return contentType;
    }

    public void setGlobal(JsonElement global) {
        this.global = global;
    }

    public JsonElement getGlobal() {
        return global;
    }

    public JsonElement getRequestHeaders(){
        return requestHeaders;
    }

//    public Map updateRequestHeaders(Map newHeaders){
//        this.requestHeaders.putAll(newHeaders);
//        return requestHeaders;
//    }

    public void setRequestBody(JsonElement requestBody) {
        this.requestBody = requestBody;
    }

//    public void updateRequestBody(String key1, String key2) throws InterfaceException {
//        if (!requestBody.getAsJsonObject().has(key2))
//            throw new InterfaceException("requestBody中没有key: "+key2);
//        requestBody.put(key2, global.get(key1));
//
//    }

    public JsonElement getRequestBody(){
        return requestBody;
    }

    public String getInterfaceName() {
        return interfaceName;
    }

    public void setInterfaceName(String interfaceName) {
        this.interfaceName = interfaceName;
    }

    public Response getResponse() {
        return response;
    }
}
