package com.hui.huiaiagent;

import com.hui.huiaiagent.app.LoveApp;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

@SpringBootTest
public class LoveAppTest {

    @Resource
    private LoveApp loveApp;

    @Test
    void testChat(){
        String chatId = UUID.randomUUID().toString();
        //1
        String message = "你好，我是晖";
        String answer = loveApp.doChat(message, chatId);
        Assertions.assertNotNull(answer);
        //2
        message = "我的另一半叫琳，我想让她更爱我";
        answer = loveApp.doChat(message, chatId);
        Assertions.assertNotNull(answer);
        //3
        message = "我的另一半叫什么？";
        answer = loveApp.doChat(message, chatId);
        Assertions.assertNotNull(answer);
    }
}
