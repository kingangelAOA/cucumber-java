package junyan.cucumber.support;

import com.esotericsoftware.yamlbeans.YamlException;
import com.esotericsoftware.yamlbeans.YamlReader;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by kingangeltot on 15/10/8.
 */
public class Common {

    /**
     * 读取yaml文件
     * @param yamlPath
     * @return
     * @throws FileNotFoundException
     * @throws YamlException
     */
    public Map getYaml(String yamlPath) throws FileNotFoundException, YamlException {
        YamlReader reader;
        Object object;
        Map map;
        System.out.println(System.getProperty("user.dir") + yamlPath);
        reader = new YamlReader(new FileReader(System.getProperty("user.dir") + yamlPath));

        object = reader.read();
        map = (Map)object;

        return map;
    }

    public String getClassName(Object object){
        return object.getClass().getSimpleName();
    }

    public static void puts(Object object){
        System.out.println(object);
    }

    /**
     * 验证此字符串是否是文件路径
     * @param path
     * @return
     */
    public static Boolean verifyPath(String path){
        String pattern = "(^//.|^/|^[a-zA-Z])?:?/.+(/$)?";
        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(path);
        return m.matches();
    }
}
