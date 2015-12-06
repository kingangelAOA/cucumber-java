Feature: 获取验证码hash

    Scenario: 正常数据
        Given 设置接口名称 captchas
        And 设置域名简称 restapi
        And 设置path /v1/captchas
        And 设置method POST
        And 设置headers:
        """
        {
            "Content-Type":"application/json"
        }
        """
        And 执行请求