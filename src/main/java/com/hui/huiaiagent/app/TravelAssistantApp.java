package com.hui.huiaiagent.app;


import com.hui.huiaiagent.advisor.MyLoggerAdvisor;
import com.hui.huiaiagent.tools.WebScrapingTool;
import com.hui.huiaiagent.tools.WebSearchTool;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.QuestionAnswerAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.InMemoryChatMemory;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.tool.ToolCallback;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.ai.tool.ToolCallbacks;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import static org.springframework.ai.chat.client.advisor.AbstractChatMemoryAdvisor.CHAT_MEMORY_CONVERSATION_ID_KEY;
import static org.springframework.ai.chat.client.advisor.AbstractChatMemoryAdvisor.CHAT_MEMORY_RETRIEVE_SIZE_KEY;

@Slf4j
@Component
public class TravelAssistantApp {

    @Resource
    private VectorStore travelAssistantVectorStore;

    @Resource
    private ToolCallback[] tools;

    @Resource
    private ToolCallbackProvider toolCallbackProvider;

//    @Resource
//    private Advisor travelAssistentCloudAdvisor;

//    @Resource
//    private VectorStore pgVectorVectorStore;

    private final ChatClient chatClient;

    private static final String SYSTEM_PROMPT = "扮演深耕旅游领域的专家。开场向用户表明身份，告知用户可分享旅行规划需求与具体困扰。" +
            "询问用户对旅行目的地的偏好类型（如自然景观、人文历史、休闲度假等）及具体心仪地点；" +
            "了解用户可支配的时间长度（如周末短途/长假深度游）与预算范围（如经济型/奢华型）；" +
            "确认同行人员构成（如独自旅行/情侣出游/亲子家庭/朋友结伴）及成员特殊需求（如老人/儿童/宠物）；" +
            "引导用户详述过往旅行经历中的偏好与避雷点，以及当前计划中存在的具体困扰（如行程时间分配/景点选择矛盾/交通衔接困难等），以便提供精准的行程规划方案与预算优化建议。（为了保证信息的实时性，你可以调用工具）";


    public TravelAssistantApp(ChatModel dashscopeChatModel) {
        // 初始化基于内存的对话记忆
        ChatMemory chatMemory = new InMemoryChatMemory();
        chatClient = ChatClient.builder(dashscopeChatModel)
                .defaultSystem(SYSTEM_PROMPT)
                .defaultAdvisors(
                        new MessageChatMemoryAdvisor(chatMemory),
                        new MyLoggerAdvisor()
                )
                .build();
    }

    public String doChat(String message, String chatId) {
       ChatResponse response = chatClient
                .prompt()
                .user(message)
                .advisors(spec -> spec.param(CHAT_MEMORY_CONVERSATION_ID_KEY, chatId)
                        .param(CHAT_MEMORY_RETRIEVE_SIZE_KEY, 10))
                .advisors(new QuestionAnswerAdvisor(travelAssistantVectorStore))
               //基于阿里云数据库的向量搜索
//               .advisors(travelAssistentCloudAdvisor)
               //基于pgvector的向量搜索
//               .advisors(new QuestionAnswerAdvisor(pgVectorVectorStore))
                .tools(toolCallbackProvider)
                .call()
                .chatResponse();
        String content = response.getResult().getOutput().getText();
        log.info("content: {}", content);
        return content;
    }

    public Flux<String> doChatByStream(String message, String chatId) {
        return chatClient
                .prompt()
                .user(message)
                .advisors(spec -> spec.param(CHAT_MEMORY_CONVERSATION_ID_KEY, chatId)
                        .param(CHAT_MEMORY_RETRIEVE_SIZE_KEY, 10))
                .stream()
                .content();
    }


}
