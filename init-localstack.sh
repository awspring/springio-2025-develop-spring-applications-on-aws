#!/bin/bash

set -e

# Create DynamoDB table
awslocal dynamodb create-table \
    --table-name order \
    --attribute-definitions AttributeName=orderId,AttributeType=S \
    --key-schema AttributeName=orderId,KeyType=HASH \
    --provisioned-throughput ReadCapacityUnits=5,WriteCapacityUnits=5

# Create SQS queue
awslocal sqs create-queue --queue-name order-queue

# Create S3 bucket
awslocal s3 mb s3://invoices

# Create SNS topic
awslocal sns create-topic --name order-notifications

# Subscriptions

set -e

# Names
TOPIC_NAME="order-created-topic"
QUEUE_NAME="order-created-queue"

# Create SNS topic
TOPIC_ARN=$(awslocal sns create-topic --name "$TOPIC_NAME" --query 'TopicArn' --output text)
echo "Created SNS topic: $TOPIC_ARN"

# Create SQS queue
QUEUE_URL=$(awslocal sqs create-queue --queue-name "$QUEUE_NAME" --query 'QueueUrl' --output text)
echo "Created SQS queue: $QUEUE_URL"

# Get SQS Queue ARN
QUEUE_ARN=$(awslocal sqs get-queue-attributes --queue-url "$QUEUE_URL" --attribute-names QueueArn --query 'Attributes.QueueArn' --output text)

# Allow SNS to send messages to SQS (set proper policy)
POLICY=$(cat <<EOF
{
  "Version": "2012-10-17",
  "Statement": [{
    "Sid": "Allow-SNS-SendMessage",
    "Effect": "Allow",
    "Principal": "*",
    "Action": "SQS:SendMessage",
    "Resource": "$QUEUE_ARN",
    "Condition": {
      "ArnEquals": {
        "aws:SourceArn": "$TOPIC_ARN"
      }
    }
  }]
}
EOF
)

awslocal sqs set-queue-attributes \
  --queue-url "$QUEUE_URL" \
  --attributes Policy="$(echo "$POLICY" | jq -c .)"

echo "Set SQS queue policy to allow SNS publishing."

# Subscribe SQS queue to SNS topic
awslocal sns subscribe \
  --topic-arn "$TOPIC_ARN" \
  --protocol sqs \
  --notification-endpoint "$QUEUE_ARN"

echo "Subscribed SQS queue to SNS topic."

echo "LocalStack resources initialized successfully!"