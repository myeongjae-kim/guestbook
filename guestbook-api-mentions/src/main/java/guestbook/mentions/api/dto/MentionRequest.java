package guestbook.mentions.api.dto;

import javax.validation.constraints.NotEmpty;

import lombok.Data;

@Data
public class MentionRequest {
    private @NotEmpty String name;
    private @NotEmpty String content;
}
