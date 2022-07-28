package anpopo.powerboard.dto;

import anpopo.powerboard.domain.Article;
import anpopo.powerboard.domain.ArticleComment;

import java.time.LocalDateTime;

public record ArticleCommentDto(
        Long articleId,
        Long articleCommentId,
        UserAccountDto userAccountDto,
        String content,
        LocalDateTime createdAt,
        String createdBy,
        LocalDateTime modifiedAt,
        String modifiedBy
) {

    public static ArticleCommentDto of(Long articleId, Long articleCommentId, UserAccountDto userAccountDto, String content, LocalDateTime createdAt, String createdBy, LocalDateTime modifiedAt, String modifiedBy) {
        return new ArticleCommentDto(articleId, articleCommentId, userAccountDto, content, createdAt, createdBy, modifiedAt, modifiedBy);
    }

    public static ArticleCommentDto from(ArticleComment articleComment) {
        return new ArticleCommentDto(
                articleComment.getArticleCommentId(),
                articleComment.getArticle().getArticleId(),
                UserAccountDto.from(articleComment.getUserAccount()),
                articleComment.getContent(),
                articleComment.getCreatedAt(),
                articleComment.getCreatedBy(),
                articleComment.getModifiedAt(),
                articleComment.getModifiedBy()
        );
    }

    public ArticleComment toEntity(Article entity) {
        return ArticleComment.of(
                userAccountDto.toEntity(),
                entity,
                content
        );
    }
}
