Feature: 兼容测试
  @compatibility
  Scenario: 登陆
    Given 设置远程url http://127.0.0.1:4723
    And 设置platform android
    And 初始化driver
    Given 查询单个元素 我的
    And 点击元素 我的
    Given 查询单个元素 登录/注册
    And 点击元素 登录/注册
    And 点击返回键
#    Given 查询单个元素 注册
#    And 点击元素 注册
#    And 点击返回键
#    Given 查询单个元素 忘记密码
#    And 点击元素 忘记密码
#    Given 查询单个元素 重置密码
#    And 点击返回键
#    Given 查询单个元素 短信验证码登录
#    And 点击元素 短信验证码登录
#    And 点击返回键
#    Given 查询单个元素 手机/邮箱/用户名
#    Given 查询单个元素 QQ登陆
#    And 点击元素 QQ登陆
#    Given 查询单个元素 我的收藏


