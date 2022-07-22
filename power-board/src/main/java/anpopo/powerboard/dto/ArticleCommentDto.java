package anpopo.powerboard.dto;

import java.time.LocalDateTime;

public record ArticleCommentDto(
    Long articleCommentId,
    String content,
    LocalDateTime modifiedAt,
    String modifiedBy
) {

    public static ArticleCommentDto of(Long articleCommentId, String content, LocalDateTime modifiedAt, String modifiedBy) {
        return new ArticleCommentDto(articleCommentId, content, modifiedAt, modifiedBy);
    }
}
