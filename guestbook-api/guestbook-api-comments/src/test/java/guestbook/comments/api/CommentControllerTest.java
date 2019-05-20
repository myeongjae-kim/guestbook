package guestbook.comments.api;

import static guestbook.comments.api.dto.CommentResponseTest.getCommentResponseFixture;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

import guestbook.comments.api.dto.CommentRequest;
import guestbook.comments.api.dto.CommentResponse;
import guestbook.comments.service.CommentService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest
class CommentControllerTest {
    private @Autowired MockMvc mvc;
    private @MockBean CommentService commentService;
    private @Autowired ObjectMapper objectMapper;

    @Test
    void readComment_ValidInput_ValidOutput() throws Exception {
        CommentResponse commentResponse = getCommentResponseFixture();
        given(commentService.readComment(anyString())).willReturn(commentResponse);

        this.mvc.perform(get("/{id}", commentResponse.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(commentResponse.getId()))
                .andExpect(jsonPath("$.mentionId").value(commentResponse.getMentionId()))
                .andExpect(jsonPath("$.content").value(commentResponse.getContent()))
                .andExpect(jsonPath("$.createdAt").isNotEmpty());
    }

    @Test
    void readComments_WithMentionId_ValidOutput() throws Exception {
        Page<CommentResponse> comments = new PageImpl<>(IntStream.range(0, 10)
                .mapToObj(i -> getCommentResponseFixture("comment id " + i))
                .collect(Collectors.toList()));
        given(commentService.readCommentsByMentionId(any(Pageable.class), anyInt())).willReturn(comments);

        this.mvc.perform(get("/mention/{mentionId}", 1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.length()").value(10));
    }

    @Test
    void createComment_ValidInput_ValidOutput() throws Exception {
        CommentRequest commentRequest = new CommentRequest();
        commentRequest.setMentionId(1);
        commentRequest.setContent("content");

        given(commentService.createComment(any(CommentRequest.class))).willReturn("comment id");

        this.mvc.perform(post("/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(commentRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value("comment id"));
    }

    @Test
    void updateComment_ValidInput_ValidOutput() throws Exception {
        CommentResponse commentResponse = getCommentResponseFixture();
        given(commentService.readComment(anyString())).willReturn(commentResponse);

        CommentRequest commentRequest = new CommentRequest();
        commentRequest.setMentionId(1);
        commentRequest.setContent("modified content");

        this.mvc.perform(put("/{id}", "comment id")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(commentRequest)))
                .andExpect(status().isOk());
    }

    @Test
    void deleteComment_ValidInput_ValidOutput() throws Exception {
        CommentResponse commentResponse = getCommentResponseFixture();
        given(commentService.readComment(anyString())).willReturn(commentResponse);

        this.mvc.perform(delete("/{id}", "comment id"))
                .andExpect(status().isOk());
    }
}
