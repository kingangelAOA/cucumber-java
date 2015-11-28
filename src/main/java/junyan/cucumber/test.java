package junyan.cucumber;

import junyan.cucumber.support.util.Common;
import org.omg.CORBA.COMM_FAILURE;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.Map;

/**
 * Created by kingangelTOT on 15/11/19.
 */
public class Test {

    public static void main(String[] args) throws FileNotFoundException {
        puts(Common.isForm("aa=bb&cc=dd"));
    }


    public static void puts(Object object){
        System.out.println(object);
    }


}
