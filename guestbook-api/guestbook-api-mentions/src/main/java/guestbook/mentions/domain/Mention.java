package guestbook.mentions.domain;

import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.util.Assert;

@Getter
@Entity
@NoArgsConstructor
public class Mention {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String content;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @Builder
    public Mention(String name, String content) {
        Assert.hasLength(name, "Name should not be empty.");
        Assert.hasLength(content, "Content should not be empty.");

        this.name = name;
        this.content = content;
    }

    public void update(String name, String content) {
        this.name = name;
        this.content = content;
    }
}
