package com.hui.huiaiagent.tools;


import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;

import java.util.List;
import java.util.stream.Collectors;

/*
 * 联网搜索工具
 */
@Slf4j
public class WebSearchTool {
    private static final String SEARCH_API_URL = "https://www.searchapi.io/api/v1/search";

    private String apiKey;

    public WebSearchTool(String apiKey) {
        this.apiKey = apiKey;
    }

    @Tool(description = "Search for information from Baidu Search Engine")
    public String searchWeb(@ToolParam(description = "Query") String query) {
        try {
            HttpResponse response = HttpRequest.get(SEARCH_API_URL)
                    .form("engine", "baidu")
                    .form("q", query)
                    .form("api_key", apiKey)
                    .timeout(10000) // 可选：设置超时（毫秒）
                    .execute();
            JSONObject jsonObject = JSONUtil.parseObj(response.body());
            JSONArray organicResults = jsonObject.getJSONArray("organic_results");
            if (organicResults == null) {
                return "No results found.";
            }
            List<Object> subList = null;
            if (organicResults.size()<5){
                subList = organicResults.subList(0, organicResults.size());
            }else{
                subList = organicResults.subList(0, 5);
            }
            log.info("调用了-----》【联网搜索工具】");
            return subList.stream().map(obj -> {
                JSONObject jsonObject1 = (JSONObject) obj;
                return jsonObject1.toString();
            }).collect(Collectors.joining(","));
        } catch (Exception e) {
            return "Error search Baidu: " + e.getMessage();
        }
    }


}
