curl -X POST -H "Accept:application/json" -H "Content-Type: application/json" \
--data @../config/connect-jdbc-sink.json http://localhost:8083/connectors

curl -X POST -H "Accept:application/json" -H "Content-Type: application/json" \
--data @../config/connect-jdbc-sink-order.json http://localhost:8083/connectors
