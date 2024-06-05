import io.confluent.kafka.serializers.json.KafkaJsonSchemaSerializer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.IntegerSerializer;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;
import java.util.Scanner;

public class OrderProducer {
    private static final String TOPIC = "orders";

    public static void main(String[] args) {
        Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:29092");
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, IntegerSerializer.class.getName());
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, KafkaJsonSchemaSerializer.class.getName());
        props.put("value.converter", "org.apache.kafka.connect.json.JsonSchemaConverter");
        props.put("schema.registry.url", "http://localhost:8081");
        KafkaProducer<Integer, Order> producer = new KafkaProducer<>(props);

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.print("Enter orderId: ");
            String orderId = scanner.nextLine();

            System.out.print("Enter customerId: ");
            String customerId = scanner.nextLine();

            System.out.print("Enter orderDate: ");
            String orderDate = scanner.nextLine();

            Order order = new Order(Integer.parseInt(customerId), orderDate);

            ProducerRecord<Integer, Order> record = new ProducerRecord<>(TOPIC, Integer.parseInt(orderId), order);
            producer.send(record);

            System.out.print("Do you want to add another orders? (yes/no): ");
            String answer = scanner.nextLine();
            if (answer.equalsIgnoreCase("no")) {
                break;
            }
        }

        // Close the producer
        producer.close();
        scanner.close();
    }
}
