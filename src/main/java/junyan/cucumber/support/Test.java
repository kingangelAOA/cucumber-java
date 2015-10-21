package junyan.cucumber.support;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

/**
 * Created by kingangelTOT on 15/10/6.
 */
public class Test extends Common{
    public static void test(CharSequence... cs){
        puts(cs);
        puts(cs.length);
        puts(cs.getClass());
    }
    public static void main(String[] args) throws IOException, InterfaceException, UiExceptions, InvocationTargetException, NoSuchMethodException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        String str = new String("aaaaaa");

        CharSequence[] cs = new String[]{"a","b"};

        test(cs);
    }
}
