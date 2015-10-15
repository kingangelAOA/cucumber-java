
## Use Maven

Open a command window and run:

mvn test

This runs Cucumber features using Cucumber's JUnit runner. The `@RunWith(Cucumber.class)` annotation on the `RunCukesTest`
class tells JUnit to kick off Cucumber.

Open a command window and run:

mvn clean test -Dcucumber.options="--plugin junit:taget_junit/cucumber.xml"

This runs Cucumber features using Cucumber's JUnit runner of course. Specifies extra output
results format that CI servers generally interpret

Open a command window and run:

mvn clean test -Dcucumber.options="classpath:features/BrowserCommands.feature:11"

This should result in no scenarios running because there are no scenarios at line:10
Moreover, if we want to override running entire features folder, we can do this



## Few options you can try on this project

The Cucumber runtime parses command line options to know what features to run, where the glue code lives, what plugins to use etc.
When you use the JUnit runner, these options are generated from the `@CucumberOptions` annotation on your test.

Sometimes it can be useful to override these options without changing or recompiling the JUnit class. This can be done with the
`cucumber.options` system property. The general form is:

Using Maven:

mvn -Dcucumber.options="..." test


### Run a subset of Features or Scenarios

Specify a particular scenario by *line* (and use the pretty plugin, which prints the scenario back)

-Dcucumber.options="classpath:features/BrowserCommands.feature:5 --plugin pretty"

You can also specify files to run by filesystem path:

-Dcucumber.options="src/test/resources/features/seleniumframework.feature:4 --plugin pretty"

You can also specify what to run by *tag*:

-Dcucumber.options="--tags @foo --plugin pretty"

##Cucumber interface公共接口定义(支持接口关联测试), UI测试未完成
###目录结构 
├── \ cucumber-html-reports //测试报告
│   ├── cucumber.json
│   ├── formatter.js
│   ├── index.html
│   ├── jquery-1.8.2.min.js
│   ├── report.js
│   └── style.css
├── AIC.iml
├── README.md
├── cucumber-auto.iml
├── pom.xml
└── src
├── main
│   ├── java
│   │   └── junyan
│   │       └── cucumber
│   │           ├── step_definitions
│   │           │   ├── AppiumSteps.java //appium接口定义(未完成)
│   │           │   └── InterfaceSteps.java //接口测试接口定义
│   │           └── support
│   │               ├── Appium.java //未完成
│   │               ├── AppiumEnv.java //未完成
│   │               ├── Common.java //通用方法类
│   │               ├── Http.java //okhttp底层封装
│   │               ├── InterfaceEnv.java //接口测试环境类
│   │               ├── InterfaceException.java //接口测试异常类
│   │               ├── Json.java //json处理封装,底层是Gson
│   │               ├── Mysql.java //mysql连接封装
│   │               ├── Reflect.java //未完成
│   │               ├── RunDriver.java //未完成
│   │               ├── Test.java
│   │               ├── UiCommon.java //未完成
│   │               ├── UiDriver.java //未完成
│   │               ├── UiDriverInterface.java //未完成
│   │               ├── UiExceptions.java //未完成
│   │               ├── models //未完成
│   │               │   ├── Assert.java
│   │               │   ├── Case.java
│   │               │   ├── Element.java
│   │               │   ├── Left.java
│   │               │   ├── Right.java
│   │               │   ├── Step.java
│   │               │   ├── TestSuite.java
│   │               │   ├── Trans.java
│   │               │   └── Worker.java
│   │               └── uimodule //未完成
│   │                   ├── MyAndroidDriver.java
│   │                   └── MyIosDriver.java
│   └── resources
│       └── config
│           ├── connect.yaml //数据库连接配置文件
│           └── run.yaml //环境设置
└── test
└── java
├── cucumber
│   └── RunTest.java //执行入口类
└── resources
├── features
│   └── test.feature //测试feature文件
└── test_data
├── testData_android.json //接口测试json数据,文件路径可变
└── test_data.json //同上

