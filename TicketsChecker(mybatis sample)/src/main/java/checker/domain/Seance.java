package checker.domain;


import checker.deserializers.CustomDateAndTimeDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.*;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Date;


@Data
@Component
@NoArgsConstructor
public class Seance implements Serializable {

    private static final long serialVersionUID = 1L;

    @Setter @Getter private Integer id;

    @Setter @Getter private String movie;

    @JsonDeserialize(using = CustomDateAndTimeDeserializer.class)
    @Setter @Getter private Date seanceTime;

    @Setter @Getter private long ticketCount;

}
