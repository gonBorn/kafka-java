import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import com.google.gson.Gson;
import java.util.Properties;
import java.util.Scanner;

public class StudentProducer {

    public static void main(String[] args) {
        // Kafka producer configuration settings
        Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:29092");
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

        // Create a Kafka producer
        KafkaProducer<String, String> producer = new KafkaProducer<>(props);

        // Scanner for user input
        Scanner scanner = new Scanner(System.in);

        while (true) {
            // Read user input
            System.out.print("Enter student name: ");
            String name = scanner.nextLine();

            System.out.print("Enter student ID: ");
            String studentId = scanner.nextLine();

            // Create Student object
            Student student = new Student(name, studentId);

            // Convert Student object to JSON
            Gson gson = new Gson();
            String studentJson = gson.toJson(student);

            // Send JSON to Kafka topic
            ProducerRecord<String, String> record = new ProducerRecord<>("students", studentJson);
            producer.send(record);

            System.out.println("Student data sent to Kafka: " + studentJson);

            // Ask user if they want to continue
            System.out.print("Do you want to add another student? (yes/no): ");
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
