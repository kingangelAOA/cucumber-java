Feature: test
  @interface
  Scenario: test
    Given 设置全局变量 {"aaa":"bbbbb", "d":1, "user_id":1}
    Given 设置接口名称 test1
    And 设置请求url http://localhost:3000/test"${d}"
    And 设置method POST
    And 设置请求数据格式 JSON
    And 设置请求数据:
    """
    /src/test/java/resources/test_data/test_data.json
    """
    Given 设置cookies aa=bb;cc=dd;adf_=adfasf
    And 设置headers {"Content-Length": 143}
    Given 数据库中获取数据设置到全局变量中, sql select * from cases where user_id = ${user_id} ,获取行数 50, 获取的参数 [summary]
    And 执行请求
    Then 最近一次请求响应状态是否是 200
    Then 接口 test1 response的body中的 [] 的值是否包含这些字段 ["a", "c", "d", "e"]

    Given 从接口 test1 的response的body中获取值,值索引为 ["a"],字段名称为: id,设置到全局变量中
    Given 查看全局变量

    Given 设置接口名称 test2
    And 设置请求url http://localhost:3000/test2
    And 设置method POST
    And 设置请求数据格式 JSON
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
    Given 设置cookies aa=bb;cc=dd;adf_=adfasf
    And 设置headers {"Content-Length": 143}
    And 执行请求

    Given 从接口 test2 的response的body中获取值,值索引为 ["bbb"],字段名称为: test2_id,设置到全局变量中
    Given 查看所有cache
    Given 查看全局变量
    Then 从全局变量中取出字段 test2_id 的值,是否等于 "b"
