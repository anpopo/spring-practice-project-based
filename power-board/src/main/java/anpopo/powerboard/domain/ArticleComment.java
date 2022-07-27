package anpopo.powerboard.domain;

import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Objects;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString(callSuper = true)
@Table(indexes = {
        @Index(columnList = "content"),
        @Index(columnList = "createdAt"),
        @Index(columnList = "createdBy"),
})
@EntityListeners(AuditingEntityListener.class)
@Entity
public class ArticleComment extends AuditingFields {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long articleCommentId;

    @Setter
    @ManyToOne(optional = false)
    @JoinColumn(name = "articleId")
    private Article article;

    @Setter
    @Column(length = 500)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    private UserAccount userAccount;

    private ArticleComment(UserAccount userAccount, Article article, String content) {
        this.userAccount = userAccount;
        this.article = article;
        this.content = content;
    }

    public static ArticleComment of(UserAccount userAccount, Article article, String content) {
        return new ArticleComment(userAccount, article, content);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ArticleComment articleComment)) return false;
        return articleCommentId != null && articleCommentId.equals(articleComment.articleCommentId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(articleCommentId);
    }

    public void updateCommentContent(String content) {
        this.content = content;
    }
}
