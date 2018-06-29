package service.domain;

import lombok.*;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


@Data
@XmlType(name = "visitor")
public class Visitor {
    @Setter @Getter private Integer id;

    @Setter @Getter private String name;

    @Setter @Getter private Integer age;
}
