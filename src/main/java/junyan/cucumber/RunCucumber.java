package junyan.cucumber;

import com.beust.jcommander.JCommander;
import cucumber.api.cli.Main;
import junyan.cucumber.support.env.Config;
import junyan.cucumber.support.util.Common;
import junyan.cucumber.support.util.CucumberReportMonitor;
import junyan.cucumber.support.args.InterfaceParams;
import junyan.cucumber.support.util.JsonUtil;

import java.util.*;

/**
 * Created by kingangeltot on 15/11/4.
 */
public class RunCucumber{
    private static final String[] BASE_PARAMS = {"-p","json:cucumber-reports/cucumber.json", "-g", "junyan/cucumber/step_definitions"};
    public static void main(String[] args){
//        String[] help = {"-p","pretty","-g", "junyan/cucumber/step_definitions", "--help" ,"-e" ,"alpha"};
        String[] test = {"/feature", "-e", "alpha", "-ep", "/Users/kingangelTOT/Application/git_work/cucumber-java/feature/env.yml", "-t",
                "@script"};
        List<String[]> result = getParams(addParams(test));
        initSystem(result.get(1));
        byte status = create(getCucumberParams(result.get(0)));
        CucumberReportMonitor.create(new String[]{"-f", "cucumber-reports", "-o", "cucumber-reports", "-n"});
        System.exit(status);
    }

    private static String[] getCucumberParams(String[] args){
        String[] results = new String[args.length+1];
        for (int i = 0; i < results.length; i++){
            if (i <= args.length)
                results[i] = args[i];
            else
                results[i] = Config.RUN_PATH;
        }
        return args;
    }

    private static void initSystem(String[] args) {
        InterfaceParams params = new InterfaceParams();
        JCommander cmd = new JCommander(params);
        cmd.parse(args);
        Config.ENV = params.getEnv();
        Config.ROOT_PATH = params.getRooPath();
        Config.PROJECT =  Config.ROOT_PATH+"/"+params.getProjectName();
        Config.MODULE = params.getModule();
        Config.ENV_VALUE = JsonUtil.tojson(Common.toMapByYaml(Config.PROJECT+"/config/env.yml"));
        runPath();
    }

    private static void runPath(){
        if (Config.MODULE.equals("all"))
            Config.RUN_PATH = Config.PROJECT;
        else
            Config.RUN_PATH = Config.PROJECT+"/"+Config.MODULE;
    }

    private static List<String[]> getParams(String[] args){
        List<String> argsList = Common.toList(args);
        List<String> paramsList = new ArrayList<>();
        for (int i = 0; i < argsList.size(); i++){
            if (Arrays.asList(Config.PARAMS).contains(argsList.get(i))){
                paramsList.add(argsList.get(i));
                paramsList.add(argsList.get(i+1));
                argsList.remove(i);
                argsList.remove(i);
                i--;
            }
        }
        List<String[]> result = new ArrayList<>();
        result.add(argsList.toArray(new String[argsList.size()]));
        result.add(paramsList.toArray(new String[paramsList.size()]));
        return result;
    }

    private static String[] addParams(String[] args){
        List<String> base = new ArrayList<>();
        base.addAll(Arrays.asList(BASE_PARAMS));
        base.addAll(Arrays.asList(args));
        return base.toArray(new String[base.size()]);
    }

    private static byte create(String[] args){
        byte status = 1;
        try {
            status = Main.run(args, Thread.currentThread().getContextClassLoader());
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        return status;
    }
}
