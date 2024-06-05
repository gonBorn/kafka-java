#!/bin/zsh

set -euo pipefail

# https://docs.confluent.io/platform/current/connect/references/restapi.html#put--connectors-(string-name)-resume

curl -k -f -X PUT "http://localhost:8083/connectors/JdbcSinkMultiTasksConnector/resume" |
  jq
