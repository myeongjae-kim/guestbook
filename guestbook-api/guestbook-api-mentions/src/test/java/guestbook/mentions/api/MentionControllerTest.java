package guestbook.mentions.api;

import static guestbook.mentions.api.dto.MentionRequestTest.getMentionRequestFixture;
import static guestbook.mentions.api.dto.MentionResponseTest.getMentionResponseFixture;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Arrays;
import java.util.List;

import guestbook.mentions.api.dto.MentionRequest;
import guestbook.mentions.api.dto.MentionResponse;
import guestbook.mentions.exception.MentionNotFoundException;
import guestbook.mentions.service.MentionService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest
class MentionControllerTest {
    private @Autowired MockMvc mvc;
    private @MockBean MentionService mentionService;
    private @Autowired ObjectMapper objectMapper;

    @Test
    void get_ValidInput_MentionResponse() throws Exception {
        MentionResponse mentionResponse = getMentionResponseFixture();
        given(mentionService.readMention(anyInt())).willReturn(mentionResponse);

        mvc.perform(get("/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").value(1))
                .andExpect(jsonPath("name").value("name"))
                .andExpect(jsonPath("content").value("content"))
                .andExpect(jsonPath("createdAt").isNotEmpty());
    }

    @Test
    void get_NonExistentId_ApiError() throws Exception {
        given(mentionService.readMention(anyInt())).willThrow(new MentionNotFoundException(1));

        mvc.perform(get("/{id}", 1))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("timestamp").isNotEmpty())
                .andExpect(jsonPath("status").isNumber())
                .andExpect(jsonPath("error").isNotEmpty())
                .andExpect(jsonPath("message").isNotEmpty());
    }

    @Test
    void getList_ValidInput_MentionResponses() throws Exception {
        List<MentionResponse> mentionResponses = Arrays.asList(
                getMentionResponseFixture(1),
                getMentionResponseFixture(2)
        );
        given(mentionService.readAllMentions()).willReturn(mentionResponses);

        mvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    void post_ValidInput_MentionResponse() throws Exception {
        given(mentionService.createMention(any(MentionRequest.class))).willReturn(1);
        String requestBody = objectMapper.writeValueAsString(getMentionRequestFixture());

        mvc.perform(
                post("/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(content().string("1"));
    }

    @Test
    void post_EmptyFields_ApiError() throws Exception {
        String requestBody = objectMapper.writeValueAsString(new MentionRequest());

        mvc.perform(
                post("/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("timestamp").isNotEmpty())
                .andExpect(jsonPath("status").isNumber())
                .andExpect(jsonPath("error").isNotEmpty())
                .andExpect(jsonPath("message").isNotEmpty());
    }

    @Test
    void put_ValidInput_MentionResponse() throws Exception {
        String requestBody = objectMapper.writeValueAsString(getMentionRequestFixture());

        mvc.perform(
                put("/{id}",1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk());
    }

    @Test
    void put_EmptyFields_ApiError() throws Exception {
        String requestBody = objectMapper.writeValueAsString(new MentionRequest());

        mvc.perform(
                put("/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("timestamp").isNotEmpty())
                .andExpect(jsonPath("status").isNumber())
                .andExpect(jsonPath("error").isNotEmpty())
                .andExpect(jsonPath("message").isNotEmpty());
    }

    @Test
    void delete_ValidInput_MentionResponse() throws Exception {
        mvc.perform(delete("/{id}",1))
                .andExpect(status().isOk());
    }
}
