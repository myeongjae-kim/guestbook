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

import guestbook.comments.api.dto.CommentPostRequest;
import guestbook.comments.api.dto.CommentResponse;
import guestbook.comments.exception.CommentNotFoundException;
import guestbook.comments.service.CommentService;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
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
                .andExpect(jsonPath("$.name").value(commentResponse.getName()))
                .andExpect(jsonPath("$.content").value(commentResponse.getContent()))
                .andExpect(jsonPath("$.createdAt").isNotEmpty());
    }

    @Test
    void readComment_NonExistentId_StatusNotFound() throws Exception {
        given(commentService.readComment(anyString())).willThrow(new CommentNotFoundException("comment id"));

        this.mvc.perform(get("/{id}", "non-existent-id"))
                .andExpect(status().isNotFound());
    }

    @Test
    void readComments_WithMentionId_ValidOutput() throws Exception {
        List<CommentResponse> comments = IntStream.range(0, 10)
                .mapToObj(i -> getCommentResponseFixture("comment id " + i))
                .collect(Collectors.toList());
        given(commentService.readCommentsOf(anyInt())).willReturn(comments);

        this.mvc.perform(get("/mention/{mentionId}", 1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(10));
    }

    @Test
    void createComment_ValidInput_ValidOutput() throws Exception {
        CommentPostRequest commentPostRequest = new CommentPostRequest();
        commentPostRequest.setMentionId(1);
        commentPostRequest.setName("name");
        commentPostRequest.setContent("content");

        given(commentService.createComment(any(CommentPostRequest.class))).willReturn("comment id");

        this.mvc.perform(post("/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(commentPostRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value("comment id"));
    }

    @Test
    void createComment_NullField_ThrowException() throws Exception {
        CommentPostRequest commentPostRequest = new CommentPostRequest();

        this.mvc.perform(post("/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(commentPostRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void updateComment_ValidInput_ValidOutput() throws Exception {
        CommentResponse commentResponse = getCommentResponseFixture();
        given(commentService.readComment(anyString())).willReturn(commentResponse);

        CommentPostRequest commentPostRequest = new CommentPostRequest();
        commentPostRequest.setMentionId(1);
        commentPostRequest.setContent("modified content");

        this.mvc.perform(put("/{id}", "comment id")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(commentPostRequest)))
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
