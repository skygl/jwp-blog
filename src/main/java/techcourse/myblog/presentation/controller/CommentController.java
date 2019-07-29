package techcourse.myblog.presentation.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;
import techcourse.myblog.application.dto.CommentDto;
import techcourse.myblog.application.service.CommentService;

import javax.servlet.http.HttpSession;

@Controller
public class CommentController {
    private static final Logger log = LoggerFactory.getLogger(CommentController.class);
    private CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/articles/{articleId}/comments")
    public RedirectView addComment(CommentDto commentDto, @PathVariable Long articleId, HttpSession session) {
        commentService.save(commentDto, articleId, session);
        RedirectView redirectView = new RedirectView("/articles/" + articleId);
        return redirectView;
    }

    @PutMapping("/articles/{articleId}/comments/{commentId}")
    public RedirectView updateComment(CommentDto commentDto, @PathVariable("articleId") Long articleId, @PathVariable("commentId") Long commentid) {
        commentService.modify(commentid, commentDto);
        return new RedirectView("/articles/" + articleId);
    }
}
