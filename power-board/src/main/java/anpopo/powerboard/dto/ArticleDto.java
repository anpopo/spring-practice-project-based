package anpopo.powerboard.dto;

import anpopo.powerboard.domain.Article;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

public record ArticleDto(
        Long articleId,
        String title,
        String content,
        String hashtag,
        LocalDateTime modifiedAt,
        String modifiedBy) {

    public static ArticleDto of(Long articleId, String title, String content, String hashtag, LocalDateTime modifiedAt, String modifiedBy) {
        return new ArticleDto(articleId, title, content, hashtag, modifiedAt, modifiedBy);
    }

}
