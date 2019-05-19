package guestbook.comments.domain;

import static java.time.LocalDateTime.now;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Comment {
    private String id;
    private Integer mentionId;
    private String content;
    private boolean deleted;
    private LocalDateTime createdAt;
    private LocalDateTime deletedAt;

    @Builder
    public Comment(Integer mentionId, String content) {
        this.mentionId = mentionId;
        this.content = content;
        this.deleted = false;
    }

    public void update(String content) {
        this.content = content;
    }

    public void delete() {
        this.deleted = true;
        this.deletedAt = now();
    }
}
