package guestbook.comments.api;

import java.util.List;
import javax.validation.Valid;

import guestbook.comments.api.dto.CommentPostRequest;
import guestbook.comments.api.dto.CommentPutRequest;
import guestbook.comments.api.dto.CommentResponse;
import guestbook.comments.service.CommentService;
import org.springframework.web.bind.annotation.CrossOrigin;
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
@CrossOrigin
public class CommentController {
    private CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping("/{id}")
    public CommentResponse read(@PathVariable String id) {
        return commentService.readComment(id);
    }

    @GetMapping("/mention/{mentionId}")
    public List<CommentResponse> list(@PathVariable Integer mentionId) {
        return commentService.readCommentsOf(mentionId);
    }

    @PostMapping
    public String create(@RequestBody @Valid CommentPostRequest commentPostRequest) {
        return commentService.createComment(commentPostRequest);
    }

    @PutMapping("/{id}")
    public void update(@PathVariable String id, @RequestBody @Valid CommentPutRequest commentPutRequest) {
        commentService.updateComment(id, commentPutRequest);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        commentService.deleteComment(id);
    }
}
