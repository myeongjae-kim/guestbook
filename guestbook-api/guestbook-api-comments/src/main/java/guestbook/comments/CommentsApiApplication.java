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
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
public class CommentsApiApplication {
    public static void main(String[] args) {
        SpringApplication.run(CommentsApiApplication.class, args);
    }

    @Bean
    @Profile("!test")
    public CommandLineRunner runner(AmazonDynamoDB amazonDynamoDb) {
        return (args) -> {
            amazonDynamoDb.putItem(getCommentRequest(
                    4,
                    "빌헬름 베어",
                    "지오반니님이 그린 달 지도 Full HD로 리마스터했습니다^^",
                    "1836-03-07T02:21:30.536Z"
            ));
            amazonDynamoDb.putItem(getCommentRequest(
                    5,
                    "윌리엄 하르트만",
                    "ㄴㄴ 제가 보아하니 달은 미행성과 지구의 충돌로 탄생했네요.",
                    "1975-04-15T08:41:23.322Z"
            ));
            amazonDynamoDb.putItem(getCommentRequest(
                    5,
                    "로빈 캐넙",
                    "오 윌리엄님 그럴싸한듯",
                    "1999-11-03T04:58:40.890Z"
            ));
            amazonDynamoDb.putItem(getCommentRequest(
                    5,
                    "알렉스 할리데이",
                    "그 행성을 테이아라고 부릅시다.",
                    "2000-05-07T10:41:04.029Z"
            ));
            amazonDynamoDb.putItem(getCommentRequest(
                    6,
                    "버즈 올드린",
                    "행성 지구에서 온 인간, 여기 달에 걸음을 남긴다.",
                    "1969-07-20T18:10:02.663Z"
            ));
            amazonDynamoDb.putItem(getCommentRequest(
                    6,
                    "성조기",
                    "...",
                    "1969-07-20T18:11:53.407Z"
            ));
            amazonDynamoDb.putItem(getCommentRequest(
                    6,
                    "지구평평론자",
                    "성조기 흔들리는거 보니까 가짜아님?ㅋ",
                    "1969-07-20T18:12:17.989Z"
            ));
            amazonDynamoDb.putItem(getCommentRequest(
                    7,
                    "손오공",
                    "오잉?",
                    "1975-02-21T12:40:44.772Z"
            ));
            amazonDynamoDb.putItem(getCommentRequest(
                    9,
                    "도우너",
                    "내 타임코스모스 고쳐내!",
                    "1996-07-24T01:08:00.146Z"
            ));
        };
    }

    private PutItemRequest getCommentRequest(Integer mentionId, String name, String content, String createdAt) {
        String id = UUID.randomUUID().toString();

        Map<String, AttributeValue> item = new HashMap<>();
        item.put("id", (new AttributeValue()).withS(id));
        item.put("mentionId", (new AttributeValue()).withN(mentionId.toString()));
        item.put("name", (new AttributeValue()).withS(name));
        item.put("content", (new AttributeValue()).withS(content));
        item.put("deleted", (new AttributeValue()).withBOOL(false));
        item.put("createdAt", (new AttributeValue()).withS(createdAt));

        return (new PutItemRequest())
                .withTableName("Comment")
                .withItem(item);
    }
}

