package techcourse.myblog.application.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import techcourse.myblog.application.converter.ArticleConverter;
import techcourse.myblog.application.converter.CommentConverter;
import techcourse.myblog.application.converter.UserConverter;
import techcourse.myblog.application.dto.CommentDto;
import techcourse.myblog.application.service.exception.CommentNotFoundException;
import techcourse.myblog.domain.Article;
import techcourse.myblog.domain.Comment;
import techcourse.myblog.domain.CommentRepository;
import techcourse.myblog.domain.User;

import java.util.List;

@Service
public class CommentService {
    private static final Logger log = LoggerFactory.getLogger(CommentService.class);

    private static ArticleConverter articleConverter = ArticleConverter.getInstance();
    private static CommentConverter commentConverter = CommentConverter.getInstance();
    private static UserConverter userConverter = UserConverter.getInstance();

    private CommentRepository commentRepository;
    private UserService userService;
    private ArticleService articleService;

    @Autowired
    public CommentService(CommentRepository commentRepository, UserService userService, ArticleService articleService) {
        this.commentRepository = commentRepository;
        this.userService = userService;
        this.articleService = articleService;
    }

    @Transactional
    public void save(CommentDto commentDto, Long articleId, String sessionEmail) {
        Article article = articleService.findById(articleId);
        User user = userService.findUserByEmail(sessionEmail);
        commentDto.setArticle(article);
        commentDto.setAuthor(user);

        commentRepository.save(commentConverter.convertFromDto(commentDto));
    }

    public List<CommentDto> findAllCommentsByArticleId(Long articleId, String sessionEmail) {
        Article article = articleService.findById(articleId);
        List<Comment> comments = commentRepository.findByArticle(article);
        List<CommentDto> commentDtos = commentConverter.createFromEntities(comments);
        for (CommentDto commentDto : commentDtos) {
            commentDto.matchAuthor(sessionEmail);
        }
        return commentDtos;
    }

    public void delete(long commentId) {
        commentRepository.deleteById(commentId);
    }

    @Transactional
    public void modify(Long commentId, CommentDto commentDto) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new CommentNotFoundException("댓글이 존재하지 않습니다"));
        comment.changeContent(commentDto);
    }
}
