package com.hui.huiaiagent;

import com.hui.huiaiagent.tools.WebScrapingTool;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class WebScrapingToolTest {

    @Test
    public void testScrapeWebPage() {
        WebScrapingTool tool = new WebScrapingTool();
        String url = "https://www.zhihu.com/tardis/bd/ans/1813076759";
        String result = tool.scrapeWebPage(url);
        assertNotNull(result);
    }
}
