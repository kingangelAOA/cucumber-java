Feature: test script
    Scenario Outline:
        Given 设置全局变量 {"aa":"cc","dd":[{"ee":true,"gg":1},{"ii":11.22}]}
#        And 执行脚本,路径 <path> 方法 <method> 参数 <args>
        And 脚本更新全局变量 路径 <path> 方法 <method> 参数 <args>
        Given 查看全局变量
        @script
        Examples:
        |path                                                                   |method|args    |
        |/Users/kingangeltot/Applications/git_work/python_lib/Lib/common/test1.py|test_global|'${dd}','ccc'|
