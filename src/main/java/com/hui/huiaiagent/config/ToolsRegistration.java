package com.hui.huiaiagent.config;

import com.hui.huiaiagent.tools.WebScrapingTool;
import com.hui.huiaiagent.tools.WebSearchTool;
import org.springframework.ai.tool.ToolCallback;
import org.springframework.ai.tool.ToolCallbacks;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ToolsRegistration {

    @Value("${search-api.api-key}")
    private String SearchApiKey;

    @Bean
    public ToolCallback[] tools() {
        return ToolCallbacks.from(
                new WebScrapingTool(),
                new WebSearchTool(SearchApiKey),
                new ToolsRegistration()
                );
    }
}
