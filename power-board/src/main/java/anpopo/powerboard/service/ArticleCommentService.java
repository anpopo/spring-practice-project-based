package anpopo.powerboard.service;

import anpopo.powerboard.domain.Article;
import anpopo.powerboard.domain.ArticleComment;
import anpopo.powerboard.dto.ArticleCommentDto;
import anpopo.powerboard.repository.ArticleCommentRepository;
import anpopo.powerboard.repository.ArticleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class ArticleCommentService {

    private final ArticleRepository articleRepository;
    private final ArticleCommentRepository articleCommentRepository;

    @Transactional(readOnly = true)
    public List<ArticleCommentDto> searchArticleComments(Long articleId) {
        return articleCommentRepository.findByArticleArticleId(articleId)
                .stream().map(articleComment ->
                        ArticleCommentDto.of(
                                articleId,
                                articleComment.getArticleCommentId(),
                                null,
                                articleComment.getContent(),
                                articleComment.getCreatedAt(),
                                articleComment.getCreatedBy(),
                                articleComment.getModifiedAt(),
                                articleComment.getModifiedBy()
                        )
                )
                .collect(Collectors.toList());
    }

    public ArticleCommentDto saveArticleComment(ArticleCommentDto dto) {
        Article article = articleRepository.findByArticleId(dto.articleId())
                .orElseThrow(() -> new EntityNotFoundException("게시글을 찾을 수 없습니다."));


        ArticleComment articleComment = ArticleComment.of(
                null,
                article,
                dto.content()
        );

        article.addArticleComment(articleComment);

        return ArticleCommentDto.of(
                article.getArticleId(),
                articleComment.getArticleCommentId(),
                null,
                articleComment.getContent(),
                articleComment.getCreatedAt(),
                articleComment.getCreatedBy(),
                articleComment.getModifiedAt(),
                articleComment.getModifiedBy()
        );
    }

    public void deleteArticleComment(Long articleCommentId) {
        articleCommentRepository.deleteById(articleCommentId);
    }

    public void updateArticleComment(ArticleCommentDto dto) {
        ArticleComment articleComment = articleCommentRepository.findByArticleCommentId(dto.articleCommentId())
                .orElseThrow(() -> new EntityNotFoundException("댓글을 찾을 수 없습니다."));
        articleComment.updateCommentContent(dto.content());
    }
}
