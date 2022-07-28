package anpopo.powerboard.dto;

import org.springframework.util.StringUtils;

import java.io.Serializable;
import java.time.LocalDateTime;

public record ArticleCommentResponse(
        Long articleCommentId,
        String content,
        LocalDateTime createdAt,
        String email,
        String nickname
) implements Serializable {

    public static ArticleCommentResponse of(Long articleCommentId, String content, LocalDateTime createdAt, String email, String nickname) {
        return new ArticleCommentResponse(articleCommentId, content, createdAt, email, nickname);
    }

    public static ArticleCommentResponse from(ArticleCommentDto dto) {

        String nickname = StringUtils.hasText(dto.userAccountDto().nickname())
                ? dto.userAccountDto().nickname()
                : dto.userAccountDto().userId();

        return new ArticleCommentResponse(
                dto.articleCommentId(),
                dto.content(),
                dto.createdAt(),
                dto.userAccountDto().email(),
                nickname
        );
    }

}