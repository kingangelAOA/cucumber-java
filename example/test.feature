Feature: test
  @interface
  Scenario: test
    Given 设置全局变量 {"aaa":"bbbbb", "d":1, "user_id":1, "abc":{"yy":"asdfas"},"www":[{"gg":1, "ff":1.234},{"hh":"sdfasdf"}]}
    Given 设置接口名称 test1
    And 设置headers:
    """
      {
        "content-type":"application/x-www-form-urlencoded",
        "Cookie":"aa=bb;cc=${aaa}"
      }
    """
    And 设置域名简称 restapi
    And 设置path /test${d}
    And 设置method POST
    And 设置请求数据:
#    """
#      {
#        "aa":1,
#        "bb":"cc",
#        "dd":[
#          {
#            "ee":true,
#            "ff":1.234556
#          },
#          {
#            "gg":${abc.yy},
#            "hh":<[/Users/kingangelTOT/Application/git_work/python_lib/Lib/common/test1.py]-[test]-[${www[0].ff}, 888888]>
#          }
#        ]
#      }
#    """
#    And 设置请求数据:
      """
      aa=${abc.yy}&cc=dd
      """
    And 执行请求

    Then 执行脚本,路径 /Users/kingangelTOT/Application/git_work/python_lib/Lib/common/test1.py 方法 assert_test 参数 null
