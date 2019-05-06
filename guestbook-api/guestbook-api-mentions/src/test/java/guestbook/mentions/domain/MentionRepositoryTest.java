package guestbook.mentions.domain;

import static org.assertj.core.api.BDDAssertions.then;

import guestbook.mentions.exception.MentionNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class MentionRepositoryTest {
    @Autowired MentionRepository mentionRepository;

    @Test
    public void saveAndFindMention_ValidInput_SavedAndFoundMention() throws Exception {
        Mention savedMention = mentionRepository.save(Mention.builder().name("name").content("content").build());

        Mention foundMention = mentionRepository.findById(savedMention.getId())
                .orElseThrow(() -> new MentionNotFoundException(savedMention.getId()));

        then(foundMention)
                .hasNoNullFieldsOrProperties()
                .hasFieldOrPropertyWithValue("id", savedMention.getId())
                .hasFieldOrPropertyWithValue("name", "name")
                .hasFieldOrPropertyWithValue("content", "content")
                .hasFieldOrProperty("createdAt");
    }
}
