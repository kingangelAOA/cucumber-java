## 功能介绍
### 使用说明
#### 1.使用环境

(1).jdk要求1.8以上
(2).本项目使用的是cucumber java版本, 使用前最好了解cucumber的机制.
   
#### 2.源码打包
    
    mvn assembly:assembly
    
#### 3.java命令
    
    java -jar xxxx.jar /Users/kingangeltot/Applications/git_work/cucumber-java/features -t @init,@account -e debug
    -e 是自定义参数, 配置接口测试需要的环境,现在只是为了设置数据库的环境, 数据库暂时支持mysql
    其它的cucumber参数介绍请使用java -jar xxxx.jar --help
    注意: 其中-p -g 参数已经设置在源码中请不要重复设置
    
##Cucumber interface公共接口定义(支持接口关联测试)

###cucumber接口测试步骤

    具体请查看源码中的example

##android, IOS, web 自动化测试
###环境配置
1.appium

    配置appium环境, 具体请查看 http://appium.io

2.grid2

    启动grid2,具体信息请查看 https://code.google.com/p/selenium/wiki/Grid2
    
3.因为启动的时候会先连接数据库,所以在数据库配置文件中连接一个可用的mysql数据否则报错(待优化)

    mysql数据库会根据,run.yaml中的配置选择对应的数据库
    
###接口步骤

1.设置远程url http://127.0.0.1:4723

    运行用例的目标ip, appium server运行的目标机器IP, grid2运行的hub的IP

2.设置platform XXX

    设置运行的平台: android, ios 或者 web, ios 未调试
     
3.设置web浏览器 XXX

    假如平台设置的是web, 这个接口可以设置 firefox, chrome等

4.初始化driver
  
    设置完必须的配置后, 生成driver
    
5.跳转到网页address http://www.baidu.com

    假如初始化driver后可以用这个接口跳转到目标页面
    
6.查询单个元素 (.*), 查询方法 (.*), 查询条件 (.*)

    第一个参数是元素名称(不能重复),第二个参数方法名称(对应源码中的方法),第三个为查询条件
    以下是查询单个元素的方法:
    "getNamedTextField",
    "findElementByIosUIAutomation",
    "findElementByAndroidUIAutomator",
    "scrollTo",
    "findElementByClassName",
    "findElementByAccessibilityId",
    "scrollToExact",
    "findElementByPartialLinkText",
    "findElementByLinkText",
    "findElementByCssSelector",
    "findElementByTagName",
    "findElementById",
    "findElementByXPath",
    "findElementByName"
    具体意思可以到appium源码或者selenium源码去查询
    
7.设置超时时间 xxx
    
    设置查询元素的超时时间,默认是10秒
    
8.查看缓存的元素

    查看缓存过的元素
    
9.点击元素 xxx

    点击元素
    
10.输入 xxx 到元素 xxxx中

    输入数据到已经被查询到的元素中区
    
11.元素 xxx 的文本信息是否等于 xxx

    获取已经查询到的元素的文本是否与设置的文本相同
    