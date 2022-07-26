package anpopo.powerboard.service;

import anpopo.powerboard.domain.Article;
import anpopo.powerboard.domain.ArticleComment;
import anpopo.powerboard.domain.UserAccount;
import anpopo.powerboard.dto.ArticleCommentDto;
import anpopo.powerboard.dto.UserAccountDto;
import anpopo.powerboard.repository.ArticleCommentRepository;
import anpopo.powerboard.repository.ArticleRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Slice;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyLong;

@DisplayName("[비지니스] 게시글의 댓글 테스트")
@ExtendWith(MockitoExtension.class)
class ArticleCommentServiceTest {


    @InjectMocks
    private ArticleCommentService sut;

    @Mock
    private ArticleCommentRepository articleCommentRepository;

    @Mock
    private ArticleRepository articleRepository;


    @DisplayName("[비지니스] 게시글 댓글 조회")
    @Test
    void searchArticleComments() {
        // given
        Long articleId = 1L;
        ArticleComment expected = createArticleComment("오늘은 화요일 너무 좋아!ㅎ");
        BDDMockito.given(articleCommentRepository.findByArticleArticleId(articleId))
                .willReturn(List.of(expected));

        // when
        List<ArticleCommentDto> actual = sut.searchArticleComments(articleId);
        Assertions.assertThat(actual)
                .hasSize(1)
                .first().hasFieldOrPropertyWithValue("content", expected.getContent())
                .isNotNull();

        BDDMockito.then(articleCommentRepository).should().findByArticleArticleId(articleId);
    }

    @DisplayName("[비지니스] 새로운 댓글 생성")
    @Test
    void createArticleComment() {
        // given
        ArticleCommentDto dto = createArticleCommentDto("오늘 하루도 밝은 하루");
        Long articleId = dto.articleId();

        BDDMockito.given(articleRepository.findByArticleId(dto.articleId()))
                .willReturn(Optional.of(createArticle()));

        // when
        ArticleCommentDto actual = sut.saveArticleComment(dto);

        // then
        Assertions.assertThat(actual)
                .hasFieldOrPropertyWithValue("content", dto.content());

        Assertions.assertThat(actual.content())
                .isEqualTo(dto.content());

        BDDMockito.then(articleRepository).should().findByArticleId(articleId);
    }

    @DisplayName("[비지니스] 맞는 게시글이 없는 경우 댓글 저장 하지 않음")
    @Test
    void notFoundArticleNotSaveComment() {
        // given
        // when
        // then
    }

    @DisplayName("[비지니스] 댓글 수정")
    @Test
    void updateArticleComment() {
        // given
        // when
        // then
    }

    @DisplayName("[비지니스] 댓글이 없는 경우 해당 댓글 수정 안함")
    @Test
    void notFoundArticleCommentNotSave() {
        // given
        // when
        // then
    }

    @DisplayName("[비지니스] 게시글 댓글 삭제")
    @Test
    void deleteArticleCommentTest() {
        // given
        Long articleCommentId = 1L;
        BDDMockito.willDoNothing().given(articleCommentRepository).deleteById(articleCommentId);

        // when
        sut.deleteArticleComment(articleCommentId);

        // then
        BDDMockito.then(articleCommentRepository).should().deleteById(articleCommentId);
    }

    private ArticleCommentDto createArticleCommentDto(String content) {
        return ArticleCommentDto.of(
                1L,
                1L,
                createUserAccountDto(),
                content,
                LocalDateTime.now(),
                "anpopo",
                LocalDateTime.now(),
                "anpopo"
        );
    }
    private UserAccountDto createUserAccountDto() {
        return UserAccountDto.of(
                1L,
                "anpopo",
                "1234",
                "anpopo0108@gmail.com",
                "anpopo",
                "this is memo",
                LocalDateTime.now(),
                "anpopo",
                LocalDateTime.now(),
                "anpopo"
        );
    }

    private ArticleComment createArticleComment(String content) {
        return ArticleComment.of(
                createUserAccount(),
                createArticle(),
                content
        );
    }

    private Article createArticle() {
        return Article.of(createUserAccount(), "title", "content", "#hashtag");
    }

    private UserAccount createUserAccount() {
        return UserAccount.of(
                "anpopo",
                "1234",
                "anpopo0108@gmail.com",
                "anpopo",
                null
        );
    }
}