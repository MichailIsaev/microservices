package service.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import org.springframework.stereotype.Component;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.util.List;

@Data
@XmlRootElement(name = "visitors")
@XmlType
public class Visitors {

    @Getter
    private List<Visitor> visitors;

    @XmlElement(name = "visitor")
    public void setVisitors(List<Visitor> visitors) {
        this.visitors = visitors;
    }

}
