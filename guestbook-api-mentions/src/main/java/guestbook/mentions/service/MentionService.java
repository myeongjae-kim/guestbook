package guestbook.mentions.service;

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

    public Mention getMention(Integer id) throws MentionNotFoundException {
        return mentionRepository.findById(id).orElseThrow(() -> new MentionNotFoundException(id));
    }
}
