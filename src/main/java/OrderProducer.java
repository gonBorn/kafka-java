import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Scanner;

public class OrderProducer {
    private static final String TOPIC = "orders";

    public static void main(String[] args) {
        KafkaProducer<String, Order> producer = new KafkaProducer<>(Utils.getProducerProperties());

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.print("Enter orderId: ");
            String orderId = scanner.nextLine();

            System.out.print("Enter customerId: ");
            String customerId = scanner.nextLine();

            System.out.print("Enter orderDate: ");
            String orderDate = scanner.nextLine();

            Order order = new Order(Integer.parseInt(customerId), orderDate);

            ProducerRecord<String, Order> record = new ProducerRecord<>(TOPIC, orderId, order);
            producer.send(record, (metadata, exception) -> {
                if (exception != null) {
                    exception.printStackTrace();
                } else {
                    System.out.println("Sent record to topic " + metadata.topic() +
                            " partition " + metadata.partition() +
                            " at offset " + metadata.offset());
                }
            });

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
