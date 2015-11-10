package junyan.cucumber.support.util;

import com.squareup.okhttp.*;
import junyan.cucumber.support.models.RequestData;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Created by kingangeltot on 15/11/6.
 */
public class HttpClientUtil {

    public static Response executeHttp(RequestData requestData){
        setRequestLog(requestData);
        Headers headers = getHeader(requestData.getHeaders());
        String method = requestData.getMethod();
        Request request = new Request.Builder()
                .url(requestData.getUrl())
                .method(method, getRequestBody(method, headers, requestData))
                .headers(getHeader(requestData.getHeaders()))
                .build();
        Response response = myExecute(request);
        setResponseLog(response);
        return response;
    }

    public static RequestBody getRequestBody(String method, Headers headers, RequestData requestData){
        if (method.equals("GET") || method.equals("HEAD"))
            return null;
        else
            if (requestData.getBody() == null)
                requestData.setBody("{}");
        return RequestBody.create(MediaType.parse(headers.get("content-type")), requestData.getBody());
    }

    public static Headers getHeader(String json){
        return Headers.of(JsonUtil.toMap(JsonUtil.toElement(json)));
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

    /**
     * 执行http请求
     * @return
     * @throws IOException
     */
    public static Response myExecute(Request request){
        OkHttpClient client = new OkHttpClient();
        client.setConnectTimeout(20000, TimeUnit.MICROSECONDS);
        try {
            return client.newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static void setRequestLog(RequestData requestData){
        Config.getLogger().info("*****interfaceName: "+requestData.getInterfaceName()+" ************");
        Config.getLogger().info("*****url: "+requestData.getUrl()+" ************");
        Config.getLogger().info("*****headers: "+requestData.getHeaders()+" ************");
        Config.getLogger().info("*****method: "+requestData.getMethod()+" ************");
        Config.getLogger().info("*****body: "+requestData.getBody()+" ************");
    }

    private static void setResponseLog(Response response){
        if (response.code() == 200) {
            Config.getLogger().info("********headers: " + response.headers().toString() + " ********");
            try {
                Config.getLogger().info("********body: " + response.body().string() + " ********");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else
            Config.getLogger().error("********code: "+String.valueOf(response.code())+" ********");
    }
}
