package anpopo.powerboard.dto;

import org.springframework.util.StringUtils;

import java.io.Serializable;
import java.time.LocalDateTime;

public record ArticleResponse(
        Long articleId,
        String title,
        String content,
        String hashtag,
        LocalDateTime createdAt,
        String email,
        String nickname
) implements Serializable {

    public static ArticleResponse of(Long articleId, String title, String content, String hashtag, LocalDateTime createdAt, String email, String nickname) {
        return new ArticleResponse(articleId, title, content, hashtag, createdAt, email, nickname);
    }

    public static ArticleResponse from(ArticleDto dto) {
        String nickname = StringUtils.hasText(dto.userAccountDto().nickname())
                ? dto.userAccountDto().nickname()
                : dto.userAccountDto().userId();

        return new ArticleResponse(
                dto.articleId(),
                dto.title(),
                dto.content(),
                dto.hashtag(),
                dto.createdAt(),
                dto.userAccountDto().email(),
                nickname
        );
    }

}