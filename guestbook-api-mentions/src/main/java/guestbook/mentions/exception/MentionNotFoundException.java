package guestbook.mentions.exception;

public class MentionNotFoundException extends RuntimeException {
    public MentionNotFoundException(Integer id) {
        super("mention id " + id + " has not been found");
    }
}
