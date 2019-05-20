package guestbook.comments.api.dto;

import static guestbook.comments.domain.CommentTest.getCommentFixture;
import static org.assertj.core.api.BDDAssertions.then;

import guestbook.comments.domain.Comment;
import org.junit.jupiter.api.Test;

public class CommentResponseTest {
    public static CommentResponse getCommentResponseFixture(String id) {
        return CommentResponse.of(getCommentFixture(id));
    }

    public static CommentResponse getCommentResponseFixture() {
        return getCommentResponseFixture("comment id");
    }

    @Test
    void construct_ValidInput_ValidOutput() {
        Comment comment = getCommentFixture();
        CommentResponse commentResponse = CommentResponse.of(comment);

        then(commentResponse)
                .hasFieldOrPropertyWithValue("id", comment.getId())
                .hasFieldOrPropertyWithValue("mentionId", comment.getMentionId())
                .hasFieldOrPropertyWithValue("content", comment.getContent())
                .hasFieldOrPropertyWithValue("createdAt", comment.getCreatedAt());
    }
}
