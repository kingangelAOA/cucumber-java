package junyan.cucumber;

import cucumber.api.cli.Main;

/**
 * Created by kingangeltot on 15/11/4.
 */
public class RunCucumber{
    public static void puts(Object object){
        System.out.println(object);
    }
    public static void main(String[] args){
//        try {
//            Class.forName("junyan.cucumber.RunCucumber");
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        }
//        ClassLoader loader = Thread.currentThread().getContextClassLoader();
//        System.out.println(loader.getResource("junyan/cucumber/step_definitions"));
//        puts(System.getProperty("user.dir")+"/src/features");
        String[] params = {"-p","pretty", "-g", "junyan/cucumber/step_definitions", "/Users/kingangeltot/Applications/features", "-t", "@account"};
        run(params);
    }

    public static void run(String[] args){
        try {
            Main.main(args);
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }
}
