package com.pnoni.openai;

import com.theokanning.openai.completion.chat.ChatCompletionRequest;
import com.theokanning.openai.completion.chat.ChatCompletionResult;
import com.theokanning.openai.completion.chat.ChatMessage;
import com.theokanning.openai.completion.chat.ChatMessageRole;
import com.theokanning.openai.service.OpenAiService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
public class OpenaiJavaTest {
    @Value("${openai.api.key}")
    private String openaiApiKey;

    @Test
    void testOpenAI() {
        OpenAiService service = new OpenAiService(openaiApiKey);
        ChatCompletionResult result = service.createChatCompletion(
            ChatCompletionRequest.builder()
                .model("gpt-3.5-turbo")
                .messages(
                    List.of(
                        new ChatMessage(ChatMessageRole.SYSTEM.value(), "친구와 대화 하듯이 친근한 대화 문구로 답해주세요."),
                        new ChatMessage(ChatMessageRole.USER.value(), "축구의 오프사이드룰에 대해 알고 있어?")
                    )
                )
                .temperature(1.0)
                .maxTokens(256)
                .build()
        );

        result.getChoices().forEach(System.out::println);
        assertThat(result.getChoices().size()).isGreaterThan(0);
    }
}
