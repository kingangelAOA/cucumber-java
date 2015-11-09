Feature: captchas

  @dev
  Scenario: 获取验证码hash
    Given 设置接口名称 captchas
    And 设置headers:
    """
      {
        "content-Type":"application/json"
      }
    """
    And 设置请求url http://restapi.alpha.elenet.me/v1/captchas
    And 设置method POST
    And 执行请求
    Then 最近一次请求响应状态是否是 200
