package com.hui.huiaiagent;

import org.springframework.ai.autoconfigure.vectorstore.pgvector.PgVectorStoreAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//@SpringBootApplication(exclude = PgVectorStoreAutoConfiguration.class)
@SpringBootApplication
public class HuiAiAgentApplication {

    public static void main(String[] args) {
        SpringApplication.run(HuiAiAgentApplication.class, args);
    }

}
