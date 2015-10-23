package junyan.cucumber.support;

import java.util.Map;

/**
 * Created by kingangeltot on 15/10/23.
 */
public class Cache {
    private static Object driverCache;
    public Cache(){

    }

    public static void setDriver(Object driver){
        driverCache = driver;
    }
    public static Object getDriver(){
        return driverCache;
    }
}
