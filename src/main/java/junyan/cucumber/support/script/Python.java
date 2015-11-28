package junyan.cucumber.support.script;

import junyan.cucumber.support.exceptions.InterfaceException;
import junyan.cucumber.support.util.Common;
import junyan.cucumber.support.env.Config;
import org.python.core.*;
import org.python.util.PythonInterpreter;
import java.util.List;
import java.util.Map;

/**
 * Created by kingangelTOT on 15/11/21.
 */
public class Python {

    PythonInterpreter interpreter = null;
    private static List<String> paths = Config.getPyPackage();

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

    public void execfile( final String fileName ) {

        this.interpreter.execfile(fileName);
    }

    public PyObject evalFunction(String functionName, String args ) {
        if (args.equals("null"))
            return this.interpreter.eval(functionName + "()");
        return this.interpreter.eval(functionName + "(" + args + ")");
    }

    public PyObject evalFunction(String path, String method, String args){
        try {
            args = Common.regularBrace(args, Config.GLOBAL);
        } catch (InterfaceException e) {
            return null;
        }
        execfile(path);
        return evalFunction(method, args);
    }

    public PyObject evalFunction(String script) throws Exception {
        Map<String, String> scripData = Script.parseScript(script);
        return evalFunction(scripData.get("path"), scripData.get("method"), scripData.get("args"));
    }
}
