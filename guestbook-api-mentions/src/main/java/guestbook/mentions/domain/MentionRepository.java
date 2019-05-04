package guestbook.mentions.domain;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MentionRepository extends JpaRepository<Mention, Integer> {
}
