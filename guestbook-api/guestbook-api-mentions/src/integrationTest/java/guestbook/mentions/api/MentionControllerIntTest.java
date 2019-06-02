package guestbook.mentions.api;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;

import guestbook.mentions.MentionsApiApplication;
import guestbook.mentions.api.dto.MentionRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

@ExtendWith(value = {SpringExtension.class})
@AutoConfigureMockMvc
@SpringBootTest(classes = MentionsApiApplication.class, webEnvironment = RANDOM_PORT)
@Transactional
@Sql("/data/mentions.sql")
public class MentionControllerIntTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void getMention_ValidInput_ValidOutput() throws Exception {
        this.mockMvc.perform(get("/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("name1"))
                .andExpect(jsonPath("$.content").value("content1"))
                .andExpect(jsonPath("$.createdAt").isNotEmpty());
    }

    @Test
    public void getAllMentions_ValidInput_ValidOutput() throws Exception {
        this.mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$.[?(@.id == 1)].name").value("name1"))
                .andExpect(jsonPath("$.[?(@.id == 1)].content").value("content1"))
                .andExpect(jsonPath("$.[?(@.id == 1)].createdAt").isNotEmpty())
                .andExpect(jsonPath("$.[?(@.id == 2)].name").value("name2"))
                .andExpect(jsonPath("$.[?(@.id == 2)].content").value("content2"))
                .andExpect(jsonPath("$.[?(@.id == 2)].createdAt").isNotEmpty());
    }

    @Test
    public void postMention_ValidInput_GetIdOfGeneratdMention() throws Exception {
        MentionRequest mentionRequest = new MentionRequest();
        mentionRequest.setName("arbitrary name");
        mentionRequest.setContent("arbitrary content");

        this.mockMvc.perform(post("/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(mentionRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isNumber());
    }

    @Test
    public void putMention_ValidInput_StatusOkAndGetTheModifiedOne() throws Exception {
        MentionRequest mentionRequest = new MentionRequest();
        mentionRequest.setName("modified name");
        mentionRequest.setContent("modified content");

        this.mockMvc.perform(put("/{id}", 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(mentionRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").doesNotExist());

        this.mockMvc.perform(get("/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("modified name"))
                .andExpect(jsonPath("$.content").value("modified content"))
                .andExpect(jsonPath("$.createdAt").isNotEmpty());
    }

    @Test
    public void deleteMentionAndGetTheDeletedOne_ValidInput_StatusOkAndFailToGetTheDeletedOne() throws Exception {
        this.mockMvc.perform(delete("/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").doesNotExist());

        this.mockMvc.perform(get("/{id}", 1))
                .andExpect(status().isNotFound());
    }
}
