package guestbook.mentions.api;

import javax.validation.Valid;

import guestbook.mentions.api.dto.MentionRequest;
import guestbook.mentions.api.dto.MentionResponse;
import guestbook.mentions.exception.MentionNotFoundException;
import guestbook.mentions.service.MentionService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class MentionController {
    private MentionService mentionService;

    public MentionController(MentionService mentionService) {
        this.mentionService = mentionService;
    }

    @PostMapping
    public Integer create(@RequestBody @Valid MentionRequest mentionRequest) {
        return mentionService.createMention(mentionRequest);
    }

    @GetMapping("/{id}")
    //TODO: Remove throws by adding exception handling controller.
    public MentionResponse read(@PathVariable Integer id) throws MentionNotFoundException {
        return mentionService.readMention(id);
    }

    @PutMapping("/{id}")
    //TODO: Remove throws by adding exception handling controller.
    public void update(
            @PathVariable Integer id,
            @RequestBody @Valid MentionRequest mentionRequest
    ) throws MentionNotFoundException {
        mentionService.updateMention(id, mentionRequest);
    }

    @DeleteMapping("/{id}")
    //TODO: Remove throws by adding exception handling controller.
    public void delete(@PathVariable Integer id) throws MentionNotFoundException {
        mentionService.deleteMention(id);
    }
}
