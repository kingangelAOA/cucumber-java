package junyan.cucumber;

import junyan.cucumber.support.util.Common;
import org.omg.CORBA.COMM_FAILURE;
import org.yaml.snakeyaml.Yaml;

import java.io.*;
import java.util.LinkedList;
import java.util.Map;

/**
 * Created by kingangelTOT on 15/11/19.
 */
public class Test {

    public static void main(String[] args) throws FileNotFoundException, UnsupportedEncodingException {
        String str = "{\"username\": \"root\", \"name\": \"\\u989c\\u519b\", \"permission\": \"root\", \"created_at\": null, \"updated_at\": null, \"id\": 1, \"password\": \"111111\", \"email\": \"adfsadf\"}";
        puts(Common.decodeUnicode(str));
    }






    public static void puts(Object object){
        System.out.println(object);
    }


}
