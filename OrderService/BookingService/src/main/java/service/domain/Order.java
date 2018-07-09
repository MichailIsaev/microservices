package service.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.UUID;

@Data
@Component
@NoArgsConstructor
public class Order implements Serializable {

    private static final long serialVersionUID = 1L;

    @JsonProperty("user_id")
    @Getter @Setter private int userId;

    @JsonProperty("session_id")
    @Getter @Setter private int sessionId;

    @JsonProperty("tickets_amount")
    @Getter @Setter private int ticketsAmount;
}
