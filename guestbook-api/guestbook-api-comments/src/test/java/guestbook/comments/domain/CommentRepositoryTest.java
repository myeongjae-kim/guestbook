package guestbook.comments.domain;

import static org.assertj.core.api.BDDAssertions.then;
import static org.assertj.core.api.BDDAssertions.thenThrownBy;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.model.CreateTableRequest;
import com.amazonaws.services.dynamodbv2.model.DeleteTableRequest;
import com.amazonaws.services.dynamodbv2.model.Projection;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;
import com.amazonaws.services.dynamodbv2.util.TableUtils;

import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

import guestbook.comments.config.DynamoDbConfig;
import guestbook.comments.exception.CommentNotFoundException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = {DynamoDbConfig.class})
public
class CommentRepositoryTest {
    private @Autowired CommentRepository commentRepository;
    private @Autowired DynamoDBMapper dynamoDbMapper;
    private @Autowired AmazonDynamoDB amazonDynamoDb;

    @BeforeEach
    void createTable() {
        createTable(amazonDynamoDb, dynamoDbMapper);
    }

    public static void createTable(AmazonDynamoDB amazonDynamoDb, DynamoDBMapper dynamoDbMapper) {
        CreateTableRequest createTableRequest = dynamoDbMapper.generateCreateTableRequest(Comment.class)
                .withProvisionedThroughput(new ProvisionedThroughput(1L, 1L));

        createTableRequest.getGlobalSecondaryIndexes().forEach(
                idx -> idx
                        .withProvisionedThroughput(new ProvisionedThroughput(1L, 1L))
                        .withProjection(new Projection().withProjectionType("ALL"))
        );

        TableUtils.createTableIfNotExists(amazonDynamoDb, createTableRequest);
    }

    @AfterEach
    void deleteTable() {
        deleteTable(amazonDynamoDb, dynamoDbMapper);
    }

    public static void deleteTable(AmazonDynamoDB amazonDynamoDb, DynamoDBMapper dynamoDbMapper) {
        DeleteTableRequest deleteTableRequest = dynamoDbMapper.generateDeleteTableRequest(Comment.class);
        TableUtils.deleteTableIfExists(amazonDynamoDb, deleteTableRequest);
    }

    @Test
    void createComment_ValidInput_CreatedComment() {
        Comment createdComment = commentRepository.save(Comment.builder()
                .mentionId(1)
                .content("content").build());

        then(createdComment)
                .hasNoNullFieldsOrPropertiesExcept("deletedAt")
                .hasFieldOrPropertyWithValue("mentionId", 1)
                .hasFieldOrPropertyWithValue("content", "content");
    }

    @Test
    void findCreatedComment_ById_FoundComment() {
        // given
        Comment createdComment = commentRepository.save(Comment.builder()
                .mentionId(1)
                .content("content").build());

        // when
        Comment foundComment = commentRepository.findById(createdComment.getId())
                .orElseThrow(() -> new CommentNotFoundException(createdComment.getId()));

        // then
        then(foundComment)
                .hasNoNullFieldsOrPropertiesExcept("deletedAt")
                .hasFieldOrPropertyWithValue("mentionId", 1)
                .hasFieldOrPropertyWithValue("content", "content");
    }

    @Test
    void findComments_ByMentionIdAndOrderByCreatedAtDesc_FoundCommentsInDesignatedOrder() {
        // given
        int size = 10;
        int mentionId = (new Random()).nextInt(1 << 31 - 1);
        IntStream.range(0, size).forEach(i -> commentRepository.save(Comment.builder()
                .mentionId(mentionId)
                .content("content " + i).build()));

        // when
        List<Comment> foundComment = commentRepository.findAllByMentionIdOrderByCreatedAtDesc(mentionId);

        // then
        then(foundComment.size()).isEqualTo(size);
        IntStream.range(1, size).forEach(i -> {
            Comment prev = foundComment.get(i-1);
            Comment next = foundComment.get(i);
            then(prev.getCreatedAt().isAfter(next.getCreatedAt())).isTrue();
        });
    }

    @Test
    void updateComment_ValidInput_UpdatedComment() {
        // given
        String id = commentRepository.save(Comment.builder().mentionId(1).content("content").build())
                .getId();

        // when
        Comment foundComment = commentRepository.findById(id)
                .orElseThrow(() -> new CommentNotFoundException(id));
        foundComment.update("updated content");
        commentRepository.save(foundComment);

        // then
        then(foundComment)
                .hasNoNullFieldsOrPropertiesExcept("deletedAt")
                .hasFieldOrPropertyWithValue("mentionId", 1)
                .hasFieldOrPropertyWithValue("content", "updated content");
    }

    @Test
    void deleteCreatedComment_TryToFindDeletedComment_ThrowCommentNotFoundException() {
        // given
        Comment createdComment = commentRepository.save(Comment.builder()
                .mentionId(1)
                .content("content").build());

        // when
        commentRepository.delete(createdComment);

        // then
        thenThrownBy(() -> commentRepository.findById(createdComment.getId())
                .orElseThrow(() -> new CommentNotFoundException(createdComment.getId())))
                .isExactlyInstanceOf(CommentNotFoundException.class);
    }
}
