package junyan.cucumber;

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
        LinkedList list = new LinkedList();
        list.add("A");
        list.add("B");
        list.add("C");
        list.add("D");
        list.removeFirst();
        list.removeFirst();
        System.out.println(list);
    }


    public static void puts(Object object){
        System.out.println(object);
    }


}
