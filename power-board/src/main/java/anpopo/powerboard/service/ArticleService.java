package anpopo.powerboard.service;

import anpopo.powerboard.domain.Article;
import anpopo.powerboard.domain.enumeration.SearchType;
import anpopo.powerboard.dto.ArticleDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ArticleService {

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
        return null;
    }

    public void deleteArticle(Long articleId) {

    }
}
