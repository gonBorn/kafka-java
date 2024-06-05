#!/bin/zsh

# https://docs.confluent.io/platform/current/connect/references/restapi.html#get--connectors

curl -k -v -X PUT "http://localhost:8083/connectors/JdbcSinkMultiTasksConnector/stop" \
  | jq
