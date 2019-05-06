package guestbook.common.dto;

import static java.time.LocalDateTime.now;

import java.time.LocalDateTime;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ApiError {
    private LocalDateTime timestamp;
    private Integer status;
    private String error;
    private String message;

    public ApiError(HttpStatus httpStatus, String message) {
        this.timestamp = now();
        this.status = httpStatus.value();
        this.error = httpStatus.getReasonPhrase();
        this.message = message;
    }
}

