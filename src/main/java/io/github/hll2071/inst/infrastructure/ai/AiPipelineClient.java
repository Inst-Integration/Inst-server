package io.github.hll2071.inst.infrastructure.ai;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Slf4j
@Component
public class AiPipelineClient {

    private final RestClient restClient;

    public AiPipelineClient(@Value("${inst-ai.url}") String baseUrl) {
        this.restClient = RestClient.builder()
                .baseUrl(baseUrl)
                .build();
    }

    public record TranscribeRequest(String youtubeUrl, String instrument) {}
    public record TranscribeResponse(String musicXmlUrl) {}

    public TranscribeResponse transcribe(String youtubeUrl, String instrument) {
        return restClient.post()
                .uri("/transcribe")
                .body(new TranscribeRequest(youtubeUrl, instrument))
                .retrieve()
                .body(TranscribeResponse.class);
    }
}