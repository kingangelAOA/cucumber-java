package junyan.cucumber;

import cucumber.api.cli.Main;
import junyan.cucumber.support.util.CucumberReportMonitor;

/**
 * Created by kingangeltot on 15/11/4.
 */
public class RunCucumber{
    public static void puts(Object object){
        System.out.println(object);
    }
    public static void main(String[] args){
        String[] params = {"-p","json:cucumber-reports/cucumber.json", "-g", "junyan/cucumber/step_definitions",
                "/Users/kingangelTOT/Downloads/features", "-t", "@interface"};
        String[] help = {"-p","pretty","-g", "junyan/cucumber/step_definitions", "--help"};
        byte status = create(params);
        CucumberReportMonitor.create(new String[]{"-f", "cucumber-reports", "-o", "cucumber-reports", "-n"});
        System.exit(status);
    }

    public static byte create(String[] args){
        byte status = 1;
        try {
            status = Main.run(args, Thread.currentThread().getContextClassLoader());
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        return status;
    }
}
