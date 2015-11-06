package junyan.cucumber.support;

import com.esotericsoftware.yamlbeans.YamlException;
import com.esotericsoftware.yamlbeans.YamlReader;
import me.ele.api.it.model.PreCondition;
import me.ele.api.it.model.TestCase;
import me.ele.api.it.model.TestSuite;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by xuxiaozhi on 11/4/15.
 */
//TODO 解析case yaml文件拿到所有的case
public class CaseUtil {
    public static List<String> fileList = new ArrayList<>();

    public static List<TestSuite> getTestSuites(String env, String casePath){
        return getTestSuites(casePath+"/"+env);
    }

    public static TestSuite getTestSuite(String yamlPath){
        YamlReader reader;
        TestSuite testSuite = null;
        try {
            reader = new YamlReader(new FileReader(yamlPath));
            reader.getConfig().setPropertyElementType(TestSuite.class, "preConditions", PreCondition.class);
            reader.getConfig().setPropertyElementType(TestSuite.class, "testCases", TestCase.class);
            testSuite = reader.read(TestSuite.class);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (YamlException e) {
            e.printStackTrace();
        }
        return testSuite;
    }

    private static List<String> getFiles(String path) {
        File root = new File(path);
        File[] files = root.listFiles();
        for (File file:files){
            if (file.isDirectory())
                getFiles(file.getAbsolutePath());
            else
                fileList.add(path+"/"+file.getName());
        }
        return fileList;
    }

    public static List<TestSuite> getTestSuites(String path){
        List<TestSuite> testSuites = new ArrayList<>();
        for (String yamlPath:getFiles(path)){
            testSuites.add(getTestSuite(yamlPath));
        }
        return testSuites;
    }

    public static TestSuite getTestSuiteByName(String name, List<TestSuite> testSuites){
        for (TestSuite testSuite:testSuites){
            if(testSuite.getName().equals(name))
                return testSuite;
        }
        return null;
    }
}
