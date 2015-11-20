package junyan.cucumber;

import org.python.core.*;
import org.python.google.common.collect.Interner;
import org.python.google.common.collect.ObjectArrays;
import org.python.util.PythonInterpreter;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by kingangelTOT on 15/11/19.
 */
public class Test {

    public static void main(String[] args){
        PythonInterpreter interp = new PythonInterpreter();
        interp.execfile("/Users/kingangelTOT/Application/git_work/python_lib/Lib/common/file.py");
        PyFunction pyfun = interp.get("test", PyFunction.class);
        Object[] objects = {"aa", 1};
        PyDictionary pyDictionary = (PyDictionary)pyfun.__call__(getPyObject(objects));
        puts(pyDictionary.get("aa").);
    }

    public static PyObject[] getPyObject(Object[] objects){
        PyObject[] pyObjects = new PyObject[objects.length];
        for (int i = 0; i < objects.length; i++){
            pyObjects[i] = getPyObject(objects[i]);
        }
        return pyObjects;
    }

    public static PyObject getPyObject(Object object){
        if (object instanceof String)
            return new PyString(object.toString());
        else if (object instanceof Integer)
            return new PyInteger((Integer)object);
        else if (object instanceof Boolean)
            return new PyBoolean((Boolean)object);
        else
            return null;
    }

    public static void puts(Object object){
        System.out.println(object);
    }
}
