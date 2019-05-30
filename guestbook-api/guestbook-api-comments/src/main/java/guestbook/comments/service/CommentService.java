package guestbook.comments.service;

import guestbook.comments.api.dto.CommentPostRequest;
import guestbook.comments.api.dto.CommentPutRequest;
import guestbook.comments.api.dto.CommentResponse;
import guestbook.comments.domain.Comment;
import guestbook.comments.domain.CommentRepository;
import guestbook.comments.exception.CommentNotFoundException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    public String createComment(CommentPostRequest commentPostRequest) {
        Comment comment = Comment.builder()
                .mentionId(commentPostRequest.getMentionId())
                .name(commentPostRequest.getName())
                .content(commentPostRequest.getContent()).build();
        return commentRepository.save(comment).getId();
    }

    public void updateComment(String id, CommentPutRequest commentPutRequest) {
        Comment comment = findCommentById(id);

        comment.update(commentPutRequest.getContent());
        commentRepository.save(comment);
    }

    public void deleteComment(String id) {
        Comment comment = findCommentById(id);
        comment.delete();
        commentRepository.save(comment);
    }

    public List<CommentResponse> readCommentsOf(Integer mentionId) {
        List<Comment> comments = commentRepository.findAllByMentionIdOrderByCreatedAtAsc(mentionId);
        return comments.stream()
                .filter(c -> !c.isDeleted())
                .map(CommentResponse::of).collect(Collectors.toList());
    }

    private Comment findCommentById(String id) {
        Optional<Comment> comment = commentRepository.findById(id);

        if (!comment.isPresent() || comment.get().isDeleted()) {
            throw new CommentNotFoundException(id);
        }

        return comment.get();
    }
}
