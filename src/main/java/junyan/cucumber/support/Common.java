package junyan.cucumber.support;

import com.esotericsoftware.yamlbeans.YamlException;
import com.esotericsoftware.yamlbeans.YamlReader;
import org.apache.bcel.generic.FLOAD;
import org.apache.commons.io.FileUtils;
import org.openjdk.jmh.generators.core.MethodInfo;
import org.openjdk.jmh.generators.core.ParameterInfo;
import org.openjdk.jmh.generators.reflection.RFGeneratorSource;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.Augmenter;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

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
    public static Map toMapByYaml(String yamlPath){
        YamlReader reader;
        Object object;
        Map map = null;
        try {
            reader = new YamlReader(new FileReader(System.getProperty("user.dir") + yamlPath));
            object = reader.read();
            map = (Map)object;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (YamlException e) {
            e.printStackTrace();
        }
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

    public static List<MethodInfo> getMethodInfo(Collection<MethodInfo> collection){
        List<MethodInfo> list = new ArrayList<>();
        Iterator<MethodInfo> iterator = collection.iterator();
        while (iterator.hasNext()){
            MethodInfo methodInfo = iterator.next();
            list.add(methodInfo);
        }
        return list;
    }

    public static List<ParameterInfo> getParameterInfo(Collection<ParameterInfo> collection){
        List<ParameterInfo> list = new ArrayList<>();
        Iterator<ParameterInfo> iterator = collection.iterator();
        while (iterator.hasNext()){
            ParameterInfo parameterInfo = iterator.next();
            list.add(parameterInfo);
        }
        return list;
    }

    public static List<String> getParameterName(Collection<ParameterInfo> collection){
        List<String> list = new ArrayList<>();
        Iterator<org.openjdk.jmh.generators.core.ParameterInfo> iterator = collection.iterator();
        while (iterator.hasNext()){
            ParameterInfo parameterInfo = iterator.next();
            list.add(parameterInfo.getType().getName());
        }
        return list;
    }

    public static Object[] toCollection(List<Object> list){
        Object[] objects = new Object[list.size()];
        for (int i = 0; i < list.size(); i++){
            objects[i] = list.get(i);
        }
        return objects;
    }

    public static List<Object> toList(Object object){
        List<Object> list = new ArrayList<>();
        list.add(object);
        return list;
    }

    public static Object convert(Class<?> target, String s) {
        if (target == Object.class || target == String.class || s == null) {
            return s;
        }
        if (target == Character.class || target == char.class) {
            return s.charAt(0);
        }
        if (target == Byte.class || target == byte.class) {
            return Byte.parseByte(s);
        }
        if (target == Short.class || target == short.class) {
            return Short.parseShort(s);
        }
        if (target == Integer.class || target == int.class) {
            return Integer.parseInt(s);
        }
        if (target == Long.class || target == long.class) {
            return Long.parseLong(s);
        }
        if (target == Float.class || target == float.class) {
            return Float.parseFloat(s);
        }
        if (target == Double.class || target == double.class) {
            return Double.parseDouble(s);
        }
        if (target == Boolean.class || target == boolean.class) {
            return Boolean.parseBoolean(s);
        }
        throw new IllegalArgumentException("Don't know how to convert to " + target);
    }

    public static Object instantiate(List<String> args, String className) throws Exception {
        // Load the class.
        Class<?> clazz = Class.forName(className);

        // Search for an "appropriate" constructor.
        for (Constructor<?> ctor : clazz.getConstructors()) {
            Class<?>[] paramTypes = ctor.getParameterTypes();

            // If the arity matches, let's use it.
            if (args.size() == paramTypes.length) {

                // Convert the String arguments into the parameters' types.
                Object[] convertedArgs = new Object[args.size()];
                for (int i = 0; i < convertedArgs.length; i++) {
                    convertedArgs[i] = convert(paramTypes[i], args.get(i));
                }

                // Instantiate the object with the converted arguments.
                return ctor.newInstance(convertedArgs);
            }
        }
        throw new IllegalArgumentException("Don't know how to instantiate " + className);
    }

    public static Object instantiate(String className, Class<?>[] classes, Object[] objects) {
        Object object = null;
        try {
            Class<?> classType = classType = Class.forName(className);
            Constructor<?> constructor = classType.getConstructor(classes);
            object = constructor.newInstance(objects);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return object;
    }

    public static Object execMethod(Object Object, String method, Object[] args){
        Object[] convertedArgs = new Object[args.length];
        Class<?>[] paramsClass = new Class[args.length];
        for (int i = 0; i < convertedArgs.length; i++) {
            paramsClass[i] = args[i].getClass();
        }
        Class clazz = Object.getClass();
        Object object = null;
        try {
            Method getMethod = clazz.getMethod(method, paramsClass);
            object = getMethod.invoke(Object, args);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return object;
    }

    public static List<String> toList(String[] strings){
        List<String> list = new ArrayList<>();
        for (int i = 0; i < strings.length; i++){
            list.add(strings[i]);
        }
        return list;
    }

    public static void run(String classPath) throws ClassNotFoundException {
        RFGeneratorSource frc = new RFGeneratorSource();
        Collection<MethodInfo> constructorCollection = frc.resolveClass(Class.forName("classPath")).getConstructors();

    }

    public static Map<String, Object> deleteNull(Map<String, Object> map){
        List<String> list = map
                .entrySet()
                .stream()
                .filter(entry -> entry.getValue().equals(""))
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
        list.forEach(map::remove);
        return map;
    }

    public static Map<String, Object> toMap(Map<String, Object> oldMap, Map<String, Object> targetMap){
        oldMap.putAll(targetMap);
        return oldMap;
    }
    public static <AnyType extends Comparable<? super AnyType>> int binarySearch(AnyType [] a, AnyType x){
        int low = 0, high = a.length - 1;
        while (low <= high){
            int mid = (low + high) / 2;
            if (a[mid].compareTo(x) < 0)
                low = mid + 1;
            else if (a[mid].compareTo(x) > 0)
                high = mid - 1;
            else
                return mid;
        }
        return -1;
    }

    public static URL getUrl(String url){
        URL newUrl = null;
        try {
            newUrl = new URL(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return newUrl;
    }

    public static Class<?> getClassType(String string) throws ClassNotFoundException {
        return Class.forName(string);
    }

    public Map getKeyWordMap(String yamlPath) throws YamlException, FileNotFoundException {
        YamlReader reader;
        Object object;
        Map map = null;

        reader = new YamlReader(new FileReader(System.getProperty("user.dir") + yamlPath));
        object = reader.read();
        map = (Map)object;

        return map;
    }

    public void screenshot(WebDriver driver, String path) throws IOException {
        WebDriver driver1 = new Augmenter().augment(driver);
        File file  = ((TakesScreenshot)driver1).getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(file, new File(path));
    }
}
