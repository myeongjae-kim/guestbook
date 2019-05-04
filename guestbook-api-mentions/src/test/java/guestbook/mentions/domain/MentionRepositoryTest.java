package guestbook.mentions.domain;

import static org.assertj.core.api.BDDAssertions.then;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataJpaTest
public class MentionRepositoryTest {
    @Autowired
    MentionRepository mentionRepository;

    @Test
    public void saveAndFindMention_ValidInput_SavedAndFoundMention() throws Exception {
        Mention savedMention = mentionRepository.save(Mention.builder().name("name").content("content").build());

        Mention foundMention = mentionRepository.findById(savedMention.getId())
                .orElseThrow(() -> new Exception("Test failed: mention id " + savedMention.getId() + "not found"));

        then(foundMention)
                .hasNoNullFieldsOrProperties()
                .hasFieldOrPropertyWithValue("id", savedMention.getId())
                .hasFieldOrPropertyWithValue("name", "name")
                .hasFieldOrPropertyWithValue("content", "content")
                .hasFieldOrProperty("createdAt");
    }
}
