package junyan.cucumber.support.args;

import com.beust.jcommander.Parameter;


/**
 * Created by kingangeltot on 15/11/17.
 */
public class InterfaceParams {
    @Parameter(names = "-e", required = true, description = "设置测试环境")
    private String env;

    @Parameter(names = "-ep", required = true, description = "设置测试环境")
    private String envPath;

    public String getEnv() {
        return env;
    }

    public String getEnvPath() {
        return envPath;
    }
}