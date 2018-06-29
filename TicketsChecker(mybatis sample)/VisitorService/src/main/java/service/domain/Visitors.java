package service.domain;

import lombok.*;
import org.springframework.stereotype.Component;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.util.List;

@Data
//@NoArgsConstructor
//@AllArgsConstructor
@XmlRootElement(name = "visitors")
@XmlType
@Component
public class Visitors {

    @Getter
    private List<Visitor> visitors;

    @XmlElement(name = "visitor")
    public void setVisitors(List<Visitor> visitors) {
        this.visitors = visitors;
    }

}
