package junyan.cucumber.support.util;

import org.apache.log4j.Logger;

import javax.swing.plaf.PanelUI;
import java.util.Map;

/**
 * Created by kingangeltot on 15/11/10.
 */
public class Config {
    public static final String API_LOG_PATH = "/target/api";
    public static final String UI_LOG_PATH = "/target/ui";
    public static final String LOG_CAT_PATH = "/target/logcat";
    public static Map<String,String> DBCONFIG;

    public static Logger getLogger() {
        Logger logger = Logger.getLogger(Config.class);
        return logger;
    }

    public static void DBinit(String json){
        DBCONFIG = JsonUtil.toMap(JsonUtil.toElement(json));
    }
}
