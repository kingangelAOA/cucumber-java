package junyan.cucumber.support;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.squareup.okhttp.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by kingangeltot on 15/9/30.
 */
public class Http extends Json {
    private static OkHttpClient client = new OkHttpClient();

    private String method;
    private String url;
    private RequestBody requestBody;
    private Map headersMap;
    private Request request;

    public Http(Http.Creater creater) throws InterfaceException {
        this.method = creater.method;
        this.url = creater.url;
        this.requestBody = creater.requestBody;
        this.headersMap = creater.headersMap;
        this.request = creater.request;
    }

    public Http() {

    }

    public void setType(){

    }

    /**
     * http数据构造器
     */
    public static class Creater{
        private String method;
        private String url;
        private RequestBody requestBody;
        private Map<String, String> headersMap;
        private Request request;

        public Creater(){
            headersMap = new HashMap<>();
        }
        public Creater method(String method, RequestBody requestBody){
            this.method = method;
            this.requestBody = requestBody;
            return this;
        }

        public Creater method(String method){
            this.method = method;
            return this;
        }

        public Creater url(String url){
            this.url = url;
            return this;
        }

        public Creater headers(Map<String, String> headersMap){
            this.headersMap = headersMap;
            return this;
        }

        public Http create() throws InterfaceException, IOException {
            Headers headers = Headers.of(headersMap);
            Request request = new Request.Builder()
                    .url(url)
                    .method(method, requestBody)
                    .headers(headers)
                    .build();
            this.request = request;
            return new Http(this);
        }
    }

    /**
     * 执行http请求
     * @return
     * @throws IOException
     */
    public Response myExecute() throws IOException {
        return client.newCall(request).execute();
    }

    /**
     * string cookies转map对象
     * @param cookie
     * @return
     */
    public Map<String, String> getCookies(String cookie){
        return toMap(cookie, "; ");
    }

    /**
     * JsonArray cookies转map对象
     * @param cookies
     * @return
     */
    public Map<String, String> getCookies(JsonArray cookies){
        Map<String, String> map = new HashMap<>();
        Iterator<JsonElement> iterator = cookies.iterator();
        while (iterator.hasNext()){
            JsonElement element = iterator.next();
            map.putAll(toMap(element.getAsJsonPrimitive().getAsString(), "; "));
        }
        return map;
    }

    /**
     * 通过cookie name获取cookie的value
     * @param headers
     * @param cookieName
     * @return
     */
    public String getCookiesValueByName(JsonElement headers, String cookieName){
        return getCookies(headers.getAsJsonObject().get("Set-Cookie").getAsJsonArray()).get(cookieName);
    }

//    public static void main(String[] args) throws IOException, InterfaceException {
//
//        String url = "http://localhost:3000/test1";
//        String json = "{\"data\":\"aaaaaaaa\"}";
//        Map<String, String> headers = new HashMap<>();
//        headers.put("Cookie", "SID=TKOuFAhMhrg4k03TS0ZfQp25ltGS6GXA8SkQ;USERID=8183916");
//        RequestBody response1 = new Http().setBodyByType(json, "JSON");
//        Http http = new Http.Creater()
//                .method("POST", response1)
//                .url(url)
//                .headers(headers)
//                .create();
//        Response response = http.myExecute();
//        System.out.println(response.body().string());
//        System.out.println(response.headers());
//        System.out.println(http.getCookies(response.headers("Set-Cookie")));
//    }
}
