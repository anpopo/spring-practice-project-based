package anpopo.powerboard.service;

import anpopo.powerboard.domain.Article;
import anpopo.powerboard.domain.enumeration.SearchType;
import anpopo.powerboard.dto.ArticleDto;
import anpopo.powerboard.repository.ArticleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class ArticleService {

    private final ArticleRepository articleRepository;

    // TODO 검색에 대한 기능 추가 필요
    @Transactional(readOnly = true)
    public Page<ArticleDto> searchArticles(SearchType title, String keyword, Pageable pageable) {
        Page<Article> pagedArticle = articleRepository.findAll(pageable);


        return Page.empty();
    }

    public ArticleDto getArticle(Long articleId) {
        Article article = articleRepository.findByArticleId(articleId)
                .orElseThrow(() -> new EntityNotFoundException("게시글을 찾을 수 없습니다."));

        return ArticleDto.of(
                article.getArticleId(),
                article.getTitle(),
                article.getContent(),
                article.getHashtag(),
                article.getModifiedAt(),
                article.getModifiedBy()
        );
    }

    public ArticleDto saveArticle(ArticleDto dto) {
        // TODO UserAccount 등록
        Article savedArticle = articleRepository.save(Article.of(null, dto.title(), dto.content(), dto.hashtag()));

        return ArticleDto.of(
                savedArticle.getArticleId(),
                savedArticle.getTitle(),
                savedArticle.getContent(),
                savedArticle.getHashtag(),
                savedArticle.getModifiedAt(),
                savedArticle.getModifiedBy()
        );
    }

    public Article updateArticle(ArticleDto dto) {
        Article article = articleRepository.findByArticleId(dto.articleId())
                .orElseThrow(() -> new EntityNotFoundException("게시글을 찾을 수 없습니다."));

        article.updateArticle(dto.title(), dto.content(), dto.hashtag());
        return article;
    }

    public void deleteArticle(Long articleId) {
        if (articleId == null) return ;
        articleRepository.deleteById(articleId);
    }
}
