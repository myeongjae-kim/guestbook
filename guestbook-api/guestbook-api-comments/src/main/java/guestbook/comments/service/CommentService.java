package guestbook.comments.service;

import java.util.Optional;

import guestbook.comments.api.dto.CommentRequest;
import guestbook.comments.api.dto.CommentResponse;
import guestbook.comments.domain.Comment;
import guestbook.comments.domain.CommentRepository;
import guestbook.comments.exception.CommentNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentService {
    private CommentRepository commentRepository;

    @Autowired
    public CommentService(CommentRepository commentRepository) {
        this.commentRepository  = commentRepository;
    }

    public CommentResponse readComment(String id) {
        return CommentResponse.of(findCommentById(id));
    }

    public String createComment(CommentRequest commentRequest) {
        Comment comment = Comment.builder()
                .mentionId(commentRequest.getMentionId())
                .content(commentRequest.getContent()).build();
        return commentRepository.save(comment).getId();
    }

    public void updateComment(String id, CommentRequest commentRequest) {
        Comment comment = findCommentById(id);

        comment.update(commentRequest.getContent());
        commentRepository.save(comment);
    }

    public void deleteComment(String id) {
        Comment comment = findCommentById(id);
        comment.delete();
        commentRepository.save(comment);
    }

    private Comment findCommentById(String id) {
        Optional<Comment> comment = commentRepository.findById(id);

        if (!comment.isPresent() || comment.get().isDeleted()) {
            throw new CommentNotFoundException(id);
        }

        return comment.get();
    }
}
