import com.google.gson.Gson;
import io.confluent.kafka.serializers.json.KafkaJsonSchemaSerializer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.apache.kafka.connect.data.Schema;
import org.apache.kafka.connect.data.SchemaBuilder;
import org.apache.kafka.connect.data.Struct;

import java.util.Properties;
import java.util.Scanner;

public class StudentProducer {
    private static final String TOPIC = "customized_students";

    public static void main(String[] args) {
        // Create a Kafka producer
//        KafkaProducer<String, String> producer = new KafkaProducer<>(props);
        KafkaProducer<String, Student> producer = new KafkaProducer<>(Utils.getProducerProperties());

        // Scanner for user input
        Scanner scanner = new Scanner(System.in);

//        Schema schema = SchemaBuilder.struct().name("Student")
//                .field("name", Schema.STRING_SCHEMA)
//                .field("studentId", Schema.STRING_SCHEMA)
//                .build();

        while (true) {
            // Read user input
            System.out.print("Enter student name: ");
            String name = scanner.nextLine();

            System.out.print("Enter student ID: ");
            String studentId = scanner.nextLine();

//             Create Student object
            Student student = new Student(name, studentId);

//             Convert Student object to JSON
            Gson gson = new Gson();
            String studentJson = gson.toJson(student);

            // Define the schema for the Student object
//             Create a Struct object
//            Struct struct = new Struct(schema)
//                    .put("name", name)
//                    .put("studentId", studentId);

            // Send JSON to Kafka topic
            ProducerRecord<String, Student> record = new ProducerRecord<>(TOPIC, studentId, student);
//            ProducerRecord<String, Struct> record = new ProducerRecord<>(TOPIC, studentId, struct);
//            ProducerRecord<String, String> record = new ProducerRecord<>("students", studentId, studentJson);
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
