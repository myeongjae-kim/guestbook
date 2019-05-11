package guestbook.mentions.domain;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MentionRepository extends JpaRepository<Mention, Integer> {
    List<Mention> findAllByOrderByCreatedAtDesc();
}
