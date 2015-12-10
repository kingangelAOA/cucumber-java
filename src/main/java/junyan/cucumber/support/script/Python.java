package junyan.cucumber.support.script;

import junyan.cucumber.support.exceptions.InterfaceException;
import junyan.cucumber.support.util.Common;
import junyan.cucumber.support.env.Config;
import org.python.core.*;
import org.python.util.PythonInterpreter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by kingangelTOT on 15/11/21.
 */
public class Python {

    public static PythonInterpreter INTERPRETER = initPython();

    public static PythonInterpreter initPython(){
        getPythonEnv();
        initPythonEnv();
//        PythonInterpreter.initialize(System.getProperties(),
//                System.getProperties(), new String[0]);
        PythonInterpreter interpreter = new PythonInterpreter();
        PySystemState sys = Py.getSystemState();
        for (String str:initPackage())
            sys.path.append(new PyString(str));
        return interpreter;
    }

    private static List<String> initPackage(){
        List<String> folders = new ArrayList<>();
        folders.add(Config.PROJECT+"/python");
        folders = Common.getFolders(Config.PROJECT+"/python", folders);
        folders.addAll(getPackage());
        return folders;
    }

    public static void initPythonEnv(){
        try {
            String[] cmd = {"/bin/bash","-c","echo king258angel@| sudo pip install -r "+Config.ROOT_PATH+"/env/requirement.txt"};
            Process pr = Runtime.getRuntime().exec(cmd);
            BufferedReader in = new BufferedReader(new InputStreamReader(pr.getInputStream()));
            String line;

            while ((line = in.readLine()) != null) {
                System.out.println(line);
            }
            in.close();
            pr.waitFor();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void getPythonEnv(){
        try {
            String[] cmd = {"/bin/bash","-c","echo king258angel@| sudo pip freeze > "+Config.ROOT_PATH+"/env/requirement.txt"};
            Process pr = Runtime.getRuntime().exec(cmd);
            BufferedReader in = new BufferedReader(new InputStreamReader(pr.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                System.out.println(line);
            }
            in.close();
            pr.waitFor();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static List<String> getPackage(){
        List<String> lists = new ArrayList<>();
        try {
            Process pr = Runtime.getRuntime().exec("python "+Config.ROOT_PATH+"/env/init_py.py");
            BufferedReader in = new BufferedReader(new InputStreamReader(pr.getInputStream()));
            String line;

            while ((line = in.readLine()) != null) {
                lists.add(line);
            }
            in.close();
            pr.waitFor();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return lists;
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
