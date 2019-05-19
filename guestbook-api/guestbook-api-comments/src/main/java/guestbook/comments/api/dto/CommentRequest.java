package guestbook.comments.api.dto;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentRequest {
    private @NotNull Integer mentionId;
    private @NotNull String content;
}
