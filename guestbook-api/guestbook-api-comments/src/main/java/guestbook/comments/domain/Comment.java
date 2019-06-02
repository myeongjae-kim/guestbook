package guestbook.comments.domain;

import static java.time.LocalDateTime.now;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter // Setters are used in aws-java-dynamodb-sdk
@NoArgsConstructor
public class Comment {
    private String id;
    private Integer mentionId;
    private String name;
    private String content;
    private boolean deleted;
    private LocalDateTime createdAt;
    private LocalDateTime deletedAt;

    @Builder
    public Comment(Integer mentionId, String name, String content) {
        this.mentionId = mentionId;
        this.name = name;
        this.content = content;
        this.createdAt = now();
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
