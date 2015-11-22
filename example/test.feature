Feature: test
  @interface
  Scenario: test
    Given 设置全局变量 {"aaa":"bbbbb", "d":1, "user_id":1, "www":[{"gg":1, "ff":1.234},{"hh":"sdfasdf"}]}
    Given 设置接口名称 test1
    And 设置headers:
    """
      {
        "content-type":"application/json",
        "Cookie":"aa=bb;cc=${aaa}"
      }
    """
    And 设置请求url http://localhost:3000/test${d}
    And 设置method POST
    And 设置请求数据:
    """
      {
        "aa":1,
        "bb":"cc",
        "dd":[
          {
            "ee":true,
            "ff":1.234556
          },
          {
            "gg":${www[0].ff},
            "hh":"<[/Users/kingangelTOT/Application/git_work/python_lib/Lib/common/test1.py]-[test]-[${www[0].ff}]>"
          }
        ]
      }
    """
    And 执行请求
