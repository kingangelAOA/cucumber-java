Feature: account
  @account
  Scenario: 账户密码登录
    Given 设置接口名称 login_for_user_info
    And 设置headers:
    """
      {
        "content-Type":"application/json"
      }
    """
    And 设置请求url http://restapi.alpha.elenet.me/v1/login_for_user_info
    And 设置method POST
    And 设置请求数据:
      """
      {
          "username": "tester_01",
          "password": "eleme517517"
      }
      """
    And 执行请求
    Then 最近一次请求响应状态是否是 200
  @account
  Scenario: 用户信息
    Given 设置接口名称 user
    And 设置headers:
    """
      {
        "Cookie":"SID=${login_for_user_info.response.headers.Cookie.SID}"
      }
    """
    And 设置请求url http://restapi.alpha.elenet.me/v1/user?extras[]=gift_amount
    And 设置method GET
    And 执行请求
    Then 最近一次请求响应状态是否是 200
  @account
  Scenario: 查询余额变更记录
    Given 设置接口名称 balance_records
    And 设置headers:
    """
      {
        "Cookie":"SID=${login_for_user_info.response.headers.Cookie.SID}"
      }
    """
    And 设置请求url http://restapi.alpha.elenet.me/v1/users/${login_for_user_info.response.body.user_id}/hongbao
    And 设置method GET

    And 执行请求
    Then 最近一次请求响应状态是否是 200
  @account
  Scenario: 红包
    Given 设置接口名称 hongbao
    And 设置headers:
    """
      {
        "Cookie":"SID=${login_for_user_info.response.headers.Cookie.SID}"
      }
    """
    And 设置请求url http://restapi.alpha.elenet.me/v1/users/${login_for_user_info.response.body.user_id}/hongbao
    And 设置method GET

    And 执行请求
    Then 最近一次请求响应状态是否是 200
  @account
  Scenario: 检查是否可提现（每日只可提现一次)
    Given 查看全局变量
    Given 设置接口名称 check
    And 设置headers:
    """
      {
        "Cookie":"SID=${login_for_user_info.response.headers.Cookie.SID}"
      }
    """
    And 设置请求url http://restapi.alpha.elenet.me/v1/user/balance/withdraw/check
    And 设置method GET
    And 执行请求
    Then 最近一次请求响应状态是否是 200

  @account
  Scenario: 检查用户是否收藏某餐厅
    Given 设置接口名称 restaurants
    And 设置headers:
    """
      {
        "Cookie":"SID=${login_for_user_info.response.headers.Cookie.SID}"
      }
    """
    And 设置请求url http://restapi.alpha.elenet.me/v1/users/${login_for_user_info.response.body.user_id}/favor/restaurants/60768
    And 设置method GET
    And 执行请求
    Then 最近一次请求响应状态是否是 200
