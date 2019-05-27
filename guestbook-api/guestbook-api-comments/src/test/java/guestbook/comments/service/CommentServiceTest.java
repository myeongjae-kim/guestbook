package guestbook.comments.service;

import static guestbook.comments.domain.CommentTest.getCommentFixture;
import static org.assertj.core.api.BDDAssertions.then;
import static org.assertj.core.api.BDDAssertions.thenThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import guestbook.comments.api.dto.CommentPostRequest;
import guestbook.comments.api.dto.CommentPutRequest;
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
    void setUp() {
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
                .hasFieldOrPropertyWithValue("name", comment.getName())
                .hasFieldOrPropertyWithValue("content", comment.getContent());
    }

    @Test
    void readComments_WithMentionId_FoundComments() {
        // given
        List<Comment> comments = Arrays.asList(
                getCommentFixture("comment id 1"),
                getCommentFixture("comment id 2"));
        given(commentRepository.findAllByMentionIdOrderByCreatedAtAsc(anyInt())).willReturn(comments);

        // when
        List<CommentResponse> foundComments = commentService.readCommentsOf(1);

        // then
        then(foundComments).extracting("id")
                .containsAll(Arrays.asList("comment id 1", "comment id 2"));
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
        CommentPostRequest commentPostRequest = new CommentPostRequest();
        commentPostRequest.setMentionId(1);
        commentPostRequest.setContent("content");

        // when
        String id = commentService.createComment(commentPostRequest);

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

        CommentPutRequest commentPutRequest = new CommentPutRequest();
        commentPutRequest.setContent(updatedComment.getContent());

        // when
        commentService.updateComment(id, commentPutRequest);

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
