#!/bin/bash

# 获取所有的connectors
connectors=$(curl -s -X GET http://localhost:8083/connectors | jq -r '.[]')

select connector in $connectors; do
  if [ -n "$connector" ]; then
    # 根据用户的选择获取connector的状态
    status=$(curl -s -X GET http://localhost:8083/connectors/$connector/status)
    echo "$status"
  fi
  break
done