Feature: test script
    Scenario Outline:
        Given 设置全局变量 {"aa":"cc","dd":[{"ee":true,"gg":1},{"ii":11.22}]}
        And 执行脚本,路径 <path> 方法 <method> 参数 <args>

        @script
        Examples:
        |path                                                                   |method|args    |
        |/Users/kingangelTOT/Application/git_work/python_lib/Lib/common/test1.py|test  |${dd[0].gg},'ccc'|
