package guestbook.mentions.domain;

import static org.assertj.core.api.BDDAssertions.then;

import org.junit.Test;

public class MentionTest {
    @Test
    public void builder_ValidInput_ValidOutput() {
        Mention mention = Mention.builder().name("name").content("content").build();

        then(mention)
                .hasFieldOrPropertyWithValue("name", "name")
                .hasFieldOrPropertyWithValue("content", "content");
    }
}
