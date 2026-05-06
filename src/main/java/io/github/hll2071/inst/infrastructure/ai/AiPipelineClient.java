package io.github.hll2071.inst.infrastructure.ai;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Slf4j
@Component
public class AiPipelineClient {

    private final RestClient restClient;

    public AiPipelineClient(
            @Value("${inst-ai.url}") String baseUrl,
            @Value("${runpod.api-key}") String apiKey
    ) {
        this.restClient = RestClient.builder()
                .baseUrl(baseUrl)
                .defaultHeader("Authorization", "Bearer " + apiKey)
                .defaultHeader("Content-Type", "application/json")
                .build();
    }

    public record RunPodInput(String youtube_url, String instrument) {}
    public record RunPodRequest(RunPodInput input) {}
    public record RunPodOutput(String musicXmlUrl) {}
    public record RunPodResponse(String id, String status, RunPodOutput output) {}

    public String transcribe(String youtubeUrl, String instrument) {
        RunPodResponse response = restClient.post()
                .uri("/runsync")
                .body(new RunPodRequest(new RunPodInput(youtubeUrl, instrument)))
                .retrieve()
                .body(RunPodResponse.class);

        if (response == null || response.output() == null) {
            throw new RuntimeException("RunPod 응답 없음");
        }

        return response.output().musicXmlUrl();
    }
}