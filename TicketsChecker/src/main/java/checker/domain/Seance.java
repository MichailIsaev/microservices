package checker.domain;


import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Date;

@Setter
@Getter
@Component
public class Seance implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;

    private String Movie;
    private Date startTime;
    private long ticketCount;
}
