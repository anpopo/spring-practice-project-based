package anpopo.powerboard.service;

import anpopo.powerboard.domain.Article;
import anpopo.powerboard.domain.enumeration.SearchType;
import anpopo.powerboard.dto.ArticleDto;
import anpopo.powerboard.repository.ArticleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class ArticleService {

    private final ArticleRepository articleRepository;

    @Transactional(readOnly = true)
    public Page<ArticleDto> searchArticles(SearchType title, String keyword) {
        return Page.empty();
    }

    public ArticleDto getArticle(Long articleId) {
        return null;
    }

    public void saveArticle(ArticleDto dto) {


    }

    public Article updateArticle(ArticleDto dto) {

        Optional<Article> optionalArticle = articleRepository.findById(dto.articleId());

        if (optionalArticle.isPresent()) {
            Article article = optionalArticle.get();
            article.updateArticle(dto.title(), dto.content(), dto.hashtag());
            return article;
        }

        return null;
    }

    public void deleteArticle(Long articleId) {
        articleRepository.findById(articleId)
                .ifPresent(articleRepository::delete);
    }
}
