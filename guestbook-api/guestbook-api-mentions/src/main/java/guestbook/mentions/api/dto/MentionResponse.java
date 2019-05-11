package guestbook.mentions.api.dto;

import java.time.LocalDateTime;

import guestbook.mentions.domain.Mention;
import lombok.Getter;

@Getter
public class MentionResponse {
    private Integer id;
    private String name;
    private String content;
    private LocalDateTime createdAt;

    private MentionResponse() {}

    public static MentionResponse of(Mention mention) {
        MentionResponse mentionResponse = new MentionResponse();
        mentionResponse.id = mention.getId();
        mentionResponse.name = mention.getName();
        mentionResponse.content = mention.getContent();
        mentionResponse.createdAt = mention.getCreatedAt();

        return mentionResponse;
    }

    public static Integer orderByCreatedAtDesc (MentionResponse a, MentionResponse b) {
        return b.createdAt.isAfter(a.createdAt) ? 1 : -1;
    }
}
