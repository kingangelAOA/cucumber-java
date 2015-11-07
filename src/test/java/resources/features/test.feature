Feature: test
  @interface
  Scenario: test
    Given 设置全局变量 {"aaa":"bbbbb", "d":1, "user_id":1}
    Given 设置接口名称 test1
    And 设置headers {"content-type":"application/json","Cookie":"aa=bb;cc=dd"}
    And 设置请求url http://localhost:3000/test"${d}"
    And 设置method POST
    And 设置请求数据:
    """
    /Users/kingangelTOT/Application/git_work/cucumber-java/src/test/java/resources/interface_data/test_data.json
    """

    And 执行请求
    Given 数据库中获取数据设置到全局变量中, sql select * from users where id = ${test1.requestBody.d} ,获取行数 0, 获取的参数 id,password,permission
    Then 最近一次请求响应状态是否是 200
    Given 查看全局变量
    Then test1.responseBody.g[0] 的值是否包含这些字段 cc,ff
    Then 从全局变量中取出字段 test1.responseBody.g[0].cc 的值,是否等于 wwww

    Given 设置接口名称 test2
    And 设置请求url http://localhost:3000/test2
    And 设置method POST
    And 设置请求数据:
      """
      {
          "user_id": 111,
          "bbb": "${test1.responseBody.d}",
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
    And 设置headers {"content-type":"application/json"}
    And 执行请求
    Given 查看全局变量
    Then 从全局变量中取出字段 test2.responseBody.ccc[0].acd 的值,是否等于 aaaa
    Then 从全局变量中取出字段 test2.responseBody.bbb 的值,是否等于 1

  @android,@all
  Scenario: android
    Given 设置远程url http://127.0.0.1:4723
    And 设置platform android
    And 初始化driver
    Given 查询单个元素 我的, 查询方法 findElementByName, 查询条件 我的
    And 点击元素 我的
    Then 元素 我的 的文本信息是否等于 我的

  @android,@all
  Scenario: android2
#    Given 设置远程url http://127.0.0.1:4723
#    And 设置platform android
#    And 初始化driver
    And 打开应用 me.ele Activity .Launcher
    Given 查询单个元素 我的, 查询方法 findElementByName, 查询条件 我的
    And 点击元素 我的
    Then 元素 我的 的文本信息是否等于 我的

  @web,@all
  Scenario: web
    Given 设置远程url http://127.0.0.1:4444
    And 设置platform web
    And 设置web浏览器 firefox
    And 初始化driver
    And 跳转到网页address http://www.baidu.com

    Given 查询单个元素 百度搜索内容框, 查询方法 findElementById, 查询条件 kw
    Given 查看缓存的元素
    And 输入 钢铁雄心4 到元素 百度搜索内容框 中
    Given 查看缓存的元素
    And 查询单个元素 百度一下, 查询方法 findElementById, 查询条件 su
    Given 查看缓存的元素
    And 点击元素 百度一下
    Given 查看缓存的元素
  @eleme
  Scenario: eleme_test
    Given 设置远程url http://127.0.0.1:4723
    And 设置platform android
    And 初始化driver
    Given 查询单个元素 搜索框
    Given 查询单个元素 品牌馆
    Given 查询单个元素 限时抢购
    Given 查询单个元素 分类
    Given 查询单个元素 第一家餐厅
    Given 查询单个元素 鲜花蛋糕
    Given 查看缓存的元素

