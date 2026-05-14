package io.github.hll2071.inst.infrastructure.ai;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.http.client.JdkClientHttpRequestFactory;
import org.springframework.web.client.RestClient;

import java.net.http.HttpClient;
import java.time.Duration;

@Slf4j
@Component
public class AiPipelineClient {

    private final RestClient restClient;

    public AiPipelineClient(
            @Value("${inst-ai.url}") String baseUrl,
            @Value("${runpod.api-key}") String apiKey
    ) {
        // DEEP-04: 타임아웃 설정 — RunPod 무응답 시 영구 대기 방지
        HttpClient httpClient = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(10))
                .build();
        JdkClientHttpRequestFactory factory = new JdkClientHttpRequestFactory(httpClient);
        factory.setReadTimeout(Duration.ofMinutes(20));

        this.restClient = RestClient.builder()
                .baseUrl(baseUrl)
                .requestFactory(factory)
                .defaultHeader("Authorization", "Bearer " + apiKey)
                .defaultHeader("Content-Type", "application/json")
                .build();
    }

    public record RunPodInput(String youtube_url, String instrument) {}
    public record RunPodRequest(RunPodInput input) {}
    public record RunPodOutput(String musicXmlUrl, String error) {}
    public record RunPodResponse(String id, String status, RunPodOutput output, String error) {}

    public String transcribe(String youtubeUrl, String instrument) {
        RunPodResponse response = restClient.post()
                .uri("/runsync")
                .body(new RunPodRequest(new RunPodInput(youtubeUrl, instrument)))
                .retrieve()
                .body(RunPodResponse.class);

        if (response == null || response.output() == null) {
            String reason = (response != null && response.error() != null) ? response.error() : "응답 없음";
            throw new RuntimeException("RunPod 실패: " + reason);
        }

        if (response.output().error() != null) {
            throw new RuntimeException("RunPod 변환 실패: " + response.output().error());
        }

        String musicXmlUrl = response.output().musicXmlUrl();
        if (musicXmlUrl == null) {
            throw new RuntimeException("RunPod musicXmlUrl 누락");
        }

        return musicXmlUrl;
    }
}