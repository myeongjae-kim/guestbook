package guestbook.comments.domain;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface CommentRepository extends PagingAndSortingRepository<Comment, String> {
    Page<Comment> findAllByMentionId(Pageable pageable, Integer episodeId);
}
