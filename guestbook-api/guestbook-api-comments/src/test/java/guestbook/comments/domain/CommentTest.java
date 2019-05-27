package guestbook.comments.domain;

import static java.time.LocalDateTime.now;
import static org.assertj.core.api.BDDAssertions.then;

import org.junit.jupiter.api.Test;

public class CommentTest {
    public static Comment getCommentFixture(String id) {
        Comment comment = Comment.builder()
                .mentionId(1)
                .name("name")
                .content("content").build();
        comment.setId(id);
        comment.setCreatedAt(now());

        return comment;
    }

    public static Comment getCommentFixture() {
        return getCommentFixture("comment id");
    }

    @Test
    void buildComment_ValidInput_CreatedComment() {
        Comment comment = Comment.builder()
                .mentionId(1)
                .name("name")
                .content("content").build();

        then(comment)
                .hasFieldOrPropertyWithValue("mentionId", 1)
                .hasFieldOrPropertyWithValue("content", "content");
    }

    @Test
    void update_ValidInput_UpdatedComment() {
        Comment comment = getCommentFixture();
        comment.update("updated content");

        then(comment)
                .hasFieldOrPropertyWithValue("content", "updated content");
    }

    @Test
    void delete_ValidInput_DeletedComment() {
        Comment comment = getCommentFixture();
        comment.delete();

        then(comment).hasFieldOrPropertyWithValue("deleted", true);
        then(comment.getDeletedAt()).isNotNull();
    }
}
