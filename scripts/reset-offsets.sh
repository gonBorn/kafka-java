#!/bin/zsh

set -euo pipefail
set -x

NEW_OFFSET=2

body="{
  \"offsets\": [
    {
      \"partition\": {
        \"kafka_partition\": 0,
        \"kafka_topic\": \"orders\"
      },
      \"offset\": {
        \"kafka_offset\": $NEW_OFFSET
      }
    }
  ]
}"

data=$(echo $body | jq -c)

# https://docs.confluent.io/platform/current/connect/references/restapi.html#patch--connectors-connector-offsets

curl -v -k -X PATCH "http://localhost:8083/connectors/JdbcSinkMultiTasksConnector/offsets" \
  --header "Content-Type: application/json" \
  --data "$data"
