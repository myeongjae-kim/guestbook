package guestbook.mentions.domain;

import static java.time.LocalDateTime.now;
import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.BDDMockito.given;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class MentionTest {
    /**
     * getMentionFixture returns a default Mention object generated by Mockito.
     * @return an instance of Mention class.
     */
    public static Mention getMentionFixture(Integer id) {
        Mention mention = Mockito.mock(Mention.class);
        given(mention.getId()).willReturn(id);
        given(mention.getName()).willReturn("name");
        given(mention.getContent()).willReturn("content");
        given(mention.getCreatedAt()).willReturn(now());

        return mention;
    }

    public static Mention getMentionFixture() {
        return getMentionFixture(1);
    }

    @Test
    void builder_ValidInput_ValidOutput() {
        Mention mention = Mention.builder().name("name").content("content").build();
        then(mention)
                .hasFieldOrPropertyWithValue("name", "name")
                .hasFieldOrPropertyWithValue("content", "content");
    }

    @Test
    void updateMention_ValidInput_MentionUpdated() {
        Mention mention = Mention.builder().name("name").content("content").build();
        mention.update("updated name", "updated content");

        then(mention)
                .hasFieldOrPropertyWithValue("name", "updated name")
                .hasFieldOrPropertyWithValue("content", "updated content");
    }
}
