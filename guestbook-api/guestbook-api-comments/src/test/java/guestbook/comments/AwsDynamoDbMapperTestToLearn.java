package guestbook.comments;

import static org.assertj.core.api.BDDAssertions.then;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperConfig;
import com.amazonaws.services.dynamodbv2.model.CreateTableRequest;
import com.amazonaws.services.dynamodbv2.model.DeleteTableRequest;
import com.amazonaws.services.dynamodbv2.model.Projection;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;
import com.amazonaws.services.dynamodbv2.util.TableUtils;

import guestbook.comments.domain.Comment;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

class AwsDynamoDbMapperTestToLearn {
    private AmazonDynamoDB amazonDynamoDb;
    private DynamoDBMapper dynamoDbMapper;
    private Comment comment;

    @BeforeEach
    void setup() {
        AWSCredentials awsCredentials = new BasicAWSCredentials("key1", "key2");
        AWSCredentialsProvider awsCredentialsProvider = new AWSStaticCredentialsProvider(awsCredentials);
        AwsClientBuilder.EndpointConfiguration endpointConfiguration = new AwsClientBuilder.EndpointConfiguration("http://localhost:8000", "ap-northeast-2");

        amazonDynamoDb = AmazonDynamoDBClientBuilder.standard()
                .withCredentials(awsCredentialsProvider)
                .withEndpointConfiguration(endpointConfiguration).build();

        dynamoDbMapper = new DynamoDBMapper(amazonDynamoDb, DynamoDBMapperConfig.DEFAULT);

        comment = Comment.builder()
                .name("name")
                .mentionId(1)
                .content("content").build();
    }

    @Test
    @Disabled
    void createTable_ValidInput_TableHasBeenCreated() {
        CreateTableRequest createTableRequest = dynamoDbMapper.generateCreateTableRequest(Comment.class)
                .withProvisionedThroughput(new ProvisionedThroughput(1L, 1L));

        createTableRequest.getGlobalSecondaryIndexes().forEach(
                idx -> idx
                        .withProvisionedThroughput(new ProvisionedThroughput(1L, 1L))
                        .withProjection(new Projection().withProjectionType("ALL"))
        );
        then(TableUtils.createTableIfNotExists(amazonDynamoDb, createTableRequest)).isTrue();
    }

    @Test
    @Disabled
    void saveAndFindItem_ShouldBeCalledAfterTableCreation_FoundItem() {
        then(comment.getId()).isNull();
        dynamoDbMapper.save(comment);
        then(comment.getId()).isNotEmpty();
    }

    @Test
    @Disabled
    void saveAndLoadItem_ShouldBeCalledAfterTableCreation_FoundItem() {
        then(comment.getId()).isNull();
        dynamoDbMapper.save(comment);
        then(comment.getId()).isNotEmpty();

        Comment foundComment = dynamoDbMapper.load(Comment.class, comment.getId());

        then(foundComment)
                .hasFieldOrPropertyWithValue("id", comment.getId());
    }

    @Test
    @Disabled
    void saveAndUpdateItem_ShouldBeCalledAfterTableCreation_UpdatedItem() {
        // given
        then(comment.getId()).isNull();
        dynamoDbMapper.save(comment);
        final String commentId = comment.getId();

        then(commentId).isNotEmpty();
        then(comment).hasFieldOrPropertyWithValue("content", "content");

        // when
        comment.update("modified content");
        dynamoDbMapper.save(comment);
        Comment foundComment = dynamoDbMapper.load(Comment.class, commentId);

        // then
        then(foundComment)
                .hasFieldOrPropertyWithValue("content", "modified content");
    }

    @Test
    @Disabled
    void saveAndDeleteItem_ShouldBeCalledAfterTableCreation_SameScannedCounts() {
        then(comment.getId()).isNull();
        dynamoDbMapper.save(comment);
        final String commentId = comment.getId();
        then(comment.getId()).isNotEmpty();
        dynamoDbMapper.delete(comment);

        Comment comment = dynamoDbMapper.load(Comment.class, commentId);
        then(comment).isNull();
    }

    @Test
    @Disabled
    void deleteTable_ShouldBeCalledAfterTableCreation_TableHasBeenCreated() {
        DeleteTableRequest deleteTableRequest = dynamoDbMapper.generateDeleteTableRequest(Comment.class);
        then(TableUtils.deleteTableIfExists(amazonDynamoDb, deleteTableRequest)).isTrue();
    }
}

