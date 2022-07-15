package anpopo.powerboard.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Collections;

@RequestMapping("/articles")
@Controller
public class ArticleController {

    @GetMapping
    public String articles(Model model) {
        model.addAttribute("articles", Collections.emptyList());
        return "articles/index";
    }

    @GetMapping("/{articleId}")
    public String articleDetail(@PathVariable("articleId") Long articleId, Model model) {
        model.addAttribute("article", new Object());
        model.addAttribute("articleComments", Collections.emptyList());
        return "articles/detail";
    }

    @GetMapping("/search")
    public String articlesSearch(Model model) {
        model.addAttribute("article", new Object());
        model.addAttribute("articleComments", Collections.emptyList());
        return "articles/search";
    }

    @GetMapping("/search-hashtag")
    public String articlesSearchHashtag(Model model) {
        return "articles/search-hashtag";
    }
}
