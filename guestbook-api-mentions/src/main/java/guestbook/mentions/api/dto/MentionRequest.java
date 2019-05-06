package guestbook.mentions.api.dto;

import javax.validation.constraints.NotEmpty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MentionRequest {
    private @NotEmpty String name;
    private @NotEmpty String content;
}
