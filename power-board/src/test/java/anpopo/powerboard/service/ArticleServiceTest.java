package anpopo.powerboard.service;

import anpopo.powerboard.domain.Article;
import anpopo.powerboard.domain.UserAccount;
import anpopo.powerboard.domain.enumeration.SearchType;
import anpopo.powerboard.dto.ArticleDto;
import anpopo.powerboard.dto.ArticleWithCommentsDto;
import anpopo.powerboard.dto.UserAccountDto;
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
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;

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
        // given
        Pageable pageable = Pageable.ofSize(20);

        BDDMockito.given(articleRepository.findAll(pageable))
                .willReturn(Page.empty());

        // when
        Page<ArticleDto> articles = sut.searchArticles(null, null, pageable);  // 제목 본문 id 닉네임 해시태그

        // then
        Assertions.assertThat(articles)
                .isNotNull()
                .isEmpty();

        BDDMockito.then(articleRepository).should().findAll(pageable);
    }

    @DisplayName("[비지니스] 게시글 검색 키워드 존재")
    @Test
    void searchArticlesByKeyword() {
        // given
        Pageable pageable = Pageable.ofSize(20);
        String searchKeyword = "null";

        BDDMockito.given(articleRepository.findByTitleContaining(anyString(), any(Pageable.class)))
                .willReturn(Page.empty());

        // when
        Page<ArticleDto> articles = sut.searchArticles(SearchType.TITLE, searchKeyword, pageable);  // 제목 본문 id 닉네임 해시태그

        // then
        Assertions.assertThat(articles)
                .isNotNull()
                .isEmpty();

        BDDMockito.then(articleRepository).should().findByTitleContaining(anyString(), any(Pageable.class));
    }

    @DisplayName("[비지니스] 게시글 조회")
    @Test
    void getArticle() {

        // given
        Article article = createArticle();

        BDDMockito.given(articleRepository.findByArticleId(anyLong()))
                .willReturn(Optional.of(article));

        // when
        ArticleWithCommentsDto actual = sut.getArticle(1L);  // 제목 본문 id 닉네임 해시태그

        // then
        Assertions.assertThat(actual).isNotNull();
        Assertions.assertThat(actual)
                .hasFieldOrPropertyWithValue("title", article.getTitle())
                .hasFieldOrPropertyWithValue("content", article.getContent())
                .hasFieldOrPropertyWithValue("hashtag", article.getHashtag());

        BDDMockito.then(articleRepository).should().findByArticleId(anyLong());
    }

    @DisplayName("[비지니스] 존재하지 않는 게시글 조회시 예외 처리")
    @Test
    void notFoundArticle() {

        // when && then
        Assertions.assertThatThrownBy(() -> sut.getArticle(1L))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining("게시글을 찾을 수 없습니다.");

        BDDMockito.then(articleRepository).should().findByArticleId(anyLong());
    }


    @DisplayName("[비지니스] 게시글 생성")
    @Test
    void createArticleTest() {
        // given
        ArticleDto dto = createArticleDto();
        Article article = createArticle();

        BDDMockito.given(articleRepository.save(any(Article.class))).willReturn(article);

        // when
        ArticleDto articleDto = sut.saveArticle(dto);

        // then
        BDDMockito.then(articleRepository).should().save(any(Article.class));

        Assertions.assertThat(articleDto)
                .hasFieldOrPropertyWithValue("title", article.getTitle())
                .hasFieldOrPropertyWithValue("content", article.getContent())
                .hasFieldOrPropertyWithValue("hashtag", article.getHashtag());
    }

    @DisplayName("[비지니스] 게시글 수정")
    @Test
    void updateArticle() {
        // given
        ArticleDto dto = createUpdateArticleDto();
        Article article = createArticle();

        BDDMockito.given(articleRepository.getReferenceById(anyLong())).willReturn(article);

        // when
        Article updatedArticle = sut.updateArticle(dto);

        // then
        Assertions.assertThat(updatedArticle.getTitle()).isEqualTo(dto.title());
        BDDMockito.then(articleRepository).should().getReferenceById(anyLong());
    }

    @DisplayName("[비지니스] 없는 게시글 수정 요청 시 예외 처리")
    @Test
    void articleNotFoundNotUpdate() {
        // given
        ArticleDto dto = createUpdateArticleDto();
        BDDMockito.given(articleRepository.getReferenceById(anyLong()))
                .willThrow(EntityNotFoundException.class);

        // when &&  then
        Assertions.assertThatThrownBy(() -> sut.updateArticle(dto))
                .isInstanceOf(EntityNotFoundException.class);

        BDDMockito.then(articleRepository).should().getReferenceById(anyLong());
    }

    @DisplayName("[비지니스] 게시글 삭제")
    @Test
    void deleteArticle() {
        // given
        BDDMockito.willDoNothing().given(articleRepository).deleteById(anyLong());

        // when
        sut.deleteArticle(1L);

        // then
        BDDMockito.then(articleRepository).should().deleteById(anyLong());
    }

    private ArticleDto createArticleDto() {
        return ArticleDto.of(null,  createUserAccountDto(),"title", "content", "#hashtag",  LocalDateTime.now(), "anpopo",  LocalDateTime.now(), "anpopo");
    }

    private ArticleDto createUpdateArticleDto() {
         return ArticleDto.of(1L, createUserAccountDto(), "title", "content", "#hashtag",  LocalDateTime.now(), "anpopo", LocalDateTime.now(), "anpopo");
    }
    private Article createArticle() {
        return Article.of(createUserAccount(), "new title", "new content", "spring board");
    }

    private UserAccount createUserAccount() {
        return UserAccount.of("anpopo", "1234", "anpopo0108@gamil.com", "anpopo", null);
    }

    private UserAccountDto createUserAccountDto() {
        return UserAccountDto.of(
                1L,
                "anpopo",
                "1234",
                "anpopo@gmail.com",
                "anpopo",
                "This is memo",
                LocalDateTime.now(),
                "anpopo",
                LocalDateTime.now(),
                "anpopo"
        );
    }


}