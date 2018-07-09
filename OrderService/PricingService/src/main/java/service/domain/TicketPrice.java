package service.domain;


import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;


@Component
//@RequestScope CHECK
@PropertySource(value = "classpath:data.properties", ignoreResourceNotFound = true)
public class TicketPrice{
    @Value("${price.default}")
    @Getter @Setter private double price;
}
