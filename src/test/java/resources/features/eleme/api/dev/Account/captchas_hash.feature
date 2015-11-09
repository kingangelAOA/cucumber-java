Feature: captchas_hash

  @dev
  Scenario: 获取验证码
    Given 查看全局变量
    Given 设置接口名称 captchas_hash
    And 设置headers:
    """
      {
        "content-Type":"application/json"
      }
    """
    And 设置请求url http://restapi.alpha.elenet.me/v1/captchas/${captchas.responseBody.code}
    And 设置method GET
    And 执行请求
    Then 最近一次请求响应状态是否是 200
