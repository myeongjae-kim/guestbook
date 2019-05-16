package guestbook.mentions;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
public class MentionsApiApplication {
    public static void main(String[] args) {
        SpringApplication.run(MentionsApiApplication.class, args);
    }
}

