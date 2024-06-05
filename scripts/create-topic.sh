# shellcheck disable=SC2164
cd /Users/zeyan.du/WorkSpace/libs/package/kafka_2.13-3.7.0
bin/kafka-topics.sh --create --topic orders --bootstrap-server localhost:29092
bin/kafka-topics.sh --create --topic customized_students --bootstrap-server localhost:29092

bin/kafka-topics.sh --list --bootstrap-server localhost:29092
