#!/bin/zsh

# 获取所有的connectors
connectors=$(curl -s -X GET http://localhost:8083/connectors | jq -r '.[]')

select connector in $connectors; do
  if [ -n "$connector" ]; then
    # 停止用户选择的connector
    curl -s -X PUT "http://localhost:8083/connectors/$connector/pause" | jq
    echo "Connector $connector has been stopped."

    # 删除用户选择的connector
    curl -s -X DELETE "http://localhost:8083/connectors/$connector" | jq
    echo "Connector $connector has been deleted."
  fi
  break
done