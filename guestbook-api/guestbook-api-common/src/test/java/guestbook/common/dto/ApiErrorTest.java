package guestbook.common.dto;

import static org.assertj.core.api.BDDAssertions.then;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

class ApiErrorTest {
    @Test
    void constructApiError_ValidInput_ConstructedApiError() {
        ApiError apiError = new ApiError(HttpStatus.NOT_FOUND, "message");

        then(apiError)
                .hasNoNullFieldsOrProperties()
                .hasFieldOrProperty("timestamp")
                .hasFieldOrPropertyWithValue("status", HttpStatus.NOT_FOUND.value())
                .hasFieldOrPropertyWithValue("error", HttpStatus.NOT_FOUND.getReasonPhrase())
                .hasFieldOrPropertyWithValue("message", "message");
    }
}
