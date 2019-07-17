package techcourse.myblog.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import techcourse.myblog.domain.ArticleRepository;
import techcourse.myblog.domain.CategoryRepository;

@Controller
public class MainController {
    private final ArticleRepository articleRepository;
    private final CategoryRepository categoryRepository;

    public MainController(final ArticleRepository articleRepository, final CategoryRepository categoryRepository) {
        this.articleRepository = articleRepository;
        this.categoryRepository = categoryRepository;
    }

    @GetMapping("/")
    public String readHomePage(Model model) {
        model.addAttribute("articles", articleRepository.findAll());

        return "/index";
    }

    @GetMapping("/writing")
    public String readWritingPage(Model model) {
        model.addAttribute("method", "post");
        return "/article-edit";
    }
}
