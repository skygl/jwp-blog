package techcourse.myblog.application.service.exception;

public class NotExistCommentIdException extends RuntimeException {
    public NotExistCommentIdException(String message) {
        super(message);
    }
}
