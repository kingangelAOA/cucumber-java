package junyan.cucumber.support.script;

import junyan.cucumber.support.env.InterfaceEnv;
import junyan.cucumber.support.exceptions.InterfaceException;
import junyan.cucumber.support.util.Common;
import org.python.core.*;
import org.python.util.PythonInterpreter;
import org.testng.Assert;

import java.util.List;
import java.util.Map;

/**
 * Created by kingangelTOT on 15/11/21.
 */
public class Python {

    PythonInterpreter interpreter = null;
    private static List<String> paths;

    public Python() throws Exception {
        PythonInterpreter.initialize(System.getProperties(),
                System.getProperties(), new String[0]);
        this.interpreter = new PythonInterpreter();
        PySystemState sys = Py.getSystemState();
        if (paths.size() == 0)
            throw new Exception("没有设置python脚本目录或者第三方包路径");
        for (String str:paths)
            sys.path.append(new PyString(str));
    }

    public static void setPaths(List<String> paths) {
        Python.paths = paths;
    }

    public void execfile( final String fileName ) {

        this.interpreter.execfile(fileName);
    }

    public PyObject evalFunction(String functionName, String args ) {
        return this.interpreter.eval(functionName + "(" + args + ")");
    }

    public PyObject evalFunction(String path, String method, String args){
        try {
            args = Common.regularBrace(args, InterfaceEnv.global);
        } catch (InterfaceException e) {
            Assert.assertEquals(false, e.getMessage());
        }
        execfile(path);
        return evalFunction(method, args);
    }

    public PyObject evalFunction(String script) throws Exception {
        Map<String, String> scripData = Script.parseScript(script);
        return evalFunction(scripData.get("path"), scripData.get("method"), scripData.get("args"));
    }

    public void puts(Object object){
        System.out.println(object);
    }
}
