package guestbook.mentions.service;

import guestbook.mentions.api.dto.MentionRequest;
import guestbook.mentions.api.dto.MentionResponse;
import guestbook.mentions.domain.Mention;
import guestbook.mentions.domain.MentionRepository;
import guestbook.mentions.exception.MentionNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MentionService {
    private MentionRepository mentionRepository;

    @Autowired
    public MentionService(MentionRepository mentionRepository) {
        this.mentionRepository = mentionRepository;
    }

    public void createMention(MentionRequest mentionRequest) {
        mentionRepository.save(Mention.builder()
                .name(mentionRequest.getName())
                .content(mentionRequest.getContent()).build());
    }

    public MentionResponse readMention(Integer id) throws MentionNotFoundException {
        return MentionResponse.of(findMentionById(id));
    }

    public void updateMention(Integer id, MentionRequest mentionRequest) throws MentionNotFoundException {
        findMentionById(id).update(mentionRequest.getName(), mentionRequest.getContent());
    }

    public void deleteMention(Integer id) throws MentionNotFoundException {
        mentionRepository.delete(findMentionById(id));
    }

    private Mention findMentionById(Integer id) throws MentionNotFoundException {
        return mentionRepository.findById(id).orElseThrow(() -> new MentionNotFoundException(id));
    }
}
