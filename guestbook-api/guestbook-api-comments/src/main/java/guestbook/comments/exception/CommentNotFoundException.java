package guestbook.comments.exception;

public class CommentNotFoundException extends RuntimeException {
    public CommentNotFoundException(String id) {
        super("comment id " + id + " has not been found");
    }
}
