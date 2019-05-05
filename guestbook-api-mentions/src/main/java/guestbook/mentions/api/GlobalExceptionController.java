package guestbook.mentions.api;

import guestbook.mentions.exception.MentionNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@ControllerAdvice
public class GlobalExceptionController {

    @ExceptionHandler(MentionNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    protected String handleNotFound(RuntimeException ex) {
        return ex.getMessage();
    }
}
