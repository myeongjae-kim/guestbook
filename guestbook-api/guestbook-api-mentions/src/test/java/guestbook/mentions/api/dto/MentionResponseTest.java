package guestbook.mentions.api.dto;

import static guestbook.mentions.domain.MentionTest.getMentionFixture;
import static org.assertj.core.api.BDDAssertions.then;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import guestbook.mentions.domain.Mention;
import org.junit.jupiter.api.Test;

public class MentionResponseTest {
    public static MentionResponse getMentionResponseFixture(Integer id) {
        return MentionResponse.of(getMentionFixture(id));
    }

    public static MentionResponse getMentionResponseFixture() {
        return MentionResponse.of(getMentionFixture(1));
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

    @Test
    void sortMentions_ValidInput_SortedMentionsOrderedByCreatedAtDesc() {
        List<MentionResponse> mentionResponses = Arrays.asList(
                getMentionResponseFixture(1),
                getMentionResponseFixture(2)
        );
        Collections.sort(mentionResponses);

        then(mentionResponses.get(0).getCreatedAt()).isAfter(mentionResponses.get(1).getCreatedAt());
    }
}
