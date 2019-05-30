package guestbook.comments;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
public class CommentsApiApplication {
    public static void main(String[] args) {
        SpringApplication.run(CommentsApiApplication.class, args);
    }
}

