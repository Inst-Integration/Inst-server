package io.github.hll2071.inst.application.score;

import io.github.hll2071.inst.domain.score.Score;
import io.github.hll2071.inst.domain.score.ScoreStatus;
import io.github.hll2071.inst.domain.score.ScoreType;
import io.github.hll2071.inst.domain.user.User;
import io.github.hll2071.inst.infrastructure.s3.S3Uploader;
import io.github.hll2071.inst.infrastructure.score.ScoreRepository;
import io.github.hll2071.inst.infrastructure.user.UserRepository;
import io.github.hll2071.inst.shared.enums.Instrument;
import io.github.hll2071.inst.shared.exception.ErrorCode;
import io.github.hll2071.inst.shared.exception.InstException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class CreateScoreByFileUseCase {

    private final ScoreRepository scoreRepository;
    private final UserRepository userRepository;
    private final S3Uploader s3Uploader;

    public record Command(Long userId, String title, Instrument instrument, MultipartFile file) {}
    public record Result(Long scoreId) {}

    @Transactional
    public Result execute(Command command) throws IOException {
        User user = userRepository.findById(command.userId())
                .orElseThrow(() -> new InstException(ErrorCode.USER_NOT_FOUND));

        String originalFilename = command.file().getOriginalFilename();
        String contentType = command.file().getContentType();
        String url = s3Uploader.upload(command.file().getBytes(), originalFilename, contentType);

        boolean isPdf = originalFilename != null && originalFilename.endsWith(".pdf");

        Score score = Score.builder()
                .user(user)
                .title(command.title())
                .scoreType(ScoreType.UPLOAD)
                .instrument(command.instrument())
                .status(ScoreStatus.DONE)
                .musicXmlUrl(isPdf ? null : url)
                .pdfUrl(isPdf ? url : null)
                .build();

        scoreRepository.save(score);
        return new Result(score.getId());
    }
}