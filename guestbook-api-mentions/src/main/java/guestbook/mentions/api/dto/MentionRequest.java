package guestbook.mentions.api.dto;

import lombok.Data;

@Data
public class MentionRequest {
    private String name;
    private String content;
}
