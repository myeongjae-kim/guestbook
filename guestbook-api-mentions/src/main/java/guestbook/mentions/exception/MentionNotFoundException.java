package guestbook.mentions.exception;

public class MentionNotFoundException extends Exception{
    public MentionNotFoundException(Integer id) {
        super("Test failed: mention id " + id + " is not found");
    }
}
