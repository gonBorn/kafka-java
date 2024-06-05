# shellcheck disable=SC2164
cd /Users/zeyan.du/WorkSpace/libs/package/kafka_2.13-3.7.0
bin/kafka-topics.sh --delete --topic orders --bootstrap-server localhost:29092
echo "orders topic deleted"
bin/kafka-topics.sh --delete --topic customized_students --bootstrap-server localhost:29092
echo "customized_students topic deleted"

curl -X DELETE http://localhost:8083/connectors/JdbcSinkMultiTasksConnector
echo "JdbcSinkMultiTasksConnector connector deleted"
curl -X DELETE http://localhost:8083/connectors/JdbcSinkConnector
echo "JdbcSinkConnector connector deleted"
