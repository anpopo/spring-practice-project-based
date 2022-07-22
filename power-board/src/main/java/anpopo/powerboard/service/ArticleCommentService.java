package anpopo.powerboard.service;

import anpopo.powerboard.dto.ArticleCommentDto;
import anpopo.powerboard.repository.ArticleCommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ArticleCommentService {

    private final ArticleCommentRepository articleCommentRepository;

    public Slice<ArticleCommentDto> searchArticleComments(Long articleId) {
        return Page.empty();
    }
}
