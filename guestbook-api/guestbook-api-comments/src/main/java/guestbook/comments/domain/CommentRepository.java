package guestbook.comments.domain;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

public interface CommentRepository extends PagingAndSortingRepository<Comment, String> {
    List<Comment> findAllByMentionIdOrderByCreatedAtAsc(Integer mentionId);
}
