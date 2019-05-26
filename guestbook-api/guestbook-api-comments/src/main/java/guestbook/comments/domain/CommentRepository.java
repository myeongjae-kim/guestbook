package guestbook.comments.domain;

import java.util.List;

import org.socialsignin.spring.data.dynamodb.repository.DynamoDBPagingAndSortingRepository;

public interface CommentRepository extends DynamoDBPagingAndSortingRepository<Comment, String> {
    List<Comment> findAllByMentionIdOrderByCreatedAtDesc(Integer mentionId);
}
