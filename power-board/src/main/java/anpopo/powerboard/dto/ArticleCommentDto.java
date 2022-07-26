package anpopo.powerboard.dto;

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
}
