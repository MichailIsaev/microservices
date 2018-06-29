package service.parser;

import org.springframework.stereotype.Component;

import javax.xml.bind.JAXBException;
import java.io.File;
import java.io.ObjectOutputStream;

@Component
public interface Parser {

    void marshall(File file, Object object) throws JAXBException;

    Object unmarshall(File file, Class c) throws JAXBException;
}
