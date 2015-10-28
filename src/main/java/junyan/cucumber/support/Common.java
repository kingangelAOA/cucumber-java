package junyan.cucumber.support;

import com.esotericsoftware.yamlbeans.YamlException;
import com.esotericsoftware.yamlbeans.YamlReader;
import org.apache.commons.io.FileUtils;
import org.openjdk.jmh.generators.core.MethodInfo;
import org.openjdk.jmh.generators.core.ParameterInfo;
import org.openjdk.jmh.generators.reflection.RFGeneratorSource;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.Augmenter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
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

    public static Map toMap(String yamlPath){
        YamlReader reader;
        Object object;
        Map map = null;
        try {
            reader = new YamlReader(new FileReader(yamlPath));
            object = reader.read();
            map = (Map)object;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (YamlException e) {
            e.printStackTrace();
        }
        return map;
    }

    public String getTime(){
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        return sdf.format(date);
    }

    public static boolean deleteDir(File dir) {
        if (dir.exists()) {
            if (dir.isDirectory()) {
                String[] children = dir.list();
                for (int i = 0; i < children.length; i++) {
                    boolean success = deleteDir(new File(dir, children[i]));
                    if (!success) {
                        return false;
                    }
                }
            }
        }
        // 目录此时为空，可以删除
        return dir.delete();
    }

    public static List<String> getFiles(String filePath, List<String> fileList) {
        File root = new File(filePath);
        File[] files = root.listFiles();
        for (File file:files){
            if (file.isDirectory())
                getFiles(file.getAbsolutePath(), fileList);
            else
                fileList.add(filePath+"/"+file.getName());

        }
        return fileList;
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

    /**
     * list 转换成 数组
     * @param list
     * @return
     */
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

    public static int convert(Class<?> target) {
        if (target == Object.class || target == String.class) {
            return 1;
        }
        if (target == Character.class || target == char.class) {
            return 2;
        }
        if (target == Byte.class || target == byte.class) {
            return 3;
        }
        if (target == Short.class || target == short.class) {
            return 4;
        }
        if (target == Integer.class || target == int.class) {
            return 5;
        }
        if (target == Long.class || target == long.class) {
            return 6;
        }
        if (target == Float.class || target == float.class) {
            return 7;
        }
        if (target == Double.class || target == double.class) {
            return 8;
        }
        if (target == Boolean.class || target == boolean.class) {
            return 9;
        }
        return 0;
//        throw new IllegalArgumentException("Don't know how to convert to " + target);
    }

    /**
     * 根据类路径获取生成该类的对象
     * @param className
     * @param objects
     * @return
     */
    public static Object instantiate(String className, Object[] objects) {
        Object object = null;
        try {
            Class<?> classType = Class.forName(className);
            Constructor<?> constructor = getRightConstructor(classType, objects);
//            puts(constructor);
            object = constructor.newInstance(objects);
        } catch (ClassNotFoundException e) {
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

    /**
     * 根据入参获取构造函数
     * @param classType
     * @param objects
     * @return
     */
    private static Constructor getRightConstructor(Class<?> classType, Object[] objects){
        Constructor reConstructor = null;
        LinkedList<Object> linkList = toLinkedList(objects);
        for (Constructor constructor:classType.getConstructors()){
            if (constructor.getParameters().length != objects.length)
                continue;
            List<Boolean> flagList = new LinkedList<>();
            for (Parameter parameter : constructor.getParameters()) {
                boolean flag = false;
                for (Object object : linkList) {
                    if (parameter.getType() == object.getClass()) {
                        flag = true;
                        linkList.remove(object);
                        break;
                    } else {
                        for (Class<?> clazz : object.getClass().getInterfaces()) {
                            Class<?> targetClazz = parameter.getType();
                            if (clazz == targetClazz) {
                                flag = true;
                                linkList.remove(object);
                                break;
                            }
                            if (convert(object.getClass()) == convert(parameter.getType())){
                                flag = true;
                                break;
                            }
                        }
                    }
                }
                flagList.add(flag);
            }

            if (!flagList.contains(false))
                reConstructor = constructor;
        }
        return reConstructor;
    }

    public void createDirectory(String path){
        File file =new File(path);
        if  (!file .exists() && !file .isDirectory())
            file .mkdir();
    }

    public void createFile(String path){
        File file=new File(path);
        if(!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 根据对象动态调用该对象的方法
    * @param Object
     * @param method
     * @param args
     * @return
     */
    public static Object execMethod(Object Object, String method, Object[] args){
        Class<?> clazz = Object.getClass();
        Object object = null;
        Method getMethod;
        try {

            getMethod = selectMethod(clazz, method, args);
//            puts(getMethod);
            object = getMethod.invoke(Object, args);
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return object;
    }

    /**
     * 动态获取method对象
     * @param clazz
     * @param name
     * @param args
     * @return
     */
    private static Method selectMethod(Class<?> clazz, String name, Object[] args){
        Method method = null;
        LinkedList<Object> linkList = toLinkedList(args);
        for (Method remethod:clazz.getMethods()){
            if (!remethod.getName().equals(name))
                continue;
            if (remethod.getParameters().length != args.length)
                continue;
            List<Boolean> flagList = new LinkedList<>();
            for (Parameter parameter : remethod.getParameters()) {
                boolean flag = false;
                for (Object object : linkList) {
                    if (parameter.getType() == object.getClass()) {
                        flag = true;
                        linkList.remove(object);
                        break;
                    } else{
                        for (Class<?> reClazz : object.getClass().getInterfaces()) {
                            Class<?> targetClazz = parameter.getType();
                            if (reClazz == targetClazz) {
                                flag = true;
                                linkList.remove(object);
                                break;
                            }
                            if (convert(object.getClass()) == convert(parameter.getType())){
                                flag = true;
                                break;
                            }
                        }
                    }
                }
                flagList.add(flag);
            }

            if (!flagList.contains(false))
                method = remethod;
        }
        return method;
    }

    private static Class<?>[] toClass(Object[] args){
        Object[] convertedArgs = new Object[args.length];
        Class<?>[] paramsClass = new Class[args.length];
        for (int i = 0; i < convertedArgs.length; i++) {
            paramsClass[i] = args[i].getClass();
        }
        return paramsClass;
    }

    public static List<Object> toList(Object[] objects){
        List<Object> list = new ArrayList<>();
        for (int i = 0; i < objects.length; i++){
            list.add(objects[i]);
        }
        return list;
    }

    public static LinkedList<Object> toLinkedList(Object[] objects){
        LinkedList<Object> linkList = new LinkedList<>();
        for (int i = 0; i < objects.length; i++){
            linkList.add(objects[i]);
        }
        return linkList;
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

    public static Map<String, Object> toMap(Map<String, Object> oldMap, Map<String, Object> targetMap) throws UiExceptions {
        Set<String> allKeys = oldMap.keySet();
        Set<String> targetKeys = targetMap.keySet();
        List<String> same = hasSame(allKeys, targetKeys);
        if (same.size() > 0)
            throw new UiExceptions(allKeys+"和"+targetKeys+" 有重复key: "+same);
        oldMap.putAll(targetMap);
        return oldMap;
    }

    public static List<String> hasSame(Set<String> oneSet, Set<String> twoSet){
        List<String> same = twoSet.stream().filter(key -> oneSet.contains(key)).collect(Collectors.toList());
        return same;
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
