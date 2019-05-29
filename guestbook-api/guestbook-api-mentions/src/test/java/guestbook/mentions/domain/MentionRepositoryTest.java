package guestbook.mentions.domain;

import static org.assertj.core.api.BDDAssertions.then;

import java.util.List;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

@DataJpaTest
public class MentionRepositoryTest {
    private @Autowired TestEntityManager testEntityManager;
    private @Autowired MentionRepository mentionRepository;

    @Test
    public void findAllMentions_ValidInput_MentionsOrderByCreatedAtDesc() {
        final int sizeOfMentions = 1 << 12;
        IntStream.range(0, sizeOfMentions).forEach((i) ->
                testEntityManager.persist(Mention.builder().name("name").content("content").build()));

        List<Mention> mentions = mentionRepository.findAllByOrderByCreatedAtDesc();

        then(mentions).hasSize(sizeOfMentions);

        for (int i = 1; i < sizeOfMentions; i++) {
            then(mentions.get(i - 1).getCreatedAt()).isAfterOrEqualTo(mentions.get(i).getCreatedAt());
        }
    }
}
