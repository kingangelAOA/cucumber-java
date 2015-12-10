package junyan.cucumber.support.env;

import com.jayway.jsonpath.JsonPath;
import junyan.cucumber.support.util.DbUtil;
import junyan.cucumber.support.util.JsonUtil;
import org.apache.log4j.Logger;
import org.apache.xmlbeans.impl.xb.xsdschema.Public;

import java.util.List;
import java.util.Map;

/**
 * Created by kingangeltot on 15/11/10.
 */
public class Config {
    public static final String API_LOG_PATH = "/target/api";
    public static Map<String, ?> DBCONFIG;
    public static final String[] PARAMS = {"-e", "-pn", "-rp", "-m"};
    public static String ENV;
    public static String ENV_VALUE;
    public static String GLOBAL = "{}";
    public static String ROOT_PATH;
    public static String PROJECT;
    public static String MODULE;
    public static String RUN_PATH;


    public static Logger getLogger() {
        return Logger.getLogger(Config.class);
    }

    public static void DBinit(String json){
        DBCONFIG = JsonUtil.toMap(JsonUtil.toElement(json));
    }

    public static String getHostConfig(){
        return JsonPath.read(ENV_VALUE, "host."+ENV);
    }

    public static Map<String, String> getDbCinfig(){
       return JsonPath.read(ENV_VALUE, "DB."+ENV);
    }
}