Feature: user

  @dev
  Scenario: 用户信息
    Given 查看全局变量
    Given 设置接口名称 用户信息
    And 设置headers:
    """
      {
        "Cookie":"SID=${账户密码登录.responseBody.Cookie.SID}"
      }
    """
    And 设置请求url http://restapi.alpha.elenet.me/v1/user?extras[]=gift_amount
    And 设置method GET
    And 执行请求
    Then 最近一次请求响应状态是否是 200
