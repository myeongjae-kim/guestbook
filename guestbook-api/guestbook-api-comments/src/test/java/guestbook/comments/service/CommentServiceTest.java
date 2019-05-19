package guestbook.comments.service;

import static guestbook.comments.domain.CommentTest.getCommentFixture;
import static org.assertj.core.api.BDDAssertions.then;
import static org.assertj.core.api.BDDAssertions.thenThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

import java.util.Optional;

import guestbook.comments.api.dto.CommentRequest;
import guestbook.comments.api.dto.CommentResponse;
import guestbook.comments.domain.Comment;
import guestbook.comments.domain.CommentRepository;
import guestbook.comments.exception.CommentNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CommentServiceTest {
    private CommentService commentService;
    private @Mock CommentRepository commentRepository;

    @BeforeEach
    public void setUp() {
        this.commentService = new CommentService(commentRepository);
    }

    @Test
    void readComment_ValidInput_ValidOutput() {
        // given
        Comment comment = getCommentFixture();
        given(commentRepository.findById(anyString())).willReturn(Optional.of(comment));

        // when
        CommentResponse commentResponse = commentService.readComment(comment.getId());

        // then
        then(commentResponse)
                .hasNoNullFieldsOrProperties()
                .hasFieldOrPropertyWithValue("id", comment.getId())
                .hasFieldOrPropertyWithValue("mentionId", comment.getMentionId())
                .hasFieldOrPropertyWithValue("content", comment.getContent());
    }

    @Test
    void readComment_InvalidId_ThrowCommentNotFoundException() {
        given(commentRepository.findById(anyString())).willReturn(Optional.empty());

        thenThrownBy(() -> commentService.readComment("invalid id"))
                .isExactlyInstanceOf(CommentNotFoundException.class);
    }

    @Test
    void createComment_ValidInput_ValidOutput() {
        // given
        Comment comment = getCommentFixture();
        given(commentRepository.save(any(Comment.class))).willReturn(comment);
        CommentRequest commentRequest = new CommentRequest();
        commentRequest.setMentionId(1);
        commentRequest.setContent("content");

        // when
        String id = commentService.createComment(commentRequest);

        // then
        then(id).isNotEmpty();
    }

    @Test
    void updateComment_ValidInput_ValidOutput() {
        // given
        Comment updatedComment = getCommentFixture();
        String id = updatedComment.getId();
        updatedComment.update("new content");
        given(commentRepository.findById(anyString())).willReturn(Optional.of(updatedComment));
        given(commentRepository.save(any(Comment.class))).willReturn(updatedComment);

        CommentRequest commentRequest = new CommentRequest();
        commentRequest.setMentionId(1);
        commentRequest.setContent(updatedComment.getContent());

        // when
        commentService.updateComment(id, commentRequest);

        // then
        CommentResponse commentResponse = commentService.readComment(id);
        then(commentResponse)
                .hasFieldOrPropertyWithValue("content", "new content")
                .hasNoNullFieldsOrProperties();
    }

    @Test
    void deleteComment_FindAfterDeleted_ThrowCommentNotFoundException() {
        // given
        Comment comment = getCommentFixture();
        given(commentRepository.findById(anyString())).willReturn(Optional.of(comment));

        // when
        commentService.deleteComment(comment.getId());
        // then no exception

        // given
        comment.delete();
        given(commentRepository.findById(anyString())).willReturn(Optional.of(comment));

        thenThrownBy(() -> commentService.readComment(comment.getId()))
                .isExactlyInstanceOf(CommentNotFoundException.class);
    }
}
