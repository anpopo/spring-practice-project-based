package anpopo.powerboard.service;

import anpopo.powerboard.domain.Article;
import anpopo.powerboard.domain.enumeration.SearchType;
import anpopo.powerboard.dto.ArticleDto;
import anpopo.powerboard.repository.ArticleRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;

@DisplayName("[비지니스] 게시글 테스트")
@ExtendWith(MockitoExtension.class)
class ArticleServiceTest {

    @InjectMocks
    private ArticleService sut;

    @Mock
    private ArticleRepository articleRepository;

    /**
     * 검색, 게시글 페이지 이동, 페이징, 홈버튼 게시판 리다이렉션, 정렬
     */

    @DisplayName("[비지니스] 게시글 검색")
    @Test
    void searchArticles() {
        // when
        Page<ArticleDto> articles = sut.searchArticles(SearchType.TITLE , "keyword");  // 제목 본문 id 닉네임 해시태그

        // then
        Assertions.assertThat(articles).isNotNull();
    }

    @DisplayName("[비지니스] 게시글 조회")
    @Test
    void getArticle() {
        // when
        ArticleDto article = sut.getArticle(1L);  // 제목 본문 id 닉네임 해시태그

        // then
        Assertions.assertThat(article).isNotNull();
    }


    @DisplayName("[비지니스] 게시글 생성")
    @Test
    void createArticle() {
        // given
        ArticleDto dto = ArticleDto.of(null, "title", "content", "hashtag", LocalDateTime.now(), "anpopo");
        Article article = Article.of("title", "content", "hashtag");


        BDDMockito.given(articleRepository.save(any(Article.class)))
                .willReturn(article);

        // when
        sut.saveArticle(dto);

        // then
        Article savedArticle = BDDMockito.then(articleRepository).should().save(any(Article.class));

        Assertions.assertThat(savedArticle.getTitle()).isEqualTo(article.getTitle());

    }

    @DisplayName("[비지니스] 게시글 수정")
    @Test
    void updateArticle() {
        // given
        ArticleDto dto = ArticleDto.of(null, "title1", "content1", "hashtag1", LocalDateTime.now(), "anpopo");
        Article article = Article.of("title", "content", "hashtag");


        BDDMockito.given(articleRepository.findById(anyLong())).willReturn(Optional.of(article));

        // when
        Article updatedArticle = sut.updateArticle(dto);

        // then

        Assertions.assertThat(updatedArticle.getTitle()).isEqualTo(dto.title());
    }

    @DisplayName("[비지니스] 게시글 삭제")
    @Test
    void deleteArticle() {
        // given
        ArticleDto dto = ArticleDto.of(null, "title1", "content1", "hashtag1", LocalDateTime.now(), "anpopo");
        Article article = Article.of("title", "content", "hashtag");


        BDDMockito.willDoNothing().given(articleRepository).delete(any(Article.class));
        // when
        sut.deleteArticle(1L);

        // then
        BDDMockito.then(articleRepository).should().delete(any(Article.class));
    }


}