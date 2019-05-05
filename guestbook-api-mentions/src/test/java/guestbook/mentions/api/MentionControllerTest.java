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

import guestbook.mentions.api.dto.MentionRequest;
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
        given(mentionService.readMention(anyInt())).willReturn(getMentionResponseFixture());

        mvc.perform(get("/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").value(1))
                .andExpect(jsonPath("name").value("name"))
                .andExpect(jsonPath("content").value("content"))
                .andExpect(jsonPath("createdAt").isNotEmpty());
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
    void put_ValidInput_MentionResponse() throws Exception {
        String requestBody = objectMapper.writeValueAsString(getMentionRequestFixture());

        mvc.perform(
                put("/{id}",1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk());
    }

    @Test
    void delete_ValidInput_MentionResponse() throws Exception {
        mvc.perform(delete("/{id}",1))
                .andExpect(status().isOk());
    }
}
