package guestbook.mentions.api.dto;

import java.time.LocalDateTime;

import guestbook.mentions.domain.Mention;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MentionResponse {
    private Integer id;
    private String name;
    private String content;
    private LocalDateTime createdAt;

    public static MentionResponse of(Mention mention) {
        return new MentionResponse(
                mention.getId(),
                mention.getName(),
                mention.getContent(),
                mention.getCreatedAt());
    }
}
