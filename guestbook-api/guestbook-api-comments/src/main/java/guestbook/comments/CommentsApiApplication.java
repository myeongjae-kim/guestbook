package guestbook.comments;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.PutItemRequest;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
public class CommentsApiApplication {
    public static void main(String[] args) {
        SpringApplication.run(CommentsApiApplication.class, args);
    }

    @Bean
    public CommandLineRunner runner(AmazonDynamoDB amazonDynamoDb) {
        return (args) -> {
            amazonDynamoDb.putItem(getCommentRequest(
                    4,
                    "빌헬름 베어', '지오반니님이 그린 달 지도 Full HD로 리마스터했습니다^^",
                    "1836-03-07T02:21:30.536Z"
            ));
        };
    }

    private PutItemRequest getCommentRequest(Integer mentionId, String content, String createdAt) {
        String id = UUID.randomUUID().toString();

        Map<String, AttributeValue> item = new HashMap<>();
        item.put("id", (new AttributeValue()).withS(id));
        item.put("mentionId", (new AttributeValue()).withN(mentionId.toString()));
        item.put("content", (new AttributeValue()).withS(content));
        item.put("deleted", (new AttributeValue()).withBOOL(false));
        item.put("createdAt", (new AttributeValue()).withS(createdAt));

        return (new PutItemRequest())
                .withTableName("Comment")
                .withItem(item);
    }
}

