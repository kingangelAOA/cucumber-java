Feature: login_for_user_info

  @dev
  Scenario: 账户密码登录
    Given 查看全局变量
    Given 设置接口名称 账户密码登录
    And 设置headers:
    """
      {
        "content-Type":"application/json"
      }
    """
    And 设置请求url http://restapi.alpha.elenet.me/v1/captchas/${captchas.responseBody.code}
    And 设置method POST
    And 设置请求数据:
      """
      {
          "username": "18817551583",
          "password": "p0000000"
      }
      """
    And 执行请求
    Then 最近一次请求响应状态是否是 200
