package anpopo.powerboard.repository;

import anpopo.powerboard.config.JpaConfig;
import anpopo.powerboard.domain.Article;
import anpopo.powerboard.domain.UserAccount;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("testdb")
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DisplayName("jpa 연결 테스트")
@Import(JpaConfig.class)
@DataJpaTest
class JpaRepositoryTest {

    @Autowired private ArticleRepository articleRepository;
    @Autowired private  ArticleCommentRepository articleCommentRepository;



    @DisplayName("select 테스트")
    @Test
    void given_when_then() {
        // given
        // when
        List<Article> articles = articleRepository.findAll();

        // then
        assertThat(articles)
                .isNotNull()
                .hasSize(123);
    }


    @DisplayName("insert 테스트")
    @Test
    void given_whenInserting_then() {
        // given
        long previousCount = articleRepository.count();
        Article newArticle = createArticle();

        // when
        Article savedArticle = articleRepository.save(newArticle);

        // then
        assertThat(articleRepository.count()).isEqualTo(previousCount + 1);
    }


    @DisplayName("update 테스트")
    @Test
    void given_whenUpdating_then() {
        // given
        Article article = articleRepository.findById(1L).orElseThrow();
        String hashtag = "#springboot";
        article.setHashtag(hashtag);

        // when
        Article savedArticle = articleRepository.saveAndFlush(article);

        // then
        assertThat(article.getHashtag()).isEqualTo(hashtag);
    }

    @DisplayName("delete 테스트")
    @Test
    void given_whenDeleting_then() {
        // given
        Article article = articleRepository.findById(1L).orElseThrow();
        long previousCount = articleRepository.count();
        long previousArticleComment = articleCommentRepository.count();
        int commentSize = article.getArticleComments().size();


        // when
        articleRepository.delete(article);

        // then
        assertThat(articleRepository.count()).isEqualTo(previousCount - 1);
        assertThat(articleCommentRepository.count()).isEqualTo(previousArticleComment - commentSize);
    }

    private Article createArticle() {
        return Article.of(null, "new title", "new content", "spring board");
    }

    private UserAccount createUserAccount() {
        return UserAccount.of("anpopo", "1234", "anpopo0108@gamil.com", "anpopo", null);
    }
}
