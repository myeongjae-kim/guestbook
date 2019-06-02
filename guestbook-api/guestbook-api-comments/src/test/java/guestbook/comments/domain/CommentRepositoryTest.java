package guestbook.comments.domain;

import static org.assertj.core.api.BDDAssertions.then;
import static org.assertj.core.api.BDDAssertions.thenThrownBy;

import guestbook.comments.config.DynamoDbConfig;
import guestbook.comments.exception.CommentNotFoundException;

import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(value = {SpringExtension.class})
@SpringBootTest(classes = {DynamoDbConfig.class})
class CommentRepositoryTest {
    private @Autowired CommentRepository commentRepository;
    private int uniqueMentionId;

    @BeforeEach
    void createUniqueMentionId() {
        uniqueMentionId = (new Random()).nextInt(1 << 31 - 1);
    }

    @Test
    void createComment_ValidInput_CreatedComment() {
        Comment createdComment = commentRepository.save(Comment.builder()
                .mentionId(uniqueMentionId)
                .name("name")
                .content("content").build());

        then(createdComment)
                .hasNoNullFieldsOrPropertiesExcept("deletedAt")
                .hasFieldOrPropertyWithValue("mentionId", uniqueMentionId)
                .hasFieldOrPropertyWithValue("name", "name")
                .hasFieldOrPropertyWithValue("content", "content");
    }

    @Test
    void findCreatedComment_ById_FoundComment() {
        // given
        Comment createdComment = commentRepository.save(Comment.builder()
                .mentionId(uniqueMentionId)
                .name("name")
                .content("content").build());

        // when
        Comment foundComment = commentRepository.findById(createdComment.getId())
                .orElseThrow(() -> new CommentNotFoundException(createdComment.getId()));

        // then
        then(foundComment)
                .hasNoNullFieldsOrPropertiesExcept("deletedAt")
                .hasFieldOrPropertyWithValue("mentionId", uniqueMentionId)
                .hasFieldOrPropertyWithValue("name", "name")
                .hasFieldOrPropertyWithValue("content", "content");
    }

    @Test
    void findComments_ByMentionIdAndOrderByCreatedAtDescDeletedFalse_FoundCommentsInDesignatedOrder() {
        // given
        int size = 10;
        IntStream.range(0, size).forEach(i -> commentRepository.save(Comment.builder()
                .mentionId(uniqueMentionId)
                .name("name " + i)
                .content("content " + i).build()));

        // when
        List<Comment> foundComment = commentRepository
                .findAllByMentionIdOrderByCreatedAtAsc(uniqueMentionId);

        // then
        then(foundComment.size()).isEqualTo(size);
        IntStream.range(1, size).forEach(i -> {
            Comment prev = foundComment.get(i - 1);
            Comment next = foundComment.get(i);
            then(prev.getCreatedAt().isBefore(next.getCreatedAt())).isTrue();
        });
    }

    @Test
    void updateComment_ValidInput_UpdatedComment() {
        // given
        String id = commentRepository.save(Comment.builder()
                .mentionId(uniqueMentionId)
                .name("name")
                .content("content").build()
        ).getId();

        // when
        Comment foundComment = commentRepository.findById(id)
                .orElseThrow(() -> new CommentNotFoundException(id));
        foundComment.update("updated content");
        commentRepository.save(foundComment);

        // then
        then(foundComment)
                .hasNoNullFieldsOrPropertiesExcept("deletedAt")
                .hasFieldOrPropertyWithValue("mentionId", uniqueMentionId)
                .hasFieldOrPropertyWithValue("name", "name")
                .hasFieldOrPropertyWithValue("content", "updated content");
    }

    @Test
    void deleteCreatedComment_TryToFindDeletedComment_ThrowCommentNotFoundException() {
        // given
        Comment createdComment = commentRepository.save(Comment.builder()
                .name("name")
                .mentionId(uniqueMentionId)
                .content("content").build());

        // when
        commentRepository.delete(createdComment);

        // then
        thenThrownBy(() -> commentRepository.findById(createdComment.getId())
                .orElseThrow(() -> new CommentNotFoundException(createdComment.getId())))
                .isExactlyInstanceOf(CommentNotFoundException.class);
    }
}
