package guestbook.mentions.service;

import static guestbook.mentions.domain.MentionTest.getMentionFixture;
import static org.assertj.core.api.BDDAssertions.then;
import static org.assertj.core.api.BDDAssertions.thenThrownBy;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;

import java.util.Optional;

import guestbook.mentions.domain.Mention;
import guestbook.mentions.domain.MentionRepository;
import guestbook.mentions.exception.MentionNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class MentionServiceTest {
    private MentionService mentionService;
    private @Mock MentionRepository mentionRepository;

    @BeforeEach
    void setup() {
        mentionService = new MentionService((mentionRepository));
    }

    @Test
    void getMention_ValidInput_MentionCreated() throws Exception {
        Mention mention = getMentionFixture();
        given(mentionRepository.findById(anyInt())).willReturn(Optional.of(mention));

        Mention foundMention = mentionService.getMention(1);

        then(foundMention)
                .hasFieldOrPropertyWithValue("id", mention.getId())
                .hasFieldOrPropertyWithValue("name", mention.getName())
                .hasFieldOrPropertyWithValue("content", mention.getContent())
                .hasFieldOrPropertyWithValue("createdAt", mention.getCreatedAt());
    }

    @Test
    void getMention_NonExistentMentionId_MentionNotFoundException() {
        given(mentionRepository.findById(anyInt())).willReturn(Optional.empty());
        thenThrownBy(() -> mentionService.getMention(1)).isExactlyInstanceOf(MentionNotFoundException.class);
    }
}
