package io.github.hll2071.inst.application.score;

import io.github.hll2071.inst.domain.score.Score;
import io.github.hll2071.inst.infrastructure.ai.AiPipelineClient;
import io.github.hll2071.inst.infrastructure.score.ScoreRepository;
import io.github.hll2071.inst.shared.exception.ErrorCode;
import io.github.hll2071.inst.shared.exception.InstException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ScorePipelineRunner {

    private final ScoreRepository scoreRepository;
    private final AiPipelineClient aiPipelineClient;

    @Async("virtualThreadExecutor")
    public void run(Long scoreId, String youtubeUrl, String instrument) {
        Score score = scoreRepository.findById(scoreId)
                .orElseThrow(() -> new InstException(ErrorCode.SCORE_NOT_FOUND));
        try {
            String musicXmlUrl = aiPipelineClient.transcribe(youtubeUrl, instrument);
            score.markDone(musicXmlUrl);
        } catch (Exception e) {
            log.error("파이프라인 실패 scoreId={}", scoreId, e);
            score.markFailed();
        } finally {
            scoreRepository.save(score);
        }
    }
}