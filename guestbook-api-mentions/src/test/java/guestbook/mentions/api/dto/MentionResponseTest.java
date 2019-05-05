package guestbook.mentions.api.dto;

import static guestbook.mentions.domain.MentionTest.getMentionFixture;
import static java.time.LocalDateTime.now;
import static org.assertj.core.api.BDDAssertions.then;

import guestbook.mentions.domain.Mention;
import org.junit.jupiter.api.Test;

public class MentionResponseTest {
    public static MentionResponse getMentionResponseFixture() {
        MentionResponse mentionResponse = new MentionResponse();
        mentionResponse.setId(1);
        mentionResponse.setName("name");
        mentionResponse.setContent("content");
        mentionResponse.setCreatedAt(now());

        return mentionResponse;
    }

    @Test
    void generateMentionResponseFromMention_ValidInput_ReturnMention() {
        Mention mention = getMentionFixture();
        MentionResponse mentionResponse = MentionResponse.of(mention);

        then(mentionResponse)
                .hasNoNullFieldsOrProperties()
                .hasFieldOrPropertyWithValue("id", mention.getId())
                .hasFieldOrPropertyWithValue("name", mention.getName())
                .hasFieldOrPropertyWithValue("content", mention.getContent())
                .hasFieldOrPropertyWithValue("createdAt", mention.getCreatedAt());
    }
}
