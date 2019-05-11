package guestbook.mentions.api;

import java.util.List;
import javax.validation.Valid;

import guestbook.mentions.api.dto.MentionRequest;
import guestbook.mentions.api.dto.MentionResponse;
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
    public MentionResponse read(@PathVariable Integer id) {
        return mentionService.readMention(id);
    }

    @GetMapping("/list")
    public List<MentionResponse> readAllMentions() {
        return mentionService.readAllMentions();
    }

    @PutMapping("/{id}")
    public void update(
            @PathVariable Integer id,
            @RequestBody @Valid MentionRequest mentionRequest
    ) {
        mentionService.updateMention(id, mentionRequest);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        mentionService.deleteMention(id);
    }
}
