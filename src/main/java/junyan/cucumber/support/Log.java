package junyan.cucumber.support;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

/**
 * Created by kingangeltot on 15/10/27.
 */
public class Log extends Common{
    //Logger实例
    private Logger logger;
    //将Log类封装成单实例的模式，独立于其他类。以后要用到日志的地方只要获得Log的实例就可以方便使用
    private static Log log;
    //构造函数，用于初始化Logger配置需要的属性
    private Log(String path) {
        //获得日志类loger的实例
        logger=Logger.getLogger(this.getClass());
        //loger所需的配置文件路径
        puts(path);
        System.setProperty("my.log", path);

        PropertyConfigurator.configure(System.getProperty("user.dir") + "/src/main/java/config/log4j.properties");
    }

    public static Log getLoger(String path){
        if(log!=null)
            return log;
        else
            return new Log(path);
    }

    public Logger getLogger() {
        return logger;
    }
}
