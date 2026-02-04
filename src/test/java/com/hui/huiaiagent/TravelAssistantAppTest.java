package com.hui.huiaiagent;

import com.hui.huiaiagent.app.TravelAssistantApp;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

@SpringBootTest
public class TravelAssistantAppTest {

    @Resource
    private TravelAssistantApp travelAssistantApp;

    @Test
    void testChat(){
        String chatId = UUID.randomUUID().toString();
        //1
        String message = "你好，我是晖";
        String answer = travelAssistantApp.doChat(message, chatId);
        Assertions.assertNotNull(answer);
        //2
        message = "我的另一半叫琳，我想让她更爱我";
        answer = travelAssistantApp.doChat(message, chatId);
        Assertions.assertNotNull(answer);
        //3
        message = "我的另一半叫什么？";
        answer = travelAssistantApp.doChat(message, chatId);
        Assertions.assertNotNull(answer);
    }
    @Test
    void testChat2(){
        String chatId = UUID.randomUUID().toString();
        String message = "我想去武汉玩，请给我制定一份旅游攻略";
        String answer = travelAssistantApp.doChat(message, chatId);
        Assertions.assertNotNull(answer);
    }
}
