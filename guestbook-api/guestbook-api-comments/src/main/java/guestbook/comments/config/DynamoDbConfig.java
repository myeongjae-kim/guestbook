package guestbook.comments.config;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperConfig;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverter;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.TimeZone;

import org.socialsignin.spring.data.dynamodb.repository.config.EnableDynamoDBRepositories;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableDynamoDBRepositories(basePackages = "guestbook.comments.domain")
public class DynamoDbConfig {
    @Value("${amazon.dynamodb.endpoint}")
    private String amazonDynamoDbEndpoint;

    @Value("${amazon.dynamodb.region}")
    private String amazonDynamoDbRegion;

    @Value("${amazon.aws.accesskey}")
    private String amazonAwsAccessKey;

    @Value("${amazon.aws.secretkey}")
    private String amazonAwsSecretKey;

    private AWSCredentialsProvider amazonAwsCredentialsProvider() {
        return new AWSStaticCredentialsProvider(amazonAwsCredentials());
    }

    @Bean
    public AWSCredentials amazonAwsCredentials() {
        return new BasicAWSCredentials(amazonAwsAccessKey, amazonAwsSecretKey);
    }

    @Bean
    public DynamoDBMapperConfig dynamoDbMapperConfig() {
        return DynamoDBMapperConfig.DEFAULT;
    }

    @Bean
    public DynamoDBMapper dynamoDbMapper(AmazonDynamoDB amazonDynamoDb, DynamoDBMapperConfig config) {
        return new DynamoDBMapper(amazonDynamoDb, config);
    }

    @Bean(name = "amazonDynamoDB")
    public AmazonDynamoDB amazonDynamoDb() {
        return AmazonDynamoDBClientBuilder.standard()
                .withCredentials(amazonAwsCredentialsProvider())
                .withEndpointConfiguration(
                        new AwsClientBuilder.EndpointConfiguration(amazonDynamoDbEndpoint, amazonDynamoDbRegion))
                .build();
    }

    public static class LocalDateTimeConverter implements DynamoDBTypeConverter<Date, LocalDateTime> {
        @Override
        public Date convert(LocalDateTime source) {
            return Date.from(source.toInstant(ZoneOffset.UTC));
        }

        @Override
        public LocalDateTime unconvert(Date source) {
            return source.toInstant().atZone(TimeZone.getDefault().toZoneId()).toLocalDateTime();
        }
    }
}

