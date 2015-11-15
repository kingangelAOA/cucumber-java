package junyan.cucumber;

import cucumber.api.cli.Main;
import junyan.cucumber.support.util.CucumberReportMonitor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by kingangeltot on 15/11/4.
 */
public class RunCucumber{
    private static final String[] BASE_PARAMS = {"-p","json:cucumber-reports/cucumber.json", "-g", "junyan/cucumber/step_definitions"};
    public static void main(String[] args){
//        String[] help = {"-p","pretty","-g", "junyan/cucumber/step_definitions", "--help"};
//        String[] test = {"/Users/kingangelTOT/Downloads/features", "-t", "@account"};
        byte status = create(addParams(args));
        CucumberReportMonitor.create(new String[]{"-f", "cucumber-reports", "-o", "cucumber-reports", "-n"});
        System.exit(status);
    }

    public static String[] addParams(String[] args){
        List<String> base = new ArrayList<>();
        base.addAll(Arrays.asList(BASE_PARAMS));
        base.addAll(Arrays.asList(args));
        return base.toArray(new String[base.size()]);
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
