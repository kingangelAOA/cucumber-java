Feature: 兼容测试
  @compatibility
  Scenario: 我的
    Given 设置远程url http://127.0.0.1:4723
    And 设置platform android
    Given 设置超时时间 50
    Given 启动log
    And 初始化driver
    Given 设置场景名称 我的
    Given 查询单个元素 我的
    Given 截图
    And 点击元素 我的
    Given 查询单个元素 登录/注册
    Given 截图
    And 点击元素 登录/注册
    Given 查询单个元素 注册
    And 点击元素 注册
    Given 截图
    And 点击返回键
    Given 查询单个元素 忘记密码
    And 点击元素 忘记密码
    Given 查询单个元素 重置密码
    Given 截图
    And 点击返回键
    Given 查询单个元素 短信验证码登录
    And 点击元素 短信验证码登录
    Given 查询单个元素 验证并登录
    Given 截图
    And 点击返回键
    Given 查询单个元素 用户名
    Given 输入 13764905634 到元素 用户名 中
    Given 截图
    Given 查询单个元素 登录密码
    Given 输入 king258angel@ 到元素 登录密码 中
    Given 截图
    Given 查询单个元素 登录
    And 点击元素 登录

    Given 查询单个元素 设置
    And 点击元素 设置
    Given 查询单个元素 退出登录
    Given 截图
    And 点击返回键

    Given 查询单个元素 我的收藏
    And 点击元素 我的收藏
    Given 查询单个元素 我的收藏暂无收藏
    Given 截图
    And 点击返回键

    Given 查询单个元素 我的收藏
    And 点击元素 我的收藏
    Given 查询单个元素 我的收藏暂无收藏
    Given 截图
    And 点击返回键

    Given 查询单个元素 美食相册
    And 点击元素 美食相册
    Given 查询单个元素 下个单再来拍照吧
    Given 截图
    And 点击返回键

    Given 查询单个元素 推荐有奖
    And 点击元素 推荐有奖
    Given 查询单个元素 邀请好友
    Given 截图
    And 点击返回键

    Given 查询单个元素 积分商城
    And 点击元素 积分商城
    Given 查询单个元素 兑换记录
    Given 截图
    And 点击元素 兑换记录
    Given 查询单个元素 去积分商城看看
    Given 截图
    And 点击返回键
    Given 查询单个元素 积分商城第一个商品
    And 点击元素 积分商城第一个商品
    Given 查询单个元素 立即兑换
    Given 截图
    And 点击返回键
    And 点击返回键

    Given 查询单个元素 饿配送会员卡
    And 点击元素 饿配送会员卡
    Given 查询单个元素 饿配送会员卡绑定
    Given 截图
    Given 查询单个元素 饿配送会员卡查询
    And 点击元素 饿配送会员卡查询
    Given 查询单个元素 饿配送会员卡查询提交
    Given 截图
    And 点击返回键
    And 点击元素 饿配送会员卡绑定
    Given 查询单个元素 绑定会员卡
    Given 截图
    And 点击返回键
    And 点击返回键

    Given 查询单个元素 服务中心
    And 点击元素 服务中心
    Given 查询单个元素 下个单再来拍照吧
    Given 截图
    And 点击返回键


  @compatibility
  Scenario:


  @compatibility
  Scenario: 退出登录
    Given 设置场景名称 退出登录
    Given 打开应用 me.ele Activity .Launcher
    Given 查询单个元素 我的
    And 点击元素 我的
    Given 查询单个元素 设置
    And 点击元素 设置
    Given 查询单个元素 退出登录
    And 点击元素 退出登录

  @compatibility
  Scenario: 退出应用
    Given 设置场景名称 退出应用
    Given 退出当前应用
    Given 关闭log

