#!/bin/bash

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

echo "LocalStack resources initialized successfully!"