$(document).ready(function() {var formatter = new CucumberHTML.DOMFormatter($('.cucumber-report'));formatter.uri("test.feature");
formatter.feature({
  "line": 1,
  "name": "test",
  "description": "",
  "id": "test",
  "keyword": "Feature"
});
formatter.before({
  "duration": 247840,
  "status": "passed"
});
formatter.scenario({
  "line": 3,
  "name": "test",
  "description": "",
  "id": "test;test",
  "type": "scenario",
  "keyword": "Scenario",
  "tags": [
    {
      "line": 2,
      "name": "@interface"
    }
  ]
});
formatter.step({
  "line": 4,
  "name": "设置全局变量 {\"aaa\":\"bbbbb\", \"d\":1, \"user_id\":1}",
  "keyword": "Given "
});
formatter.step({
  "line": 5,
  "name": "设置接口名称 test1",
  "keyword": "Given "
});
formatter.step({
  "line": 6,
  "name": "设置请求url http://localhost:3000/test\"${d}\"",
  "keyword": "And "
});
formatter.step({
  "line": 7,
  "name": "设置method POST",
  "keyword": "And "
});
formatter.step({
  "line": 8,
  "name": "设置请求数据格式 JSON",
  "keyword": "And "
});
formatter.step({
  "line": 9,
  "name": "设置请求数据:",
  "keyword": "And ",
  "doc_string": {
    "content_type": "",
    "line": 10,
    "value": "/src/test/java/resources/test_data/test_data.json"
  }
});
formatter.step({
  "line": 13,
  "name": "设置cookies aa\u003dbb;cc\u003ddd;adf_\u003dadfasf",
  "keyword": "Given "
});
formatter.step({
  "line": 14,
  "name": "设置headers {\"Content-Length\": 143}",
  "keyword": "And "
});
formatter.step({
  "line": 15,
  "name": "数据库中获取数据设置到全局变量中, sql select * from cases where user_id \u003d ${user_id} ,获取行数 50, 获取的参数 [summary]",
  "keyword": "Given "
});
formatter.step({
  "line": 16,
  "name": "执行请求",
  "keyword": "And "
});
formatter.step({
  "line": 17,
  "name": "最近一次请求响应状态是否是 200",
  "keyword": "Then "
});
formatter.step({
  "line": 18,
  "name": "接口 test1 response的body中的 [] 的值是否包含这些字段 [\"a\", \"c\", \"d\", \"e\"]",
  "keyword": "Then "
});
formatter.step({
  "line": 20,
  "name": "从接口 test1 的response的body中获取值,值索引为 [\"a\"],字段名称为: id,设置到全局变量中",
  "keyword": "Given "
});
formatter.step({
  "line": 21,
  "name": "查看全局变量",
  "keyword": "Given "
});
formatter.step({
  "line": 23,
  "name": "设置接口名称 test2",
  "keyword": "Given "
});
formatter.step({
  "line": 24,
  "name": "设置请求url http://localhost:3000/test2",
  "keyword": "And "
});
formatter.step({
  "line": 25,
  "name": "设置method POST",
  "keyword": "And "
});
formatter.step({
  "line": 26,
  "name": "设置请求数据格式 JSON",
  "keyword": "And "
});
formatter.step({
  "line": 27,
  "name": "设置请求数据:",
  "keyword": "And ",
  "doc_string": {
    "content_type": "",
    "line": 28,
    "value": "{\n    \"user_id\": 111,\n    \"bbb\": \"${id}\",\n    \"ccc\": [\n        {\n            \"acd\": \"aaaa\"\n        }\n    ],\n    \"dd\": {\n        \"cc\": [\n            \"a\",\n            \"b\"\n        ]\n    }\n}"
  }
});
formatter.step({
  "line": 45,
  "name": "设置cookies aa\u003dbb;cc\u003ddd;adf_\u003dadfasf",
  "keyword": "Given "
});
formatter.step({
  "line": 46,
  "name": "设置headers {\"Content-Length\": 143}",
  "keyword": "And "
});
formatter.step({
  "line": 47,
  "name": "执行请求",
  "keyword": "And "
});
formatter.step({
  "line": 49,
  "name": "从接口 test2 的response的body中获取值,值索引为 [\"bbb\"],字段名称为: test2_id,设置到全局变量中",
  "keyword": "Given "
});
formatter.step({
  "line": 50,
  "name": "查看所有cache",
  "keyword": "Given "
});
formatter.step({
  "line": 51,
  "name": "查看全局变量",
  "keyword": "Given "
});
formatter.step({
  "line": 52,
  "name": "从全局变量中取出字段 test2_id 的值,是否等于 \"b\"",
  "keyword": "Then "
});
formatter.match({
  "arguments": [
    {
      "val": "{\"aaa\":\"bbbbb\", \"d\":1, \"user_id\":1}",
      "offset": 7
    }
  ],
  "location": "InterfaceSteps.java:94"
});
formatter.result({
  "duration": 152599978,
  "status": "passed"
});
formatter.match({
  "arguments": [
    {
      "val": "test1",
      "offset": 7
    }
  ],
  "location": "InterfaceSteps.java:28"
});
formatter.result({
  "duration": 102742,
  "status": "passed"
});
formatter.match({
  "arguments": [
    {
      "val": "http://localhost:3000/test\"${d}\"",
      "offset": 8
    }
  ],
  "location": "InterfaceSteps.java:33"
});
formatter.result({
  "duration": 681620,
  "status": "passed"
});
formatter.match({
  "arguments": [
    {
      "val": "POST",
      "offset": 9
    }
  ],
  "location": "InterfaceSteps.java:46"
});
formatter.result({
  "duration": 74046,
  "status": "passed"
});
formatter.match({
  "arguments": [
    {
      "val": "JSON",
      "offset": 9
    }
  ],
  "location": "InterfaceSteps.java:51"
});
formatter.result({
  "duration": 55586,
  "status": "passed"
});
formatter.match({
  "location": "InterfaceSteps.java:56"
});
formatter.result({
  "duration": 6283823,
  "status": "passed"
});
formatter.match({
  "arguments": [
    {
      "val": "aa\u003dbb;cc\u003ddd;adf_\u003dadfasf",
      "offset": 10
    }
  ],
  "location": "InterfaceSteps.java:75"
});
formatter.result({
  "duration": 368716,
  "status": "passed"
});
formatter.match({
  "arguments": [
    {
      "val": "{\"Content-Length\": 143}",
      "offset": 10
    }
  ],
  "location": "InterfaceSteps.java:90"
});
formatter.result({
  "duration": 322827,
  "status": "passed"
});
formatter.match({
  "arguments": [
    {
      "val": "select * from cases where user_id \u003d ${user_id} ",
      "offset": 22
    },
    {
      "val": "50",
      "offset": 75
    },
    {
      "val": "[summary]",
      "offset": 85
    }
  ],
  "location": "InterfaceSteps.java:148"
});
formatter.result({
  "duration": 80059108,
  "status": "passed"
});
formatter.match({
  "location": "InterfaceSteps.java:98"
});
formatter.result({
  "duration": 176167205,
  "status": "passed"
});
formatter.match({
  "arguments": [
    {
      "val": "200",
      "offset": 14
    }
  ],
  "location": "InterfaceSteps.java:182"
});
formatter.result({
  "duration": 2214073,
  "status": "passed"
});
formatter.match({
  "arguments": [
    {
      "val": "test1",
      "offset": 3
    },
    {
      "val": "[]",
      "offset": 25
    },
    {
      "val": "[\"a\", \"c\", \"d\", \"e\"]",
      "offset": 39
    }
  ],
  "location": "InterfaceSteps.java:187"
});
formatter.result({
  "duration": 275459,
  "status": "passed"
});
formatter.match({
  "arguments": [
    {
      "val": "test1",
      "offset": 4
    },
    {
      "val": "[\"a\"]",
      "offset": 34
    },
    {
      "val": "id",
      "offset": 47
    }
  ],
  "location": "InterfaceSteps.java:126"
});
formatter.result({
  "duration": 161432,
  "status": "passed"
});
formatter.match({
  "location": "InterfaceSteps.java:167"
});
formatter.result({
  "duration": 290644,
  "status": "passed"
});
formatter.match({
  "arguments": [
    {
      "val": "test2",
      "offset": 7
    }
  ],
  "location": "InterfaceSteps.java:28"
});
formatter.result({
  "duration": 36796,
  "status": "passed"
});
formatter.match({
  "arguments": [
    {
      "val": "http://localhost:3000/test2",
      "offset": 8
    }
  ],
  "location": "InterfaceSteps.java:33"
});
formatter.result({
  "duration": 88911,
  "status": "passed"
});
formatter.match({
  "arguments": [
    {
      "val": "POST",
      "offset": 9
    }
  ],
  "location": "InterfaceSteps.java:46"
});
formatter.result({
  "duration": 36198,
  "status": "passed"
});
formatter.match({
  "arguments": [
    {
      "val": "JSON",
      "offset": 9
    }
  ],
  "location": "InterfaceSteps.java:51"
});
formatter.result({
  "duration": 30935,
  "status": "passed"
});
formatter.match({
  "location": "InterfaceSteps.java:56"
});
formatter.result({
  "duration": 335473,
  "status": "passed"
});
formatter.match({
  "arguments": [
    {
      "val": "aa\u003dbb;cc\u003ddd;adf_\u003dadfasf",
      "offset": 10
    }
  ],
  "location": "InterfaceSteps.java:75"
});
formatter.result({
  "duration": 109404,
  "status": "passed"
});
formatter.match({
  "arguments": [
    {
      "val": "{\"Content-Length\": 143}",
      "offset": 10
    }
  ],
  "location": "InterfaceSteps.java:90"
});
formatter.result({
  "duration": 64718,
  "status": "passed"
});
formatter.match({
  "location": "InterfaceSteps.java:98"
});
formatter.result({
  "duration": 15527593,
  "status": "passed"
});
formatter.match({
  "arguments": [
    {
      "val": "test2",
      "offset": 4
    },
    {
      "val": "[\"bbb\"]",
      "offset": 34
    },
    {
      "val": "test2_id",
      "offset": 49
    }
  ],
  "location": "InterfaceSteps.java:126"
});
formatter.result({
  "duration": 169800,
  "status": "passed"
});
formatter.match({
  "location": "InterfaceSteps.java:122"
});
formatter.result({
  "duration": 4153835,
  "status": "passed"
});
formatter.match({
  "location": "InterfaceSteps.java:167"
});
formatter.result({
  "duration": 275523,
  "status": "passed"
});
formatter.match({
  "arguments": [
    {
      "val": "test2_id",
      "offset": 11
    },
    {
      "val": "\"b\"",
      "offset": 28
    }
  ],
  "location": "InterfaceSteps.java:171"
});
formatter.result({
  "duration": 102642,
  "status": "passed"
});
formatter.after({
  "duration": 11203,
  "status": "passed"
});
});