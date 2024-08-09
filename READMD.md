# Reference

## Kafka Connect Concept

https://docs.confluent.io/platform/current/connect/index.html
https://docs.confluent.io/platform/current/connect/userguide.html#connect-userguide

## Kafka Connect JDBC Connector

https://www.confluent.io/hub/confluentinc/kafka-connect-jdbc?session_ref=direct

## Other open source connectors
https://www.confluent.io/product/connectors/?_ga=2.68501202.25982689.1717039609-1247891401.1693981423&_gl=1*1kp58w3*_ga*MTI0Nzg5MTQwMS4xNjkzOTgxNDIz*_ga_D2D3EGKSGD*MTcxNzEyNDQzMC41Mi4xLjE3MTcxMjU0NDkuNjAuMC4w

## Kafka quick start
download https://www.apache.org/dyn/closer.cgi?path=/kafka/3.7.0/kafka_2.13-3.7.0.tgz

### REA kafka cli
https://gist.git.realestate.com.au/yang-hu/05c1ce4a5ca1ec649cb8df8bfe455946

```bash
tar -xzf kafka_2.13-3.7.0.tgz
cd kafka_2.13-3.7.0
```

start brokers
```bash
bin/zookeeper-server-start.sh config/zookeeper.properties
```

```bash
bin/kafka-server-start.sh config/server.properties
```
```bash
cd /Users/zeyan.du/WorkSpace/libs/package/kafka_2.13-3.7.0
```

create topic
```bash
bin/kafka-topics.sh --create --topic students --bootstrap-server localhost:29092
```

list topic
```bash
bin/kafka-topics.sh --list --bootstrap-server localhost:9092
```

produce
```bash
bin/kafka-console-producer.sh --topic quickstart-events --bootstrap-server localhost:9092
```

consume
```bash
bin/kafka-console-consumer.sh --topic quickstart-events --from-beginning --bootstrap-server localhost:9092
```

# JDBC sink config
https://docs.confluent.io/kafka-connectors/jdbc/current/sink-connector/sink_config_options.html

table.name.format
A format string for the destination table name, which may contain ${topic} as a placeholder for the originating topic name.

For example, kafka_${topic} for the topic orders will map to the table name kafka_orders.

Type: string
Default: ${topic}
Importance: medium

# Required Kafka Connect configurations
https://docs.confluent.io/platform/current/installation/docker/config-reference.html

## Install the JDBC sink connector
https://docs.confluent.io/kafka-connectors/jdbc/current/sink-connector/overview.html#install-the-jdbc-sink-connector

## AWS Lambda sink connector
https://docs.confluent.io/kafka-connectors/aws-lambda/current/overview.html
https://www.confluent.io/hub/confluentinc/kafka-connect-aws-lambda?_ga=2.26271741.1937555616.1722783142-1247891401.1693981423&_gl=1*c92a0m*_gcl_au*OTc2MTEyNzMuMTcyMjQwNzAyNw..*_ga*MTI0Nzg5MTQwMS4xNjkzOTgxNDIz*_ga_D2D3EGKSGD*MTcyMjc4MzE0MS43Ni4xLjE3MjI3ODMyODcuMzguMC4w

manually install
https://docs.confluent.io/platform/current/connect/userguide.html#connect-installing-plugins

```bash
curl -X POST -H "Accept:application/json" -H "Content-Type: application/json" \
--data @/etc/kafka/connect-jdbc-sink.json http://localhost:8083/connectors
```

或

```bash
curl -X POST -H "Accept:application/json" -H "Content-Type: application/json" \
--data @./config/connect-jdbc-sink.json http://localhost:8083/connectors
```

replica
partition
分区数要靠下游的节点数来决定

pk.mode 是 Confluent JDBC Sink Connector 的一个配置选项，
用于指定如何处理 Kafka 记录的键（key）以及如何将其映射到数据库表的主键。
如果你希望 Kafka topic 中的数据被自动创建表并写入数据库，你需要指定 pk.mode。  
"pk.mode": "record_key"，key将被用作数据库表的主键。
如果key是一个结构体（Struct），那么它的所有字段都将被用作复合主键

如果你希望生成的表有主键，但 Kafka 记录的键不适合作为主键，
你可以使用 pk.fields 选项来指定一个或多个字段作为主键

Assignment received from leader connect-1-7e2db73f-e396-4b15-8b7e-7267ad091d7a for group my-connect-group for generation 1. The group has 1 members, 0 of which are static. (kafka.coordinator.group.GroupCoordinator)

```bash
curl -X GET http://localhost:8083/connectors 
curl http://localhost:8083/connectors/JdbcSinkConnector/status
curl -X DELETE http://localhost:8083/connectors/JdbcSinkConnector
```

"topics.regex": ".*"或    "topics": "students"必填一个

delete.enabled=false：这个配置项表示是否允许删除操作。当设置为false时，Sink Connector不会处理Kafka中的删除操作（即，Kafka中的null值记录）。这意味着，如果Kafka中的某个记录的值为null，那么这个记录不会被Sink Connector处理。 

"delete.enabled": "true"配置项允许connector处理Kafka中的删除记录（也就是说，当Kafka中的一条记录的值部分为null时，connector会删除数据库中对应的记录）

由于你的配置设置了delete.enabled=false和pk.mode=record_key，所以你需要确保Kafka中的每个记录都有一个非空的结构体（Struct）值和非空的结构体（Struct）模式。换句话说，你不能发送值为null或者没有结构体模式的记录到Kafka中，否则Sink Connector将无法处理这些记录。

自动创建表的功能依赖于Kafka记录的schema。如果Kafka记录没有schema（例如，你正在使用schemaless的数据格式，如JSON），那么Connector可能无法正确地创建表。

### converter
https://www.confluent.io/blog/kafka-connect-deep-dive-converters-serialization-explained/

Common converters include:

Avro
io.confluent.connect.avro.AvroConverter

Protobuf
io.confluent.connect.protobuf.ProtobufConverter

String
org.apache.kafka.connect.storage.StringConverter

JSON
org.apache.kafka.connect.json.JsonConverter

JSON Schema
io.confluent.connect.json.JsonSchemaConverter

ByteArray
org.apache.kafka.connect.converters.ByteArrayConverter

json-schema-serializer-and-deserializer
https://docs.confluent.io/platform/current/schema-registry/fundamentals/serdes-develop/serdes-json.html#json-schema-serializer-and-deserializer-for-sr-on-product

mvn 地址
https://mvnrepository.com/artifact/io.confluent/kafka-connect-json-schema-converter

converter
https://docs.confluent.io/platform/current/schema-registry/connect.html#json-schema

### 注册schema
curl -X GET http://localhost:8081/schemas/ids/1
[2024-06-05 13:49:24,419] INFO 192.168.160.1 - - [05/Jun/2024:13:49:24 +0000] "GET /schemas/ids/1 HTTP/1.1" 404 51 "-" "curl/8.6.0" 25 (io.confluent.rest-utils.requests)
[2024-06-05 13:50:59,557] INFO Registering new schema: subject customized_students-value, version null, id null, type JSON, schema size 288 (io.confluent.kafka.schemaregistry.rest.resources.SubjectVersionsResource)

### connect dlq
https://docs.confluent.io/platform/current/connect/index.html#dead-letter-queues
"errors.tolerance": "all",
"errors.deadletterqueue.topic.name": "dlq-gcs-sink-01",
"errors.deadletterqueue.context.headers.enable": true
