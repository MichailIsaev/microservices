package service.domain;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Data
@Component
@NoArgsConstructor
public class OrderInfo {
    private static final long serialVersionUID = 1L;

    @Getter @Setter private String filmTitle;
    @Getter @Setter private String filmDescription;
    @Getter @Setter private int hallId;
    @Getter @Setter private LocalDateTime time;
    @Getter @Setter private int ticketsAmount;
    @Getter @Setter private double price;

}
