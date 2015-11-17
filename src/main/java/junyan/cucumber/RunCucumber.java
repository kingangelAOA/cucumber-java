package junyan.cucumber;

import com.beust.jcommander.JCommander;
import cucumber.api.cli.Main;
import junyan.cucumber.support.util.Config;
import junyan.cucumber.support.util.CucumberReportMonitor;
import junyan.cucumber.support.args.InterfaceParams;

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
        String[] test = {"/Users/kingangeltot/Applications/git_work/cucumber-java/features", "-t", "@init,@account", "-e", "debug"};
        List<String[]> result = getParams(addParams(test));
        initSystem(result.get(1));
        byte status = create(result.get(0));
        CucumberReportMonitor.create(new String[]{"-f", "cucumber-reports", "-o", "cucumber-reports", "-n"});
        System.exit(status);
    }

    public static void initSystem(String[] args){
        InterfaceParams params = new InterfaceParams();
        JCommander cmd = new JCommander(params);
        cmd.parse(args);
        Config.env = params.getEnv();
    }

    public static List<String[]> getParams(String[] args){
        List<String> argsList = new ArrayList<>();
        argsList.addAll(Arrays.asList(args));
        List<String> paramsList = new ArrayList<>();
        for (int i = 0; i < argsList.size(); i++){
            if (Arrays.asList(Config.PARAMS).contains(args[i])){
                paramsList.add(argsList.get(i));
                paramsList.add(argsList.get(i+1));
                argsList.remove(i);
                argsList.remove(i);
            }
        }
        List<String[]> result = new ArrayList<>();
        result.add(argsList.toArray(new String[argsList.size()]));
        result.add(paramsList.toArray(new String[paramsList.size()]));
        return result;
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
