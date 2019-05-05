package guestbook.mentions.api.dto;


import static org.assertj.core.api.BDDAssertions.then;

import javax.validation.Validation;
import javax.validation.Validator;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class MentionRequestTest {
    private static Validator validator;

    @BeforeAll
    static void setup() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Test
    void generateMentionRequest_EmptyName_ViolatedConstraint() {
        MentionRequest mentionRequest = new MentionRequest();
        mentionRequest.setName("");
        mentionRequest.setContent("content");

        then(validator.validate(mentionRequest)).hasSize(1);
    }

    @Test
    void generateMentionRequest_EmptyContent_ViolatedConstraint() {
        MentionRequest mentionRequest = new MentionRequest();
        mentionRequest.setName("name");
        mentionRequest.setContent("");

        then(validator.validate(mentionRequest)).hasSize(1);
    }
}
