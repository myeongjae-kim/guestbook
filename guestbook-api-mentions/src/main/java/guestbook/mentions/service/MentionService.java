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

    public Integer createMention(MentionRequest mentionRequest) {
        Mention createdMention = mentionRepository.save(Mention.builder()
                .name(mentionRequest.getName())
                .content(mentionRequest.getContent()).build());
        return createdMention.getId();
    }

    public MentionResponse readMention(Integer id) throws MentionNotFoundException {
        return MentionResponse.of(findMentionById(id));
    }

    public void updateMention(Integer id, MentionRequest mentionRequest) throws MentionNotFoundException {
        Mention foundMention = findMentionById(id);
        foundMention.update(mentionRequest.getName(), mentionRequest.getContent());
        mentionRepository.save(foundMention);
    }

    public void deleteMention(Integer id) throws MentionNotFoundException {
        mentionRepository.delete(findMentionById(id));
    }

    private Mention findMentionById(Integer id) throws MentionNotFoundException {
        return mentionRepository.findById(id).orElseThrow(() -> new MentionNotFoundException(id));
    }
}
