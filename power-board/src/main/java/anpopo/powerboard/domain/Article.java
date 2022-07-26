package anpopo.powerboard.domain;

import lombok.*;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString(callSuper = true)
@Table(indexes = {
        @Index(columnList = "title"),
        @Index(columnList = "hashtag"),
        @Index(columnList = "createdAt"),
        @Index(columnList = "createdBy"),
})
@Entity
public class Article extends AuditingFields {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long articleId;

    @Setter
    @Column(nullable = false)
    private String title;

    @Setter
    @Column(nullable = false, length = 10000)
    private String content;

    @Setter
    @Column
    private String hashtag;

    @OrderBy(" createdAt desc ")
    @OneToMany(mappedBy = "article", cascade = CascadeType.ALL)
    @ToString.Exclude
    private final Set<ArticleComment> articleComments = new LinkedHashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    private UserAccount userAccount;

    private Article(UserAccount userAccount, String title,String content,String hashtag) {
        this.userAccount = userAccount;
        this.title = title;
        this.content = content;
        this.hashtag = hashtag;
    }

    public static Article of(UserAccount userAccount, String title, String content, String hashtag) {
        return new Article(userAccount, title, content, hashtag);
    }

    /**
     * - 영속화 되지 않은 엔티티는 전부 다른 값으로 본다.
     * - 자바 14부터 도입된 패턴 변수
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Article article)) return false;
        return articleId != null && articleId.equals(article.articleId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(articleId);
    }

    public void updateArticle(String title, String content, String hashtag) {
        this.title = title;
        this.content = content;
        this.hashtag = hashtag;
    }

    public void addArticleComment(ArticleComment articleComment) {
        this.articleComments.add(articleComment);
    }
}
