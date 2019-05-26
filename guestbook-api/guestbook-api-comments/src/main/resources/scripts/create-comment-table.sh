#!/usr/bin/env bash

curl -X POST \
  http://localhost:8000/ \
  -H 'Content-Type: application/json' \
  -H 'Authorization: AWS4-HMAC-SHA256 Credential=key1/20190526/ap-northeast-2' \
  -H 'X-Amz-Target: DynamoDB_20120810.CreateTable' \
  -d '{
    "AttributeDefinitions": [
        {
            "AttributeName": "id",
            "AttributeType": "S"
        },
        {
            "AttributeName": "mentionId",
            "AttributeType": "N"
        },
        {
            "AttributeName": "createdAt",
            "AttributeType": "S"
        }
    ],
    "TableName": "Comment",
    "KeySchema": [
        {
            "AttributeName": "id",
            "KeyType": "HASH"
        }
    ],
    "GlobalSecondaryIndexes": [
        {
            "IndexName": "byMentionId",
            "KeySchema": [
                {
                    "AttributeName": "mentionId",
                    "KeyType": "HASH"
                },
                {
                    "AttributeName": "createdAt",
                    "KeyType": "RANGE"
                }
            ],
            "Projection": {
                "ProjectionType": "ALL"
            },
            "ProvisionedThroughput": {
                "ReadCapacityUnits": 1,
                "WriteCapacityUnits": 1
            }
        }
    ],
    "ProvisionedThroughput": {
        "ReadCapacityUnits": 1,
        "WriteCapacityUnits": 1
    }
}'