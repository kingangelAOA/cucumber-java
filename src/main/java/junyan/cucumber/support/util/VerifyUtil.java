package junyan.cucumber.support.util;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import junyan.cucumber.support.env.Config;
import junyan.cucumber.support.exceptions.InterfaceException;
import junyan.cucumber.support.script.Script;
import org.testng.Assert;

/**
 * Created by kingangelTOT on 15/11/8.
 */
public class VerifyUtil {
    public static String cookies(String cookies){
        cookies = Common.replace(cookies, "\"","");
        return cookies;
    }

    public static String headers(String headers, String global){
        JsonObject jsonObject = JsonUtil.toElement(headers).getAsJsonObject();
        try {
            if (jsonObject.has("Cookie")) {
                String cookies = Common.regularBrace(jsonObject.get("Cookie").getAsString(), global);
                cookies = cookies(cookies);
                jsonObject.add("Cookie", new JsonPrimitive(cookies));
            }
            headers = Common.regularBrace(new Gson().toJson(jsonObject), global);
        } catch (InterfaceException e) {
            e.printStackTrace();
        }
        return headers;
    }

    public static String pathOrText(String data){
        try {
            if (Common.verifyPath(data))
                data = Common.readFile(data);
            if (Common.hasBrance(data))
                data = Common.regularBrace(data, Config.GLOBAL);
            if (Script.hasScript(data))
                data = Script.evalScript(data);
            if (data != null && data.isEmpty())
                return null;

        } catch (InterfaceException e) {
            Assert.assertTrue(false, e.getMessage());
        }
        return data;
    }
}
