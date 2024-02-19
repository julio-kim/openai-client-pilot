package com.pnoni.openai;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
class OpenaiCurlTests {
	@Value("${openai.api.url}")
	private String openaiApiUrl;
	@Value("${openai.api.key}")
	private String openaiApiKey;

	@Test
	void testOpenAI() {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("Authorization", "Bearer " + openaiApiKey);

		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<String> responseEntity = restTemplate.exchange(
				openaiApiUrl,
				HttpMethod.POST,
				new HttpEntity<>(createChatCompletion(), headers),
				String.class
		);

		System.out.println(responseEntity.getBody());
		assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
	}

	String createChatCompletion() {
		return """
			{
				"model": "gpt-3.5-turbo",
				"messages": [
					{
						"role": "system",
						"content": "친구와 대화 하듯이 친근한 대화 문구로 답해주세요."
					},
					{
						"role": "user",
						"content": "축구의 오프사이드룰에 대해 알고 있어?"
					}
				],
				"temperature": 1,
				"max_tokens": 256
			}
			""";
	}
}
