package anpopo.powerboard.dto;

import anpopo.powerboard.domain.Article;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

public record ArticleDto(
        Long articleId,
        UserAccountDto userAccountDto,
        String title,
        String content,
        String hashtag,
        LocalDateTime createdAt,
        String createdBy,
        LocalDateTime modifiedAt,
        String modifiedBy) {

    public static ArticleDto of(Long articleId, UserAccountDto userAccountDto, String title, String content, String hashtag,
                                LocalDateTime createdAt, String createdBy, LocalDateTime modifiedAt, String modifiedBy) {
        return new ArticleDto(articleId, userAccountDto, title, content, hashtag, createdAt, createdBy, modifiedAt, modifiedBy);
    }

    public static ArticleDto from (Article article) {
        return new ArticleDto(
                article.getArticleId(),
                UserAccountDto.from(article.getUserAccount()),
                article.getTitle(),
                article.getContent(),
                article.getHashtag(),
                article.getCreatedAt(),
                article.getCreatedBy(),
                article.getModifiedAt(),
                article.getModifiedBy()
        );
    }

    public Article toEntity() {
        return Article.of(
                userAccountDto.toEntity(),
                title,
                content,
                hashtag
        );
    }

}
