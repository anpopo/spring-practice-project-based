package anpopo.powerboard.service;

import anpopo.powerboard.domain.Article;
import anpopo.powerboard.dto.ArticleCommentDto;
import anpopo.powerboard.repository.ArticleCommentRepository;
import anpopo.powerboard.repository.ArticleRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Slice;

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
        Article article = Article.of("title", "content", "hashtag");

        BDDMockito.given(articleRepository.findById(anyLong())).willReturn(Optional.of(article));

        // when
        Slice<ArticleCommentDto> articleComments = sut.searchArticleComments(1L);
        Assertions.assertThat(articleComments).isNotNull();
    }
}