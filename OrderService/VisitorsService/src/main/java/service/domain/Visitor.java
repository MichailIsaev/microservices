package service.domain;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.xml.bind.annotation.XmlType;


@Data
@XmlType(name = "visitor")
public class Visitor {
    @Setter @Getter private Integer id;

    @Setter @Getter private String name;

    @Setter @Getter private Integer age;
}
