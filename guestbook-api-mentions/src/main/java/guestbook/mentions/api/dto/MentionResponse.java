package guestbook.mentions.api.dto;

import java.time.LocalDateTime;

import guestbook.mentions.domain.Mention;
import lombok.Data;

@Data
public class MentionResponse {
    private Integer id;
    private String name;
    private String content;
    private LocalDateTime createdAt;

    public static MentionResponse of(Mention mention) {
        MentionResponse mentionResponse = new MentionResponse();
        mentionResponse.setId(mention.getId());
        mentionResponse.setName(mention.getName());
        mentionResponse.setContent(mention.getContent());
        mentionResponse.setCreatedAt(mention.getCreatedAt());

        return mentionResponse;
    }
}
