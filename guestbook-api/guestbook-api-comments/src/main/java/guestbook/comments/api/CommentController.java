package guestbook.comments.api;

import static org.springframework.data.domain.Sort.Direction.DESC;

import javax.validation.Valid;

import guestbook.comments.api.dto.CommentRequest;
import guestbook.comments.api.dto.CommentResponse;
import guestbook.comments.service.CommentService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
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

    @GetMapping("/mention/{id}")
    public Page<CommentResponse> list(
            @PageableDefault(sort = "createdAt", direction = DESC) Pageable pageParam,
            @PathVariable Integer id) {
        return commentService.readCommentsByMentionId(pageParam, id);
    }

    @PostMapping
    public String create(@RequestBody @Valid CommentRequest commentRequest) {
        return commentService.createComment(commentRequest);
    }

    @PutMapping("/{id}")
    public void update(@PathVariable String id, @RequestBody @Valid CommentRequest commentRequest) {
        commentService.updateComment(id, commentRequest);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        commentService.deleteComment(id);
    }
}
