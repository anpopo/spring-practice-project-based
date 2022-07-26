package anpopo.powerboard.service;

import anpopo.powerboard.domain.Article;
import anpopo.powerboard.domain.ArticleComment;
import anpopo.powerboard.dto.ArticleCommentDto;
import anpopo.powerboard.repository.ArticleCommentRepository;
import anpopo.powerboard.repository.ArticleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class ArticleCommentService {

    private final ArticleRepository articleRepository;
    private final ArticleCommentRepository articleCommentRepository;

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
        Optional<Article> optionalArticle = articleRepository.findByArticleId(dto.articleId());

        if (optionalArticle.isPresent()) {
            Article article = optionalArticle.get();

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

        return null;
    }

    public void deleteArticleComment(Long articleCommentId) {
        articleCommentRepository.deleteById(articleCommentId);
    }
}
