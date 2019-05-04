package guestbook.mentions.domain;

import static org.assertj.core.api.BDDAssertions.then;

import org.junit.Test;

public class MentionTest {
    @Test
    public void builder_ValidInput_ValidOutput() {
        Mention.MentionBuilder mentionBuilder = Mention.builder().name("name").content("content");
        then(mentionBuilder).hasToString("Mention.MentionBuilder(name=name, content=content)");

        Mention mention = mentionBuilder.build();
        then(mention)
                .hasFieldOrPropertyWithValue("name", "name")
                .hasFieldOrPropertyWithValue("content", "content");
    }
}
