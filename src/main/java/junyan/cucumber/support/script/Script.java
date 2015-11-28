package junyan.cucumber.support.script;

import org.python.core.PyObject;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by kingangelTOT on 15/11/22.
 */
public class Script {

    public static String evalScript(String script){
        try {
            Map<String, String> scriptData = parseScript(script);
            if (scriptData.get("path").contains(".py")){
                Python python = new Python();
                String result = python.evalFunction(script).asString();
                script = script.replaceAll("\\<\\["+scriptData.get("path")+"\\]-\\["+scriptData.get("method")+"\\]-\\["+scriptData.get("args")+"\\]\\>"
                        , result);
            }else if (scriptData.get("path").contains(".rb")){
            } else
                throw new Exception("不支持除python和ruby的脚本......");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return script;
    }

    public static PyObject evalScript(String path, String method, String args){
        PyObject pyObject = null;
        try {
            if (path.contains(".py")){
                Python python = new Python();
                pyObject = python.evalFunction(path, method, args);
            }else if (path.contains(".rb")){
            } else
                throw new Exception("不支持除python和ruby的脚本......");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return pyObject;
    }

    public static Boolean hasScript(String script){
        Pattern pattern = Pattern.compile("\\<\\[(.*)\\]-\\[(.*)\\]-\\[(.*)\\]\\>");
        Matcher matcher = pattern.matcher(script);
        return matcher.find();
    }

    public static Map<String, String> parseScript(String script) throws Exception {
        Map<String,String> result = new HashMap<>();
        Pattern pattern = Pattern.compile("\\<\\[(.*)\\]-\\[(.*)\\]-\\[(.*)\\]\\>");
        Matcher matcher = pattern.matcher(script);
        if (!matcher.find())
            throw new Exception("解析脚本公式失败");
        if (matcher.group(1).isEmpty())
            throw new Exception("脚本路径不能为空");
        if (matcher.group(2).isEmpty())
            throw new Exception("脚本方法不能为空");
        result.put("path", matcher.group(1));
        result.put("method", matcher.group(2));
        result.put("args", matcher.group(3));
        return result;
    }
}
