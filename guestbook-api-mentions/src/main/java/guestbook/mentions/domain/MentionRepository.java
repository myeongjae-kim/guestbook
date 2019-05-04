package guestbook.mentions.domain;

import org.springframework.data.repository.CrudRepository;

public interface MentionRepository extends CrudRepository<Mention, Integer> {
}
