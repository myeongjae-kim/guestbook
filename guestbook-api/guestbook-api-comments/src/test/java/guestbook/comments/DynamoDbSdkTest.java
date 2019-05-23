package guestbook.comments;

import static org.assertj.core.api.BDDAssertions.then;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.model.AttributeDefinition;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.CreateTableRequest;
import com.amazonaws.services.dynamodbv2.model.DeleteItemRequest;
import com.amazonaws.services.dynamodbv2.model.DeleteItemResult;
import com.amazonaws.services.dynamodbv2.model.GetItemRequest;
import com.amazonaws.services.dynamodbv2.model.GetItemResult;
import com.amazonaws.services.dynamodbv2.model.GlobalSecondaryIndex;
import com.amazonaws.services.dynamodbv2.model.KeySchemaElement;
import com.amazonaws.services.dynamodbv2.model.KeyType;
import com.amazonaws.services.dynamodbv2.model.Projection;
import com.amazonaws.services.dynamodbv2.model.ProjectionType;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;
import com.amazonaws.services.dynamodbv2.model.PutItemRequest;
import com.amazonaws.services.dynamodbv2.model.PutItemResult;
import com.amazonaws.services.dynamodbv2.model.ScalarAttributeType;
import com.amazonaws.services.dynamodbv2.util.TableUtils;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

class DynamoDbSdkTest {
    private AWSCredentials awsCredentials;
    private AWSCredentialsProvider awsCredentialsProvider;
    private AmazonDynamoDB amazonDynamoDB;
    private Map<String, AttributeValue> item;

    @BeforeEach
    void setup() {
        awsCredentials = new BasicAWSCredentials(
                "key1", "key2");

        awsCredentialsProvider = new AWSStaticCredentialsProvider(awsCredentials);

        amazonDynamoDB = AmazonDynamoDBClientBuilder.standard()
                .withCredentials(awsCredentialsProvider)
                .withEndpointConfiguration(
                        new AwsClientBuilder.EndpointConfiguration("http://localhost:8000", "ap-northeast-2"))
                .build();

        item = new HashMap<>();
        item.put("id", (new AttributeValue()).withS("uuid"));
        item.put("mentionId", (new AttributeValue()).withN("1"));
        item.put("content", (new AttributeValue()).withS("comment content"));
        item.put("deleted", (new AttributeValue()).withBOOL(false));
        item.put("createdAt", (new AttributeValue()).withS("to be changed"));
        item.put("deletedAt", (new AttributeValue()).withS("to be changed"));
    }

    @Test @Disabled
    void createTable_SendCreateTableRequest_TableHasBeenCreated() {
        CreateTableRequest createTableRequest = (new CreateTableRequest())
                .withAttributeDefinitions(
                        new AttributeDefinition("id", ScalarAttributeType.S),
                        new AttributeDefinition("mentionId", ScalarAttributeType.N),
                        new AttributeDefinition("createdAt", ScalarAttributeType.S)
                ).withTableName("Comment").withKeySchema(
                        new KeySchemaElement("id", KeyType.HASH)
                ).withGlobalSecondaryIndexes(
                        (new GlobalSecondaryIndex())
                                .withIndexName("byMentionId")
                                .withKeySchema(
                                        new KeySchemaElement("mentionId", KeyType.HASH),
                                        new KeySchemaElement("createdAt", KeyType.RANGE) )
                                .withProjection(
                                        (new Projection()).withProjectionType(ProjectionType.ALL))
                                .withProvisionedThroughput(new ProvisionedThroughput(1L, 1L))
                ).withProvisionedThroughput(
                        new ProvisionedThroughput(1L, 1L)
                );

        boolean hasTableBeenCreated = TableUtils.createTableIfNotExists(amazonDynamoDB, createTableRequest);
        then(hasTableBeenCreated).isTrue();
    }


    @Test @Disabled
    void putItem() {
        PutItemRequest putItemRequest = (new PutItemRequest())
                .withTableName("Comment")
                .withItem(item);

        PutItemResult putItemResult = amazonDynamoDB.putItem(putItemRequest);
        then(putItemResult.getSdkHttpMetadata().getHttpStatusCode()).isEqualTo(200);
    }

    @Test @Disabled
    void getItem() {
        Map<String, AttributeValue> key = new HashMap<>();
        key.put("id", (new AttributeValue()).withS("uuid"));

        GetItemRequest getItemRequest = (new GetItemRequest())
                .withTableName("Comment")
                .withKey(key);

        GetItemResult getItemResult = amazonDynamoDB.getItem(getItemRequest);

        then(getItemResult.getItem()).containsAllEntriesOf(item);
    }

    @Test @Disabled
    void deleteItem() {
        Map<String, AttributeValue> key = new HashMap<>();
        key.put("id", (new AttributeValue()).withS("uuid"));

        DeleteItemRequest deleteItemRequest = (new DeleteItemRequest())
                .withTableName("Comment")
                .withKey(key);

        DeleteItemResult deleteItemResult = amazonDynamoDB.deleteItem(deleteItemRequest);

        then(deleteItemResult.getSdkHttpMetadata().getHttpStatusCode()).isEqualTo(200);
    }

    @Test @Disabled
    void getDeletedItem_ValidInput_NullItem() {
        Map<String, AttributeValue> key = new HashMap<>();
        key.put("id", (new AttributeValue()).withS("uuid"));

        GetItemRequest getItemRequest = (new GetItemRequest())
                .withTableName("Comment")
                .withKey(key);

        GetItemResult getItemResult = amazonDynamoDB.getItem(getItemRequest);

        then(getItemResult.getItem()).isNull();
    }
}
