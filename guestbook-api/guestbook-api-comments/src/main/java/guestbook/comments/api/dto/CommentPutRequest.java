package guestbook.comments.api.dto;

import javax.validation.constraints.NotEmpty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentPutRequest {
    private @NotEmpty String content;
}
