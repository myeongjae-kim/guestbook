package guestbook.comments;

import static org.assertj.core.api.BDDAssertions.then;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.model.CreateTableRequest;
import com.amazonaws.services.dynamodbv2.model.Projection;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;
import com.amazonaws.services.dynamodbv2.model.ScanRequest;
import com.amazonaws.services.dynamodbv2.model.ScanResult;
import com.amazonaws.services.dynamodbv2.util.TableUtils;

import guestbook.comments.config.DynamoDbConfig;
import guestbook.comments.domain.Comment;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = {DynamoDbConfig.class})
class SpringDataDynamoDbTestToLearn {
    private @Autowired AmazonDynamoDB amazonDynamoDb;
    private @Autowired DynamoDBMapper dynamoDbMapper;
    private Comment comment;

    @BeforeEach
    void setup() {
        comment = Comment.builder()
                .mentionId(1)
                .content("content").build();
    }

    @Test @Disabled
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

    @Test @Disabled
    void putItem_ShouldBeCalledAfterTableCreation_IncreasedScannedCounts() {
        ScanRequest scanRequest = new ScanRequest().withTableName("Comment");
        int beforeCount = amazonDynamoDb.scan(scanRequest).getCount();

        dynamoDbMapper.save(comment);
        System.out.println(comment.getId());

        ScanResult result = amazonDynamoDb.scan(scanRequest);
        then(result.getCount()).isEqualTo(beforeCount + 1);
    }

    @Test @Disabled
    void putAnddeleteItem_ShouldBeCalledAfterTableCreation_SameScannedCounts() {
        ScanRequest scanRequest = new ScanRequest().withTableName("Comment");
        int beforeCount = amazonDynamoDb.scan(scanRequest).getCount();

        dynamoDbMapper.save(comment);
        dynamoDbMapper.delete(comment);

        ScanResult result = amazonDynamoDb.scan(scanRequest);
        then(result.getCount()).isEqualTo(beforeCount);
    }
}
