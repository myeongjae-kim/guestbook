package guestbook.comments.api.dto;

import guestbook.comments.domain.Comment;

import java.time.LocalDateTime;

import lombok.Getter;

@Getter
public class CommentResponse {
    private String id;
    private Integer mentionId;
    private String name;
    private String content;
    private LocalDateTime createdAt;

    private CommentResponse(Comment comment) {
        this.id = comment.getId();
        this.mentionId = comment.getMentionId();
        this.name = comment.getName();
        this.content = comment.getContent();
        this.createdAt = comment.getCreatedAt();
    }

    public static CommentResponse of(Comment comment) {
        return new CommentResponse(comment);
    }
}
