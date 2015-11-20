Feature: example
  Scenario: interface

    #设置接口名称,为了存储数据到全局变量中,不可重复
    Given 设置接口名称 xxx

    #设置请求url, 可以从全局变量中根据jsonpath来取值,设置到url中
    #eg: 全局变量为{"aa":{"cc":[{"dd":"ee"},{"ff":"gg"}]}}, http://localhost:3000/test/${aa.cc[0].dd}
    #=>实际url:http://localhost:3000/test/ee
    And 设置请求url http://localhost:3000/test/${jsonPath}

    And 设置method POST

    #设置数据库连接配置, 以环境区分, 根据java命令参数中的 -e 来选择
    Given 设置DB:
    """
    {
  		"debug":{
  			"host": "127.0.0.1",
			"username": "root",
			"password": "xxxxxxxxx@",
			"database": "ui_auto_web",
			"port": "3306"
  		},
  		"test":{
  			"host": "192.168.0.1",
			"username": "root",
			"password": "xxxxxxxxxxxx@",
			"database": "ui_auto_web",
			"port": "3306"
  		}
  	}
    """

    #设置请求数据, 可以是json文件路径,可以是json文本, 可以是form格式数据
    And 设置请求数据:
    """
    /.json/文件/绝对路径
    or
    {
      "aa":"bb"
    }
    or
    aa=bb&bb=cc
    """

    #设置headers,格式为json, 支持jsonpath从全局变量中取值
    And 设置headers:
    """
    {
      "content-Type":"application/json",
      "Cookie":"SID=${login_for_user_info.response.headers.Cookie.SID}"
    }
    """

    #设置全局变量, 格式要求json
    Given 设置全局变量 {"aa":"bb"}

    #方便调试用, 一旦用到, 当前最新的全局变量会打印在log中
    Given 查看全局变量

    #开始请求, 前提已经设置好: 接口名称, url, method, 请求数据(除了get, head), headers(假如需要)
    When 执行请求

    #把数据库中的数据存储到全局变量中, sql支持jsonpath传值
    #假如sql查出的数据长度是2, 获取行数是0, 则获取的数据是第一行数据中的参数, 然后传入的全局变量中
    Given 数据库中获取数据设置到全局变量中, sql select * from users where id = ${jsonpath},获取行数 0, 获取的参数 aa,bb,cc

    #断言,全局变量中利用jsonpath取出的值是否是xxxx
    Then 从全局变量中取出字段 jsonpath 的值,是否等于 xxxx

    #断言,全局变量中的两个字段值是否相等
    Then 比较两个全局变量中的字段 jsonpath 是否等于字段 jsonpath

    #断言,请求的status ,200,201等
    Then 最近一次请求响应状态是否是 (.*)

    #断言,返回的json是否符合jsonSchema的描述,可以是路径或者文本
    Then jsonSchema验证response:
    """
    /jsonSchema/.json/文件的据对路径
    or
    {
      ......
    }
    """

    #断言, 字面上的意思
    Then 当前responseBody中的 jsonpath 是否等于 xxxx

    #断言, 字面上的意思
    Then 当前responseBody中header的Content-Type是否等于 xxxxxx

    #断言, 字面上的意思
    Then 当前responseBody中header的 xxxxx 是否等于 xxxxx

    #断言, 字面上的意思
    Then 当前responseBody中的Cookie是否包含 xxxx

    #断言, 字面上的意思
    Then 当前responseBody中Cookie的 xxxx 是否等于 xxxx

    #特殊的断言可以二次开发
    #接口测试的扩展在src/main/java/junyan.cucumber.step_definitions.InterfaceSteps

