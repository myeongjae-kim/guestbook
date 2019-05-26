#!/usr/bin/env bash

curl -X POST \
  http://localhost:8000/ \
  -H 'Authorization: AWS4-HMAC-SHA256 Credential=key1/20190526/ap-northeast-2' \
  -H 'Content-Type: application/json' \
  -H 'X-Amz-Target: DynamoDB_20120810.DeleteItem' \
  -d '{
	"TableName":"Comment",
	"Key":{
		"id": {"S": "uuid"}
	}
}'