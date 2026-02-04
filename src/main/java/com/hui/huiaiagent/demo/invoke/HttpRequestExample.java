package com.hui.huiaiagent.demo.invoke;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.json.JSONObject;

public class HttpRequestExample {
    public static void main(String[] args) {
        // 请求的URL
        String url = "https://dashscope.aliyuncs.com/compatible-mode/v1/chat/completions";

        // 创建JSON请求体
        JSONObject jsonObject = new JSONObject();
        jsonObject.set("model", "qwen-plus");

        // 创建messages数组
        JSONObject message1 = new JSONObject();
        message1.set("role", "system");
        message1.set("content", "You are a helpful assistant.");

        JSONObject message2 = new JSONObject();
        message2.set("role", "user");
        message2.set("content", "你是谁？");

        jsonObject.set("messages", new JSONObject[]{message1, message2});

        // 发送POST请求
        try {
            HttpResponse response = HttpRequest.post(url)
                    .header("Authorization", "Bearer " + SDKAppConfig.API_KEY)
                    .header("Content-Type", "application/json")
                    .body(jsonObject.toString())
                    .execute();

            // 输出响应内容
            System.out.println(response.body());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}