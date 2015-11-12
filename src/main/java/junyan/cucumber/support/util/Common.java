package junyan.cucumber.support.util;

import com.esotericsoftware.yamlbeans.YamlException;
import com.esotericsoftware.yamlbeans.YamlReader;
import com.jayway.jsonpath.JsonPath;
import junyan.cucumber.support.exceptions.InterfaceException;
import junyan.cucumber.support.exceptions.UiExceptions;
import org.apache.commons.io.FileUtils;
import org.openjdk.jmh.generators.core.MethodInfo;
import org.openjdk.jmh.generators.core.ParameterInfo;
import org.openjdk.jmh.generators.reflection.RFGeneratorSource;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.Augmenter;

import java.io.*;
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
            reader = new YamlReader(new FileReader(yamlPath));
            object = reader.read();
            map = (Map)object;
        }catch (YamlException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
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

    public static Boolean hasBrance(String target){
        Pattern pattern = Pattern.compile("\\$\\{(.*?)\\}");
        Matcher matcher = pattern.matcher(target);
        return matcher.find();
    }

    public static String regularBrace(String target, String json) throws InterfaceException {
        Pattern pattern = Pattern.compile("\\$\\{(.*?)\\}");
        Matcher matcher = pattern.matcher(target);
        String newTarget;
        if (matcher.find()){
            String matchers = matcher.group(1);
            if (matchers == null)
                throw new InterfaceException("匹配格式有误,\"${}\"或者${}");
            String newWatchers = matchers.replace("[", "\\[");
            newWatchers = newWatchers.replace("]", "\\]");
            newTarget = target.replaceAll("\\$\\{"+newWatchers+"\\}", getResult(JsonPath.read(json, matchers)).toString());
            newTarget = regularBrace(newTarget, json);
            target = newTarget;
        }
        return target;
    }

    public static Object getResult(Object object){
        if (object instanceof String)
            return "\""+object+"\"";
        if (object instanceof Number)
            return String.valueOf(object);
        else
            return object;
    }

    public static String replace(String source, String oldRelax, String newRelax){
        source = source.replace(oldRelax, newRelax);
        return source;
    }

    public static String readFile(String path) {
        File file = new File(path);
        BufferedReader reader = null;
        String laststr = "";
        try {
            reader = new BufferedReader(new FileReader(file));
            String tempString = null;
            while ((tempString = reader.readLine()) != null) {
                laststr = laststr + tempString;
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                }
            }
        }
        return laststr;
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
