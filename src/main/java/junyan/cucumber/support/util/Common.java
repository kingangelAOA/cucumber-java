package junyan.cucumber.support.util;

import com.google.gson.JsonElement;
import com.jayway.jsonpath.JsonPath;
import junyan.cucumber.support.exceptions.InterfaceException;
import junyan.cucumber.support.exceptions.UiExceptions;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.Augmenter;
import org.yaml.snakeyaml.Yaml;

import java.io.*;
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
     */
    public static Map toMapByYaml(String yamlPath){
        InputStream input = null;
        try {
            input = new FileInputStream(new File(yamlPath));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Yaml yaml = new Yaml();
        return  (Map) yaml.load(input);
    }

    public String getTime(){
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        return sdf.format(date);
    }

    public static Boolean isForm(String target){
        Pattern pattern = Pattern.compile("(.*)=(.*)&*?");
        Matcher matcher = pattern.matcher(target);
        return matcher.find();
    }

    public static boolean deleteDir(File dir) {
        if (dir.exists()) {
            if (dir.isDirectory()) {
                String[] children = dir.list();
                for (String aChildren : children) {
                    boolean success = deleteDir(new File(dir, aChildren));
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
        for (File file: files != null ? files : new File[0]){
            if (file.isDirectory())
                getFiles(file.getAbsolutePath(), fileList);
            else
                fileList.add(filePath+"/"+file.getName());

        }
        return fileList;
    }

    public static void puts(Object object){
        System.out.println(object);
    }

    /**
     * 验证此字符串是否是文件路径
     */
    public static Boolean verifyPath(String path){
        String pattern = "(^//.|^/|^[a-zA-Z])?:?/.+(/$)?";
        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(path);
        return m.matches();
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
                throw new InterfaceException("匹配格式有误,${}");
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
            String tempString;
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
                    return null;
                }
            }
        }
        return laststr;
    }

    /**
     * list 转换成 数组
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

    public static List<Object> toList(Object[] objects){
        List<Object> list = new ArrayList<>();
        Collections.addAll(list, objects);
        return list;
    }

    public static LinkedList<Object> toLinkedList(Object[] objects){
        LinkedList<Object> linkList = new LinkedList<>();
        Collections.addAll(linkList, objects);
        return linkList;
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
        return twoSet.stream().filter(oneSet::contains).collect(Collectors.toList());
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

    public void screenshot(WebDriver driver, String path) throws IOException {
        WebDriver driver1 = new Augmenter().augment(driver);
        File file  = ((TakesScreenshot)driver1).getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(file, new File(path));
    }


}
