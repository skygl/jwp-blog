package techcourse.myblog.presentation.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
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
    private CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/articles/{articleId}/comments")
    public RedirectView addComment(CommentDto commentDto, @PathVariable Long articleId, HttpSession session) {
        commentService.save(commentDto, articleId, (String) session.getAttribute("email"));
        RedirectView redirectView = new RedirectView("/articles/" + articleId);
        return redirectView;
    }

    @DeleteMapping("/articles/{articleId}/comments/{commentId}")
    public ModelAndView deleteComment(@PathVariable Long commentId, @PathVariable Long articleId, HttpSession session) {
        commentService.delete(commentId, (String) session.getAttribute("email"));
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setView(new RedirectView("/articles/" + articleId));
        return modelAndView;
    }

    @PutMapping("/articles/{articleId}/comments/{commentId}")
    public RedirectView updateComment(CommentDto commentDto, @PathVariable("articleId") Long articleId, @PathVariable("commentId") Long commentId, HttpSession session) {
        commentService.modify(commentId, commentDto, (String) session.getAttribute("email"));
        return new RedirectView("/articles/" + articleId);
    }
}