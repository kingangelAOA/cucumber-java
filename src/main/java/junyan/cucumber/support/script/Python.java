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

    public static PythonInterpreter INTERPRETER;

    public static void initPython(List<String> paths){
        PythonInterpreter.initialize(System.getProperties(),
                System.getProperties(), new String[0]);
        PythonInterpreter interpreter = new PythonInterpreter();
        PySystemState sys = Py.getSystemState();
        for (String str:paths)
            sys.path.append(new PyString(str));
        INTERPRETER = interpreter;
    }

    public static void execfile( final String fileName ) {
        INTERPRETER.execfile(fileName);
    }

    public static PyObject evalFunction(String functionName, String args ) {
        if (args.equals("null"))
            INTERPRETER.eval(functionName + "("+"'"+Config.ENV+"'"+")");
        return INTERPRETER.eval(functionName + "(" + "'"+Config.ENV+"'," + args + ")");
    }

    public static PyObject evalFunction(String path, String method, String args){
        try {
            args = Common.regularBrace(args, Config.GLOBAL);
        } catch (InterfaceException e) {
            return null;
        }
        execfile(path);
        return evalFunction(method, args);
    }

    public static PyObject evalFunction(String script) throws Exception {
        Map<String, String> scripData = Script.parseScript(script);
        return evalFunction(scripData.get("path"), scripData.get("method"), scripData.get("args"));
    }

}
