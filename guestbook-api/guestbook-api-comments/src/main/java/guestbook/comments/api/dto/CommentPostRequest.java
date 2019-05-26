package guestbook.comments.api.dto;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentPostRequest {
    private @NotNull Integer mentionId;
    private @NotNull String content;
}
