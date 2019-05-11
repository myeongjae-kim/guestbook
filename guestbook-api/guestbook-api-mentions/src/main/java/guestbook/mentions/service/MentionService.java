package guestbook.mentions.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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

    public MentionResponse readMention(Integer id) {
        return MentionResponse.of(findMentionById(id));
    }

    public List<MentionResponse> readAllMentions() {
        List<MentionResponse> mentionsResponses = new ArrayList<>();
        mentionRepository.findAll().forEach(m -> mentionsResponses.add(MentionResponse.of(m)));
        Collections.sort(mentionsResponses);

        return mentionsResponses;
    }

    public void updateMention(Integer id, MentionRequest mentionRequest) {
        Mention foundMention = findMentionById(id);
        foundMention.update(mentionRequest.getName(), mentionRequest.getContent());
        mentionRepository.save(foundMention);
    }

    public void deleteMention(Integer id) {
        mentionRepository.delete(findMentionById(id));
    }

    private Mention findMentionById(Integer id) {
        return mentionRepository.findById(id).orElseThrow(() -> new MentionNotFoundException(id));
    }
}
