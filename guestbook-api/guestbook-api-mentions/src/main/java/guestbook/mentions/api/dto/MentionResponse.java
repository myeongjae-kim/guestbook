package guestbook.mentions.api.dto;

import java.time.LocalDateTime;

import guestbook.mentions.domain.Mention;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
public class MentionResponse implements Comparable<MentionResponse> {
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

    @Override
    public int compareTo(MentionResponse mentionResponse) {
        return this.createdAt.isAfter(mentionResponse.createdAt) ? -1 : 1; // Order by desc
    }
}
