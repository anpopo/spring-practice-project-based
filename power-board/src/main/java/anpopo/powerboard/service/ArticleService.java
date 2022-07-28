package anpopo.powerboard.service;

import anpopo.powerboard.domain.Article;
import anpopo.powerboard.domain.enumeration.SearchType;
import anpopo.powerboard.dto.ArticleDto;
import anpopo.powerboard.dto.ArticleWithCommentsDto;
import anpopo.powerboard.repository.ArticleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.persistence.EntityNotFoundException;

@Service
@Transactional
@RequiredArgsConstructor
public class ArticleService {

    private final ArticleRepository articleRepository;

    @Transactional(readOnly = true)
    public Page<ArticleDto> searchArticles(SearchType searchType, String keyword, Pageable pageable) {
        if (!StringUtils.hasText(keyword)) {
            return articleRepository.findAll(pageable).map(ArticleDto::from);
        }

        return switch (searchType) {
            case TITLE -> articleRepository.findByTitleContaining(keyword, pageable).map(ArticleDto::from);
            case ID -> articleRepository.findByUserAccount_UserIdContaining(keyword,  pageable).map(ArticleDto::from);
            case CONTENT -> articleRepository.findByContentContaining(keyword, pageable).map(ArticleDto::from);
            case HASHTAG -> articleRepository.findByHashtag(keyword, pageable).map(ArticleDto::from);
            case NICKNAME -> articleRepository.findByUserAccount_NicknameContaining(String.format("#%s", keyword), pageable).map(ArticleDto::from);
        };
    }

    public ArticleWithCommentsDto getArticle(Long articleId) {
        return articleRepository.findByArticleId(articleId)
                .map(ArticleWithCommentsDto::from)
                .orElseThrow(() -> new EntityNotFoundException("게시글을 찾을 수 없습니다."));

    }

    public ArticleDto saveArticle(ArticleDto dto) {
        Article savedArticle = articleRepository.save(dto.toEntity());
        return ArticleDto.from(savedArticle);
    }

    public Article updateArticle(ArticleDto dto) {
        Article article = articleRepository.getReferenceById(dto.articleId());
        article.updateArticle(dto.title(), dto.content(), dto.hashtag());

        return article;
    }

    public void deleteArticle(Long articleId) {
        if (articleId == null) return ;
        articleRepository.deleteById(articleId);
    }
}