###cucumber接口测试使用方法
1.Given 设置全局变量 {"aaa":"bbbbb", "d":1}
    全局变量为接口与接口的数据中转站, 是关联接口测试的关键;
2.Given 设置接口名称 xxxx
    设置接口名称, 名称随意, 关联接口测试的时候, 名称不能重复
3.And 设置请求url http://localhost
    设置请求path
    支持参数传递
        example:
            全局变量为 {"aaa":"bbbbb", "d":1}
            url:http://localhost:3000/test${d}
            result: http://localhost:3000/test1
4.And 设置method POST
    支持 get, head, post, delete, put, patch
5.And 设置请求数据格式 json
    支持 json form url_encode_form
    
6.And 设置请求数据:
    请求数据格式为json或者.json文件路径
    example:
        And 设置请求数据:
            """
            /src/test/java/resources/test_data/test_data.json
            """
        And 设置请求数据:
              """
              {
                  "user_id": 111,
                  "bbb": "${id}",
                  "ccc": [
                      {
                          "acd": "aaaa"
                      }
                  ],
                  "dd": {
                      "cc": [
                          "a",
                          "b"
                      ]
                  }
              }
              """
        当请求数据格式是form时候,请求的数据body也是json格式的字符串(为了方便测试数据的管理),内部逻辑会自动处理成form
        
7.设置cookies aa=bb;cc=dd
    支持参数传递
        example:
            全局变量为 {"aaa":"bbbbb", "d":1}
            cookies: aa=bb;cc=dd;ee=${aaa}
            result: aa=bb;cc=dd;ee=bbbbb
            
8.设置headers {"Content-Length": 143}
9.And 执行请求
    执行http请求
        在执行请求前,get等不需要requestBody的必须先设置:interfaceName, url, method, 其它的请求必须设置:interfaceName, url, method, contentType, requestBody
10.Given 查看所有cache
    方便调试, cache中包含此次用例的responseHeader, responseBody, requestHeaders, requestBody, code
11.从接口 test1 的response的body中获取值,值索引为 [c, 0, ddd],字段名称为: abc,设置到全局变量中
    假如responseBody为{"a":"v","c":[{"ddd":0},{"eee":1.1}], 234}, 则全局变量中新更新数据{"abc":0}
    
12.从接口 (.*) 的request的body中获取值,值索引为: (.*),字段名称为: (.*),设置到全局变量中
    同11
13.从接口 (.*) 中获取response的cookies值, 字段 (.*), 设置到全局变量中
    同11, 把cookies中的某个值设置到全局变量中
14.数据库中获取数据设置到全局变量中, sql select * from users where id=1111,获取行数 0, 获取的参数 [username, age]
    sql查出的是数组数据根据行数取出,sql结果第几行数据(第一行是0以此类推), 返回的结果是{"username":"lalala","age":18},这个数据会更新到全局变量中

15.查看全局变量
    方便调试

16.从全局变量中取出字段 aaa 的值,是否等于 111
    全局变量{"aaa":111},result:true
    全局变量{"aaa":"111"},result:false
    
17.比较两个全局变量中的字段 aaa 是否等于字段 ccc
    全局变量{"aaa":111,"ccc":111},result:true
    全局变量{"aaa":111,"ccc":"111"},result:false
18.最近一次请求响应状态是否是 200
    断言请求返回状态是否是200 
    
19.接口 test response的body中的 [] 的值是否包含这些字段 [a,b,c]
    example:
        responseBody:{"a":"adfsad","b":"ccccc","af":"ddddd"}
        result:false
        responseBody:{"a":"adfsad","b":"ccccc","c":"ddddd"}
        result:true
    接口 test response的body中的 [c] 的值是否包含这些字段 [aa,bb,cc]
        responseBody:{"a":"adfsad","b":"ccccc","c":{"aa":"afsdf","bb":"asdfasfd","cc":"asdfasdf"}}
        result:true
20.可以自己二次开发,根据不同的项目开发不同的接口,因为不同的项目断言差异性很大,只能写比较通用的接口和断言.......