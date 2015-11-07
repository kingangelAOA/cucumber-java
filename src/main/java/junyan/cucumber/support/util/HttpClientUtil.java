package junyan.cucumber.support.util;

import com.squareup.okhttp.*;
import junyan.cucumber.support.models.RequestData;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by kingangeltot on 15/11/6.
 */
public class HttpClientUtil {

    public static Response executeHttp(RequestData requestData){
        Headers headers = getHesder(requestData.getHeaders());
        Request request = new Request.Builder()
                .url(requestData.getUrl())
                .method(requestData.getMethod(), RequestBody.create(MediaType.parse(headers.get("content-type")), requestData.getBody()))
                .headers(getHesder(requestData.getHeaders()))
                .build();
        return myExecute(request);
    }

    public static Headers getHesder(String json){
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
        try {
            return client.newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
