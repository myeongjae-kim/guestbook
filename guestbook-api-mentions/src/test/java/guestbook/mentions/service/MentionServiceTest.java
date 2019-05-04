package guestbook.mentions.service;

import static guestbook.mentions.domain.MentionTest.getMentionFixture;
import static org.assertj.core.api.BDDAssertions.then;
import static org.assertj.core.api.BDDAssertions.thenThrownBy;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;

import java.util.Optional;

import guestbook.mentions.api.dto.MentionRequest;
import guestbook.mentions.api.dto.MentionResponse;
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
    void createMention_ValidInput_NoException() {
        MentionRequest mentionRequest = new MentionRequest();
        mentionRequest.setName("name");
        mentionRequest.setContent("content");

        // Test is successful if below statement does not throw any exception.
        // The integration test checks whether the mention is really generated.
        mentionService.createMention(mentionRequest);
    }

    @Test
    void readMention_ValidInput_MentionFound() throws Exception {
        Mention mention = getMentionFixture();
        given(mentionRepository.findById(anyInt())).willReturn(Optional.of(mention));

        MentionResponse foundMention = mentionService.readMention(1);

        then(foundMention)
                .hasFieldOrPropertyWithValue("id", mention.getId())
                .hasFieldOrPropertyWithValue("name", mention.getName())
                .hasFieldOrPropertyWithValue("content", mention.getContent())
                .hasFieldOrPropertyWithValue("createdAt", mention.getCreatedAt());
    }

    @Test
    void readMention_NonExistentMentionId_MentionNotFoundException() {
        given(mentionRepository.findById(anyInt())).willReturn(Optional.empty());
        thenThrownBy(() -> mentionService.readMention(1)).isExactlyInstanceOf(MentionNotFoundException.class);
    }

    @Test
    void updateMention_ValidInput_NoException() throws MentionNotFoundException {
        given(mentionRepository.findById(anyInt())).willReturn(Optional.of(new Mention()));

        MentionRequest mentionRequest = new MentionRequest();
        mentionRequest.setName("updated name");
        mentionRequest.setContent("updated content");

        // Test is successful if below statement does not throw any exception.
        // The integration test checks whether the mention is really updated.
        mentionService.updateMention(1, mentionRequest);
    }

    @Test
    void updateMention_NonExistentMentionId_MentionNotFoundException() {
        given(mentionRepository.findById(anyInt())).willReturn(Optional.empty());
        thenThrownBy(() -> mentionService.updateMention(1, new MentionRequest()))
                .isExactlyInstanceOf(MentionNotFoundException.class);
    }

    @Test
    void deleteMention_ValidInput_NoException() throws MentionNotFoundException {
        given(mentionRepository.findById(anyInt())).willReturn(Optional.of(new Mention()));
        mentionService.deleteMention(1);
    }

    @Test
    void deleteMention_NonExistentMentionId_MentionNotFoundException() {
        given(mentionRepository.findById(anyInt())).willReturn(Optional.empty());
        thenThrownBy(() -> mentionService.deleteMention(1))
                .isExactlyInstanceOf(MentionNotFoundException.class);
    }
}
