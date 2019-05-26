package guestbook.comments.api.dto;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentPutRequest {
    private @NotNull String content;
}
