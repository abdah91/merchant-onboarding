package com.merchant.service.impl;

import com.merchant.model.dto.SlackPayload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class SlackClient {

    private final WebClient webClient;

    @Value("${slack.webhook.url}")
    private String slackWebhookUrl;

    @Autowired
    public SlackClient(WebClient.Builder webClientBuilder, RestTemplateBuilder restTemplateBuilder) {
        this.webClient = webClientBuilder.build();
    }

    public String sendMessage(String text) {
        return webClient.post()
                .uri(slackWebhookUrl)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new SlackPayload(text))
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }
}
