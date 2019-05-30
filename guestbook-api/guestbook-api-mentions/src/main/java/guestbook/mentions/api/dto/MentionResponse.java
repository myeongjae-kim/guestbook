package guestbook.mentions.api.dto;

import guestbook.mentions.domain.Mention;

import java.time.LocalDateTime;

import lombok.Getter;

@Getter
public class MentionResponse {
    private Integer id;
    private String name;
    private String content;
    private LocalDateTime createdAt;

    private MentionResponse(Mention mention) {
        this.id = mention.getId();
        this.name = mention.getName();
        this.content = mention.getContent();
        this.createdAt = mention.getCreatedAt();
    }

    public static MentionResponse of(Mention mention) {
        return new MentionResponse(mention);
    }
}
