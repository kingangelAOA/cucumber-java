Feature: 绑定设备(推送相关)

    Scenario: 正常数据
        Given 设置接口名称 bind_device
        And 脚本更新全局变量 路径 <string> 方法 <string> 参数 <string>
        And 设置域名简称 restapi
        And 设置path /v1/bind_device
        And 设置method POST
        And 设置headers:
        """
        {
            "User-Agent":"Rajax/1 Apple/iPhone7,2 iPhone_OS/9.0.2 Eleme/5.4 ID/88BF6235-423A-4058-8580-ACE608E7B48A; IsJailbroken/0",
            "Content-Type":"application/json"
        }
        """
        And 设置请求数据:
        """
        {
            "push_token": "798a224d51574dfe8664553f3c53109e"
        }
        """
        And 执行请求
        Then 最近一次请求响应状态是否是 200
