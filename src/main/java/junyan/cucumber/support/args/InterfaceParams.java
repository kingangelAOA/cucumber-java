package junyan.cucumber.support.args;

import com.beust.jcommander.Parameter;


/**
 * Created by kingangeltot on 15/11/17.
 */
public class InterfaceParams {
    @Parameter(names = "-e", required = true, description = "设置测试环境")
    private String env;

    @Parameter(names = "-pn", required = true, description = "项目名称")
    private String projectName;

    @Parameter(names = "-rp", required = true, description = "根目录")
    private String rooPath;

    @Parameter(names = "-m", required = false, description = "项目模块")
    private String module;

    public String getEnv() {
        return env;
    }

    public String getProjectName() {
        return projectName;
    }

    public String getRooPath() {
        return rooPath;
    }

    public String getModule() {
        return module;
    }
}