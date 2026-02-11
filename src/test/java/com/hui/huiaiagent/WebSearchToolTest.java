package com.hui.huiaiagent;

import com.hui.huiaiagent.tools.WebSearchTool;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class WebSearchToolTest {

    @Value("${search-api.api-key}")
    private String SearchApiKey;
    @Test
    public void testSearchWeb() {
        WebSearchTool tool = new WebSearchTool(SearchApiKey);
        String query = "如何学习java";
        String result = tool.searchWeb(query);
        assertNotNull(result);
    }
}
