package junyan.cucumber.support;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import com.squareup.okhttp.*;
import me.ele.api.it.model.PreCondition;
import sun.jvm.hotspot.tools.HeapSummary;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Map;

/**
 * Created by kingangeltot on 15/11/6.
 */
public class HttpClientUtil {

    public static String executeHttp(PreCondition preCondition){
        Headers headers = getHesder(preCondition.getHeaders());
        System.out.println(RequestBody.create(MediaType.parse(headers.get("content-type")), preCondition.getBody()));
        System.out.println(RequestBody.create(MediaType.parse(headers.get("content-type")), preCondition.getBody()).contentType());
        Request request = new Request.Builder()
                .url(preCondition.getUrl())
                .method(preCondition.getMethod(), RequestBody.create(MediaType.parse(headers.get("content-type")), preCondition.getBody()))
                .headers(getHesder(preCondition.getHeaders()))
                .build();
        String body = null;
        try {
            body = myExecute(request).body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return body;
    }

    public static Headers getHesder(String json){
        return Headers.of(toMap(toElement(json)));
    }

    public static JsonElement toElement(String json){
        return new JsonParser().parse(json);
    }

    public static Map<String, String> toMap(JsonElement jsonElement){
        Type type = new TypeToken<Map<String, String>>(){}.getType();
        return new Gson().fromJson(jsonElement, type);
    }

    /**
     * 执行http请求
     * @return
     * @throws IOException
     */
    public static Response myExecute(Request request){
        OkHttpClient client = new OkHttpClient();
        try {
            return client.newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
