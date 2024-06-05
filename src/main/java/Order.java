import com.fasterxml.jackson.annotation.JsonProperty;

public record Order(@JsonProperty int customerId, @JsonProperty String orderDate) {
}
