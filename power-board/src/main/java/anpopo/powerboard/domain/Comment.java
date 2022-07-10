package anpopo.powerboard.domain;

import java.time.LocalDateTime;

public class Comment {

    private Long commentId;
    private Article article;
    private String content;

    private LocalDateTime createdAt;
    private String createdBy;
    private LocalDateTime modifiedAt;
    private String modifiedBy;
}
