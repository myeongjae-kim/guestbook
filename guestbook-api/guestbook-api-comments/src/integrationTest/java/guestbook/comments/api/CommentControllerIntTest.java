package guestbook.comments.api;

import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.BDDAssertions.then;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;

import guestbook.comments.CommentsApiApplication;
import guestbook.comments.api.dto.CommentPostRequest;
import guestbook.comments.api.dto.CommentPutRequest;

import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.client.RestTemplate;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = CommentsApiApplication.class, webEnvironment = RANDOM_PORT)
@AutoConfigureMockMvc
class CommentControllerIntTest {
    private @Autowired MockMvc mvc;
    private @Autowired ObjectMapper objectMapper;
    private @LocalServerPort Integer randomServerPort;
    private int uniqueMentionId;

    @BeforeEach
    void createUniqueMentionId() {
        uniqueMentionId = (new Random()).nextInt(1 << 31 - 1);
    }

    @Test
    void readComment_ValidInput_ValidOutput() throws Exception {
        String id = createCommentAndReturnId(uniqueMentionId);
        this.mvc.perform(get("/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.mentionId").isNumber())
                .andExpect(jsonPath("$.name").isString())
                .andExpect(jsonPath("$.content").isString())
                .andExpect(jsonPath("$.createdAt").isNotEmpty());
    }

    @Test
    void readComments_WithMentionId_ValidOutput() throws Exception {
        final int size = 10;
        List<String> ids = IntStream.range(0, size)
                .mapToObj(i -> createCommentAndReturnId(uniqueMentionId, "arbitrary name", "arbitrary content"))
                .collect(toList());

        MvcResult result = this.mvc.perform(get("/mention/{mentionId}", uniqueMentionId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(size))
                .andReturn();

        String responseBody = result.getResponse().getContentAsString();
        ids.forEach(id -> then(responseBody).contains(id));
    }

    @Test
    void createComment_ValidInput_ValidOutput() throws Exception {
        CommentPostRequest commentPostRequest = new CommentPostRequest();
        commentPostRequest.setMentionId(1);
        commentPostRequest.setName("name");
        commentPostRequest.setContent("content");

        this.mvc.perform(post("/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(commentPostRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isString());
    }

    @Test
    void updateComment_ValidInput_ValidOutput() throws Exception {
        String id = createCommentAndReturnId(uniqueMentionId);

        CommentPutRequest commentPutRequest = new CommentPutRequest();
        String modifiedContent = "modified content";
        commentPutRequest.setContent(modifiedContent);

        this.mvc.perform(put("/{id}", id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(commentPutRequest)))
                .andExpect(status().isOk());

        this.mvc.perform(get("/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.mentionId").isNumber())
                .andExpect(jsonPath("$.content").value(modifiedContent))
                .andExpect(jsonPath("$.createdAt").isNotEmpty());
    }

    @Test
    void deleteComment_ValidInput_ValidOutput() throws Exception {
        String id = createCommentAndReturnId(uniqueMentionId);

        this.mvc.perform(delete("/{id}", id))
                .andExpect(status().isOk());

        this.mvc.perform(get("/{id}", id))
                .andExpect(status().isNotFound());
    }

    private String createCommentAndReturnId(int mentionId) {
        return createCommentAndReturnId(mentionId, "name", "content");
    }

    private String createCommentAndReturnId(int mentionId, String name, String content) {
        final RestTemplate restTemplate = new RestTemplate();

        CommentPostRequest commentPostRequest = new CommentPostRequest();
        commentPostRequest.setMentionId(mentionId);
        commentPostRequest.setName(name);
        commentPostRequest.setContent(content);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");

        HttpEntity<CommentPostRequest> request = new HttpEntity<>(commentPostRequest, headers);

        String commentApiEndpoint = "http://localhost:" + randomServerPort;

        return restTemplate.postForEntity(
                commentApiEndpoint,
                request,
                String.class).getBody();
    }
}
