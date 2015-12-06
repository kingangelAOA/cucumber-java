package junyan.cucumber.support.env;

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
    public static final String[] PARAMS = {"-e", "-ep"};
    public static String ENV;
    public static Map ENV_VALUE;
    public static DbUtil MYSQL;
    public static String GLOBAL = "{}";
    public static String ROOT_PATH;

    public static Logger getLogger() {
        return Logger.getLogger(Config.class);
    }

    public static void DBinit(String json){
        DBCONFIG = JsonUtil.toMap(JsonUtil.toElement(json));
    }

    public static Map<String, String> getHostConfig(){
        Map<String, Map<String, String>> hostConfig = (Map<String, Map<String, String>>)ENV_VALUE.get("host");
        return hostConfig.get(ENV);
    }

    public static Map<String, String> getDbCinfig(){
        Map<String, Map<String, String>> dbConfig = (Map<String, Map<String, String>>)ENV_VALUE.get("mysql");
        return dbConfig.get(ENV);
    }

    public static List<String> getPyPackage(){
        return (List<String>)ENV_VALUE.get("pythonPackage");
    }

    public static String getRootPath(){
        return ENV_VALUE.get("rootPath").toString();
    }
}