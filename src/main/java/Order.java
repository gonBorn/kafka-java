import com.fasterxml.jackson.annotation.JsonProperty;

public record Order(@JsonProperty Integer customerId, @JsonProperty String orderDate) {
}
