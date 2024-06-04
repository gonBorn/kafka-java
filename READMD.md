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